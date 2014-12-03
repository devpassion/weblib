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
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import org.ecosystems.weblib.html.form.config.BadFormBeanClassException;
import org.ecosystems.weblib.html.form.config.FormContext;
import org.ecosystems.weblib.html.form.config.FormContextFactoryImpl;
import org.ecosystems.weblib.html.form.config.InitFormException;
import org.ecosystems.weblib.log.LoggerAdapter;

/**
 * Tag affichant son corps si le formulaire est posté.
 */
public class ConditionalFormTag extends BodyTagSupport
{

    private static final long serialVersionUID = 7828347569273455551L;

    private static final LoggerAdapter LOG_ADAPTER = new LoggerAdapter( ConditionalInputTag.class );

    private FormTag formParent;

    private String formName;

    private boolean showOnInvalid = false;

    /**
     * Constructeur par défaut.
     */
    public ConditionalFormTag()
    {
        super();
    }

    /**
     * Définit le nom du formulaire associé au tag.
     * 
     * @param formName the formName to set
     */
    public void setFormName(final String formName)
    {
        this.formName = formName;
    }

    /**
     * Récupère le nom du formulaire associé au tag.
     * 
     * @return the formName
     */
    public String getFormName()
    {
        return this.formName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
     */
    @Override
    public int doStartTag() throws JspException
    {
        if (this.formParent == null)
        {
            final Tag parent = this.getParent();

            if (parent == null || !(parent instanceof FormTag))
            {
                if (this.formName != null)
                {
                    try
                    {
                        final FormContext<?> formContext = FormContextFactoryImpl.getProvider().getFormContext( this.formName,
                                (HttpServletRequest) this.pageContext.getRequest() );
                        if (formContext.isValid() && formContext.isPosted())
                        {
                            return Tag.EVAL_BODY_INCLUDE;
                        }
                        return Tag.SKIP_BODY;
                    }

                    catch (InitFormException | BadFormBeanClassException e)
                    {
                        throw new JspException( e );
                    }

                }
                throw LOG_ADAPTER.logAndReturnJspException( "Le tag input n'est pas contenu " + //$NON-NLS-1$
                        "dans un formulaire et aucun nom de formulaire n'est spécifié" ); //$NON-NLS-1$
            }
            this.formParent = (FormTag) parent;
        }

        if (!this.formParent.getFormContext().isPosted())
        {
            return Tag.SKIP_BODY;
        }

        if (this.formParent.isValid())
        {
            return (this.isShowOnInvalid()) ? Tag.SKIP_BODY : Tag.EVAL_BODY_INCLUDE;
        }
        return (this.isShowOnInvalid()) ? Tag.EVAL_BODY_INCLUDE : Tag.SKIP_BODY;
    }

    /**
     * Indique si le contenu du tag est affiché quand le formulaire est invalide (faux par défaut).
     * 
     * @return vrai si le contenu du tag est affiché quand le formulaire est invalide
     */
    public boolean isShowOnInvalid()
    {
        return showOnInvalid;
    }

    /**
     * @param showOnInvalid the showOnInvalid to set
     */
    public void setShowOnInvalid(final boolean showOnInvalid)
    {
        this.showOnInvalid = showOnInvalid;
    }

}
