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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.ecosystems.weblib.html.AbstractHtmlTag;
import org.ecosystems.weblib.html.AllowedTypesHashTable;
import org.ecosystems.weblib.html.AttributeType;
import org.ecosystems.weblib.html.InvalidAttributeTypeException;
import org.ecosystems.weblib.html.SimpleAttribute;
import org.ecosystems.weblib.html.TagDispositionPolicy;
import org.ecosystems.weblib.html.form.config.BadFormBeanClassException;
import org.ecosystems.weblib.html.form.config.FieldContext;
import org.ecosystems.weblib.html.form.config.FormContext;
import org.ecosystems.weblib.html.form.config.FormContextFactoryImpl;
import org.ecosystems.weblib.html.form.config.InitFormException;
import org.ecosystems.weblib.log.LoggerAdapter;

/**
 * Tag form.
 */
public class FormTag extends AbstractHtmlTag
{
    private static final LoggerAdapter LOGGER_ADAPTER = new LoggerAdapter( FormTag.class );

    private String postedkey;

    private FormContext<?> formContext;

    /**
     * ID de série.
     */
    private static final long serialVersionUID = -4033919486763167280L;

    /**
     * Crée un nouveau tag de formulaire.
     */
    public FormTag()
    {
        super( "form", TagDispositionPolicy.ENCLOSE_BODY ); //$NON-NLS-1$
    }

    /**
     * Définit le contexte de formulaire.
     * 
     * @throws JspException Levée si un problème empêche la récupération du contexte de formulaire
     */
    private void setFormContext() throws JspException
    {

        try
        {
            this.formContext = FormContextFactoryImpl.getProvider().getFormContext( this.postedkey,
                    (HttpServletRequest) this.pageContext.getRequest() );
        }
        catch (InitFormException | BadFormBeanClassException e)
        {
            throw new JspException( e );
        }

    }

    /**
     * @return the formContext
     */
    public FormContext<?> getFormContext()
    {
        return formContext;
    }

    /**
     * Définit la valeur passée en POST à la clé {@link FormContext#POSTED_KEY_NAME} pour indiquer la soumission du formulaire.
     * 
     * @param postedKey the postedkey to set.
     */
    public void setPostedKey(final String postedKey)
    {
        this.postedkey = postedKey;
    }

    /**
     * Récupère la valeur passée en POST à la clé {@link FormContext#POSTED_KEY_NAME} pour indiquer la soumission du formulaire.
     * 
     * @return the postedkey
     */
    public String getPostedKey()
    {
        return this.postedkey;
    }

    @Override
    protected void setAllowedsAttributes(final AllowedTypesHashTable alloweds)
    {
        super.setAllowedsAttributes( alloweds );
        alloweds.add( "name", AttributeType.getStringAttributeType() );
        alloweds.add( "onsubmit", AttributeType.getStringAttributeType() );
        alloweds.add( "action", AttributeType.getStringAttributeType(), true, "#" );
        alloweds.add( "method", AttributeType.getStringAttributeType(), true, "post" );
        alloweds.add( "enctype", AttributeType.getStringAttributeType(), true, "application/x-www-form-urlencoded" );

    }

    /**
     * Récupère le contexte de champ.
     * 
     * @param name nom du champ
     * @return Contexte de champ
     * @throws NoSuchFieldException Levée si le champ n'est pas présent dans la configuration
     */
    public FieldContext<?, ?> getFieldContext(final String name) throws NoSuchFieldException
    {
        final FieldContext<?, ?> ret = this.formContext.getFieldContexts().get( name );
        LOGGER_ADAPTER.trace( "getFormContext(", name, ") : (postedKey = ", this.postedkey, ") return '", ret, "'" );
        if (ret == null)
        {
            throw new NoSuchFieldException( this.formContext.getFormName(), name );
        }
        return ret;
    }

    /**
     * Indique si le formulaire est valide.
     * 
     * @return vrai si le formulaire est valide
     */
    public boolean isValid()
    {
        return this.formContext.isValid();
    }

    /**
     * Indique si le formulaire supporte la persistance de session.
     * 
     * @return vrai si la persistence de session est supportée
     */
    public boolean isSessionPersistent()
    {
        return this.formContext.isSessionPersistent();
    }

    @Override
    public int doStartTag() throws JspException
    {
        setFormContext();
        try
        {
            this.setAttribute( new SimpleAttribute<String>( AttributeType.getStringAttributeType(), "name", this.formContext.getFormName() ) );
        }
        catch (final InvalidAttributeTypeException e)
        {
            throw new JspException( e );
        }
        return super.doStartTag();
    }
}
