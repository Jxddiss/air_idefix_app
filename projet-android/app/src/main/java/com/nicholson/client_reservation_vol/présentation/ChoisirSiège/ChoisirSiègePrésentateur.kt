package com.nicholson.client_reservation_vol.présentation.ChoisirSiège

import android.util.Log
import com.nicholson.client_reservation_vol.domaine.entité.Vol
import com.nicholson.client_reservation_vol.domaine.interacteur.VolService
import com.nicholson.client_reservation_vol.présentation.ChoisirSiège.ContratVuePrésentateurChoisirSiège.*
import com.nicholson.client_reservation_vol.présentation.Modèle

class ChoisirSiègePrésentateur( private val vue : IChoisirSiègeVue) : IChoisirSiègePrésentateur {
    private val modèle : Modèle = Modèle.obtenirInstance()
    private lateinit var volCourrant : Vol
    private var numSiègeCourrant : String = ""
    private var idAndroidDernierSiègeCliqué = 0

    override fun traiterDémarage() {
        var classe : String
        if(modèle.siegeVolAller){
            volCourrant = modèle.getVolCourrantAller(modèle.indiceVolAller)
            classe = modèle.réservationAller.sièges[0].classe
        }
        else{
            volCourrant = modèle.getVolCourrantRetour(modèle.indiceVolRetour)
            classe = modèle.réservationRetour.sièges[0].classe
        }

        vue.miseEnPlace(
            nomVilleDépart = volCourrant.aeroportDebut.ville.nom,
            nomVilleArrivée = volCourrant.aeroportFin.ville.nom,
            urlPhoto = volCourrant.aeroportFin.ville.url_photo,
            classe
        )
    }

    override fun traiterSiègeCliqué( id : Int, code : String ) {
        numSiègeCourrant = code


        if ( idAndroidDernierSiègeCliqué == 0 ){
            idAndroidDernierSiègeCliqué = id
            vue.miseÀjourSiègeCliquéVersSélectionnée( id )
        }else{
            vue.miseÀjourSiègeCliquéVersDéSélectionné( idAndroidDernierSiègeCliqué )
            idAndroidDernierSiègeCliqué = id
            vue.miseÀjourSiègeCliquéVersSélectionnée( id )
        }
    }

    override fun traiterDialogConfirmer() {
        if ( numSiègeCourrant.isNotEmpty() ){
            if(modèle.siegeVolAller){

                Log.d("test siegeVolAller",modèle.siegeVolAller.toString())
                modèle.indiceVolCourrant = modèle.indiceVolAller
                modèle.réservationAller.sièges[0].numéro = numSiègeCourrant
                modèle.siegeVolAller = false
                vue.redirigerVersChoisirSiegeRetour()
            }
            else{
                Log.d("test siegeVolAller",modèle.siegeVolAller.toString())
                modèle.indiceVolCourrant = modèle.indiceVolRetour
                modèle.réservationRetour.sièges[0].numéro = numSiègeCourrant
                modèle.ajouterReservation(modèle.réservationAller)
                modèle.ajouterReservation(modèle.réservationRetour)
                vue.redirigerVersMesRéservation()
            }

        }else{
            vue.afficherErreur( "Veuillez choisir un siège" )
        }
    }

    override fun traiterConfirmerRéservation() {
        vue.afficherDialogConfirmer()
    }

    override fun vérifierStatutSiège( id: Int, code: String ) {
        val siège = volCourrant.sièges.firstOrNull {
            it.numéro == code
                    && it.classe == modèle.réservationAller.sièges[0].classe
        }

        if ( siège != null ){
            vue.placerStatutSiègeOccupée( id )

        }else{
            vue.placerStatutSiègeDisponible( id )

        }
    }
}