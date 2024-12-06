package com.nicholson.client_reservation_vol.domaine.interacteur

import com.nicholson.client_reservation_vol.donnée.ISourceDeDonneeAuth
import com.nicholson.client_reservation_vol.donnée.fictive.SourceDeDonnéesAuthFictive
import com.nicholson.client_reservation_vol.donnée.http.ClientHttp

class Authentification {
    companion object {
        var sourceDeDonnées : ISourceDeDonneeAuth = SourceDeDonnéesAuthFictive()
        var clientHttpPrésent = false

        suspend fun effectuerLogin() {
            val token = sourceDeDonnées.obtenirToken()
            if ( clientHttpPrésent ) {
                ClientHttp.ajouterToken( token )
            }
        }

        suspend fun seDéconnecter() {
            sourceDeDonnées.seDeconnecter()
            if ( clientHttpPrésent ) {
                ClientHttp.retirerIntercepteurs()
            }
        }
    }
}