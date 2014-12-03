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
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.ecosystems.weblib.html.form.config.FieldContext;
import org.ecosystems.weblib.html.form.config.generated.Option;

/**
 * Classe abstraite de base des validateurs de formulaire.
 */
public abstract class AbstractInputValidator implements InputValidator
{
    private final Properties options = new Properties();

    private FieldContext<?, ?> fc;

    @Override
    public void setRequest(final HttpServletRequest request)
    {

    }

    @Override
    public void setOptions(final List<? extends Option> options)
    {
        for (final Option o : options)
        {
            this.options.put( o.getKey(), o.getValue() );
        }
    }

    @Override
    public void setFieldContext(final FieldContext<?, ?> fc)
    {
        this.fc = fc;
    }

    /**
     * Récupère le contexte de champ associé au validateur.
     * 
     * @return Contexte de champ du validateur
     */
    protected FieldContext<?, ?> getFieldContext()
    {
        return this.fc;
    }

    /**
     * Récupère les options du validateur.
     * 
     * @return options du validateur spécifiées dans la configuration
     */
    protected Properties getOptions()
    {
        return this.options;
    }

}
