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
package org.ecosystems.weblib.html.loop.pagination;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import org.ecosystems.lib.config.Configurator;

/**
 * Tag conteneur du lien vers la page précédente.
 */
public class PrevPaginationContainerTag extends BodyTagSupport
{

    private static final long serialVersionUID = -7382090936000749726L;

    /**
     * Crée un nouveau tag PrevPaginationContainerTag.
     */
    public PrevPaginationContainerTag()
    {
    }

    @Override
    public int doStartTag() throws JspException
    {
        final String paginationParamName = Configurator.getString( Configurator.PAGINATION.PAGE_PARAM_NAME, "page" );
        final String parameter = this.pageContext.getRequest().getParameter( paginationParamName );
        Integer pageNumber;
        if (parameter == null || parameter.isEmpty())
        {
            return Tag.SKIP_BODY;
        }
        pageNumber = Integer.valueOf( parameter );
        if (pageNumber > 1)
            return Tag.EVAL_BODY_INCLUDE;
        return Tag.SKIP_BODY;
    }

}
