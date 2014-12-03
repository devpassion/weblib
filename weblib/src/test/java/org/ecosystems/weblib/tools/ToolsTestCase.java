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
/**
 * 
 */
package org.ecosystems.weblib.tools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.ecosystems.lib.tools.StringTools;
import org.junit.Test;

/**
 * @author tim288
 * 
 */
public class ToolsTestCase
{
    /**
     * Teste les outils de gestion de tableaux.
     */
    @Test
    public void testArrayTools()
    {
        // final String[] one = { "one", "two", "tree" };
        //
        // final String[] two = { "one2", "two2", "tree2" };
        //
        // final String[] waitingResult = { "one", "two", "tree", "one2", "two2", "tree2" };
        // final String[] result = ArrayTools.concat( String.class, one, two );
        //
        // final String resultStr = StringTools.join( ",", result );
        //
        // assertTrue( "Le tableau résultant n'a pas le bon nombre d'éléments : " + resultStr, result.length == 6 );
        //
        // assertTrue( "Le tableau résultant n'est pas le résultat attendu : " + resultStr, Arrays.equals( waitingResult, result ) );

        arrayTestCase( String.class, new String[] { "one", "two", "tree", "one2", "two2", "tree2" }, new String[] { "one", "two", "tree" },
                new String[] { "one2", "two2", "tree2" } );

        arrayTestCase( Integer.class, new Integer[] { 1, 2, 3, 11, 12, 13, 21, 22, 23 }, new Integer[] { 1, 2, 3 }, new Integer[] { 11, 12,
                13 }, new Integer[] { 21, 22, 23 } );

    }

    private <T> void arrayTestCase(final Class<T> clazz, final T[] waitedArray, @SuppressWarnings("unchecked") final T[]... arrays)
    {
        final T[] result = ArrayTools.concat( clazz, arrays );

        final String resultStr = StringTools.join( ",", result );

        final String flag = "Attendu : " + StringTools.join( ",", waitedArray ) + "; resultat : " + resultStr;

        assertTrue( "Le tableau résultant n'a pas le bon nombre d'éléments : " + resultStr + "; " + flag,
                result.length == waitedArray.length );

        assertTrue( "Le tableau résultant n'est pas le résultat attendu : " + resultStr + "; " + flag, Arrays.equals( waitedArray, result ) );
    }

    /**
     * Teste le remplacement des paramètres dans les URL.
     * 
     * @throws MalformedURLException Levée si une URL est mal formée
     */
    @Test
    public void testHttpQueryReplace() throws MalformedURLException
    {
        testHttpQueryReplace( "http://www.domain.com/file/file2/page.html?query1=one&query2=two",
                "http://www.domain.com/file/file2/page.html?query1=one&query2=replace", "query2", "replace" );

        testHttpQueryReplace( "http://www.domain.com/file/file2/page.html", "http://www.domain.com/file/file2/page.html?query2=replace",
                "query2", "replace" );

        // String url = "http://www.domain.com/file/file2/page.html?query1=one&query2=two";
        // URL result = HttpTools.replaceInQuery(new URL(url), "query2", "replace");

        // assertTrue("url invalide : " + result.toString(),
        // "http://www.domain.com/file/file2/page.html?query1=one&query2=replace".equals(result.toString()) );

    }

    private void testHttpQueryReplace(final String url, final String waited, final String param, final String replace)
            throws MalformedURLException
    {
        final URL result = HttpTools.replaceInQuery( new URL( url ), param, replace );

        assertTrue( "url invalide : " + result.toString(), waited.equals( result.toString() ) );
    }

    @Test
    public void testExtractKey()
    {
        final Map<String, Pair<String>> tests = new HashMap<String, Pair<String>>();

        tests.put( "key[value]", new Pair<String>( "key", "value" ) );
        tests.put( "key[[[value]", new Pair<String>( "key", "[[value" ) );
        tests.put( "key[value", new Pair<String>( "key", "valu" ) );
        tests.put( "key[value]pouet", new Pair<String>( "key", "value]poue" ) );
        tests.put( "key[", new Pair<String>( "key", "" ) );
        tests.put( "tlang", new Pair<String>( "tlang", "" ) );
        tests.put( "", new Pair<String>( "", "" ) );

        for (final Entry<String, Pair<String>> e : tests.entrySet())
        {
            assertEquals( e.getValue(), StringTools.extractKey( e.getKey() ) );
        }
    }
}
