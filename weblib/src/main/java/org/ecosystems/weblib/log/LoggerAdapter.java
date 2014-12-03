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
package org.ecosystems.weblib.log;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.ecosystems.lib.tools.StringTools;

/**
 * Facilités de logging.
 */
public class LoggerAdapter
{
    private final Class<?> clazz;

    /**
     * Nouvel adapteur de logs.
     * 
     * @param clazz Classe loggant les messages
     */
    public LoggerAdapter(final Class<?> clazz)
    {
        this.clazz = clazz;
    }

    /**
     * Récupère le logger.
     * 
     * @return Logger de cet adapteur
     */
    public Logger getLogger()
    {
        return Logger.getLogger( this.clazz );
    }

    /**
     * Logge un ensemble d'erreurs, en vérifiant au préalable que le niveau est suffisant.
     * 
     * @param level Niveau d'arreurs à logger
     * @param messages messages à logger, une référence à null provoque l'affichage de la chaine "null"
     */
    public void log(final Level level, final Object... messages)
    {
        if (this.getLogger().isEnabledFor( level ))
        {
            final StringBuilder sb = new StringBuilder();
            for (final Object o : messages)
            {
                sb.append( StringTools.getStringOrNull( o ) );
            }
            this.getLogger().log( level, sb.toString() );
        }
    }

    /**
     * Logge une collection, en vérifiant au préalable que le niveau est suffisant.
     * 
     * @param level Niveau d'erreurs à logger
     * @param collection collection à logger, une référence à null provoque l'affichage de la chaine "null"
     */
    public void log(final Level level, final Collection<?> collection)
    {
        if (this.getLogger().isEnabledFor( level ))
        {
            final StringBuilder sb = new StringBuilder();
            for (final Object o : collection)
            {
                sb.append( StringTools.getStringOrNull( o ) );
            }
            this.getLogger().log( level, sb.toString() );
        }
    }

    /**
     * Logge puis lève une erreur de type {@link JspException}.
     * 
     * @param message message d'erreur
     * @param t cause de l'erreur, loggée telle quelle puis considérée comme cause de la {@link JspException} levée
     * @return Exception {@link JspException} avec l'exception loggée comme cause
     */
    public JspException logAndReturnJspException(final String message, final Throwable t)
    {
        this.getLogger().log( Level.FATAL, message, t );
        return new JspException( message, t );
    }

    /**
     * Logge puis lève une erreur de type {@link JspException}.
     * 
     * 
     * @param t cause de l'erreur, loggée telle quelle puis considérée comme cause de la {@link JspException} levée
     * @return Exception {@link JspException} avec l'exception loggée comme cause
     */
    public JspException logAndReturnJspException(final Throwable t)
    {
        this.logAndReturnJspException( "", t ); //$NON-NLS-1$
        return new JspException( t );
    }

    /**
     * Logge puis retourne une erreur de type {@link JspException}.
     * 
     * @param message message d'erreur
     * @return Exception {@link JspException} avec l'exception loggée comme cause
     */
    public JspException logAndReturnJspException(final String message)
    {
        this.getLogger().log( Level.FATAL, message );
        return new JspException( message );
    }

    /**
     * Logge puis lève une erreur de type {@link JspException}.
     * 
     * @param message message d'erreur
     * @param t cause de l'erreur, loggée telle quelle puis considérée comme cause de la {@link JspException} levée
     * @return Exception {@link ServletException} avec l'exception loggée comme cause
     */
    public ServletException logAndReturnServletException(final String message, final Throwable t)
    {
        this.getLogger().log( Level.FATAL, message, t );
        return new ServletException( message, t );
    }

    /**
     * Logge puis lève une erreur de type {@link JspException}.
     * 
     * @param t cause de l'erreur, loggée telle quelle puis considérée comme cause de la {@link JspException} levée
     * @return Exception {@link ServletException} avec l'exception loggée comme cause
     */
    public ServletException logAndReturnServletException(final Throwable t)
    {
        this.logAndReturnJspException( "", t ); //$NON-NLS-1$
        return new ServletException( t );
    }

    /**
     * Logge puis retourne une erreur de type {@link JspException}.
     * 
     * @param message message d'erreur
     * @return Exception {@link ServletException} avec l'exception loggée comme cause
     */
    public ServletException logAndReturnServletException(final String message)
    {
        this.getLogger().log( Level.FATAL, message );
        return new ServletException( message );
    }

    /**
     * Logge un ensemble d'erreurs de type TRACE.
     * 
     * @param messages messages à logger
     */
    public void trace(final Object... messages)
    {
        this.log( Level.TRACE, messages );
    }

    /**
     * Logge un ensemble d'erreurs de type DEBUG.
     * 
     * @param messages messages à logger
     */
    public void debug(final Object... messages)
    {
        this.log( Level.DEBUG, messages );
    }

    /**
     * Logge un ensemble d'erreurs de type INFO.
     * 
     * @param messages messages à logger
     */
    public void info(final Object... messages)
    {
        this.log( Level.INFO, messages );
    }

    /**
     * Logge un ensemble d'erreurs de type WARN.
     * 
     * @param messages messages à logger
     */
    public void warn(final Object... messages)
    {
        this.log( Level.WARN, messages );
    }

    /**
     * Logge un ensemble d'erreurs de type ERROR.
     * 
     * @param messages messages à logger
     */
    public void error(final Object... messages)
    {
        this.log( Level.ERROR, messages );
    }

    /**
     * Logge un ensemble d'erreurs de type FATAL.
     * 
     * @param messages messages à logger
     */
    public void fatal(final Object... messages)
    {
        this.log( Level.FATAL, messages );
    }

    /**
     * Logge un message de niveau WARN avec une exception.
     * 
     * @param message message
     * @param t Exception à logger
     */
    public void warn(final Object message, final Throwable t)
    {
        this.log( Level.WARN, message, t );
    }

    /**
     * Logge un message de niveau ERROR avec une exception.
     * 
     * @param message message
     * @param t Exception à logger
     */
    public void error(final Object message, final Throwable t)
    {
        this.log( Level.ERROR, message, t );
    }

    /**
     * Logge un message de niveau FATAL avec une exception.
     * 
     * @param message message
     * @param t Exception à logger
     */
    public void fatal(final Object message, final Throwable t)
    {
        this.log( Level.FATAL, message, t );
    }

    /**
     * Logge les paramètres et attributs d'un contexte (niveau TRACE).
     * 
     * @param pageContext contexte
     */
    public void logRequestParameters(final PageContext pageContext)
    {
        final Enumeration<?> en = pageContext.getRequest().getParameterNames();
        while (en.hasMoreElements())
        {
            final String pName = (String) en.nextElement();
            this.trace( pName, "=", pageContext.getRequest().getParameter( pName ) ); //$NON-NLS-1$
        }

        final Enumeration<?> en1 = pageContext.getRequest().getAttributeNames();

        while (en1.hasMoreElements())
        {
            final String name = (String) en1.nextElement();
            this.trace( name, "=", StringTools.getStringOrNull( //$NON-NLS-1$
                    pageContext.getRequest().getAttribute( name ) ) );
        }
    }

    /**
     * Logge les paramètres et attributs d'un contexte (niveau TRACE).
     * 
     * @param request requete dont les paramètres doivent être tracés
     */
    public void logRequestParameters(final HttpServletRequest request)
    {
        final Enumeration<?> en = request.getParameterNames();
        while (en.hasMoreElements())
        {
            final String pName = (String) en.nextElement();
            this.trace( pName, "=", request.getParameter( pName ) ); //$NON-NLS-1$
        }

        final Enumeration<?> en1 = request.getAttributeNames();

        while (en1.hasMoreElements())
        {
            final String name = (String) en1.nextElement();
            this.trace( name, "=", StringTools.getStringOrNull( request.getAttribute( name ) ) ); //$NON-NLS-1$
        }
    }

    /**
     * Logge l'ensemble des champs d'un objet par introspection.
     * 
     * @param level Niveau de log ( TRACE conseillé )
     * @param o objet à inspecter par introspection
     */
    public void logObjectProperties(final Level level, final Object o)
    {
        if (this.getLogger().isEnabledFor( level ))
        {
            if (o == null)
            {
                this.log( level, "null" ); //$NON-NLS-1$
                return;
            }

            final Field[] fields = o.getClass().getDeclaredFields();

            if (fields.length == 0)
            {
                this.log( level, "no fields" ); //$NON-NLS-1$
            }

            for (int i = 0; i < fields.length; i++)
            {
                try
                {
                    this.log( level, fields[i].getName() + " - " + fields[i].get( o ) ); //$NON-NLS-1$
                }
                catch (final IllegalArgumentException e)
                {
                    this.logAndReturnJspException( e );
                }
                catch (final IllegalAccessException e)
                {
                    this.logAndReturnJspException( e );
                }
            }
        }
    }

}
