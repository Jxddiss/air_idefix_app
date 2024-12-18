package com.nicholson.client_reservation_vol.donnée.http.encodeur

import com.google.gson.stream.JsonWriter
import com.nicholson.client_reservation_vol.domaine.entité.Réservation
import com.nicholson.client_reservation_vol.donnée.exceptions.SourceDeDonnéesException

class EncodeurJSONRéservation {

    companion object {
        fun encoderRéservation(writer: JsonWriter , réservation: Réservation) {

            writer.beginObject()
            writer.name("id").value(réservation.id)
            writer.name("numéroRéservation").value(réservation.numéroRéservation)
            writer.name("idVol").value(réservation.idVol)


            if (réservation.client == null) {
                throw SourceDeDonnéesException("Client est null")
            }
            writer.name("client")
            EncodeurJSONClient.encoderClient(writer, réservation.client!!)


            if (réservation.siège == null) {
                throw SourceDeDonnéesException("Siège est null")
            }
            writer.name("siège")
            EncodeurJSONSiège.encoderSiège(writer, réservation.siège)

            writer.name("classe").value(réservation.classe)
            writer.endObject()
        }


    }
}