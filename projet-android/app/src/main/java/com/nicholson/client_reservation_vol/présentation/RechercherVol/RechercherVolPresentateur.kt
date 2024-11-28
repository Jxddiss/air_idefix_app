package com.nicholson.client_reservation_vol.présentation.RechercherVol

import android.util.Log
import com.nicholson.client_reservation_vol.domaine.entité.Historique
import com.nicholson.client_reservation_vol.domaine.entité.Ville
import com.nicholson.client_reservation_vol.présentation.Modèle
import com.nicholson.client_reservation_vol.présentation.OTD.FiltreRechercheVol
import com.nicholson.client_reservation_vol.présentation.OTD.HistoriqueListItemOTD
import com.nicholson.client_reservation_vol.présentation.RechercherVol.ContractRechercherVol.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class RechercherVolPresentateur:  ContractRechercherVol.IRechercheVolVuePrésentateur{

    private val modèle: Modèle = Modèle.obtenirInstance()
    private var vue: IRechercheVolVue? = null

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
                                      dateRetour:String?,
                                      estAllerSimple: Boolean
                                      ) {

        if(villeAeroportDe.isEmpty() || villeAeroportVers.isEmpty() || dateDebutString.isEmpty()){
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


            val dateDebutLocal = LocalDate.parse(dateDebutString, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            val historique: Historique = if (estAllerSimple) {

                Historique(
                    villeDe = aeroportDe.ville.nom,
                    villeVers = aeroportVers.ville.nom,
                    aeroportDe = aeroportDe.code,
                    aeroportVers = aeroportVers.code,
                    dateDepart = dateDebutLocal,
                    dateRetour = null
                )
            } else {

                if (dateRetour.isNullOrEmpty()) {
                    vue?.afficherToast("Erreur, veuillez sélectionner une date de retour.")
                    return
                }

                val dateRetourLocal = LocalDate.parse(dateRetour, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                Historique(
                    villeDe = aeroportDe.ville.nom,
                    villeVers = aeroportVers.ville.nom,
                    aeroportDe = aeroportDe.code,
                    aeroportVers = aeroportVers.code,
                    dateDepart = dateDebutLocal,
                    dateRetour = dateRetourLocal
                )
            }

            enregistrerRecherche(historique)
            vue?.redirigerVersListeVols()

        }catch (ex :  Exception){
            Log.d("Erreur", ex.message.toString())
        }

    }


    override fun traiterActionRecherche() {
       vue?.obtenirInfoRecherche()
    }


    // pour l'instant j'ai ajoute cet log pour verifier que tout est sur ma listeHistorique et si tout est bien sauvarger
    private fun enregistrerRecherche(historique: Historique) {
        //Log.d("RechercherVolPresent", "Essaye d'ajouter a la bd: $historique")
        modèle.créerHistorique(historique)
        Log.d("RechercherVolPresent ", "Historique ajouter: $historique")
        //Log.d("RechercherVolPresent", "ma listHistorique maintenant: $listHistorique")
    }

    override fun traiterObtenirHistorique() {
        val listeDeHistorique = modèle.listeHistorique
        Log.d("HistoriquePrésentateur", "Historique liste size: ${listeDeHistorique.size}")

        if (listeDeHistorique.isNotEmpty()) {
            val firstHistorique = listeDeHistorique.first()
            val historiqueOTD = HistoriqueListItemOTD(
                villeDe = firstHistorique.villeDe,
                villeVers = firstHistorique.villeVers,
                aeroportDe = firstHistorique.aeroportDe,
                aeroportVers = firstHistorique.aeroportVers,
                dateDepart = firstHistorique.dateDepart,
                dateRetour = firstHistorique.dateRetour
            )
            vue?.afficherHistorique(historiqueOTD)
        }
    }

}