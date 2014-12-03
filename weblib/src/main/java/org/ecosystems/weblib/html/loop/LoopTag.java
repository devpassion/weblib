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

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.sql.rowset.CachedRowSet;

import org.ecosystems.weblib.tools.ClassTools;

/**
 * Boucle sur une ensemble de résultats.
 */
public class LoopTag extends AbstractLoopTag
{

    private static final long serialVersionUID = -5412995273292160453L;

    private CachedRowSet set = null;

    private ResultProvider resultProvider;

    private String resultProviderClass;

    /**
     * Récupère la classe du fournisseur de résultats.
     * 
     * @return the resultProviderClass
     */
    public String getResultProviderClass()
    {
        return this.resultProviderClass;
    }

    /**
     * Définit la classe du fournisseur de résultats.
     * 
     * @param resultProviderClass the resultProviderClass to set
     */
    public void setResultProviderClass(final String resultProviderClass)
    {
        this.resultProviderClass = resultProviderClass;
    }

    // [start] Protected methods

    /**
     * Fournit le résultat courant aux tag enfants.
     * 
     * @return resultat placé sur la ligne courante
     */
    protected ResultSet getResult()
    {
        return this.set;
    }

    @Override
    protected boolean endLoop() throws JspException
    {
        try
        {
            if (!this.set.next())
            {
                this.set.close();
                this.getLogger().trace( "doAfterBody() : SKIP_BODY" ); //$NON-NLS-1$
                return true;
            }

        }
        catch (SQLException e)
        {
            throw new JspException( e );
        }

        return false;
    }

    @Override
    protected boolean startLoop() throws JspException
    {
        final Object oParent = this.getParent();

        if (oParent instanceof LoopWrapperTag)
        {
            final LoopWrapperTag parent = (LoopWrapperTag) oParent;
            this.set = parent.getResult();
            return true;
        }

        this.resultProvider = ClassTools.loadClassInJsp( this.getLogger(), this.getResultProviderClass() );

        try
        {
            this.set = this.resultProvider.getResult( this.pageContext );

            if (this.set != null && this.set.next())
            {
                this.getLogger().trace( "doStartTag() : EVAL_BODY_BUFFERED" ); //$NON-NLS-1$
                return true;
            }

        }
        catch (SQLException e)
        {
            throw new JspException( e );
        }
        return false;
    }

}
