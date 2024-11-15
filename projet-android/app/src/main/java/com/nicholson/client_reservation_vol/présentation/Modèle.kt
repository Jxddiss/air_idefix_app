package com.nicholson.client_reservation_vol.présentation

import com.nicholson.client_reservation_vol.domaine.entité.Réservation
import com.nicholson.client_reservation_vol.domaine.entité.Vol
import com.nicholson.client_reservation_vol.domaine.interacteur.VolService
import com.nicholson.client_reservation_vol.donnée.SourceDeDonnées
import com.nicholson.client_reservation_vol.donnée.fictive.SourceDonnéesFictive
import com.nicholson.client_reservation_vol.présentation.OTD.FiltreRechercheVol
import java.time.LocalDateTime

class Modèle private constructor( private val volService : VolService = VolService() ) {

    companion object{
        @Volatile
        private var instance : Modèle? = null

        fun obtenirInstance() =
            instance ?: synchronized(this){
                instance ?: Modèle().also { instance = it }
            }
    }

    val sourceDeDonnées : SourceDeDonnées = SourceDonnéesFictive()
    var indiceVolCourrant : Int = 0
    var filtreVolCourrant = FiltreRechercheVol(
        LocalDateTime.now(), "YUL", "JFK"
    )

    var listeVol : List<Vol> = listOf()
        get(){
            if ( field.isEmpty() ){
                field = volService.obtenirListeVol()
            }
            return field
        }
    var listeRéservation : MutableList<Réservation> = mutableListOf()
        get(){
            if (field.isEmpty() ){
                field = sourceDeDonnées.getListRéservation()
            }
            return field
        }

    fun getVolCourrant() : Vol =
        volService.obtenirVolParId( listeVol[indiceVolCourrant].id )

    fun obtenirListeVolParFiltre() : List<Vol> {
        listeVol = volService.obtenirListeVolParFiltre( filtreVolCourrant )
        return listeVol
    }
}