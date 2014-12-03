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
package org.ecosystems.weblib.sql;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.ecosystems.lib.config.Configurator;
import org.ecosystems.weblib.log.LoggerAdapter;

/**
 * Gestionnaire de pool de connection.
 */
public final class PoolManager
{
    private static LoggerAdapter logAdapter = new LoggerAdapter( PoolManager.class );

    private static DataSource ds;

    private PoolManager()
    {
    }

    /**
     * Initialise le manager et effectue un test de connection.
     * 
     * @throws NamingException Levée si une ressource est introuvable
     */
    public static void init() throws NamingException
    {
        Context initCtx;
        logAdapter.info( "Tentative de chargement de ressource jdbc" ); //$NON-NLS-1$
        initCtx = new InitialContext();
        final String url = Configurator.getString( Configurator.JDBC.NAME );
        logAdapter.debug( "Ressource Jdbc Chargée avec succès" ); //$NON-NLS-1$
        logAdapter.debug( "url : ", url ); //$NON-NLS-1$
        ds = (DataSource) initCtx.lookup( url );
    }

    /**
     * Récupère une connection SQL.
     * 
     * @return connection SQL
     * @throws SQLException Levée si le SGBD renvoie une erreur
     */
    public static Connection getConnection() throws SQLException
    {
        logAdapter.debug( "getConnection..." ); //$NON-NLS-1$
        final Connection out = ds.getConnection();
        logAdapter.trace( "connection handled" ); //$NON-NLS-1$
        return out;
    }

}
