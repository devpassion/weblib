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
package org.ecosystems.weblib.html.form;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

/**
 * Bean du formulaire "party".
 */
public class Party
{
    private String title;

    private String address;

    private BigDecimal lat;

    private BigDecimal lng;

    private Date date;

    private String description;

    private String mail;

    private String name;

    private Time time;

    private String lang;

    // private Integer min;

    // private Integer hour;

    /**
     * @return the lat
     */
    public BigDecimal getLat()
    {
        return this.lat;
    }

    /**
     * @param lat the lat to set
     */
    public void setLat(final BigDecimal lat)
    {
        this.lat = lat;
    }

    /**
     * @return the lng
     */
    public BigDecimal getLng()
    {
        return this.lng;
    }

    /**
     * @param lng the lng to set
     */
    public void setLng(final BigDecimal lng)
    {
        this.lng = lng;
    }

    /**
     * 
     */
    public Party()
    {
    }

    /**
     * @return the title
     */
    public String getTitle()
    {
        return this.title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(final String title)
    {
        this.title = title;
    }

    /**
     * @return the address
     */
    public String getAddress()
    {
        return this.address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(final String address)
    {
        this.address = address;
    }

    /**
     * @param date the date to set
     */
    public void setDate(final Date date)
    {
        this.date = date;
    }

    /**
     * @return the date
     */
    public Date getDate()
    {
        return this.date;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(final String description)
    {
        this.description = description;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return this.description;
    }

    /**
     * @return the mail
     */
    public String getMail()
    {
        return this.mail;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(final String mail)
    {
        this.mail = mail;
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
     * @param min the min to set
     */
    // public void setMin( Integer min )
    // {
    // this.min = min;
    // }

    /**
     * @return the min
     */
    // public Integer getMin()
    // {
    // return min;
    // }

    /**
     * @param hour the hour to set
     */
    // public void setHour( Integer hour )
    // {
    // this.hour = hour;
    // }

    /**
     * @return the hour
     */
    // public Integer getHour()
    // {
    // return hour;
    // }

    /**
     * @param time the time to set
     */
    public void setTime(final Time time)
    {
        this.time = time;
    }

    /**
     * @return the time
     */
    public Time getTime()
    {
        return this.time;
    }

    public String getLang()
    {
        return lang;
    }

    public void setLang(String lang)
    {
        this.lang = lang;
    }

    /**
     * @param title Titre de l'évènement
     * @param address Adresse de l'évènement
     * @param lat latitude
     * @param lng longitude
     * @param date date de l'évèneemnt
     * @param description description
     * @param mail mail du créateur de l'évènement
     * @param name nom du créateur de l'évènement
     * @param time heure de l'évènement
     */
    public Party(final String title, final String address, final BigDecimal lat, final BigDecimal lng, final Date date,
            final String description, final String mail, final String name, final Time time)
    {
        super();
        this.title = title;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.date = date;
        this.description = description;
        this.mail = mail;
        this.name = name;
        this.time = time;
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
        builder.append( "Party [address=" ).append( this.address ).append( ", date=" ).append( this.date ).append( ", description=" )
                .append( this.description ).append( ", lat=" ).append( this.lat ).append( ", lng=" ).append( this.lng ).append( ", mail=" )
                .append( this.mail ).append( ", name=" ).append( this.name ).append( ", time=" ).append( this.time ).append( ", title=" )
                .append( this.title ).append( "]" );
        return builder.toString();
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
        result = prime * result + ((this.address == null) ? 0 : this.address.hashCode());
        result = prime * result + ((this.date == null) ? 0 : this.date.hashCode());
        result = prime * result + ((this.description == null) ? 0 : this.description.hashCode());
        result = prime * result + ((this.lat == null) ? 0 : this.lat.hashCode());
        result = prime * result + ((this.lng == null) ? 0 : this.lng.hashCode());
        result = prime * result + ((this.mail == null) ? 0 : this.mail.hashCode());
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        result = prime * result + ((this.time == null) ? 0 : this.time.hashCode());
        result = prime * result + ((this.title == null) ? 0 : this.title.hashCode());
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
        final Party other = (Party) obj;
        if (this.address == null)
        {
            if (other.address != null)
                return false;
        }
        else if (!this.address.equals( other.address ))
            return false;
        if (this.date == null)
        {
            if (other.date != null)
                return false;
        }
        else if (!this.date.equals( other.date ))
            return false;
        if (this.description == null)
        {
            if (other.description != null)
                return false;
        }
        else if (!this.description.equals( other.description ))
            return false;
        if (this.lat == null)
        {
            if (other.lat != null)
                return false;
        }
        else if (!this.lat.equals( other.lat ))
            return false;
        if (this.lng == null)
        {
            if (other.lng != null)
                return false;
        }
        else if (!this.lng.equals( other.lng ))
            return false;
        if (this.mail == null)
        {
            if (other.mail != null)
                return false;
        }
        else if (!this.mail.equals( other.mail ))
            return false;
        if (this.name == null)
        {
            if (other.name != null)
                return false;
        }
        else if (!this.name.equals( other.name ))
            return false;
        if (this.time == null)
        {
            if (other.time != null)
                return false;
        }
        else if (!this.time.equals( other.time ))
            return false;
        if (this.title == null)
        {
            if (other.title != null)
                return false;
        }
        else if (!this.title.equals( other.title ))
            return false;
        return true;
    }

}
