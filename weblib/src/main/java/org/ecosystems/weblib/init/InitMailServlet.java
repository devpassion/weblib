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
package org.ecosystems.weblib.init;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.ecosystems.lib.config.Configurator;
import org.ecosystems.weblib.log.LoggerAdapter;
import org.ecosystems.weblib.mail.Mailer;

/**
 * Servlet initialisant les paramètres de mail.
 */
public class InitMailServlet extends HttpServlet
{

    private static final long serialVersionUID = 5968749693701178097L;

    private static final LoggerAdapter LOG_ADAPTER = new LoggerAdapter( InitMailServlet.class );

    @Override
    public void init(final ServletConfig config) throws ServletException
    {

        super.init( config );

        final String from = Configurator.getString( Configurator.MAIL.FROM );
        final String smtp = Configurator.getString( Configurator.MAIL.SMTP_HOST );
        try
        {
            LOG_ADAPTER.info( "initialisation du mailer ..." ); //$NON-NLS-1$
            Mailer.init( smtp, new InternetAddress( from ) );
            LOG_ADAPTER.info( "mailer initialisé" ); //$NON-NLS-1$
        }
        catch (AddressException e)
        {
            throw new ServletException( e );
        }
    }
}
