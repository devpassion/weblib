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
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.StringUtils;
import org.ecosystems.weblib.html.form.ManageFormException;
import org.ecosystems.weblib.html.form.config.generated.Field;
import org.ecosystems.weblib.html.form.config.generated.Form;
import org.ecosystems.weblib.html.form.config.generated.Forms;
import org.ecosystems.weblib.log.LoggerAdapter;
import org.ecosystems.weblib.tools.HttpTools;
import org.xml.sax.SAXException;

/**
 * Implémentation de {@link FormContextProvider}.
 */
public class FormContextProviderImpl implements FormContextProvider
{
    /**
     * Nom du package contenant les classes parsées par jaxb.
     */
    private static final String JAXB_PACKAGE = "org.ecosystems.weblib.html.form.config.generated"; //$NON-NLS-1$

    private static final LoggerAdapter LOGGER_ADAPTER = new LoggerAdapter( FormContextProviderImpl.class );

    private Forms formConfig;

    private final Map<String, Form> formMapping = new Hashtable<>();

    private final Map<String, Class<?>> formBeanMapping = new Hashtable<>();

    @Override
    public void loadConfig(final File configFile) throws InitFormException, FileNotFoundException, BadFormBeanClassException
    {
        if (!configFile.exists())
        {
            throw new FileNotFoundException( "Fichier de configuration de formulaire introuvable" );
        }

        JAXBContext jc;
        try
        {
            jc = JAXBContext.newInstance( JAXB_PACKAGE );
            final Unmarshaller unmarshaller = jc.createUnmarshaller();

            final SchemaFactory sf = SchemaFactory.newInstance( javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI );
            final Schema schema = sf.newSchema( Thread.currentThread().getContextClassLoader().getResource( "forms_schema.xsd" ) );
            unmarshaller.setSchema( schema );
            formConfig = (Forms) unmarshaller.unmarshal( configFile );
            LOGGER_ADAPTER.info( "fichier " + configFile.toString() + " chargé" );
            for (final Form f : formConfig.getForm())
            {
                LOGGER_ADAPTER.trace( "formulaire : " + f.toString() );
                for (final Field field : f.getField())
                {
                    if (field == null)
                        throw new InitFormException( "Champ de formulaire " + f.getFormName() + " null" );
                    LOGGER_ADAPTER.trace( field.getName() + "," );
                }
                this.formMapping.put( f.getFormName(), f );

                LOGGER_ADAPTER.info( "Chargement du contexte du formulaire " + f.getFormName() );

                if (f.getFormName() == null || f.getFormName().isEmpty())
                {
                    throw new InitFormException( "Formulaire sans som trouvé dans le fichier " + configFile.getPath() );
                }

                if (f.getBean() == null || f.getBean().isEmpty())
                {
                    throw new InitFormException( "Formulaire " + f.getFormName() + " trouvé dans le fichier " + configFile.getPath()
                            + " sans bean" );
                }

                try
                {
                    this.formBeanMapping.put( f.getFormName(), Thread.currentThread().getContextClassLoader().loadClass( f.getBean() ) );
                }
                catch (ClassNotFoundException | ClassCastException e)
                {
                    throw new BadFormBeanClassException( f.getFormName(), null, e );
                }

            }

            LOGGER_ADAPTER.debug( "Contexte des formulaires chargé avec succès" ); //$NON-NLS-1$
        }
        catch (final JAXBException | SAXException e)
        {
            throw new InitFormException( "Chargement de la configuration de formulaires échoué", e );
        }
        catch (final Exception e)
        {
            throw new InitFormException( "Chargement de la configuration de formulaires échoué à cause d'une erreur inatendue", e );
        }

    }

    // private final Map<ServletRequest, FormContextImpl<?>> contexts = new HashMap<ServletRequest, FormContextImpl<?>>();

    private static final String CONTEXT_MAP_ATTR_NAME = "org.ecosystems.weblib.html.form.config.CONTEXT_MAP";

    @Override
    public <T> FormContext<T> getFormContext(final String formName, final Class<T> beanClass, final ServletRequest request)
            throws InitFormException, BadFormBeanClassException
    {

        LOGGER_ADAPTER.trace( "getFormContext(", formName, ", ", beanClass, " ,", request.toString(), ")" );

        final Form form = this.formMapping.get( formName );

        if (request.getAttribute( CONTEXT_MAP_ATTR_NAME ) == null)
        {
            LOGGER_ADAPTER.trace( "getFormContext(", formName, ", ", beanClass, " ,", request.toString(),
                    "); CONTEXT_MAP_ATTR_NAME == null" );
            request.setAttribute( CONTEXT_MAP_ATTR_NAME, new HashMap<String, FormContext<?>>() );
            // request.setAttribute( CONTEXT_MAP_ATTR_NAME, new FormContextImpl<T>( form, (HttpServletRequest) request, beanClass ) );
        }

        final Object oAttribute = request.getAttribute( CONTEXT_MAP_ATTR_NAME );

        if (!(Map.class.isAssignableFrom( oAttribute.getClass() )))
        {
            throw new InitFormException( "La requête contient un argument " + CONTEXT_MAP_ATTR_NAME + " qui n'est pas de type Map" );
        }

        final Map<String, FormContext<?>> fcMap = (Map<String, FormContext<?>>) oAttribute;

        if (!fcMap.containsKey( formName ))
        {
            fcMap.put( formName, new FormContextImpl<T>( form, (HttpServletRequest) request, beanClass ) );
        }

        // if (!(oAttribute instanceof FormContext<?>))
        // {
        // throw new InitFormException( "La requête contient un argument " + CONTEXT_MAP_ATTR_NAME
        // + " qui n'est pas de type FormContext<?> (" + oAttribute.getClass().toString() + ")", new ConversionException(
        // oAttribute, FormContext.class ) );
        // }

        // final FormContext<T> ret = (FormContext<T>) oAttribute;
        final FormContext<T> ret = (FormContext<T>) fcMap.get( formName );

        if (ret == null)
        {
            throw new InitFormException( "Contexte de formulaire introuvable : " + formName );
        }

        if (!ret.getFormName().equals( formName ))
        {
            throw new InitFormException( "Formulaire " + formName + " demandé mais contexte de " + ret.getFormName() + " renvoyé" );
        }
        return ret;

    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> FormContext<T> getFormContext(final String formName, final ServletRequest request) throws InitFormException,
            BadFormBeanClassException
    {
        try
        {
            // L'erreur de cast est récupérée et décrite, ce qui justifie le suppressWarning
            final FormContext<T> formContext = this.getFormContext( formName, (Class<T>) this.formBeanMapping.get( formName ), request );
            LOGGER_ADAPTER.trace( "getFormContext(", formName, ",", request.toString(), ") : return ", formContext.getFormName() );
            return formContext;
        }
        catch (final ClassCastException e)
        {
            throw new BadFormBeanClassException( formName, this.formBeanMapping.get( formName ), e );
        }
    }

    /**
     * Récupère le nom du formulaire qui sera celui traité.
     * ATTENTION : la fonction ne vérifie pas si le formulaire existe dans la configuration.
     * 
     * Si on a un paramètre get ou post dont le nom est {@link FormContext#POSTED_KEY_NAME}.
     * Si on a un paramètre get et un paramètre post, c'est le paramètre post qui est prioritaire.
     * 
     * @param request
     * 
     * @return formulaire posté, ou null si rien n'est posté.
     * @throws IOException
     * @throws FileUploadException
     */
    private String getCurrentFormName(final HttpServletRequest request) throws FileUploadException, IOException
    {
        final RequestValueProvider rvp = RequestValueProviderFactory.getRVPProvider( request );
        final Object formName = rvp.getParameter( FormContext.POSTED_KEY_NAME );
        LOGGER_ADAPTER.debug( "found form : ", formName );
        if (formName != null)
        {
            if (formName.getClass().isArray())
            {
                LOGGER_ADAPTER.trace( "form is array : ", formName );
                // Si on a un tableau de deux éléments, on prend celui qui n'est pas dans l'url (il faut ausi enlver pk de l'url de
                // redirection)
                // Si on est en méthode GET, pas de quartier, donc il ne devrai pas y avoir de tableau
                // Si on est en post, on vire le champ get

                final String[] aFormName = (String[]) formName;

                LOGGER_ADAPTER.trace( "method == ", request.getMethod() );
                if ("POST".equals( request.getMethod() ) && aFormName.length == 2)
                {
                    LOGGER_ADAPTER.trace( "query == ", request.getQueryString() );
                    if (StringUtils.equals( aFormName[0], aFormName[1] ) && aFormName[0] != null)
                    {
                        return aFormName[0];
                    }

                    // TODO : ici, deux valeurs différentes, il faut virer les apramètres
                    final Map<String, String> query = HttpTools.getQueryMap( request.getQueryString() );
                    final String pk = query.get( FormContext.POSTED_KEY_NAME );
                    if (pk != null && pk.equals( aFormName[0] ))
                    {
                        return aFormName[1];
                    }
                    return aFormName[0];
                }
                LOGGER_ADAPTER.warn( "ATTENTION : tableau trouvé pour formulaire GET : ", aFormName );
                return null;
            }
            LOGGER_ADAPTER.trace( "form is string : ", formName );
            return formName.toString();
        }
        return null;

    }

    @Override
    public void executeValidForm(final ServletContext context, final HttpServletRequest request, final HttpServletResponse response)
            throws InitFormException, ManageFormException, IOException, BadFormBeanClassException
    {

        try
        {
            LOGGER_ADAPTER.debug( "executeValidForm ( url = ", request.getRequestURL(), "?", request.getQueryString(), " )..." );

            // throw new NotImplementedException( "Code à finir" );
            final String formName = this.getCurrentFormName( request );
            if (formName != null)
            {
                if (this.formMapping.containsKey( formName ))
                {
                    this.getFormContext( formName.toString(), request ).executeIfValid( context, request, response );
                }
                else
                {
                    LOGGER_ADAPTER.warn( "Formulaire inexistant : ", formName );
                }
            }

        }
        catch (final FileUploadException e)
        {
            throw new InitFormException( "Une erreur d'upload a été levée", e );
        }

    }

    @Override
    public Forms getConfig()
    {
        return formConfig;
    }

}
