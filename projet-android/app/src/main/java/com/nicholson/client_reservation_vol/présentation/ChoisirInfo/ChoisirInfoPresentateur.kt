package com.nicholson.client_reservation_vol.présentation.ChoisirInfo

import com.nicholson.client_reservation_vol.domaine.entité.Client
import com.nicholson.client_reservation_vol.donnée.exceptions.SourceDeDonnéesException
import com.nicholson.client_reservation_vol.présentation.Modèle
import com.nicholson.client_reservation_vol.présentation.OTD.ClientOTD
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ChoisirInfoPresentateur(
    var vue: ContratVueChoisirInfo.IChoisirInfoVue = ChoisirInfoVue(),
    private val iocontext: CoroutineContext = Dispatchers.IO
) :
    ContratVueChoisirInfo.IChoisirInfoPrésentateur {

    private val modele = Modèle.obtenirInstance()
    private var job : Job? = null

    override fun traiterDémarage() {
        job = CoroutineScope( iocontext ).launch {
            try {
                val vol = modele.getVolCourrantAller(modele.indiceVolAller)
                val clientCourrant = modele.obtenirClientCourrant()
                val clientOTD = ClientOTD(
                    nom = clientCourrant.nom,
                    prénom = clientCourrant.prénom,
                    adresse = clientCourrant.adresse,
                    numéroPasseport = clientCourrant.numéroPasseport,
                    email = clientCourrant.email ?: "",
                    téléphone = clientCourrant.numéroTéléphone ?: ""
                )

                CoroutineScope( Dispatchers.Main ).launch {
                    vue.miseEnPlace( vol.aeroportDebut.ville.nom,
                        vol.aeroportFin.ville.nom,
                        vol.aeroportFin.ville.url_photo,
                        clientOTD
                    )
                }
            } catch ( ex : SourceDeDonnéesException ) {
                modele.messageErreurRéseauExistant = true
                CoroutineScope( Dispatchers.Main ).launch {
                    vue.redirigerBienvenueErreur()
                }
            }
        }
    }

    override fun traiterObtenirInfo(clientOTD: ClientOTD) {
        if( clientOTD.nom.isEmpty()
            || clientOTD.prénom.isEmpty()
            || clientOTD.numéroPasseport.isEmpty() ){

            vue.afficherMessageErreur("Veuillez remplir tous les champs.")
            return
        }
        modele.ajouterClient(convertirClientOTDAClient(clientOTD))
        modele.réservationAller.clients = modele.listeClient
        modele.réservationRetour.clients = modele.listeClient
        vue.redirigerAChoisirSiege()
    }

    override fun traiterDemandeRedirectionChoisirSiege() {
            vue.obtenirInfoClient()
    }

    private fun convertirClientOTDAClient(clientOTD: ClientOTD) : Client{
        return Client(
            modele.listeClient.size + 1,
            clientOTD.nom,
            clientOTD.prénom,
            clientOTD.adresse,
            clientOTD.numéroPasseport,
            clientOTD.email,
            clientOTD.téléphone
        )
    }

}
