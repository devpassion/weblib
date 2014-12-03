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

/**
 * Exception levée lorsqu'un traitement de formulaire échoue.
 */
public class ManageFormException extends Exception
{

    private static final long serialVersionUID = -5322994043729347668L;

    /**
     * Constructeur par défaut.
     */
    public ManageFormException()
    {
        super();
    }

    /**
     * Crée une nouvelle exception à partir d'un message et d'une cause.
     * 
     * @param message Message d'erreur
     * @param cause Cause de l'erreur
     * @param enableSuppression Active la suppression
     * @param writableStackTrace Indique si la stackTrace est modifiable
     */
    public ManageFormException(final String message, final Throwable cause, final boolean enableSuppression,
            final boolean writableStackTrace)
    {
        super( message, cause, enableSuppression, writableStackTrace );
    }

    /**
     * Crée une nouvelle exception à partir d'un message et d'une cause.
     * 
     * @param message Message d'erreur
     * @param cause Cause de l'erreur
     */
    public ManageFormException(final String message, final Throwable cause)
    {
        super( message, cause );
    }

    /**
     * Crée une nouvelle exception à partir d'un message.
     * 
     * @param message Message d'erreur
     */
    public ManageFormException(final String message)
    {
        super( message );
    }

    /**
     * Crée une nouvelle exception à partird'une cause.
     * 
     * @param cause Cause de l'erreur
     */
    public ManageFormException(final Throwable cause)
    {
        super( cause );
    }

}
