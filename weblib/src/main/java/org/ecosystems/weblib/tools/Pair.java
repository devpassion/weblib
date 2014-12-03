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
package org.ecosystems.weblib.tools;

/**
 * Paire de données de même type.
 * 
 * @param <T> Type des donénes de la paire
 */
public class Pair<T>
{
    private final T first;

    private final T second;

    /**
     * Nouvelle paire.
     * 
     * @param first première donnée
     * @param second seconde données
     */
    public Pair(final T first, final T second)
    {
        super();
        this.first = first;
        this.second = second;
    }

    /**
     * Récupère le premier élément de la paire.
     * 
     * @return the first
     */
    public T getFirst()
    {
        return this.first;
    }

    /**
     * Récupère le second élément de la paire.
     * 
     * @return the second
     */
    public T getSecond()
    {
        return this.second;
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
        result = prime * result + ((this.first == null) ? 0 : this.first.hashCode());
        result = prime * result + ((this.second == null) ? 0 : this.second.hashCode());
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
        final Pair<?> other = (Pair<?>) obj;
        if (this.first == null)
        {
            if (other.first != null)
                return false;
        }
        else if (!this.first.equals( other.first ))
            return false;
        if (this.second == null)
        {
            if (other.second != null)
                return false;
        }
        else if (!this.second.equals( other.second ))
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( "Pair [first=" );
        builder.append( first );
        builder.append( ", second=" );
        builder.append( second );
        builder.append( "]" );
        return builder.toString();
    }

}
