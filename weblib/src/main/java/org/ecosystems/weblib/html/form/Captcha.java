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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.ecosystems.weblib.tools.RandomTools;

/**
 * Captcha anti-robot.
 * 
 * Un captcha est composé d'une image aléatoire contenant un challenge, de la solution du challenge.
 * 
 */
public class Captcha
{
    private final File image;

    private final String response;

    /**
     * Génère un captcha aléatoire.
     * 
     * @throws IOException Levée si il est impossible de générer une image avec un challenge
     */
    public Captcha() throws IOException
    {
        super();
        final Integer a = (int) RandomTools.getRandomLong( 0, 10 );
        final Integer b = (int) RandomTools.getRandomLong( 0, 10 );
        final String question = a.toString() + "+" + b.toString();
        this.image = this.getImage( question );
        this.response = Integer.valueOf( a + b ).toString();
    }

    private File getImage(final String question) throws IOException
    {
        final Process proc = Runtime.getRuntime().exec( "convert -pointsize 72 label:" + question + " jpeg:-" );
        final InputStream in = proc.getInputStream();

        final File temp = File.createTempFile( "tempcaptcha" + question + System.currentTimeMillis(), ".tmp" );
        final BufferedOutputStream out = new BufferedOutputStream( new FileOutputStream( temp ) );

        final byte[] buffer = new byte[1024];
        while (in.read( buffer ) > 0)
        {
            out.write( buffer );
        }

        out.close();
        in.close();
        return temp;
    }

    /**
     * Récupère l'image du challenge.
     * 
     * @return the image
     */
    public File getImage()
    {
        return image;
    }

    /**
     * Détruit de la mémoire l'image du challenge.
     */
    public void destroy()
    {
        this.image.delete();
    }

    /**
     * Solution du challenge.
     * 
     * @return Réponse à apporter au captcha pour la validation
     */
    public String getResponse()
    {
        return this.response;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((image == null) ? 0 : image.hashCode());
        result = prime * result + ((response == null) ? 0 : response.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof Captcha))
        {
            return false;
        }
        final Captcha other = (Captcha) obj;
        if (image == null)
        {
            if (other.image != null)
            {
                return false;
            }
        }
        else if (!image.equals( other.image ))
        {
            return false;
        }
        if (response == null)
        {
            if (other.response != null)
            {
                return false;
            }
        }
        else if (!response.equals( other.response ))
        {
            return false;
        }
        return true;
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
        builder.append( "Captcha [image=" );
        builder.append( image );
        builder.append( ", response=" );
        builder.append( response );
        builder.append( "]" );
        return builder.toString();
    }

}
