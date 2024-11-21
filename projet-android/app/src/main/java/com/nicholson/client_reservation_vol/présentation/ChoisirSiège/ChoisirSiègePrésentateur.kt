package com.nicholson.client_reservation_vol.présentation.ChoisirSiège

import com.nicholson.client_reservation_vol.domaine.entité.Vol
import com.nicholson.client_reservation_vol.présentation.ChoisirSiège.ContratVuePrésentateurChoisirSiège.*
import com.nicholson.client_reservation_vol.présentation.Modèle

class ChoisirSiègePrésentateur( private val vue : IChoisirSiègeVue) : IChoisirSiègePrésentateur {
    private val modèle : Modèle = Modèle.obtenirInstance()
    private lateinit var volCourrant : Vol
    private var numSiègeCourrant : String = ""
    private var idAndroidDernierSiègeCliqué = 0

    override fun traiterDémarage() {

        if(modèle.aller){
            volCourrant = modèle.getVolCourrant(modèle.indiceVolAller)
        }
        else{
            volCourrant = modèle.getVolCourrant(modèle.indiceVolRetour)
        }

        vue.miseEnPlace(
            nomVilleDépart = volCourrant.aeroportDebut.ville.nom,
            nomVilleArrivée = volCourrant.aeroportFin.ville.nom,
            urlPhoto = volCourrant.aeroportFin.ville.url_photo,
            modèle.classeChoisis
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
            if(modèle.aller){
                modèle.indiceVolCourrant = modèle.indiceVolAller
            }
            else{
                modèle.indiceVolCourrant = modèle.indiceVolRetour
            }
            modèle.créerRéservation( numSiègeCourrant )
            vue.redirigerVersMesRéservation()
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
                    && it.classe == modèle.classeChoisis
        }

        if ( siège != null ){
            vue.placerStatutSiègeOccupée( id )

        }else{
            vue.placerStatutSiègeDisponible( id )

        }
    }
}