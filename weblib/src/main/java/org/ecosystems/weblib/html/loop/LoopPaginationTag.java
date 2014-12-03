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

import org.ecosystems.weblib.log.LoggerAdapter;
import org.ecosystems.weblib.sql.RequestSQLPagination;
import org.ecosystems.weblib.tools.ClassTools;

/**
 * Tag de pagination associé à une boucle, affiche un numéro de page.
 */
public class LoopPaginationTag extends AbstractLoopTag
{

    private static final long serialVersionUID = 5875440201296468136L;

    /**
     * Nom du paramètre de session contenant une Map stockée dans les attributs de requête indiquant le nombre
     * de résultats de chaque index de boucle.
     */
    public static final String PAGE_COUNT_SESSION_PARAM_NAME = "page_count";

    private static final LoggerAdapter LOGGER_ADAPTER = new LoggerAdapter( LoopPaginationTag.class );

    // private String loopIndex;

    private String pageParamName;

    private String rcParamName;

    private RequestSQLPagination pagination = null;

    private int pageCount;

    private int pageNumber = 0;

    private boolean current = false;

    private ResultProvider resultProvider;

    private String resultProviderClass;

    /**
     * Récupère une valeur indiquant si la page courante est le numéro de page indiqué par le contenu du tag.
     * 
     * @return the current
     */
    public boolean isCurrent()
    {
        return this.current;
    }

    /**
     * Récupère le nom de la classe du {@link ResultProvider}.
     * 
     * @return the resultProviderClass
     */
    public String getResultProviderClass()
    {
        return this.resultProviderClass;
    }

    /**
     * Définit le nom de la classe du {@link ResultProvider}.
     * 
     * @param resultProviderClass the resultProviderClass to set
     */
    public void setResultProviderClass(final String resultProviderClass)
    {
        this.resultProviderClass = resultProviderClass;
    }

    // private int getResultCount() throws JspException
    // {
    // Object oMap = this.pageContext.getRequest().getAttribute( LoopPaginationTag.PAGE_COUNT_SESSION_PARAM_NAME );
    // if ( oMap == null || !( oMap instanceof Map<?, ?> ) )
    // {
    // throw new JspException( "Pas de résultat de compteur de page trouve pour l'index " + this.loopIndex );
    // }
    // try
    // {
    // // ici le cast est entouré d'un try/catch
    // @SuppressWarnings( "unchecked" )
    // Map<String, Integer> map = (Map<String, Integer>) oMap;
    //
    // if ( !map.containsKey( this.loopIndex ) )
    // {
    // throw new JspException( "Map trouvée mais pas de résultat de compteur de page trouve pour l'index " +
    // this.loopIndex );
    // }
    // return map.get( this.loopIndex ).intValue();
    // }
    // catch ( ClassCastException e )
    // {
    // throw new JspException( "Map invalide pour l'index " + this.loopIndex + ". ??? ", e );
    // }
    //
    // }

    @Override
    public void doInitBody() throws JspException
    {

        LOGGER_ADAPTER.debug( "doInitBody" );

        this.pageNumber = 1;

        this.resultProvider = ClassTools.loadClassInJsp( LOGGER_ADAPTER, this.resultProviderClass );

        this.pagination = new RequestSQLPagination( this.pageContext.getRequest(), this.getPageParamName(), this.getRcParamName() );

        LOGGER_ADAPTER.debug( "doInitBody : pagination = ", this.pagination );

        try
        {
            this.pageCount = this.pagination.pages( this.resultProvider.getCount( this.pageContext ) );
            LOGGER_ADAPTER.debug( "doInitBody : Total de pages = ", Integer.valueOf( this.pageCount ) );
        }
        catch (SQLException e)
        {
            throw new JspException( e );
        }

        this.current = (this.pagination.current() == this.pageNumber);

        // super.doInitBody();
    }

    /**
     * Récupère le nom du paramètre indiquant la page courante.
     * 
     * @return the pageParamName
     */
    public String getPageParamName()
    {
        return this.pageParamName;
    }

    /**
     * Définit le nom du paramètre indiquant la page courante.
     * 
     * @param pageParamName the pageParamName to set
     */
    public void setPageParamName(final String pageParamName)
    {
        this.pageParamName = pageParamName;
    }

    /**
     * Récupère le nom du paramètre HTTp indiquant le nombre de résdultats par pages.
     * 
     * @return the rcParamName
     */
    public String getRcParamName()
    {
        return this.rcParamName;
    }

    /**
     * Définit le nom du paramètre HTTp indiquant le nombre de résdultats par pages.
     * 
     * @param rcParamName the rcParamName to set
     */
    public void setRcParamName(final String rcParamName)
    {
        this.rcParamName = rcParamName;
    }

    // /**
    // * @return the loopIndex
    // */
    // public String getLoopIndex()
    // {
    // return this.loopIndex;
    // }
    //
    // /**
    // * @param loopIndex the loopIndex to set
    // */
    // public void setLoopIndex(final String loopIndex)
    // {
    // this.loopIndex = loopIndex;
    // }

    /**
     * Récupère le numéro courant de la page.
     * 
     * @return numéro de la apge courante
     */
    int getCurrentPageNumber()
    {
        return this.pageNumber;
    }

    /**
     * Récupère le nombre total de page.
     * 
     * @return nombre total de pages
     */
    int getTotalPageNumber()
    {
        return this.pageCount;
    }

    @Override
    protected boolean endLoop() throws JspException
    {
        this.pageNumber++;
        LOGGER_ADAPTER.debug( "doAfterBody, page number = ", Integer.valueOf( this.pageNumber ), " / page count = ",
                Integer.valueOf( this.pageCount ) );
        if (this.pageNumber > this.pageCount)
        {
            LOGGER_ADAPTER.debug( "doAfterBody, return end of loop" );
            return true;
        }
        this.current = (this.pagination.current() == this.pageNumber);
        LOGGER_ADAPTER.debug( "doAfterBody, return continue loop, isCurrent = ", Boolean.valueOf( this.current ) );
        return false;
    }

    @Override
    protected boolean startLoop() throws JspException
    {
        LOGGER_ADAPTER.debug( "startLoop" );

        this.pageNumber = 1;

        this.resultProvider = ClassTools.loadClassInJsp( LOGGER_ADAPTER, this.resultProviderClass );

        this.pagination = new RequestSQLPagination( this.pageContext.getRequest(), this.getPageParamName(), this.getRcParamName() );

        LOGGER_ADAPTER.debug( "doInitBody : pagination = ", this.pagination );

        try
        {
            this.pageCount = this.pagination.pages( this.resultProvider.getCount( this.pageContext ) );
            LOGGER_ADAPTER.debug( "doInitBody : Total de pages = ", Integer.valueOf( this.pageCount ) );
        }
        catch (SQLException e)
        {
            throw new JspException( e );
        }

        this.current = (this.pagination.current() == this.pageNumber);
        return true;
    }

}
