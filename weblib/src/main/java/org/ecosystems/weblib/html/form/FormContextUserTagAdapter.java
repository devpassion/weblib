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
import org.ecosystems.weblib.html.FormInputHtmlTag;
import org.ecosystems.weblib.html.UnknowAttributeException;
import org.ecosystems.weblib.html.form.config.FieldContext;

/**
 * Adapter pour les tags JSP contenus dans un formulaire.
 */
public class FormContextUserTagAdapter
{

    private FormTag formParent = null;

    private final FormInputHtmlTag tag;

    /**
     * Constructeur par défaut.
     * 
     * @param tag Tag adapté
     */
    public FormContextUserTagAdapter(final FormInputHtmlTag tag)
    {
        this.tag = tag;
    }

    /**
     * Défini le formulaire parent.
     * 
     * @throws JspException Levée si le tag n'est pas contenu dans un Tag {@link FormTag}
     */
    void setFormParent() throws JspException
    {
        final Tag parent = this.tag.getParent();

        if (!(parent instanceof FormTag))
        {
            if (parent == null)
            {
                throw this.tag.getLogger().logAndReturnJspException(
                        StringTools.concat( "Le tag input n'est pas contenu dans un formulaire " + //$NON-NLS-1$
                                "ni dans aucun tag " ) ); //$NON-NLS-1$
            }
            throw this.tag.getLogger().logAndReturnJspException(
                    StringTools.concat( "Le tag input n'est pas contenu dans un formulaire mais " + //$NON-NLS-1$
                            "dans un tag ", StringTools.getStringOrNull( parent.getClass() ) ) ); //$NON-NLS-1$

        }
        this.formParent = (FormTag) parent;
    }

    /**
     * Récupère le formulaire conteneur.
     * 
     * @return the formParent
     */
    public FormTag getFormParent()
    {
        return this.formParent;
    }

    /**
     * Récupère le contexte de champ de l'input associé à la classe.
     * 
     * @return Contexte de champ
     * @throws UnknowAttributeException Levée si le champ name n'est pas présent
     * @throws NoSuchFieldException Levée si le contexte de champ n'est pas trouvé dans la configuration de formulaire
     */
    public FieldContext<?, ?> getFieldContext() throws UnknowAttributeException, NoSuchFieldException
    {
        final String name = this.tag.getAttribute( "name" ).getValue().toString(); //$NON-NLS-1$
        return this.formParent.getFieldContext( name );
    }

}
