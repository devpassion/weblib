/**
 * Copyright (C) 2011 e-cosystems — Tous droits réservés.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.ecosystems.weblib.lang;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.ecosystems.lib.config.Configurator;
import org.ecosystems.weblib.log.LoggerAdapter;
import org.ecosystems.weblib.tools.ClassTools;

/**
 * Gestionnaire de langues.
 */
public final class LanguageManager
{
    private static final LoggerAdapter LOGGER_ADAPTER = new LoggerAdapter( LanguageManager.class );

    private static final String LANGUAGE_ATTRIBUTE_NAME = "org.ecosystems.weblib.lang.LanguageManager.lang_attribute";

    /**
     * Nom du paramètre contenant la langue.
     */
    private static String languageParameterName = null;

    private static String defaultLang = null;

    private static LanguageConverter convertor;

    private static List<String> availablesLanguages = null;

    private static List<String> allLanguages = null;

    private static Locale currentLocale = null;

    private LanguageManager()
    {
        // Classe statique
    }

    /**
     * Récupère la locale courante. Vérifie dans l'ordre :
     * - Si un attribut de requête est déjà stocké pour cette requête, on l'utilise.
     * - Si un paramètre ordonnant un changement de langue est envoyé, dans ce cas on défini le cookie de langue et on
     * retourne cette locale - Si un cookie de langue est posé, dans ce cas on retourne la locale
     * - Si le user-agent spécifie une langue, dans ce cas on retourne la locale si elle est valide
     * - Sinon on renvoie une locale par défaut
     * Dans tous les cas, sauf si un attibut de requête est déjà défini, on défini la langue avec un attribut de requête
     * 
     * @param context Contexte de la page
     * @return Locale courant
     * @throws NoLanguageAvailableException Levée si aucune langue n'est disponible
     */
    public static Locale getCurrentLocale(final PageContext context) throws NoLanguageAvailableException
    {
        return getCurrentLocale( (HttpServletRequest) context.getRequest(), (HttpServletResponse) context.getResponse() );
    }

    /**
     * Equivalent à {@link LanguageManager#getCurrentLocale(HttpServletRequest, HttpServletResponse)} avec le paramètre response à null.
     * 
     * @param request Requête HTTP
     * @return Locale courante associée à la requête
     * @throws NoLanguageAvailableException Levée si aucune langue n'est disponible
     * @deprecated Utiliser {@link LanguageManager#getCurrentLocale(HttpServletRequest, HttpServletResponse)}
     */
    /*
     * @Deprecated
     * public static Locale getCurrentLocale(final HttpServletRequest request) throws NoLanguageAvailableException
     * {
     * return getCurrentLocale( request, null );
     * }
     */

    /**
     * Récupère la locale courante. Vérifie dans l'ordre :
     * - Si un attribut de requête est déjà stocké pour cette requête, on l'utilise.
     * - Si un paramètre ordonnant un changement de langue est envoyé, dans ce cas on défini le cookie de langue et on
     * retourne cette locale - Si un cookie de langue est posé, dans ce cas on retourne la locale
     * - Si le user-agent spécifie une langue, dans ce cas on retourne la locale si elle est valide
     * - Sinon on renvoie une locale par défaut
     * Dans tous les cas, sauf si un attibut de requête est déjà défini, on défini la langue avec un attribut de requête
     * 
     * @param request requête HTTP
     * @param response réponse HTTP
     * @return Locale courante associée à la requête
     * @throws NoLanguageAvailableException Levée si aucune langue n'est disponible
     * @throws NullPointerException Levée si la réponse ou la requête est null
     */
    public static Locale getCurrentLocale(final HttpServletRequest request, final HttpServletResponse response)
            throws NoLanguageAvailableException
    {
        final Object attributeLang = request.getAttribute( LANGUAGE_ATTRIBUTE_NAME );

        // LOGGER_ADAPTER.trace( "Determination de la langue ..." );

        // LOGGER_ADAPTER.trace( "Recherche d'attribut de requête ..." );
        if (attributeLang != null)
        {
            // LOGGER_ADAPTER.trace( "Locale trouvée : ", attributeLang );
            if (attributeLang instanceof Locale)
            {
                LOGGER_ADAPTER.trace( "Locale déjà définie : ", attributeLang );
                return (Locale) attributeLang;
            }
            LOGGER_ADAPTER.warn( "Language request attribute is not a Locale, 'class = ", attributeLang.getClass(), "), toString = '",
                    attributeLang, "'" );

        }

        LOGGER_ADAPTER.trace( "Recherche d'attribut GET ..." );

        response.addHeader( "Cache-Control", "no-cache" );
        response.addHeader( "Pragma", "no-cache" );

        final Object lgParam = request.getParameter( getLanguageParameterName() );
        if (lgParam != null)
        {
            LOGGER_ADAPTER.trace( "attribut trouvé : ", lgParam );
            if (getAvailablesLanguages().contains( lgParam.toString() ))
            {
                LOGGER_ADAPTER.trace( "paramètre trouvé : ", lgParam );

                LOGGER_ADAPTER.trace( "Pose du cookie : ", lgParam );
                final Cookie langCookie = new Cookie( getLanguageParameterName(), lgParam.toString() );
                response.addCookie( langCookie );

                currentLocale = new Locale( lgParam.toString() );
                LOGGER_ADAPTER.trace( "Définition de l'attribut de langue : ", lgParam );
                request.setAttribute( LANGUAGE_ATTRIBUTE_NAME, currentLocale );
                return currentLocale;
            }
            LOGGER_ADAPTER.warn( "Langue invalide : ", lgParam );
        }

        final Cookie[] cookies = request.getCookies();

        LOGGER_ADAPTER.trace( "Recherche de cookie ... " );
        if (cookies != null)
        {
            LOGGER_ADAPTER.trace( "Cookies trouvés ... " );
            Cookie ccookie;
            for (int i = 0; i < cookies.length; i++)
            {
                ccookie = cookies[i];
                if (ccookie.getName().equals( getLanguageParameterName() ) && getAvailablesLanguages().contains( ccookie.getValue() ))
                {
                    LOGGER_ADAPTER.trace( "Langue de cookie : ", ccookie.getValue() );
                    currentLocale = new Locale( ccookie.getValue() );
                    request.setAttribute( LANGUAGE_ATTRIBUTE_NAME, currentLocale );
                    return currentLocale;
                }
            }
        }

        LOGGER_ADAPTER.trace( "Pas de cookie trouvé, recherche de langue dans les locales de requêtes : " );

        final Enumeration<?> en = request.getLocales();
        Locale l;
        while (en.hasMoreElements())
        {
            l = (Locale) en.nextElement();
            LOGGER_ADAPTER.trace( "Locale ", l, " trouvée " );
            if (getAvailablesLanguages().contains( l.getLanguage() ))
            {
                LOGGER_ADAPTER.trace( "lang in http-accept-language or server default : ", l.getLanguage() );
                currentLocale = new Locale( l.getLanguage() );
                request.setAttribute( LANGUAGE_ATTRIBUTE_NAME, currentLocale );

                final Cookie langCookie = new Cookie( getLanguageParameterName(), currentLocale.getLanguage() );
                response.addCookie( langCookie );

                return currentLocale;
            }
        }

        currentLocale = new Locale( getDefautlLang() );
        request.setAttribute( LANGUAGE_ATTRIBUTE_NAME, currentLocale );
        return currentLocale;
    }

    /**
     * Récupère la locale courante.
     * 
     * @deprecated utiliser {@link LanguageManager#getCurrentLocale(HttpServletRequest, HttpServletResponse)}
     * @return the currentLocale
     * @throws NotYetDefinedLocaleException Levée si la locale courante n'est pas définie
     */
    @Deprecated
    public static Locale getCurrentLocale() throws NotYetDefinedLocaleException
    {
        if (currentLocale == null)
        {
            throw new NotYetDefinedLocaleException();
        }
        return currentLocale;
    }

    /**
     * Récupère l'ensemble des langues disponibles (ie : traduites et affichables) pour l'appliciation.
     * 
     * @return Set des codes de langue valides
     * @throws NoLanguageAvailableException Levée si aucune langue n'est disponible
     */
    public static List<String> getAvailablesLanguages() throws NoLanguageAvailableException
    {
        if (availablesLanguages == null)
        {
            // Il doit y avoir dans la configuration une liste de chaines, ce
            // qui est forcément possible, même si pas forcément pertinent
            // Ceci justifie le suppressWarning
            @SuppressWarnings("unchecked")
            final List<String> als = Configurator.getConfig().getList( Configurator.Lang.AVAILABLES_LANGS );
            if (als.isEmpty())
            {
                throw new NoLanguageAvailableException();
            }
            availablesLanguages = als;
        }
        return availablesLanguages;
    }

    /**
     * Récupère l'ensemble des langues possibles à la traduction (langues connues, traduites ou non).
     * 
     * @return Set des codes de langue connus
     * @throws NoLanguageAvailableException Levée si aucune langue n'est disponible
     */
    public static List<String> getAllLanguages() throws NoLanguageAvailableException
    {
        if (allLanguages == null)
        {
            // Il doit y avoir dans la configuration une liste de chaines, ce
            // qui est forcément possible, même si pas forcément pertinent
            // Ceci justifie le suppressWarning
            @SuppressWarnings("unchecked")
            final List<String> als = Configurator.getConfig().getList( Configurator.Lang.ALL_LANGS );
            if (als.isEmpty())
            {
                throw new NoLanguageAvailableException();
            }
            allLanguages = als;
        }
        return allLanguages;
    }

    /**
     * Récupère les locales disponibles.
     * 
     * @return liste des locales disponibles
     * @throws NoLanguageAvailableException Levée si aucune locale n'est disponible
     */
    public static List<Locale> getAvailablesLocales() throws NoLanguageAvailableException
    {
        final List<Locale> ret = new ArrayList<Locale>();
        for (final String l : getAvailablesLanguages())
        {
            ret.add( new Locale( l ) );
        }
        return ret;
    }

    /**
     * Récupère les locales connues.
     * 
     * @return liste des locales disponibles
     * @throws NoLanguageAvailableException Levée si aucune locale n'est disponible
     */
    public static List<Locale> getAllLocales() throws NoLanguageAvailableException
    {
        final List<Locale> ret = new ArrayList<Locale>();
        for (final String l : getAllLanguages())
        {
            ret.add( new Locale( l ) );
        }
        return ret;
    }

    /**
     * Récupère le convertisseur de langue.
     * 
     * @return Convertisseur de langue
     * @throws NoSuchLanguageConverterException Levée si le convertisseur est introuvable
     */
    public static LanguageConverter getConvertor() throws NoSuchLanguageConverterException
    {
        if (convertor == null)
        {
            try
            {
                convertor = ClassTools.loadClass( LOGGER_ADAPTER, Configurator.getString( Configurator.Lang.CONVERTER ) );
            }
            catch (ClassNotFoundException | InstantiationException | IllegalAccessException e)
            {
                throw new NoSuchLanguageConverterException( e );
            }
        }
        // @TODO : ???
        return new PropertiesLanguageConverter();
    }

    /**
     * Récupère le nom du paramètre ordonnant une changement de langue.
     * 
     * @return nom du paramètre get ou post ordonnant un changement de langue
     */
    public static String getLanguageParameterName()
    {
        if (languageParameterName == null)
        {
            languageParameterName = Configurator.getString( Configurator.Lang.PARAMETER_NAME );
        }
        return languageParameterName;
    }

    private static String getDefautlLang()
    {
        if (defaultLang == null)
        {
            defaultLang = Configurator.getConfig().getString( Configurator.Lang.DEFAULT_LANG );
        }
        return defaultLang;
    }

    /**
     * Récupération de traduction par défaut pour fonction el.
     * 
     * @param namespace espace de nom
     * @param key clé de l'élément de langue
     * @return Traduction de l'élément de langue avec la locale courante
     * @throws NoSuchTraductionException Levée si la traduction est introuvable
     * @throws NoSuchTraductionRessourceException Levée si une ressource de traduction est indisponible
     * @throws NotYetDefinedLocaleException Levée si la locale courante n'est pas encore disponible
     * @throws NoSuchLanguageConverterException Levée si le convertisseur est introuvable
     */
    @Deprecated
    public static String getTraduction(final String namespace, final String key) throws NoSuchTraductionException,
            NoSuchTraductionRessourceException, NotYetDefinedLocaleException, NoSuchLanguageConverterException
    {
        return getConvertor().getTraduction( namespace, key );
    }

    /**
     * Récupération de traduction par défaut.
     * 
     * @param context Contexte de la page
     * @param namespace espace de nom
     * @param key clé de l'élément de langue
     * @return Traduction de l'élément de langue avec la locale courante
     * @throws NoSuchTraductionException Levée si la traduction est introuvable
     * @throws NoSuchTraductionRessourceException Levée si une ressource de traduction est indisponible
     * @throws NoSuchLanguageConverterException Levée si le convertisseur est introuvable
     * @throws NoLanguageAvailableException Levée si aucun langage n'est disponible
     */
    public static String getTraduction(final PageContext context, final String namespace, final String key)
            throws NoSuchTraductionException, NoSuchTraductionRessourceException, NoSuchLanguageConverterException,
            NoLanguageAvailableException
    {
        return getConvertor().getTraduction( context, namespace, key );
    }

    /**
     * Récupération de traduction par défaut pour fonction el.
     * 
     * @param context Contexte de la page
     * @param namespace espace de nom
     * @param key clé de l'élément de langue
     * @return Traduction de l'élément de langue avec la locale courante
     * @throws JspException Exception dont la cause explicite l'erreur de langue
     */
    public static String getELTraduction(final PageContext context, final String namespace, final String key) throws JspException
    {
        try
        {
            return getConvertor().getTraduction( context, namespace, key );
        }
        catch (NoSuchTraductionException | NoSuchTraductionRessourceException | NoLanguageAvailableException
                | NoSuchLanguageConverterException e)
        {
            throw new JspException( e );
        }
    }

}
