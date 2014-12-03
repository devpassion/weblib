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
package org.ecosystems.weblib.lang;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import org.ecosystems.weblib.log.LoggerAdapter;

/**
 * Paramètre de langage.
 * 
 * Ce tag est un paramètre de chaîne de langue. Il doit obligatoirement être contenu dans un Tag {@link LanguageElementTag}
 */
public class LanguageParameterTag extends BodyTagSupport
{

    private static final long serialVersionUID = -7066261979534145224L;

    private String name;

    private String value;

    @Override
    public int doAfterBody()
    {
        this.value = this.bodyContent.getString();
        return Tag.SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException
    {
        final Object oParent = this.getParent();
        if (!(oParent instanceof LanguageElementTag))
        {
            throw this.getLogger().logAndReturnJspException( "Les paramètres de langage doivent se trouver dans un tag LanguageElement" );
        }
        final LanguageElementTag le = (LanguageElementTag) oParent;
        le.addParameter( this.getName(), this.value );
        return Tag.EVAL_PAGE;
    }

    /**
     * Récupère le nom du paramètre.
     * 
     * @return the name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Définit le nom du paramètre.
     * 
     * @param name the name to set
     */
    public void setName(final String name)
    {
        this.name = name;
    }

    private LoggerAdapter logAdp;

    /**
     * Récupère un logger.
     * 
     * @return logger associé à la classe.
     */
    protected LoggerAdapter getLogger()
    {
        if (this.logAdp == null)
        {
            this.logAdp = new LoggerAdapter( this.getClass() );
        }
        return this.logAdp;
    }
}
