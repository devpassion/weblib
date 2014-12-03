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

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.ecosystems.lib.tools.StringTools;
import org.ecosystems.weblib.log.LoggerAdapter;

/**
 * Convertisseur d'éléments de langue basé sur des fichiers properties.
 */
public class PropertiesLanguageConverter implements LanguageConverter
{

    private static final String BUNDLE_NAME = "lang.";

    private final Map<Locale, Map<String, ResourceBundle>> ressourcesMap = new HashMap<Locale, Map<String, ResourceBundle>>();

    private LoggerAdapter logAdp;

    /**
     * Récupère un logger.
     * 
     * @return logger associé à la classe.
     */
    protected LoggerAdapter getLogger()
    {
        if (this.logAdp == null)
        {
            this.logAdp = new LoggerAdapter( this.getClass() );
        }
        return this.logAdp;
    }

    /**
     * Récupère une ressource.
     * 
     * @param namespace escpace de nom
     * @param locale locale de mandée
     * @param noFlushIfError indique si les données de langue ne doivent pas être recherchées de nouveau en cas d'erreur
     * @return Ressource
     * @throws NoSuchTraductionRessourceException Levée si une ressource de traduction est indisponible
     */
    protected ResourceBundle getResourceBundle(final String namespace, final Locale locale, final boolean noFlushIfError)
            throws NoSuchTraductionRessourceException
    {
        this.getLogger().trace( "recherche de ressource : namespace = ", namespace, //$NON-NLS-1$
                ", locale = ", locale, " / no flush :", Boolean.valueOf( noFlushIfError ) ); //$NON-NLS-1$ //$NON-NLS-2$

        if (locale == null)
        {
            throw new NoSuchTraductionRessourceException( "Pas de locale spécifiée" ); //$NON-NLS-1$
        }

        if (!this.ressourcesMap.containsKey( locale ))
        {
            this.ressourcesMap.put( locale, new HashMap<String, ResourceBundle>() );

        }
        final Map<String, ResourceBundle> ns = this.ressourcesMap.get( locale );

        if (!ns.containsKey( namespace ))
        {
            try
            {
                final ResourceBundle found = UTF8ResourceBundle.getBundle( BUNDLE_NAME + namespace, locale, Thread.currentThread()
                        .getContextClassLoader() );
                ns.put( namespace, found );
            }
            catch (final NullPointerException e)
            {
                getLogger().warn( "La recherche de resource a levé une exception", e );
                if (!noFlushIfError)
                {
                    this.flushRessources();
                    return this.getResourceBundle( namespace, locale, true );
                }
                throw new NoSuchTraductionRessourceException( "La recherche de la ressource : " //$NON-NLS-1$
                        + BUNDLE_NAME + namespace + "a echoué", e ); //$NON-NLS-1$
            }

        }

        return ns.get( namespace );
    }

    /**
     * Récupère une ressource.
     * 
     * @param namespace escpace de nom
     * @param locale locale de mandée
     * @return Ressource
     * @throws NoSuchTraductionRessourceException Levée si une ressource de traduction est indisponible
     */
    protected ResourceBundle getResourceBundle(final String namespace, final Locale locale) throws NoSuchTraductionRessourceException
    {
        return this.getResourceBundle( namespace, locale, false );
    }

    /**
     * Récupère une chaîne traduite avec ses paramètres.
     * 
     * @param string chaîne traduite
     * @param parameters paramètres de la chaîne
     * @return Chaîne traduite avec les chaînes entre crochets remplacées par leur paramètre
     */
    protected String getParameterString(final String string, final Map<String, Object> parameters)
    {
        String k;
        String out = string;
        for (final Entry<String, Object> e : parameters.entrySet())
        {
            k = StringTools.concat( "{", e.getKey(), "}" ); //$NON-NLS-1$ //$NON-NLS-2$
            if (string.contains( k ))
            {
                out = out.replace( k, StringTools.getStringOrNull( e.getValue(), "" ) ); //$NON-NLS-1$
            }
        }

        return out;
    }

    @Override
    public String getTraduction(final String namespace, final String langKey, final Locale locale) throws NoSuchTraductionException,
            NoSuchTraductionRessourceException
    {
        return this.getTraduction( namespace, langKey, locale, new HashMap<String, Object>() );
    }

    @Override
    public String getTraduction(final String namespace, final String langKey, final Locale locale, final Map<String, Object> parameters)
            throws NoSuchTraductionException, NoSuchTraductionRessourceException
    {
        return this.getTraduction( namespace, langKey, locale, parameters, false );
    }

    /**
     * Récupère une traduction avec paramètres.
     * 
     * @param namespace Espace de nom de traduction
     * @param langKey Clé de l'élément de langue
     * @param locale Locale demandée
     * @param parameters Paramètres de la chaîne de langue
     * @param noFlushIfError ne pas réinitialiser les données de langue en cas d'erreur
     * @return Traduction
     * @throws NoSuchTraductionException Levée si la traduction est introuvable
     * @throws NoSuchTraductionRessourceException Levée si la resource de traduction est introuvable
     */
    protected String getTraduction(final String namespace, final String langKey, final Locale locale, final Map<String, Object> parameters,
            final boolean noFlushIfError) throws NoSuchTraductionException, NoSuchTraductionRessourceException
    {
        String str;
        try
        {
            str = this.getResourceBundle( namespace, locale ).getString( langKey );
            return this.getParameterString( str, parameters );
        }
        catch (final MissingResourceException e)
        {
            try
            {
                str = this.getResourceBundle( namespace, new Locale( "en" ) ).getString( langKey ); //$NON-NLS-1$
                return this.getParameterString( str, parameters );
            }
            catch (final MissingResourceException e1)
            {
                if (!noFlushIfError)
                {
                    this.flushRessources();
                    return this.getTraduction( namespace, langKey, locale, parameters, true );
                }
                throw new NoSuchTraductionException( namespace, langKey, locale, e1 );
            }
        }
        catch (final Exception e)
        {
            throw new NoSuchTraductionException( namespace, langKey, locale, e );
        }
    }

    /**
     * Vide le cache des ressources.
     */
    public void flushRessources()
    {
        this.getLogger().info( "flush" ); //$NON-NLS-1$
        this.ressourcesMap.clear();
    }

    @Deprecated
    @Override
    public String getTraduction(final String namespace, final String langKey, final Map<String, Object> parameters)
            throws NoSuchTraductionException, NoSuchTraductionRessourceException, NotYetDefinedLocaleException
    {
        return this.getTraduction( namespace, langKey, LanguageManager.getCurrentLocale(), parameters );
    }

    @Deprecated
    @Override
    public String getTraduction(final String namespace, final String langKey) throws NoSuchTraductionException,
            NoSuchTraductionRessourceException, NotYetDefinedLocaleException
    {
        return this.getTraduction( namespace, langKey, LanguageManager.getCurrentLocale(), new HashMap<String, Object>() );
    }

    @Override
    public String getTraduction(final HttpServletRequest request, final HttpServletResponse response, final String namespace,
            final String langKey) throws NoSuchTraductionException, NoSuchTraductionRessourceException, NoLanguageAvailableException
    {
        return this
                .getTraduction( namespace, langKey, LanguageManager.getCurrentLocale( request, response ), new HashMap<String, Object>() );
    }

    @Override
    public String getTraduction(final HttpServletRequest request, final HttpServletResponse response, final String namespace,
            final String langKey, final Map<String, Object> parameters) throws NoSuchTraductionException,
            NoSuchTraductionRessourceException, NoLanguageAvailableException
    {
        return this.getTraduction( namespace, langKey, LanguageManager.getCurrentLocale( request, response ), parameters );
    }

    @Override
    public String getTraduction(final PageContext context, final String namespace, final String langKey,
            final Map<String, Object> parameters) throws NoSuchTraductionException, NoSuchTraductionRessourceException,
            NoLanguageAvailableException
    {
        return this.getTraduction( namespace, langKey,
                LanguageManager.getCurrentLocale( (HttpServletRequest) context.getRequest(), (HttpServletResponse) context.getResponse() ),
                parameters );
    }

    @Override
    public String getTraduction(final PageContext context, final String namespace, final String langKey) throws NoSuchTraductionException,
            NoSuchTraductionRessourceException, NoLanguageAvailableException
    {
        return this.getTraduction( namespace, langKey,
                LanguageManager.getCurrentLocale( (HttpServletRequest) context.getRequest(), (HttpServletResponse) context.getResponse() ),
                new HashMap<String, Object>() );
    }

    @Override
    public Iterator<LanguageElement> getAllAvailableElements(final String namespace, final Locale locale)
            throws NoSuchTraductionRessourceException
    {

        final ResourceBundle b = this.getResourceBundle( namespace, locale );

        // final Iterator<Entry<String, ResourceBundle>> it = ressourcesMap.get( locale ).entrySet().iterator();
        return new Iterator<LanguageElement>()
        {
            Enumeration<String> en = b.getKeys();

            @Override
            public void remove()
            {
                this.en = b.getKeys();
            }

            @Override
            public LanguageElement next()
            {
                final String key = en.nextElement();
                try
                {
                    return new LanguageElement( locale, namespace, key, getTraduction( namespace, key, locale ) );
                }
                catch (NoSuchTraductionException | NoSuchTraductionRessourceException e)
                {
                    logAdp.error( "Erreur à l'itération d'éléments de langue", e );
                    return null;
                }
            }

            @Override
            public boolean hasNext()
            {
                return en.hasMoreElements();
            }
        };
    }
}
