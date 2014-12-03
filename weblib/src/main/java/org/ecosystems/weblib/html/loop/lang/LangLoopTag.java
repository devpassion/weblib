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
package org.ecosystems.weblib.html.loop.lang;

import java.util.Iterator;
import java.util.Locale;
import java.util.MissingResourceException;

import javax.servlet.jsp.JspException;

import org.ecosystems.weblib.html.loop.AbstractLoopTag;
import org.ecosystems.weblib.lang.LanguageManager;
import org.ecosystems.weblib.lang.NoLanguageAvailableException;
import org.ecosystems.weblib.log.LoggerAdapter;

/**
 * Boucle sur les langues existantes.
 */
public class LangLoopTag extends AbstractLoopTag
{

    private static final long serialVersionUID = 3164070317732560039L;

    private static final LoggerAdapter LOG_ADAPTER = new LoggerAdapter( LangLoopTag.class );

    private Iterator<Locale> langs = null;

    private Locale currentLang;

    private boolean current;

    private boolean all = false;

    @Override
    protected boolean endLoop() throws JspException
    {
        LOG_ADAPTER.trace( "endLoop itération = " ); //$NON-NLS-1$
        if (this.langs.hasNext())
        {
            this.currentLang = this.langs.next();
            LOG_ADAPTER.trace( "currentLang = ", this.currentLang ); //$NON-NLS-1$
            try
            {
                this.current = (this.currentLang.equals( LanguageManager.getCurrentLocale( this.pageContext ) ));
            }
            catch (final MissingResourceException e)
            {
                throw new JspException( e );
            }
            catch (final NoLanguageAvailableException e)
            {
                throw new JspException( e );
            }
            return false;
        }
        return true;
    }

    @Override
    protected boolean startLoop() throws JspException
    {
        LOG_ADAPTER.trace( "startLoop" ); //$NON-NLS-1$
        try
        {
            this.langs = (isAll()) ? LanguageManager.getAllLocales().iterator() : LanguageManager.getAvailablesLocales().iterator();
            if (this.langs.hasNext())
            {
                this.currentLang = this.langs.next();
                LOG_ADAPTER.trace( "currentLang = ", this.currentLang ); //$NON-NLS-1$
                return true;
            }
            return false;
        }
        catch (final NoLanguageAvailableException e)
        {
            throw new JspException( e );
        }
    }

    /**
     * Récupère la locale courante.
     * 
     * @return the currentLang
     */
    Locale getCurrentLang()
    {
        return this.currentLang;
    }

    /**
     * Indique si la langue courante est la langue sélectionnée.
     * 
     * @return vrai si la langue courante est la langue sélectionnée
     */
    boolean isCurrent()
    {
        return this.current;
    }

    public boolean isAll()
    {
        return all;
    }

    public void setAll(final boolean all)
    {
        this.all = all;
    }

}
