package com.nicholson.client_reservation_vol.présentation.ChoisirInfo

import com.nicholson.client_reservation_vol.domaine.entité.Client
import com.nicholson.client_reservation_vol.domaine.entité.Vol
import com.nicholson.client_reservation_vol.présentation.Modèle
import com.nicholson.client_reservation_vol.présentation.OTD.ClientOTD

class ChoisirInfoPresentateur(var vue: ContratVueChoisirInfo.IChoisirInfoVue = ChoisirInfoVue()) :
    ContratVueChoisirInfo.IChoisirInfoPrésentateur {
        private val modele = Modèle.obtenirInstance()
    override fun traiterDémarage() {

        val vol = modele.getVolCourrant(modele.indiceVolAller)

        vue.miseEnPlace( vol.aeroportDebut.ville.nom,
            vol.aeroportFin.ville.nom, vol.aeroportFin.ville.url_photo, !modele.aller)
    }

    override fun traiterObtenirInfo(clientOTD: ClientOTD) {
        if( clientOTD.nom.isEmpty()
            || clientOTD.prénom.isEmpty()
            || clientOTD.numéroPasseport.isEmpty() ){

            vue.afficherMessageErreur("Veuillez remplir tous les champs.")
            return
        }
        modele.ajouterClient(convertirClientOTDAClient(clientOTD))
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
