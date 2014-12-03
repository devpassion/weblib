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

import org.ecosystems.lib.tools.StringTools;

/**
 * Exception levée lorsqu'un champ de formulaire est introuvable ou incohérent.
 */
public class NoSuchFieldException extends Exception
{

    private static final long serialVersionUID = -9178827909184471161L;

    private final String fieldName;

    /**
     * Crée une nouvelle exception à partir d'un nom de champ introuvable.
     * 
     * @param fieldName Nom du champ de formulaire introuvable
     */
    public NoSuchFieldException(final String fieldName)
    {
        this.fieldName = fieldName;
    }

    /**
     * Crée une nouvelle exception à partir d'un nom de champ introuvable et d'une cause.
     * 
     * @param fieldName Nom du champ de formulaire introuvable
     * @param cause cause de l'erreur
     * @param enableSuppression active la suppression
     * @param writableStackTrace indique si la StackTrace est modifiable
     */
    public NoSuchFieldException(final String fieldName, final Throwable cause, final boolean enableSuppression,
            final boolean writableStackTrace)
    {
        super( "", cause, enableSuppression, writableStackTrace );
        this.fieldName = fieldName;
    }

    /**
     * Crée une nouvelle exception à partir d'un nom de champ introuvable et d'une cause.
     * 
     * @param fieldName Nom du champ de formulaire introuvable
     * @param cause cause de l'erreur
     */
    public NoSuchFieldException(final String fieldName, final Throwable cause)
    {
        super( cause );
        this.fieldName = fieldName;
    }

    /**
     * Récupère le nom du champ de formulaire introuvable.
     * 
     * @return Nom du champ de formulaire introuvable
     */
    public String getFieldName()
    {
        return this.fieldName;
    }

    @Override
    public String getMessage()
    {
        return StringTools.concat( "Champ de formulaire introuvable : ", this.fieldName );
    }
}
