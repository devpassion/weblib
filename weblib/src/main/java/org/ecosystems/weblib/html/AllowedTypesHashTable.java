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

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * Table d'attributs autorisés pour un tag HTML.
 */
public class AllowedTypesHashTable
{

    // private Hashtable<String, Class<?>> table = new Hashtable<String, Class<?>>();
    private final Hashtable<String, AttributeType<?>> typesTable = new Hashtable<String, AttributeType<?>>();

    private final Hashtable<String, Object> defaultsTable = new Hashtable<String, Object>();

    private final HashSet<String> requireds = new HashSet<String>();

    /**
     * Constructeur par défaut.
     */
    AllowedTypesHashTable()
    {
        super();
    }

    /**
     * Ajoute un attribut autorisé non requis.
     * 
     * @param name nom de l'attribut autorisé à ajouter
     * @param attributeType type d'attribut à ajouter à la hashTable
     */
    public void add(final String name, final AttributeType<?> attributeType)
    {
        this.add( name, attributeType, false, null );
    }

    /**
     * Ajoute un attribut autorisé.
     * 
     * @param name nom de l'attribut autorisé à ajouter
     * @param attributeType type d'attribut à ajouter à la hashTable
     * @param required indique si l'attribut est requis
     */
    public void add(final String name, final AttributeType<?> attributeType, final boolean required)
    {
        this.add( name, attributeType, required, null );
    }

    /**
     * Ajoute un attribut autorisé avec une valeur par défaut.
     * 
     * @param <T> Type infféré d'attribut
     * @param name nom de l'attribut autorisé à ajouter
     * @param attributeType type d'attribut à ajouter à la hashTable
     * @param required indique si l'attribut est requis
     * @param defaultValue Valeur par défaut
     */
    public <T> void add(final String name, final AttributeType<T> attributeType, final boolean required, final T defaultValue)
    {
        this.typesTable.put( name, attributeType );

        if (required)
        {
            this.requireds.add( name );
        }
        if (defaultValue != null)
        {
            this.defaultsTable.put( name, defaultValue );
        }
    }

    /**
     * Récupère une valeur par défaut.
     * 
     * @param name nom de l'attribut
     * @return valeur par défaut ou null si aucune valeur par défaut n'est définie
     */
    Object getDefaultValue(final String name)
    {
        return this.defaultsTable.get( name );
    }

    /**
     * Récupère le type d'attribut d'un attribut de la table.
     * 
     * @param name nom d'attribut à récupérer
     * @return attribut récupéré, ou null si il est introuvable
     */
    AttributeType<?> get(final String name)
    {
        return this.typesTable.get( name );
    }

    /**
     * Indique si un attribut est autorisé pour la balise.
     * 
     * @param attribute attribut dont la validité doit être testée
     * @return vrai si le type d'attribut est autorisé, c'est à dire présent dans la HashTable avec le même nom et la
     *         même type
     */
    boolean isAllowed(final Attribute<?> attribute)
    {
        final AttributeType<?> attType = this.typesTable.get( attribute.getName() );
        if (attType == null)
        {
            return false;
        }
        if (!attType.getAttributeClazz().equals( attribute.getAttributeType().getAttributeClazz() ))
        {
            return false;
        }
        return true;
    }

    /**
     * Récupère un itérateur sur les éléments requis.
     * 
     * @return Itérateur sur les noms d'éléments requis
     */
    Iterator<String> requiredsIterator()
    {
        return this.requireds.iterator();
    }

    /**
     * Récupère un itérateur sur les valeurs par défaut possibles des attributs où une valeur par défaut est définie.
     * 
     * @return Itérateur sur des entrées contenant en clé les noms d'attributs et en valeur la valeur par défaut
     */
    Iterator<Entry<String, Object>> defaultsIterator()
    {
        return this.defaultsTable.entrySet().iterator();
    }
}
