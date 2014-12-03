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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

/**
 * Tag affiché dans une boucle de choix de langue si la langue de l'itération est la langue courante.
 */
public class CurrentTag extends BodyTagSupport
{

    private static final long serialVersionUID = -5921241372652229307L;

    @Override
    public int doStartTag() throws JspException
    {
        final Object oParent = this.getParent();

        if (!(oParent instanceof LangLoopTag))
        {
            throw new JspException( "Le tag CurrentLang doit être contenu dans une boucle de langue" ); //$NON-NLS-1$
        }

        final LangLoopTag parent = (LangLoopTag) oParent;

        if (parent.isCurrent())
        {
            return Tag.EVAL_BODY_INCLUDE;
        }
        return Tag.SKIP_BODY;
    }

}
