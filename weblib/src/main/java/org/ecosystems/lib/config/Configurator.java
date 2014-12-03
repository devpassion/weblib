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
package org.ecosystems.lib.config;

import org.apache.commons.configuration.Configuration;

/**
 * Gestionnaire de configuration.
 */
public final class Configurator
{

    private static Configuration config;

    private Configurator()
    {
    }

    /**
     * Défini la configuration.
     * 
     * @param config the config to set
     */
    public static void setConfig(final Configuration config)
    {
        Configurator.config = config;
    }

    /**
     * Récupère la configuration.
     * 
     * @return the config
     */
    public static Configuration getConfig()
    {
        return config;
    }

    /**
     * Récupère une {@link String} dans la configuration ou une valeur par défaut.
     * 
     * @param key clé de la chaîne à récupérer
     * @param defaultValue valeur par défaut
     * @return Chaîne de la configuration, ou null si absente
     */
    public static String getString(final String key, final String defaultValue)
    {
        if (config == null)
        {
            throw new IllegalStateException( "Le configurator n'est pas initialisé" ); //$NON-NLS-1$
        }
        try
        {
            return config.getString( key, defaultValue );
        }
        catch (final NullPointerException e)
        {
            throw new IllegalArgumentException( "Clé introuvable : " + key, e ); //$NON-NLS-1$
        }
    }

    /**
     * Récupère une {@link String} dans la configuration.
     * 
     * @param key clé de la chaîne à récupérer
     * @return Chaîne de la configuration, ou null si absente
     */
    public static String getString(final String key)
    {
        if (config == null)
        {
            throw new IllegalStateException( "Le configurator n'est pas initialisé" ); //$NON-NLS-1$
        }
        try
        {
            return config.getString( key );
        }
        catch (final NullPointerException e)
        {
            throw new IllegalArgumentException( "Clé introuvable : " + key, e ); //$NON-NLS-1$
        }
    }

    /**
     * Classe fournissant des constantes relatives à la pagination.
     */
    public static final class PAGINATION
    {

        private PAGINATION()
        {
        }

        /**
         * Nom du paramètre de configuration définissant le nom du paramètre GET ou POST indiquant le numéro de page.
         */
        public static final String PAGE_PARAM_NAME = "pagination.paramName";

    }

    /**
     * Classe fournissant des constantes pour accéder à la section de configuration des formats.
     */
    public static final class Format
    {

        private Format()
        {
        }

        /**
         * Format de date ( du type dd/mm/yyy ).
         */
        public static final String DATE_FORMAT = "format.dateFormat"; //$NON-NLS-1$

        /**
         * Format de temps.
         */
        public static final String TIME_FORMAT = "format.timeFormat"; //$NON-NLS-1$

        /**
         * Format des dates affichables.
         */
        public static final String USER_DATE_FORMAT = "format.displayableDateFormat"; //$NON-NLS-1$

        /**
         * Format des heures affichables.
         */
        public static final String USER_TIME_FORMAT = "format.displayableTimeFormat"; //$NON-NLS-1$
    }

    /**
     * Classe fournissant des constantes pour accéder à la section de configuration de langue.
     */
    public static final class Lang
    {

        private Lang()
        {
        }

        /**
         * Langue par défaut si aucune langue n'est trouvée ( peut être masquée par la langue par défaut du serveur ).
         */
        public static final String DEFAULT_LANG = "lang.defaultLang"; //$NON-NLS-1$

        /**
         * Nom de la classe du convertisseur de langue utilisé.
         */
        public static final String CONVERTER = "lang.converter"; //$NON-NLS-1$

        /**
         * Nom du paramètre ordonnant le changement de langue.
         */
        public static final String PARAMETER_NAME = "lang.parameterName"; //$NON-NLS-1$

        /**
         * Langues disponibles.
         */
        public static final String AVAILABLES_LANGS = "lang.availablesLangs"; //$NON-NLS-1$

        /**
         * Chaine contenant toutes les langues disponibles(traduites ou non).
         */
        public static final String ALL_LANGS = "lang.allLangs";
    }

    /**
     * Classe fournissant des constantes pour accéder à la section de configuration JDBC.
     */
    public static final class JDBC
    {

        private JDBC()
        {
        }

        /**
         * Nom de l'objet nommé à appeller dans un servlet pour accéder à la source JDBC.
         */
        public static final String NAME = "sql.name"; //$NON-NLS-1$

        /**
         * Nombre de résultats par page par défaut.
         */
        public static final String DEFAULT_PAGE_COUNT = "sql.default_page_count"; //$NON-NLS-1$

        /**
         * Nombre maximum de résultats de requête.
         */
        public static final String MAX_RESULTS = "sql.max_results"; //$NON-NLS-1$

    }

    /**
     * Configuration de serveur SMTP.
     */
    public static final class MAIL
    {

        private MAIL()
        {
        }

        /**
         * Hote SMTP.
         */
        public static final String SMTP_HOST = "mail.smtp_host"; //$NON-NLS-1$

        /**
         * Mail de l'expéditeur des messages.
         */
        public static final String FROM = "mail.from"; //$NON-NLS-1$

        /**
         * Si la valeur est autre que "0", les mails ne seront pas envoyés mais les fonctions de mails se comporteront
         * comme si c'etait le cas.
         */
        public static final String NO_MAIL = "mail.nomail"; //$NON-NLS-1$

        /**
         * Mail du développeur.
         */
        public static final String DEV_MAIL = "mail.devMail";

    }

    /**
     * Configuration du domaine.
     */
    public static final class HOST
    {

        private HOST()
        {
        }

        /**
         * Url complète du site.
         */
        public static final String FULL_URL = "host.full_url"; //$NON-NLS-1$

        /**
         * Nom du site web.
         */
        public static final String SITE_NAME = "host.site_name"; //$NON-NLS-1$
    }

    /**
     * Classe fournissant des constantes relatives à la configuration générale.
     */
    public static final class MAIN
    {

        private MAIN()
        {
        }

        /**
         * placé à un pour activer le mode de tests web.
         */
        public static final String WEB_TEST_MODE = "main.web_test_mode"; //$NON-NLS-1$

    }

}
