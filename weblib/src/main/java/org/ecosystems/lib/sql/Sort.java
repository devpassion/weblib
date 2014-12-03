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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Tri de champs.
 */
public class Sort implements SQLStringable
{
    /**
     * Tris élémentaires appliqués à ce tri, dans l'ordre d'application.
     */
    private List<FieldSort> sorts = new ArrayList<FieldSort>();

    /**
     * Constructeur par défaut.
     */
    public Sort()
    {
        super();
    }

    /**
     * Crée un tri de champ à partir d'une liste de tris élémentaires.
     * 
     * @param sorts tris élémentaires
     */
    public Sort(final List<FieldSort> sorts)
    {
        super();
        this.sorts = sorts;
    }

    /**
     * Crée un tri de champ à partir d'un tableau de tris élémentaires.
     * 
     * @param sorts tris élémentaires
     */
    public Sort(final FieldSort[] sorts)
    {
        super();
        this.sorts = Arrays.asList( sorts );
    }

    /**
     * Crée un nouveau tri sur un champ ascendant.
     * 
     * @param fieldName nom du champ
     */
    public Sort(final String fieldName)
    {
        this( new FieldSort( fieldName, Sort.Order.ASC ) );
    }

    /**
     * Crée un nouveau tri sur un champ.
     * 
     * @param fieldName nom du champ
     * @param order ordre de tri
     */
    public Sort(final String fieldName, final Sort.Order order)
    {
        this( new FieldSort( fieldName, order ) );
    }

    /**
     * Crée un nouveau tri sur un champ.
     * 
     * @param fieldSort champ
     */
    public Sort(final FieldSort fieldSort)
    {
        super();
        this.addSortField( fieldSort );
    }

    /**
     * Ajoute un champ au tri.
     * 
     * @param fieldSort tri élémentaire
     */
    public void addSortField(final FieldSort fieldSort)
    {
        this.sorts.add( fieldSort );
    }

    @Override
    public String toSQLString()
    {
        final StringBuilder sb = new StringBuilder();

        int i = 0;
        for (FieldSort fs : this.sorts)
        {
            i++;
            sb.append( fs.toSQLString() );
            if (i != this.sorts.size())
            {
                sb.append( ", " );
            }
        }

        return sb.toString();
    }

    @Override
    public String toString()
    {
        return this.toSQLString();
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
        result = prime * result + ((this.sorts == null) ? 0 : this.sorts.hashCode());
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
        final Sort other = (Sort) obj;
        if (this.sorts == null)
        {
            if (other.sorts != null)
                return false;
        }
        else if (!this.sorts.equals( other.sorts ))
            return false;
        return true;
    }

    /**
     * Ajoute un champ au tri.
     * 
     * @param fieldName nom du champ
     * @param order ordre de tri
     */
    public void addSortField(final String fieldName, final Sort.Order order)
    {
        this.sorts.add( new FieldSort( fieldName, order ) );
    }

    /**
     * Ordre de tri.
     */
    public enum Order
    {
        /**
         * Ordre descendant.
         */
        DESC,

        /**
         * Ordre ascendent.
         */
        ASC
    }
}
