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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.ecosystems.weblib.html.form.config.FieldContext;
import org.ecosystems.weblib.html.form.config.generated.Option;

/**
 * Interface des validateurs pour les tags input.
 */
public interface InputValidator
{
    /**
     * Indique si une valeur est valide.
     * 
     * @param value Valuer à valider
     * @return vrai si la valeur est valide
     */
    boolean validate(Object value);

    /**
     * Nom de la règle associée.
     * 
     * @return nom de la règle de validation
     */
    String ruleName();

    /**
     * Défini la requête envoyée. Cette méthode peut être vide si le contexte courant n'est pas utile pour déterminer si
     * l'entrée est valide.
     * 
     * @param request requête de validation du formulaire contenant le cjaùmp validé
     * @deprecated devenue inutile (du moins devrait l'être)
     */
    @Deprecated
    void setRequest(HttpServletRequest request);

    /**
     * Définit les options du validateur
     * 
     * @param options options trouvées
     */
    void setOptions(List<? extends Option> options);

    /**
     * Récupère les options de validateur (Spécifiéee dans le xml de configuration de formulaire).
     * 
     * @return options de configuration du validateur
     */
    // Properties getOptions();

    /**
     * Définit le contexte de champ de formulaire.
     * 
     * @param fc Contexte de champ
     */
    void setFieldContext(FieldContext<?, ?> fc);

}
