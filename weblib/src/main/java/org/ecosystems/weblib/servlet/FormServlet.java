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
package org.ecosystems.weblib.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ecosystems.weblib.html.form.ManageFormException;
import org.ecosystems.weblib.html.form.config.BadFormBeanClassException;
import org.ecosystems.weblib.html.form.config.FormContextFactoryImpl;
import org.ecosystems.weblib.html.form.config.InitFormException;
import org.ecosystems.weblib.log.LoggerAdapter;

/**
 * Servlet initialisant un formulaire.
 */
public class FormServlet extends LanguageServlet
{

    private static final long serialVersionUID = -1234997844123398542L;

    private static final LoggerAdapter LOGGER_ADAPTER = new LoggerAdapter( FormServlet.class );

    /**
     * Execute le premier formulaire trouvé.
     * 
     * @param request requête HTTP
     * @param response réponse HTTP
     * @throws ServletException Levée si l'initailisation de formulaire échoue
     */
    private void doForm(final HttpServletRequest request, final HttpServletResponse response) throws ServletException
    {
        LOGGER_ADAPTER.debug( "doForm..." );
        try
        {
            FormContextFactoryImpl.getProvider().executeValidForm( this.getServletContext(), request, response );
        }

        catch (InitFormException | IOException | BadFormBeanClassException e)
        {
            throw new ServletException( e );
        }
        catch (final ManageFormException e)
        {
            LOGGER_ADAPTER.warn( "Erreur de formulaire dont le bean est valide : ", e );
            this.manageFormError( e );
        }

    }

    protected void manageFormError(final ManageFormException e) throws ServletException
    {
        throw new ServletException( e );
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException
    {
        super.doGet( request, response );
        this.doForm( request, response );

    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException
    {
        super.doPost( request, response );
        this.doForm( request, response );

    }

}
