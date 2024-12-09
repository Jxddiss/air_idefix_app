package com.nicholson.client_reservation_vol.donnée.http.décodeur


import com.google.gson.stream.JsonReader
import com.google.gson.stream.MalformedJsonException
import com.nicholson.client_reservation_vol.domaine.entité.Client
import com.nicholson.client_reservation_vol.domaine.entité.Réservation
import com.nicholson.client_reservation_vol.domaine.entité.Siège
import com.nicholson.client_reservation_vol.donnée.exceptions.SourceDeDonnéesException
import java.io.EOFException
import java.io.StringReader

class DécodeurJSONRéservation {

    companion object{
        fun décoderRéservation( json : String ) : Réservation {
            val reader = JsonReader(StringReader(json))
            var id : Int = 0
            lateinit var numéroRéservation: String
            var idVol : Int = 0
            lateinit var client : Client
            lateinit var siege : Siège
            lateinit var classe : String

            try {
                reader.beginObject()
                while ( reader.hasNext() ){
                    when ( reader.nextName() ){
                        "id" -> id = reader.nextInt()
                        "numéroRéservation" -> numéroRéservation = reader.nextString()
                        "idVol" -> idVol = reader.nextInt()
                        "client" ->  client = DécodeurJSONClient.décoderClient( reader )
                        "siège" -> siege = DécodeurJSONSiège.décoderSiege( reader )
                        "classe" -> classe = reader.nextString()
                        else -> reader.skipValue()
                    }

                }
                reader.endObject()
                siege.idRéservation = id
                siege.idVol = idVol
                return Réservation(id,numéroRéservation,idVol,client,siege,classe)
            }
            catch ( ex : EOFException) {
                throw SourceDeDonnéesException("Format JSON invalide : ${ex.message}")
            } catch(ex : MalformedJsonException){
                throw SourceDeDonnéesException("Format JSON invalide : ${ex.message}")
            } catch( ex : NumberFormatException){
                throw SourceDeDonnéesException("Format JSON invalide : ${ex.message}")
            }

        }
    }
}