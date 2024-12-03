package com.nicholson.client_reservation_vol.donnée.http.décodeur

import android.util.Log
import com.google.gson.stream.JsonReader
import com.google.gson.stream.MalformedJsonException
import com.nicholson.client_reservation_vol.domaine.entité.Aeroport
import com.nicholson.client_reservation_vol.domaine.entité.Ville
import com.nicholson.client_reservation_vol.donnée.exceptions.SourceDeDonnéesException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.io.EOFException
import java.io.StringReader

class DécodeurJSONAéroport {
    companion object {
        fun décodéListeAéroports(json : String ) : List<Aeroport> {
            val reader = JsonReader( StringReader( json ) )
            val listAeroport = mutableListOf<Aeroport>()
            CoroutineScope( Dispatchers.Main )
            try {
                reader.beginArray()
                while ( reader.hasNext() ) {
                    listAeroport.add( décodéAeroport( reader ) )
                }
                reader.endArray()
            } catch ( ex : EOFException) {
                throw SourceDeDonnéesException("Format JSON invalide : ${ex.message}")
            } catch(ex : MalformedJsonException){
                throw SourceDeDonnéesException("Format JSON invalide : ${ex.message}")
            }

            return listAeroport
        }

        fun décodéAeroport(reader: JsonReader ) : Aeroport {
            var id = 0
            lateinit var code : String
            lateinit var nom : String
            lateinit var ville : Ville

            try {
                reader.beginObject()
                while ( reader.hasNext() ) {
                    when( reader.nextName() ) {
                        "id" -> id = reader.nextInt()
                        "code" -> code = reader.nextString()
                        "nom" -> nom = reader.nextString()
                        "ville" -> ville = DécodeurJSONVille.décodéVille( reader )
                        else -> reader.skipValue()
                    }
                }
                reader.endObject()

                return Aeroport(id, code, nom, ville, ville.pays)
            } catch ( ex : EOFException) {
                throw SourceDeDonnéesException("Format JSON invalide : ${ex.message}")
            } catch(ex : MalformedJsonException){
                throw SourceDeDonnéesException("Format JSON invalide : ${ex.message}")
            }
        }
    }
}
