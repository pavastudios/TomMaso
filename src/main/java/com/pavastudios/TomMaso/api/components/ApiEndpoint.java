package com.pavastudios.TomMaso.api.components;

import com.pavastudios.TomMaso.api.groups.BlogEndpoint;
import com.pavastudios.TomMaso.model.Utente;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Consente di definire un'endpoint per il servizio di API del sito
 */
public class ApiEndpoint {

    private final Manage action;
    private final Endpoint endpoint;

    /**
     * Crea un nuovo endpoint
     * @param endpointInfo informazioni per la generazione dell'endpoint
     * @param action l'azione che l'endpoint dovrà eseguire quando invocato
     */
    public ApiEndpoint(Endpoint endpointInfo, Manage action) {
        this.action = action;
        this.endpoint = endpointInfo;
    }

    /**
     * @return true se questo endpoint necessita che l'utente abbia eseguito il login per essere eseguito
     */
    public boolean requireLogin() {
        return endpoint.requireLogin();
    }

    /**
     * @return lista dei parametri obbligatori da passare all'endpoint
     * @see ApiParameter
     */
    public ApiParameter[] getParams() {
        return endpoint.params();
    }

    /**
     * Esegui l'azione definita per questo endpoint
     *
     * @see Endpoint
     * @param parser contiene i parametri passati nella richiesta HTTP
     * @param writer il writer in cui scrivere la risposta
     * @param user null se l'utente non ha eseguito il login, altrimenti contiene l'utente che ha eseguito la richiesta
     */
    public void manage(ApiParser parser, ApiWriter writer, Utente user) throws SQLException, IOException {
        action.executeEndpoint(parser, writer, user);
    }

    /**
     * Interfaccia che definisce l'azione da eseguire se la richiesta inviata dal client è corretta,
     * se l'attributo <i>requireLogin</i> dell'Endpoint collegato a questo endpoint è settato a <i>true</i>
     * allora <i>user</i> è garantito essere diverso da <i>null</i>,
     * inoltre eseguendo <i>getValueFromName(param)</i> con <i>param</i> contenuto nei parametri dell'Endpoint collegato
     * garantisce che la chiamata non ritorni <i>null</i>
     *
     * @see ApiParameter
     * @see Endpoint
     */
    public interface Manage {
        /**
         * Azione da eseguire se la richiesta inviata dal client è corretta
         * @param parser contiene i parametri passati nella richiesta HTTP
         * @param writer il writer in cui scrivere la risposta
         * @param user null se l'utente non ha eseguito il login, altrimenti contiene l'utente che ha eseguito la richiesta
         */
        void executeEndpoint(ApiParser parser, ApiWriter writer, Utente user) throws SQLException, IOException;
    }

}
