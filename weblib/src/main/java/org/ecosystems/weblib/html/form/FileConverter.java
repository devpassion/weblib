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

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.ecosystems.weblib.log.LoggerAdapter;

/**
 * Convertisseur de fichiers.
 * 
 */
public class FileConverter implements Converter<File>
{

    private static final int SIZE_THRESHOLD = 20 * 1024 * 1024;
    private static final LoggerAdapter LOGGER_ADAPTER = new LoggerAdapter( FileConverter.class );

    @Override
    public File convert(final Object o) throws ConversionException
    {
        LOGGER_ADAPTER.debug( "conversion en fichier de l'objet : ", o );

        if (o == null || !(o instanceof File))
        {
            return null;
        }

        return (File) o;
    }

    @Override
    public String toString(final Object value) throws InvalidBoxedTypeException
    {
        if (value == null)
        {
            return null;
        }

        if (!(value instanceof File))
        {
            throw new InvalidBoxedTypeException( this.getConvertClass(), value );
        }

        return ((File) value).getName();
    }

    @Override
    public Class<File> getConvertClass()
    {
        return File.class;
    }

    @Override
    public void setRequest(final String name, final HttpServletRequest request)
    {
    }

}
