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
package org.ecosystems.lib.sql;

import org.ecosystems.lib.sql.Sort.Order;

/**
 * Tri élémentaire d'un champ avec ordre (descendant ou ascendant).
 */
public class FieldSort implements SQLStringable
{
    /**
     * Nom du champ de tri.
     */
    private final String fieldName;

    /**
     * Ordre de tri.
     */
    private final Sort.Order order;

    /**
     * Crée un tri élémentaire à partir d'un nom de champ et d'un ordre.
     * 
     * @param fieldName nom du champ à trier
     * @param order ordre de tri
     */
    public FieldSort(final String fieldName, final Order order)
    {
        super();
        this.fieldName = fieldName;
        this.order = order;
    }

    /**
     * Récupère la nom de champ.
     * 
     * @return the field
     */
    public String getFieldName()
    {
        return this.fieldName;
    }

    /**
     * Récupère l'ordre de tri.
     * 
     * @return the order
     */
    public Sort.Order getOrder()
    {
        return this.order;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.order == null) ? 0 : this.order.hashCode());
        result = prime * result + ((this.fieldName == null) ? 0 : this.fieldName.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final FieldSort other = (FieldSort) obj;
        if (this.order == null)
        {
            if (other.order != null)
                return false;
        }
        else if (!this.order.equals( other.order ))
            return false;
        if (this.fieldName == null)
        {
            if (other.fieldName != null)
                return false;
        }
        else if (!this.fieldName.equals( other.fieldName ))
            return false;
        return true;
    }

    @Override
    public String toSQLString()
    {
        return this.fieldName + " " + this.order.toString();
    }

    @Override
    public String toString()
    {
        return this.toSQLString();
    }
}
