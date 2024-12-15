package com.nicholson.client_reservation_vol.présentation.RechercherVol

import android.util.Log
import com.nicholson.client_reservation_vol.domaine.entité.Aeroport
import com.nicholson.client_reservation_vol.domaine.entité.Historique
import com.nicholson.client_reservation_vol.donnée.exceptions.SourceDeDonnéesException
import com.nicholson.client_reservation_vol.présentation.Modèle
import com.nicholson.client_reservation_vol.présentation.OTD.FiltreRechercheVol
import com.nicholson.client_reservation_vol.présentation.OTD.HistoriqueListItemOTD
import com.nicholson.client_reservation_vol.présentation.RechercherVol.ContractRechercherVol.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.coroutines.CoroutineContext


class RechercherVolPresentateur( iocontext : CoroutineContext = Dispatchers.IO ):  IRechercheVolVuePrésentateur {

    private val modèle: Modèle = Modèle.obtenirInstance()
    private var vue: IRechercheVolVue? = null
    private var job : Job? = null
    private var iocontext : CoroutineContext = iocontext

    override fun attacherVue(vue: IRechercheVolVue) {
        this.vue = vue
    }

    override fun détacherVue() {
        vue = null
    }

    override fun obtenirListeVilles() {
        vue?.montrerChargement()

        job = CoroutineScope( iocontext ).launch {
            try {
                val aéroportsAvecCodes = modèle.obtenirListeAéroports().map { "${it.ville.nom} (${it.code})" }
                CoroutineScope( Dispatchers.Main ).launch {
                    vue?.afficherListeVilles( aéroportsAvecCodes )
                }
            }catch ( ex : SourceDeDonnéesException ) {
                modèle.messageErreurRéseauExistant = true
                CoroutineScope( Dispatchers.Main ).launch {
                    vue?.redirigerBienvenueErreur()
                }
            }
        }
    }

    override fun traiterInfoRecherche(villeAeroportDe: String,
                                      villeAeroportVers: String,
                                      dateDebutString:String,
                                      dateRetourString:String) {

        job = CoroutineScope( iocontext ).launch {
            //réinitialisation des bool
            modèle.volRetourExiste = false
            modèle.aller = true
            modèle.siegeVolAller = true
            modèle.listeVolAller = listOf()
            modèle.listeVolRetour = listOf()


            if (villeAeroportDe.isEmpty() || villeAeroportVers.isEmpty() || dateDebutString.isEmpty()) {
                CoroutineScope(Dispatchers.Main).launch {
                    vue?.afficherToast("Erreur, veuillez sélectionner tous les champs.")
                }
                return@launch
            }


            val aeroportDe = modèle.obtenirListeAéroports().first {
                villeAeroportDe.contains(it.code)
            }
            val aeroportVers = modèle.obtenirListeAéroports().first {
                villeAeroportVers.contains(it.code)
            }

            try {
                val dateDebut = validerDate(dateDebutString, "La date de départ ne peut pas être avant aujourd'hui.")
                if (dateDebut == null) return@launch

                var dateRetourLocal: LocalDate? = null
                if (dateRetourString.isNotEmpty()) {
                    dateRetourLocal = validerDate(dateRetourString, "La date de retour ne peut pas être avant aujourd'hui.")
                    if (dateRetourLocal == null) return@launch

                    modèle.filtreVolRetour = creerFiltreVol(dateRetourLocal, aeroportVers, aeroportDe)
                    modèle.volRetourExiste = true
                }

                modèle.filtreVolAller = creerFiltreVol(dateDebut, aeroportDe, aeroportVers)

                val historique = creerHistorique(aeroportDe, aeroportVers, dateDebut, dateRetourLocal)
                enregistrerRecherche(historique)

                CoroutineScope(Dispatchers.Main).launch {
                    vue?.redirigerVersListeVols()
                }
            } catch (ex: IllegalArgumentException) {
                Log.d("Erreur", ex.message.toString())
                return@launch
            }
        }
    }



    override fun traiterActionRecherche() {
       vue?.obtenirInfoRecherche()
    }

    //convertion  d'entrée en un objet LocalDate sans avoir besoin d'ajouter manuellement " 00:00"
    private fun parseDate(dateString: String): LocalDate {
        return LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    }

    //Fun pour la creation de Object Historique
    private fun creerHistorique(
        aeroportDe: Aeroport,
        aeroportVers: Aeroport,
        dateDebutLocal: LocalDate,
        dateRetourLocal: LocalDate?
    ): Historique {
        return Historique(
            villeDe = aeroportDe.ville.nom,
            villeVers = aeroportVers.ville.nom,
            aeroportDe = aeroportDe.code,
            aeroportVers = aeroportVers.code,
            dateDepart = dateDebutLocal,
            dateRetour = dateRetourLocal
        )
    }

    private fun creerFiltreVol(dateDebut: LocalDate, aeroportDe: Aeroport, aeroportVers: Aeroport): FiltreRechercheVol {
        return FiltreRechercheVol(
            dateDébut = dateDebut.atStartOfDay(),
            codeAéroportDébut = aeroportDe.code,
            codeAéroportFin = aeroportVers.code
        )
    }


    private fun enregistrerRecherche(historique: Historique) {
        modèle.créerHistorique(historique)
        Log.d("RechercherVolPresent ", "Historique ajouter: $historique")
    }

    override fun traiterObtenirHistorique() {
        if(modèle.historiqueCliqué){
            modèle.historiqueCliqué = false

            val historique = modèle.obtenirHistoriqueCourrant()
            val historiqueOTD = HistoriqueListItemOTD(
                villeDe = historique.villeDe,
                villeVers = historique.villeVers,
                aeroportDe = historique.aeroportDe,
                aeroportVers = historique.aeroportVers,
                dateDepart = historique.dateDepart,
                dateRetour = historique.dateRetour
            )
            vue?.afficherHistorique(historiqueOTD)
        }
    }

    fun validerDate(dateString: String, errorMessage: String): LocalDate? {
        val parsedDate = parseDate(dateString)
        if (parsedDate.isBefore(LocalDate.now())) {
            CoroutineScope(Dispatchers.Main).launch {
                vue?.afficherToast(errorMessage)
            }
            return null
        }
        return parsedDate
    }
}