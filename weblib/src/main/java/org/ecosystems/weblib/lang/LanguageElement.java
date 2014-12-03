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

import java.util.Locale;

/**
 * Element de langue
 * 
 */
public class LanguageElement
{
    private String langKey;
    private Locale lang;
    private String namespace;
    private String traduction;

    public LanguageElement(final Locale lang, final String namespace, final String langKey, final String traduction)
    {
        super();
        this.langKey = langKey;
        this.lang = lang;
        this.namespace = namespace;
        this.traduction = traduction;
    }

    /**
     * @return the langKey
     */
    public String getLangKey()
    {
        return langKey;
    }

    /**
     * @param langKey the langKey to set
     */
    public void setLangKey(final String langKey)
    {
        this.langKey = langKey;
    }

    /**
     * @return the lang
     */
    public Locale getLang()
    {
        return lang;
    }

    /**
     * @param lang the lang to set
     */
    public void setLang(final Locale lang)
    {
        this.lang = lang;
    }

    /**
     * @return the namespace
     */
    public String getNamespace()
    {
        return namespace;
    }

    /**
     * @param namespace the namespace to set
     */
    public void setNamespace(final String namespace)
    {
        this.namespace = namespace;
    }

    /**
     * @return the traduction
     */
    public String getTraduction()
    {
        return traduction;
    }

    /**
     * @param traduction the traduction to set
     */
    public void setTraduction(final String traduction)
    {
        this.traduction = traduction;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((lang == null) ? 0 : lang.hashCode());
        result = prime * result + ((langKey == null) ? 0 : langKey.hashCode());
        result = prime * result + ((namespace == null) ? 0 : namespace.hashCode());
        result = prime * result + ((traduction == null) ? 0 : traduction.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof LanguageElement))
        {
            return false;
        }
        final LanguageElement other = (LanguageElement) obj;
        if (lang == null)
        {
            if (other.lang != null)
            {
                return false;
            }
        }
        else if (!lang.equals( other.lang ))
        {
            return false;
        }
        if (langKey == null)
        {
            if (other.langKey != null)
            {
                return false;
            }
        }
        else if (!langKey.equals( other.langKey ))
        {
            return false;
        }
        if (namespace == null)
        {
            if (other.namespace != null)
            {
                return false;
            }
        }
        else if (!namespace.equals( other.namespace ))
        {
            return false;
        }
        if (traduction == null)
        {
            if (other.traduction != null)
            {
                return false;
            }
        }
        else if (!traduction.equals( other.traduction ))
        {
            return false;
        }
        return true;
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
        builder.append( "LanguageElement [langKey=" );
        builder.append( langKey );
        builder.append( ", lang=" );
        builder.append( lang );
        builder.append( ", namespace=" );
        builder.append( namespace );
        builder.append( ", traduction=" );
        builder.append( traduction );
        builder.append( "]" );
        return builder.toString();
    }

}
