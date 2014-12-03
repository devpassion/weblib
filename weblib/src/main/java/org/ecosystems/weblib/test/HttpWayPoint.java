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
package org.ecosystems.weblib.test;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ecosystems.lib.tools.StringTools;
import org.ecosystems.weblib.tools.HttpTools;

/**
 * Noeud dans un chemin HTTP.
 */
public class HttpWayPoint
{
    private final Map<String, String> requestParameters;

    private final String requestUrl;

    /**
     * Nouveau noeud à partir d'une requête.
     * 
     * @param request Requête de ce point
     */
    public HttpWayPoint(final HttpServletRequest request)
    {
        this.requestParameters = HttpTools.getParameters( request );
        this.requestUrl = request.getRequestURI();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( "HttpWayPoint [requestParameters=" ).append( StringTools.join( ";", this.requestParameters ) ).append( "\n" )
                .append( ", requestUrl=" ).append( this.requestUrl ).append( "]" );
        return builder.toString();
    }

}
