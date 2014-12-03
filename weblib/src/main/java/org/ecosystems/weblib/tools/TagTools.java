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

import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTag;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.jsp.tagext.Tag;

/**
 * Outils de gestion des tags JSP.
 */
public final class TagTools
{
    private TagTools()
    {
    }

    /**
     * Récupère un tag de type précisé parmi les parents d'un tag.
     * 
     * @param tag Tag dont un parent doit être trouvé
     * @param parentClass Classe du tag parent à trouver
     * @return Le premier tag parent du type demandé ou null si il n'est pas trouvé
     */
    public static <U extends Tag> U getAncestor(final Tag tag, final Class<U> parentClass)
    {
        final Tag parent = tag.getParent();
        if (parent == null)
        {
            return null;
        }
        if (parentClass.isAssignableFrom( parent.getClass() ))
        {
            return (U) parent;
        }
        return getAncestor( parent, parentClass );
    }

    /**
     * Récupère un tag de type précisé parmi les parents d'un tag.
     * 
     * @param <U> Type du tag jsp parent
     * @param tag Tag dont un parent doit être trouvé
     * @param parentClass Classe du tag parent à trouver
     * @return Le premier tag parent du type demandé ou null si il n'est pas trouvé
     */
    @SuppressWarnings("unchecked")
    public static <U extends JspTag> U getAncestor(final SimpleTag tag, final Class<U> parentClass)
    {
        return (U) SimpleTagSupport.findAncestorWithClass( tag, parentClass );
    }

}
