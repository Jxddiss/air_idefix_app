package com.nicholson.client_reservation_vol.donnée.http.encodeur

import com.google.gson.stream.JsonWriter
import com.nicholson.client_reservation_vol.domaine.entité.Client
import com.nicholson.client_reservation_vol.domaine.entité.Siège

class EncodeurJSONSiège {

    companion object {
        fun encoderSiège(writer: JsonWriter, siège: Siège) {
            writer.beginObject()
            writer.name("id").value(siège.id)
            writer.name("numéroSiège").value(siège.numéro)
            writer.name("classe").value(siège.classe)
            writer.name("statut").value(siège.statut)
            writer.endObject()
        }
    }

}