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

import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ecosystems.weblib.html.form.Captcha;
import org.ecosystems.weblib.html.form.CaptchaValidator;
import org.ecosystems.weblib.log.LoggerAdapter;

public class CaptchaServlet extends HttpServlet
{

    private static final LoggerAdapter LOGGER_ADAPTER = new LoggerAdapter( CaptchaServlet.class );

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException
    {
        resp.addHeader( "Cache-Control", "no-cache" );
        resp.addHeader( "Pragma", "no-cache" );
        final Captcha captcha = new Captcha();
        LOGGER_ADAPTER.debug( "Captcha = ", captcha, " (ip=", req.getRemoteAddr(), ")" );
        req.getSession().setAttribute( CaptchaValidator.CAPTCHA_SESSION_PARAM_NAME, captcha.getResponse() );
        LOGGER_ADAPTER.trace( "session challenge = ", req.getSession().getAttribute( CaptchaValidator.CAPTCHA_SESSION_PARAM_NAME ) );
        final byte[] buffer = new byte[1024];
        final FileInputStream in = new FileInputStream( captcha.getImage() );
        while (in.read( buffer ) > 0)
        {
            resp.getOutputStream().write( buffer );
        }
        in.close();
        captcha.destroy();
        resp.flushBuffer();
    }
}
