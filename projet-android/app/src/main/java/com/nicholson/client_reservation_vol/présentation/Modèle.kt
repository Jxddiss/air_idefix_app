package com.nicholson.client_reservation_vol.présentation

import android.util.Log
import com.nicholson.client_reservation_vol.domaine.entité.Client
import com.nicholson.client_reservation_vol.domaine.entité.Aeroport
import com.nicholson.client_reservation_vol.domaine.entité.Historique
import com.nicholson.client_reservation_vol.domaine.entité.Réservation
import com.nicholson.client_reservation_vol.domaine.entité.Siège
import com.nicholson.client_reservation_vol.domaine.entité.Vol
import com.nicholson.client_reservation_vol.domaine.interacteur.ClientService
import com.nicholson.client_reservation_vol.domaine.interacteur.HistoriqueService
import com.nicholson.client_reservation_vol.domaine.interacteur.ManipulerReservation
import com.nicholson.client_reservation_vol.domaine.interacteur.ModifierClient
import com.nicholson.client_reservation_vol.domaine.interacteur.ObtenirAéroport
import com.nicholson.client_reservation_vol.domaine.interacteur.ObtenirClient
import com.nicholson.client_reservation_vol.domaine.interacteur.ObtenirReservation
import com.nicholson.client_reservation_vol.domaine.interacteur.ObtenirSièges
import com.nicholson.client_reservation_vol.domaine.interacteur.RechercherVol
import com.nicholson.client_reservation_vol.donnée.ISourceDeDonnéesHistorique
import com.nicholson.client_reservation_vol.donnée.http.ClientHttp
import com.nicholson.client_reservation_vol.présentation.OTD.FiltreRechercheVol
import java.time.LocalDateTime


class Modèle private constructor() {

    private val clientService: ClientService = ClientService()

    private var historiqueService: HistoriqueService? = null

    companion object {
        @Volatile
        private var instance : Modèle? = null

        fun obtenirInstance() =
            instance ?: synchronized(this) {
                instance ?: Modèle().also { instance = it }
            }
    }

    var indiceVolCourrant: Int = 0
    var indiceVolRetour: Int = 0
    var indiceVolAller: Int = 0
    var indiceHistoriqueCourrant : Int = 0
    var historiqueCliqué = false
    var indiceRéservationCourrante: Int = 0
    var pageCourrante : String? = null
    var messageErreurRéseauExistant = false
    var client : Client? = null
    var estConnecté = false

    // Setter pour sourceDeDonnées
    fun initialiserSourceDeDonnées( sourceHistorique : ISourceDeDonnéesHistorique) {
        Log.d("Modèle", "Initializing SourceDeDonnées")
        historiqueService = HistoriqueService( sourceHistorique )
    }

    var filtreVolAller = FiltreRechercheVol(
        LocalDateTime.now(), "YUL", "JFK"
    )
    var filtreVolRetour: FiltreRechercheVol = FiltreRechercheVol(
        LocalDateTime.now(), "YUL", "JFK"
    )

    var réservationAller = Réservation(
        id = 0,
        numéroRéservation = "",
        idVol = 1,
        client = null,
        siège = null
    )
    var réservationRetour = Réservation(
        id = 0,
        numéroRéservation = "",
        idVol = 1,
        client = null,
        siège = null
    )

    var volRetourExiste: Boolean = false
    var aller: Boolean = true
    var siegeVolAller : Boolean = true

    var listeVolAller: List<Vol> = listOf()
    var listeVolRetour: List<Vol> = listOf()
    var listeRéservation: List<Réservation> = listOf()

    var listeClient: MutableList<Client> = mutableListOf()
        get() {
            if (field.isEmpty()) {
                field = clientService.obtenirListeClient()
            }
            return field
        }

    var listeHistorique: List<Historique> = listOf()
        get() {
            field = historiqueService?.obtenirListeHistorique() ?: listOf()
            return field
        }

    var listeAéroports : List<Aeroport> = listOf()

    suspend fun getVolCourrantAller(indice: Int): Vol {
        return RechercherVol.obtenirDétailVol(listeVolAller[indice].id)
    }

    suspend fun getVolCourrantRetour(indice: Int): Vol {
        return RechercherVol.obtenirDétailVol(listeVolRetour[indice].id)
    }

    suspend fun obtenirListeVolAllerParFiltre(): List<Vol> {
        listeVolAller = RechercherVol.rechercherVolParFiltre(filtreVolAller)
        return listeVolAller
    }

    suspend fun obtenirListeVolRetourParFiltre(): List<Vol> {
        listeVolRetour = RechercherVol.rechercherVolParFiltre(filtreVolRetour)
        return listeVolRetour
    }

    suspend fun obtenirVolParId(id: Int): Vol {
        return RechercherVol.obtenirDétailVol(id)
    }

    fun avancerVolCourrant() {
        val listeVol : List<Vol>

        if(aller){

            listeVol = listeVolAller

            if (indiceVolAller < listeVol.size - 1) {
                indiceVolAller++
            } else {
                indiceVolAller = 0
            }
        }
        else{

            listeVol = listeVolRetour

            if (indiceVolRetour < listeVol.size - 1) {
                indiceVolRetour++
            } else {
                indiceVolRetour = 0
            }
        }

    }

    fun reculerVolCourrant() {
        val listeVol : List<Vol>

        if(aller){

            listeVol = listeVolAller

            if (indiceVolAller > 0) {
                indiceVolAller--
            } else {
                indiceVolAller = listeVol.size - 1
            }
        }
        else{

            listeVol = listeVolRetour

            if (indiceVolRetour > 0) {
                indiceVolRetour--
            } else {
                indiceVolRetour = listeVol.size - 1
            }
        }
    }

    fun getVolPrécédent(): Vol {
        val listeVol = if (aller) listeVolAller else listeVolRetour

        if(aller){
            if (indiceVolAller > 0) {
                return listeVol[indiceVolAller - 1]
            } else {
                return listeVol[listeVol.size - 1]
            }
        }
        else{
            if (indiceVolRetour > 0) {
                return listeVol[indiceVolRetour - 1]
            } else {
                return listeVol[listeVol.size - 1]
            }
        }
    }

    fun getVolSuivant(): Vol {
        val listeVol : List<Vol>

        if(aller){
            listeVol = listeVolAller
            if (indiceVolAller < listeVol.size - 1){
                return listeVol[indiceVolAller + 1]
            } else{
                return listeVol[0]
            }
        }
        else {
            listeVol = listeVolRetour
            if (indiceVolRetour < listeVol.size - 1){
                return listeVol[indiceVolRetour + 1]
            } else{
                return listeVol[0]
            }
        }
    }

    suspend fun obtenirReservationCourrante(): Réservation {
        return ObtenirReservation.obtenirDétailsRéservation(listeRéservation[indiceRéservationCourrante].id)
    }

    suspend fun obtenirListReservation():List<Réservation>{
        listeRéservation = ObtenirReservation.obtenirListeRéservation()
        return listeRéservation
    }

    suspend fun obtenirClientCourrant(): Client {
        if ( client == null ){
            client = ObtenirClient.obtenirClientCourrant()
        }
        return client as Client
    }

    suspend fun modifierClient( clientModifié : Client ) {
        if( client != clientModifié ){
            ModifierClient.modifierClient( clientModifié )
            client = clientModifié
        }
    }

    suspend fun créerRéservationAller(classeChoisis : String) : Réservation {
        val réservation = Réservation(
            id = 0,
            numéroRéservation = "",
            idVol = getVolCourrantAller(indiceVolAller).id,
            client = client,
            siège =
                Siège(
                    1,
                    numéro = "",
                    classe = classeChoisis,
                    statut = "Occupée",
                ),
            classe = classeChoisis
        )
        return réservation
    }

    suspend fun créerRéservationRetour(classeChoisis : String) : Réservation {
        val réservation = Réservation(
            id = 0,
            numéroRéservation = "",
            idVol = getVolCourrantRetour(indiceVolRetour).id,
            client = client,
            siège =
            Siège(
                1,
                numéro = "",
                classe = classeChoisis,
                statut = "Occupée",
            ),
            classe = classeChoisis
        )
        return réservation
    }

    suspend fun ajouterReservation(réservation : Réservation) {
        ManipulerReservation.ajouterRéservation(réservation)
    }

    suspend fun obtenirListeAéroports(): List<Aeroport> {
        if ( listeAéroports.isEmpty() ){
            listeAéroports = ObtenirAéroport.obtenirListeAeroport()
        }
        return listeAéroports
    }

    suspend fun obtenirSiègesVolCourrant( idVol : Int ) : List<Siège> {
        return ObtenirSièges.obtenirSiègesParIdVol( idVol )
    }

    fun créerHistorique( historique: Historique ) {
        historiqueService?.ajouterHistorique( historique )
    }

    fun obtenirHistoriqueCourrant() : Historique{
        return listeHistorique[indiceHistoriqueCourrant]
    }

    fun effectuerLogin( token : String ) {
        ClientHttp.ajouterToken( token )
        estConnecté = true
    }

     fun seDeconecté() {
        ClientHttp.retirerIntercepteurs()
        estConnecté = false
    }

    fun supprimerHistorique(historique: Historique) {
        historiqueService?.supprimerHistorique(historique)
        listeHistorique = listeHistorique.filter { it != historique }
    }

}