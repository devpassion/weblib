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

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

/**
 * Outils de gestion de fichiers.
 */
public final class FileTools
{
    private FileTools()
    {
    }

    /**
     * Taille du buffer lors de la lecture de flux.
     */
    private static final int BUFFER_SIZE = 1024;

    /**
     * Converti un flux en fichier.
     * 
     * @param basePath Chemin de base
     * @param fileName Nom du fichier à créer
     * @param stream flux d'entrée
     * @return Fichier crée avec le contenu du flux
     * @throws IOException Levée si le fichier existe déjà
     */
    public static File streamToFile(final String basePath, final String fileName, final InputStream stream) throws IOException
    {
        final File file = new File( basePath + File.separatorChar + fileName );

        try
        {
            final OutputStream out = new FileOutputStream( file );
            final byte[] buf = new byte[BUFFER_SIZE];
            int len;
            try
            {
                while ((len = stream.read( buf )) > 0)
                {
                    out.write( buf, 0, len );
                }
            }
            finally
            {
                out.close();
            }
        }
        finally
        {

            stream.close();
        }
        return file;
    }

    /**
     * Imprime une chaine dans un fichier, si le fichier existe, une erreur est générée.
     * 
     * @param path Chemin où imprimer le fichier
     * @param content contenu à imprimer
     * @throws IOException Levée si le fichier existe déjà ou si une erreur I/O apparaît
     */
    public static void printStringToFile(final Path path, final String content) throws IOException
    {
        if (Files.exists( path ))
        {
            throw new FileAlreadyExistsException( "le fichier" + path + "existe déjà" );
        }

        final Set<PosixFilePermission> perms = PosixFilePermissions.fromString( "rw-rw-r-x" );
        final FileAttribute<Set<PosixFilePermission>> attrs = PosixFilePermissions.asFileAttribute( perms );

        Files.createFile( path, attrs );

        final FileWriter writer = new FileWriter( path.toFile() );

        try
        {
            writer.write( content );
        }
        finally
        {
            writer.flush();
        }
    }
}
