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

import org.ecosystems.weblib.log.LoggerAdapter;

/**
 * Validateur d'énumérations.
 * 
 * @param <T> type dénumération
 */
public abstract class EnumValidator<T extends Enum<T>> extends AbstractInputValidator
{

    private final Class<T> clazz;

    /**
     * Crée un nouveau validateur d'énumérations à partir de la classe de l'énumération.
     * 
     * @param enumClass Classe de l'énum
     */
    protected EnumValidator(final Class<T> enumClass)
    {
        this.clazz = enumClass;
    }

    @Override
    public boolean validate(final Object value)
    {
        if (value == null)
        {
            return false;
        }
        try
        {

            Enum.valueOf( this.clazz, value.toString() );
        }
        catch (final IllegalArgumentException e)
        {
            new LoggerAdapter( EnumValidator.class ).debug( e );
            return false;
        }
        return true;
    }

}
