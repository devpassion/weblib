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
import javax.servlet.ServletContext;

import org.ecosystems.lib.tools.StringTools;

/**
 * Outils d'initialisation de servlet.
 */
public final class InitServletTools
{
    private InitServletTools()
    {
    }

    /**
     * Récupère un fichier dans le contexte web.
     * 
     * @param config configuration de la servlet
     * @param parameterFileName nom du paramètre de configuration de la servlet qui spécifie le nom du fichier à ouvrir
     * @return Fichier demandé
     * @throws FileNotFoundException Levée si le fichier n'existe pas
     */
    public static File getRealFile(final ServletConfig config, final String parameterFileName) throws FileNotFoundException
    {
        final String location = config.getInitParameter( parameterFileName );

        final ServletContext sc = config.getServletContext();

        String webAppPath = sc.getRealPath( "/" ); //$NON-NLS-1$
        if (webAppPath == null)
        {
            webAppPath = "/"; //$NON-NLS-1$
        }
        final String fileName = StringTools.concat( webAppPath, location );
        final File file = new File( fileName );
        if (!file.exists())
        {
            throw new FileNotFoundException( fileName );
        }
        return file;
    }

}
