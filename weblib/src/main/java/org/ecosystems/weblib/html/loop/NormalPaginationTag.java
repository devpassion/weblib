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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import org.ecosystems.weblib.log.LoggerAdapter;
import org.ecosystems.weblib.tools.TagTools;

/**
 * Tag contenant le code HTML d'un numéro normal de page. Obligatoirement contenu dans un tag {@link LoopPaginationTag}.
 */
public class NormalPaginationTag extends BodyTagSupport
{

    private static final LoggerAdapter LOGGER_ADAPTER = new LoggerAdapter( NormalPaginationTag.class );

    private static final long serialVersionUID = 8094966633782563058L;

    private LoopPaginationTag parent;

    @Override
    public int doStartTag() throws JspException
    {
        LOGGER_ADAPTER.debug( "doStartTag" );
        this.parent = (LoopPaginationTag) TagTools.getAncestor( this, LoopPaginationTag.class );
        if (this.parent == null)
        {
            throw new JspException( "Le tag normalPage n'est pas contenu dans un tag loopPages" );
        }

        if (this.parent.isCurrent())
        {
            LOGGER_ADAPTER.debug( "SKIP_BODY" );
            return Tag.SKIP_BODY;
        }
        LOGGER_ADAPTER.debug( "EVAL_BODY_INCLUDE" );
        return Tag.EVAL_BODY_INCLUDE;
    }
}
