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
 * Exception levée lorsqu'un attribut est requis.
 */
public class MissingAttributeException extends AttributeException
{

    private static final long serialVersionUID = -4773248386406441690L;

    /**
     * Crée une exception à partir d'un attribut requis et du tag nécéssitant cet attribut.
     * 
     * @param attribute attribut requis manquant
     * @param tag tag devant contenir l'attribut
     */
    public MissingAttributeException(final Attribute<?> attribute, final HtmlTag tag)
    {
        super( attribute, tag );
    }

    /**
     * Crée une exception à partir d'un nom d'attribut requis et du tag nécéssitant cet attribut.
     * 
     * @param attributeName Nom de l'attribut requis manquant
     * @param tag tag devant contenir l'attribut
     */
    public MissingAttributeException(final String attributeName, final HtmlTag tag)
    {
        super( attributeName, tag );
    }

    @Override
    protected String getBeginMsg()
    {
        return "Attribut requis mais manquant : "; //$NON-NLS-1$
    }

}
