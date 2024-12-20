package com.nicholson.client_reservation_vol.présentation.ChoisirSiège

import android.util.Log
import com.nicholson.client_reservation_vol.domaine.entité.Siège
import com.nicholson.client_reservation_vol.domaine.entité.Vol
import com.nicholson.client_reservation_vol.donnée.exceptions.SourceDeDonnéesException
import com.nicholson.client_reservation_vol.présentation.ChoisirSiège.ContratVuePrésentateurChoisirSiège.*
import com.nicholson.client_reservation_vol.présentation.Modèle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ChoisirSiègePrésentateur(
    private val vue : IChoisirSiègeVue,
    iocontext : CoroutineContext = Dispatchers.IO
    ) : IChoisirSiègePrésentateur {

    private val modèle : Modèle = Modèle.obtenirInstance()
    private var volCourrant : Vol? = null
    private var sièges : List<Siège> = listOf()
    private var numSiègeCourrant : String = ""
    private var idAndroidDernierSiègeCliqué = 0
    private var job : Job? = null
    private var iocontext : CoroutineContext = iocontext

    override fun traiterDémarage() {
        job = CoroutineScope( iocontext ).launch {
            var classe = ""
            if(modèle.siegeVolAller){

                try {
                    volCourrant = modèle.getVolCourrantAller(modèle.indiceVolAller)
                } catch ( ex : SourceDeDonnéesException ) {
                    modèle.messageErreurRéseauExistant = true
                    CoroutineScope( Dispatchers.Main ).launch {
                        vue.redirigerÀBienvenueErreur()
                    }
                }

                val réservationAller = modèle.réservationAller
                if(réservationAller.siège != null){
                    classe = réservationAller.siège.classe
                }

            }
            else{
                volCourrant = modèle.getVolCourrantRetour(modèle.indiceVolRetour)
                val réservationRetour = modèle.réservationRetour
                if(réservationRetour.siège != null) {
                    classe = réservationRetour.siège.classe
                }
            }

            if ( volCourrant != null ) {
                try {
                    sièges = modèle.obtenirSiègesVolCourrant(volCourrant!!.id)
                } catch ( ex : SourceDeDonnéesException ) {
                    modèle.messageErreurRéseauExistant = true
                    CoroutineScope( Dispatchers.Main ).launch {
                        vue.redirigerÀBienvenueErreur()
                    }
                }

                CoroutineScope(Dispatchers.Main).launch {
                    vue.miseEnPlace(
                        nomVilleDépart = volCourrant!!.aeroportDebut.ville.nom,
                        nomVilleArrivée = volCourrant!!.aeroportFin.ville.nom,
                        urlPhoto = volCourrant!!.aeroportFin.ville.url_photo,
                        classeChoisis = classe
                    )
                    vue.miseEnPlaceSièges()
                }
            }
        }
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
        vue.désactiverBtnConfirmer()
        job = CoroutineScope( iocontext ).launch {
            if(modèle.siegeVolAller){
                modèle.indiceVolCourrant = modèle.indiceVolAller
                modèle.réservationAller.siège?.numéro = numSiègeCourrant
                modèle.siegeVolAller = false
                if(modèle.listeVolRetour.isEmpty()){
                    try {
                        modèle.ajouterReservation(modèle.réservationAller)
                        CoroutineScope( Dispatchers.Main ).launch {
                            vue.redirigerVersMesRéservation()
                        }
                    } catch ( ex : SourceDeDonnéesException ) {
                        CoroutineScope( Dispatchers.Main ).launch {
                            modèle.messageErreurRéseauExistant = true
                            vue.redirigerÀBienvenueErreur()
                        }
                    }
                }
                else {
                    CoroutineScope( Dispatchers.Main ).launch {
                        vue.redirigerVersChoisirSiegeRetour()
                    }
                }
            }
            else{
                modèle.indiceVolCourrant = modèle.indiceVolRetour
                modèle.réservationRetour.siège?.numéro = numSiègeCourrant
                try {
                    modèle.ajouterReservation(modèle.réservationAller)
                    modèle.ajouterReservation(modèle.réservationRetour)
                    CoroutineScope( Dispatchers.Main ).launch {
                        vue.redirigerVersMesRéservation()
                    }
                } catch ( ex : SourceDeDonnéesException ) {
                    CoroutineScope( Dispatchers.Main ).launch {
                        modèle.messageErreurRéseauExistant = true
                        vue.redirigerÀBienvenueErreur()
                    }
                }
            }
        }
    }

    override fun traiterConfirmerRéservation() {
        if ( numSiègeCourrant.isEmpty() ){
            vue.afficherErreurChampsVides()
        } else{
            vue.afficherDialogConfirmer()
        }
    }

    override fun vérifierStatutSiège( id: Int, code: String ) {
        job = CoroutineScope( iocontext ).launch {
            val siège = sièges.firstOrNull {
                it.numéro == code
                        && it.classe == modèle.réservationAller.siège?.classe?.lowercase()
                        && it.statut == "occupé"
            }

            if ( siège != null ){
                CoroutineScope( Dispatchers.Main ).launch {
                    vue.placerStatutSiègeOccupée( id )
                }
            }else{
                CoroutineScope( Dispatchers.Main ).launch {
                    vue.placerStatutSiègeDisponible( id )
                }
            }
        }
    }
}