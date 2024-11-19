package com.nicholson.client_reservation_vol.présentation.ChoisirSiège

import com.nicholson.client_reservation_vol.domaine.entité.Réservation
import com.nicholson.client_reservation_vol.domaine.entité.Siège
import com.nicholson.client_reservation_vol.présentation.ChoisirSiège.ContratVuePrésentateurChoisirSiège.*
import com.nicholson.client_reservation_vol.présentation.Modèle

class ChoisirSiègePrésentateur( private val vue : IChoisirSiègeVue) : IChoisirSiègePrésentateur {
    private val modèle : Modèle = Modèle.obtenirInstance()
    private val volCourrant = modèle.getVolCourrant()
    private var siègeCourrant : Siège? = null

    override fun traiterDémarage() {
        vue.miseEnPlace(
            nomVilleDépart = volCourrant.aeroportDebut.ville.nom,
            nomVilleArrivée = volCourrant.aeroportFin.ville.nom,
            urlPhoto = volCourrant.aeroportFin.ville.url_photo
        )
    }

    override fun traiterSiègeCliqué( id : Int, code : String ) {
        if ( siègeCourrant == null ){
            siègeCourrant = Siège(0, code, "Économique", "Occupée", 0, 0)
            vue.miseÀjourSiègeCliquéVersSélectionnée( id )
        }else{
            siègeCourrant = null
            vue.miseÀjourSiègeCliquéVersDéSélectionné( id )
        }
    }

    override fun traiterConfirmerRéservation() {
        TODO("Not yet implemented")
    }

    override fun vérifierStatutSiège( id: Int, code: String ) {
        val siège = volCourrant.sièges.firstOrNull { it.numéro == code }
        if (siège != null){
            vue.placerStatutSiègeOccupée( id )
        }else{
            vue.placerStatutSiègeDisponible( id )
        }
    }
}