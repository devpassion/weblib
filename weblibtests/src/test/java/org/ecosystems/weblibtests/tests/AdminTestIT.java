package org.ecosystems.weblibtests.tests;

import java.util.Locale;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.Test;

public class AdminTestIT
{
    // @BeforeClass
    // public static void setUpBeforeClass() throws Exception
    // {
    //
    // JAXBContext context = JAXBContext.newInstance( FormTest.class );
    //
    // Unmarshaller unmarshaller = context.createUnmarshaller();
    //
    // File configFile = new File( "src/main/webapp/WEB-INF/formTestBeans.xml"
    // );
    //
    // FormTest config = (FormTest) unmarshaller.unmarshal( configFile );
    //
    // }

    @Test
    public void test()
    {
        WebTester tester = new WebTester();

        tester.setBaseUrl( "http://localhost:8080/weblibtests" );

        tester.getTestContext().setLocale( Locale.FRENCH );

        tester.getTestContext().addRequestHeader( "Accept-Language", "fr" );

        tester.beginAt( "/admin.html" );

    }
}
