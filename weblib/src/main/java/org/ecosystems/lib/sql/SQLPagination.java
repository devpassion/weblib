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
package org.ecosystems.lib.sql;

import org.ecosystems.lib.config.Configurator;

/**
 * Pagination SQL.
 */
public interface SQLPagination extends Pagination
{
    /**
     * Index du premier élément.
     * 
     * @return index SQL du premier élément de la page courante
     */
    int beginIndex();

    /**
     * Pagination totale (toutes les pages).
     */
    SQLPagination ALL = new SQLPagination()
    {

        @Override
        public int resultPerPage()
        {
            return Integer.parseInt( Configurator.getString( Configurator.JDBC.MAX_RESULTS ) );
        }

        @Override
        public int pages(final int totalResults)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public int current()
        {
            return 1;
        }

        @Override
        public int beginIndex()
        {
            return 0;
        }
    };
}
