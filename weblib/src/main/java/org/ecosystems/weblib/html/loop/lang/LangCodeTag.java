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
package org.ecosystems.weblib.html.loop.lang;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.ecosystems.weblib.log.LoggerAdapter;

/**
 * Tag affiché dans une boucle de choix de langue affichant le code de langue.
 */
public class LangCodeTag extends SimpleTagSupport
{

    private static final LoggerAdapter LOG_ADAPTER = new LoggerAdapter( LangCodeTag.class );

    @Override
    public void doTag() throws JspException, IOException
    {
        final Object oParent = this.getParent();

        if (oParent == null || !(oParent instanceof LangLoopTag))
        {
            throw new JspException( "Le tag CurrentLang doit être contenu dans une boucle de langue" ); //$NON-NLS-1$
        }

        final LangLoopTag parent = (LangLoopTag) oParent;

        LOG_ADAPTER.trace( "doTag, display :  ", parent.getCurrentLang().getLanguage() ); //$NON-NLS-1$

        this.getJspContext().getOut().print( parent.getCurrentLang().getLanguage() );
    }
}
