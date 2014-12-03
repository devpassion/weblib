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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import org.ecosystems.lib.tools.StringTools;
import org.ecosystems.weblib.html.AbstractHtmlTag;
import org.ecosystems.weblib.html.AllowedTypesHashTable;
import org.ecosystems.weblib.html.AttributeType;
import org.ecosystems.weblib.html.FormInputHtmlTag;
import org.ecosystems.weblib.html.InvalidAttributeTypeException;
import org.ecosystems.weblib.html.OneValueAttribute;
import org.ecosystems.weblib.html.SimpleAttribute;
import org.ecosystems.weblib.html.TagDispositionPolicy;
import org.ecosystems.weblib.html.UnknowAttributeException;
import org.ecosystems.weblib.test.TestFlags;

/**
 * Tag input.
 */
public class InputTag extends AbstractHtmlTag implements FormInputHtmlTag
{

    private boolean persistent = true;

    private final FormContextUserTagAdapter formContextAdapter;

    private static final long serialVersionUID = 1381572656715300355L;

    /**
     * Crée un Tag input.
     */
    public InputTag()
    {
        super( "input", TagDispositionPolicy.BEFORE_BODY ); //$NON-NLS-1$
        this.formContextAdapter = new FormContextUserTagAdapter( this );
    }

    @Override
    protected void setAllowedsAttributes(final AllowedTypesHashTable alloweds)
    {
        super.setAllowedsAttributes( alloweds );
        alloweds.add( "accept", AttributeType.getStringAttributeType() ); //$NON-NLS-1$
        alloweds.add( "maxlength", AttributeType.getIntegerAttributeType() ); //$NON-NLS-1$
        alloweds.add( "size", AttributeType.getIntegerAttributeType() ); //$NON-NLS-1$
        alloweds.add( "type", AttributeType.getInputTypeAttributeType(), true, InputType.TEXT ); //$NON-NLS-1$
        alloweds.add( "name", AttributeType.getStringAttributeType(), true ); //$NON-NLS-1$
        alloweds.add( "value", AttributeType.getStringAttributeType() ); //$NON-NLS-1$
        alloweds.add( "rel", AttributeType.getStringAttributeType() ); //$NON-NLS-1$
        alloweds.add( "readonly", AttributeType.getBooleanAttributeType() ); //$NON-NLS-1$
        alloweds.add( "required", AttributeType.getStringAttributeType() ); //$NON-NLS-1$
        alloweds.add( "placeholder", AttributeType.getStringAttributeType() ); //$NON-NLS-1$
        alloweds.add( "checked", AttributeType.getStringAttributeType() ); //$NON-NLS-1$
        alloweds.add( "autocomplete", AttributeType.getStringAttributeType() ); //$NON-NLS-1$
        alloweds.add( "src", AttributeType.getStringAttributeType() ); //$NON-NLS-1$
    }

    /**
     * Indique si le tag est persistant (i.e. son contenu est conservé tant que la session n'est pas détruite).
     * 
     * @return the persistent
     */
    public boolean isPersistent()
    {
        return this.persistent;
    }

    /**
     * Définit si le tag est persistant.
     * 
     * @param persistent the persistent to set
     */
    public void setPersistent(final boolean persistent)
    {
        this.persistent = persistent;
    }

    @Override
    public int doStartTag() throws JspException
    {

        this.formContextAdapter.setFormParent();

        if (this.isPersistent())
        {
            this.setPersistentValue();
        }

        try
        {
            if (!"checkbox".equals( StringTools.getStringOrNull( this.getAttribute( "type" ).getValue() ) )
                    && this.formContextAdapter.getFormParent().getFieldContext( this.getAttribute( "name" ).getValue().toString() )
                            .isRequired())
            {
                if (!TestFlags.isTestEnabled())
                    this.setAttribute( new OneValueAttribute( "required" ) );
                else
                    this.removeAttribute( "required" );

            }
            else if (this.hasAttribute( "required" )
                    && "checkbox".equals( StringTools.getStringOrNull( this.getAttribute( "type" ).getValue() ) ))
            {
                this.removeAttribute( "required" );
                // this.getLogger().warn( "L'attribut required n'est pas supporté dans les balises jsp(tag ", this.toString(), ")" );
            }
        }
        catch (final UnknowAttributeException | InvalidAttributeTypeException | NoSuchFieldException e)
        {
            throw new JspException( e );
        }

        super.doStartTag();

        return Tag.SKIP_BODY;
    }

    /**
     * Défini l'attribut value si le tag est persistent.
     * 
     * @throws JspException Levée si les attributs sont invalides ( voir cause )
     */
    private void setPersistentValue() throws JspException
    {
        String valueName;
        try
        {
            valueName = this.getAttribute( "name" ).getValue().toString();
            final Object type = this.getAttribute( "type" ).getValue();
            this.getLogger().debug( "attribut de contexte pour ", valueName, " = ",
                    this.formContextAdapter.getFormParent().getFieldContext( valueName ).getDisplayableValue(), "(type == ", type, ")" );

            final Object value = this.formContextAdapter.getFormParent().getFieldContext( valueName ).getDisplayableValue();

            if (!("checkbox".equals( StringTools.getStringOrNull( type ) )))
            {
                this.getLogger().trace( "value = ", value );
                this.setAttribute( new SimpleAttribute<String>( AttributeType.getStringAttributeType(), "value", value ) );
            }
            else
            {

                this.getLogger().trace( "checkbox, persistent value = ", value, ", inputTag.value == ", this.getAttribute( "value" ) );
                if (value != null
                        && this.getAttribute( "value" ) != null
                        && (value.toString().equals( this.getAttribute( "value" ).getValue() ) || value.toString().contains(
                                "," + this.getAttribute( "value" ).getValue() + "," )))
                {
                    this.getLogger().trace( "checked" );
                    this.setAttribute( new SimpleAttribute<>( AttributeType.getStringAttributeType(), "checked", "checked" ) );
                }
                else
                {
                    this.removeAttribute( "checked" );
                }
            }

        }
        catch (final UnknowAttributeException e)
        {
            throw new JspException( e );
        }
        catch (final NoSuchFieldException e)
        {
            throw new JspException( e );
        }
        catch (final InvalidAttributeTypeException e)
        {
            throw new JspException( e );
        }
        catch (final NullPointerException e)
        {
            throw new JspException( "Il semble que le paramètre name n'est pas renseigné", e ); //$NON-NLS-1$
        }
        catch (final InvalidBoxedTypeException e)
        {
            throw new JspException( e );
        }

    }

    /**
     * Types de balises INPUT.
     */
    public enum InputType
    {
        /**
         * zone de texte.
         */
        TEXT,

        /**
         * zone de password.
         */
        PASSWORD,

        /**
         * Case à cocher.
         */
        CHECKBOX,

        /**
         * Bouton Radio.
         */
        RADIO,

        /**
         * Bouton de soumission.
         */
        SUBMIT,

        /**
         * Reset de formulaire.
         */
        RESET,

        /**
         * Upload de fichier.
         */
        FILE,

        /**
         * Champ caché.
         */
        HIDDEN,

        /**
         * Bouton de soumission graphique.
         */
        IMAGE,

        /**
         * Bouton poussoir.
         */
        BUTTON,

        /**
         * Choix de couleur.
         */
        COLOR,

        /**
         * Champ de saisie de nombre.
         */
        NUMBER,

        /**
         * Champ de saisie d'intervalle.
         */
        RANGE,

        /**
         * Champ de recherche.
         */
        SEARCH,

        /**
         * Champ date.
         */
        DATE,

        /**
         * Champ datetime.
         */
        DATETIME,

        /**
         * Champ mail.
         */
        EMAIL,

        /**
         * Champ numéro de telephone.
         */
        TEL,

        /**
         * Champ de saisie d'heure.
         */
        TIME,

        /**
         * Champ de saisie d'URL.
         */
        URl,

        /**
         * Champ de saisie de semaine.
         */
        WEEK;

        @Override
        public String toString()
        {
            return super.toString().toLowerCase();
        }

    }

}
