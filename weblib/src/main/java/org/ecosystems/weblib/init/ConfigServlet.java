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

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.ecosystems.lib.config.Configurator;

/**
 * Servlet chargeant la configuration.
 */
public class ConfigServlet extends HttpServlet
{

    private static final long serialVersionUID = -6508695485901124040L;

    private static final String GENERAL_CONFIG_FILENAME = "config-location"; //$NON-NLS-1$

    @Override
    public void init(final ServletConfig config) throws ServletException
    {
        System.out.println( "Tentative de chargement de la configuration" ); //$NON-NLS-1$

        File configFile;
        try
        {
            System.setProperty( "log4j.defaultInitOverride", Boolean.TRUE.toString().toLowerCase() ); //$NON-NLS-1$
            System.setProperty( "log4j.debug", Boolean.TRUE.toString().toLowerCase() ); //$NON-NLS-1$
            configFile = InitServletTools.getRealFile( config, GENERAL_CONFIG_FILENAME );
            System.out.println( "Fichier de configuration trouvé" ); //$NON-NLS-1$
        }
        catch (FileNotFoundException e1)
        {
            throw new ServletException( "Fichier de configuration générale introuvable", e1 ); //$NON-NLS-1$
        }

        try
        {
            final XMLConfiguration xmlConfig = new XMLConfiguration( configFile );
            Configurator.setConfig( xmlConfig );
            System.out.println( "Configuration générale chargée avec succès" ); //$NON-NLS-1$
        }
        catch (ConfigurationException e)
        {
            throw new ServletException( e );
        }

        super.init( config );
    }

}
