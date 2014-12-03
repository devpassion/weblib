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
package org.ecosystems.weblib.tools;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import org.ecosystems.lib.tools.StringTools;

/**
 * Outils de gestion d'urls.
 */
public final class HttpTools
{
    private HttpTools()
    {
    }

    /**
     * Récupère une url de requête.
     * 
     * @param request requête http
     * @return Url de la requête
     */
    public static String getUrl(final HttpServletRequest request)
    {
        String url = request.getRequestURL().toString();
        final String queryString = request.getQueryString();
        if (queryString != null)
        {
            url += "?" + queryString;
        }
        return url;
    }

    /**
     * Récupère els paramètres GET d'une URL.
     * 
     * @param query requête HTTP
     * @return Map contenant les paramètres, avec en clé leur nom et en valeur leur valeur
     */
    public static Map<String, String> getQueryMap(final String query)
    {
        final Map<String, String> map = new HashMap<String, String>();
        if (query == null)
            return map;
        final String[] params = query.split( "&" );
        for (final String param : params)
        {
            final String name = param.split( "=" )[0];
            final String value = param.split( "=" )[1];
            map.put( name, value );
        }
        return map;
    }

    /**
     * Remplace les paramètres GET d'une requête ou les crée.
     * 
     * @param url URL à traiter
     * @param paramName nom du paramètre GET à creer ou à mettre à jour
     * @param value Nouvelle valeur du paramètre GET
     * @return URl mise à jour
     * @throws MalformedURLException Levée si l'url crée est mal formée
     */
    public static URL replaceInQuery(final URL url, final String paramName, final String value) throws MalformedURLException
    {
        final Map<String, String> query = HttpTools.getQueryMap( url.getQuery() );
        query.put( paramName, value );

        return new URL( url, url.getPath() + "?" + StringTools.join( "&", query ) );

    }

    /**
     * Remplace les paramètres GET d'une requête ou les crée.
     * 
     * @param url URL à traiter
     * @param paramName nom du paramètre GET à creer ou à mettre à jour
     * @param value Nouvelle valeur du paramètre GET
     * @return URl mise à jour
     * @throws MalformedURLException Levée si l'url crée est mal formée
     */
    public static String replaceInQueryString(final String queryString, final String paramName, final String value)
            throws MalformedURLException
    {
        final Map<String, String> query = HttpTools.getQueryMap( queryString );
        query.put( paramName, value );

        return StringTools.join( "&", query );

    }

    /**
     * Récupère les paramètres d'une requête.
     * 
     * @param request requête HTTP
     * @return Map contenant les paramètres, avec en clé leur nom et en valeur leur valeur
     */
    public static Map<String, String> getParameters(final HttpServletRequest request)
    {
        final Map<String, String> ret = new HashMap<>();
        @SuppressWarnings("unchecked")
        final Enumeration<String> names = request.getParameterNames();
        String name;
        while (names.hasMoreElements())
        {
            name = names.nextElement();
            ret.put( name, StringTools.getStringOrNull( request.getParameter( name ) ) );
        }
        return ret;
    }

    /**
     * Récupère les attributs de session d'une requête.
     * 
     * @param request Requête
     * @return Map contenant les attributs de session, avec en clé les nom d'attibuts et en valeur leur valeur
     */
    public static Map<String, Object> getSessionAttributes(final HttpServletRequest request)
    {
        final Map<String, Object> out = new Hashtable<>();

        if (!request.isRequestedSessionIdValid())
        {
            return out;
        }

        @SuppressWarnings("unchecked")
        final Enumeration<String> names = request.getSession().getAttributeNames();

        String name;
        while (names.hasMoreElements())
        {
            name = names.nextElement();

            out.put( name, request.getSession().getAttribute( name ) );
        }

        return out;
    }

    /**
     * Récupère les attributs d'une requête.
     * 
     * @param request Requête
     * @return Map contenant les attributs de session, avec en clé les nom d'attibuts et en valeur leur valeur
     */
    public static Map<String, Object> getAttributes(final HttpServletRequest request)
    {
        final Map<String, Object> out = new Hashtable<>();

        @SuppressWarnings("unchecked")
        final Enumeration<String> names = request.getAttributeNames();

        String name;
        while (names.hasMoreElements())
        {
            name = names.nextElement();

            out.put( name, request.getAttribute( name ) );
        }

        return out;
    }

    /**
     * Recupère le domain statique associé à une requete ie : static.domaine.com
     * 
     * @param context contexte de la page
     * @return domaine statique cookie-free
     */
    public static String getStaticDomain(final PageContext context)
    {
        return "static." + context.getRequest().getRemoteHost()
                + ((context.getRequest().getRemotePort() != 80) ? context.getRequest().getRemotePort() : "");
    }
}
