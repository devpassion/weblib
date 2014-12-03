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

import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;
import javax.sql.rowset.CachedRowSet;

import org.ecosystems.weblib.log.LoggerAdapter;
import org.ecosystems.weblib.tools.ClassTools;

/**
 * Wrapper de tag Loop. Affiche son contenu uniquement si des résultats sont fournis.
 */
public class LoopWrapperTag extends BodyTagSupport
{

    private static final long serialVersionUID = 5957554177242448718L;

    private static final LoggerAdapter LOG_ADAPTER = new LoggerAdapter( LoopWrapperTag.class );

    private CachedRowSet set = null;

    private ResultProvider resultProvider;

    private String resultProviderClass;

    private String loopIndex;

    /**
     * Fournit le résultat courant aux tag enfants.
     * 
     * @return resultat placé sur la ligne courante
     */
    CachedRowSet getResult()
    {
        return this.set;
    }

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

    @Override
    public int doStartTag() throws JspException
    {
        this.resultProvider = ClassTools.loadClassInJsp( LOG_ADAPTER, this.getResultProviderClass() );

        try
        {
            this.set = this.resultProvider.getResult( this.pageContext );

            if (this.set != null && !this.set.isLast() && this.set.next())
            {
                LOG_ADAPTER.trace( "doStartTag() : EVAL_BODY_INCLUDE" ); //$NON-NLS-1$
                return Tag.EVAL_BODY_INCLUDE;
            }

        }
        catch (SQLException e)
        {
            throw new JspException( e );
        }
        LOG_ADAPTER.trace( "doStartTag() : SKIP_BODY" ); //$NON-NLS-1$
        return Tag.SKIP_BODY;
    }

    /**
     * Définit l'index de boucle.
     * 
     * @param loopIndex the loopIndex to set
     */
    public void setLoopIndex(final String loopIndex)
    {
        this.loopIndex = loopIndex;
    }

    /**
     * Récupère l'index de boucle, donnée unique permattant d'identifier la boucle.
     * 
     * @return the loopIndex
     */
    public String getLoopIndex()
    {
        return this.loopIndex;
    }
}
