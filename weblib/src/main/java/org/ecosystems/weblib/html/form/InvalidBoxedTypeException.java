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
 * Exception levée lorsqu'un type boxé ne peut être déboxé.
 */
public class InvalidBoxedTypeException extends Exception
{

    private static final long serialVersionUID = -3420538149964420380L;

    private final Class<?> missingDebox;

    private final Object o;

    /**
     * Nouvelle exception à partir d'une classe et d'un objet dont la conversion a échoué.
     * 
     * @param missingDebox CLasse vers laquelle on a tenté de déboxer un objet
     * @param o object dont le unboxing a échoué
     */
    public InvalidBoxedTypeException(final Class<?> missingDebox, final Object o)
    {
        super();
        this.missingDebox = missingDebox;
        this.o = o;
    }

    /**
     * Nouvelle exception à partir d'une classe, d'un objet dont la conversion a échoué et d'une cause.
     * 
     * @param missingDebox CLasse vers laquelle on a tenté de déboxer un objet
     * @param o object dont le unboxing a échoué
     * @param cause Cause de l'erreur
     */
    public InvalidBoxedTypeException(final Class<?> missingDebox, final Object o, final Throwable cause)
    {
        super( cause );
        this.missingDebox = missingDebox;
        this.o = o;
    }

    @Override
    public String getMessage()
    {
        return StringTools.concat( "Unboxing échoué vers la classe", //$NON-NLS-1$
                StringTools.getStringOrNull( this.missingDebox ), " de l'objet  ", //$NON-NLS-1$
                StringTools.getStringOrNull( this.o ), " de type ", (this.o != null) ? this.o.getClass().toString() : "null" ); //$NON-NLS-1$
    }
}
