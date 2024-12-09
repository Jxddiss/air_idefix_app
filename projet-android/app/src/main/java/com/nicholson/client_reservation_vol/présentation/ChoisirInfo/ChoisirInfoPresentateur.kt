package com.nicholson.client_reservation_vol.présentation.ChoisirInfo

import com.nicholson.client_reservation_vol.domaine.entité.Client
import com.nicholson.client_reservation_vol.donnée.exceptions.SourceDeDonnéesException
import com.nicholson.client_reservation_vol.présentation.Modèle
import com.nicholson.client_reservation_vol.présentation.OTD.ClientModifiableOTD
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

    override fun traiterObtenirInfo(clientOTD: ClientModifiableOTD) {
        if( clientOTD.nom.isEmpty()
            || clientOTD.prénom.isEmpty()
            || clientOTD.numéroPasseport.isEmpty() ){

            vue.afficherMessageErreur("Veuillez remplir tous les champs.")
            return
        }



        job = CoroutineScope( iocontext ).launch {
            try {
                modele.modifierClient( convertirClientOTDAClient( clientOTD ) )
                modele.réservationAller.client = modele.obtenirClientCourrant()
                modele.réservationRetour.client = modele.obtenirClientCourrant()
                CoroutineScope( Dispatchers.Main ).launch {
                    vue.redirigerAChoisirSiege()
                }
            } catch ( ex : SourceDeDonnéesException ) {
                modele.messageErreurRéseauExistant = true
                CoroutineScope( Dispatchers.Main ).launch {
                    vue.redirigerBienvenueErreur()
                }
            }
        }
    }

    override fun traiterDemandeRedirectionChoisirSiege() {
            vue.obtenirInfoClient()
    }

    private fun convertirClientOTDAClient(clientOTD: ClientModifiableOTD) : Client {
        return Client(
            modele.client?.id ?: 0,
            clientOTD.nom,
            clientOTD.prénom,
            clientOTD.adresse,
            clientOTD.numéroPasseport,
            modele.client?.email ?: "",
            clientOTD.téléphone
        )
    }

}
