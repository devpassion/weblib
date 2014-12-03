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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.naming.ConfigurationException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import net.sourceforge.jwebunit.junit.WebTester;

import org.ecosystems.lib.tools.StringTools;
import org.ecosystems.weblib.html.form.config.generated.FieldValue;
import org.ecosystems.weblib.html.form.config.generated.FormScenario;
import org.ecosystems.weblib.html.form.config.generated.FormTest;
import org.ecosystems.weblib.html.form.config.generated.Link;
import org.ecosystems.weblib.html.form.config.generated.ScenarioSequence;
import org.ecosystems.weblib.html.form.config.generated.WaitedResult;
import org.ecosystems.weblib.log.LoggerAdapter;
import org.ecosystems.weblib.tools.FileTools;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Testeur de scénarios de formulaires.
 * 
 */
public abstract class FormScenarioTester
{

    private static final LoggerAdapter LOGGER_ADAPTER = new LoggerAdapter( FormScenarioTester.class );

    /**
     * Nom de la propriété système contenant le nom du fichier de configuration des tests de formulaires.
     */
    private static final String TEST_FORM_FILE_SYSTEM_PROP_NAME = "org.ecosystems.weblib.test.form.TEST_FORM_FILE";

    /**
     * Nom de la propriété système contenant les noms de dossiers de resources statiques à copier pour les snapshots séparées par des
     * virgules.
     */
    private static final String STATIC_RESOURCES_PROP_NAME = "org.ecosystems.weblib.test.form.STATIC_RESOURCES";

    /**
     * Chemin des snapshots.
     */
    private static Path htmlResults;

    /**
     * Configuration des tests de formulaires.
     */
    private static FormTest testFormConfig = null;

    /**
     * Définit l'environnement de tests.
     * 
     * @throws Exception Toute erreur
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        final String formTestConfigFileName = System.getProperty( TEST_FORM_FILE_SYSTEM_PROP_NAME );

        if (formTestConfigFileName == null || formTestConfigFileName.isEmpty())
        {
            throw new ConfigurationException( "La variable système " + TEST_FORM_FILE_SYSTEM_PROP_NAME
                    + " doit être définie avec un nom de fichier existant" );
        }

        // Configuration des tests
        final JAXBContext context = JAXBContext.newInstance( FormTest.class );
        final Unmarshaller unmarshaller = context.createUnmarshaller();
        final SchemaFactory sf = SchemaFactory.newInstance( javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI );
        final Schema schema = sf.newSchema( FormScenarioTester.class.getClassLoader().getResource( "FormTestXMLSchema.xsd" ) );
        unmarshaller.setSchema( schema );
        // "src/main/webapp/WEB-INF/formTestBeans.xml"
        final File testConfigFile = new File( formTestConfigFileName );
        if (!testConfigFile.exists())
        {
            throw new FileNotFoundException( formTestConfigFileName );
        }

        testFormConfig = (FormTest) unmarshaller.unmarshal( testConfigFile );

        initSnapShots();
    }

    private static String lastSnapShot = "";

    /**
     * Lance le test de scénarios.
     * 
     * @throws Exception Toute erreur levée par le test
     */
    @Test
    public void launch() throws Exception
    {
        for (final ScenarioSequence sequence : testFormConfig.getScenarioSequence())
        {
            this.testSenarioSequence( this.getNewTester(), sequence );
        }
    }

    /**
     * Récupère un nolouveau testeur web.
     * 
     * @return nouveau testeur web
     */
    protected abstract WebTester getNewTester();

    // protected abstract LoggerAdapter getLogger();

    /**
     * Lance une séquence de scénarios.
     * 
     * @param tester Testeur http
     * @param sequence Séquence telle que lue dans la configuration xml
     * @throws Exception Toute erreur
     */
    private void testSenarioSequence(final WebTester tester, final ScenarioSequence sequence) throws Exception
    {
        LOGGER_ADAPTER.info( "Test de la séquence ", sequence.getName(), " ..." );

        tester.beginAt( sequence.getBegin() );
        final List<FormScenario> sortedScenarios = new ArrayList<>();
        sortedScenarios.addAll( sequence.getFormScenario() );
        Collections.sort( sortedScenarios, new Comparator<FormScenario>()
        {
            @Override
            public int compare(final FormScenario o1, final FormScenario o2)
            {
                return o1.getSenarioIndex().shortValue() - o2.getSenarioIndex().shortValue();
            }
        } );

        for (final FormScenario scenario : sortedScenarios)
        {
            try
            {
                testFormInPage( tester, scenario, sequence.getName() );
            }
            catch (final AssertionError e)
            {
                LOGGER_ADAPTER.warn( "Assertion échouée au scénario " + StringTools.getStringOrNull( scenario.getSenarioIndex() )
                        + " de la séquence " + StringTools.getStringOrNull( sequence.getName() ) + "(dernier snapshot : " + lastSnapShot
                        + ")" + " : " + e.getMessage(), e );
                throw e;
            }
            catch (final Exception e)
            {
                throw new Exception( "Erreur au scénario " + StringTools.getStringOrNull( scenario.getSenarioIndex() ) + " de la séquence "
                        + StringTools.getStringOrNull( sequence.getName() ) + "(dernier snapshot : " + lastSnapShot + ")" + " : "
                        + e.getMessage(), e );
            }
        }
    }

    /**
     * Teste un simple scénario.
     * 
     * @param tester Testeur http
     * @param scenario Scénario tel que lu dans la configuration
     * @param sequenceName Nom de la séquence du scénario
     * @throws InterruptedException Levée si une pause dans le scénario est impossible
     * @throws IOException Lévée si un snapshot est impossible à écrire
     */
    private void testFormInPage(final WebTester tester, final FormScenario scenario, final String sequenceName)
            throws InterruptedException, IOException
    {

        LOGGER_ADAPTER.info( "Test du scenario ", scenario.getSenarioIndex(), " ..." );

        final StringBuilder message = new StringBuilder( "scénario : " );

        final String workingFormId = scenario.getWorkingFormId();

        if (workingFormId != null && !workingFormId.isEmpty())
        {
            tester.setWorkingForm( workingFormId );
        }

        for (final FieldValue fv : scenario.getFieldValue())
        {
            message.append( "[" + fv.getFieldName() + "=\"" + fv.getValue() + "\"]" );

            LOGGER_ADAPTER.trace( message.toString() );

            if (fv.getPreClick() != null)
            {
                tester.assertButtonPresent( fv.getPreClick().getId().getLocalPart() );

                tester.clickButton( fv.getPreClick().getId().getLocalPart() );

                final BigInteger delay = fv.getPreClick().getDelay();
                if (delay.intValue() > 0)
                {
                    Thread.sleep( delay.intValue() );
                }
            }

            LOGGER_ADAPTER.trace( fv.getFieldName(), " = ", fv.getValue() );

            switch (fv.getType())
            {
            case OPTION:
                tester.selectOptionByValue( fv.getFieldName(), fv.getValue().get( 0 ) );
                break;
            case FILE:

                final File toUpFile = new File( fv.getValue().get( 0 ) );
                if (!toUpFile.exists())
                    throw new FileNotFoundException( fv.getValue().get( 0 ) );

                // Create temp file.
                final File temp = File.createTempFile( toUpFile.getName(), "" );
                LOGGER_ADAPTER.debug( "création du fichier temporaire : ", toUpFile.getName() );
                // Delete temp file when program exits.
                temp.deleteOnExit();
                // Write to temp file
                final FileOutputStream fos = new FileOutputStream( temp );
                final FileInputStream fis = new FileInputStream( toUpFile );
                final byte[] buffer = new byte[1024];
                while (fis.read( buffer ) > 0)
                {
                    fos.write( buffer );
                }
                fis.close();
                fos.close();

                final String filename = temp.getAbsolutePath();

                tester.setTextField( fv.getFieldName(), filename );
                LOGGER_ADAPTER.trace( "file: définition du champ ", fv.getFieldName(), " à la valeur \"", filename, "\"" );
                break;
            case CHECKBOX:
                for (final String fvv : fv.getValue())
                {
                    tester.checkCheckbox( fv.getFieldName(), fvv );
                }
                break;
            case TEXT:
            default:
                tester.setTextField( fv.getFieldName(), fv.getValue().get( 0 ) );
                LOGGER_ADAPTER.trace( "file: définition du champ ", fv.getFieldName(), " à la valeur \"", fv.getValue(), "\"" );
            }

            if (fv.getPostClick() != null)
            {
                tester.assertButtonPresent( fv.getPostClick().getId().getLocalPart() );

                tester.clickButton( fv.getPostClick().getId().getLocalPart() );

                final BigInteger delay = fv.getPostClick().getDelay();
                if (delay.intValue() > 0)
                {
                    Thread.sleep( delay.intValue() );
                }
            }
        }
        final String snapshot = sequenceName + ".scenario_" + scenario.getSenarioIndex();

        makeSnapShot( tester, snapshot + "_before-submit" );

        if (scenario.getSubmit() != null)
        {
            tester.assertButtonPresent( scenario.getSubmit().getId().getLocalPart() );
            tester.clickButton( scenario.getSubmit().getId().getLocalPart() );

            if (scenario.getSubmit().getDelay() != null)
            {
                Thread.sleep( scenario.getSubmit().getDelay().intValue() );
            }

        }
        else
        {
            final Link link = scenario.getLink();
            tester.assertLinkPresent( link.getId().getLocalPart() );
            tester.clickLink( link.getId().getLocalPart() );

            if (link.getDelay() != null)
            {
                Thread.sleep( link.getDelay().longValue() );
            }
        }

        LOGGER_ADAPTER.info( "Ecriture du snapshot ", snapshot );
        makeSnapShot( tester, snapshot + "_after-submit" );
        for (final WaitedResult wr : scenario.getWaitedResults().getWaitedResult())
        {
            final String message2 = "Scénario " + StringTools.getStringOrNull( scenario.getSenarioIndex() ) + " "
                    + tester.getTestingEngine().getPageURL().toString() + "  snapshot:" + snapshot;
            switch (wr.getResultType())
            {
            case MATCH_URL:
                tester.assertMatch(
                        message.append( "\nmissing : " ).append( wr.getContent() ).append( "/ found : " )
                                .append( tester.getTestingEngine().getPageURL().toString() ).toString(), wr.getContent(), tester
                                .getTestingEngine().getPageURL().toString() );
                break;
            case NO_MATCH_URL:
                tester.assertNotMatch(
                        message.append( "\nnot missing : " ).append( wr.getContent() ).append( "/unexpected : " )
                                .append( tester.getTestingEngine().getPageURL().toString() ).toString(), wr.getContent(), tester
                                .getTestingEngine().getPageURL().toString() );
                break;

            case MATCH_SOURCE:
                tester.assertMatch( message2 + " / must match \"" + StringTools.getStringOrNull( wr.getContent() ) + "\"", wr.getContent(),
                        tester.getPageSource() );
                break;
            case NO_MATCH_SOURCE:
                tester.assertNotMatch( message2 + " / must no match \"" + StringTools.getStringOrNull( wr.getContent() ) + "\"",
                        wr.getContent(), tester.getPageSource() );
                break;
            default:
                throw new IllegalArgumentException( "Le type de résultat attendu " + StringTools.getStringOrNull( wr.getResultType() )
                        + " est invalide" );
            }
        }

    }

    /**
     * initialise les snapshots.
     * 
     * @throws IOException Levée si le dossier des snapshots est impossible à créer
     */
    private static void initSnapShots() throws IOException
    {

        final String staticResourcesBase = StringTools.getStringOrNull( System.getProperty( STATIC_RESOURCES_PROP_NAME ), "" );

        htmlResults = Paths.get( "target", "integration-results" );

        final Set<PosixFilePermission> perms = PosixFilePermissions.fromString( "rwxrwxr-x" );
        final FileAttribute<Set<PosixFilePermission>> attrs = PosixFilePermissions.asFileAttribute( perms );

        // new String[] { "img", "js", "css" };
        final String[] staticResources = staticResourcesBase.split( "," );

        if (!Files.exists( htmlResults ))
        {
            Files.createDirectories( htmlResults, attrs );
        }

        LOGGER_ADAPTER.info( "copie des resources statiques ", staticResourcesBase );

        for (final String ss : staticResources)
        {

            final Path from = Paths.get( "src/main/webapp", ss );
            final Path to = Paths.get( "target/integration-results", ss );
            Files.copy( from, to, StandardCopyOption.REPLACE_EXISTING );
        }
    }

    /**
     * Crée un snapshot.
     * 
     * @param tester Testeur http
     * @param name Nom du snapshot
     * @throws IOException Levée si le snapshot est impossible à écrire
     */
    protected void makeSnapShot(final WebTester tester, final String name) throws IOException
    {

        final Path file = Paths.get( htmlResults.toString(), name );
        if (file.toFile().exists())
        {
            file.toFile().delete();
        }
        FileTools.printStringToFile( file, tester.getPageSource() );
        lastSnapShot = file.toString();
    }

}
