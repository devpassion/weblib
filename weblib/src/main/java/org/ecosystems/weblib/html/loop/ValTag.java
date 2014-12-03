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
package org.ecosystems.weblib.html.loop;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.lang.StringEscapeUtils;
import org.ecosystems.weblib.log.LoggerAdapter;
import org.ecosystems.weblib.tools.ClassTools;
import org.ecosystems.weblib.tools.TagTools;
import org.ecosystems.weblib.tools.ValueTransformer;

/**
 * Valeur d'un résultat, à placer à l'intérieur d'une boucle.
 */
public class ValTag extends BodyTagSupport
{
    // [start] Members

    private static final long serialVersionUID = -7770756451755681955L;

    private static final LoggerAdapter LOG_ADAPTER = new LoggerAdapter( ValTag.class );

    private String name;

    private boolean disableEscape = false;

    private ValueTransformer transformer;

    // [end]

    // [start] Constructors

    // [end]

    // [start] Getters and setters

    /**
     * Récupère le nom de la valeur à afficher, telle que définie dans le fournisseur de résultats.
     * 
     * @return the name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Définit le nom de la valeur à afficher, telle que définie dans le fournisseur de résultats.
     * 
     * @param name the name to set
     */
    public void setName(final String name)
    {
        this.name = name;
    }

    /**
     * Récupère une valeur indiquant si l'échappement de tag HTML est activée.
     * 
     * @return the disableEscape
     */
    public boolean isDisableEscape()
    {
        return this.disableEscape;
    }

    /**
     * Indique si l'échappement de tag HTML est activée.
     * 
     * @param disableEscape the disableEscape to set
     */
    public void setDisableEscape(final boolean disableEscape)
    {
        this.disableEscape = disableEscape;
    }

    /**
     * Récupère la classe du {@link org.ecosystems.weblib.tools.ValueTransformer} appliqué sur la valeur.
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
     * Définit la classe du {@link org.ecosystems.weblib.tools.ValueTransformer} appliqué sur la valeur.
     * 
     * @param transformer the transformer to set
     * @throws JspException Levée si la classe est impossible à instancier
     */
    public void setTransformer(final String transformer) throws JspException
    {
        this.transformer = ClassTools.loadClassInJsp( LOG_ADAPTER, transformer );
    }

    // [end]

    // [start] Public methods

    @Override
    public int doStartTag() throws JspException
    {
        LOG_ADAPTER.trace( "doStartTag()" ); //$NON-NLS-1$
        final Tag oparent = TagTools.getAncestor( this, LoopTag.class );
        if (!(oparent instanceof LoopTag))
        {
            throw new JspException( "Un tag Val doit être contenu dans un tag Loop" ); //$NON-NLS-1$
        }
        final LoopTag parent = (LoopTag) oparent;
        Object value;
        try
        {

            value = parent.getResult().getObject( this.name );
            if (this.transformer != null)
            {
                value = this.transformer.transform( value, this.pageContext );
            }
            LOG_ADAPTER.trace( "doStartTag(), value (name = '", this.name, "') : ", value ); //$NON-NLS-1$ //$NON-NLS-2$
            if (value != null)
            {
                if (this.disableEscape)
                {
                    this.pageContext.getOut().print( value );
                }
                else
                {
                    this.pageContext.getOut().print( StringEscapeUtils.escapeHtml( value.toString() ) );
                }
            }
        }
        catch (SQLException e)
        {
            throw new JspException( e );
        }
        catch (IOException e)
        {
            throw new JspException( e );
        }

        // return Tag.EVAL_BODY_INCLUDE;
        return Tag.SKIP_BODY;
    }
    // [end]

    // [start] Protected methods

    // [end]

    // [start] Private methods

    // [end]

}
