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

import org.ecosystems.lib.tools.StringTools;

/**
 * Exception levée lorsqu'un un champ input est introuvable.
 */
public class NoSuchFieldException extends Exception
{
    private static final long serialVersionUID = -5991447523665289283L;

    private final String formName;

    private final String fieldName;

    /**
     * Nouvelle exception à partir de noms de formulaire et de champ.
     * 
     * @param formName Nom du formulaire dont un champ input est introuvable
     * @param fieldName nom du champ introuvable
     */
    public NoSuchFieldException(final String formName, final String fieldName)
    {
        super();
        this.formName = formName;
        this.fieldName = fieldName;
    }

    /**
     * Récupère le nom du formulaire où un champ est introuvable.
     * 
     * @return the form
     */
    public String getFormName()
    {
        return this.formName;
    }

    /**
     * Récupère le nom du champ introuvable.
     * 
     * @return the fieldName
     */
    public String getFieldName()
    {
        return this.fieldName;
    }

    @Override
    public String getMessage()
    {
        return StringTools.concat( "Champ introuvable dans le formulaire  ", this.formName, " : ", this.fieldName );
    }

}
