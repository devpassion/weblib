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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.ecosystems.weblibtests.servlet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ecosystems.weblib.html.form.FormListener;
import org.ecosystems.weblib.html.form.ManageFormException;
import org.ecosystems.weblibtests.model.Logo;

/**
 * Ecouteur du formulaire d'ajout de logos
 */
public class AddFormListener implements FormListener<Logo> {

	@Override
	public void manageBean(final Logo bean, final ServletContext context,
			final HttpServletRequest request, final HttpServletResponse response)
			throws ManageFormException {
		// throw new ManageFormException( "AddForm soumis" );

	}

}
