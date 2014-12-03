/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ecosystems.weblibtests.tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.ecosystems.weblib.html.form.FormListener;
import org.ecosystems.weblib.html.form.ManageFormException;

/**
 *
 * @author tim288
 */
public class MailFormListener implements FormListener<MailBean>
{

    @Override
    public void manageBean(MailBean bean, ServletContext context, HttpServletRequest request, HttpServletResponse response) throws ManageFormException
    {
        Address mail = bean.getAddress(); // On peut utiliser ici un typage fort
        try
        {
            // Insersion dans la base (par exemple)
            response.sendRedirect("<newURL>"); // on peut ensuite rediriger l'url vers une page indiquant la validation
        }
        catch (IOException ex)
        {
            Logger.getLogger(MailFormListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
