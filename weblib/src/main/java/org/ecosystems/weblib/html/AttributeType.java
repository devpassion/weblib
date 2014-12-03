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
import org.ecosystems.weblib.html.form.ButtonTag;
import org.ecosystems.weblib.html.form.ButtonTag.ButtonType;
import org.ecosystems.weblib.html.form.InputTag.InputType;

/**
 * Type d'attribut.
 * 
 * @param <T> Type de la valeur de l'attribut
 */
public abstract class AttributeType<T>
{
    private final Class<T> clazz;

    /**
     * Crée un type d'attribut à partir d'une classe.
     * 
     * @param attributeClazz classe de la valeur de l'attribut
     */
    public AttributeType(final Class<T> attributeClazz)
    {
        super();
        this.clazz = attributeClazz;
    }

    /**
     * Récupère la classe de l'attribut.
     * 
     * @return the clazz
     */
    protected Class<?> getAttributeClazz()
    {
        return this.clazz;
    }

    /**
     * Converti un objet en valeur d'attribut.
     * 
     * @param object objet à convertir
     * @return valeur convertie
     * @throws InvalidAttributeTypeException Levée lorsq'un type d'attibut est invalide
     */
    public abstract T fromObject(Object object) throws InvalidAttributeTypeException;

    /**
     * Récupère le type Attribute<Integer>.
     * 
     * @return Attribut de type entier
     */
    public static AttributeType<Integer> getIntegerAttributeType()
    {
        return new AttributeType<Integer>( Integer.class )
        {

            @Override
            public Integer fromObject(final Object object)
            {
                return Integer.valueOf( object.toString() );
            }

        };
    }

    /**
     * Récupère le type Attribute<String>.
     * 
     * @return Attribut de type String
     */
    public static AttributeType<String> getStringAttributeType()
    {
        return new AttributeType<String>( String.class )
        {

            @Override
            public String fromObject(final Object object)
            {
                return StringTools.getStringOrNull( object, "" ); //$NON-NLS-1$
            }

        };
    }

    /**
     * Récupère le type Attribute<InputType>.
     * 
     * @return Attribut de type {@link InputType}
     */
    public static AttributeType<InputType> getInputTypeAttributeType()
    {
        return new AttributeType<InputType>( InputType.class )
        {

            @Override
            public InputType fromObject(final Object object) throws InvalidAttributeTypeException
            {
                try
                {
                    return InputType.valueOf( object.toString().toUpperCase() );
                }
                catch (IllegalArgumentException e)
                {
                    throw new InvalidAttributeTypeException( "unknow", null, e ); //$NON-NLS-1$
                }
            }

        };
    }

    /**
     * Récupère le type Attribute<ButtonTag.ButtonType> (pour les types de bouton de soumission).
     * 
     * @return Type d'attribut "type" pour le tag {@link ButtonTag}
     */
    public static AttributeType<ButtonTag.ButtonType> getButtonTypeAttributeType()
    {
        return new AttributeType<ButtonTag.ButtonType>( ButtonType.class )
        {

            @Override
            public ButtonType fromObject(final Object object) throws InvalidAttributeTypeException
            {
                try
                {
                    return ButtonTag.ButtonType.valueOf( object.toString().toUpperCase() );
                }
                catch (IllegalArgumentException e)
                {
                    throw new InvalidAttributeTypeException( "unknow", null, e ); //$NON-NLS-1$
                }
            }

        };
    }

    /**
     * Récupère le type Attribute<Bolean> (faux si la valeur est vide, vrai sinon).
     * 
     * @return Attribut de type {@link Boolean}
     */
    public static AttributeType<Boolean> getBooleanAttributeType()
    {
        return new AttributeType<Boolean>( Boolean.class )
        {

            @Override
            public Boolean fromObject(final Object object) throws InvalidAttributeTypeException
            {
                if (object == null || object.toString().isEmpty())
                {
                    return Boolean.FALSE;
                }
                return Boolean.TRUE;
            }
        };
    }

}
