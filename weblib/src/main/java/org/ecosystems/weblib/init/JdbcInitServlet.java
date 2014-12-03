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
package org.ecosystems.weblib.init;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.ecosystems.lib.config.Configurator;
import org.ecosystems.lib.tools.StringTools;
import org.ecosystems.weblib.log.LoggerAdapter;
import org.ecosystems.weblib.sql.PoolManager;

/**
 * Servlet de test de la connection JDBC.
 */
public class JdbcInitServlet extends HttpServlet
{
    private static LoggerAdapter logAdapter = new LoggerAdapter( JdbcInitServlet.class );

    private static final long serialVersionUID = 8957632455523645430L;

    @Override
    public void init() throws ServletException
    {
        try
        {
            PoolManager.init();
        }
        catch (NamingException e)
        {
            throw new ServletException( StringTools.concat(
                    "Impossible de trouver la ressource nommée du paramètre ", Configurator.JDBC.NAME ), e ); //$NON-NLS-1$
        }

        catch (Exception e)
        {
            throw new ServletException( e );
        }

        Connection connection = null;
        try
        {

            connection = PoolManager.getConnection();
            connection.close();
            logAdapter.info( "Test de connection SQL réussi" ); //$NON-NLS-1$
        }
        catch (SQLException e)
        {
            throw new ServletException( "Erreur de connection SQL", e ); //$NON-NLS-1$
        }
    }
}
