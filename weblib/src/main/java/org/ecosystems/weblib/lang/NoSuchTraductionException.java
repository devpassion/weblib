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

import org.ecosystems.lib.tools.StringTools;

/**
 * Exception levée lorsqu'une traduction est introuvable.
 */
public class NoSuchTraductionException extends Exception
{

    private static final long serialVersionUID = 1209184429884352136L;

    private final String namespace;

    private final String key;

    private final Locale locale;

    /**
     * Nouvelle exception à partir d'un espace de nom, d'une clé et d'une locale.
     * 
     * @param namespace espace de nom de l'élément de langue
     * @param key clé de l'élément de langue
     * @param locale locale demandée pour la traduction
     */
    public NoSuchTraductionException(final String namespace, final String key, final Locale locale)
    {
        super();
        this.namespace = namespace;
        this.key = key;
        this.locale = locale;
    }

    /**
     * Nouvelle exception à partir d'un espace de nom, d'une clé, d'une locale et d'une cause.
     * 
     * @param namespace espace de nom de l'élément de langue
     * @param key clé de l'élément de langue
     * @param locale locale demandée pour la traduction
     * @param t cause de l'exception
     */
    public NoSuchTraductionException(final String namespace, final String key, final Locale locale, final Throwable t)
    {
        super( t );
        this.namespace = namespace;
        this.key = key;
        this.locale = locale;
    }

    @Override
    public String getMessage()
    {
        return StringTools.concat( "Traduction intouvable : ", StringTools.getStringOrNull( this.namespace ), " : ",
                StringTools.getStringOrNull( this.key ), " : ", StringTools.getStringOrNull( this.locale ) );
    }

}
