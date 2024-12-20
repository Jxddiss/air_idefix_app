package com.nicholson.client_reservation_vol.donnée.http.encodeur

import com.google.gson.stream.JsonWriter
import com.nicholson.client_reservation_vol.domaine.entité.Réservation
import com.nicholson.client_reservation_vol.donnée.exceptions.SourceDeDonnéesException

class EncodeurJSONRéservation {

    companion object {
        fun encoderRéservation(writer: JsonWriter , réservation: Réservation) {

            writer.beginObject()
            writer.name("idVol").value(réservation.idVol)

            if (réservation.client == null) {
                throw SourceDeDonnéesException("Client est null")
            }
            writer.name("clientCourriel").value(réservation.client!!.email)

            if (réservation.siège == null) {
                throw SourceDeDonnéesException("Siège est null")
            }
            writer.name("siège")
            EncodeurJSONSiège.encoderSiège(writer, réservation.siège)

            writer.name("classe").value(réservation.classe)
            writer.name("bagages").value(1)
            writer.endObject()
        }


    }
}