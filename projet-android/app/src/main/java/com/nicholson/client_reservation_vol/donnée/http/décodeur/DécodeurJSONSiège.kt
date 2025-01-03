package com.nicholson.client_reservation_vol.donnée.http.décodeur

import com.google.gson.stream.JsonReader
import com.google.gson.stream.MalformedJsonException
import com.nicholson.client_reservation_vol.domaine.entité.Siège
import com.nicholson.client_reservation_vol.donnée.exceptions.SourceDeDonnéesException
import java.io.EOFException
import java.io.StringReader

class DécodeurJSONSiège {

    companion object {
        fun décoderListSiège( json : String ) : List<Siège> {
            val reader = JsonReader( StringReader( json ) )
            val listSièges = mutableListOf<Siège>()

            try {
                reader.beginArray()
                while ( reader.hasNext() ) {
                    listSièges.add( décoderSiege( reader ) )
                }
                reader.endArray()
            } catch ( ex : EOFException) {
                throw SourceDeDonnéesException("Format JSON invalide : ${ex.message}")
            } catch(ex : MalformedJsonException){
                throw SourceDeDonnéesException("Format JSON invalide : ${ex.message}")
            }

            return listSièges
        }

        fun décoderSiege( reader: JsonReader ) : Siège{
            var id = 0
            lateinit var numéro : String
            lateinit var classe : String
            lateinit var statut : String

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

                return Siège(id,numéro,classe,statut)
            }
            catch ( ex : EOFException) {
                throw SourceDeDonnéesException("Format JSON invalide : ${ex.message}")
            } catch(ex : MalformedJsonException){
                throw SourceDeDonnéesException("Format JSON invalide : ${ex.message}")
            }

        }
    }
}