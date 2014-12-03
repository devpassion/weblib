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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import org.ecosystems.weblib.html.form.HTTPDataManager;

/**
 * Tag affichant son corps si une variable est absente dans le contexte.
 */
public class MissingVariableTag extends BodyTagSupport
{

    private String name;

    private RequestPolicy policy;

    /**
     * 
     */
    private static final long serialVersionUID = 8992946894409748652L;

    /**
     * Récupère le nom de la variable qui doit avoir la valeur demandée.
     * 
     * @return the variableName
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Définit le nom de la variable qui doit avoir la valeur demandée.
     * 
     * @param name the variableName to set
     */
    public void setName(final String name)
    {
        this.name = name;
    }

    /**
     * Récupère la politique de recherche de la variable.
     * 
     * @return the policy
     */
    public RequestPolicy getPolicy()
    {
        return this.policy;
    }

    /**
     * Définit la politique de recherche de la variable.
     * 
     * @param policy the policy to set
     */
    public void setPolicy(final RequestPolicy policy)
    {
        this.policy = policy;
    }

    @Override
    public int doStartTag() throws JspException
    {
        if (!HTTPDataManager.hasData( this.pageContext, this.name, this.policy ))
        {
            return Tag.EVAL_BODY_INCLUDE;
        }
        //LOG_ADAPTER.trace( "end, eval body" ); //$NON-NLS-1$
        return Tag.EVAL_BODY_INCLUDE;
    }
}
