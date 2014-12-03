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

import java.util.EventListener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Écouteur de validation de formulaire.
 * 
 * @param <T> Type du bean géré
 */
public interface FormListener<T> extends EventListener
{

    /**
     * Traite un bean représentant un formulaire dans le cas ou celui ci est posté et valide. Doit être exécutée dans
     * une servlet
     * 
     * @param bean Bean à l'image du formulaire
     * @param context Contexte de servlet
     * @param request Requête HTTP
     * @param response Réponse HTTP
     * @throws ManageFormException Levée si une erreur survient
     */
    void manageBean(T bean, ServletContext context, HttpServletRequest request, HttpServletResponse response) throws ManageFormException;
}
