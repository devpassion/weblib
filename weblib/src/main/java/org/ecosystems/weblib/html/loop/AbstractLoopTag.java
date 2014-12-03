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
import javax.servlet.jsp.tagext.IterationTag;
import javax.servlet.jsp.tagext.Tag;

import org.ecosystems.weblib.log.LoggerAdapter;

/**
 * Classe abstraite des boucles.
 */
public abstract class AbstractLoopTag extends BodyTagSupport
{

    private static final long serialVersionUID = -722421997516189577L;

    private LoggerAdapter logAdapter = null;

    /**
     * Constructeur par défaut.
     */
    public AbstractLoopTag()
    {
        super();
    }

    @Override
    public final int doStartTag() throws JspException
    {

        if (startLoop())
        {
            return Tag.EVAL_BODY_INCLUDE;
        }

        this.getLogger().trace( "doStartTag() : SKIP_BODY" ); //$NON-NLS-1$
        return Tag.SKIP_BODY;
    }

    @Override
    public void doInitBody() throws JspException
    {
        this.getLogger().trace( "doInitBody()" ); //$NON-NLS-1$
        super.doInitBody();
    }

    @Override
    public final int doAfterBody() throws JspException
    {
        if (this.endLoop())
        {
            return Tag.SKIP_BODY;
        }

        this.getLogger().trace( "doAfterBody() : EVAL_BODY_AGAIN" ); //$NON-NLS-1$
        return IterationTag.EVAL_BODY_AGAIN;
    }

    /**
     * Traitement de fin d'itération.
     * 
     * @return vrai si la boucle est terminée
     * @throws JspException Levée si une erreur survient
     */
    protected abstract boolean endLoop() throws JspException;

    /**
     * Traitement de début de boucle.
     * 
     * @return vrai si la boucle continue, faux sinon
     * @throws JspException Levée si une erreur survient
     */
    protected abstract boolean startLoop() throws JspException;

    /**
     * Adapter de logs.
     * 
     * @return Adapter de logs de la classe
     */
    protected LoggerAdapter getLogger()
    {
        if (this.logAdapter == null)
        {
            this.logAdapter = new LoggerAdapter( this.getClass() );
        }
        return this.logAdapter;
    }

}
