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
package org.ecosystems.weblib.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.apache.commons.lang.NotImplementedException;

/**
 * Requete crée pour tests.
 * 
 */
@SuppressWarnings({ "deprecation" })
public class ForgedHttpServletRequest implements HttpServletRequest
{
    private final Hashtable<String, String> parameters = new Hashtable<>();

    private final Hashtable<String, Object> attributes = new Hashtable<>();

    private String encoding;

    private int contentLenght;

    private final String contentType = "application/x-www-form-urlencoded";

    private String protocol;

    private String scheme;

    private String serverName;

    private int serverPort;

    private String queryString;

    private final String method;

    /**
     * Crée une nouvelle requête forgée avec paramètres GET.
     * 
     * @param method Méthode de la requête
     * 
     * @param parameters Paramètres http de la requête
     */
    public ForgedHttpServletRequest(final String method, final Map<String, String> parameters)
    {
        super();
        this.method = method;
        this.parameters.putAll( parameters );
    }

    @Override
    public Object getAttribute(final String name)
    {
        return this.attributes.get( name );
    }

    @Override
    public Enumeration<String> getAttributeNames()
    {
        return this.attributes.keys();
    }

    @Override
    public String getCharacterEncoding()
    {
        return this.encoding;
    }

    @Override
    public void setCharacterEncoding(final String env) throws UnsupportedEncodingException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public int getContentLength()
    {
        return this.contentLenght;
    }

    @Override
    public String getContentType()
    {
        return this.contentType;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException
    {
        return null;
    }

    @Override
    public String getParameter(final String name)
    {
        return this.parameters.get( name );
    }

    @Override
    public Enumeration<String> getParameterNames()
    {
        return this.parameters.keys();
    }

    @Override
    public String[] getParameterValues(final String name)
    {
        final String[] out = new String[1];
        out[0] = this.getParameter( name );
        return out;
    }

    @Override
    public Map<String, String> getParameterMap()
    {
        return this.parameters;
    }

    @Override
    public String getProtocol()
    {
        return this.protocol;
    }

    @Override
    public String getScheme()
    {
        return this.scheme;
    }

    @Override
    public String getServerName()
    {
        return this.serverName;
    }

    @Override
    public int getServerPort()
    {
        return this.serverPort;
    }

    @Override
    public BufferedReader getReader() throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getRemoteAddr()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getRemoteHost()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setAttribute(final String name, final Object o)
    {
        this.attributes.put( name, o );
    }

    @Override
    public void removeAttribute(final String name)
    {
        this.attributes.remove( name );
    }

    @Override
    public Locale getLocale()
    {
        return Locale.FRANCE;
    }

    @Override
    public Enumeration<Locale> getLocales()
    {
        return new Enumeration<Locale>()
        {
            private final Locale[] locales = { Locale.FRANCE, Locale.ENGLISH };
            private short cnt = 0;

            @Override
            public boolean hasMoreElements()
            {
                return locales.length < cnt++;
            }

            @Override
            public Locale nextElement()
            {
                return locales[cnt];
            }

        };
    }

    @Override
    public boolean isSecure()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(final String path)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Deprecated
    @Override
    public String getRealPath(final String path)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getRemotePort()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getLocalName()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getLocalAddr()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getLocalPort()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getAuthType()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Cookie[] getCookies()
    {
        return null;
    }

    @Override
    public long getDateHeader(final String name)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getHeader(final String name)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Enumeration<String> getHeaders(final String name)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Enumeration<String> getHeaderNames()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getIntHeader(final String name)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getMethod()
    {
        return this.method;
    }

    @Override
    public String getPathInfo()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPathTranslated()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getContextPath()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getQueryString()
    {
        return this.queryString;
    }

    @Override
    public String getRemoteUser()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isUserInRole(final String role)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Principal getUserPrincipal()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getRequestedSessionId()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getRequestURI()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StringBuffer getRequestURL()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getServletPath()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HttpSession getSession(final boolean create)
    {
        throw new NotImplementedException();
    }

    @Override
    public HttpSession getSession()
    {
        return new HttpSession()
        {

            @Override
            public void setMaxInactiveInterval(final int interval)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void setAttribute(final String name, final Object value)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void removeValue(final String name)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void removeAttribute(final String name)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void putValue(final String name, final Object value)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public boolean isNew()
            {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void invalidate()
            {
                // TODO Auto-generated method stub

            }

            @Override
            public String[] getValueNames()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Object getValue(final String name)
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public HttpSessionContext getSessionContext()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public ServletContext getServletContext()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public int getMaxInactiveInterval()
            {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public long getLastAccessedTime()
            {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public String getId()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public long getCreationTime()
            {
                // TODO Auto-generated method stub
                return 0;
            }

            @SuppressWarnings("rawtypes")
            @Override
            public Enumeration getAttributeNames()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Object getAttribute(final String name)
            {
                // TODO Auto-generated method stub
                return null;
            }
        };
    }

    @Override
    public boolean isRequestedSessionIdValid()
    {
        throw new NotImplementedException();
    }

    @Override
    public boolean isRequestedSessionIdFromCookie()
    {
        throw new NotImplementedException();
    }

    @Override
    public boolean isRequestedSessionIdFromURL()
    {
        throw new NotImplementedException();
    }

    @Deprecated
    @Override
    public boolean isRequestedSessionIdFromUrl()
    {
        throw new NotImplementedException();
    }

}
