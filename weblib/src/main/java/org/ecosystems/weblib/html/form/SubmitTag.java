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
import javax.servlet.jsp.tagext.Tag;

import org.ecosystems.weblib.html.AttributeType;
import org.ecosystems.weblib.html.FormInputHtmlTag;
import org.ecosystems.weblib.html.InvalidAttributeTypeException;
import org.ecosystems.weblib.html.SimpleAttribute;
import org.ecosystems.weblib.html.form.config.FormContext;

/**
 * Bouton de soumission de formulaire.
 * 
 * Le bouton doit être placé dans un formulaire HTML. Il détecte automatiquement ce qu'il doit poster pour indiquer quel formulaire est
 * soumit.
 */
public class SubmitTag extends ButtonTag implements FormInputHtmlTag
{

    private static final long serialVersionUID = 7810995288431629554L;

    private final FormContextUserTagAdapter formContextAdapter;

    /**
     * Crée un nouveau bouton de soumission.
     */
    public SubmitTag()
    {
        super();
        this.formContextAdapter = new FormContextUserTagAdapter( this );
    }

    @Override
    public int doStartTag() throws JspException
    {
        this.formContextAdapter.setFormParent();

        try
        {
            this.setAttribute( new SimpleAttribute<String>( AttributeType.getStringAttributeType(), "value", this.formContextAdapter
                    .getFormParent().getPostedKey() ) );
            this.setAttribute( new SimpleAttribute<String>( AttributeType.getStringAttributeType(), "name", FormContext.POSTED_KEY_NAME ) );
        }
        catch (InvalidAttributeTypeException e)
        {
            throw this.getLogger().logAndReturnJspException( e );
        }
        super.doStartTag();

        final ButtonType type = this.getTypeAttribute();

        if (type == ButtonType.RESET || type == ButtonType.BUTTON)
        {
            throw new JspException( "Un bouton reset ne peut pas être un bouton de soumission ni un bouton poussoir" );
        }

        return Tag.EVAL_BODY_INCLUDE;
    }

}
