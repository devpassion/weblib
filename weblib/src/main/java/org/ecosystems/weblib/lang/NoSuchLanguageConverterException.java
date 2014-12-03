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
/**
 * 
 */
package org.ecosystems.weblib.lang;

/**
 * Exception générée lorsque le convertisseur d'éléments de langue est introuvable.
 */
public class NoSuchLanguageConverterException extends Exception
{

    private static final long serialVersionUID = 6908897834471387237L;

    /**
     * Constructeur par défaut.
     */
    public NoSuchLanguageConverterException()
    {

    }

    /**
     * Nouvelle exception avec un message.
     * 
     * @param message Message d'erreur
     */
    public NoSuchLanguageConverterException(final String message)
    {
        super( message );
    }

    /**
     * Nouvelle exception avec une cause.
     * 
     * @param cause Cause de l'erreur
     */
    public NoSuchLanguageConverterException(final Throwable cause)
    {
        super( cause );
    }

    /**
     * Nouvelle exception à partir d'un message et d'une cause.
     * 
     * @param message Message d'erreur
     * @param cause Cause de l'erreur
     */
    public NoSuchLanguageConverterException(final String message, final Throwable cause)
    {
        super( message, cause );
    }

    /**
     * Nouvelle exception à partir d'un message et d'une cause.
     * 
     * @param message Message d'erreur
     * @param cause Cause de l'erreur
     * @param enableSuppression indique si la suppression est activée
     * @param writableStackTrace indique si la stackTrace est modifiable
     */
    public NoSuchLanguageConverterException(final String message, final Throwable cause, final boolean enableSuppression,
            final boolean writableStackTrace)
    {
        super( message, cause, enableSuppression, writableStackTrace );
    }

}
