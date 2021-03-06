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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.ecosystems.lib.config.Configurator;
import org.ecosystems.weblib.log.LoggerAdapter;
import org.ecosystems.weblib.tools.HttpTools;
import org.ecosystems.weblib.tools.TagTools;

/**
 * Tag affichant le lien vers la page précédente. Doit être contenu dans un tag {@link PrevPaginationContainerTag}
 */
public class PrevPaginationLinkTag extends TagSupport
{

    private static final long serialVersionUID = 8212798799714065367L;

    private static final LoggerAdapter LOGGER_ADAPTER = new LoggerAdapter( PrevPaginationLinkTag.class );

    private boolean contentInjectMode = false;

    private String defaultPage = "";

    @Override
    public int doStartTag() throws JspException
    {
        final Tag parent = TagTools.getAncestor( this, PrevPaginationContainerTag.class );
        if (parent == null)
        {
            throw new JspException( "Le tag NextPaginationLinkTag n'est pas contenu dans un tag NextPaginationContainerTag" );
        }
        try
        {
            this.pageContext.getOut().write( this.getLink( (HttpServletRequest) this.pageContext.getRequest() ) );
        }
        catch (IOException | URISyntaxException e)
        {
            throw new JspException( e );
        }
        return super.doStartTag();
    }

    private String getLink(final HttpServletRequest request) throws URISyntaxException, MalformedURLException, JspException
    {
        final String paginationParamName = Configurator.getString( Configurator.PAGINATION.PAGE_PARAM_NAME, "page" );
        final String parameter = request.getParameter( paginationParamName );
        Integer pageNumber;
        // final String base = ( (HttpServletRequest) this.pageContext.getRequest()).getContextPath();
        final URL link = new URL( request.getRequestURL().toString() );

        if (parameter == null || parameter.isEmpty())
        {
            LOGGER_ADAPTER.warn( "Tag PrevPaginationLinkTag évalué en absence de page, défini à 1" );
            pageNumber = 1;
        }
        else
        {
            try
            {
                pageNumber = Integer.valueOf( parameter ) - Integer.valueOf( 1 );
            }
            catch (final NumberFormatException e)
            {
                LOGGER_ADAPTER.warn( "pagination invalide (définie à 1)", e );
                pageNumber = 1;
            }
        }

        final String ret;

        if (!this.contentInjectMode)
        {
            ret = HttpTools.replaceInQuery( link, paginationParamName, pageNumber.toString() ).toString();
        }
        else
        {

            final Object include = request.getAttribute( "include" );
            if (include == null)
            {
                throw new JspException( "Mode injection de contenu mais le apramètre include est vide dans la requete "
                        + request.getRequestURI().toString() );
            }
            if (include.equals( this.defaultPage ))
            {
                ret = request.getContextPath() + "/" + "?"
                        + HttpTools.replaceInQueryString( request.getQueryString(), paginationParamName, pageNumber.toString() );
            }
            else
            {
                ret = request.getContextPath() + "/" + include + ".html?"
                        + HttpTools.replaceInQueryString( request.getQueryString(), paginationParamName, pageNumber.toString() );
            }

        }
        LOGGER_ADAPTER.debug( "url transformée : ", ret );
        return ret;

    }

    public boolean isContentInjectMode()
    {
        return contentInjectMode;
    }

    public void setContentInjectMode(final boolean contentInjectMode)
    {
        this.contentInjectMode = contentInjectMode;
    }

    public String getDefaultPage()
    {
        return defaultPage;
    }

    public void setDefaultPage(final String defaultPage)
    {
        this.defaultPage = defaultPage;
    }
}
