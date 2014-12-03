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

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.validator.Form;
import org.ecosystems.lib.config.Configurator;
import org.ecosystems.lib.tools.StringTools;
import org.ecosystems.weblib.html.EnumConverter;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * TestCase des convertisseurs et validateurs.
 */
public class ConverterTestCase
{
    /**
     * Nom de la propriété système contenant le nom du fichier de configuration générale.
     */
    private static final String CONFIG_FILE_SYSTEM_PROP_NAME = "org.ecosystems.weblib.test.form.CONFIG_FILE";

    /**
     * Définit la configuration générale.
     * 
     * @throws ConfigurationException Levée si la configuration est introuvable
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

        final File configFile = new File( configFileName );
        final XMLConfiguration xmlConfig = new XMLConfiguration( configFile );
        Configurator.setConfig( xmlConfig );
    }

    /**
     * Teste la validation et la conversion de dates.
     * 
     * @throws ConversionException Levée si une conversion échoue
     */
    @SuppressWarnings({ "nls", "boxing" })
    @Test
    public void testDateConverter() throws ConversionException
    {
        final DateValidator validator = new DateValidator();
        final DateConverter converter = new DateConverter();

        final Object[] invalids = new Object[] { "pouet", "456/89/2002", "2002", 156, "02/31/2012", "13/01/2000", "02/29/2011" };
        final Object[] valids = new Object[] { "01/01/2002", "01/31/2002", "12/30/2002", "02/29/2012" };

        this.testValidation( java.sql.Date.class, invalids, valids, validator, converter );

    }

    /**
     * Teste la validation et conversion d'entiers.
     * 
     * @throws ConversionException Levée si une conversion échoue
     */
    @SuppressWarnings({ "nls", "boxing" })
    @Test
    public void testIntegerConverter() throws ConversionException
    {
        final IntegerValidator validator = new IntegerValidator();
        final IntegerConverter converter = new IntegerConverter();

        final Object[] invalids = new Object[] { "12a", new Object(), new BigDecimal( "12.23" ), 0.12 };
        final Object[] valids = new Object[] { 12, new Integer( 13 ), 0 };

        this.testValidation( Integer.class, invalids, valids, validator, converter );

    }

    /**
     * Teste la validation et conversion de types numériques.
     * 
     * @throws ConversionException Levée si une conversion échoue
     */
    @SuppressWarnings({ "nls", "boxing" })
    @Test
    public void testNumericConverter() throws ConversionException
    {
        final NumericValidator validator = new NumericValidator();
        final NumericConverter converter = new NumericConverter();

        final Object[] invalids = new Object[] { "pouet", "17h23", "17:61", "chaine" };
        final Object[] valids = new Object[] { new Double( 123.456 ), 12, new BigDecimal( "123.789" ) };

        this.testValidation( BigDecimal.class, invalids, valids, validator, converter );
    }

    /**
     * Teste la validation et conversion de type {@link Time}.
     * 
     * @throws ConversionException Levée si une converiosn échoue
     */
    @SuppressWarnings("nls")
    @Test
    public void testTimeConverter() throws ConversionException
    {
        final TimeValidator validator = new TimeValidator();
        final TimeConverter converter = new TimeConverter();

        final Object[] invalids = new Object[] { "pouet", "17h23", "17:61", "24:00", "-3" };
        final Object[] valids = new Object[] { "17:30", "14:30", "00:30", "00:00" };

        this.testValidation( Time.class, invalids, valids, validator, converter );

    }

    @Test
    public void testStringArrayConverter() throws ConversionException
    {
        final StringArrayConverter converter = new StringArrayConverter();
        final ArrayValidator validator = new HTMLStringArrayValidator();

        final Object[][] invalids = new Object[][] { { "pouet", "17h23", "17:61", "24:00", "<javascript>" },
                { "pouet", "17h23", "17:61", "24:00", "<", null } };
        final Object[][] valids = new Object[][] { { "blabla", "truc", "bidule", "00:00", null }, { "17:30", "14:30", "00:30", "pouet" } };

        String msg;
        for (final Object[] io : invalids)
        {
            msg = StringTools.concat( "valid : ", StringTools.join( ",", io ), " (must be invalid)" );
            assertFalse( msg, validator.validate( io ) );
        }

        for (final Object[] io : valids)
        {
            msg = StringTools.concat( "invalid : ", StringTools.join( ",", io ), " (must be valid)" );
            assertTrue( msg, validator.validate( io ) );
            converter.convert( io );
        }

    }

    /**
     * Teste le validateur de mail.
     * 
     * @throws ConversionException Levée si une conversion de valeur échoue
     */
    @SuppressWarnings("nls")
    @Test
    public void testMailValidator() throws ConversionException
    {

        final Object[] invalids = new Object[] { "mailpouet", "@mailpouet", "mail@mail" };
        final Object[] valids = new Object[] { "mail@mail.com", "My.super.mail@machin.com" };

        this.testValidation( null, invalids, valids, new MailValidator() );

    }

    @Test
    public void testFileConverter() throws FileNotFoundException, ConversionException
    {
        final FileConverter fc = new FileConverter();
        final ImageFileValidator ifv = new ImageFileValidator();
        final File f = new File( "target/test-classes/images/img.jpg" );
        if (!f.exists())
        {
            throw new FileNotFoundException( "target/test-classes/images/img.jpg" );
        }

        final File f2 = new File( "target/test-classes/images/bad_img.jpg" );
        if (!f2.exists())
        {
            throw new FileNotFoundException( "target/test-classes/images/bad_img.jpg" );
        }

        fc.convert( f );
        fc.convert( f2 );

        assertTrue( "Le fichier " + f.getName() + " devrait être une image valide", ifv.validate( f ) );

        assertFalse( "Le fichier " + f2.getName() + " devrait être une image invalide", ifv.validate( f2 ) );
    }

    /**
     * Teste la conversion d'énumérations.
     * 
     * @throws ConversionException Levée si une converison échoue
     */
    @SuppressWarnings("nls")
    @Test
    public void testEnumConverter() throws ConversionException
    {
        final EnumValidator<TryEnum> validator = new EnumValidator<TryEnum>( TryEnum.class )
        {

            @Override
            public String ruleName()
            {
                return "testEnumRule"; //$NON-NLS-1$
            }

        };

        final EnumConverter<TryEnum> converter = new EnumConverter<TryEnum>()
        {

            @Override
            public Class<TryEnum> getConvertClass()
            {
                return TryEnum.class;
            }

        };

        final Object[] invalids = new Object[] { "POOOUUUET", "0", new Form() };
        final Object[] valids = new Object[] { "POUET", "BADABOUM", "POMPODOM" };

        this.testValidation( TryEnum.class, invalids, valids, validator, converter );

    }

    /**
     * Teste le validateur de texte HTML.
     * 
     * @throws ConversionException Levée si une conversion de valeur échoue
     */
    @SuppressWarnings("nls")
    @Test
    public void testHTMLValidator() throws ConversionException
    {

        this.testValidation( String.class, new Object[] { "badaboum<script>vilain script</script>", "<b>powned !</b>",
                "<script type=\"javascript\">malicious" }, new Object[] { "correct", "I'm a good user", "" }, new HTMLStringValidator() );

    }

    /**
     * Teste une validation.
     * 
     * @param <T> Classe de l'objet à valider
     * @param convertedClass Classe attendue
     * @param invalids valeurs devant être invalides
     * @param valids valeur devant être valides
     * @param validator Validateur
     * @throws ConversionException Levée si une conversion de valeur échoue
     */
    protected <T> void testValidation(final Class<T> convertedClass, final Object[] invalids, final Object[] valids,
            final InputValidator validator) throws ConversionException
    {
        this.testValidation( convertedClass, invalids, valids, validator, null );
    }

    /**
     * 
     * Teste une validation.
     * 
     * @param <T> Classe à valider
     * 
     * @param convertedClass Classe attendue
     * @param invalids valeurs devant être invalides
     * @param valids valeur devant être valides
     * @param validator Validateur
     * @param converter Convertisseur
     * @throws ConversionException Levée si une erreur apparait à la conversion
     */
    protected <T> void testValidation(final Class<T> convertedClass, final Object[] invalids, final Object[] valids,
            final InputValidator validator, final Converter<T> converter) throws ConversionException
    {
        for (final Object o : invalids)
        {
            final String message = "\"" + StringTools.getStringOrNull( o ) + "\" doit être une valeur invalide pour une conversion en "
                    + StringTools.getStringOrNull( convertedClass, "{Classe non renseignée}" );
            assertFalse( message, validator.validate( o ) );
        }

        for (final Object o : valids)
        {
            final String message = "\"" + StringTools.getStringOrNull( o ) + "\" doit être une valeur valide pour une conversion en "
                    + StringTools.getStringOrNull( convertedClass, "{Classe non renseignée}" );
            assertTrue( message, validator.validate( o ) );
            if (converter != null)
            {
                converter.convert( o );
            }
        }

    }

}
