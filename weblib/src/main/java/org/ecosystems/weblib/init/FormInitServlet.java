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

import org.ecosystems.weblib.html.form.config.BadFormBeanClassException;
import org.ecosystems.weblib.html.form.config.FormContextFactoryImpl;
import org.ecosystems.weblib.html.form.config.InitFormException;
import org.ecosystems.weblib.log.LoggerAdapter;

/**
 * Servlet initialisant la configuration de formulaire.
 */
public class FormInitServlet extends HttpServlet
{

    private static final LoggerAdapter LOGGER_ADAPTER = new LoggerAdapter( FormInitServlet.class );

    /**
     * Nom du paramètre du servlet contenant le nom du fichier contenant la configuration xml des formulaires.
     */
    private static final String FORM_LOCATION_PARAM_NAME = "forms-xml-location"; //$NON-NLS-1$

    /**
     * Nom du paramètre qui, si il est à true, indique de chercher le formulaire dans le projet et non dans les resources web.
     */
    private static final String WEB_NAME_PARAM_NAME = "no-web-location";

    private static final long serialVersionUID = 1096078863004783971L;

    @Override
    public void init(final ServletConfig config) throws ServletException
    {

        try
        {
            final File realFile;
            if ("true".equals( config.getInitParameter( WEB_NAME_PARAM_NAME ) ))
            {
                realFile = new File( config.getInitParameter( FORM_LOCATION_PARAM_NAME ) );
                if (!realFile.exists())
                {
                    throw new FileNotFoundException( config.getInitParameter( FORM_LOCATION_PARAM_NAME ) );
                }
            }
            else
            {
                realFile = InitServletTools.getRealFile( config, FORM_LOCATION_PARAM_NAME );
            }
            LOGGER_ADAPTER.info( "Tentative de chargement du fichier " + realFile.getPath() );

            FormContextFactoryImpl.getProvider().loadConfig( realFile );
        }
        catch (FileNotFoundException | InitFormException | BadFormBeanClassException e)
        {
            throw new ServletException( e );
        }
        LOGGER_ADAPTER.info( "Contexte des formulaires chargé avec succès" ); //$NON-NLS-1$

    }
}
