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

import java.util.HashSet;
import java.util.Set;

/**
 * Convertisseur d'un tableau d'{@link Object} vers un tableau de {@link String}.
 * 
 */
public class StringArrayConverter extends SetConverter<String>
{

    @Override
    public Class<Set<String>> getConvertClass()
    {
        final Set<String> s = new HashSet<>();
        return (Class<Set<String>>) s.getClass();
    }

    @Override
    protected Converter<String> getElementConverter()
    {
        return new StringConverter();
    }

}
