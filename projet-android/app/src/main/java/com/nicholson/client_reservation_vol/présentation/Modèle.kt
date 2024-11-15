package com.nicholson.client_reservation_vol.présentation

import com.nicholson.client_reservation_vol.domaine.entité.Réservation
import com.nicholson.client_reservation_vol.domaine.entité.Vol
import com.nicholson.client_reservation_vol.donnée.SourceDeDonnées
import com.nicholson.client_reservation_vol.donnée.fictive.SourceDonnéesFictive

class Modèle private constructor() {

    companion object{
        @Volatile
        private var instance : Modèle? = null

        fun obtenirInstance() =
            instance ?: synchronized(this){
                instance ?: Modèle().also { instance = it }
            }
    }

    val sourceDeDonnées : SourceDeDonnées = SourceDonnéesFictive()
    var listeVol : List<Vol> = listOf()
        get(){
            if ( field.isEmpty() ){
                field = sourceDeDonnées.getListeVol()
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

    var indiceVolCourrant : Int = 0

    fun getVolCourrant() : Vol{
        return listeVol[indiceVolCourrant]
    }
}