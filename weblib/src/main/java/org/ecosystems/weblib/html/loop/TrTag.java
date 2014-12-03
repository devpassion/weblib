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

import org.ecosystems.weblib.html.AbstractHtmlTag;
import org.ecosystems.weblib.html.TagDispositionPolicy;

/**
 * Tag tr.
 * 
 * TODO : tester ?
 */
public class TrTag extends AbstractHtmlTag
{

    private static final long serialVersionUID = 7124997618941325184L;

    /**
     * Constructeur par défaut.
     */
    public TrTag()
    {
        super( "tr", TagDispositionPolicy.ENCLOSE_BODY ); //$NON-NLS-1$
    }

}
