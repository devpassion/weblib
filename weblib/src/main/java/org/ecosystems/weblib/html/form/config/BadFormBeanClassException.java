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
 * Exception levée lorsqu'un formulaire est associé à un type de bean qui ne lui correspond pas.
 */
public class BadFormBeanClassException extends Exception
{

    private static final long serialVersionUID = 8149292847956629382L;

    private final String formName;

    private final Class<?> formBeanClass;

    /**
     * Nouvelle excpetion à partie d'un nom de formulaire et d'une classe de bean.
     * 
     * @param formName Nom du formulaire
     * @param formBeanClass Classe du bean dont l'absence d'association provoque l'erreur
     */
    public BadFormBeanClassException(final String formName, final Class<?> formBeanClass)
    {
        super();
        this.formName = formName;
        this.formBeanClass = formBeanClass;
    }

    /**
     * Nouvelle excpetion à partie d'un nom de formulaire, d'une classe de bean et d'une cause.
     * 
     * @param formName Nom du formulaire
     * @param formBeanClass Classe du bean dont l'absence d'association provoque l'erreur
     * @param cause Cause de l'erreur
     */
    public BadFormBeanClassException(final String formName, final Class<?> formBeanClass, final Throwable cause)
    {
        super( cause );
        this.formName = formName;
        this.formBeanClass = formBeanClass;

    }

    /**
     * Nouvelle excpetion à partie d'un nom de formulaire, d'une classe de bean et d'une cause.
     * 
     * @param formName Nom du formulaire
     * @param formBeanClass Classe du bean dont l'absence d'association provoque l'erreur
     * @param cause Cause de l'erreur
     * @param enableSuppression active la suppression
     * @param writableStackTrace indique si la StackTrace est modifibale
     */
    public BadFormBeanClassException(final String formName, final Class<?> formBeanClass, final Throwable cause,
            final boolean enableSuppression, final boolean writableStackTrace)
    {
        super( "", cause, enableSuppression, writableStackTrace );
        this.formName = formName;
        this.formBeanClass = formBeanClass;
    }

    @Override
    public String getMessage()
    {
        return StringTools.concat( "Le formulaire ", StringTools.getStringOrNull( this.formName ), " n'est pas associé au bean ",
                StringTools.getStringOrNull( this.formBeanClass ) );
    }
}
