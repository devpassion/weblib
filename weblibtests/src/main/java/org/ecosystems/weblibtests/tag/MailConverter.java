/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ecosystems.weblibtests.tag;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import org.ecosystems.weblib.html.form.ConversionException;
import org.ecosystems.weblib.html.form.Converter;
import org.ecosystems.weblib.html.form.InvalidBoxedTypeException;

/**
 *
 * @author tim288
 */
public class MailConverter implements Converter<Address>
{

    @Override
    public Address convert(Object o) throws ConversionException
    {
        // Conversion de texte en adresse mail
        try
        {
            return new InternetAddress(Objects.toString(o));
        }
        catch (AddressException ex)
        {
            // A ce stade, le validateur doit garantir que l'adresse est valide, sinon, c'est un problème
            Logger.getLogger(MailConverter.class.getName()).log(Level.SEVERE, null, ex);
            throw new ConversionException(o, Address.class);
        }
    }

    @Override
    public String toString(Object value) throws InvalidBoxedTypeException
    {
        // Conversion en chaine de caracère pour l'affichage dans le formulaire
        return Objects.toString(value);
    }

    @Override
    public Class<Address> getConvertClass()
    {
        return Address.class;
    }

    @Override
    public void setRequest(String name, HttpServletRequest request)
    {
        
    }
    
}
