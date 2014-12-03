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

import java.util.List;

import org.ecosystems.weblib.html.form.ConversionException;
import org.ecosystems.weblib.html.form.InvalidBoxedTypeException;

/**
 * Contexte de champ de formulaire.
 * 
 * Ce contexte est initialisé à sa création.
 * 
 * @param <T> Type de bean du formulaire parent
 * @param <U> Type de la valeur géré par ce champ
 */
public interface FieldContext<T, U>
{

    /**
     * Récupère la classe du type de la valeur contenue dans le champ.
     * 
     * @return the type
     */
    Class<U> getType();

    /**
     * Récupère le nom du champ, qui lui sert de clé POST.
     * 
     * @return the name
     */
    String getName();

    /**
     * Récupère la valeur du champ.
     * 
     * @return valeur convertie trouvée dans le contexte, ou null la valeur n'existe pas
     * @throws ConversionException Levée si une erreur de conversion apparait
     */
    U getValue() throws ConversionException;

    /**
     * Récupère une valeur affichable du paramètre représenté par ce contexte.
     * 
     * @return Valeur affichable ou chaîne vide si la valeur est nulle
     * @throws InvalidBoxedTypeException Levée si un unboxing est impossible lors de la conversion
     */
    String getDisplayableValue() throws InvalidBoxedTypeException;

    /**
     * Indique si la valeur a été postée dans ce contexte.
     * 
     * @return Vrai si la valeur est trouvée dans la requête
     */
    boolean isPosted();

    /**
     * Récupère une valeur indiquant si le champ est valide.
     * 
     * @return Vrai si la valeur est postée/persistante et valide
     */
    boolean isValid();

    /**
     * Récupère une valeur indiquant si le champ est requis pour la soumission du formulaire.
     * 
     * @return Vrai si le champ est requis
     */
    boolean isRequired();

    /**
     * Récupère la liste des noms de règles invalides pour ce champ.
     * 
     * @return Règles invalides pour le champ, dans l'ordre d'apparition
     */
    List<String> getInvalidRules();

    /**
     * Récupère le contexte de formulaire dont dépend le champ.
     * 
     * @return Contexte de formulaire parent de ce champ
     */
    FormContext<T> getFormContext();

    /**
     * Crée une copie du contexte de champ.
     * 
     * @param parent Nouveau contexte de formulaire de la copie générée
     * @param rvp Fournisseur de résultats de requête
     * @param newValue Nouvelle valeur du champ
     * @return Contexte de champ cloné
     * @throws InitFormException Levée si la configuration de formulaire est imposssible à initialiser
     * @throws ConversionException Levée si la conversion de la valeur échoue
     */
    FieldContext<T, U> cloneWithOtherValue(FormContext<T> parent, RequestValueProvider rvp, U newValue) throws InitFormException,
            ConversionException;

}
