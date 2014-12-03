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

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 * Convertisseur d'éléments de langues, clé vers chaîne de langue.
 */
public interface LanguageConverter
{

    /**
     * Récupère une traduction.
     * 
     * @param namespace espace de noms de l'élément de langue
     * @param langKey clé de l'élément de langue
     * @param locale locale de la traduction
     * @return Traduction de l'élément de langue
     * @throws NoSuchTraductionException Levée si la traduction est introuvable
     * @throws NoSuchTraductionRessourceException Levée si une ressource de traduction est indisponible
     */
    String getTraduction(String namespace, String langKey, Locale locale) throws NoSuchTraductionException,
            NoSuchTraductionRessourceException;

    /**
     * Récupère une traduction avec paramètres.
     * 
     * @param namespace espace de noms de l'élément de langue
     * @param langKey clé de l'élément de langue
     * @param locale locale de la traduction
     * @param parameters paramètres de la chaine de langue
     * @return Traduction de l'élément de langue
     * @throws NoSuchTraductionException Levée si la traduction est introuvable
     * @throws NoSuchTraductionRessourceException Levée si une ressource de traduction est indisponible
     */
    String getTraduction(String namespace, String langKey, Locale locale, Map<String, Object> parameters) throws NoSuchTraductionException,
            NoSuchTraductionRessourceException;

    /**
     * Récupère une traduction avec paramètres.
     * 
     * @param namespace espace de noms de l'élément de langue
     * @param langKey clé de l'élément de langue
     * @param parameters paramètres de la chaine de langue
     * @return Traduction de l'élément de langue avec la locale courante
     * @throws NoSuchTraductionException Levée si la traduction est introuvable
     * @throws NoSuchTraductionRessourceException Levée si une ressource de traduction est indisponible
     * @throws NotYetDefinedLocaleException Levée si la locale courante n'est pas encore disponible
     * 
     * @deprecated Utiliser une méthode spécifiant la langue de la chaine à récupérer
     */
    @Deprecated
    String getTraduction(String namespace, String langKey, Map<String, Object> parameters) throws NoSuchTraductionException,
            NoSuchTraductionRessourceException, NotYetDefinedLocaleException;

    /**
     * Récupère une chaîne de langue en recherchant la langue dans la requête et en la spécifiant dans la réponse si nécessaire.
     * 
     * @param request Requête courante
     * @param response Réponse http
     * @param namespace espace de noms de l'élément de langue
     * @param langKey clé de l'élément de langue
     * @return Traduction de l'élément de langue avec la locale courante
     * @throws NoSuchTraductionException Levée si la traduction est introuvable
     * @throws NoSuchTraductionRessourceException Levée si une ressource de traduction est indisponible
     * @throws NoLanguageAvailableException Levée si aucune langue n'est trouvée dans la configuration
     */
    String getTraduction(HttpServletRequest request, HttpServletResponse response, String namespace, String langKey)
            throws NoSuchTraductionException, NoSuchTraductionRessourceException, NoLanguageAvailableException;

    /**
     * Récupère une chaîne de langue avec paramètres en recherchant la langue dans la requête et en la spécifiant dans la réponse si
     * nécessaire.
     * 
     * @param request Requête courante
     * @param response Réponse http
     * @param namespace espace de noms de l'élément de langue
     * @param langKey clé de l'élément de langue
     * @param parameters paramètres de la chaine de langue
     * @return Traduction de l'élément de langue avec la locale courante
     * @throws NoSuchTraductionException Levée si la traduction est introuvable
     * @throws NoSuchTraductionRessourceException Levée si une ressource de traduction est indisponible
     * @throws NoLanguageAvailableException Levée si aucune langue n'est trouvée dans la configuration
     */
    String getTraduction(HttpServletRequest request, HttpServletResponse response, String namespace, String langKey,
            Map<String, Object> parameters) throws NoSuchTraductionException, NoSuchTraductionRessourceException,
            NoLanguageAvailableException;

    /**
     * Récupère une chaîne de langue en recherchant la langue dans la requête et en la spécifiant dans la réponse si nécessaire.
     * 
     * @param context Contexte de page
     * @param namespace espace de noms de l'élément de langue
     * @param langKey clé de l'élément de langue
     * @param parameters paramètres de la chaine de langue
     * @return Traduction de l'élément de langue avec la locale courante
     * @throws NoSuchTraductionException Levée si la traduction est introuvable
     * @throws NoSuchTraductionRessourceException Levée si une ressource de traduction est indisponible
     * @throws NoLanguageAvailableException Levée si la locale demandée n'est pas disponible
     */
    String getTraduction(PageContext context, String namespace, String langKey, Map<String, Object> parameters)
            throws NoSuchTraductionException, NoSuchTraductionRessourceException, NoLanguageAvailableException;

    /**
     * Récupère une chaîne de langue avec paramètres en recherchant la langue dans la requête et en la spécifiant dans la réponse si
     * nécessaire.
     * 
     * @param context Contexte de page
     * @param namespace espace de noms de l'élément de langue
     * @param langKey clé de l'élément de langue
     * @return Traduction de l'élément de langue avec la locale courante
     * @throws NoSuchTraductionException Levée si la traduction est introuvable
     * @throws NoSuchTraductionRessourceException Levée si une ressource de traduction est indisponible
     * @throws NoLanguageAvailableException Levée si la locale demandée n'est pas disponible
     */
    String getTraduction(PageContext context, String namespace, String langKey) throws NoSuchTraductionException,
            NoSuchTraductionRessourceException, NoLanguageAvailableException;

    /**
     * Récupère une traduction.
     * 
     * @param namespace espace de noms de l'élément de langue
     * @param langKey clé de l'élément de langue
     * @return Traduction de l'élément de langue avec la locale courante
     * @throws NoSuchTraductionException Levée si la traduction est introuvable
     * @throws NoSuchTraductionRessourceException Levée si une ressource de traduction est indisponible
     * @throws NotYetDefinedLocaleException Levée si la locale courante n'est pas encore disponible
     * @deprecated utiliser une méthode spécifiant la langue à renvoyer
     */
    @Deprecated
    String getTraduction(String namespace, String langKey) throws NoSuchTraductionException, NoSuchTraductionRessourceException,
            NotYetDefinedLocaleException;

    /**
     * Récupère un itérateur parcourant tous les éléments de langue disponibles.
     * 
     * @param lang
     * @return
     * @throws NoSuchTraductionRessourceException
     */
    Iterator<LanguageElement> getAllAvailableElements(String namespace, Locale locale) throws NoSuchTraductionRessourceException;

}
