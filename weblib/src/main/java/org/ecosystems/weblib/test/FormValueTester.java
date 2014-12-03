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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.ConfigurationException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.XMLConfiguration;
import org.ecosystems.lib.config.Configurator;
import org.ecosystems.lib.tools.StringTools;
import org.ecosystems.weblib.html.form.ConversionException;
import org.ecosystems.weblib.html.form.Converter;
import org.ecosystems.weblib.html.form.InputValidator;
import org.ecosystems.weblib.html.form.config.BadFormBeanClassException;
import org.ecosystems.weblib.html.form.config.FormContextFactoryImpl;
import org.ecosystems.weblib.html.form.config.InitFormException;
import org.ecosystems.weblib.html.form.config.generated.Field;
import org.ecosystems.weblib.html.form.config.generated.FieldValues;
import org.ecosystems.weblib.html.form.config.generated.Form;
import org.ecosystems.weblib.html.form.config.generated.FormTest;
import org.ecosystems.weblib.html.form.config.generated.FormValues;
import org.ecosystems.weblib.html.form.config.generated.Forms;
import org.ecosystems.weblib.html.form.config.generated.Validator;
import org.ecosystems.weblib.log.LoggerAdapter;
import org.ecosystems.weblib.tools.ClassTools;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Classe de base des testeurs de valeurs de formulaire.
 * Ce test peut être effectué sans intégration. Dans ce cas on utilise seulement la section
 * 
 * Il faut toutefois initialiser le contexte avec la méthode {@link FormValueTester#setAllConfig(String, String, String)}.
 * 
 * 
 */
public class FormValueTester
{

    /**
     * Adapteur de logs.
     */
    private static final LoggerAdapter LOGGER_ADAPTER = new LoggerAdapter( FormValueTester.class );

    /**
     * Nom de la propriété système contenant le nom du fichier de configuration générale.
     */
    private static final String CONFIG_FILE_SYSTEM_PROP_NAME = "org.ecosystems.weblib.test.form.CONFIG_FILE";

    /**
     * Nom de la propriété système contenant le nom du fichier de configuration de formulaire.
     */
    private static final String FORM_CONFIG_FILE_SYSTEM_PROP_NAME = "org.ecosystems.weblib.test.form.FORM_CONFIG_FILE";

    /**
     * Nom de la propriété système contenant le nom du fichier de configuration des tests de formulaires.
     */
    private static final String TEST_FORM_FILE_SYSTEM_PROP_NAME = "org.ecosystems.weblib.test.form.TEST_FORM_FILE";

    /**
     * Configuration du testeur de formulaires.
     */
    private static FormTest testFormConfig = null;

    /**
     * Configuration de formulaires.
     */
    private static Forms formsConfig = null;

    /**
     * Initialise les tests.
     * 
     * @throws Exception Toute erreur
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        final String configFileName = System.getProperty( CONFIG_FILE_SYSTEM_PROP_NAME );
        final String formConfigFileName = System.getProperty( FORM_CONFIG_FILE_SYSTEM_PROP_NAME );
        final String formTestConfigFileName = System.getProperty( TEST_FORM_FILE_SYSTEM_PROP_NAME );

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

        if (formTestConfigFileName == null || formTestConfigFileName.isEmpty())
        {
            throw new ConfigurationException( "La variable système " + TEST_FORM_FILE_SYSTEM_PROP_NAME
                    + " doit être définie avec un nom de fichier existant" );
        }

        // setAllConfig("src/test/resources/formTestBeans.xml", "src/test/resources/testForm.xml", "src/test/resources/wlconfig.xml");
        setAllConfig( formTestConfigFileName, formConfigFileName, configFileName );

    }

    /**
     * Défini toute la configuration de l'environnement.
     * 
     * @param testFileName Fichier de configuration des tests de formulaires
     * @param formFileName Fichier de configuration des formulaires
     * @param configFileName Fichier de configuration générale
     * @throws org.apache.commons.configuration.ConfigurationException Levée si une erreur apparait dans le chargement de la configuration
     *         générale
     * @throws BadFormBeanClassException Levée si un Bean de formulaire est invalide
     * @throws InitFormException Levée si une initialisation de configuration de formulaires échoue
     * @throws FileNotFoundException Levée si un fichier est introuvable
     * @throws JAXBException Levée si une désérialisation échoue
     */
    public static void setAllConfig(final String testFileName, final String formFileName, final String configFileName)
            throws org.apache.commons.configuration.ConfigurationException, FileNotFoundException, InitFormException,
            BadFormBeanClassException, JAXBException
    {

        // Configuration générale
        final Configuration config = new XMLConfiguration( new File( configFileName ) );
        Configurator.setConfig( config );

        // Configuration de formulaires
        FormContextFactoryImpl.getProvider().loadConfig( new File( formFileName ) );
        formsConfig = FormContextFactoryImpl.getProvider().getConfig();

        // Configuration des tests
        final JAXBContext context = JAXBContext.newInstance( FormTest.class );
        final Unmarshaller unmarshaller = context.createUnmarshaller();
        // "src/main/webapp/WEB-INF/formTestBeans.xml"
        final File testConfigFile = new File( testFileName );
        if (!testConfigFile.exists())
        {
            throw new FileNotFoundException( testFileName );
        }

        testFormConfig = (FormTest) unmarshaller.unmarshal( testConfigFile );

    }

    /**
     * Lance le test.
     * 
     * @throws Exception Levée pour toute erreur aux tests
     */
    @Test
    public void launch() throws Exception
    {
        for (final FormValues form : testFormConfig.getFormValues())
        {
            Form formConfig = null;

            for (final Form ff : formsConfig.getForm())
            {
                if (ff.getFormName().equals( form.getFormName() ))
                    formConfig = ff;
            }
            if (formConfig == null)
            {
                throw new ConfigurationException( "La configuration du formulaire " + form.getFormName() + " est introuvable" );
            }

            this.testFormValues( form, formConfig );
        }
    }

    private void testFormValues(final FormValues form, final Form formConfig) throws FileNotFoundException, InitFormException,
            BadFormBeanClassException, ClassNotFoundException, InstantiationException, IllegalAccessException, ConversionException
    {

        // final Map<String, String> parameters = new HashMap<>();

        String fieldName;

        for (final FieldValues f : form.getFieldValues())
        {
            fieldName = f.getFieldName();
            final Field field = getFieldConfig( fieldName, formConfig );
            final List<InputValidator> ivs = getinputValidators( field );
            final Converter<?> converter = ClassTools.loadClass( LOGGER_ADAPTER, field.getConverter() );
            if (f.getValidValues() != null)
            {
                for (final String vv : f.getValidValues().getValidValue())
                {
                    for (final InputValidator iv : ivs)
                        assertTrue( StringTools.concat( f.getFieldName(), " = ", vv, " (invalid for rule " + iv.ruleName()
                                + ", must be valid)" ), iv.validate( vv ) );
                    converter.convert( vv );
                }
            }
            if (f.getBadValues() != null)
            {
                for (final String vv : f.getBadValues().getBadValue())
                {
                    for (final InputValidator iv : ivs)
                        assertFalse( StringTools.concat( f.getFieldName(), " = ", vv, " (valid for rule " + iv.ruleName()
                                + ", must be invalid)" ), iv.validate( vv ) );
                }
            }
        }

        // ForgedHttpServletRequest request = new ForgedHttpServletRequest(parameters);

        // FormContextFactoryImpl.getProvider().loadConfig(new File("WEB-INF/forms.xml"));

        // FormContextFactoryImpl.getProvider().getFormContext(form.getFormName(), , request);

    }

    private List<InputValidator> getinputValidators(final Field fieldConfig) throws ClassNotFoundException, InstantiationException,
            IllegalAccessException
    {
        final List<InputValidator> out = new ArrayList<>();
        if (fieldConfig.getValidators() == null)
            return out;
        InputValidator validator;
        for (final Validator v : fieldConfig.getValidators().getValidator())
        {
            validator = ClassTools.loadClass( LOGGER_ADAPTER, v.getValidatorClass() );
            out.add( validator );
        }
        return out;
    }

    private Field getFieldConfig(final String fieldName, final Form fc)
    {
        for (final Field f : fc.getField())
        {
            if (fieldName.equals( f.getName() ))
                return f;
        }
        throw new IllegalArgumentException( "Le champ " + StringTools.getStringOrNull( fieldName )
                + "n'est pas présent dans la configuration de formulaire" );
    }

    // private <T> void infferFormContext( final String formName, final ServletRequest request, final Class<T> clazz )
    // throws InitFormException, BadFormBeanClassException
    // {
    // final FormContext<T> fc = FormContextFactoryImpl.getProvider().getFormContext( formName, request );
    //
    // }
}
