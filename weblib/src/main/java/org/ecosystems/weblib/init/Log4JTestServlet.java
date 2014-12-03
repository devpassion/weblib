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
package org.ecosystems.weblib.init;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Servlet de test du logging.
 */
public class Log4JTestServlet extends HttpServlet
{

    private static final long serialVersionUID = -1118892964747009580L;

    private static Logger log = Logger.getLogger( Log4JTestServlet.class );

    /**
     * Constructeur par défaut.
     * 
     * @see HttpServlet#HttpServlet()
     */
    public Log4JTestServlet()
    {
        super();
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException
    {
        response.setContentType( "text/html" ); //$NON-NLS-1$
        final PrintWriter out = response.getWriter();
        out.println( "Howdy" ); //$NON-NLS-1$
        log.debug( "debug message" ); //$NON-NLS-1$
        log.info( "info message" ); //$NON-NLS-1$
        log.warn( "warn message" ); //$NON-NLS-1$
        log.error( "error message" ); //$NON-NLS-1$
        log.fatal( "fatal message" ); //$NON-NLS-1$
    }

}
