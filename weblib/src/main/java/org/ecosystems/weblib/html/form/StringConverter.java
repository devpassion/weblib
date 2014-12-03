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

import javax.servlet.http.HttpServletRequest;

/**
 * Convertisseur en chaîne ( appelle simplement toString ).
 */
public class StringConverter implements Converter<String>
{

    @Override
    public String convert(final Object o) throws ConversionException
    {
        if (o == null)
        {
            return null;
        }
        return o.toString();
    }

    @Override
    public Class<String> getConvertClass()
    {
        return String.class;
    }

    @Override
    public String toString(final Object value) throws InvalidBoxedTypeException
    {
        if (value == null)
        {
            return ""; //$NON-NLS-1$
        }
        return value.toString();
    }

    @Override
    public void setRequest(final String name, final HttpServletRequest request)
    {

    }

}
