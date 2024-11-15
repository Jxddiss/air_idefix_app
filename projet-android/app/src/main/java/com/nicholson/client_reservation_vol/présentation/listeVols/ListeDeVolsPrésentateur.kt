package com.nicholson.client_reservation_vol.présentation.listeVols
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
        val listeDeVols = modèle.obtenirListeVolParFiltre()
        val villeDestination =  listeDeVols[0].aeroportFin.ville
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

        vue.afficherInfoDestination( villeDestination.nom, villeDestination.url_photo )
        vue.afficherVols( listeVolsOTD )
    }

    override fun traiterVolCliqué( index: Int ) {
        modèle.indiceVolCourrant = index
        vue.redirigerVersChoixClasse()
    }
}