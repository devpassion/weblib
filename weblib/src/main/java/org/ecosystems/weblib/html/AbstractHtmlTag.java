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
package org.ecosystems.weblib.html;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import org.apache.log4j.Level;
import org.ecosystems.weblib.log.LoggerAdapter;

/**
 * Classe abstraite des tags HTML.
 */
public abstract class AbstractHtmlTag extends BodyTagSupport implements JspHtmlTag
{

    // [start] properties

    /**
     * ID de série.
     */
    private static final long serialVersionUID = -9176167486666395564L;

    /**
     * Adapter de logs.
     */
    private LoggerAdapter logAdp;

    /**
     * Indique comment le tag est disposé par rapport à son corp.
     */
    private final TagDispositionPolicy dispositionPolicy;

    /**
     * Caractère de début de tag.
     */
    public static final String TAG_BEGIN = "<"; //$NON-NLS-1$

    /**
     * Caractère de fin de tag ( généralement '>').
     */
    public static final String TAG_END = ">"; //$NON-NLS-1$

    /**
     * Attributs html du tag.
     */
    private final Hashtable<String, Attribute<?>> attributes = new Hashtable<String, Attribute<?>>();

    /**
     * Nom du tag, qui sera le nom de la balise html.
     */
    private String name;

    /**
     * Table contenant les types de tags HTMl valides pour la balise.
     */
    private final AllowedTypesHashTable allowedsTable = new AllowedTypesHashTable();

    /**
     * Tags enfants du tag.
     */
    private final List<HtmlTag> children = new ArrayList<HtmlTag>();

    // [end]

    // [start] Constructors

    /**
     * Crée un nouveau tag HTMl abstrait.
     * 
     * Méthode à utiliser dans les constructeurs dérivés.
     * 
     * @param name Nom de la balise
     * @param dispositionPolicy politique de disposition du corps
     */
    public AbstractHtmlTag(final String name, final TagDispositionPolicy dispositionPolicy)
    {
        this.name = name;
        this.dispositionPolicy = dispositionPolicy;
        this.setAllowedsAttributes( this.allowedsTable );
    }

    // [end]

    // [start] Static methods

    /**
     * Ajoute les attributs de script.
     * 
     * @param alloweds Liste à laquelle ajouter les attributs
     */
    private static void addEventsAttrs(final AllowedTypesHashTable alloweds)
    {
        final String[] js = new String[] { "onclick", "ondblclick", //$NON-NLS-1$ //$NON-NLS-2$
                "onmousedown", "onmouseup", "onmouseover", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                "onmousemove", "onmouseout", "onkeypress", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                "onkeydown", "onkeyup" }; //$NON-NLS-1$ //$NON-NLS-2$
        for (final String attr : js)
        {
            alloweds.add( attr, AttributeType.getStringAttributeType() );
        }
    }

    // [end]

    // [start] Protecteds methods

    /**
     * Défini les attributs autorisés. Il faut impérativement appeler la super-méthode dans une réecriture
     * 
     * @param alloweds attributs autorisés
     */
    protected void setAllowedsAttributes(final AllowedTypesHashTable alloweds)
    {
        addEventsAttrs( alloweds );
        alloweds.add( "class", AttributeType.getStringAttributeType() ); //$NON-NLS-1$
        alloweds.add( "id", AttributeType.getStringAttributeType() ); //$NON-NLS-1$
        alloweds.add( "title", AttributeType.getStringAttributeType() ); //$NON-NLS-1$
        alloweds.add( "lang", AttributeType.getStringAttributeType() ); //$NON-NLS-1$
        alloweds.add( "dir", AttributeType.getStringAttributeType() ); //$NON-NLS-1$
        alloweds.add( "style", AttributeType.getStringAttributeType() ); //$NON-NLS-1$
    }

    /**
     * Vérifie que les attributs requis sont bien présents.
     * 
     * @throws MissingAttributeException Levée si un attribut requis est manquant
     */
    protected void validRequireds() throws MissingAttributeException
    {
        final Iterator<String> it = this.allowedsTable.requiredsIterator();
        String attName;
        while (it.hasNext())
        {
            attName = it.next();
            if (!this.attributes.containsKey( attName ))
            {
                final Object value = this.allowedsTable.getDefaultValue( attName );
                if (value == null)
                {
                    final MissingAttributeException t = new MissingAttributeException( attName, this );
                    this.getLogger().fatal( "", t ); //$NON-NLS-1$
                    throw t;
                }

            }
        }
    }

    /**
     * Définis les attributs par défauts si besoin.
     * 
     * @throws InvalidAttributeTypeException Levée si une création d'attribut provoque un type invalide
     */
    protected void addDefaults() throws InvalidAttributeTypeException
    {
        final Iterator<Entry<String, Object>> it = this.allowedsTable.defaultsIterator();
        while (it.hasNext())
        {
            final Entry<String, Object> e = it.next();
            this.getLogger().log( Level.TRACE, this.attributes );
            if (!this.attributes.containsKey( e.getKey() ))
            {
                this.getLogger().trace( "set default ", e.getKey(), " = ", e.getValue() ); //$NON-NLS-1$ //$NON-NLS-2$
                this.setAttribute( this.getInfferAttribute( this.allowedsTable.get( e.getKey() ), e.getKey(), e.getValue() ) );
            }
        }
    }

    /**
     * Récupère un logger.
     * 
     * @return logger associé à la classe.
     */
    @Override
    public LoggerAdapter getLogger()
    {
        if (this.logAdp == null)
        {
            this.logAdp = new LoggerAdapter( this.getClass() );
        }
        return this.logAdp;
    }

    // [end]

    // [start] HtmlTag Override

    @Override
    public List<HtmlTag> getChildren()
    {
        return this.children;
    }

    @Override
    public void addChildren(final HtmlTag children1)
    {
        this.children.add( children1 );
    }

    /*
     * (non-Javadoc)
     * 
     * @see html.HtmlTag#getAttribute(java.lang.String)
     */
    @Override
    public Attribute<?> getAttribute(final String name1) throws UnknowAttributeException
    {
        final Attribute<?> attr = this.attributes.get( name1 );
        if (attr == null)
        {
            final UnknowAttributeException t = new UnknowAttributeException( name1, this );
            this.getLogger().fatal( "Attribut null", t );
            throw t;
        }
        return attr;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ecosystems.weblib.html.HtmlTag#hasAttribute(java.lang.String)
     */
    @Override
    public boolean hasAttribute(final String name0)
    {
        return this.attributes.containsKey( name0 );
    }

    @Override
    public void removeAttribute(final String name1)
    {
        this.attributes.remove( name1 );

    }

    /*
     * (non-Javadoc)
     * 
     * @see html.HtmlTag#getTagName()
     */
    @Override
    public String getTagName()
    {
        return this.name;
    }

    /**
     * Défini le nom de la balise.
     * 
     * @param name nouveau nom de la balise
     */
    protected void setName(final String name)
    {
        this.name = name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see html.HtmlTag#setAttribute(html.Attribute)
     */
    @Override
    public void setAttribute(final Attribute<?> attribute) throws InvalidAttributeTypeException
    {
        if (!this.allowedsTable.isAllowed( attribute ))
        {
            final InvalidAttributeTypeException t = new InvalidAttributeTypeException( attribute, this );
            this.getLogger().getLogger().fatal( "", t ); //$NON-NLS-1$
            throw t;
        }
        this.attributes.put( attribute.getName(), attribute );
    }

    /*
     * (non-Javadoc)
     * 
     * @see html.HtmlTag#getBeginTag()
     */
    @Override
    public String getBeginTag()
    {
        final StringBuilder sb = new StringBuilder( TAG_BEGIN );
        sb.append( this.getTagName() );
        sb.append( " " ); //$NON-NLS-1$

        for (final Attribute<?> a : this.attributes.values())
        {
            sb.append( a.toString() );
        }

        if (this.dispositionPolicy == TagDispositionPolicy.BEFORE_BODY)
        {
            sb.append( " /" );
        }

        sb.append( TAG_END );

        for (final HtmlTag child : this.children)
        {
            sb.append( child.getBeginTag() );
            sb.append( child.getEndTag() );
        }
        return sb.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see html.HtmlTag#getEndTag()
     */
    @Override
    public String getEndTag()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append( TAG_BEGIN );
        sb.append( "/" ); //$NON-NLS-1$
        sb.append( this.getTagName() );
        sb.append( TAG_END );
        return sb.toString();
    }

    // [end]

    // [start] BodyTagSupport Override

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.jsp.tagext.BodyTagSupport#doEndTag()
     */
    @Override
    public int doEndTag() throws JspException
    {
        try
        {
            switch (this.dispositionPolicy)
            {
            case ENCLOSE_BODY:
                this.pageContext.getOut().write( this.getEndTag() );
                break;
            default:
            }
        }
        catch (final IOException e)
        {
            throw this.getLogger().logAndReturnJspException( e );
        }
        return Tag.EVAL_PAGE;
    }

    @Override
    public int doAfterBody() throws JspException
    {
        return super.doAfterBody();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
     */
    @Override
    public int doStartTag() throws JspException
    {
        try
        {
            this.addDefaults();
            this.validRequireds();
            this.pageContext.getOut().write( this.getBeginTag() );

            // switch (this.dispositionPolicy)
            // {
            // case BEFORE_BODY:
            // this.pageContext.getOut().write( this.getEndTag() );
            // break;
            // default:
            // }

        }
        catch (final InvalidAttributeTypeException e)
        {
            throw this.getLogger().logAndReturnJspException( e );
        }
        catch (final MissingAttributeException e)
        {
            throw this.getLogger().logAndReturnJspException( e );
        }
        catch (final IOException e)
        {
            throw this.getLogger().logAndReturnJspException( e );
        }

        return Tag.EVAL_BODY_INCLUDE;
    }

    // [end]

    // [start] DynamicAttributes Override

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( "AbstractHtmlTag [logAdp=" );
        builder.append( logAdp );
        builder.append( ", dispositionPolicy=" );
        builder.append( dispositionPolicy );
        builder.append( ", attributes=" );
        builder.append( attributes );
        builder.append( ", name=" );
        builder.append( name );
        builder.append( ", allowedsTable=" );
        builder.append( allowedsTable );
        builder.append( ", children=" );
        builder.append( children );
        builder.append( "]" );
        return builder.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.jsp.tagext.DynamicAttributes#setDynamicAttribute(java.lang.String, java.lang.String,
     * java.lang.Object)
     */
    @Override
    public void setDynamicAttribute(final String uri, final String localName, final Object value) throws JspException
    {
        this.getLogger().trace( "setDynamicAttribute ", localName ); //$NON-NLS-1$
        final AttributeType<?> at = this.allowedsTable.get( localName );
        if (at == null)
        {
            throw this.getLogger().logAndReturnJspException( new InvalidAttributeTypeException( localName, this ) );
        }

        try
        {
            this.setAttribute( this.getInfferAttribute( at, localName, value ) );
        }
        catch (final InvalidAttributeTypeException e)
        {
            throw this.getLogger().logAndReturnJspException( e );
        }

    }

    /**
     * Méthode inférant le type d'attribut ajouté.
     * 
     * @param <T> Type de l'attribut
     * @param attributeType Type d'attribut
     * @param name1 Nom de l'attribut
     * @param value Valeur de l'attribut
     * @return Nouvel attribut simple
     * @throws InvalidAttributeTypeException Levée si la valeur n'est pas convertible dans le type du paramètre
     */
    private <T> Attribute<T> getInfferAttribute(final AttributeType<T> attributeType, final String name1, final Object value)
            throws InvalidAttributeTypeException
    {
        return new SimpleAttribute<T>( attributeType, name1, value );
    }

    // [end]

    // [start] private methods

    // [end]

}
