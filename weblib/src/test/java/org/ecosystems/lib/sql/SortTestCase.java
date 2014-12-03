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

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test de la classe {@link Sort}
 */
public class SortTestCase
{

    /**
     * Test du type {@link Sort}
     */
    @Test
    public void testSort()
    {
        final Sort sort = new Sort( new FieldSort[] { new FieldSort( "one", Sort.Order.ASC ), new FieldSort( "two", Sort.Order.DESC ) } );
        assertTrue( sort.toSQLString().equals( "one ASC, two DESC" ) );
    }
}
