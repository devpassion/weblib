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
package org.ecosystems.weblib.test;

import org.ecosystems.lib.config.Configurator;

/**
 * Classe indiquant l'état de l'application au niveau des tests.
 * 
 * @author tim288
 * 
 */
public final class TestFlags
{

    private TestFlags()
    {
    }

    /**
     * Indique si l'application est en tests.
     * 
     * @return vrai si l'application est en tests
     */
    public static boolean isTestEnabled()
    {
        return "1".equals( Configurator.getString( Configurator.MAIN.WEB_TEST_MODE ) )
                || "1".equals( System.getProperty( "org.ecosystems.test" ) );
    }

}
