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

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ecosystems.weblib.html.RequestPolicy;
import org.ecosystems.weblib.html.form.ConversionException;
import org.ecosystems.weblib.html.form.ManageFormException;

/**
 * Interface des contextes de formulaire. Le contexte de formulaire représente l'état d'un formulaire lors d'une
 * requête.
 * 
 * Lors de sa création, le contexte de formulaire possède ou non un bean son image.
 * Il initialise ses champs en fonction du contexte :
 * - un champ posté invalide est affiché mais pas à l'image du bean
 * - un champ posté valide est affiché et à l'image du bean si tout le formulaire est valide.
 * - un champ est stocké en session uniquement si il est valide.
 * Si le formulaire est posté et valide, le bean est à l'image du formulaire.
 * Si le formulaire est posté mais invalide, le bean n'est pas initialisé, ni la session (les champs se définissent en
 * fonction de ce qui est posté). Si une persistance de session existe pour le formulaire, elle est supprimée
 * Si le formulaire n'est pas posté, le bean et les champs sont à l'image des valeurs stockées en session si la
 * persistance de session est activée.
 * 
 * @param <T> Type de bean du formulaire
 */
public interface FormContext<T>
{

    /**
     * Nom du paramètre indiquant le nom du formulaire posté.
     */
    String POSTED_KEY_NAME = "pk";

    /**
     * Nom du paramètre indiquant que le formulaire doit être effacé.
     **/
    String RESET_KEY_NAME = "reset";

    /**
     * Récupère le nom du formulaire, tel que décrit dans la configuration.
     * 
     * @return the formName
     **/
    String getFormName();

    /**
     * Récupère le bean du formulaire.
     * 
     * @return Bean correspondant au formulaire *
     * @throws InitFormException Levée si le bean ne peut être crée
     */
    T getBean() throws InitFormException;

    /**
     * Récupère la classe du beande formulaire.
     * 
     * @return the beanClass
     */
    Class<T> getBeanClass();

    /**
     * Indique si le formulaire est valide.
     * 
     * @return vrai si tous les inputs du formulaire sont valides
     */
    boolean isValid();

    /**
     * Indique si le formulaire est posté.
     * 
     * @return vrai si le formulaire est posté ( pas nécessairement valide ), faux dans tous les cas si un reset est
     *         envoyé
     */
    boolean isPosted();

    /**
     * Récupère les contextes de champs de formulaires, initialisés avec la requête courante.
     * 
     * @return the fieldContexts
     */
    Map<String, FieldContext<T, ?>> getFieldContexts();

    /**
     * Exécute le formulaire si il est posté et valide.
     * 
     * Si le formulaire est valide, le listener est exécuté, puis, si la persistance de session est activée, le bean est
     * stocké dans la session. Enfin si une redirection est spécifiée, la requête est redirigée vers la page de
     * redirection.
     * 
     * @param context Contexte de la servlet appelante
     * @param request Requête http
     * @param response Réponse http
     * @throws InitFormException Levée si l'initialisation du formulaire échoue
     * @throws ManageFormException Levée si le traitement du bean échoue
     * @throws IOException Levée si la redirection échoue en cas d'erreur I/O
     */
    void executeIfValid(ServletContext context, HttpServletRequest request, HttpServletResponse response) throws InitFormException,
            ManageFormException, IOException;

    /**
     * Supprime le bean de formulaire de la session.
     * 
     * @param request requête
     */
    void flushBean(HttpServletRequest request);

    /**
     * Indique si le formulaire possède des données persistantes. Renvoie toujours false pour un contexte de formulaire
     * ne supportant pas la persistance de session.
     * 
     * @return vrai si le formulaire a défini au moins une donnée persistante
     */
    boolean hasPersistentData();

    /**
     * Indique si le formulaire supporte la persistance de session.
     * 
     * @return vrai si la persistence de session est supportée
     */
    boolean isSessionPersistent();

    /**
     * Charge un bean, à l'issue de l'opération, le bean sera persistent dans la session pour ce formulaire.
     * 
     * @param bean1 Bean à charger
     * @param request Requête de servlet
     * @throws SecurityException Levée si une contrainte de sécurité empêche l'instantiation du bean
     * @throws NoSuchMethodException Levée si un getter/setter du bean est inexistant
     * @throws InvocationTargetException Levée si une méthode du bean lance une exception
     * @throws IllegalArgumentException Levée si un argument d'une méthode du bean est invalide
     * @throws IllegalAccessException Levée si l'accès à une méthode du bean est interdit
     * @throws ConversionException Levée si une conversion de valeur du formulaire est impossible
     * @throws InitFormException Levée si l'initialisation du contexte de formulaire est impossible
     */
    void loadBean(T bean1, HttpServletRequest request) throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, InitFormException, ConversionException;

    /**
     * Charge un bean, à l'issue de l'opération, le bean sera persistent pour le type de politique contexte spécifié.
     * 
     * @param bean1 Bean à charger
     * @param request Requête de servlet
     * @param policy Politique de contexte
     * @throws SecurityException Levée si une contrainte de sécurité empêche l'instantiation du bean
     * @throws NoSuchMethodException Levée si un getter/setter du bean est inexistant
     * @throws InvocationTargetException Levée si une méthode du bean lance une exception
     * @throws IllegalArgumentException Levée si un argument d'une méthode du bean est invalide
     * @throws IllegalAccessException Levée si l'accès à une méthode du bean est interdit
     * @throws ConversionException Levée si une conversion de valeur du formulaire est impossible
     * @throws InitFormException Levée si l'initialisation du contexte de formulaire est impossible
     */
    void loadBean(T bean1, HttpServletRequest request, RequestPolicy policy) throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException, InitFormException, ConversionException;

}
