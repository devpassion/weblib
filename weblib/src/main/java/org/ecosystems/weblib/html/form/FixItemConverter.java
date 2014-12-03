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

import org.ecosystems.lib.sql.FixItem;

/**
 * Convertiseur de {@link FixItem}.
 * 
 * @param <T> Type de l'item
 */
public abstract class FixItemConverter<T extends FixItem> implements Converter<T>
{

    @Override
    public T convert(final Object o) throws ConversionException
    {
        return (T) this.getFixItemInstance().fromName( o.toString() );
    }

    @Override
    public String toString(final Object value) throws InvalidBoxedTypeException
    {
        if (value == null)
        {
            return "";
        }

        final FixItem fromName = this.getFixItemInstance().fromName( value.toString() );

        if (fromName == null)
        {
            return "";
        }

        return fromName.getName();
    }

    protected abstract T getFixItemInstance();

    @Override
    public void setRequest(final String name, final HttpServletRequest request)
    {
        // TODO Auto-generated method stub

    }

}
