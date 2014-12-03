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

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.ecosystems.weblib.log.LoggerAdapter;

/**
 * Fournisseur de configuration SQl de test.
 */
public final class TestConfigProvider
{
    private TestConfigProvider()
    {
    }

    private static final LoggerAdapter LOGGER_ADAPTER = new LoggerAdapter( TestConfigProvider.class );

    /**
     * Récupère une connection SQL de test dans un fichier de configuration xml.
     * 
     * Les paramètres du fichier de configuration sont les suivants :
     * jdbc-driver : driver jdbc
     * sql_host : hote sql
     * sql_port : port de connection sql
     * sql_db : base de données
     * sql_user : user sql
     * sql_password : mot de passe sql
     * 
     * @param configFileName Nom du fichier de configuration
     * @return Connection
     * @throws FileNotFoundException Levée si le fichier de configuration est introuvable
     * @throws ConfigurationException Levée si la configuration n'est pas chargée
     * @throws SQLException Levée si la connection échoue
     */
    public static Connection getConnectionString(final String configFileName) throws FileNotFoundException, ConfigurationException,
            SQLException
    {
        final File file = new File( configFileName );
        if (!file.exists())
            throw new FileNotFoundException( configFileName );
        final XMLConfiguration config = new XMLConfiguration( file );
        final StringBuilder jdbc = new StringBuilder( "jdbc:" );
        jdbc.append( config.getString( "jdbc-driver", "postgresql" ) );
        jdbc.append( "://" );
        jdbc.append( config.getString( "sql_host", "localhost" ) );
        jdbc.append( ":" );
        jdbc.append( config.getInteger( "sql_port", 5432 ) );
        jdbc.append( "/" );
        jdbc.append( config.getString( "sql_db" ) );
        LOGGER_ADAPTER.info( "connection de test : ", jdbc.toString(), "user : ", config.getString( "sql_user" ) );
        return DriverManager.getConnection( jdbc.toString(), config.getString( "sql_user" ), config.getString( "sql_password" ) );
    }
}
