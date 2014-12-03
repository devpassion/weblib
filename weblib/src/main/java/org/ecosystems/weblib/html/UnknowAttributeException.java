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
 * Exception levée lorsqu'un attribut non défini est demandé.
 */
public class UnknowAttributeException extends AttributeException
{

    private static final long serialVersionUID = 8640558594329030566L;

    /**
     * Crée une exception à partir d'un attribut non définit et d'un tag nécéssitant cet attribut.
     * 
     * @param attribute attribut inconnu
     * @param tag tag ou l'attribut manquant a tenté d'être ajouté
     */
    public UnknowAttributeException(final Attribute<?> attribute, final HtmlTag tag)
    {
        super( attribute, tag );
    }

    /**
     * Crée une exception à partir d'un nom d'attribut non définit et d'un tag nécéssitant cet attribut.
     * 
     * @param attributeName Nom de l'attribut inconnu
     * @param tag tag ou l'attribut manquant a tenté d'être ajouté
     */
    public UnknowAttributeException(final String attributeName, final HtmlTag tag)
    {
        super( attributeName, tag );
    }

    @Override
    protected String getBeginMsg()
    {
        return "Attribut introuvable : "; //$NON-NLS-1$
    }
}
