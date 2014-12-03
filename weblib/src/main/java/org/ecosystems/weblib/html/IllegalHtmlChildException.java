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

/**
 * Exception levée lorsqu'un ajout de tag enfant est invalide.
 */
public class IllegalHtmlChildException extends Exception
{

    private static final long serialVersionUID = -6395306670632574519L;

    private final HtmlTag parent;

    private final HtmlTag children;

    /**
     * Crée une nouvelle exception à partir d'un tag et du tag invalide qui lui est ajouté.
     * 
     * @param parent tag parent
     * @param children tag enfant
     */
    public IllegalHtmlChildException(final HtmlTag parent, final HtmlTag children)
    {
        this.parent = parent;
        this.children = children;
    }

    /**
     * Récupère la tag auquel il est ajouté un tag invalide.
     * 
     * @return the parent
     */
    public HtmlTag getParent()
    {
        return this.parent;
    }

    /**
     * Récupère le tag qui ne peut pas être ajouté au tag parent.
     * 
     * @return the children
     */
    public HtmlTag getChildren()
    {
        return this.children;
    }

    @Override
    public String getMessage()
    {
        return "Ajout de tag enfant illégal : ".concat( StringTools.getStringOrNull( this.children ) ).concat( "to" )
                .concat( StringTools.getStringOrNull( this.parent ) );

    }

}
