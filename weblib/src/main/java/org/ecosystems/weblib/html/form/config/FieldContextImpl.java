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

import org.ecosystems.lib.tools.StringTools;
import org.ecosystems.weblib.html.form.ConversionException;
import org.ecosystems.weblib.html.form.Converter;
import org.ecosystems.weblib.html.form.InvalidBoxedTypeException;
import org.ecosystems.weblib.html.form.config.generated.Field;
import org.ecosystems.weblib.log.LoggerAdapter;

/**
 * Classe fournissant un contexte de champ.
 * 
 * @param <T> Type de bean du formulaire parent
 * @param <U> Type du champ
 */
class FieldContextImpl<T, U> implements FieldContext<T, U>
{
    /**
     * Préfixe des champs de session pour les formulaires.
     */
    private static final String FORM_PARAM_PREFIX = "forms"; //$NON-NLS-1$

    private ValidatorList validators;

    private final FormContext<T> formContext;

    private final Converter<U> converter;

    private final String name;

    private U value;

    private final boolean required;

    private boolean posted;

    private boolean valid;

    private static final LoggerAdapter LOG_ADAPTER = new LoggerAdapter( FieldContextImpl.class );

    private final Field field;

    /**
     * nouveau contexte de champ.
     * 
     * @param parent Formulaire parent du champ
     * @param field configuration de champ champ input
     * @param value Valeur du paramètre (expérimental)
     * @param request requête appelante
     * @param converter Convertisseur du chap, permet l'inférence de type
     * @param reset Indique si le paramètre est remis à zéro
     * @throws InitFormException Levée si le formulaire est impossible à initialiser
     * @throws ConversionException Levée si la conversion de la donnée échoue
     */
    public FieldContextImpl(final FormContext<T> parent, final Field field, final Object value, final RequestValueProvider rvp,
            final Converter<U> converter, final boolean reset) throws InitFormException, ConversionException
    {
        this.field = field;
        this.formContext = parent;
        this.name = field.getName();
        this.required = field.isRequired();
        this.converter = converter;
        try
        {
            this.validators = new ValidatorList( LOG_ADAPTER, field.getValidators(), rvp, this );
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException e)
        {
            throw new InitFormException( StringTools.concat( "Classe du validateur introuvable pour le champ ", field.getName() ), e );
        }
        if (!reset)
        {
            this.setValue( value, rvp );
        }
    }

    @SuppressWarnings("unchecked")
    private void setValue(final Object val, final RequestValueProvider rvp) throws ConversionException
    {
        if (this.formContext.isPosted())
        {

            // final String val = request.getParameter( this.name );

            // / @TODO : si le champ est requis, il est considéré comme posté
            this.posted = rvp.hasParameter( this.name ) || rvp.hasFile( this.name ) || this.isRequired();
            // Remettre dans le if si problème

            if (this.posted)
            {
                this.valid( val );
                if (this.valid)
                {
                    this.value = this.converter.convert( val );
                    if (this.formContext.isSessionPersistent())
                    {
                        final String sessionParamName = FieldContextImpl.getSessionParamName( this.formContext.getFormName(), this.name );

                        rvp.setSessionValue( sessionParamName, this.value );
                        LOG_ADAPTER.trace( "writed in session : ", sessionParamName, " = ", this.value );
                    }
                }
            }
        }
        else if (this.formContext.isSessionPersistent())
        {
            final String sessionParamName = FieldContextImpl.getSessionParamName( this.formContext.getFormName(), this.name );
            this.value = (U) rvp.getSessionValue( sessionParamName );
        }
    }

    /**
     * Récupère un nom de paramètre de session pour la paramètre représenté par ce contexte.
     * 
     * @param formName Nom du formulaire
     * @param paramName nom du paramètre
     * @return Chaine de la forme 'forms.form.param'
     */
    static String getSessionParamName(final String formName, final String paramName)
    {
        return StringTools.concat( FORM_PARAM_PREFIX, ".", formName, ".", paramName );
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ecosystems.weblib.html.form.config.FieldContext#getType()
     */
    @Override
    public Class<U> getType()
    {
        return this.converter.getConvertClass();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ecosystems.weblib.html.form.config.FieldContext#getName()
     */
    @Override
    public String getName()
    {
        return this.name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ecosystems.weblib.html.form.config.FieldContext#getCastableValue()
     */
    @Override
    public U getValue() throws ConversionException
    {
        return this.value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ecosystems.weblib.html.form.config.FieldContext#getDisplayableValue()
     */
    @Override
    public String getDisplayableValue() throws InvalidBoxedTypeException
    {
        return this.converter.toString( this.value );
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ecosystems.weblib.html.form.config.FieldContext#isPosted()
     */
    @Override
    public boolean isPosted()
    {
        return this.posted;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ecosystems.weblib.html.form.config.FieldContext#isValid()
     */
    @Override
    public boolean isValid()
    {
        return this.valid;
    }

    private void valid(final Object valueToValid)
    {
        this.validators.setInvalidsRules( valueToValid );
        this.valid = this.validators.getInvalidsRules().isEmpty();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ecosystems.weblib.html.form.config.FieldContext#isRequired()
     */
    @Override
    public boolean isRequired()
    {
        return this.required;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ecosystems.weblib.html.form.config.FieldContext#getInvalidRules()
     */
    @Override
    public List<String> getInvalidRules()
    {
        return this.validators.getInvalidsRules();
    }

    @Override
    public FormContext<T> getFormContext()
    {
        return this.formContext;
    }

    @Override
    public FieldContext<T, U> cloneWithOtherValue(final FormContext<T> parent, final RequestValueProvider rvp, final U newValue)
            throws InitFormException, ConversionException
    {
        try
        {
            return new FieldContextImpl<>( parent, this.field, this.converter.toString( newValue ), rvp, this.converter, false );
        }
        catch (final InvalidBoxedTypeException e)
        {
            throw new ConversionException( newValue, this.converter.getConvertClass(), e );
        }
    }
    /**
     * Défini la valeur
     * 
     * @param value2 valeur à définir
     */
    // void setValue( Object value2 )
    // {
    // this.value = value2;
    // this.valid();
    // }

}
