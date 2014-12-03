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

import java.util.Collections;
import java.util.List;

import javax.servlet.jsp.JspException;

/**
 * Simple texte HTML manipulable comme un tag.
 */
public class HtmlText implements HtmlTag
{

    private final String text;

    /**
     * Crée du texte HTML à partir d'une chaîne.
     * 
     * @param text texte HTML
     */
    public HtmlText(final String text)
    {
        this.text = text;
    }

    @Override
    public void addChildren(final HtmlTag children) throws IllegalHtmlChildException
    {
        throw new IllegalHtmlChildException( this, children );
    }

    @Override
    public Attribute<?> getAttribute(final String name) throws UnknowAttributeException
    {
        throw new UnknowAttributeException( name, this );
    }

    @Override
    public String getBeginTag()
    {
        return this.text;
    }

    @Override
    public String getEndTag()
    {
        return ""; //$NON-NLS-1$
    }

    @Override
    public String getTagName()
    {
        return ""; //$NON-NLS-1$
    }

    @Override
    public void setAttribute(final Attribute<?> attribute) throws InvalidAttributeTypeException
    {
        throw new InvalidAttributeTypeException( attribute, this );
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ecosystems.weblib.html.HtmlTag#hasAttribute(java.lang.String)
     */
    @Override
    public boolean hasAttribute(final String name)
    {
        return false;
    }

    @Override
    public void setDynamicAttribute(final String arg0, final String arg1, final Object arg2) throws JspException
    {
        throw new JspException( new InvalidAttributeTypeException( arg1, this ) );
    }

    @Override
    public List<HtmlTag> getChildren()
    {
        return Collections.emptyList();
    }

    @Override
    public void removeAttribute(final String name)
    {
        // TODO Auto-generated method stub

    }

}
