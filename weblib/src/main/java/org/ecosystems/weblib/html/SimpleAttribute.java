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
package org.ecosystems.weblib.html;

import org.ecosystems.lib.tools.StringTools;
import org.ecosystems.weblib.log.LoggerAdapter;

/**
 * Attribut HTML simple.
 * 
 * @param <T> Type de l'attribut
 */
public class SimpleAttribute<T> implements Attribute<T>
{

    private final AttributeType<T> attributeType;

    private final String name;

    private T value;

    private LoggerAdapter logAdp;

    // private boolean required;

    /**
     * Crée un attribut non requis.
     * 
     * @param attributeType Type d'attribut
     * @param name nom de l'attribut
     * @param value valeur de l'attribut, sera convertie dans le type correct
     * @throws InvalidAttributeTypeException Levée lorsque le type d'attribut est invalide par rapport à se valeur
     */
    public SimpleAttribute(final AttributeType<T> attributeType, final String name, final Object value)
            throws InvalidAttributeTypeException
    {
        this.name = name;
        this.attributeType = attributeType;
        try
        {
            this.getLogger().trace( "value = ".concat( StringTools.getStringOrNull( value ) ) ); //$NON-NLS-1$
            this.value = attributeType.fromObject( value );
        }
        catch (InvalidAttributeTypeException e)
        {
            final InvalidAttributeTypeException t = new InvalidAttributeTypeException( name, null, e );
            this.getLogger().fatal( "", t ); //$NON-NLS-1$
            throw t;
        }
    }

    /**
     * Récupère un logger.
     * 
     * @return logger associé à la classe.
     */
    protected LoggerAdapter getLogger()
    {
        if (this.logAdp == null)
        {
            this.logAdp = new LoggerAdapter( this.getClass() );
        }
        return this.logAdp;
    }

    @Override
    public AttributeType<T> getAttributeType()
    {
        return this.attributeType;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public T getValue()
    {
        return this.value;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append( this.getName() );
        sb.append( "=\"" ); //$NON-NLS-1$
        sb.append( this.getValue().toString() );
        sb.append( "\" " ); //$NON-NLS-1$
        return sb.toString();
    }

}
