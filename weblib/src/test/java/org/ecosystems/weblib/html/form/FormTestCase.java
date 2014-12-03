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
package org.ecosystems.weblib.html.form;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.ecosystems.lib.config.Configurator;
import org.ecosystems.lib.tools.StringTools;
import org.ecosystems.weblib.html.form.config.BadFormBeanClassException;
import org.ecosystems.weblib.html.form.config.FormContext;
import org.ecosystems.weblib.html.form.config.FormContextFactoryImpl;
import org.ecosystems.weblib.html.form.config.InitFormException;
import org.ecosystems.weblib.test.ForgedHttpServletRequest;
import org.ecosystems.weblib.tools.Pair;
import org.ecosystems.weblib.tools.RandomTools;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests divers sur les formulaires.
 */
public class FormTestCase
{

    /**
     * Nom de la propriété système contenant le nom du fichier de configuration générale.
     */
    private static final String CONFIG_FILE_SYSTEM_PROP_NAME = "org.ecosystems.weblib.test.form.CONFIG_FILE";

    /**
     * Nom de la propriété système contenant le nom du fichier de configuration de formulaire.
     */
    private static final String FORM_CONFIG_FILE_SYSTEM_PROP_NAME = "org.ecosystems.weblib.test.form.FORM_CONFIG_FILE";

    /**
     * Charge la configuration générale et la configuration de formulaire.
     * 
     * @throws FileNotFoundException Levée si un fhcier de configuration est introuvable
     * @throws ConfigurationException Levée en cas d'ereur de configuration
     * @throws InitFormException Levée si l'initailisation des formulaires échoue
     * @throws BadFormBeanClassException Levée si un bean de formulaire ne correspond pas à un nom de formulaire
     */
    @BeforeClass
    public static void setUpBeforeClass() throws FileNotFoundException, ConfigurationException, InitFormException,
            BadFormBeanClassException
    {

        final String configFileName = System.getProperty( CONFIG_FILE_SYSTEM_PROP_NAME );
        final String formConfigFileName = System.getProperty( FORM_CONFIG_FILE_SYSTEM_PROP_NAME );

        if (configFileName == null || configFileName.isEmpty())
        {
            throw new ConfigurationException( "La variable système " + CONFIG_FILE_SYSTEM_PROP_NAME
                    + " doit être définie avec un nom de fichier existant" );
        }

        if (formConfigFileName == null || formConfigFileName.isEmpty())
        {
            throw new ConfigurationException( "La variable système " + FORM_CONFIG_FILE_SYSTEM_PROP_NAME
                    + " doit être définie avec un nom de fichier existant" );
        }

        // File configFile = new File( "src/test/resources/wlconfig.xml" );
        final XMLConfiguration xmlConfig = new XMLConfiguration( configFileName );
        Configurator.setConfig( xmlConfig );

        // File configFile2 = new File( "src/test/resources/testForm.xml" );
        FormContextFactoryImpl.getProvider().loadConfig( new File( formConfigFileName ) );
    }

    /**
     * Pas de traitement de fin.
     * 
     * @throws Exception Toute erreur levée
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }

    /**
     * Crée un bean de formulaire et vérifie que tous ces champs sont valides.
     * 
     * @throws NoSuchFormException Levée si le formulaire est introuvable
     * @throws BadFormBeanClassException Levée si le bean de formulaire n'est pas du type demandé
     * @throws InitFormException Levée si l'initialisation de la configuration de formulaire échoue
     */
    @Test
    public final void test() throws NoSuchFormException, InitFormException, BadFormBeanClassException
    {
        final Party party = new Party( "test - Fête du boudin", "17 rue Paul bellamy", new BigDecimal( 0 ), new BigDecimal( 0 ),
                java.sql.Date.valueOf( "2012-01-12" ), "Venez nombreux à la fête du boudin!", "mail@mail.com", "marcel",
                Time.valueOf( "17:30:00" ) );

        final Map<String, String> parameters = new HashMap<>();
        parameters.put( "title", party.getTitle() );
        parameters.put( "address", party.getAddress() );
        parameters.put( "date", "01/12/2012" );
        parameters.put( "lat", "0" );
        parameters.put( "lng", "0" );
        parameters.put( "time", "17:30" );
        parameters.put( "name", "marcel" );
        parameters.put( "description", party.getDescription() );
        parameters.put( "mail", party.getMail() );
        parameters.put( FormContext.POSTED_KEY_NAME, "party" );

        final Map<String, String> parameters2 = new HashMap<>();
        parameters2.put( "title", party.getTitle() );
        parameters2.put( "address", party.getAddress() );
        parameters2.put( "date", "0112/2012" );
        parameters2.put( "lat", "0" );
        parameters2.put( "lng", "0" );
        parameters2.put( "time", "1730" );
        parameters2.put( "name", "marcel" );
        parameters2.put( "description", party.getDescription() );
        parameters2.put( "mail", "INVALID_MAIL<>" );
        parameters2.put( FormContext.POSTED_KEY_NAME, "party" );

        final ForgedHttpServletRequest request = new ForgedHttpServletRequest( "post", parameters );
        final ForgedHttpServletRequest request2 = new ForgedHttpServletRequest( "post", parameters2 );

        final FormContext<Party> context = FormContextFactoryImpl.getProvider().getFormContext( "party", Party.class, request );
        final FormContext<Party> context2 = FormContextFactoryImpl.getProvider().getFormContext( "party", Party.class, request2 );

        final Party formParty = context.getBean();
        final Party formParty2 = context2.getBean();

        final StringBuilder sb = new StringBuilder();
        sb.append( "Bean initial : " );
        sb.append( StringTools.getStringOrNull( party ) );
        sb.append( "\n" );
        sb.append( "Bean de formulaire : " );
        sb.append( StringTools.getStringOrNull( formParty ) );
        sb.append( "\n" );
        sb.append( "Bean de formulaire erroné : " );
        sb.append( StringTools.getStringOrNull( formParty2 ) );
        sb.append( "\n" );

        assertTrue( "Le bean de formulaire ne correspond pas\n" + sb.toString(), party.equals( context.getBean() ) );
        assertFalse( "Le bean du second formulaire ne doit pas correspondre\n" + sb.toString(), party.equals( context2.getBean() ) );
        assertTrue( "Le formulaire n'est pas considéré posté\n" + sb.toString(), context2.getFieldContexts().get( "mail" ).isPosted() );

        assertFalse( "mail doit être invalide", context2.getFieldContexts().get( "mail" ).isValid() );
        assertFalse( "date doit être invalide", context2.getFieldContexts().get( "date" ).isValid() );
        assertFalse( "time doit être invalide", context2.getFieldContexts().get( "time" ).isValid() );
        assertTrue( "name doit être valide", context2.getFieldContexts().get( "name" ).isValid() );

    }

    /**
     * Teste la bonne validité d'une série de valeurs.
     * 
     * @throws BadFormBeanClassException Levée si le bean de formulaire n'est pas du type demandé
     * @throws InitFormException Levée si l'initialisation de la configuration de formulaire échoue
     */
    @Test
    public final void fullTest() throws InitFormException, BadFormBeanClassException
    {
        final Party party = new Party( "test - Fête du boudin", "17 rue Paul bellamy", new BigDecimal( 0 ), new BigDecimal( 0 ),
                java.sql.Date.valueOf( "2012-01-12" ), "Venez nombreux à la fête du boudin!", "mail@mail.com", "marcel",
                Time.valueOf( "17:30:00" ) );

        final Map<String, String> vParameters1 = new HashMap<>();
        vParameters1.put( "title", party.getTitle() );
        vParameters1.put( "address", party.getAddress() );
        vParameters1.put( "date", "01/12/2012" );
        vParameters1.put( "lat", "0" );
        vParameters1.put( "lng", "0" );
        vParameters1.put( "time", "17:30" );
        vParameters1.put( "name", "marcel" );
        vParameters1.put( "description", party.getDescription() );
        vParameters1.put( "mail", party.getMail() );

        final Map<String, String> vParameters2 = new HashMap<>();
        vParameters2.put( "title", party.getTitle() );
        vParameters2.put( "address", party.getAddress() );

        vParameters2.put( "lat", "0" );
        vParameters2.put( "lng", "0" );

        vParameters2.put( "name", "marcel" );
        vParameters2.put( "description", party.getDescription() );

        final Map<String, String> iparameters2 = new HashMap<>();
        iparameters2.put( "date", "0112/2012" );
        iparameters2.put( "time", "1730" );
        iparameters2.put( "mail", "INVALID_MAIL<>" );

        new FormCase<>( "party", Party.class, party, vParameters1, null ).test();
        new FormCase<>( "party", Party.class, party, vParameters2, iparameters2 ).test();

    }

    /**
     * Teste la bonne validité une série de valeurs de façon aléatoire.
     * 
     * @throws BadFormBeanClassException Levée si le bean de formulaire n'est pas du type demandé
     * @throws InitFormException Levée si l'initialisation de la configuration de formulaire échoue
     */
    @Test
    public final void randomTest() throws InitFormException, BadFormBeanClassException
    {
        final Map<String, Pair<Collection<String>>> values = new Hashtable<>();
        values.put( "title", new Pair<Collection<String>>( Arrays.asList( "test - fête du boudin", "sfqsdfsdf" ), new ArrayList<String>() ) );

        values.put( "address", new Pair<Collection<String>>( Arrays.asList( "sfqsdfsdf" ), new ArrayList<String>() ) );
        values.put( "description", new Pair<Collection<String>>( Arrays.asList( "sfqsdfsdf" ), new ArrayList<String>() ) );
        values.put( "name", new Pair<Collection<String>>( Arrays.asList( "sfqsdfsdf" ), new ArrayList<String>() ) );

        values.put( "date",
                new Pair<Collection<String>>( Arrays.asList( "01/01/2012", "12/15/2011" ), Arrays.asList( "22/01/2012", "15/00/2011" ) ) );

        values.put(
                "time",
                new Pair<Collection<String>>( Arrays.asList( "17:30", "23:59", "00:00" ), Arrays.asList( "1730", "0", "17:60", "24:00",
                        "-13" ) ) );

        values.put( "date",
                new Pair<Collection<String>>( Arrays.asList( "01/01/2012", "12/15/2011" ), Arrays.asList( "22/01/2012", "15/00/2011" ) ) );

        values.put(
                "lat",
                new Pair<Collection<String>>( Arrays.asList( "-45.26598", "12", "00.00", "4567.1564" ), Arrays.asList( "0,13",
                        "15/00/2011", "789+123", "sdqdqd" ) ) );

        values.put(
                "lng",
                new Pair<Collection<String>>( Arrays.asList( "-45.26598", "12", "00.00", "4567.1564" ), Arrays.asList( "0,13",
                        "15/00/2011", "789+123", "sdqdqd" ) ) );

        values.put(
                "mail",
                new Pair<Collection<String>>( Arrays.asList( "mail@mail.com", "my.prov@my.mail.org.com",
                        "shitMail@mail.myfuckmail.omc.com.com" ), Arrays.asList( "INVALID<<", "badmailàmail.com", "<script>",
                        "I'm not a mail" ) ) );

        for (int k = 0; k < 20; k++)
        {
            FormCase.testAll( "party", Party.class, values );
        }

    }

    /**
     * Cas de test de formulaire.
     * 
     * 
     * @param <T> Type de bean du formulaire
     */
    private static class FormCase<T>
    {
        private final String formName;

        private final Class<T> beanClass;

        private final T initialBean;

        private final Hashtable<String, String> validValues = new Hashtable<>();

        private final Hashtable<String, String> inValidValues = new Hashtable<>();

        /**
         * Teste la validité (resp. non validité) d'une série de valeurs prises au hasard dans une liste.
         * 
         * @param <U> Classe du bean de formulaire
         * @param formName Nom du formulaire à tester
         * @param beanClass Classe du bean de formulaire
         * @param values Valeurs à tester, la clé est le nom de champ, la paire contient en first les valeurs valides,
         *        en second les valeurs invalides possible.
         * 
         * @throws BadFormBeanClassException Levée si le bean de formulaire n'est pas du type demandé
         * @throws InitFormException Levée si l'initialisation de la configuration de formulaire échoue
         * 
         */
        public static <U> void testAll(final String formName, final Class<U> beanClass, final Map<String, Pair<Collection<String>>> values)
                throws InitFormException, BadFormBeanClassException
        {
            Hashtable<String, String> validValues1;
            Hashtable<String, String> inValidValues1;

            final int size = values.size();

            for (int i = 0; i < size; i++)
            {

                validValues1 = new Hashtable<>();
                inValidValues1 = new Hashtable<>();

                final Set<String> fieldNames = new HashSet<>();
                fieldNames.addAll( values.keySet() );

                String winnerField;
                String winnerValue;
                for (int j = 0; j < i; j++)
                {
                    winnerField = RandomTools.getRandomElement( fieldNames );
                    winnerValue = RandomTools.getRandomElement( values.get( winnerField ).getFirst() );
                    validValues1.put( winnerField, winnerValue );
                    fieldNames.remove( winnerField );

                }

                for (int k = i; k < (size - i); k++)
                {
                    winnerField = RandomTools.getRandomElement( fieldNames );

                    final Collection<String> invalids = values.get( winnerField ).getSecond();
                    if (invalids.isEmpty())
                    {
                        winnerValue = RandomTools.getRandomElement( values.get( winnerField ).getFirst() );
                        validValues1.put( winnerField, winnerValue );
                    }
                    else
                    {
                        winnerValue = RandomTools.getRandomElement( invalids );
                        inValidValues1.put( winnerField, winnerValue );
                    }

                    fieldNames.remove( winnerField );
                }

                new FormCase<>( formName, beanClass, null, validValues1, inValidValues1 ).test();
            }

        }

        /**
         * Teste la validité (resp. non validité) d'une série de valeurs prises au hasard dans une liste.
         * 
         * @param formName Nom du formulaire à tester
         * @param beanClass Classe du bean de formulaire
         * @param initialBean Bean dont les champs doivent correspondre aux valeurs valides
         * @param validValues Valeurs valides à tester, la clé est le nom de champ, la valeur est la valeur
         * @param inValidValues Valeurs invalides à tester, la clé est le nom de champ, la valeur est la valeur
         * 
         */
        public FormCase(final String formName, final Class<T> beanClass, final T initialBean, final Map<String, String> validValues,
                final Map<String, String> inValidValues)
        {
            super();
            this.formName = formName;
            this.beanClass = beanClass;
            this.initialBean = initialBean;
            if (validValues != null)
            {
                this.validValues.putAll( validValues );
            }

            if (inValidValues != null)
            {
                this.inValidValues.putAll( inValidValues );
            }
        }

        public void test() throws InitFormException, BadFormBeanClassException
        {

            final Map<String, String> parameters = new Hashtable<>();
            parameters.putAll( this.validValues );
            parameters.putAll( this.inValidValues );
            parameters.put( FormContext.POSTED_KEY_NAME, this.formName );
            final boolean mustBeValid = (this.inValidValues.isEmpty());

            final ForgedHttpServletRequest request = new ForgedHttpServletRequest( "post", parameters );

            final FormContext<T> context = FormContextFactoryImpl.getProvider().getFormContext( this.formName, this.beanClass, request );

            final T formBean = context.getBean();

            final StringBuilder sb = new StringBuilder();
            sb.append( "Comparaison de beans  : " );
            sb.append( "bean initial : " );
            sb.append( this.initialBean );
            sb.append( "bean de formulaire : " );
            sb.append( formBean.toString() );

            if (this.initialBean != null)
            {
                if (mustBeValid)
                {
                    assertTrue( sb.append( "\nLes beans ne correspondent pas" ).toString(), this.initialBean.equals( formBean ) );
                }
                else
                {
                    assertFalse( sb.append( "\nLes beans ne doivent pas correspondre" ).toString(), this.initialBean.equals( formBean ) );
                }
            }

            for (final Entry<String, String> e : this.validValues.entrySet())
            {
                assertTrue( e.getKey() + " doit être valide (valeur = " + StringTools.getStringOrNull( e.getValue() ) + ")", context
                        .getFieldContexts().get( e.getKey() ).isValid() );
            }

            for (final Entry<String, String> e : this.inValidValues.entrySet())
            {
                assertFalse( e.getKey() + " doit être valide (valeur = " + StringTools.getStringOrNull( e.getValue() ) + ")", context
                        .getFieldContexts().get( e.getKey() ).isValid() );
            }
        }
    }

}
