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
package org.ecosystems.weblib.html.loop;

import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.sql.rowset.CachedRowSet;

/**
 * Fournisseur de résultats.
 */
public interface ResultProvider
{
    /**
     * Récupère un ensemble de résultats.
     * 
     * @param context Contexte de la page demandant le résultat
     * @return Résultats, peut renvoyer null en cas d'absence de résultats
     * @throws SQLException Levée si le SGBD renvoie une erreur
     * @throws JspException Levée si une erreur appairait
     */
    CachedRowSet getResult(PageContext context) throws SQLException, JspException;

    /**
     * Récupère le nombre total d'enregistrements présents dans la persistance de données.
     * 
     * @param context Contexte de la page affichant le tag
     * 
     * @return nombre d'enregistrements
     * @throws SQLException Levée si le SGBD renvoie une erreur
     * @throws JspException Levée si une erreur appairaits
     */
    int getCount(PageContext context) throws SQLException, JspException;
}
