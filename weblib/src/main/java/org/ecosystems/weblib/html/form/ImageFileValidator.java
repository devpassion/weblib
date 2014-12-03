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
package org.ecosystems.weblib.html.form;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.ecosystems.weblib.log.LoggerAdapter;

/**
 * Validateur pour une image.
 */
public class ImageFileValidator extends AbstractInputValidator
{

    private static final LoggerAdapter LOGGER_ADAPTER = new LoggerAdapter( ImageFileValidator.class );

    @Override
    public boolean validate(final Object value)
    {
        LOGGER_ADAPTER.trace( "validate file ", value, "..." );
        if (value == null || !(value instanceof File))
        {
            return false;
        }

        final File f = (File) value;

        try
        {
            final String mimeType = net.sf.jmimemagic.Magic.getMagicMatch( f, false ).getMimeType();
            LOGGER_ADAPTER.debug( "mime = ", mimeType );
            return mimeType.startsWith( "image/" );
        }
        catch (final net.sf.jmimemagic.MagicParseException | net.sf.jmimemagic.MagicMatchNotFoundException
                | net.sf.jmimemagic.MagicException e)
        {
            LOGGER_ADAPTER.warn( "détermination du type du fichier " + f.getName() + " impossible :", e );
            return false;
        }

    }

    @Override
    public String ruleName()
    {
        // TODO Auto-generated method stub
        return "ValidImageFile";
    }

    @Override
    public void setRequest(final HttpServletRequest request)
    {
        // TODO Auto-generated method stub

    }

}
