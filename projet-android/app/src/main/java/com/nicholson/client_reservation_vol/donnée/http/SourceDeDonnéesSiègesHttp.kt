package com.nicholson.client_reservation_vol.donnée.http

import com.nicholson.client_reservation_vol.domaine.entité.Siège
import com.nicholson.client_reservation_vol.donnée.ISourceSiège
import com.nicholson.client_reservation_vol.donnée.exceptions.SourceDeDonnéesException
import com.nicholson.client_reservation_vol.donnée.http.décodeur.DécodeurJSONSiège
import okhttp3.Request
import okio.IOException

class SourceDeDonnéesSiègesHttp( val urlApi : String ) : ISourceSiège {

    override suspend fun obtenirSiègesParVolID(idVol: Int): List<Siège> {
        val urlRequête = "$urlApi/vols/$idVol/sièges"

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
                if( corpsDeRéponse != null ){
                    return DécodeurJSONSiège.décoderListSiège( corpsDeRéponse )
                } else {
                    throw SourceDeDonnéesException( "Corps de réponse vide" )
                }
            } else {
                throw SourceDeDonnéesException("Code : ${réponse.code}")
            }
        } catch( ex : IOException ) {
            throw SourceDeDonnéesException("Erreur inconnue : ${ex.message}")
        }
    }

}