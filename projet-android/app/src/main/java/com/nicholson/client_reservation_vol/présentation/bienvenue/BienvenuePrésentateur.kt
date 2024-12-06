package com.nicholson.client_reservation_vol.présentation.bienvenue
import com.nicholson.client_reservation_vol.présentation.Modèle
import com.nicholson.client_reservation_vol.présentation.bienvenue.ContratVuePrésentateurBienvenue.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class BienvenuePrésentateur( var vue : IBienvenueVue = BienvenueVue() ) : IBienvenuePrésentateur {
    private val modèle: Modèle = Modèle.obtenirInstance()

    override fun traiterDemandeRedirectionListeReservations() {
        vue.redirigerAListeReservation()
    }

    override fun traiterDemandeRedirectionRechercherUnVol() {
        modèle.aller = true
        modèle.volRetourExiste = false
        vue.redirigerARechercherUnVol()
    }

    override fun traiterDémarage() {
        if ( modèle.messageErreurRéseauExistant ) {
            modèle.messageErreurRéseauExistant = false
            vue.afficherMessageErreur()
        }

        if( modèle.estConnecté ) {
            vue.montrerDéconnexion()
        }
    }

    override fun traiterDéconnexion() {
        vue.seDéconnecter(
            réussite = {
                vue.cacherDéconnexion()
                modèle.seDeconecté()
            },
            échec = {
                vue.afficherMessageErreur()
            }
        )
    }
}