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

import javax.servlet.http.HttpServletRequest;

import org.ecosystems.weblib.log.LoggerAdapter;

/**
 * Validateur vérifiant que chaque donnée d'un tableau est valide avec un validateur. Un tableau vide est considéré comme valide.
 * 
 */
public abstract class ArrayValidator extends AbstractInputValidator
{

    private static final LoggerAdapter LOGGER_ADAPTER = new LoggerAdapter( ArrayValidator.class );

    @Override
    public boolean validate(final Object value)
    {
        if (value == null)
        {
            return true;
        }
        final InputValidator sv = this.getItemValidator();
        if (value.getClass().isAssignableFrom( Object[].class ))
        {

            for (final Object s : (Object[]) value)
            {
                LOGGER_ADAPTER.trace( "validation de \"", s, "\"" );
                if (!sv.validate( s ))
                {
                    // throw new IllegalArgumentException( "invalide :  \"" + StringTools.getStringOrNull( value ) + "\"" );
                    LOGGER_ADAPTER.debug( "invalide : \"", s, "\"" );
                    return false;
                }
            }
            LOGGER_ADAPTER.trace( "toutes les valeurs du tableau sont valides" );
            return true;
        }

        LOGGER_ADAPTER.trace( "value est de type " + value.getClass() );
        return sv.validate( value );

    }

    /**
     * Récupère une instance du validateur utilisé sur chaque élément du tableau.
     * 
     * @return Validateur appliqué sur les éléments du tableau
     */
    protected abstract InputValidator getItemValidator();

    @Override
    public void setRequest(final HttpServletRequest request)
    {
        // TODO Auto-generated method stub

    }

}
