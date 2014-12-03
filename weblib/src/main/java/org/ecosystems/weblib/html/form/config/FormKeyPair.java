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
/**
 * 
 */
package org.ecosystems.weblib.html.form.config;

/**
 * Paire classe/nom identifiant un formulaire.
 * 
 * @param <T> Type de bean de formulaire
 */
class FormKeyPair<T>
{
    private final Class<T> clazz;

    private final String name;

    /**
     * Nouvelle paire.
     * 
     * @param clazz Classe de bean du formulaire
     * @param name nom du formulaire
     */
    public FormKeyPair(final Class<T> clazz, final String name)
    {
        super();
        this.clazz = clazz;
        this.name = name;
    }

    /**
     * Classe de bean du formulaire.
     * 
     * @return the clazz
     */
    public Class<T> getClazz()
    {
        return this.clazz;
    }

    /**
     * Nom du formulaire.
     * 
     * @return the name
     */
    public String getName()
    {
        return this.name;
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
        result = prime * result + ((this.clazz == null) ? 0 : this.clazz.toString().hashCode());
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
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
        final FormKeyPair<?> other = (FormKeyPair<?>) obj;
        if (this.clazz == null)
        {
            if (other.clazz != null)
                return false;
        }
        else if (!this.clazz.equals( other.clazz ))
            return false;
        if (this.name == null)
        {
            if (other.name != null)
                return false;
        }
        else if (!this.name.equals( other.name ))
            return false;
        return true;
    }

}
