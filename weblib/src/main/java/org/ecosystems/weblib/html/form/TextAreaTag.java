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

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.commons.fileupload.FileUploadException;
import org.ecosystems.lib.tools.StringTools;
import org.ecosystems.weblib.html.AbstractHtmlTag;
import org.ecosystems.weblib.html.AllowedTypesHashTable;
import org.ecosystems.weblib.html.AttributeType;
import org.ecosystems.weblib.html.FormInputHtmlTag;
import org.ecosystems.weblib.html.HtmlText;
import org.ecosystems.weblib.html.InvalidAttributeTypeException;
import org.ecosystems.weblib.html.OneValueAttribute;
import org.ecosystems.weblib.html.TagDispositionPolicy;
import org.ecosystems.weblib.html.UnknowAttributeException;
import org.ecosystems.weblib.html.form.config.RequestValueProviderFactory;
import org.ecosystems.weblib.log.LoggerAdapter;
import org.ecosystems.weblib.test.TestFlags;

/**
 * Tag "textarea".
 */
public class TextAreaTag extends AbstractHtmlTag implements FormInputHtmlTag
{

    private static final long serialVersionUID = -54120950170107959L;

    private final FormContextUserTagAdapter formContextAdapter;

    private boolean persistent = true;

    private static final LoggerAdapter LOGGER_ADAPTER = new LoggerAdapter( TextAreaTag.class );

    /**
     * Contructeur par défaut.
     */
    public TextAreaTag()
    {
        super( "textarea", TagDispositionPolicy.ENCLOSE_BODY ); //$NON-NLS-1$
        this.formContextAdapter = new FormContextUserTagAdapter( this );
    }

    @Override
    protected void setAllowedsAttributes(final AllowedTypesHashTable alloweds)
    {
        super.setAllowedsAttributes( alloweds );
        alloweds.add( "name", AttributeType.getStringAttributeType(), true ); //$NON-NLS-1$
        alloweds.add( "rows", AttributeType.getIntegerAttributeType() ); //$NON-NLS-1$
        alloweds.add( "cols", AttributeType.getIntegerAttributeType() ); //$NON-NLS-1$
        alloweds.add( "required", AttributeType.getStringAttributeType() ); //$NON-NLS-1$
        alloweds.add( "placeholder", AttributeType.getStringAttributeType() ); //$NON-NLS-1$
    }

    /**
     * Indique si le tag est persistant.
     * 
     * @return the persistent
     */
    public boolean isPersistent()
    {
        return this.persistent;
    }

    /**
     * Définit la parsistance.
     * 
     * @param persistent the persistent to set
     */
    public void setPersistent(final boolean persistent)
    {
        this.persistent = persistent;
    }

    @Override
    public int doStartTag() throws JspException
    {
        this.formContextAdapter.setFormParent();

        String valueName;
        try
        {
            valueName = this.getAttribute( "name" ).getValue().toString(); //$NON-NLS-1$
            this.getLogger().trace( StringTools.concat( "valueName = ", valueName ) ); //$NON-NLS-1$
            // final Object value = this.pageContext.getRequest().getParameter( valueName );
            final Object value = RequestValueProviderFactory.getRVPProvider( (HttpServletRequest) this.pageContext.getRequest() )
                    .getParameter( valueName );

            this.getLogger().trace( StringTools.concat( "value = ", //$NON-NLS-1$
                    StringTools.getStringOrNull( value ) ) );

            // TODO : code recopié, présent aussi dans InputTag
            try
            {
                if (this.formContextAdapter.getFormParent().getFieldContext( this.getAttribute( "name" ).getValue().toString() )
                        .isRequired())
                {
                    if (!TestFlags.isTestEnabled())
                        this.setAttribute( new OneValueAttribute( "required" ) );
                    else
                        this.removeAttribute( "required" );
                }
                else if (this.hasAttribute( "required" )) //$NON-NLS-1$
                {
                    throw new JspException( "L'attribut required n'est pas supporté par html5" ); //$NON-NLS-1$
                }
            }
            catch (final UnknowAttributeException e)
            {
                throw new JspException( e );
            }
            catch (final NoSuchFieldException e)
            {
                throw new JspException( e );
            }
            catch (final InvalidAttributeTypeException e)
            {
                throw new JspException( e );
            }

            if (value != null && this.persistent)
            {
                LOGGER_ADAPTER.debug( "Ajout du texte \"", value, "\"" );
                this.getChildren().clear();
                this.addChildren( new HtmlText( value.toString() ) );
            }
        }
        catch (final UnknowAttributeException | FileUploadException | IOException e)
        {
            throw new JspException( e );
        }

        return super.doStartTag();
    }
}
