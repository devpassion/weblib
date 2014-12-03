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

import java.io.File;
import java.util.Locale;
import java.util.Set;

/**
 * Bean de logo.
 */
public class Logo
{
    private String name;

    private Scope scope;

    private Set<Categorie> categories;

    private File imgFile;

    private String comments;

    private Set<Forme> formes;

    private String zone;

    private String country;

    private String countries;

    private String Link;

    private String linkInfo;

    private File image;

    private Locale lang;

    /**
     *
     */
    public Logo()
    {
        super();
    }

    /**
     * @param name Nom du logo
     * @param scope portée du logo
     * @param categories catégories du logo
     * @param imgFile Chemin de l'image
     * @param description Description de l'image
     * @param formes Formes de l'image
     * @param zone zone de l'image
     * @param country pays de l'image
     * @param countries pays multiples de l'image
     */
    public Logo(final String name, final Scope scope, final Set<Categorie> categories, final File imgFile, final String description,
            final Set<Forme> formes, final String zone, final String country, final String countries)
    {
        super();
        this.name = name;
        this.scope = scope;
        this.categories = categories;
        this.imgFile = imgFile;
        this.comments = description;
        this.formes = formes;
        this.zone = zone;
        this.country = country;
        this.countries = countries;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * @param name the name to set
     */
    public void setName(final String name)
    {
        this.name = name;
    }

    /**
     * @return the scope
     */
    public Scope getScope()
    {
        return this.scope;
    }

    /**
     * @param scope the scope to set
     */
    public void setScope(final Scope scope)
    {
        this.scope = scope;
    }

    /**
     * @return the categorie
     */
    public Set<Categorie> getCategory()
    {
        return this.categories;
    }

    /**
     * @param categorie the categorie to set
     */
    public void setCategory(final Set<Categorie> categorie)
    {
        this.categories = categorie;
    }

    /**
     * @return the imgPath
     */
    public File getImgFile()
    {
        return this.imgFile;
    }

    /**
     * @param imgFile the imgPath to set
     */
    public void setImgFile(final File imgFile)
    {
        this.imgFile = imgFile;
    }

    /**
     * @return the forme
     */
    public Set<Forme> getForme()
    {
        return this.formes;
    }

    /**
     * @param forme the forme to set
     */
    public void setForme(final Set<Forme> forme)
    {
        this.formes = forme;
    }

    /**
     * @return the zone
     */
    public String getZone()
    {
        return this.zone;
    }

    /**
     * @param zone the zone to set
     */
    public void setZone(final String zone)
    {
        this.zone = zone;
    }

    /**
     * @return the country
     */
    public String getCountry()
    {
        return this.country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(final String country)
    {
        this.country = country;
    }

    /**
     * @return the countries
     */
    public String getCountries()
    {
        return this.countries;
    }

    /**
     * @param countries the countries to set
     */
    public void setCountries(final String countries)
    {
        this.countries = countries;
    }

    public String getLink()
    {
        return Link;
    }

    public void setLink(final String link)
    {
        Link = link;
    }

    public String getLinkInfo()
    {
        return linkInfo;
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
        result = prime * result + ((Link == null) ? 0 : Link.hashCode());
        result = prime * result + ((categories == null) ? 0 : categories.hashCode());
        result = prime * result + ((comments == null) ? 0 : comments.hashCode());
        result = prime * result + ((countries == null) ? 0 : countries.hashCode());
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        result = prime * result + ((formes == null) ? 0 : formes.hashCode());
        result = prime * result + ((image == null) ? 0 : image.hashCode());
        result = prime * result + ((imgFile == null) ? 0 : imgFile.hashCode());
        result = prime * result + ((lang == null) ? 0 : lang.hashCode());
        result = prime * result + ((linkInfo == null) ? 0 : linkInfo.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((scope == null) ? 0 : scope.hashCode());
        result = prime * result + ((zone == null) ? 0 : zone.hashCode());
        return result;
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
        builder.append( "Logo [name=" );
        builder.append( name );
        builder.append( ", scope=" );
        builder.append( scope );
        builder.append( ", categories=" );
        builder.append( categories );
        builder.append( ", imgFile=" );
        builder.append( imgFile );
        builder.append( ", comments=" );
        builder.append( comments );
        builder.append( ", formes=" );
        builder.append( formes );
        builder.append( ", zone=" );
        builder.append( zone );
        builder.append( ", country=" );
        builder.append( country );
        builder.append( ", countries=" );
        builder.append( countries );
        builder.append( ", Link=" );
        builder.append( Link );
        builder.append( ", linkInfo=" );
        builder.append( linkInfo );
        builder.append( ", image=" );
        builder.append( image );
        builder.append( ", lang=" );
        builder.append( lang );
        builder.append( "]" );
        return builder.toString();
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
        if (!(obj instanceof Logo))
        {
            return false;
        }
        final Logo other = (Logo) obj;
        if (Link == null)
        {
            if (other.Link != null)
            {
                return false;
            }
        }
        else if (!Link.equals( other.Link ))
        {
            return false;
        }
        if (categories == null)
        {
            if (other.categories != null)
            {
                return false;
            }
        }
        else if (!categories.equals( other.categories ))
        {
            return false;
        }
        if (comments == null)
        {
            if (other.comments != null)
            {
                return false;
            }
        }
        else if (!comments.equals( other.comments ))
        {
            return false;
        }
        if (countries == null)
        {
            if (other.countries != null)
            {
                return false;
            }
        }
        else if (!countries.equals( other.countries ))
        {
            return false;
        }
        if (country == null)
        {
            if (other.country != null)
            {
                return false;
            }
        }
        else if (!country.equals( other.country ))
        {
            return false;
        }
        if (formes == null)
        {
            if (other.formes != null)
            {
                return false;
            }
        }
        else if (!formes.equals( other.formes ))
        {
            return false;
        }
        if (image == null)
        {
            if (other.image != null)
            {
                return false;
            }
        }
        else if (!image.equals( other.image ))
        {
            return false;
        }
        if (imgFile == null)
        {
            if (other.imgFile != null)
            {
                return false;
            }
        }
        else if (!imgFile.equals( other.imgFile ))
        {
            return false;
        }
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
        if (linkInfo == null)
        {
            if (other.linkInfo != null)
            {
                return false;
            }
        }
        else if (!linkInfo.equals( other.linkInfo ))
        {
            return false;
        }
        if (name == null)
        {
            if (other.name != null)
            {
                return false;
            }
        }
        else if (!name.equals( other.name ))
        {
            return false;
        }
        if (scope == null)
        {
            if (other.scope != null)
            {
                return false;
            }
        }
        else if (!scope.equals( other.scope ))
        {
            return false;
        }
        if (zone == null)
        {
            if (other.zone != null)
            {
                return false;
            }
        }
        else if (!zone.equals( other.zone ))
        {
            return false;
        }
        return true;
    }

    public void setLinkInfo(final String link_info)
    {
        this.linkInfo = link_info;
    }

    public File getImage()
    {
        return image;
    }

    public void setImage(final File image)
    {
        this.image = image;
    }

    public String getLang()
    {
        return lang.getLanguage();
    }

    public void setLang(final String lang)
    {
        this.lang = new Locale( lang );
    }

    public String getComments()
    {
        return comments;
    }

    public void setComments(final String comments)
    {
        this.comments = comments;
    }

}
