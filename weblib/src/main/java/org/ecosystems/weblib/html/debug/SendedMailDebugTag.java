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
package org.ecosystems.weblib.html.debug;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.ecosystems.lib.tools.StringTools;
import org.ecosystems.weblib.mail.Mailer;

/**
 * Tag affichant les mails qui seraient envoyés si le paramètre nomail n'etait pas activé.
 */
public class SendedMailDebugTag extends SimpleTagSupport
{
    @Override
    public void doTag() throws JspException, IOException
    {
        final StringBuilder out = new StringBuilder( "Sended : <br />" );
        out.append( "destinataires : " );
        out.append( StringTools.join( ", ", Mailer.getCurrentDests() ) );
        out.append( "<br />" );
        out.append( Mailer.getCurrentMail().replaceAll( "\n", "<br />" ) );
        out.append( "<br />" );
        this.getJspContext().getOut().print( out.toString() );
    }

}
