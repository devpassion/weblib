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
package org.ecosystems.weblib.html;

import javax.servlet.http.HttpServletRequest;

import org.ecosystems.weblib.html.form.ConversionException;
import org.ecosystems.weblib.html.form.Converter;
import org.ecosystems.weblib.html.form.InvalidBoxedTypeException;

/**
 * COnvertisseur d'énumérations.
 * 
 * @param <T> type de l'enumération
 */
public abstract class EnumConverter<T extends Enum<T>> implements Converter<T>
{

    @Override
    public T convert(final Object o) throws ConversionException
    {
        if (o == null)
        {
            return null;
        }
        return Enum.valueOf( this.getConvertClass(), o.toString() );
    }

    @Override
    public String toString(final Object value) throws InvalidBoxedTypeException
    {
        if (value == null)
        {
            return null;
        }
        return Enum.valueOf( this.getConvertClass(), value.toString() ).toString();
    }

    @Override
    public void setRequest(final String name, final HttpServletRequest request)
    {
    }

}
