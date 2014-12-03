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
 * Exception levée lorsqu'une erreur de conversion de donnée de contexte échoue.
 */
public class ConversionException extends Exception
{

    private static final long serialVersionUID = -2933444440109844653L;

    private final Object in;

    private final Class<?> type;

    /**
     * Crée une nouvelle exception à partir de l'objet dont la conversion échoue et du type vers lequel la conversion a été tentée.
     * 
     * @param in Object dont la conversion a échoué
     * @param type type vers lequel une conversion a été tentée
     */
    public ConversionException(final Object in, final Class<?> type)
    {
        super();
        this.in = in;
        this.type = type;
    }

    /**
     * Crée une nouvelle exception à partir de l'objet dont la conversion échoue, du type vers lequel la conversion a été tentée et d'une
     * cause.
     * 
     * @param in Object dont la conversion a échoué
     * @param type type vers lequel une conversion a été tentée
     * @param cause cause mère de l'erreur
     */
    public ConversionException(final Object in, final Class<?> type, final Throwable cause)
    {
        super( cause );
        this.in = in;
        this.type = type;
    }

    /**
     * Récupère l'objet dont la conversion a échoué.
     * 
     * @return Object dont la conversion a échoué
     */
    public Object getIn()
    {
        return this.in;
    }

    /**
     * Récupère le type vers lequel la conversion a échoué?
     * 
     * @return Type vers lequel une conversion a été tentée
     */
    public Class<?> getType()
    {
        return this.type;
    }

    @Override
    public String getMessage()
    {
        return StringTools.concat( "Conversion échouée,  ", StringTools.getStringOrNull( this.in ), //$NON-NLS-1$
                " en ", StringTools.getStringOrNull( this.type ) ); //$NON-NLS-1$
    }

}
