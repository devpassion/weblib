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
package org.ecosystems.weblib.lang;

/**
 * Exception levée si la locale est demandée avant d'avoir été définie dans une servlet.
 * 
 * @see LanguageManager#getCurrentLocale(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
 */
public class NotYetDefinedLocaleException extends Exception
{

    private static final long serialVersionUID = 9078047188239817829L;

    /**
     * Constructeur par défaut.
     */
    public NotYetDefinedLocaleException()
    {
    }

    /**
     * Nouvelle exception à partir d'un message.
     * 
     * @param message message
     */
    public NotYetDefinedLocaleException(final String message)
    {
        super( message );
    }

    /**
     * Nouvelle exception à partir d'une cause.
     * 
     * @param cause cause
     */
    public NotYetDefinedLocaleException(final Throwable cause)
    {
        super( cause );
    }

    /**
     * Nouvelle exception à partir d'un message et d'une cause.
     * 
     * @param message message
     * @param cause cause
     */
    public NotYetDefinedLocaleException(final String message, final Throwable cause)
    {
        super( message, cause );
    }

}
