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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.ecosystems.lib.tools.StringTools;
import org.ecosystems.weblib.log.LoggerAdapter;

/**
 * Objet fournissant les paramètres de requête au contexte de formulaire.
 * 
 */
public class RequestValueProvider
{

    private static final LoggerAdapter LOGGER_ADAPTER = new LoggerAdapter( RequestValueProvider.class );

    private final Map<String, List<String>> fieldsMap = new HashMap<>();

    private final Map<String, File> filesMap = new HashMap<>();

    private final HttpServletRequest request;

    /**
     * Nouveau fournisseur de apramètres à partir d'une requête.
     * 
     * L'objet est rempli en fonction du type MIME de la requête.
     * 
     * @param request requête
     * @throws IOException Levée si erreur IO
     * @throws FileUploadException Levée si une erreur empêche l'upload du fichier
     */
    public RequestValueProvider(final HttpServletRequest request) throws FileUploadException, IOException
    {
        this( request, true );
    }

    public RequestValueProvider(final HttpServletRequest request, final boolean parseFiles) throws FileUploadException, IOException
    {

        this.request = request;
        LOGGER_ADAPTER.trace( "build rvp ..." );
        if (ServletFileUpload.isMultipartContent( request ))
        {
            // Create a new file upload handler
            final ServletFileUpload upload = new ServletFileUpload();

            // Parse the request
            FileItemIterator iter;

            iter = upload.getItemIterator( request );
            LOGGER_ADAPTER.trace( "parse upload request " );
            while (iter.hasNext())
            {
                final FileItemStream item = iter.next();
                final String name = item.getFieldName();
                final InputStream stream = item.openStream();
                if (item.isFormField())
                {
                    final String asString = Streams.asString( stream );
                    LOGGER_ADAPTER.trace( "add field ", name, " = ", asString );

                    // fieldsMap.put( name, asString );
                    this.addOnFieldMap( name, asString );
                }
                else if (parseFiles)
                {
                    Streams.checkFileName( item.getName() );
                    // final String name2 = item.getName();

                    final File f = File.createTempFile( "upload", item.getName() );
                    final FileOutputStream fos = new FileOutputStream( f );

                    final byte[] buffer = new byte[1024];
                    while (stream.read( buffer ) > 0)
                    {
                        fos.write( buffer );
                    }
                    stream.close();
                    fos.close();
                    filesMap.put( name, f );
                }
            }
        }
        else
        {
            LOGGER_ADAPTER.trace( "parse normal request " );
            // fieldsMap.putAll( request.getParameterMap() );
            @SuppressWarnings("unchecked")
            final Enumeration<String> e = request.getParameterNames();
            String n;
            while (e.hasMoreElements())
            {
                n = e.nextElement();
                LOGGER_ADAPTER.trace( "add field ", n, " = ", StringTools.join( ",", request.getParameterValues( n ) ) );
                this.addOnFieldMap( n, request.getParameterValues( n ) );
                // fieldsMap.put( n, (String) request.getParameter( n ) );
            }
        }
    }

    /**
     * Indique si un paramètre est envoyé dans la requete sauf si il s'agit d'un fichier.
     * 
     * @param name nom du paramètre
     * @return vrai si il est envoyé (même vide), faux sinon
     */
    public boolean hasParameter(final String name)
    {
        return fieldsMap.containsKey( name );
    }

    /**
     * Récupère un paramètre non fichier.
     * 
     * Si le paramètre n'est pas multivalué, le paramètre String upcasté en objet est renvoyé.
     * Si le paramètre est multivalué, un tableau de String upcasté en objet est renvoyé.
     * 
     * @param name nom du paramètre
     * @return paramètre, ou null si le nom n'existe pas
     */
    public Object getParameter(final String name)
    {

        final List<String> list = fieldsMap.get( name );
        if (list == null)
        {
            return null;
        }
        if (list.size() < 2)
        {
            return list.get( 0 );
        }
        return list.toArray();

    }

    /**
     * Indique si un fichier est envoyé dans la requête.
     * 
     * @param fieldName nom du paramètre contenant le fichier
     * @return vrai si un fichier est envoyé, faux sinon
     */
    public boolean hasFile(final String fieldName)
    {
        return filesMap.containsKey( fieldName );
    }

    /**
     * Récupère un fichier temporaire corespodant à un fichier evoyé dans la requête.
     * 
     * @param fieldName nom du fichier.
     * @return fichier temporaire, ou null si il n'existe pas
     */
    public File getFile(final String fieldName)
    {
        return filesMap.get( fieldName );
    }

    public void setSessionValue(final String name, final Object value)
    {
        this.request.getSession().setAttribute( name, value );
    }

    public Object getSessionValue(final String name)
    {
        return this.request.getSession().getAttribute( name );
    }

    @Override
    public String toString()
    {
        return this.fieldsMap.toString();
    }

    private void addOnFieldMap(final String name, final String value)
    {
        if (!this.fieldsMap.containsKey( name ))
        {
            this.fieldsMap.put( name, new ArrayList<>( Arrays.asList( value ) ) );
        }
        else
        {
            final List<String> list = this.fieldsMap.get( name );
            if (!list.contains( value ))
            {
                list.add( value );
            }
        }
    }

    private void addOnFieldMap(final String name, final String[] value)
    {
        this.fieldsMap.put( name, Arrays.asList( value ) );

    }

    public HttpServletRequest getRequest()
    {
        return this.request;
    }
}
