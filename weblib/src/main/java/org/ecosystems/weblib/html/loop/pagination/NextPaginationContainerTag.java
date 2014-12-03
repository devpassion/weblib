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

/**
 * Tag contenant un bouton de pagination "next"
 * 
 * Le tag affiche théoriquement son contenu si une page suivante existe mais pour des raisons d'optimisation, il est préférable que ça ne
 * soit pas le cas.
 * 
 * Il contient un tag {@link NextPaginationLinkTag}, qui représente le lien vers la page suivante.
 * 
 * @author tim288
 * 
 */
public class NextPaginationContainerTag extends BodyTagSupport
{

    private static final long serialVersionUID = -1238981720404270396L;

    /**
     * Nouveau tag de paginatino suivante.
     */
    public NextPaginationContainerTag()
    {
    }

    @Override
    public int doStartTag() throws JspException
    {
        return Tag.EVAL_BODY_INCLUDE;
    }

}
