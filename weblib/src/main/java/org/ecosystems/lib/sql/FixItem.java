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
package org.ecosystems.lib.sql;

import javax.servlet.jsp.PageContext;

import org.ecosystems.weblib.lang.LanguageManager;
import org.ecosystems.weblib.lang.NoLanguageAvailableException;
import org.ecosystems.weblib.lang.NoSuchLanguageConverterException;
import org.ecosystems.weblib.lang.NoSuchTraductionException;
import org.ecosystems.weblib.lang.NoSuchTraductionRessourceException;
import org.ecosystems.weblib.lang.NotYetDefinedLocaleException;

/**
 * Item de liste fixe.
 */
public abstract class FixItem
{

    /**
     * Nom de l'item.
     */
    private final String name;

    /**
     * Clée de langue du nom de l'item.
     */
    private final String langKey;

    /**
     * Espace de noms de langues du nom de l'item.
     */
    private final String namespace;

    /**
     * Crée un nouvel item fixe.
     * 
     * @param namespace Espace de nom de traduction
     * @param name nom de l'item
     * @param langKey Chaine de langue de l'item
     */
    protected FixItem(final String namespace, final String name, final String langKey)
    {
        super();
        this.namespace = namespace;
        this.name = name;
        this.langKey = langKey;
    }

    /**
     * Récupère l'item qui possède le nom spécifie.
     * 
     * @param itemName nom de l'item
     * @return item, ou null si il n'est pas trouvé
     */
    public abstract FixItem fromName(String itemName);

    /**
     * Récupère le nom de l'item.
     * 
     * @return the name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Récupère la clé de l'élément de langue de l'item.
     * 
     * @return the langKey
     */
    public String getLangKey()
    {
        return this.langKey;
    }

    /**
     * Récupère la traduction du nom.
     * 
     * @deprecated Utiliser plutôt le nom sotcké dans la base.
     * 
     * @return Nom traduit dans la langue courante
     * @throws NotYetDefinedLocaleException Levée si la locale courante n'est pas encore disponible
     * @throws NoSuchTraductionRessourceException Levée si une ressource de traduction est indisponible
     * @throws NoSuchTraductionException Levée si la traduction est introuvable
     * @throws NoSuchLanguageConverterException Levée si une traduction est introuvable
     */
    @Deprecated
    public String getNameTraduction() throws NoSuchTraductionException, NoSuchTraductionRessourceException, NotYetDefinedLocaleException,
            NoSuchLanguageConverterException

    {
        return LanguageManager.getTraduction( this.namespace, this.langKey );
    }

    /**
     * Récupère la traduction du nom
     * 
     * @deprecated Utiliser plutôt le nom sotcké dans la base.
     * 
     * @param context Contexte de la page
     * 
     * @return Nom traduit dans la langue courante
     * @throws NoSuchTraductionRessourceException Levée si une ressource de traduction est indisponible
     * @throws NoSuchTraductionException Levée si la traduction est introuvable
     * @throws NoSuchLanguageConverterException Levée si une traduction est introuvable
     * @throws NoLanguageAvailableException Levée si le language demandé n'est pas disponible
     */
    @Deprecated
    public String getNameTraduction(final PageContext context) throws NoSuchTraductionException, NoSuchTraductionRessourceException,
            NoSuchLanguageConverterException, NoLanguageAvailableException

    {
        return LanguageManager.getTraduction( context, this.namespace, this.langKey );
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
        result = prime * result + ((this.langKey == null) ? 0 : this.langKey.hashCode());
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
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
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final FixItem other = (FixItem) obj;
        if (this.langKey == null)
        {
            if (other.langKey != null)
                return false;
        }
        else if (!this.langKey.equals( other.langKey ))
            return false;
        if (this.name == null)
        {
            if (other.name != null)
                return false;
        }
        else if (!this.name.equals( other.name ))
            return false;
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
        return this.name;
    }

}
