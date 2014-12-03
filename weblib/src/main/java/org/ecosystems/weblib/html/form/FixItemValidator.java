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
 * Validateur d'item Fixe.
 * 
 * @see FixItem
 * 
 * @param <T> type de FixItem
 */
public abstract class FixItemValidator<T extends FixItem> extends AbstractInputValidator
{

    // private static final LoggerAdapter LOGGER_ADAPTER = new LoggerAdapter( FixItemValidator.class );

    @Override
    public boolean validate(final Object value)
    {
        if (value == null)
        {
            return false;
        }

        return this.getFixItemInstance().fromName( value.toString() ) != null;
    }

    /**
     * Récupère une instance de l'item fixe à convertir.
     * TODO : foireux, revoir la classe {@link FixItem}
     * 
     * @return instance
     */
    protected abstract T getFixItemInstance();

    @Override
    public void setRequest(final HttpServletRequest request)
    {
    }

}
