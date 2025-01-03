package com.nicholson.client_reservation_vol.donnée.http

import com.nicholson.client_reservation_vol.domaine.entité.Vol
import com.nicholson.client_reservation_vol.donnée.ISourceDeDonnéesVols
import com.nicholson.client_reservation_vol.donnée.exceptions.SourceDeDonnéesException
import com.nicholson.client_reservation_vol.donnée.http.décodeur.DécodeurJSONVol
import com.nicholson.client_reservation_vol.présentation.OTD.FiltreRechercheVol
import okhttp3.Request
import okio.IOException

class SourceDeDonnéesVolsHttp( val urlApi : String ) : ISourceDeDonnéesVols {

    override suspend fun obtenirListeVolParFiltre(filtre: FiltreRechercheVol): List<Vol> {
        val urlRequête = "$urlApi/vols?dateDebut=${filtre.dateDébut}" +
                "&&aeroportDebut=${filtre.codeAéroportDébut}" +
                "&&aeroportFin=${filtre.codeAéroportFin}"

        try {
            val client = ClientHttp.obtenirInstance()
            val requête = Request.Builder()
                .url( urlRequête )
                .get()
                .build()

            val réponse = client.newCall( requête ).execute()
            if ( réponse.code == 200 ) {
                val corpsDeRéponse = réponse.body?.string()
                réponse.body?.close()
                if ( corpsDeRéponse != null ){
                    return DécodeurJSONVol.décoderListeVols( corpsDeRéponse )
                }else{
                    throw SourceDeDonnéesException( "Corps de réponse vide" )
                }
            } else {
                throw SourceDeDonnéesException("Code : ${réponse.code}, url : $urlRequête")
            }
        } catch( ex : IOException) {
            throw SourceDeDonnéesException("Erreur inconnue : ${ex.message}")
        }
    }

    override suspend fun obtenirVolParId(id: Int): Vol {
        val urlRequête = "$urlApi/vols/$id"

        try {
            val client = ClientHttp.obtenirInstance()
            val requête = Request.Builder()
                .url( urlRequête )
                .get()
                .build()

            val réponse = client.newCall( requête ).execute()
            if ( réponse.code == 200 ) {
                val corpsDeRéponse = réponse.body?.string()
                réponse.body?.close()
                if ( corpsDeRéponse != null ){
                    return DécodeurJSONVol.décoderVol( corpsDeRéponse )
                }else{
                    throw SourceDeDonnéesException( "Corps de réponse vide" )
                }
            } else {
                throw SourceDeDonnéesException("Code : ${réponse.code}, url : $urlRequête")
            }
        } catch( ex : IOException ) {
            throw SourceDeDonnéesException("Erreur inconnue : ${ex.message}")
        }
    }
}