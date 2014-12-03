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
 * Exception levée lorsqu'un formulaire est introuvable dans la configuration.
 */
public class NoSuchFormException extends Exception
{

    private static final long serialVersionUID = 192748558654343594L;

    private String formName;

    /**
     * Nouvelle exception à partir d'un nom de formulaire introuvable.
     * 
     * @param formName Nom du formulaire introuvable
     */
    public NoSuchFormException(final String formName)
    {
        this.formName = formName;
    }

    /**
     * Nouvelle exception à partir d'un nom de formulaire et d'une cause.
     * 
     * @param formName Nom du formulaire introuvable
     * @param cause cause de l'erreur
     * @param enableSuppression Active la suppression
     * @param writableStackTrace StackTrace modifiable
     */
    public NoSuchFormException(final String formName, final Throwable cause, final boolean enableSuppression,
            final boolean writableStackTrace)
    {
        super( "", cause, enableSuppression, writableStackTrace );
        this.formName = formName;
    }

    /**
     * Nouvelle exception à partir d'un nom de formulaire et d'une cause.
     * 
     * @param formName Nom du formulaire introuvable
     * @param cause cause de l'erreur
     */
    public NoSuchFormException(final String formName, final Throwable cause)
    {
        super( formName, cause );
    }

    /**
     * Récupère le nom du formulaire introuvable.
     * 
     * @return the formName
     */
    public String getFormName()
    {
        return this.formName;
    }

    @Override
    public String getMessage()
    {
        return StringTools.concat( "formulaire introuvable : ", this.formName ); //$NON-NLS-1$
    }

}
