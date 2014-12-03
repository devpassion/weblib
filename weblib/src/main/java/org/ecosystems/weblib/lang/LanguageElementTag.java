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
package org.ecosystems.weblib.lang;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.ecosystems.weblib.log.LoggerAdapter;

/**
 * Tag représentant un élément de langage.
 */
public class LanguageElementTag extends TagSupport
{

    private static final long serialVersionUID = 7185034527311747953L;

    private String namespace;

    private String key;

    private Locale locale = null;

    private final Map<String, Object> parameters = new HashMap<String, Object>();

    private LanguageConverter convertor;

    /**
     * Espace de nom de l'élément de langage.
     * 
     * @return the namespace
     */
    public String getNamespace()
    {
        return this.namespace;
    }

    /**
     * Définit l'espace de nom de l'élément de langue affiché.
     * 
     * @param namespace the namespace to set
     */
    public void setNamespace(final String namespace)
    {
        this.namespace = namespace;
    }

    /**
     * Récupère la clé de l'élément de langue.
     * 
     * @return the key
     */
    public String getKey()
    {
        return this.key;
    }

    /**
     * Définit la clé de l'élément de langue.
     * 
     * @param key the key to set
     */
    public void setKey(final String key)
    {
        this.key = key;
    }

    /**
     * Récupère la locale.
     * 
     * TODO : sans effet
     * 
     * @return the locale
     */
    public String getLang()
    {
        return this.locale.getLanguage();
    }

    /**
     * Définit la locale.
     * 
     * @param language Code ISO de la langue à définir
     */
    public void setLang(final String language)
    {
        this.locale = new Locale( language );
    }

    /**
     * Ajoute un paramètre de langue.
     * 
     * @param name nom du paramètre
     * @param value valeur du paramètre
     */
    public void addParameter(final String name, final Object value)
    {
        this.parameters.put( name, value );
    }

    @Override
    public int doStartTag()
    {
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() throws JspException
    {
        if (this.convertor == null)
        {
            try
            {
                this.convertor = LanguageManager.getConvertor();
            }
            catch (final NoSuchLanguageConverterException e)
            {
                throw new JspException( e );
            }

        }

        // this.locale = (Locale) this.pageContext.getAttribute( LanguageManager.getLanguageParameterName(), PageContext.REQUEST_SCOPE );

        try
        {
            this.locale = LanguageManager.getCurrentLocale( pageContext );
        }
        catch (final NoLanguageAvailableException e2)
        {
            throw new JspException( e2 );
        }

        this.getLogger().trace( "Langue : ", this.pageContext.getAttribute( //$NON-NLS-1$
                LanguageManager.getLanguageParameterName(), PageContext.REQUEST_SCOPE ) );

        String str;
        this.getLogger().trace( "clé : ", this.key ); //$NON-NLS-1$
        try
        {
            str = this.convertor.getTraduction( this.namespace, this.key, this.locale, this.parameters );
            this.getLogger().debug( "traduction : ", str, " ( ", //$NON-NLS-1$ //$NON-NLS-2$
                    this.locale.getLanguage(), " ) " ); //$NON-NLS-1$
        }
        catch (NoSuchTraductionException | NoSuchTraductionRessourceException e1)
        {
            throw this.getLogger().logAndReturnJspException( e1 );
        }

        try
        {
            this.pageContext.getOut().write( str );
        }
        catch (final IOException e)
        {
            throw this.getLogger().logAndReturnJspException( e );
        }
        return super.doEndTag();
    }

    private LoggerAdapter logAdp;

    /**
     * Récupère un logger.
     * 
     * @return logger associé à la classe.
     */
    protected LoggerAdapter getLogger()
    {
        if (this.logAdp == null)
        {
            this.logAdp = new LoggerAdapter( this.getClass() );
        }
        return this.logAdp;
    }

}
