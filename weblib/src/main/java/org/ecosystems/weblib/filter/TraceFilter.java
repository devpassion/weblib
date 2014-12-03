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
package org.ecosystems.weblib.filter;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.ecosystems.weblib.log.LoggerAdapter;
import org.ecosystems.weblib.test.HttpWay;
import org.ecosystems.weblib.test.HttpWayPoint;

/**
 * Filtre tracant les parcours des visiteurs.
 */
public class TraceFilter implements Filter
{

    /**
     * Nom du paramètre du filtre indiquant le pattern des urls tracées.
     */
    private static final String MATCH_URL_PARAM_NAME = "matchUrl";

    /**
     * Adapteur de logs.
     */
    private static final LoggerAdapter LOGGER_ADAPTER = new LoggerAdapter( TraceFilter.class );

    /**
     * Pattern.
     */
    private Pattern matchUrlRegex;

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException
    {
        final String pattern = filterConfig.getInitParameter( MATCH_URL_PARAM_NAME );
        if (pattern != null)
        {
            this.matchUrlRegex = Pattern.compile( "\\Q" + pattern.replace( "*", "\\E(.*)\\Q" ) + "\\E" );
        }
        else
        {
            this.matchUrlRegex = Pattern.compile( "(.*)" );
        }

        LOGGER_ADAPTER.debug( "regex = ", this.matchUrlRegex.pattern() );
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException,
            ServletException
    {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (this.matchUrlRegex.matcher( httpRequest.getRequestURI() ).matches())
        {

            final Object oHttpWay = httpRequest.getSession().getAttribute( HttpWay.class.toString() );
            final HttpWay way;

            if (oHttpWay == null)
            {
                way = new HttpWay();
            }
            else if (oHttpWay instanceof HttpWay)
            {
                way = (HttpWay) oHttpWay;
            }
            else
            {

                LOGGER_ADAPTER.warn( "HttpWay not good type : ", oHttpWay.toString() );
                way = new HttpWay();
            }
            final HttpWayPoint point = new HttpWayPoint( httpRequest );
            way.addPoint( point );
            LOGGER_ADAPTER.debug( "new point : ", point );
            httpRequest.getSession().setAttribute( HttpWay.class.toString(), way );
        }

        chain.doFilter( request, response );
    }

    @Override
    public void destroy()
    {
        //
    }
}
