package com.nicholson.client_reservation_vol.présentation.listeVols
import com.nicholson.client_reservation_vol.domaine.entité.Vol
import com.nicholson.client_reservation_vol.présentation.Modèle
import com.nicholson.client_reservation_vol.présentation.OTD.VolListItemOTD
import com.nicholson.client_reservation_vol.présentation.listeVols.ContratVuePrésentateurListeVols.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import kotlin.coroutines.CoroutineContext

class ListeDeVolsPrésentateur (
    val vue : IListeDeVolsVue = ListeDeVolsVue(),
    iocontext : CoroutineContext = Dispatchers.IO
) :  IListeDeVolsPrésentateur{

    val modèle : Modèle = Modèle.obtenirInstance()
    val formatterDate = DateTimeFormatter.ofPattern( "dd MMMM yyyy" )
    val formatterHeure = DateTimeFormatter.ofPattern( "HH:MM" )
    private var job : Job? = null
    private var iocontext : CoroutineContext = iocontext

    override fun traiterObtenirVols() {
        job = CoroutineScope( iocontext ).launch {
            var listeDeVols : List<Vol> = listOf()
            if(modèle.aller) {
                modèle.listeVolAller = modèle.obtenirListeVolAllerParFiltre()
                listeDeVols = modèle.listeVolAller
            }
            else{
                modèle.listeVolRetour = modèle.obtenirListeVolRetourParFiltre()
                listeDeVols = modèle.listeVolRetour
            }
            val listeVolsOTD = listeDeVols.map {
                VolListItemOTD(
                    dateDépart = it.dateDepart.format( formatterDate ),
                    heureDépart = it.dateDepart.format( formatterHeure ),
                    heureArrivée = it.dateArrivee.format( formatterHeure ),
                    prixÉconomique = String.format( "%.2f$", it.prixParClasse["Économique"] ),
                    nomVilleDépart = it.aeroportDebut.ville.nom,
                    nomVilleArrivée = it.aeroportFin.ville.nom,
                    codeAéroportDépart = it.aeroportDebut.code,
                    codeAéroportArrivée = it.aeroportFin.code,
                    durée = it.durée.toComponents {
                            hrs, min, _, _ ->
                        "${hrs}h${min}"
                    }
                )
            }
            if ( listeDeVols.isNotEmpty() ){
                val villeDestination =  listeDeVols[0].aeroportFin.ville
                CoroutineScope( Dispatchers.Main ).launch {
                    vue.afficherInfoDestination( villeDestination.nom, villeDestination.url_photo )
                }
            }else{
                CoroutineScope( Dispatchers.Main ).launch {
                    vue.afficherMessagePasDeVol()
                }
            }
            CoroutineScope( Dispatchers.Main ).launch {
                vue.afficherVols( listeVolsOTD )
            }
        }
    }

    override fun traiterVolCliqué( index: Int ) {
        if(modèle.aller){
            modèle.indiceVolAller = index
        }
        else{
            modèle.indiceVolRetour = index
        }
        modèle.indiceVolCourrant = index
        vue.redirigerVersChoixClasse()
    }
}