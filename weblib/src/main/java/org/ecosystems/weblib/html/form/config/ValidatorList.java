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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.ecosystems.weblib.html.form.InputValidator;
import org.ecosystems.weblib.html.form.config.generated.Validator;
import org.ecosystems.weblib.html.form.config.generated.Validators;
import org.ecosystems.weblib.log.LoggerAdapter;
import org.ecosystems.weblib.tools.ClassTools;

/**
 * Liste de validateurs.
 */
public class ValidatorList
{
    private static final LoggerAdapter LOGGER_ADAPTER = new LoggerAdapter( ValidatorList.class );

    private final List<InputValidator> list = new ArrayList<InputValidator>();

    private final List<String> invalidsRules = new ArrayList<String>();

    private final FieldContext<?, ?> fc;

    /**
     * Nouvelle liste de validateurs.
     * 
     * @param logAdapter adapteur de log appellant
     * @param validators Balise xml "validators"
     * @param request Requête de soumission du formulaire
     * @throws ClassNotFoundException Levée si la classe du validateur est introuvable
     * @throws InstantiationException Levée si la classe du validateur est impossible à instancier
     * @throws IllegalAccessException Levée si il est impossible d'accéder au constructeur du validateur
     */
    public ValidatorList(final LoggerAdapter logAdapter, final Validators validators, final RequestValueProvider rvp,
            final FieldContext<?, ?> fc) throws ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        this.fc = fc;
        if (validators != null)
        {
            for (final Validator v : validators.getValidator())
            {
                this.add( logAdapter, v, rvp.getRequest() );
            }
        }
    }

    /**
     * Récupère les règles invalides.
     * 
     * @return the invalidsRules
     */
    public List<String> getInvalidsRules()
    {
        return this.invalidsRules;
    }

    private void add(final LoggerAdapter logAdapter, final Validator validator, final HttpServletRequest request)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        final InputValidator v = (InputValidator) ClassTools.loadClass( logAdapter, validator.getValidatorClass() );
        v.setOptions( validator.getOption() );
        v.setFieldContext( this.fc );
        v.setRequest( request );
        this.list.add( v );
    }

    /**
     * Défini les règles invalides.
     * 
     * @param value Valeur à contrôler
     */
    void setInvalidsRules(final Object value)
    {
        this.invalidsRules.clear();
        LOGGER_ADAPTER.trace( "test sur la valeur : ", value );
        for (final InputValidator v : this.list)
        {
            LOGGER_ADAPTER.trace( "regle : ", v.ruleName() );
            if (!v.validate( value ))
            {
                LOGGER_ADAPTER.debug( "regle invalide : ", v.ruleName() ); //$NON-NLS-1$
                this.invalidsRules.add( v.ruleName() );
            }
            else
            {
                LOGGER_ADAPTER.trace( "regle valide : ", v.ruleName() );
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( "ValidatorList [invalidsRules=" ).append( this.invalidsRules ) //$NON-NLS-1$
                .append( "]" ); //$NON-NLS-1$
        return builder.toString();
    }

}
