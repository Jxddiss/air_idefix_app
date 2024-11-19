package com.nicholson.client_reservation_vol.présentation.RechercherVol

import android.util.Log
import com.nicholson.client_reservation_vol.domaine.entité.Historique
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
    private val listHistorique = mutableListOf<Historique>()

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
            vue?.afficherToast("Erreur, veuillez sélectionner tous les champs.")
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

            val nbrPassagersInt = nbrPassagers.toInt()
            val dateDebutLocal = LocalDate.parse(dateDebutString, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            val dateRetourLocal = LocalDate.parse(dateDebutString, DateTimeFormatter.ofPattern("dd/MM/yyyy"))

            //save historique ici
            val historique = Historique(
                villeDe = aeroportDe.ville.nom,
                villeVers = aeroportVers.ville.nom,
                aeroportDe = aeroportDe.code,
                aeroportVers = aeroportVers.code,
                dateDepart = dateDebutLocal,
                dateRetour = dateRetourLocal,
                nbrPassangers = nbrPassagersInt
            )

            enregistrerRecherche(historique)

            vue?.redirigerVersListeVols()
        }catch (ex :  Exception){
            Log.d("Erreur", ex.message.toString())
            return
        }

    }


    override fun traiterActionRecherche() {
       vue?.obtenirInfoRecherche()
    }


    // pour l'instant j'ai ajoute cet log pour verifier que tout est sur ma listeHistorique et oui tout est bien sauvarger
    private fun enregistrerRecherche(historique: Historique) {
        listHistorique.add(historique)
        Log.d("Historique", "Historique added: $historique")
        Log.d("Historique", "Current listHistorique: $listHistorique")
    }

}