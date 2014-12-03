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
package org.ecosystems.weblib.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Filtre d'encoage UTF-8.
 */
public class CharsetFilter implements Filter
{

    /**
     * Nom du paramètre de la configuration du filtre indiquant l'encodage à utiliser.
     */
    private static final String ENCODING_PARAMETER_NAME = "requestEncoding"; //$NON-NLS-1$

    /**
     * Encodage du filtre.
     */
    private String encoding;

    @Override
    public void destroy()
    {
        //
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain next) throws IOException,
            ServletException
    {
        request.setCharacterEncoding( this.encoding );
        response.setCharacterEncoding( this.encoding );
        next.doFilter( request, response );
    }

    @Override
    public void init(final FilterConfig config) throws ServletException
    {
        this.encoding = config.getInitParameter( ENCODING_PARAMETER_NAME );
    }

}
