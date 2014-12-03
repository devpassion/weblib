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

import org.apache.commons.validator.EmailValidator;

/**
 * Validateur de mail.
 */
public class MailValidator extends AbstractInputValidator
{

    //private static final String MAIL_REGEX = "(.*)@(.*)\\.(.*)"; //$NON-NLS-1$

    @Override
    public boolean validate(final Object value)
    {
        if (value == null)
        {
            return false;
        }
        final EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid( value.toString() );
    }

    @Override
    public String ruleName()
    {
        return "InvalidMail"; //$NON-NLS-1$
    }

    @Override
    public void setRequest(final HttpServletRequest request)
    {
        // TODO Auto-generated method stub
    }
}
