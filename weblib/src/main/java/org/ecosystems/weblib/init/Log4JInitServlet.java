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

import java.io.File;
import java.io.FileNotFoundException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.xml.DOMConfigurator;
import org.ecosystems.lib.tools.StringTools;

/**
 * Servlet implementation class Log4JInitServlet.
 */
public class Log4JInitServlet extends HttpServlet
{

    private static final long serialVersionUID = 1L;

    private static final String XML_LOCATION_PARAMETER = "log4j-xml-location"; //$NON-NLS-1$

    /**
     * Constructeur par défaut.
     * 
     * @see HttpServlet#HttpServlet()
     */
    public Log4JInitServlet()
    {
        super();
    }

    @Override
    public void init(final ServletConfig config) throws ServletException
    {
        System.out.println( "Log4JInitServlet is initializing log4j" ); //$NON-NLS-1$

        File configFile;
        try
        {
            configFile = InitServletTools.getRealFile( config, XML_LOCATION_PARAMETER );
        }
        catch (FileNotFoundException e)
        {
            throw new ServletException( e );
        }

        System.out.println( StringTools.concat( "Initializing log4j with: ", //$NON-NLS-1$
                configFile.getAbsolutePath() ) );

        DOMConfigurator.configure( configFile.getAbsolutePath() );

        super.init( config );

    }

}
