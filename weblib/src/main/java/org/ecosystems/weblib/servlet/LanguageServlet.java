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
package org.ecosystems.weblib.servlet;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ecosystems.lib.config.Configurator;
import org.ecosystems.weblib.lang.LanguageManager;
import org.ecosystems.weblib.lang.NoLanguageAvailableException;
import org.ecosystems.weblib.log.LoggerAdapter;

/**
 * Servlet initialisant des fonctionnalités de multilinguisme.
 */
public class LanguageServlet extends HttpServlet
{

    private static final long serialVersionUID = 533923192619502266L;

    private LoggerAdapter logAdapter = null;

    /**
     * Récupère un logger pour cette classe.
     * 
     * @return logger de cette classe
     */
    protected LoggerAdapter getLogger()
    {
        if (this.logAdapter == null)
        {
            this.logAdapter = new LoggerAdapter( this.getClass() );
        }
        return this.logAdapter;
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException
    {
        this.doLangRequest( request, response );
        // super.doGet( request, response );
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException
    {
        this.doLangRequest( request, response );
        // super.doPost( request, response );
    }

    private void doLangRequest(final HttpServletRequest request, final HttpServletResponse response) throws ServletException
    {
        try
        {
            final Locale lang = LanguageManager.getCurrentLocale( request, response );
            this.getLogger().debug( "langue définie à ", lang.getLanguage() ); //$NON-NLS-1$
            request.setAttribute( Configurator.getString( Configurator.Lang.PARAMETER_NAME ), lang );

        }
        catch (NoLanguageAvailableException e)
        {
            throw new ServletException( e );
        }
    }
}
