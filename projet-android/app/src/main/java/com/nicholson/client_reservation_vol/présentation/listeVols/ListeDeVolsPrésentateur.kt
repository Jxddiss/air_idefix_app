package com.nicholson.client_reservation_vol.présentation.listeVols
import com.nicholson.client_reservation_vol.domaine.entité.Vol
import com.nicholson.client_reservation_vol.présentation.Modèle
import com.nicholson.client_reservation_vol.présentation.OTD.VolListItemOTD
import com.nicholson.client_reservation_vol.présentation.listeVols.ContratVuePrésentateurListeVols.*
import java.time.format.DateTimeFormatter

class ListeDeVolsPrésentateur (
    val vue : IListeDeVolsVue = ListeDeVolsVue()
) :  IListeDeVolsPrésentateur{
    val modèle : Modèle = Modèle.obtenirInstance()

    val formatterDate = DateTimeFormatter.ofPattern( "dd MMMM yyyy" )
    val formatterHeure = DateTimeFormatter.ofPattern( "HH:MM" )

    override fun traiterObtenirVols() {
        val listeDeVols : List<Vol>
        if(modèle.aller) {
            listeDeVols = modèle.obtenirListeVolParFiltre(modèle.filtreVolCourrant)
        }
        else{
            listeDeVols = modèle.obtenirListeVolParFiltre(modèle.filtreVolRetour)
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
            vue.afficherInfoDestination( villeDestination.nom, villeDestination.url_photo )
        }else{
            vue.afficherMessagePasDeVol()
        }
        vue.afficherVols( listeVolsOTD )
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