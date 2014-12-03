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
package org.ecosystems.weblib.html.form.config;

/**
 * Implementation par défaut de la fabrique de contextes de formulaires.
 */
public final class FormContextFactoryImpl
{
    private static FormContextProviderImpl instance = new FormContextProviderImpl();

    private FormContextFactoryImpl()
    {
    }

    /**
     * Récupère une instance unique de provider.
     * 
     * @return provider de contexte de formulaire
     */
    public static FormContextProvider getProvider()
    {
        return instance;
    }

}
