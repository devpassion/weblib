package org.ecosystems.weblib.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Code emprunté à http://www.servlets.com/soapbox/filters.html.
 * 
 * 
 */
public class TimerFilter implements Filter
{

    /**
     * Nom du paramètre du filtre indiquant le nom du paramètre de session indiquant le temps de réponse des pages.
     */
    public static final String RESPONSE_TIME_ATTRIBUTE_NAME = "org.ecosystems.weblib.filter.TimerFilter.RESPONSE_TIME_ATTRIBUTE_NAME";

    /**
     * Configuration du filtre.
     */
    private FilterConfig config0 = null;

    @Override
    public void init(final FilterConfig config) throws ServletException
    {
        this.config0 = config;
    }

    @Override
    public void destroy()
    {
        this.config0 = null;
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException,
            ServletException
    {

        final long before = System.currentTimeMillis();

        chain.doFilter( request, response );

        final long after = System.currentTimeMillis();

        final long time = after - before;

        String name = "";

        request.setAttribute( RESPONSE_TIME_ATTRIBUTE_NAME, Long.valueOf( time ) );

        if (request instanceof HttpServletRequest)
        {
            name = ((HttpServletRequest) request).getRequestURI();

        }

        this.config0.getServletContext().log( name + ": " + time + "ms" );
    }
}
