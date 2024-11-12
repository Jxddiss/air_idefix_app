package com.nicholson.client_reservation_vol.présentation

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

}