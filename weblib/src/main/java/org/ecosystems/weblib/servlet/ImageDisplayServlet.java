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
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet affichant une image.
 */
public abstract class ImageDisplayServlet extends HttpServlet
{

    private static final long serialVersionUID = 4797654015901262485L;

    /**
     * Taille du buffer lors de la lecture de flux.
     */
    private static final int BUFFER_SIZE = 1024;

    /**
     * Récupère un flux binare de lecture sur l'image.
     * 
     * @param request requête http
     * @return flux binaire d'entrée
     * @throws ServletException Levée si l'image ne peut pas être récupérée
     */
    protected abstract InputStream getImgStream(HttpServletRequest request) throws ServletException;

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException
    {
        final InputStream stream = this.getImgStream( request );
        final byte[] buffer = new byte[BUFFER_SIZE];
        while (stream.read( buffer, 0, BUFFER_SIZE ) > 0)
        {
            response.getOutputStream().write( buffer );
        }
        response.getOutputStream().flush();
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException
    {
        super.doPost( req, resp );
    }
}
