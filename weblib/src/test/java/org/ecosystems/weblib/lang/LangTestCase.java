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
package org.ecosystems.weblib.lang;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.ecosystems.lib.config.Configurator;
import org.ecosystems.lib.tools.StringTools;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests des fonctionnalités de langue.
 * 
 * @author tim288
 * 
 */
public class LangTestCase
{
    /**
     * Nom de la propriété système contenant le nom du fichier de configuration générale.
     */
    private static final String CONFIG_FILE_SYSTEM_PROP_NAME = "org.ecosystems.weblib.test.form.CONFIG_FILE";

    /**
     * Définit la configuration générale.
     * 
     * @throws ConfigurationException Levée si l'initailisatino de la configuration échoue.
     */
    @BeforeClass
    public static void setUpBeforeClass() throws ConfigurationException
    {

        final String configFileName = System.getProperty( CONFIG_FILE_SYSTEM_PROP_NAME );

        if (configFileName == null || configFileName.isEmpty())
        {
            throw new ConfigurationException( "La variable système " + CONFIG_FILE_SYSTEM_PROP_NAME
                    + " doit être définie avec un nom de fichier existant" );
        }

        // "src/test/resources/wlconfig.xml"
        final File configFile = new File( configFileName );
        final XMLConfiguration xmlConfig = new XMLConfiguration( configFile );
        Configurator.setConfig( xmlConfig );
    }

    /**
     * Teste simpliste du fonctionnement des traductions.
     * 
     * @throws NoSuchTraductionException Levée si la chaine n'est pas traduite
     * @throws NoSuchTraductionRessourceException Levée si la resource des langue est introuvable
     * @throws NoSuchLanguageConverterException Levée si le convertisseur de langues est introuvable
     * @throws UnsupportedEncodingException Levée si l'encodage n'est pas supporté
     */
    @Test
    public void testTrad() throws NoSuchTraductionException, NoSuchTraductionRessourceException, NoSuchLanguageConverterException,
            UnsupportedEncodingException
    {

        testPropertyEquals( "weblib", "test1", "I'm the test1 string!" );
        testPropertyEquals( "weblib", "accent1", "String with àéè accents!" );

    }

    /**
     * Récupère une chaîne de langue et vérifie qu'elle correspond bien à ce qui est attendu.
     * 
     * @param namespace Espace de nom de la traduction à tester
     * @param key clé de langue à tester
     * @param waited traduction attendue
     * 
     * @throws NoSuchLanguageConverterException Levée si le convertisseur de langues est introuvable
     * @throws NoSuchTraductionRessourceException Levée si la ressource de traduction est introuvable
     * @throws NoSuchTraductionException Levée si la traduction est introuvable
     */
    private void testPropertyEquals(final String namespace, final String key, final String waited) throws NoSuchTraductionException,
            NoSuchTraductionRessourceException, NoSuchLanguageConverterException
    {
        final String result = LanguageManager.getConvertor().getTraduction( namespace, key, Locale.FRENCH );
        assertTrue( StringTools.concat( "Chaine trouvée : \"", result, "\", Chaine attendue : \"", waited, "\"" ), waited.equals( result ) );
    }
}
