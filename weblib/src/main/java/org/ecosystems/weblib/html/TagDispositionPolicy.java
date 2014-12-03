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

/**
 * Politique de disposition du corp par rapport au tag.
 */
public enum TagDispositionPolicy
{
    /**
     * La balise d'ouverture puis celle de fermeture se trouvent avant le corps.
     */
    BEFORE_BODY,

    /**
     * Le tag englobe le corps.
     */
    ENCLOSE_BODY,

    /**
     * TODO : utiliser ?
     */
    NO_BODY;
}
