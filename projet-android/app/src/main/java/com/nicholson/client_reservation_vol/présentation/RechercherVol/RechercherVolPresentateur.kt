package com.nicholson.client_reservation_vol.présentation.RechercherVol

import android.util.Log
import com.nicholson.client_reservation_vol.domaine.entité.Ville
import com.nicholson.client_reservation_vol.présentation.Modèle
import com.nicholson.client_reservation_vol.présentation.OTD.FiltreRechercheVol
import com.nicholson.client_reservation_vol.présentation.RechercherVol.ContractRechercherVol.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class RechercherVolPresentateur:  ContractRechercherVol.IRechercheVolVuePrésentateur {

    private val modèle: Modèle = Modèle.obtenirInstance()
    private var vue: IRechercheVolVue? = null
    private val listHistorique: MutableList<String> = mutableListOf()


    override fun attacherVue(vue: ContractRechercherVol.IRechercheVolVue) {
        this.vue = vue
    }

    override fun détacherVue() {
        vue = null
    }

    override fun obtenirListeVilles() {
        val aéroportsAvecCodes = modèle.obtenirListeAéroports().map { "${it.ville.nom} (${it.code})" }
        vue?.afficherListeVilles(aéroportsAvecCodes)
    }

    override fun traiterInfoRecherche(villeAeroportDe: String,
                                      villeAeroportVers: String,
                                      dateDebutString:String,
                                      dateRetour:String,
                                      nbrPassagers:String) {

        if(villeAeroportDe.isEmpty() || villeAeroportVers.isEmpty() || dateDebutString.isEmpty() || nbrPassagers.isEmpty()){
            return

        }
        val aeroportDe=modèle.obtenirListeAéroports().first{
            villeAeroportDe.contains(it.code)
        }
        val aeroportVers=modèle.obtenirListeAéroports().first {
            villeAeroportVers.contains(it.code)
        }

        try{
            val dateDebut = LocalDateTime.parse(dateDebutString+" 00:00",DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
            modèle.filtreVolCourrant = FiltreRechercheVol(
                dateDébut = dateDebut,
                codeAéroportDébut = aeroportDe.code,
                codeAéroportFin = aeroportVers.code
            )

            //historique
            vue?.redirigerVersListeVols()
        }catch (ex :  Exception){
            Log.d("Erreur", ex.message.toString())
            return
        }

    }


    override fun traiterActionRecherche() {
       vue?.obtenirInfoRecherche()
    }



    fun enregistrerRecherche(villeDe: String, villeVers: String, aeroportDe: String, aeroportVers: String, dateDepart: LocalDate, dateRetour: LocalDate, nbrPassagers: Int) {
        val recherche = "De $villeDe ($aeroportDe) vers $villeVers ($aeroportVers), départ: $dateDepart, retour: $dateRetour, passagers: $nbrPassagers"
        listHistorique.add(recherche)
    }
}