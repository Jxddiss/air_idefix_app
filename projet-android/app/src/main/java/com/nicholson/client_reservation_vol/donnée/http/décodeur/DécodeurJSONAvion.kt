package com.nicholson.client_reservation_vol.donnée.http.décodeur

import com.google.gson.stream.JsonReader
import com.google.gson.stream.MalformedJsonException
import com.nicholson.client_reservation_vol.domaine.entité.Avion
import com.nicholson.client_reservation_vol.donnée.exceptions.SourceDeDonnéesException
import java.io.EOFException

class DécodeurJSONAvion {
    companion object {
        fun décoderAvion(reader: JsonReader) : Avion {
            var id = 0
            var type = ""

            try {
                reader.beginObject()
                while ( reader.hasNext() ) {
                    when( reader.nextName() ) {
                        "id" -> id = reader.nextInt()
                        "type" -> type = reader.nextString()
                        else -> reader.skipValue()
                    }
                }
                reader.endObject()

                return Avion(id, type)

            } catch ( ex : EOFException) {
                throw SourceDeDonnéesException("Format JSON invalide : ${ex.message}")
            } catch(ex : MalformedJsonException){
                throw SourceDeDonnéesException("Format JSON invalide : ${ex.message}")
            }
        }
    }
}