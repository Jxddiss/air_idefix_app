package com.nicholson.client_reservation_vol.donnée.http.décodeur

import android.util.JsonReader
import android.util.MalformedJsonException
import com.nicholson.client_reservation_vol.domaine.entité.Aeroport
import com.nicholson.client_reservation_vol.domaine.entité.Avion
import com.nicholson.client_reservation_vol.domaine.entité.Vol
import com.nicholson.client_reservation_vol.domaine.entité.VolStatut
import com.nicholson.client_reservation_vol.donnée.exceptions.SourceDeDonnéesException
import java.io.EOFException
import java.io.StringReader
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class DécodeurJSONVol {
    companion object {
        fun décodéListeVols(json : String ) : List<Vol> {
            val reader = JsonReader( StringReader( json ) )
            val listVol = mutableListOf<Vol>()

            try {
                reader.beginArray()
                while ( reader.hasNext() ){
                    listVol.add( décodéVol( reader ) )
                }
                reader.endArray()
            } catch ( ex : EOFException) {
                throw SourceDeDonnéesException("Format JSON invalide : ${ex.message}")
            } catch(ex : MalformedJsonException){
                throw SourceDeDonnéesException("Format JSON invalide : ${ex.message}")
            }

            return listVol
        }

        fun décodéVol(json : String ) : Vol {
            val reader = JsonReader( StringReader( json ) )
            return décodéVol( reader )
        }

        private fun décodéVol(reader: JsonReader ) : Vol {
            var id  = 0
            lateinit var numeroVol: String
            lateinit var aeroportDebut: Aeroport
            lateinit var aeroportFin: Aeroport
            lateinit var dateDepart: LocalDateTime
            lateinit var dateArrivee: LocalDateTime
            lateinit var avion: Avion
            lateinit  var prixParClasse: Map<String, Double>
            var poidsMaxBag: Int = 0
            lateinit var statutVol: List<VolStatut>
            var durée: Duration = Duration.ZERO

            try {
                reader.beginObject()
                while ( reader.hasNext() ) {
                    when( reader.nextName() ) {
                        "id" -> id = reader.nextInt()
                        "trajet" -> {
                            reader.beginObject()
                            while (reader.hasNext()) {
                                when (reader.nextName()) {
                                    "numéroTrajet" -> numeroVol = reader.nextString()
                                    "aéroportDébut" -> aeroportDebut =
                                        DécodeurJSONAéroport.décodéAeroport(reader)
                                    "aéroportFin" -> aeroportFin =
                                        DécodeurJSONAéroport.décodéAeroport(reader)
                                    else -> reader.skipValue()
                                }
                            }
                            reader.endObject()
                        }
                        "dateDepart" -> dateDepart = LocalDateTime.parse( reader.nextString() )
                        "dateArrivee" -> dateArrivee = LocalDateTime.parse( reader.nextString() )
                        "avion" -> avion = DécodeurJSONAvion.décodéAvion( reader )
                        "prixParClasse" -> {
                            val tempMap = mutableMapOf<String, Double>()
                            reader.beginObject()
                            while ( reader.hasNext() ) {
                                when ( reader.nextName() ){
                                    "économique" -> {
                                        val key = "Économique"
                                        val value = reader.nextDouble()
                                        tempMap[key] = value
                                    }
                                    "affaire" -> {
                                        val key = "Affaire"
                                        val value = reader.nextDouble()
                                        tempMap[key] = value
                                    }
                                    "première" -> {
                                        val key = "Première"
                                        val value = reader.nextDouble()
                                        tempMap[key] = value
                                    }
                                    else -> reader.skipValue()
                                }
                            }
                            reader.endObject()
                            prixParClasse = tempMap
                        }
                        "poidsMaxBag" -> poidsMaxBag = reader.nextInt()
                        "vol_statut" -> statutVol = décodéStatutVol( reader )
                        "durée" -> durée = Duration.parse( reader.nextString() )
                        else -> reader.skipValue()
                    }
                }
                reader.endObject()
                return Vol(id, numeroVol, aeroportDebut, aeroportFin, dateDepart, dateArrivee, avion, prixParClasse, poidsMaxBag, statutVol, durée)
            } catch ( ex : EOFException) {
                throw SourceDeDonnéesException("Format JSON invalide : ${ex.message}")
            } catch(ex : MalformedJsonException){
                throw SourceDeDonnéesException("Format JSON invalide : ${ex.message}")
            }
        }

        private fun décodéStatutVol(reader: JsonReader ) : List<VolStatut>{
            val listStatutVol = mutableListOf<VolStatut>()
            try {
                reader.beginArray()
                while (reader.hasNext()) {
                    reader.beginObject()
                    var idVol = 0
                    var statut = ""
                    var heure = LocalTime.MIN
                    while (reader.hasNext()) {
                        when (reader.nextName()) {
                            "idVol" -> idVol = reader.nextInt()
                            "statut" -> statut = reader.nextString()
                            "heure" -> heure = LocalTime.parse(
                                reader.nextString(),
                                DateTimeFormatter.ISO_LOCAL_DATE_TIME )
                            else -> reader.skipValue()
                        }
                    }
                    reader.endObject()
                    listStatutVol.add(VolStatut(idVol, statut, heure))
                }
                reader.endArray()
            } catch ( ex : EOFException) {
                throw SourceDeDonnéesException("Format JSON invalide : ${ex.message}")
            } catch(ex : MalformedJsonException){
                throw SourceDeDonnéesException("Format JSON invalide : ${ex.message}")
            }
            return listStatutVol
        }
    }
}