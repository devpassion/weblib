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
package org.ecosystems.weblib.html.form.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadException;

public final class RequestValueProviderFactory
{
    // private static final Map<HttpServletRequest, RequestValueProvider> rvpMap = new HashMap<>();
    private static final String RVP_ATTR_NAME = "org.ecosystems.weblib.html.form.config.RVP_ATTR_NAME";

    private RequestValueProviderFactory()
    {
    }

    public static RequestValueProvider getRVPProvider(final HttpServletRequest request) throws FileUploadException, IOException
    {
        try
        {
            RequestValueProvider rvp = (RequestValueProvider) request.getAttribute( RVP_ATTR_NAME );
            if (rvp == null)
            {
                rvp = new RequestValueProvider( request );
                request.setAttribute( RVP_ATTR_NAME, rvp );
            }

            return rvp;
        }
        catch (final ClassCastException e)
        {
            throw new IllegalArgumentException( "L'attribut de requête " + RVP_ATTR_NAME + " ne contient pas un rvp valide", e );
        }

    }
}
