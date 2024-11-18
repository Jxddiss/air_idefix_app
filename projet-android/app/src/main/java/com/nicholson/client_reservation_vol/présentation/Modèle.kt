package com.nicholson.client_reservation_vol.présentation

import android.util.Log
import com.nicholson.client_reservation_vol.domaine.entité.Aeroport
import com.nicholson.client_reservation_vol.domaine.entité.Historique
import com.nicholson.client_reservation_vol.domaine.entité.Réservation
import com.nicholson.client_reservation_vol.domaine.entité.Ville
import com.nicholson.client_reservation_vol.domaine.entité.Vol
import com.nicholson.client_reservation_vol.domaine.interacteur.HistoriqueService
import com.nicholson.client_reservation_vol.domaine.interacteur.VolService
import com.nicholson.client_reservation_vol.donnée.SourceDeDonnées
import com.nicholson.client_reservation_vol.donnée.fictive.SourceDonnéesFictive
import com.nicholson.client_reservation_vol.présentation.OTD.FiltreRechercheHistorique
import com.nicholson.client_reservation_vol.présentation.OTD.FiltreRechercheVol
import java.time.LocalDate
import java.time.LocalDateTime

class Modèle private constructor( private val volService : VolService = VolService(),
                                  private val historiqueService: HistoriqueService = HistoriqueService() ) {

    companion object {
        @Volatile
        private var instance: Modèle? = null

        fun obtenirInstance() =
            instance ?: synchronized(this) {
                instance ?: Modèle().also { instance = it }
            }
    }

    // À remplacer par des services lié à chaque collection
    val sourceDeDonnées: SourceDeDonnées = SourceDonnéesFictive()
    var indiceVolCourrant: Int = 0
    var filtreVolCourrant = FiltreRechercheVol(
        LocalDateTime.now(), "YUL", "JFK"
    )



    var listeVol: List<Vol> = listOf()
        get() {
            if (field.isEmpty()) {
                field = volService.obtenirListeVol()
            }
            return field
        }
    var listeRéservation: MutableList<Réservation> = mutableListOf()
        get() {
            if (field.isEmpty()) {
                field = sourceDeDonnées.getListRéservation()
            }
            return field
        }

    var listeHistorique: List<Historique> = listOf()
        get() {
            if (field.isEmpty()) {
                field = historiqueService.obtenirListeHistorique()
            }
            return field
        }

    fun getVolCourrant(): Vol =
        volService.obtenirVolParId(listeVol[indiceVolCourrant].id)

    fun obtenirListeVolParFiltre(): List<Vol> {
        listeVol = volService.obtenirListeVolParFiltre(filtreVolCourrant)
        return listeVol
    }

    fun obtenirVolParId(id: Int): Vol {
        return volService.obtenirVolParId(id)
    }

    fun avancerVolCourrant() {
        if (indiceVolCourrant < listeVol.size - 1) {
            indiceVolCourrant++
        } else {
            indiceVolCourrant = 0
        }
    }

    fun reculerVolCourrant() {
        if (indiceVolCourrant > 0) {
            indiceVolCourrant--
        } else {
            indiceVolCourrant = listeVol.size - 1
        }
    }

    fun getVolPrécédent(): Vol {
        if (indiceVolCourrant > 0) {
            return listeVol[indiceVolCourrant - 1]
        } else {
            return listeVol[listeVol.size - 1]
        }
    }

    fun getVolSuivant(): Vol {
        if (indiceVolCourrant < listeVol.size - 1) {
            return listeVol[indiceVolCourrant + 1]
        } else {
            return listeVol[0]
        }
    }



    fun obtenirListeAéroports(): List<Aeroport> {
        return SourceDonnéesFictive.listAeoroport
    }


    //Funtion pour sauvarger les info pour apres les obtenir dans lhistorique
    fun ajouterRecherche(historique: Historique) {
        historiqueService.ajouterHistorique(historique)
    }


    fun obtenirHistoriqueRecherche(): List<Historique> {
        return historiqueService.obtenirListeHistorique()
    }


}