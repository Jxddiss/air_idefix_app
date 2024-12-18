package com.nicholson.client_reservation_vol.donnée.http.décodeur

import com.google.gson.stream.JsonReader
import com.google.gson.stream.MalformedJsonException
import com.nicholson.client_reservation_vol.domaine.entité.Ville
import com.nicholson.client_reservation_vol.donnée.exceptions.SourceDeDonnéesException
import java.io.EOFException

class DécodeurJSONVille {
    companion object {
        fun décoderVille(reader: JsonReader ) : Ville {
            var id = 0
            var nom = ""
            var pays = ""
            var url_photo = ""

            try {
                reader.beginObject()
                while ( reader.hasNext() ) {
                    when( reader.nextName() ) {
                        "id" -> id = reader.nextInt()
                        "nom" -> nom = reader.nextString()
                        "pays" -> pays = reader.nextString()
                        "url_photo" -> url_photo = reader.nextString()
                        else -> reader.skipValue()
                    }
                }
                reader.endObject()
                return Ville(id, nom, pays, url_photo)
            } catch ( ex : EOFException) {
                throw SourceDeDonnéesException("Format JSON invalide : ${ex.message}")
            } catch(ex : MalformedJsonException){
                throw SourceDeDonnéesException("Format JSON invalide : ${ex.message}")
            }

        }
    }
}
