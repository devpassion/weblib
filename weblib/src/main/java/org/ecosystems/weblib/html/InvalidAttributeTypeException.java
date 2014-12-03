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

/**
 * Exception levée lorsqu'un type d'attribut est invalide.
 */
public class InvalidAttributeTypeException extends AttributeException
{

    private static final long serialVersionUID = -597664642129874659L;

    /**
     * Crée une nouvelle exception à partir d'un attribut invalide et du tag auquel on a tenté de l'ajouter.
     * 
     * @param attribute Attribut invalide
     * @param tag tag devant contenir l'attribut
     */
    public InvalidAttributeTypeException(final Attribute<?> attribute, final HtmlTag tag)
    {
        super( attribute, tag );
    }

    /**
     * Crée une nouvelle exception à partir d'un nom d'attribut invalide et du tag auquel on a tenté de l'ajouter.
     * 
     * @param attributeName Nom de l'attribut invalide
     * @param tag tag devant contenir l'attribut
     */
    public InvalidAttributeTypeException(final String attributeName, final HtmlTag tag)
    {
        super( attributeName, tag );
    }

    /**
     * Crée une nouvelle exception à partir d'un attribut invalide et du tag auquel on a tenté de l'ajouter et d'une cause.
     * 
     * @param attribute Attribut invalide
     * @param tag tag devant contenir l'attribut
     * @param t @see {@link Exception#getCause()}
     */
    public InvalidAttributeTypeException(final Attribute<?> attribute, final HtmlTag tag, final Throwable t)
    {
        super( attribute, tag, t );
    }

    /**
     * Crée une nouvelle exception à partir d'un nom d'attribut invalide, du tag auquel on a tenté de l'ajouter et d'une cause.
     * 
     * @param attributeName Nom de l'attribut invalide
     * @param tag tag devant contenir l'attribut
     * @param t @see {@link Exception#getCause()}
     */
    public InvalidAttributeTypeException(final String attributeName, final HtmlTag tag, final Throwable t)
    {
        super( attributeName, tag, t );
    }

    @Override
    protected String getBeginMsg()
    {
        return "Attribut invalide : "; //$NON-NLS-1$
    }

}
