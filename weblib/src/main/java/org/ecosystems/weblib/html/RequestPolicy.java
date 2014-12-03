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
 * Politique de recherche du paramètre à afficher.
 */
public enum RequestPolicy
{
    /**
     * Paramètre GET ou POST si introuvable.
     */
    GET_POST,

    /**
     * Paramètre de session.
     */
    SESSION,

    /**
     * Parametre de Cookie
     */
    // COOKIE,

    /**
     * Paramètre de session puis recherche dans POST puis GET si introuvable.
     */
    SESSION_POST_GET,

    /**
     * Attribut de requête.
     */
    REQUEST_ATTRIBUTE,

    /**
     * Scope session, get, post, ou requete.
     */
    ALL

}
