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
package org.ecosystems.weblibtests.tests;

import java.util.Locale;

import net.sourceforge.jwebunit.junit.WebTester;

import org.ecosystems.weblib.test.FormScenarioTester;

/**
 * Implémentation du testeur de formulaires
 * 
 */
public class FormScenarioTesterImplIT extends FormScenarioTester
{

    // @BeforeClass
    // public static void setUpBeforeClass() throws Exception
    // {
    //
    // //setFormConfig("src/main/webapp/WEB-INF/formTestBeans.xml",
    // "src/main/webapp/WEB-INF/testForm.xml",
    // "src/main/webapp/WEB-INF/wlconfig.xml");
    //
    // }

    // @Test
    // @Override
    // public void launch() throws InterruptedException, InitFormException,
    // BadFormBeanClassException, IOException
    // {
    // super.launch();
    // }

    @Override
    protected WebTester getNewTester()
    {
        WebTester tester = new WebTester();

        tester.setBaseUrl( "http://localhost:8080/weblibtests" );

        tester.getTestContext().setLocale( Locale.FRENCH );

        tester.getTestContext().addRequestHeader( "Accept-Language", "fr" );

        return tester;
    }

}
