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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.ecosystems.weblib.tools.TagTools;

/**
 * Tag affichant le numéro courant de page.
 */
public class PageNumberTag extends SimpleTagSupport
{
    @Override
    public void doTag() throws JspException, IOException
    {
        final LoopPaginationTag parent = TagTools.getAncestor( this, LoopPaginationTag.class );
        if (parent == null)
        {
            throw new JspException( "Le tag PageNumber n'est pas dans une boucle de pagination" );
        }
        this.getJspContext().getOut().write( Integer.valueOf( parent.getCurrentPageNumber() ).toString() );
    }
}
