package com.nicholson.client_reservation_vol.présentation.listeVols
import com.nicholson.client_reservation_vol.domaine.entité.Vol
import com.nicholson.client_reservation_vol.donnée.exceptions.SourceDeDonnéesException
import com.nicholson.client_reservation_vol.donnée.http.exception.AuthentificationException
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
            try {
                val listeDeVols : List<Vol>
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
                            "${String.format("%02d",hrs)}h${String.format("%02d",min)}"
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
                    vue.masquerChargement()
                    vue.afficherVols( listeVolsOTD )
                }
            } catch ( ex : SourceDeDonnéesException ) {
                modèle.messageErreurRéseauExistant = true
                CoroutineScope( Dispatchers.Main ).launch {
                    vue.redirigerBienvenueErreur()
                }
            } catch ( ex : AuthentificationException ) {
                CoroutineScope( Dispatchers.Main ).launch {
                    vue.seConnecter(
                        réussite = {
                            modèle.effectuerLogin( it )
                            vue.montrerChargement()
                            traiterObtenirVols()
                        },
                        échec = {
                            modèle.messageErreurRéseauExistant = true
                            vue.redirigerBienvenueErreur()
                        }
                    )
                }
            }
        }
    }

    override fun traiterVolCliqué( index: Int ) {
        if ( modèle.estConnecté ) {
            if(modèle.aller){
                modèle.indiceVolAller = index
            }
            else{
                modèle.indiceVolRetour = index
            }
            modèle.indiceVolCourrant = index
            vue.redirigerVersChoixClasse()
        } else {
            vue.montrerChargement()
            vue.afficherMessageNonConnectée()
            vue.seConnecter(
                réussite = {
                    modèle.effectuerLogin( it )
                    vue.redirigerVersChoixClasse()
                },
                échec = {
                    modèle.messageErreurRéseauExistant = true
                    vue.redirigerBienvenueErreur()
                }
            )
        }
    }

    override fun traiterDémarage() {
        vue.montrerChargement()
    }
}