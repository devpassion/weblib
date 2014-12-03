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
import org.ecosystems.weblib.log.LoggerAdapter;

/**
 * Tag affichant son corps si une variable est présente dans le contexte et correspond à une éventuelle valeur.
 */
public class ConditionalVariableTag extends BodyTagSupport
{

    private static final long serialVersionUID = -6787520564962644901L;

    private static final LoggerAdapter LOG_ADAPTER = new LoggerAdapter( ConditionalVariableTag.class );

    private String missingValue = null;

    private String name = null;

    private RequestPolicy policy;

    /**
     * Valeur nécessaire pour que le corps soit évalué. La comparaison se fait en chaîne de caractères.
     * 
     * @return valeur nécessaire, ou null si l'existence seule de la variable est nécessaire
     */
    public String getMissingValue()
    {
        return this.missingValue;
    }

    /**
     * Définit la valeur qui doit être trouvée pour afficher le tag.
     * 
     * @param missingValue the missingValue to set
     */
    public void setMissingValue(final String missingValue)
    {
        this.missingValue = missingValue;
    }

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
        LOG_ADAPTER.trace( "doStartTag" ); //$NON-NLS-1$

        final Object value = HTTPDataManager.getData( this.pageContext, this.name, this.policy );

        LOG_ADAPTER.trace( "value = ", value ); //$NON-NLS-1$

        if (value == null)
        {
            return Tag.SKIP_BODY;
        }

        LOG_ADAPTER.trace( "missingValue = ", this.missingValue ); //$NON-NLS-1$

        if (this.missingValue != null)
        {
            if (value.toString().equals( this.missingValue ))
            {
                LOG_ADAPTER.trace( "match, evel body" ); //$NON-NLS-1$
                return Tag.EVAL_BODY_INCLUDE;
            }
            LOG_ADAPTER.trace( "no match, skip body" ); //$NON-NLS-1$
            return Tag.SKIP_BODY;
        }
        LOG_ADAPTER.trace( "end, eval body" ); //$NON-NLS-1$
        return Tag.EVAL_BODY_INCLUDE;
    }
}
