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

import java.util.List;

import javax.servlet.jsp.tagext.DynamicAttributes;

/**
 * Interface des tags HTML.
 */
public interface HtmlTag extends DynamicAttributes
{

    /**
     * Récupère le nom de la balise.
     * 
     * @return nom de la balise
     */
    String getTagName();

    /**
     * Définit un attribut à la balise.
     * 
     * @param attribute Attribut à définir
     * @throws InvalidAttributeTypeException Levée si l'attribut n'est pas supporté par la balise
     */
    void setAttribute(Attribute<?> attribute) throws InvalidAttributeTypeException;

    /**
     * Supprime un attribut (Aucune erreur si l'attribut n'existe pas).
     * Après l'opération, l'attribut n'est plus rendu.
     * 
     * @param name nom de l'attribut
     */
    void removeAttribute(String name);

    /**
     * Récupère un attribut de la balise.
     * 
     * @param name nom de l'attribut à récupérer
     * @return attribut
     * @throws UnknowAttributeException levée si l'attribut est inexistant
     */
    Attribute<?> getAttribute(String name) throws UnknowAttributeException;

    /**
     * Indique si un attribut est présent.
     * 
     * @param name nom de l'attribut à rechercher
     * @return vrai si l'attribut est présent
     */
    boolean hasAttribute(String name);

    /**
     * Récupère la balise de début de tag.
     * 
     * @return Balise de début du tag
     */
    String getBeginTag();

    /**
     * Récupère la balise de fin de tag.
     * 
     * @return Tag de fermeture
     */
    String getEndTag();

    /**
     * Ajoute un tag enfant.
     * 
     * @param children tag enfant à ajouter
     * @throws IllegalHtmlChildException levée si l'ajout est illégal
     */
    void addChildren(HtmlTag children) throws IllegalHtmlChildException;

    /**
     * Récupère l'ensemble des tags enfant.
     * 
     * @return List contenant l'ensemble des tags enfants, ou la liste nulle si le tag ne possède pas d'enfants
     */
    List<HtmlTag> getChildren();
}
