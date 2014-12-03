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
package org.ecosystems.weblib.html.form;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import org.ecosystems.weblib.html.form.config.FieldContext;
import org.ecosystems.weblib.log.LoggerAdapter;
import org.ecosystems.weblib.tools.TagTools;

/**
 * Tag affichant son corps si un champ input associé est invalide.
 */
public class ConditionalInputTag extends BodyTagSupport
{
    private static final LoggerAdapter LOG_ADAPTER = new LoggerAdapter( ConditionalInputTag.class );

    private FormTag formParent;

    private FieldContext<?, ?> fieldContext;

    private String name;

    private String rule;

    private static final long serialVersionUID = -4580467950196734507L;

    /**
     * Tag affichant son corps si un champ input associé est invalide.
     */
    public ConditionalInputTag()
    {
        super();

    }

    /**
     * Définit le nom du champ associé au tag.
     * 
     * @param name the name to set
     */
    public void setName(final String name)
    {
        this.name = name;
    }

    /**
     * Récupère le nom du champ associé au tag.
     * 
     * @return the name
     */
    public String getName()
    {
        return this.name;
    }

    @Override
    public int doStartTag() throws JspException
    {
        final FormTag parent = TagTools.getAncestor( this, FormTag.class );

        if (parent == null)
        {
            throw LOG_ADAPTER.logAndReturnJspException( "InputTag non contenu dans un FormTag" ); //$NON-NLS-1$
        }
        this.formParent = parent;

        try
        {
            this.fieldContext = this.formParent.getFieldContext( this.name );
        }
        catch (final NoSuchFieldException e)
        {
            throw LOG_ADAPTER.logAndReturnJspException( e );
        }

        LOG_ADAPTER.trace( "regles invalides pour le champ", this.name, " ( rule = ", this.rule, ") : ",
                this.fieldContext.getInvalidRules() );

        final boolean display = !this.fieldContext.getInvalidRules().isEmpty() && this.fieldContext.getInvalidRules().contains( this.rule )
                || ((this.rule == null || this.rule.isEmpty()) && !this.fieldContext.isValid());

        if (this.formParent.isValid() || !this.fieldContext.isPosted() || !display)
        {
            LOG_ADAPTER.trace( "Rule not display" );
            return Tag.SKIP_BODY;
        }
        LOG_ADAPTER.trace( "Rule display" );
        return Tag.EVAL_BODY_INCLUDE;

    }

    /**
     * Définit le nom de la règle provoquant l'affichage du tag.
     * 
     * @param rule the rule to set
     */
    public void setRule(final String rule)
    {
        this.rule = rule;
    }

    /**
     * Récupère le nom de la règle provoquant l'affichage du tag.
     * 
     * @return the rule
     */
    public String getRule()
    {
        return this.rule;
    }
}
