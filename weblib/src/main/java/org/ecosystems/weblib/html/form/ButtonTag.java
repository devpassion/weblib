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
package org.ecosystems.weblib.html.form;

import javax.servlet.jsp.JspException;

import org.ecosystems.weblib.html.AbstractHtmlTag;
import org.ecosystems.weblib.html.AllowedTypesHashTable;
import org.ecosystems.weblib.html.Attribute;
import org.ecosystems.weblib.html.AttributeType;
import org.ecosystems.weblib.html.InvalidAttributeTypeException;
import org.ecosystems.weblib.html.TagDispositionPolicy;
import org.ecosystems.weblib.html.UnknowAttributeException;

/**
 * Tag button.
 */
public class ButtonTag extends AbstractHtmlTag
{

    private static final long serialVersionUID = -1865422515601376448L;

    /**
     * Constructeur par défaut.
     */
    public ButtonTag()
    {
        super( "button", TagDispositionPolicy.ENCLOSE_BODY ); //$NON-NLS-1$
    }

    @Override
    protected void setAllowedsAttributes(final AllowedTypesHashTable alloweds)
    {
        super.setAllowedsAttributes( alloweds );
        alloweds.add( "name", AttributeType.getStringAttributeType() ); //$NON-NLS-1$
        alloweds.add( "value", AttributeType.getStringAttributeType() ); //$NON-NLS-1$
        alloweds.add( "type", AttributeType.getButtonTypeAttributeType(), true, ButtonType.SUBMIT ); //$NON-NLS-1$
        alloweds.add( "src", AttributeType.getStringAttributeType() ); //$NON-NLS-1$
    }

    /**
     * Récupère le type de bouton.
     * 
     * @return Type de bouton
     * @throws JspException Levée si l'attribut type est inconnu ou invalide
     */
    @SuppressWarnings("unchecked")
    protected ButtonType getTypeAttribute() throws JspException
    {
        Attribute<?> a;
        ButtonType type;
        try
        {
            a = this.getAttribute( "type" ); //$NON-NLS-1$
        }
        catch (final UnknowAttributeException e1)
        {
            throw this.getLogger().logAndReturnJspException( e1 );
        }

        try
        {
            // Ici si une erreur de cast apparaît, on la logge et la gère
            // Ceci justifie le suppressWarning
            type = ((Attribute<ButtonType>) a).getValue();
        }
        catch (final ClassCastException e)
        {
            throw this.getLogger().logAndReturnJspException( new InvalidAttributeTypeException( a, this, e ) );
        }
        return type;
    }

    /**
     * Types de boutons poussoirs.
     */
    public enum ButtonType
    {
        /**
         * Bouton poussoir.
         */
        BUTTON,

        /**
         * Bouton de soumission.
         */
        SUBMIT,

        /**
         * Bouton de réinitialisation.
         */
        RESET;

        @Override
        public String toString()
        {
            return super.toString().toLowerCase();
        }
    }
}
