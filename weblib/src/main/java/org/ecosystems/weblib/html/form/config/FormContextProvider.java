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
package org.ecosystems.weblib.html.form.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ecosystems.weblib.html.form.ManageFormException;
import org.ecosystems.weblib.html.form.config.generated.Forms;

/**
 * Fournisseur de contextes de formulaire. Le role du fournisseur de contexte est de fournir un contexte de formulaire
 * en fonction d'une requête et d'une configuration. La configuration est de type xml, chargé par la méthode Le contexte
 * peut être inclus dans la requête en fonction de l'implémentation, ceci de façon transparente pour l'utilisateur de la
 * méthode.
 */
public interface FormContextProvider
{

    /**
     * Charge la configuration des formulaires.
     * 
     * @param configFile Fichier de configuration
     * @throws InitFormException Levée si la configuration de formulaire est impossible à effectuer
     * @throws FileNotFoundException Levée si le fichier de configuration de formulaires est introuvable
     * @throws BadFormBeanClassException Levée si le bean de formulaire est introuvable ou impossible à charger
     */
    void loadConfig(File configFile) throws InitFormException, FileNotFoundException, BadFormBeanClassException;

    /**
     * Récupère la configuration de formulaires.
     * 
     * @return configuration de formulaires
     */
    Forms getConfig();

    /**
     * Récupère un contexte de formulaire.
     * 
     * @param <T> Type de bean du contexte récupéré
     * 
     * @param formName Nom du formulaire
     * @param beanClass bean du formulaire
     * @param request requête http
     * @return Contexte de formulaire
     * @throws InitFormException Levée si l'initialisation de formulaire échoue
     * @throws BadFormBeanClassException Levée si le type de bean ne correspond pas au nom du formulaire
     */
    <T> FormContext<T> getFormContext(String formName, Class<T> beanClass, ServletRequest request) throws InitFormException,
            BadFormBeanClassException;

    /**
     * Récupère un contexte de formulaire. Le typage de la sortie n'est pas garanti, une erreur de cast est possible.
     * 
     * @param <T> Type de bean du contexte récupéré
     * 
     * @param formName Nom du formulaire
     * @param request requête http
     * @return Contexte de formulaire
     * @throws InitFormException Levée si l'initialisation de formulaire échoue
     * @throws BadFormBeanClassException Levée si le type de bean ne correspond pas au nom du formulaire
     */
    <T> FormContext<T> getFormContext(String formName, ServletRequest request) throws InitFormException, BadFormBeanClassException;

    /**
     * Execute le formulaire valide et posté si il en existe un.
     * 
     * @param context Contexte de servlet
     * 
     * @param request Requête envoyée
     * @param response réponse renvoyée
     * @throws BadFormBeanClassException Levée si un bean de classe est de type incohérent
     * @throws IOException Levée si la redirectino échoue
     * @throws ManageFormException Levée si le listener de formulaire lève une erreur
     * @throws InitFormException Levée si l'initialisation de formulaire échoue
     */
    void executeValidForm(ServletContext context, HttpServletRequest request, HttpServletResponse response) throws InitFormException,
            ManageFormException, IOException, BadFormBeanClassException;

}
