package com.nicholson.client_reservation_vol.donnée.http.décodeur

import com.google.gson.stream.JsonReader
import com.google.gson.stream.MalformedJsonException
import com.nicholson.client_reservation_vol.domaine.entité.Siège
import com.nicholson.client_reservation_vol.donnée.exceptions.SourceDeDonnéesException
import java.io.EOFException

class DécodeurJSONSiège {

    companion object {
        fun décoderSiege( reader: JsonReader ) : Siège{
            var id = 0
            lateinit var numéro : String
            lateinit var classe : String
            lateinit var statut : String
            var idRéservation = 0
            var idVol = 0

            try{
                reader.beginObject()
                while( reader.hasNext() ){
                    when( reader.nextName() ){
                        "id" -> id = reader.nextInt()
                        "numéroSiège" -> numéro = reader.nextString()
                        "classe" -> classe = reader.nextString()
                        "statut" -> statut = reader.nextString()
                        else -> reader.skipValue()
                    }

                }
                reader.endObject()

                return Siège(id,numéro,classe,statut,idRéservation,idVol)
            }
            catch ( ex : EOFException) {
                throw SourceDeDonnéesException("Format JSON invalide : ${ex.message}")
            } catch(ex : MalformedJsonException){
                throw SourceDeDonnéesException("Format JSON invalide : ${ex.message}")
            }

        }
    }
}