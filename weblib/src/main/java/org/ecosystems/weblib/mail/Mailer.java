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
package org.ecosystems.weblib.mail;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.ecosystems.lib.config.Configurator;
import org.ecosystems.lib.tools.StringTools;
import org.ecosystems.weblib.lang.LanguageManager;
import org.ecosystems.weblib.lang.NoSuchLanguageConverterException;
import org.ecosystems.weblib.lang.NoSuchTraductionException;
import org.ecosystems.weblib.lang.NoSuchTraductionRessourceException;
import org.ecosystems.weblib.log.LoggerAdapter;
import org.ecosystems.weblib.test.TestFlags;
import org.ecosystems.weblib.tools.ArrayTools;

/**
 * Classe envoyant des mails multilingues.
 */
public final class Mailer
{
    private static Session session;

    private static InternetAddress from;

    private static boolean sendmail = false;

    private static String currentMail = "";

    private static InternetAddress[] currentDests = {};

    private static final LoggerAdapter LOGGER_ADAPTER = new LoggerAdapter( Mailer.class );

    private Mailer()
    {
    }

    /**
     * Indique si le l'envoi de mail est activé.
     * Si l'envoi de mails est désactivé, les mails ne seront pas envoyés mais seront considérés comme envoyés et
     * La balise {@link org.ecosystems.weblib.html.debug.DebugParamsTag} indiquera l'envoi.
     * 
     * @see org.ecosystems.lib.config.Configurator.MAIL
     * @return vrai sil'envoi de mail est effectif, renvoie faux si le mailer n'est pas initialisé
     */
    public static boolean isMailEnabled()
    {
        return sendmail;
    }

    /**
     * Initialise le mailer.
     * 
     * @param smtpHost Nom d'hôte SMTP
     * @param from0 Contenu du champ from des mails
     */
    public static void init(final String smtpHost, final InternetAddress from0)
    {
        final Properties props = new Properties();
        props.put( "mail.smtp.host", smtpHost ); //$NON-NLS-1$
        props.put( "mail.mime.charset", "utf-8" );

        sendmail = ("0".equals( Configurator.getString( Configurator.MAIL.NO_MAIL ) ) && !TestFlags.isTestEnabled());
        if (sendmail)
        {
            LOGGER_ADAPTER.info( "mail enabled" );
        }
        else
        {
            LOGGER_ADAPTER.info( "mail not enabled" );
        }
        session = Session.getInstance( props );
        from = from0;
    }

    /**
     * Envoie un mail multilingue.
     * 
     * @param locale Locale demandée
     * @param namespace espace de nom des traductions
     * @param subjectKey clé de langue du sujet du mail
     * @param subjectParams paramètres su sujet
     * @param msgKey clé de langue du corps du mail
     * @param msgParams paramètres du corps
     * @param dests destinataires
     * @throws NoSuchTraductionException Levée si une traduction est introuvable
     * @throws NoSuchTraductionRessourceException Levée si une ressource de traduction demandée est introuvable
     * @throws MessagingException Levée si l'envoi de message echoue
     * @throws NoSuchLanguageConverterException Levée si le convertisseur est introuvable
     */
    public static void send(final Locale locale, final String namespace, final String subjectKey, final Map<String, Object> subjectParams,
            final String msgKey, final Map<String, Object> msgParams, final InternetAddress... dests) throws NoSuchTraductionException,
            NoSuchTraductionRessourceException, MessagingException, NoSuchLanguageConverterException
    {
        final String sSubject = LanguageManager.getConvertor().getTraduction( namespace, subjectKey, locale, subjectParams );
        final String sMsg = LanguageManager.getConvertor().getTraduction( namespace, msgKey, locale, msgParams );
        send( sSubject, sMsg, dests );
    }

    /**
     * Envoie un message simple.
     * 
     * @param subject sujet du message
     * @param message message
     * @param dests destinataires
     * @throws MessagingException Levée si l'envoi de message echoue
     */
    public static void send(final String subject, final String message, final InternetAddress... dests) throws MessagingException
    {
        LOGGER_ADAPTER.info( "Tentative d'envoi de mail ( sujet : ", subject, " ) à : ", StringTools.join( ",", dests ) );
        LOGGER_ADAPTER.debug( subject, "\n", message );

        if (sendmail)
        {
            Transport.send( getMessage( subject, message ), dests );
            LOGGER_ADAPTER.info( "mail envoyé" );
        }
        else
        {
            LOGGER_ADAPTER.info( "mail non envoyé, nomail activé (voir configuration)" );
            currentMail = subject + " / " + message;
            Mailer.addCurrentDests( dests );
        }
    }

    /**
     * Envoie un mail multilingue.
     * 
     * @param locale Locale demandée
     * @param namespace espace de nom des traductions
     * @param subjectKey clé de langue du sujet du mail
     * @param msgKey clé de langue du corps du mail
     * @param dests destinataires
     * @throws NoSuchTraductionException Levée si une traduction est introuvable
     * @throws NoSuchTraductionRessourceException Levée si une ressource de traduction demandée est introuvable
     * @throws MessagingException Levée si l'envoi de message echoue
     * @throws NoSuchLanguageConverterException Levée si le convertisseur est introuvable
     */
    public static void send(final Locale locale, final String namespace, final String subjectKey, final String msgKey,
            final InternetAddress... dests) throws NoSuchTraductionException, NoSuchTraductionRessourceException, MessagingException,
            NoSuchLanguageConverterException
    {
        send( locale, namespace, subjectKey, new HashMap<String, Object>(), msgKey, new HashMap<String, Object>(), dests );
    }

    private static Message getMessage(final String subject, final String msg) throws MessagingException
    {
        final MimeMessage ret = new MimeMessage( session );
        ret.addHeader( "Content-Type", "text/plain; charset=\"UTF-8\"" );
        ret.setFrom( from );
        ret.setSubject( subject, "UTF-8" );
        ret.setText( msg, "UTF-8" );
        return ret;
    }

    /**
     * Récupère le texte du dernier mail envoyé (pour debug).
     * 
     * @return the currentMail
     */
    public static String getCurrentMail()
    {
        return currentMail;
    }

    /**
     * Récupère les destinataires du dernier mail envoyé.
     * 
     * @return the currentDests
     */
    public static InternetAddress[] getCurrentDests()
    {
        return currentDests.clone();
    }

    /**
     * Définit les derniers destinataires de mail (pour debug).
     * 
     * @param currentDests1 the currentDests to set
     */
    private static void addCurrentDests(final InternetAddress[] currentDests1)
    {
        Mailer.currentDests = ArrayTools.concat( InternetAddress.class, currentDests, currentDests1 );
    }

}
