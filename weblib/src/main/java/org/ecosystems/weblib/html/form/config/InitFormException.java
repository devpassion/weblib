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
package org.ecosystems.weblib.html.form.config;

/**
 * Exception levée lorsqu'un formulaire est impossible à initialiser.
 */
public class InitFormException extends Exception
{

    private static final long serialVersionUID = -6190660882911883088L;

    /**
     * Crée une nouvelle exception avec un message d'erreur.
     * 
     * @param message message de l'erreur
     */
    public InitFormException(final String message)
    {
        super( message );
    }

    /**
     * Crée une nouvelle exception avec sans message d'erreur avec une cause.
     * 
     * @param cause Cause de l'erreur
     */
    public InitFormException(final Throwable cause)
    {
        super( cause );
    }

    /**
     * Crée une nouvelle exception avec un message d'erreur et une cause.
     * 
     * @param message message de l'erreur
     * @param cause Cause de l'erreur
     */
    public InitFormException(final String message, final Throwable cause)
    {
        super( message, cause );
    }

    /**
     * Crée une nouvelle exception avec un message d'erreur et une cause.
     * 
     * @param message message de l'erreur
     * @param cause Cause de l'erreur
     * @param enableSuppression active la suppression
     * @param writableStackTrace indique si la StackTrace est modifibale
     */
    public InitFormException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace)
    {
        super( message, cause, enableSuppression, writableStackTrace );
    }

}
