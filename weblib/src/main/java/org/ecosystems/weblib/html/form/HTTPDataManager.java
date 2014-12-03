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
package org.ecosystems.weblib.html.form;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import org.ecosystems.weblib.html.RequestPolicy;
import org.ecosystems.weblib.log.LoggerAdapter;
import org.ecosystems.weblib.tools.ArrayTools;

/**
 * Gestionnaire de données persistantes HTTP.
 */
public final class HTTPDataManager
{

    private static final LoggerAdapter LOG_ADAPTER = new LoggerAdapter( HTTPDataManager.class );

    private HTTPDataManager()
    {
    }

    /**
     * Indique si une donnée est présente dans le contexte (même nulle)
     * 
     * @param context Contexte de la page
     * @param key clé de la donnée
     * @param policy politique de récupération
     * @return Vrai si la valeur existe (même culle), faux sinon
     */
    @SuppressWarnings("unchecked")
    public static boolean hasData(final PageContext context, final String key, final RequestPolicy policy)
    {
        switch (policy)
        {
        case GET_POST:
            return ArrayTools.isInEnum( context.getRequest().getParameterNames(), key );

        case SESSION:
            return ArrayTools.isInEnum( context.getSession().getAttributeNames(), key );
        case SESSION_POST_GET:
            return ArrayTools.isInEnum( context.getRequest().getParameterNames(), key )
                    || ArrayTools.isInEnum( context.getSession().getAttributeNames(), key );

        case REQUEST_ATTRIBUTE:
            return ArrayTools.isInEnum( context.getRequest().getAttributeNames(), key );
        case ALL:
        default:
            return ArrayTools.isInEnum( context.getRequest().getParameterNames(), key )
                    || ArrayTools.isInEnum( context.getSession().getAttributeNames(), key )
                    || ArrayTools.isInEnum( context.getRequest().getAttributeNames(), key );

        }
    }

    /**
     * Récupère une donnée de contexte.
     * 
     * @param context contexte de la page
     * @param key clé de l'élément
     * @param policy politique de récupération
     * @return Donnée trouvé, null sinon
     */
    public static Object getData(final PageContext context, final String key, final RequestPolicy policy)
    {
        LOG_ADAPTER.debug( "getData, key = ", key, " method = ", policy );

        Object value = null;
        switch (policy)
        {
        case GET_POST:
            value = context.getRequest().getParameter( key );
            break;
        case SESSION:
            value = context.getSession().getAttribute( key );
            break;
        case SESSION_POST_GET:
            value = context.getRequest().getParameter( key );
            if (value == null)
            {
                value = context.getSession().getAttribute( key );
            }
            break;
        case REQUEST_ATTRIBUTE:
            value = context.getRequest().getAttribute( key );
            break;
        case ALL:
        default:
            value = context.getRequest().getAttribute( key );
            if (value == null)
            {
                value = context.getRequest().getParameter( key );
                if (value == null)
                {
                    value = context.getSession().getAttribute( key );
                }
            }
            break;

        }

        LOG_ADAPTER.debug( "getData return, value = ", value ); //$NON-NLS-1$

        return value;
    }

    /**
     * Défini une valeur de contexte.
     * 
     * @param request requête
     * @param key clé de l'élément
     * @param value valeur à définir
     * @param policy politique de définition
     */
    public static void setData(final HttpServletRequest request, final String key, final Object value, final RequestPolicy policy)
    {

        LOG_ADAPTER.debug( "setData, key =", key, " value = ", value, "method = ", policy );
        switch (policy)
        {
        case GET_POST:
            throw new IllegalArgumentException( "type " + policy.toString() + " non compatible avec une écriture" );
        case SESSION:
            request.getSession().setAttribute( key, value );
            break;
        case REQUEST_ATTRIBUTE:
            request.setAttribute( key, value );
            break;
        case ALL:
        default:
            throw new IllegalArgumentException( "Politique " + policy.toString() + " invalide" );

        }
    }

    /**
     * Efface une donnée persistante.
     * 
     * @param request requête
     * @param name nom du paramètre ( clé )
     */
    public static void flush(final HttpServletRequest request, final String name)
    {
        request.removeAttribute( name );
        request.getSession().setAttribute( name, null );
    }

    /**
     * Récupère une donnée de contexte.
     * 
     * @param request requête
     * @param key clé de l'élément
     * @param policy politique de récupération
     * @return Donnée trouvé, null sinon
     */
    public static Object getData(final HttpServletRequest request, final String key, final RequestPolicy policy)
    {
        LOG_ADAPTER.debug( "getData, key = ", key, " method = ", policy ); //$NON-NLS-1$ //$NON-NLS-2$

        Object value = null;
        switch (policy)
        {
        case GET_POST:
            value = request.getParameter( key );
            break;
        case SESSION:
            value = request.getSession().getAttribute( key );
            break;
        case SESSION_POST_GET:
            value = request.getParameter( key );
            if (value == null)
            {
                value = request.getSession().getAttribute( key );
            }
            break;
        case REQUEST_ATTRIBUTE:
            value = request.getAttribute( key );
            break;
        case ALL:
        default:
            value = request.getAttribute( key );
            if (value == null)
            {
                value = request.getParameter( key );
                if (value == null)
                {
                    value = request.getSession().getAttribute( key );
                }
            }
            break;

        }
        LOG_ADAPTER.debug( "getData return, value = ", value ); //$NON-NLS-1$
        return value;
    }
}
