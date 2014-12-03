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
package org.ecosystems.weblib.test;

import java.util.ArrayList;
import java.util.List;

import org.ecosystems.lib.tools.StringTools;

/**
 * Chemin parcouru dans une application HTTP.
 * 
 * Le chemin est représenté par une liste triée de {@link HttpWayPoint}
 */
public class HttpWay
{
    private final List<HttpWayPoint> points = new ArrayList<>();

    /**
     * Constructeur par défaut.
     */
    public HttpWay()
    {

    }

    /**
     * Ajoute un point au chemin.
     * 
     * @param point point de chemin
     */
    public void addPoint(final HttpWayPoint point)
    {
        this.points.add( point );
    }

    @Override
    public String toString()
    {
        return StringTools.join( "||\n", this.points );
    }
}
