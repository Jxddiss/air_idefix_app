package com.nicholson.client_reservation_vol.donnée.http.encodeur

import com.google.gson.stream.JsonWriter
import com.nicholson.client_reservation_vol.domaine.entité.Client

class EncodeurJSONClient {

    companion object {
        fun encoderClient(writer: JsonWriter, client: Client){
            writer.beginObject()
            writer.name("id").value(client.id)
            writer.name("nom").value(client.nom)
            writer.name("prénom").value(client.prénom)
            writer.name("adresse").value(client.adresse)
            writer.name("numéroPasseport").value(client.numéroPasseport)
            writer.name("email").value(client.email)
            writer.name("numéroTéléphone").value(client.numéroTéléphone)
            writer.endObject()
        }
    }
}