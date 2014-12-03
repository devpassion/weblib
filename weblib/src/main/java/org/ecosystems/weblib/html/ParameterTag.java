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

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringEscapeUtils;
import org.ecosystems.weblib.html.form.HTTPDataManager;
import org.ecosystems.weblib.log.LoggerAdapter;
import org.ecosystems.weblib.tools.ClassTools;
import org.ecosystems.weblib.tools.ValueTransformer;

/**
 * Tag affichant un paramètre de contexte.
 */
public class ParameterTag extends TagSupport
{

    private static final long serialVersionUID = -868446320961147492L;

    private static final LoggerAdapter LOG_ADAPTER = new LoggerAdapter( ParameterTag.class );

    private RequestPolicy policy;

    private String name;

    private ValueTransformer transformer;

    /**
     * Constructeur par défaut.
     */
    public ParameterTag()
    {
        super();
    }

    /**
     * Définit le nom du paramètre à afficher.
     * 
     * @param name the name to set
     */
    public void setName(final String name)
    {
        this.name = name;
    }

    /**
     * Récupère le nom du paramètre à afficher.
     * 
     * @return the name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Définit la politique de recherche du paramètre.
     * 
     * @param policy the policy to set
     */
    public void setPolicy(final RequestPolicy policy)
    {
        this.policy = policy;
    }

    /**
     * Récupère le transformer qui traitera le contenu de la variable avant affichage.
     * 
     * @return the transformer
     */
    public String getTransformer()
    {
        if (this.transformer == null)
        {
            return ""; //$NON-NLS-1$
        }
        return this.transformer.getClass().getName();
    }

    /**
     * Définit le transformer qui traitera le contenu de la variable avant affichage.
     * 
     * @param transformer the transformer to set
     * @throws JspException Levée si la classe est impossible à instancier
     */
    public void setTransformer(final String transformer) throws JspException
    {
        this.transformer = ClassTools.loadClassInJsp( LOG_ADAPTER, transformer );
    }

    /**
     * Récupère la politique de recherche du paramètre.
     * 
     * @return the policy
     */
    public RequestPolicy getPolicy()
    {
        return this.policy;
    }

    @Override
    public int doStartTag() throws JspException
    {
        Object value = HTTPDataManager.getData( this.pageContext, this.name, this.policy );

        if (value != null)
        {
            if (this.transformer != null)
            {
                value = this.transformer.transform( value, this.pageContext );
            }

            try
            {
                this.pageContext.getOut().write( StringEscapeUtils.escapeHtml( value.toString() ) );
            }
            catch (IOException e)
            {
                throw new JspException( e );
            }
        }
        return Tag.SKIP_BODY;
    }

}
