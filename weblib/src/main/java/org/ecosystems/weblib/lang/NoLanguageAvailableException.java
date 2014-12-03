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

/**
 * Exception levée lorsqu'aucune langue n'est trouvée dans la configuration.
 */
public class NoLanguageAvailableException extends Exception
{

    private static final long serialVersionUID = -6456915576342753743L;

    /**
     * Constructeur par défaut.
     */
    public NoLanguageAvailableException()
    {
    }

    /**
     * Nouvelle erreur avec cause.
     * 
     * @param cause cause de l'erreur
     */
    public NoLanguageAvailableException(final Throwable cause)
    {
        super( cause );
    }

    @Override
    public String getMessage()
    {
        return "Aucune langue n'est disponible"; //$NON-NLS-1$
    }
}
