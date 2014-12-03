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
import org.ecosystems.weblib.html.AttributeType;
import org.ecosystems.weblib.html.FormInputHtmlTag;
import org.ecosystems.weblib.html.TagDispositionPolicy;

/**
 * Tag HTML select.
 */
public class SelectTag extends AbstractHtmlTag implements FormInputHtmlTag
{

    // [start] Members

    private static final long serialVersionUID = -1270484895548756469L;

    private final FormContextUserTagAdapter formContextAdapter;

    // [end]

    // [start] Constructors

    /**
     * Constructeur par défaut.
     */
    public SelectTag()
    {
        super( "select", TagDispositionPolicy.ENCLOSE_BODY ); //$NON-NLS-1$
        this.formContextAdapter = new FormContextUserTagAdapter( this );
    }

    // [end]

    // [start] Getters and setters

    // [end]

    // [start] Public methods

    @Override
    public int doStartTag() throws JspException
    {
        this.formContextAdapter.setFormParent();
        return super.doStartTag();
    }

    /**
     * Récupère l'adapteur fournissant le contexte.
     * 
     * @return Adapteur de contexte de formulaire
     */
    FormContextUserTagAdapter getFormContextAdapter()
    {
        return this.formContextAdapter;
    }

    // [end]

    // [start] Protected methods
    @Override
    protected void setAllowedsAttributes(final AllowedTypesHashTable alloweds)
    {
        super.setAllowedsAttributes( alloweds );
        alloweds.add( "name", AttributeType.getStringAttributeType() ); //$NON-NLS-1$
        alloweds.add( "size", AttributeType.getIntegerAttributeType() ); //$NON-NLS-1$
        alloweds.add( "multiple", AttributeType.getStringAttributeType() ); //$NON-NLS-1$

    }
    // [end]

    // [start] Private methods

    // [end]

}
