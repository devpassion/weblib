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
 * Validateur de champ vérifiant que la valeur entrée pour un champ de captcha est correcte.
 * 
 */
public class CaptchaValidator extends AbstractInputValidator
{
    /**
     * Nom du paramètre de session contenant le captcha.
     */
    public static final String CAPTCHA_SESSION_PARAM_NAME = "org.ecosystems.weblib.captcha";

    private static final LoggerAdapter LOGGER_ADAPTER = new LoggerAdapter( CaptchaValidator.class );

    private Object sessionVal;

    @Override
    public boolean validate(final Object value)
    {
        LOGGER_ADAPTER.debug( "Property test = ", System.getProperty( "org.ecosystems.test" ) );
        LOGGER_ADAPTER.debug( "Challenge response in session = ", this.sessionVal );
        LOGGER_ADAPTER.debug( "Cntered value = ", value );
        return "1".equals( System.getProperty( "org.ecosystems.test" ) )
                || (this.sessionVal != null && this.sessionVal.toString().equals( value ));
    }

    @Override
    public String ruleName()
    {
        return "InvalidCaptcha";
    }

    @Override
    public void setRequest(final HttpServletRequest request)
    {
        LOGGER_ADAPTER.trace( "set attribute ", CAPTCHA_SESSION_PARAM_NAME, " in session, value = ",
                request.getSession().getAttribute( CAPTCHA_SESSION_PARAM_NAME ) );
        this.sessionVal = request.getSession().getAttribute( CAPTCHA_SESSION_PARAM_NAME );
    }
}
