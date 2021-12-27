package com.pavastudios.TomMaso.api;

import com.pavastudios.TomMaso.storage.model.Utente;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Interfaccia che definisce l'azione da eseguire se la richiesta inviata dal client è corretta,
 * se l'attributo <i>requireLogin</i> dell'Endpoint collegato a questo endpoint è settato a <i>true</i>
 * allora <i>user</i> è garantito essere diverso da <i>null</i>,
 * inoltre eseguendo <i>getValueFromName(param)</i> con <i>param</i> contenuto nei parametri dell'Endpoint collegato
 * garantisce che la chiamata non ritorni <i>null</i>
 *
 * @see Endpoint
 */
public interface ApiAction {
    /**
     * Azione da eseguire se la richiesta inviata dal client è corretta
     *
     * @param parser contiene i parametri passati nella richiesta HTTP
     * @param writer il writer in cui scrivere la risposta
     * @param user   null se l'utente non ha eseguito il login, altrimenti contiene l'utente che ha eseguito la richiesta
     */
    void executeEndpoint(ApiParser parser, ApiWriter writer, Utente user) throws SQLException, IOException;
}
