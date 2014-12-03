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

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ecosystems.weblib.lang.NoSuchLanguageConverterException;
import org.ecosystems.weblib.lang.NoSuchTraductionException;
import org.ecosystems.weblib.lang.NoSuchTraductionRessourceException;
import org.ecosystems.weblib.log.LoggerAdapter;
import org.ecosystems.weblib.mail.Mailer;

/**
 * Servlet de test de mail.
 */
public class TestMailServlet extends LanguageServlet
{

    private static final long serialVersionUID = -1176416732795406011L;

    private static final String DEST_PARAM_NAME = "dest"; //$NON-NLS-1$

    private InternetAddress dest;

    private static final LoggerAdapter LOG_ADAPTER = new LoggerAdapter( TestMailServlet.class );

    @Override
    public void init(final ServletConfig config) throws ServletException
    {
        super.init( config );

        final String dest0 = config.getInitParameter( DEST_PARAM_NAME );

        LOG_ADAPTER.debug( "dest : ", this.dest ); //$NON-NLS-1$

        try
        {

            this.dest = new InternetAddress( dest0 );
        }
        catch (AddressException e)
        {
            throw new ServletException( e );
        }

    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException
    {
        super.doGet( req, resp );

        try
        {
            Mailer.send( Locale.FRENCH, "mail", "test_subject", "test_msg", this.dest ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        }
        catch (NoSuchTraductionException | NoSuchTraductionRessourceException | MessagingException | NoSuchLanguageConverterException e)
        {
            throw new ServletException( e );
        }
        resp.getWriter().println( "message send to " + this.dest.toString() ); //$NON-NLS-1$
    }
}
