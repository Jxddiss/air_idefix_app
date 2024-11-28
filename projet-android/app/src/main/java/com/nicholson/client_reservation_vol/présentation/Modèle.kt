package com.nicholson.client_reservation_vol.présentation

import android.content.Context
import android.util.Log
import com.nicholson.client_reservation_vol.domaine.entité.Client
import com.nicholson.client_reservation_vol.domaine.entité.Aeroport
import com.nicholson.client_reservation_vol.domaine.entité.Historique
import com.nicholson.client_reservation_vol.domaine.entité.Réservation
import com.nicholson.client_reservation_vol.domaine.entité.Siège
import com.nicholson.client_reservation_vol.domaine.entité.Ville
import com.nicholson.client_reservation_vol.domaine.entité.Vol
import com.nicholson.client_reservation_vol.domaine.interacteur.AeroportService
import com.nicholson.client_reservation_vol.domaine.interacteur.ClientService
import com.nicholson.client_reservation_vol.domaine.interacteur.RéservationService
import com.nicholson.client_reservation_vol.domaine.interacteur.HistoriqueService
import com.nicholson.client_reservation_vol.domaine.interacteur.VolService
import com.nicholson.client_reservation_vol.donnée.DataBase.SourceDeDonnéesLocalImpl
import com.nicholson.client_reservation_vol.donnée.SourceDeDonnées
import com.nicholson.client_reservation_vol.présentation.OTD.FiltreRechercheVol
import java.time.LocalDate
import java.time.LocalDateTime


class Modèle private constructor( private val volService : VolService = VolService(),
                                  private val clientService: ClientService = ClientService(),
                                  private val réservationService: RéservationService = RéservationService(),
                                  private var historiqueService: HistoriqueService? = null,
                                  private val aeroportService: AeroportService = AeroportService()) {

    private var sourceDeDonnées: SourceDeDonnées? = null

    companion object {
        @Volatile
        private var instance : Modèle? = null

        fun obtenirInstance() =
            instance ?: synchronized(this) {
                instance ?: Modèle().also { instance = it }
            }
    }

    // Setter pour sourceDeDonnées
    fun initialiserSourceDeDonnées(context: Context) {
        Log.d("Modèle", "Initializing SourceDeDonnées")
        if (sourceDeDonnées == null) {
            sourceDeDonnées = SourceDeDonnéesLocalImpl(context)
            historiqueService = HistoriqueService(sourceDeDonnées!!)
        }
    }
    //Getter pour sourceDeDonnées
    fun getSourceDeDonnées(): SourceDeDonnées {
        return sourceDeDonnées
            ?: throw IllegalStateException("SourceDeDonnées not initialized!")
    }

    var indiceVolCourrant : Int = 0
    var indiceRéservationCourrante : Int = 0
    var indiceClientCourrant : Int = 0
    var filtreVolCourrant = FiltreRechercheVol(
        LocalDateTime.now(), "YUL", "JFK"
    )
    var classeChoisis = "Économique"



    var listeVol: List<Vol> = listOf()
    var listeRéservation : MutableList<Réservation> = mutableListOf()
        get(){
            if (field.isEmpty() ){
                field = réservationService.obtenirListeRéservation()
            }
            return field
        }

    var listeClient : MutableList<Client> = mutableListOf()
        get(){
            if(field.isEmpty()){
                field = clientService.obtenirListeClient()
            }
            return field
        }

    var listeHistorique: List<Historique> = listOf()
        get() {
            if (field.isEmpty()) {
                field = historiqueService?.obtenirListeHistorique() ?: listOf()
            }
            return field
        }

    fun getVolCourrant(): Vol =
        volService.obtenirVolParId(listeVol[indiceVolCourrant].id)

    fun obtenirListeVolParFiltre() : List<Vol> {
        listeVol = volService.obtenirListeVolParFiltre( filtreVolCourrant )
        return listeVol
    }

    fun obtenirVolParId( id : Int ) : Vol {
        return volService.obtenirVolParId( id )
    }

    fun avancerVolCourrant() {
        if ( indiceVolCourrant < listeVol.size - 1 ){
            indiceVolCourrant ++
        } else {
            indiceVolCourrant = 0
        }
    }

    fun reculerVolCourrant() {
        if( indiceVolCourrant > 0 ){
            indiceVolCourrant --
        }else{
            indiceVolCourrant = listeVol.size - 1
        }
    }

    fun getVolPrécédent() : Vol {
        if ( indiceVolCourrant > 0 ){
            return listeVol[indiceVolCourrant - 1]
        } else {
            return listeVol[listeVol.size - 1]
        }
    }

    fun getVolSuivant() : Vol {
        if ( indiceVolCourrant < listeVol.size - 1 ){
            return listeVol[indiceVolCourrant + 1]
        } else {
            return listeVol[0]
        }
    }

    fun obtenirReservationParId( id : Int ): Réservation {
        return réservationService.obtenirRéservationParid( id )
    }

    fun obtenirReservationCourrante(): Réservation {
        return réservationService.obtenirRéservationParid(listeRéservation[indiceRéservationCourrante].id)
    }

    fun obtenirClientCourrant(): Client{
        return listeClient[indiceClientCourrant]
    }

    fun ajouterClient(client : Client) {
        listeClient = mutableListOf(client)
        clientService.ajouterClient(client)
    }

    fun créerRéservation( numéroSiege : String ) {
        val réservation = Réservation(
            id = 0,
            numéroRéservation = "",
            idVol = getVolCourrant().id,
            clients = listeClient,
            sièges = mutableListOf(
                Siège(
                    1,
                    numéro = numéroSiege,
                    classe = classeChoisis,
                    statut = "Occupée",
                    idRéservation = 0,
                    idVol = getVolCourrant().id
                )
            )
        )

        réservationService.ajouterRéservation( réservation )
    }



    fun obtenirListeAéroports(): List<Aeroport> {
        return aeroportService.obtenirListeAeroport()
    }

    fun créerHistorique( historique: Historique ){
        historiqueService?.ajouterHistorique( historique )
    }

}