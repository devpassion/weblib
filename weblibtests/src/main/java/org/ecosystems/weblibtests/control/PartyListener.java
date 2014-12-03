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
package org.ecosystems.weblibtests.control;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ecosystems.weblib.html.form.FormListener;
import org.ecosystems.weblib.log.LoggerAdapter;
import org.ecosystems.weblibtests.model.Party;

/**
 * Ecouteur du formualire d'évèneemtn ponctuel
 */
public class PartyListener implements FormListener<Party> {

	@Override
	public void manageBean(Party party, ServletContext context,
			HttpServletRequest request, HttpServletResponse response) {
		new LoggerAdapter(PartyListener.class).trace(party);

	}

}
