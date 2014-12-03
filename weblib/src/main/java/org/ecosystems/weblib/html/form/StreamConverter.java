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

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Convertisseur en flux d'entrée pour les fichiers uploadés.
 */
public class StreamConverter implements Converter<InputStream>
{

    private HttpServletRequest request;
    private String fieldName;

    @Override
    public InputStream convert(final Object o) throws ConversionException
    {
        // Doit renvoyer le flux depuis la requete sans tenir compte de l'entrée
        // TODO : pas terrible !
        final DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
        final ServletFileUpload uploadHandler = new ServletFileUpload( fileItemFactory );

        try
        {
            @SuppressWarnings("unchecked")
            final List<FileItem> items = uploadHandler.parseRequest( request );
            for (final FileItem fi : items)
            {
                if (fi.getFieldName() == this.fieldName)
                {
                    return fi.getInputStream();
                }
            }
        }
        catch (final FileUploadException | IOException e)
        {
            throw new ConversionException( o, this.getConvertClass(), e );
        }

        return null;
    }

    @Override
    public String toString(final Object value) throws InvalidBoxedTypeException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Class<InputStream> getConvertClass()
    {
        return InputStream.class;
    }

    @Override
    public void setRequest(final String name, final HttpServletRequest request0)
    {
        this.request = request0;
        this.fieldName = name;
    }

}
