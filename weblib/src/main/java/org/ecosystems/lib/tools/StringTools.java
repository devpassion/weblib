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
package org.ecosystems.lib.tools;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.ecosystems.weblib.log.LoggerAdapter;
import org.ecosystems.weblib.tools.Pair;

/**
 * Outils divers de manipulation de chaines.
 */
public final class StringTools
{
    private static final LoggerAdapter LOGGER_ADAPTER = new LoggerAdapter( StringTools.class );

    private StringTools()
    {
    }

    /**
     * Equivalent de join en php ( merci à stackOverfow ).
     * 
     * @param token séparateur
     * @param strings chaines à lier
     * @return chaine jointe
     */
    public static String join(final String token, final String[] strings)
    {
        if (strings.length == 0)
        {
            return ""; //$NON-NLS-1$
        }

        final StringBuffer sb = new StringBuffer();

        for (int x = 0; x < (strings.length - 1); x++)
        {
            sb.append( strings[x] );
            sb.append( token );
        }
        sb.append( strings[strings.length - 1] );

        return (sb.toString());
    }

    /**
     * Equivalent de join en php ( merci à stackOverfow ).
     * 
     * @param token séparateur
     * @param strings chaines à lier
     * @return chaine jointe, si le tableau est à null, la chaîne vide est renvoyée
     */
    public static String join(final String token, final Object[] strings)
    {
        if (strings == null || strings.length == 0)
        {
            return ""; //$NON-NLS-1$
        }

        final StringBuilder sb = new StringBuilder();

        for (int x = 0; x < (strings.length - 1); x++)
        {
            sb.append( strings[x] );
            sb.append( token );
        }
        sb.append( strings[strings.length - 1] );

        return (sb.toString());
    }

    /**
     * Equivalent de join en php ( merci à stackOverfow ).
     * 
     * @param token séparateur
     * @param strings chaines à lier
     * @return chaine jointe
     */
    public static String join(final String token, final List<?> strings)
    {
        if (strings.size() == 0)
        {
            return ""; //$NON-NLS-1$
        }

        final StringBuilder sb = new StringBuilder();

        for (int x = 0; x < (strings.size() - 1); x++)
        {
            sb.append( strings.get( x ) );
            sb.append( token );
        }
        sb.append( strings.get( strings.size() - 1 ) );

        return (sb.toString());
    }

    /**
     * Equivalent de join pour une {@link Map}.
     * 
     * @param <K> Type dés clés de la {@link Map}, placés dans la chaine résultante à droite du token
     * @param <V> Type dés valeurs de la {@link Map}, placés dans la chaine résultante à gauche du token
     * 
     * @param token séparateur
     * @param map map à joindre
     * @return chaine jointe
     */
    public static <K, V> String join(final String token, final Map<K, V> map)
    {
        if (map.size() == 0)
        {
            return "";
        }

        final StringBuilder sb = new StringBuilder();

        int i = 0;

        for (final Entry<K, V> entry : map.entrySet())
        {
            i++;
            sb.append( entry.getKey() ).append( "=" ).append( entry.getValue() );
            if (i < map.size())
            {
                sb.append( token );
            }
        }

        return (sb.toString());
    }

    /**
     * Récupère le toString d'un objet ou une chaine si null.
     * 
     * @param object Objet à transformer en chaine
     * @param nullString Chaine à afficher si l'objet est null
     * @return toString de l'objet, ou NullString si l'objet est null
     */
    public static String getStringOrNull(final Object object, final String nullString)
    {
        return (object == null) ? nullString : object.toString();
    }

    /**
     * Récupère le toString d'un objet ou une chaine si null.
     * 
     * @param object Objet à transformer en chaine
     * @return toString de l'objet, ou "null" si l'objet est null
     */
    public static String getStringOrNull(final Object object)
    {
        return StringTools.getStringOrNull( object, "null" ); //$NON-NLS-1$
    }

    /**
     * Récupère une concaténation de chaines ( utilise {@link StringBuilder} ).
     * 
     * @param strings chaines à concaténer
     * @return Chaine concaténée
     */
    public static String concat(final String... strings)
    {
        final StringBuilder sb = new StringBuilder();
        for (final String s : strings)
        {
            sb.append( s );
        }
        return sb.toString();
    }

    /**
     * Renvoie une chaîne dont la première lettre est en majuscule.
     * 
     * @param str Chaîne à traiter
     * @return chaîne avec le premier caractère en majuscule
     */
    public static String firstUpper(final String str)
    {
        if (str.length() < 2)
        {
            throw new IllegalArgumentException( StringTools.concat( "StringTools.firstUpper(", str, //$NON-NLS-1$
                    ") : La chaine doit contenir au moins 2 caractères" ) ); //$NON-NLS-1$
        }
        return new StringBuilder( str.substring( 0, 1 ).toUpperCase( Locale.ENGLISH ) ).append( str.substring( 1 ) ).toString();
    }

    /**
     * Transforme un nom de propriété en accesseur ( ie : "property" devient "getProperty()" ).
     * 
     * @param property propriété
     * @return Chaîne représentant l'accesseur
     */
    public static String toGetter(final String property)
    {
        return new StringBuilder().append( "get" ).append( firstUpper( property ) ).toString(); //$NON-NLS-1$
    }

    /**
     * Transforme un nom de propriété en mutateur ( ie : "property" devient "setProperty()" ).
     * 
     * @param property propriété
     * @return Chaîne représentant le mutateur
     */
    public static String toSetter(final String property)
    {
        return new StringBuilder().append( "set" ).append( firstUpper( property ) ).toString(); //$NON-NLS-1$
    }

    /**
     * Extrait la clé et l'index d'une chaine du type 'key[index]' et renvoie (key, index)
     * 
     * @param str Chaine à parser
     * @return paire du type (key, index), si le parsing est impossible, la chaine entière est considérée comme clé. Si la chaine est vide
     *         ou null, null est renvoyé
     */
    public static Pair<String> extractKey(final String str)
    {
        LOGGER_ADAPTER.trace( "extraction de la chaine '" + str + "'" );
        final int bi = str.indexOf( "[" );
        if (bi == -1)
            return new Pair<String>( str, "" );

        final Pair<String> out = new Pair<String>( str.substring( 0, bi ), (bi < str.length() - 1) ? str.substring( bi + 1,
                str.length() - 1 ) : "" );
        return out;
    }
}
