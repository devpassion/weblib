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
package org.ecosystems.lib.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;

/**
 * This is class provides {@link java.sql.Array} interface for PostgreSQL <code>text</code> array.
 * 
 * @author Valentine Gogichashvili
 */

public class PostgreSQLTextArray implements java.sql.Array
{

    private final Object[] stringArray;

    private final String stringValue;

    /**
     * Initializing constructor.
     * 
     * @param stringArray Array of stringables objects
     */
    public PostgreSQLTextArray(final Object[] stringArray)
    {
        this.stringArray = stringArray;
        this.stringValue = stringArrayToPostgreSQLTextArray( this.stringArray );

    }

    @Override
    public String toString()
    {
        return this.stringValue;
    }

    private static final String NULL = "NULL";

    /**
     * This static method can be used to convert an string array to string representation of PostgreSQL text array.
     * 
     * @param stringArray a source String array
     * @return string representation of a given text array
     */
    public static String stringArrayToPostgreSQLTextArray(final Object[] stringArray)
    {
        if (stringArray == null)
        {
            return NULL;
        }

        if (stringArray.length == 0)
        {
            return "{}";
        }

        final int arrayLength = stringArray.length;
        // count the string length and if need to quote
        int neededBufferLentgh = 2; // count the beginning '{' and the ending '}' brackets
        final boolean[] shouldQuoteArray = new boolean[stringArray.length];
        for (int si = 0; si < arrayLength; si++)
        {
            // count the comma after the first element
            if (si > 0)
                neededBufferLentgh++;

            boolean shouldQuote;
            final String s = stringArray[si].toString();
            if (s == null)
            {
                neededBufferLentgh += 4;
                shouldQuote = false;
            }
            else
            {
                final int l = s.length();
                neededBufferLentgh += l;
                if (l == 0 || s.equalsIgnoreCase( NULL ))
                {
                    shouldQuote = true;
                }
                else
                {
                    shouldQuote = false;
                    // scan for commas and quotes
                    for (int i = 0; i < l; i++)
                    {
                        final char ch = s.charAt( i );
                        switch (ch)
                        {
                        case '"':
                        case '\\':
                            shouldQuote = true;
                            // we will escape these characters
                            neededBufferLentgh++;
                            break;
                        case ',':
                        case '\'':
                        case '{':
                        case '}':
                            shouldQuote = true;
                            break;
                        default:
                            if (Character.isWhitespace( ch ))
                            {
                                shouldQuote = true;
                            }
                            break;
                        }
                    }
                }
                // count the quotes
                if (shouldQuote)
                    neededBufferLentgh += 2;
            }
            shouldQuoteArray[si] = shouldQuote;
        }

        // construct the String
        final StringBuilder sb = new StringBuilder( neededBufferLentgh );
        sb.append( '{' );
        for (int si = 0; si < arrayLength; si++)
        {
            final String s = stringArray[si].toString();
            if (si > 0)
                sb.append( ',' );
            if (s == null)
            {
                sb.append( NULL );
            }
            else
            {
                final boolean shouldQuote = shouldQuoteArray[si];
                if (shouldQuote)
                    sb.append( '"' );
                // for (int i = 0, l = s.length(); i < l; i++)
                final int l = s.length();
                for (int i = 0; i < l; i++)
                {
                    final char ch = s.charAt( i );
                    if (ch == '"' || ch == '\\')
                        sb.append( '\\' );
                    sb.append( ch );
                }
                if (shouldQuote)
                    sb.append( '"' );
            }
        }
        sb.append( '}' );
        assert sb.length() == neededBufferLentgh;
        return sb.toString();
    }

    @Override
    public Object getArray() throws SQLException
    {
        return this.stringArray == null ? null : Arrays.copyOf( this.stringArray, this.stringArray.length );
    }

    @Override
    public Object getArray(final Map<String, Class<?>> map) throws SQLException
    {
        return getArray();
    }

    @Override
    public Object getArray(final long index, final int count) throws SQLException
    {
        return this.stringArray == null ? null : Arrays.copyOfRange( this.stringArray, (int) index, (int) index + count );
    }

    @Override
    public Object getArray(final long index, final int count, final Map<String, Class<?>> map) throws SQLException
    {
        return getArray( index, count );
    }

    @Override
    public int getBaseType() throws SQLException
    {
        return java.sql.Types.VARCHAR;
    }

    @Override
    public String getBaseTypeName() throws SQLException
    {
        return "text";
    }

    @Override
    public ResultSet getResultSet() throws SQLException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResultSet getResultSet(final Map<String, Class<?>> map) throws SQLException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResultSet getResultSet(final long index, final int count) throws SQLException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResultSet getResultSet(final long index, final int count, final Map<String, Class<?>> map) throws SQLException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void free() throws SQLException
    {
        //
    }

}
