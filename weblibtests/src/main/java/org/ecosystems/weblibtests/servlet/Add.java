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

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ecosystems.weblib.servlet.FormServlet;

/**
 * Sevlet de la page d'ajouts
 * 
 */
public class Add extends FormServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -808756142713888722L;

	@Override
	protected void doGet(final HttpServletRequest req,
			final HttpServletResponse resp) throws ServletException,
			IOException {
		super.doGet(req, resp);
		if (!resp.isCommitted()) {
			this.getServletContext().getRequestDispatcher("/add.jsp")
					.forward(req, resp);
		}

	}

	@Override
	protected void doPost(final HttpServletRequest req,
			final HttpServletResponse resp) throws ServletException,
			IOException {
		super.doPost(req, resp);
		if (!resp.isCommitted()) {
			this.getServletContext().getRequestDispatcher("/add.jsp")
					.forward(req, resp);
		}
	}
}
