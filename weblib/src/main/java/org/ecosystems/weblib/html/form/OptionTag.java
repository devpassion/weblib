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

import org.ecosystems.weblib.html.AbstractHtmlTag;
import org.ecosystems.weblib.html.AllowedTypesHashTable;
import org.ecosystems.weblib.html.AttributeType;
import org.ecosystems.weblib.html.InvalidAttributeTypeException;
import org.ecosystems.weblib.html.OneValueAttribute;
import org.ecosystems.weblib.html.TagDispositionPolicy;
import org.ecosystems.weblib.html.UnknowAttributeException;
import org.ecosystems.weblib.html.form.config.FieldContext;

/**
 * Tag HTML option.
 */
public class OptionTag extends AbstractHtmlTag
{

    // [start] Members

    private static final long serialVersionUID = 8696030198644083827L;

    private SelectTag parent;

    // [end]

    // [start] Constructors

    /**
     * Constructeur par défaut.
     */
    public OptionTag()
    {
        super( "option", TagDispositionPolicy.ENCLOSE_BODY ); //$NON-NLS-1$
    }

    // [end]

    // [start] Getters and setters

    // [end]

    // [start] Public methods

    @Override
    public int doStartTag() throws JspException
    {
        final Tag tParent = this.getParent();
        if (!(tParent instanceof SelectTag))
        {
            throw new JspException( "Le tag option n'est pas contenu dans un tag Select" ); //$NON-NLS-1$
        }

        this.parent = (SelectTag) tParent;

        try
        {
            final String value = this.getAttribute( "value" ).getValue().toString(); //$NON-NLS-1$

            final FieldContext<?, ?> fc = this.parent.getFormContextAdapter().getFieldContext();
            if (fc.getDisplayableValue() != null && fc.getDisplayableValue().equals( value ))
            {
                this.setAttribute( new OneValueAttribute( "selected" ) ); //$NON-NLS-1$
            }
        }
        catch (NoSuchFieldException e)
        {
            throw new JspException( e );
        }
        catch (UnknowAttributeException e)
        {
            throw new JspException( e );
        }
        catch (InvalidAttributeTypeException e)
        {
            throw new JspException( e );
        }
        catch (InvalidBoxedTypeException e)
        {
            throw new JspException( e );
        }

        return super.doStartTag();
    }

    @Override
    public int doAfterBody() throws JspException
    {
        // TODO Auto-generated method stub
        return super.doAfterBody();
    }

    // [end]

    // [start] Protected methods

    @Override
    protected void setAllowedsAttributes(final AllowedTypesHashTable alloweds)
    {
        super.setAllowedsAttributes( alloweds );
        alloweds.add( "value", AttributeType.getStringAttributeType() ); //$NON-NLS-1$
        alloweds.add( "label", AttributeType.getStringAttributeType() ); //$NON-NLS-1$
        alloweds.add( "selected", AttributeType.getStringAttributeType() ); //$NON-NLS-1$
    }

    // [end]

    // [start] Private methods

    // [end]

}
