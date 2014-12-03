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
package org.ecosystems.weblibtests.model;

import java.util.HashMap;
import java.util.Map;

import org.ecosystems.lib.sql.FixItem;

/**
 * Catégorie de logo.
 */
public final class Categorie extends FixItem
{

    /**
     * Espace de nom des chaînes de langues de categorie
     */
    public static final String NAMESPACE = "category";

    /**
     * Produits et services
     */
    public static final Categorie PRODUCT = new Categorie( NAMESPACE, "product", "product" );

    /**
     * Alimentation
     */
    public static final Categorie ALIMENTATION = new Categorie( NAMESPACE, "alimentation", "alimentation" );

    /**
     * Cosmétique
     */
    public static final Categorie COSMETIQUE = new Categorie( NAMESPACE, "cosmetique", "cosmetique" );

    /**
     * Produits d'entretien
     */
    public static final Categorie ENTRETIEN = new Categorie( NAMESPACE, "entretien", "entretien" );

    /**
     * Matériaux
     */
    public static final Categorie MATERIAUX = new Categorie( NAMESPACE, "materiaux", "materiaux" );

    /**
     * Emballage
     */
    public static final Categorie EMBALLAGE = new Categorie( NAMESPACE, "emballage", "emballage" );

    /**
     * Autre
     */
    public static final Categorie OTHER = new Categorie( NAMESPACE, "other", "other" );

    public static final Map<String, Categorie> itemsMap = new HashMap<String, Categorie>();

    /**
     * Nouvelle catégorie.
     * 
     * @param namespace espace de noms de langues de la traduction
     * @param name Nom de la catégorie
     * @param langKey clé de langue de la catégorie
     */
    private Categorie(final String namespace, final String name, final String langKey)
    {
        super( namespace, name, langKey );

    }

    @Override
    public FixItem fromName(String itemName)
    {
        if (this.itemsMap.size() == 0)
        {
            itemsMap.put( Categorie.PRODUCT.getName(), Categorie.PRODUCT );
            itemsMap.put( Categorie.ALIMENTATION.getName(), Categorie.ALIMENTATION );
            itemsMap.put( Categorie.COSMETIQUE.getName(), Categorie.COSMETIQUE );
            itemsMap.put( Categorie.ENTRETIEN.getName(), Categorie.ENTRETIEN );
            itemsMap.put( Categorie.MATERIAUX.getName(), Categorie.MATERIAUX );
            itemsMap.put( Categorie.EMBALLAGE.getName(), Categorie.EMBALLAGE );
            itemsMap.put( Categorie.OTHER.getName(), Categorie.OTHER );
        }
        return itemsMap.get( itemName );
    }

}
