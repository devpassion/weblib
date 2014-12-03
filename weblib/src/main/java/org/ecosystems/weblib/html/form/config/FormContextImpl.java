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
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.reflect.MethodUtils;
import org.ecosystems.lib.tools.StringTools;
import org.ecosystems.weblib.html.RequestPolicy;
import org.ecosystems.weblib.html.form.ConversionException;
import org.ecosystems.weblib.html.form.Converter;
import org.ecosystems.weblib.html.form.FormListener;
import org.ecosystems.weblib.html.form.HTTPDataManager;
import org.ecosystems.weblib.html.form.ManageFormException;
import org.ecosystems.weblib.html.form.config.generated.Field;
import org.ecosystems.weblib.html.form.config.generated.Form;
import org.ecosystems.weblib.log.LoggerAdapter;
import org.ecosystems.weblib.tools.ClassTools;
import org.ecosystems.weblib.tools.HttpTools;

/**
 * Contexte de formulaire. Fournit toutes les informations nécessaires au traitement des formulaires
 * 
 * @param <T> Type du bean géré
 */
class FormContextImpl<T> implements FormContext<T>
{

    /**
     * Nom de l'attribut de requête content le contexte de formulaire.
     **/
    @Deprecated
    public static final String FORM_CONTEXT_ATTRIBUTE_NAME = "form_context"; //$NON-NLS-1$

    private final String formName;

    private T bean;

    private final Class<T> beanClass;

    private FormListener<T> listener;

    private final boolean valid;

    private boolean posted;

    private final Form form;

    private final Map<String, FieldContext<T, ?>> fieldContexts = new HashMap<>();

    private boolean reset;

    private static final LoggerAdapter LOG_ADAPTER = new LoggerAdapter( FormContextImpl.class );

    private final RequestValueProvider rvp;

    private static final Pattern pattern = Pattern.compile( "\\{([^\\{\\}]*)\\}" );

    /**
     * Crée un nouveau contexte de formulaire associé à une requête.
     * 
     * @param form Configuration de formulaire
     * @param request requête de servlet
     * @param beanClass Classe du bean du formulaire
     * @throws InitFormException Levée si le formulaire est impossible à initialiser
     * @throws BadFormBeanClassException Levée si le type de bean est incohérent
     */
    FormContextImpl(final Form form, final HttpServletRequest request, final Class<T> beanClass) throws InitFormException,
            BadFormBeanClassException
    {

        if (form == null)
        {
            throw new InitFormException( "Configuration de formulaire nulle trouvée. "
                    + "Le contexte de formulaire n'a peut être pas été initialisé" );
        }

        this.form = form;
        if (!form.getBean().equals( beanClass.getName() ))
        {
            throw new BadFormBeanClassException( form.getFormName(), beanClass );
        }
        this.beanClass = beanClass;

        this.formName = form.getFormName();
        try
        {
            this.listener = ClassTools.loadClass( LOG_ADAPTER, form.getListener() );
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException e)
        {
            throw new InitFormException( "Listener introuvable ou impossible à instancier", e );
        }

        try
        {
            this.rvp = RequestValueProviderFactory.getRVPProvider( request );
        }
        catch (FileUploadException | IOException e)
        {
            throw new InitFormException( e );
        }

        this.setFields();

        boolean v = true;

        for (final FieldContext<T, ?> f : this.fieldContexts.values())
        {
            v = v && f.isValid();
        }

        this.valid = v;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ecosystems.weblib.html.form.config.FormContext#getFormName()
     */
    @Override
    public String getFormName()
    {
        return this.formName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ecosystems.weblib.html.form.config.FormContext#getBean()
     */
    @Override
    public T getBean() throws InitFormException
    {
        if (this.bean == null)
        {
            this.setBean();
        }
        return this.bean;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ecosystems.weblib.html.form.config.FormContext#getBeanClass()
     */
    @Override
    public Class<T> getBeanClass()
    {
        return this.beanClass;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ecosystems.weblib.html.form.config.FormContext#isValid()
     */
    @Override
    public boolean isValid()
    {
        return this.valid;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ecosystems.weblib.html.form.config.FormContext#isPosted()
     */
    @Override
    public boolean isPosted()
    {
        return this.posted;
    }

    /**
     * Indique si le formulaire doit être effacé.
     * 
     * @return vrai si un ordre de reset a été envoyé
     */
    public boolean isReset()
    {
        return this.reset;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ecosystems.weblib.html.form.config.FormContext#getFieldContexts()
     */
    @Override
    public Map<String, FieldContext<T, ?>> getFieldContexts()
    {
        return this.fieldContexts;
    }

    /**
     * Récupère la configuration de formulaire.
     * 
     * @return the form
     **/
    public Form getForm()
    {
        return this.form;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ecosystems.weblib.html.form.config.FormContext#executeIfValid(javax.servlet.ServletContext,
     * javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void executeIfValid(final ServletContext context, final HttpServletRequest request, final HttpServletResponse response)
            throws InitFormException, ManageFormException, IOException
    {
        LOG_ADAPTER.debug( StringTools.concat( "executeIfValid : ", this.formName ) ); //$NON-NLS-1$
        if (this.isPosted() && this.isValid())
        {
            LOG_ADAPTER.debug( StringTools.concat( "posted and valid : ", this.formName ) ); //$NON-NLS-1$

            final String flush = this.form.getFlush();
            if (flush != null && !flush.isEmpty())
            {
                final String[] flushs = flush.split( "," );
                for (final String f : flushs)
                {
                    try
                    {
                        final FormContext<?> fc = FormContextFactoryImpl.getProvider().getFormContext( f, request );
                        fc.flushBean( request );

                    }
                    catch (final BadFormBeanClassException e)
                    {
                        LOG_ADAPTER.warn( "Erreur au flush du formulaire ", this.formName,
                                ", impossible de récupérer le contexte du formulaire ", f, e );
                    }
                    catch (final Exception e)
                    {
                        LOG_ADAPTER.warn( "Erreur au flush du formulaire ", this.formName, e );
                    }
                }
            }

            this.setBean();

            this.listener.manageBean( this.getBean(), context, request, response );

            String validRedirect = this.form.getValidRedirect();

            LOG_ADAPTER.trace( StringTools.concat( "remplacement de paramètres de la requête : ", HttpTools.getUrl( request ) ) );

            final Matcher matcher = pattern.matcher( validRedirect );

            while (matcher.find())
            {
                validRedirect = validRedirect.replace( matcher.group(), request.getParameter( matcher.group( 1 ) ) );
                LOG_ADAPTER.trace( "Url de redirection : ", validRedirect );
            }

            if (!(response.isCommitted() || validRedirect == null || validRedirect.isEmpty()))
            {
                LOG_ADAPTER.debug( "formulaire valide, redirection : " + validRedirect );
                response.sendRedirect( request.getContextPath() + validRedirect );
            }
            else
            {
                LOG_ADAPTER.debug( "formulaire invalide" );
            }

        }
        else
        {
            LOG_ADAPTER.debug( "formulaire invalide " );
            for (final Entry<String, FieldContext<T, ?>> f : this.fieldContexts.entrySet())
            {
                if (!f.getValue().isValid())
                {
                    LOG_ADAPTER.debug( "champ ", f.getKey(), " invalide ", f.getValue().getInvalidRules() );
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ecosystems.weblib.html.form.config.FormContext#isSessionPersistent()
     */
    @Override
    public boolean isSessionPersistent()
    {
        return this.form.isSessionPersistent();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ecosystems.weblib.html.form.config.FormContext#flushBean(javax.servlet.http.HttpServletRequest)
     */
    @Override
    public void flushBean(final HttpServletRequest request)
    {
        for (final Entry<String, FieldContext<T, ?>> e : this.fieldContexts.entrySet())
        {
            HTTPDataManager.flush( request, FieldContextImpl.getSessionParamName( this.getFormName(), e.getValue().getName() ) );
        }

    }

    /**
     * Définit les champs du contexte.
     * 
     * @param request requête
     * @throws InitFormException Levée si le formulaire n'est pas initialisé
     */
    private void setFields() throws InitFormException
    {

        LOG_ADAPTER.trace( "setfields", " / rvp.getParameter( ", POSTED_KEY_NAME, " )" );
        LOG_ADAPTER.trace( "setfields", " / rvp== ", rvp.toString() );

        this.reset = (this.formName.equals( rvp.getParameter( RESET_KEY_NAME ) ));
        this.posted = !this.reset && (this.formName.equals( rvp.getParameter( POSTED_KEY_NAME ) ));

        LOG_ADAPTER.debug( "formulaire : ", form.getFormName(), " / reset : ", Boolean.valueOf( this.reset ) );
        LOG_ADAPTER.debug( "formulaire : ", form.getFormName(), " / posted : ", Boolean.valueOf( this.posted ) );

        for (final Field f : this.form.getField())
        {
            LOG_ADAPTER.trace( "field : ", f.getName(), " = ", rvp.getParameter( f.getName() ) );
            if (this.fieldContexts.containsKey( f.getName() ))
            {
                throw new InitFormException( "Le champ " + f.getName() + " est déclaré plusieurs fois dans le formulaire " + this.formName );
            }
            // this.valid = this.valid && ( ( fc.isRequired() && fc.isValid() ) // !§%$! de formateur !
            // || ( !fc.isRequired() && ( !fc.isPosted() || fc.isValid() ) ) );
            Converter<?> converter;
            try
            {
                converter = ClassTools.loadClass( LOG_ADAPTER, f.getConverter() );
                // converter.setRequest( f.getName(), request );
                if (rvp.hasParameter( f.getName() ))
                {
                    this.fieldContexts.put( f.getName(), this.infferFieldContext( converter, f, rvp.getParameter( f.getName() ), rvp ) );
                }
                else if (rvp.hasFile( f.getName() ))
                {
                    this.fieldContexts.put( f.getName(), this.infferFieldContext( converter, f, rvp.getFile( f.getName() ), rvp ) );
                }
                else
                {
                    LOG_ADAPTER.trace( "le champ ", f.getName(), " n'a pas de valeur, null défini" );
                    this.fieldContexts.put( f.getName(), this.infferFieldContext( converter, f, null, rvp ) );
                }
            }
            catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ConversionException e)
            {
                throw new InitFormException( e );
            }

        }
    }

    /**
     * Récupère un contexte de champ de formulaire.
     * 
     * @param <V> Type de la valeur gérée par ce champ
     * @param converter convertisseur utilisé
     * @param f champ tel que décrit dans la configuration
     * @param request requête courante
     * @return Contexte de champ associé au champ et à la requête
     * @throws InitFormException Levée si l'initialisation du formulaire échoue
     * @throws ConversionException Levée si une conversion échoue
     */
    private <V> FieldContext<T, V> infferFieldContext(final Converter<V> converter, final Field f, final Object value,
            final RequestValueProvider rvp) throws InitFormException, ConversionException
    {
        return new FieldContextImpl<>( this, f, value, rvp, converter, this.reset );
    }

    /**
     * Défini le bean de formulaire en fonction des valeurs http données par le contexte de champ.
     * 
     * @throws InitFormException Levée si l'initailisation du formulaire échoue
     */
    private void setBean() throws InitFormException
    {
        try
        {
            this.bean = this.beanClass.newInstance();
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            throw new InitFormException( "Classe Bean impossible à instancier", e ); //$NON-NLS-1$
        }

        for (final Entry<String, FieldContext<T, ?>> e : this.fieldContexts.entrySet())
        {
            try
            {

                LOG_ADAPTER.trace( "e.getValue().getName() == ", e.getValue().getName() );
                LOG_ADAPTER.trace( "e.getValue().getValue() == ", e.getValue().getValue() );
                LOG_ADAPTER.trace( "e.getValue().gettype() == ", e.getValue().getType() );
                if (!(e.getValue().getType() == null && e.getValue().getValue() == null))
                {
                    final String setter = StringTools.toSetter( e.getValue().getName() );
                    // throw new IllegalArgumentException( "valeur de type composite null, impossible de déterminer le type" );

                    final Class<?> type = (e.getValue().getType() != null) ? e.getValue().getType() : e.getValue().getValue().getClass();
                    final Object value = e.getValue().getValue();
                    LOG_ADAPTER.trace( "appel du setter : ", setter, ", type = ", type, ", value type = ", value != null ? value.getClass()
                            : "null", ", value = ", value );
                    final Method m = MethodUtils.getMatchingAccessibleMethod( this.beanClass, setter, new Class[] { type } );
                    if (m == null)
                    {
                        throw new NoSuchMethodException( StringTools.concat( this.beanClass.toString(), ".", setter, "(", type.toString(),
                                ")" ) );
                    }
                    m.invoke( this.bean, value );
                }

            }
            catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException
                    | SecurityException | ConversionException | NullPointerException e1)
            {
                final String message = StringTools.concat( "Accesseur (", StringTools.toSetter( e.getValue().getName() ), ") du type de ",
                        this.bean.getClass().getCanonicalName(), " impossible à appeller" );
                throw new InitFormException( message, e1 );
            }

        }

    }

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
    @Override
    public void loadBean(final T bean1, final HttpServletRequest request) throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException, InitFormException, ConversionException
    {
        this.loadBean( bean1, request, RequestPolicy.SESSION );
    }

    @Override
    public void loadBean(final T bean1, final HttpServletRequest request, final RequestPolicy policy) throws NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InitFormException,
            ConversionException
    {
        this.bean = bean1;
        Object value;
        Method getter;

        for (final Entry<String, FieldContext<T, ?>> e : this.fieldContexts.entrySet())
        {
            final String name = FieldContextImpl.getSessionParamName( this.formName, e.getKey() );

            getter = this.bean.getClass().getMethod( StringTools.toGetter( e.getValue().getName() ) );
            value = getter.invoke( this.bean );

            // TODO
            // request.getSession().setAttribute( name, value );
            // this.fieldContexts.put( e.getKey(), ( (FieldContextImpl<T, ?>) e.getValue() ).cloneWithOtherValue( this,
            // request, value ) );
            this.infferAndPutInFieldContext( e.getKey(), e.getValue(), value, rvp );
            HTTPDataManager.setData( request, name, value, policy );

        }

    }

    @SuppressWarnings("unchecked")
    private <U> void infferAndPutInFieldContext(final String key, final FieldContext<T, U> fc, final Object value,
            final RequestValueProvider rvp1) throws InitFormException, ConversionException
    {
        try
        {
            this.fieldContexts.put( key, fc.cloneWithOtherValue( this, rvp1, (U) value ) );
        }
        catch (final ClassCastException e)
        {
            throw new ConversionException( value, fc.getType(), e );
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ecosystems.weblib.html.form.config.FormContext#hasPersistentData()
     */
    @Override
    public boolean hasPersistentData()
    {
        LOG_ADAPTER.trace( "hasPersistentData, test ..." ); //$NON-NLS-1$
        boolean ret = false;
        for (final FieldContext<T, ?> fc : this.fieldContexts.values())
        {
            try
            {
                LOG_ADAPTER.trace( "hasPersistentData, test field :", fc.getName(), //$NON-NLS-1$
                        " / value = ", fc.getValue() ); //$NON-NLS-1$
                ret = ret || (fc.getValue() != null);
            }
            catch (final ConversionException e)
            {
                LOG_ADAPTER.warn( "hasPersistentData : conversion fail, return false" ); //$NON-NLS-1$
                return false;
            }
        }
        LOG_ADAPTER.trace( "hasPersistentData, return value = ", Boolean.valueOf( ret ) ); //$NON-NLS-1$
        return ret;
    }

}
