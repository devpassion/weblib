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
package org.ecosystems.weblib.html.debug;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.ecosystems.lib.tools.StringTools;
import org.ecosystems.weblib.log.LoggerAdapter;

/**
 * Tag affichant les paramètres de contexte.
 */
public class DebugParamsTag extends TagSupport
{
    private static final LoggerAdapter LOGGER_ADAPTER = new LoggerAdapter( DebugParamsTag.class );

    private static final long serialVersionUID = -3249730925015576739L;

    private String devIP = null;

    @Override
    public int doStartTag() throws JspException
    {

        final String remoteAddr = this.pageContext.getRequest().getRemoteAddr();
        LOGGER_ADAPTER.debug( "ip cliente : ", remoteAddr );
        LOGGER_ADAPTER.trace( "ip dev : ", this.devIP );

        if (this.devIP != null && !remoteAddr.equals( this.devIP ) && !remoteAddr.startsWith( "127." ))
        {
            LOGGER_ADAPTER.debug( "message non affiché" );
            return super.doStartTag();
        }

        final Map<String, Integer> scopes = new HashMap<String, Integer>();

        scopes.put( "PAGE_SCOPE", Integer.valueOf( PageContext.PAGE_SCOPE ) ); //$NON-NLS-1$
        // scopes.put( "APPLICATION_SCOPE", PageContext.APPLICATION_SCOPE );
        scopes.put( "REQUEST_SCOPE", Integer.valueOf( PageContext.REQUEST_SCOPE ) ); //$NON-NLS-1$
        scopes.put( "SESSION_SCOPE", Integer.valueOf( PageContext.SESSION_SCOPE ) ); //$NON-NLS-1$

        final String[] sysInfos = new String[] { "java.compiler", "java.class.version", "java.home", "java.version", "java.vendor",
                "java.class.path" };

        final StringBuilder out = new StringBuilder();

        for (String entry : sysInfos)
        {
            out.append( entry + " = " + System.getProperty( entry ) );
            out.append( "<br />" );
        }

        String key, val;
        for (Entry<String, Integer> e : scopes.entrySet())
        {

            out.append( "SCOPE : " + e.getKey() + "<br />" ); //$NON-NLS-1$ //$NON-NLS-2$
            final Enumeration<String> en = this.pageContext.getAttributeNamesInScope( e.getValue().intValue() );

            while (en.hasMoreElements())
            {
                key = en.nextElement();
                val = StringTools.concat( key, " = ", StringTools.getStringOrNull( //$NON-NLS-1$
                        this.pageContext.findAttribute( key ) ), "<br />" ); //$NON-NLS-1$
                out.append( val );
            }
        }
        try
        {
            this.pageContext.getOut().print( out.toString() );
        }
        catch (IOException e1)
        {
            throw new JspException( e1 );
        }
        return Tag.SKIP_BODY;
    }

    /**
     * Récupère l'IP de la machine qui recevra les informations de débug en bas de page.
     * 
     * @return the devIP
     */
    public String getDevIP()
    {
        return this.devIP;
    }

    /**
     * Définit l'IP de la machine qui recevra les informations de débug en bas de page.
     * 
     * @param devIP the devIP to set
     */
    public void setDevIP(final String devIP)
    {
        this.devIP = devIP;
    }
}
