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
package org.ecosystems.weblib.tools;

import javax.servlet.ServletException;
import javax.servlet.jsp.JspException;

import org.ecosystems.weblib.log.LoggerAdapter;

/**
 * Outils relatifs à la reflexion.
 */
public final class ClassTools
{
    private ClassTools()
    {
    }

    /**
     * Charge une classe, la classe doit posséder un constructeur par défaut accessible.
     * 
     * @param <T> Type que doit hériter la classe chargée
     * @param logAdapter {@link LoggerAdapter} de la classe appellant la méthode
     * @param className nom de la classe à charger
     * @return instance de la classe chargée
     * @throws JspException Levée si le chargement est impossible, si la classe est d'un type incompatible avec T ou si
     *         la classe ne peut pas être instanciée
     */
    @SuppressWarnings("unchecked")
    public static <T> T loadClassInJsp(final LoggerAdapter logAdapter, final String className) throws JspException
    {
        Class<? extends T> c;
        try
        {
            c = (Class<? extends T>) Thread.currentThread().getContextClassLoader().loadClass( className );
        }
        catch (ClassNotFoundException e)
        {
            throw logAdapter.logAndReturnJspException( "Chargement de classe échoué", e ); //$NON-NLS-1$
        }
        catch (ClassCastException e)
        {
            throw logAdapter.logAndReturnJspException( "Erreur de cast : ", e ); //$NON-NLS-1$
        }

        try
        {
            return c.newInstance();
        }
        catch (InstantiationException e)
        {
            throw logAdapter.logAndReturnJspException( e );
        }
        catch (IllegalAccessException e)
        {
            throw logAdapter.logAndReturnJspException( e );
        }
    }

    /**
     * Charge une classe, la classe doit posséder un constructeur par défaut accessible.
     * 
     * @param <T> Type que doit hériter la classe chargée
     * @param logAdapter {@link LoggerAdapter} de la classe appellant la méthode
     * @param className nom de la classe à charger
     * @return instance de la classe chargée
     * @throws ServletException Levée si le chargement est impossible, si la classe est d'un type incompatible avec T ou
     *         si la classe ne peut pas être instanciée
     */
    @SuppressWarnings("unchecked")
    public static <T> T loadClassInServlet(final LoggerAdapter logAdapter, final String className) throws ServletException
    {
        Class<? extends T> c;
        try
        {
            c = (Class<? extends T>) Thread.currentThread().getContextClassLoader().loadClass( className );
        }
        catch (ClassNotFoundException e)
        {
            throw logAdapter.logAndReturnServletException( "Chargement de classe échoué", e ); //$NON-NLS-1$
        }
        catch (ClassCastException e)
        {
            throw logAdapter.logAndReturnServletException( "Erreru de cast : ", e ); //$NON-NLS-1$
        }

        try
        {
            return c.newInstance();
        }
        catch (InstantiationException e)
        {
            throw logAdapter.logAndReturnServletException( e );
        }
        catch (IllegalAccessException e)
        {
            throw logAdapter.logAndReturnServletException( e );
        }
    }

    /**
     * Charge une classe, la classe doit posséder un constructeur par défaut accessible.
     * 
     * @param <T> Type que doit hériter la classe chargée
     * @param logAdapter {@link LoggerAdapter} de la classe appellant la méthode
     * @param className nom de la classe à charger
     * @return instance de la classe chargée
     * @throws ClassNotFoundException Levée si la classe est introuvable ( loggée )
     * @throws InstantiationException Levée si la classe est impossible à instantier ( loggée )
     * @throws IllegalAccessException Levée si la classe est inaccessible ( loggée )
     */
    @SuppressWarnings("unchecked")
    public static <T> T loadClass(final LoggerAdapter logAdapter, final String className) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException
    {
        Class<? extends T> c;
        try
        {
            c = (Class<? extends T>) Thread.currentThread().getContextClassLoader().loadClass( className );
        }
        catch (ClassNotFoundException e)
        {
            logAdapter.fatal( "Chargement de classe échoué", e ); //$NON-NLS-1$
            throw e;
        }
        catch (ClassCastException e)
        {
            logAdapter.fatal( "Erreur de cast", e ); //$NON-NLS-1$
            throw e;
        }

        try
        {
            return c.newInstance();
        }
        catch (InstantiationException e)
        {
            logAdapter.fatal( e );
            throw e;
        }
        catch (IllegalAccessException e)
        {
            logAdapter.fatal( e );
            throw e;
        }
    }
}
