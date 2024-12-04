package com.nicholson.client_reservation_vol.donnée.http.décodeur

import com.google.gson.stream.JsonReader
import com.google.gson.stream.MalformedJsonException
import com.nicholson.client_reservation_vol.domaine.entité.Client
import com.nicholson.client_reservation_vol.donnée.exceptions.SourceDeDonnéesException
import java.io.EOFException
import java.io.StringReader

class DécodeurJSONClient {

    companion object {
        fun décoderClient( json : String ) : Client{
            val reader = JsonReader(StringReader(json))
            var id : Int = 0
            lateinit var nom : String
            lateinit var prénom : String
            lateinit var adresse : String
            lateinit var numéroPasseport : String
            var email : String? = null
            var numéroTéléphone : String? = null

            try {
                reader.beginObject()
                while ( reader.hasNext() ) {
                    when( reader.nextName() ) {
                        "id" -> id = reader.nextInt()
                        "nom" -> nom = reader.nextString()
                        "prénom" -> prénom = reader.nextString()
                        "adresse" -> adresse = reader.nextString()
                        "numéroPasseport" -> numéroPasseport = reader.nextString()
                        "email" -> email = reader.nextString()
                        "numéroTéléphone" -> numéroTéléphone = reader.nextString()
                        else -> reader.skipValue()
                    }
                }
                reader.endObject()

                return Client(id, nom, prénom, adresse, numéroPasseport, email, numéroTéléphone)

            } catch ( ex : EOFException) {
                throw SourceDeDonnéesException("Format JSON invalide : ${ex.message}")
            } catch(ex : MalformedJsonException){
                throw SourceDeDonnéesException("Format JSON invalide : ${ex.message}")
            }
        }
        }
}
