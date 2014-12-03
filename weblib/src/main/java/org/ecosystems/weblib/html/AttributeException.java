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
 * Exception relative à un attribut invalidedans un tag donné.
 */
public abstract class AttributeException extends Exception
{

    private static final long serialVersionUID = -8304941124327403126L;

    private Attribute<?> attribute;

    private String attributeName;

    private final HtmlTag tag;

    /**
     * Récupère le message qui précède le message automatique.
     * 
     * @return Message d'erreur précédent la spécification de l'attribut ou du tag
     */
    protected abstract String getBeginMsg();

    /**
     * Crée une exception indiquant qu'un attribut est invalide.
     * 
     * @param attribute attribut invalide
     * @param tag tag ou l'attribut invalide a tenté d'être ajouté
     */
    public AttributeException(final Attribute<?> attribute, final HtmlTag tag)
    {
        super();
        this.attribute = attribute;
        this.tag = tag;
    }

    /**
     * Crée une exception indiquant qu'un attribut est invalide.
     * 
     * @param attributeName nom de l'attribut invalide
     * @param tag tag ou l'attribut invalide a tenté d'être ajouté
     */
    public AttributeException(final String attributeName, final HtmlTag tag)
    {
        super();
        this.attributeName = attributeName;
        this.tag = tag;
    }

    /**
     * Crée une exception indiquant qu'un attribut est invalide.
     * 
     * @param attribute attribut invalide
     * @param tag tag ou l'attribut invalide a tenté d'être ajouté
     * @param t @see {@link Exception#getCause()}
     */
    public AttributeException(final Attribute<?> attribute, final HtmlTag tag, final Throwable t)
    {
        super( t );
        this.attribute = attribute;
        this.tag = tag;
    }

    /**
     * Crée une exception indiquant qu'un attribut est invalide.
     * 
     * @param attributeName nom de l'attribut invalide
     * @param tag tag ou l'attribut invalide a tenté d'être ajouté
     * @param t @see {@link Exception#getCause()}
     */
    public AttributeException(final String attributeName, final HtmlTag tag, final Throwable t)
    {
        super( t );
        this.attributeName = attributeName;
        this.tag = tag;
    }

    /**
     * Récupère l'attribut invalide.
     * 
     * @return the attribute
     */
    public Attribute<?> getAttribute()
    {
        return this.attribute;
    }

    /**
     * Récupère le nom de l'attribut invalide.
     * 
     * @return the attributeName
     */
    public String getAttributeName()
    {
        return this.attributeName;
    }

    @Override
    public String getMessage()
    {
        return this.getBeginMsg().concat( (this.attribute == null) ? this.getAttributeName() : this.attribute.getName() )
                .concat( " tag : " ).concat( StringTools.getStringOrNull( this.tag ) ); //$NON-NLS-1$
    }
}
