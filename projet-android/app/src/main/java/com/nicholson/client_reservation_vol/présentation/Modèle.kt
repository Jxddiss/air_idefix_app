package com.nicholson.client_reservation_vol.présentation

import com.nicholson.client_reservation_vol.domaine.entité.Client
import com.nicholson.client_reservation_vol.domaine.entité.Aeroport
import com.nicholson.client_reservation_vol.domaine.entité.Historique
import com.nicholson.client_reservation_vol.domaine.entité.Réservation
import com.nicholson.client_reservation_vol.domaine.entité.Siège
import com.nicholson.client_reservation_vol.domaine.entité.Vol
import com.nicholson.client_reservation_vol.domaine.interacteur.AeroportService
import com.nicholson.client_reservation_vol.domaine.interacteur.ClientService
import com.nicholson.client_reservation_vol.domaine.interacteur.RéservationService
import com.nicholson.client_reservation_vol.domaine.interacteur.HistoriqueService
import com.nicholson.client_reservation_vol.domaine.interacteur.VolService
import com.nicholson.client_reservation_vol.présentation.OTD.FiltreRechercheVol
import java.time.LocalDateTime

class Modèle private constructor( private val volService : VolService = VolService(),
                                  private val clientService: ClientService = ClientService(),
                                  private val réservationService: RéservationService = RéservationService(),
                                  private val historiqueService: HistoriqueService = HistoriqueService(),
                                  private val aeroportService: AeroportService = AeroportService() ) {

    companion object {
        @Volatile
        private var instance: Modèle? = null

        fun obtenirInstance() =
            instance ?: synchronized(this) {
                instance ?: Modèle().also { instance = it }
            }
    }

    var indiceVolCourrant: Int = 0
    var indiceVolRetour: Int = 0
    var indiceVolAller: Int = 0
    var indiceRéservationCourrante: Int = 0
    var indiceClientCourrant: Int = 0
    var pageCourrante : String? = null


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
        clients = listOf(),
        sièges = mutableListOf(
            Siège(
                1,
                numéro = "",
                classe = "",
                statut = "",
                idRéservation = 0,
                idVol = 1
            )
        )
    )
    var réservationRetour = Réservation(
        id = 0,
        numéroRéservation = "",
        idVol = 1,
        clients = listOf(),
        sièges = mutableListOf(
            Siège(
                1,
                numéro = "",
                classe = "",
                statut = "",
                idRéservation = 0,
                idVol = 1
            )
        )
    )


    var volRetourExiste: Boolean = false
    var aller: Boolean = true
    var siegeVolAller : Boolean = true

    var listeVolAller: List<Vol> = listOf()
    var listeVolRetour: List<Vol> = listOf()
    var listeRéservation: MutableList<Réservation> = mutableListOf()
        get() {
            if (field.isEmpty()) {
                field = réservationService.obtenirListeRéservation()
            }
            return field
        }

    var listeClient: MutableList<Client> = mutableListOf()
        get() {
            if (field.isEmpty()) {
                field = clientService.obtenirListeClient()
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

    fun getVolCourrantAller(indice: Int): Vol {
        return volService.obtenirVolParId(listeVolAller[indice].id)
    }

    fun getVolCourrantRetour(indice: Int): Vol {
        return volService.obtenirVolParId(listeVolRetour[indice].id)
    }

    fun obtenirListeVolAllerParFiltre(): List<Vol> {
        listeVolAller = volService.obtenirListeVolParFiltre(filtreVolAller)
        return listeVolAller
    }

    fun obtenirListeVolRetourParFiltre(): List<Vol> {
        listeVolRetour = volService.obtenirListeVolParFiltre(filtreVolRetour)
        return listeVolRetour
    }

    fun obtenirVolParId(id: Int): Vol {
        return volService.obtenirVolParId(id)
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

    fun obtenirReservationParId(id: Int): Réservation {
        return réservationService.obtenirRéservationParid(id)
    }

    fun obtenirReservationCourrante(): Réservation {
        return réservationService.obtenirRéservationParid(listeRéservation[indiceRéservationCourrante].id)
    }

    fun obtenirClientCourrant(): Client {
        return listeClient[indiceClientCourrant]
    }

    fun ajouterClient(client: Client) {
        listeClient = mutableListOf(client)
        clientService.ajouterClient(client)
    }

    fun créerRéservationAller(classeChoisis : String) : Réservation {
        val réservation = Réservation(
            id = 0,
            numéroRéservation = "",
            idVol = getVolCourrantAller(indiceVolAller).id,
            clients = listeClient,
            sièges = mutableListOf(
                Siège(
                    1,
                    numéro = "",
                    classe = classeChoisis,
                    statut = "Occupée",
                    idRéservation = 0,
                    idVol = getVolCourrantAller(indiceVolAller).id
                )
            )
        )
        return réservation
    }
    fun créerRéservationRetour(classeChoisis : String) : Réservation {
        val réservation = Réservation(
            id = 0,
            numéroRéservation = "",
            idVol = getVolCourrantRetour(indiceVolRetour).id,
            clients = listeClient,
            sièges = mutableListOf(
                Siège(
                    1,
                    numéro = "",
                    classe = classeChoisis,
                    statut = "Occupée",
                    idRéservation = 0,
                    idVol = getVolCourrantRetour(indiceVolRetour).id
                )
            )
        )
        return réservation
    }


    fun ajouterReservation(réservation : Réservation) {
        réservationService.ajouterRéservation(réservation)
    }

    fun obtenirListeAéroports(): List<Aeroport> {
        return aeroportService.obtenirListeAeroport()
    }

    fun créerHistorique(historique: Historique) {
        historiqueService.ajouterHistorique(historique)
    }

}