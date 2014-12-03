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
package org.ecosystems.weblibtests.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Time;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ecosystems.weblib.html.form.config.BadFormBeanClassException;
import org.ecosystems.weblib.html.form.config.FormContext;
import org.ecosystems.weblib.html.form.config.FormContextFactoryImpl;
import org.ecosystems.weblib.html.form.config.InitFormException;
import org.ecosystems.weblib.servlet.FormServlet;
import org.ecosystems.weblibtests.model.Party;

/**
 * Servlet de la page d'administration d'évènement
 */
public class AdminTestServlet extends FormServlet
{
    /**
     * 
     */
    private static final long serialVersionUID = -7201428260155550465L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        super.doGet( request, response );
        this.doRequest( request, response );
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        super.doPost( request, response );
        this.doRequest( request, response );
    }

    private void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        Party party = new Party( "test - Fête du boudin", "17 rue Paul bellamy", new BigDecimal( 0 ), new BigDecimal( 0 ),
                java.sql.Date.valueOf( "2012-01-12" ), "Venez nombreux à la fête du boudin!", "mail@mail.com", "marcel",
                Time.valueOf( "17:30:00" ), null );

        FormContext<Party> fc;
        try
        {
            fc = FormContextFactoryImpl.getProvider().getFormContext( "party", Party.class, request );
        }
        catch (InitFormException | BadFormBeanClassException e)
        {

            throw new ServletException( e );
        }
        // try {
        // fc.loadBean(party, request);
        // } catch (NoSuchMethodException | SecurityException
        // | IllegalAccessException | IllegalArgumentException
        // | InvocationTargetException | InitFormException
        // | ConversionException e) {
        // // TODO Auto-generated catch block
        // throw new ServletException(e);
        // }

        this.getServletContext().getRequestDispatcher( "/admin.jsp" ).forward( request, response );

    }
}
