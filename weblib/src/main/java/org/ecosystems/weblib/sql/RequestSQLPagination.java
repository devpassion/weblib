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
package org.ecosystems.weblib.sql;

import javax.servlet.ServletRequest;

import org.ecosystems.lib.config.Configurator;
import org.ecosystems.lib.sql.SQLPagination;

/**
 * Pagination basée sur les données d'une requête (typiquement les paramètres get).
 */
public class RequestSQLPagination implements SQLPagination
{

    private int currentPage;

    private int resultsPerPage;

    /**
     * Nouvelle pagination basée sur des apramètres de requête.
     * 
     * @param servletRequest Requête http
     * @param pageParamName Nom du paramètre de requête indiquant le numéro de page
     * @param resultsParamName Nom du paramètre de requête indiquant le nombre de résultats par page
     */
    public RequestSQLPagination(final ServletRequest servletRequest, final String pageParamName, final String resultsParamName)
    {
        final String pnValue = servletRequest.getParameter( pageParamName );
        final String rcValue = servletRequest.getParameter( resultsParamName );

        try
        {
            this.resultsPerPage = Integer.valueOf( rcValue ).intValue();
        }
        catch (final NumberFormatException e)
        {
            final String cp = Configurator.getString( Configurator.JDBC.DEFAULT_PAGE_COUNT );
            try
            {
                this.resultsPerPage = Integer.valueOf( cp ).intValue();
            }
            catch (final NumberFormatException e1)
            {
                throw new IllegalArgumentException( "Paramètre de configuration invalide : " + Configurator.JDBC.DEFAULT_PAGE_COUNT, e1 );
            }
        }

        try
        {
            this.currentPage = Integer.valueOf( pnValue ).intValue();
        }
        catch (final NumberFormatException e)
        {
            this.currentPage = 1;
        }

    }

    public RequestSQLPagination(final ServletRequest servletRequest, final String pageParamName, final int resultPerPage)
    {
        final String pnValue = servletRequest.getParameter( pageParamName );

        this.resultsPerPage = resultPerPage;

        try
        {
            this.currentPage = Integer.valueOf( pnValue ).intValue();
        }
        catch (final NumberFormatException e)
        {
            this.currentPage = 1;
        }
    }

    @Override
    public int beginIndex()
    {
        return (this.currentPage - 1) * this.resultsPerPage;
    }

    @Override
    public int current()
    {
        return this.currentPage;
    }

    @Override
    public int pages(final int totalResults)
    {
        return (totalResults / this.resultPerPage()) + 1;
    }

    @Override
    public int resultPerPage()
    {
        return this.resultsPerPage;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( "RequestSQLPagination [currentPage=" ).append( this.currentPage ).append( ", resultsPerPage=" )
                .append( this.resultsPerPage ).append( "]" );
        return builder.toString();
    }

}
