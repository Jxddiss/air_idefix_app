package com.nicholson.client_reservation_vol.présentation.choisirclasse

import android.util.Log
import com.nicholson.client_reservation_vol.domaine.entité.Vol
import com.nicholson.client_reservation_vol.présentation.Modèle
import com.nicholson.client_reservation_vol.présentation.OTD.VolChoixClassOTD
import com.nicholson.client_reservation_vol.présentation.choisirclasse.ContratVuePrésentateurChoisirClasse.*
import java.time.format.DateTimeFormatter

class ChoisirClassePrésentateur(
    private val vue : IChoisirClasseVue = ChoisirClasseVue()
) : IChoisirClassePrésentateur {
    private val modèle : Modèle = Modèle.obtenirInstance()
    private val formatterDate = DateTimeFormatter.ofPattern( "dd MMMM yyyy" )
    private val formatterHeure = DateTimeFormatter.ofPattern( "HH:MM" )
    private var classeChoisis = "Économique"

    override fun traiterDémarage() {
        var vol : Vol
        if(modèle.aller){
            vol = modèle.getVolCourrantAller(modèle.indiceVolAller)
        }
        else{
            vol = modèle.getVolCourrantRetour(modèle.indiceVolRetour)
        }
        val volPrécédent = modèle.getVolPrécédent()
        val volSuivant = modèle.getVolSuivant()
        val volChoixClassOTD = VolChoixClassOTD(
            dateDépart = vol.dateDepart.format( formatterDate ),
            heureDépart = vol.dateDepart.format( formatterHeure ),
            heureArrivée = vol.dateArrivee.format( formatterHeure ),
            prixÉconomique = String.format( "%.2f$", vol.prixParClasse["Économique"] ),
            prixAffaire = String.format( "%.2f$", vol.prixParClasse["Affaire"] ),
            prixPremière = String.format( "%.2f$", vol.prixParClasse["Première"] ),
            nomVilleDépart = vol.aeroportDebut.ville.nom,
            nomVilleArrivée = vol.aeroportFin.ville.nom,
            urlPhotoVilleArrivé = vol.aeroportFin.ville.url_photo,
            codeAéroportDépart = vol.aeroportDebut.code,
            codeAéroportArrivée = vol.aeroportFin.code,
            durée = vol.durée.toComponents {
                    hrs, min, _, _ ->
                "${hrs}h${min}"
            }
        )
        vue.miseEnPlace( volChoixClassOTD )
        vue.placerVolPrécédent(
            volPrécédent.dateDepart.format( formatterDate ),
            String.format( "%.2f$", volPrécédent.prixParClasse["Économique"] )
        )
        vue.placerVolSuivant(
            volSuivant.dateDepart.format( formatterDate ),
            String.format( "%.2f$", volSuivant.prixParClasse["Économique"] )
        )
    }

    override fun traiterContinuer() {

        if(modèle.volRetourExiste){
            modèle.aller = false
            modèle.volRetourExiste = false
            modèle.réservationAller = modèle.créerRéservationAller(classeChoisis)
            Log.d("test volcourrant aller",modèle.getVolCourrantAller(modèle.indiceVolAller).toString())
            Log.d("what is reservation aller ", modèle.réservationAller.toString())
            vue.choisirVolRetour()
            //modèle.classeChoisis = classeChoisis
        }
        else{
            //modèle.classeChoisisRetour = classeChoisis
            modèle.réservationRetour = modèle.créerRéservationRetour(classeChoisis)
            Log.d("test volcourrant retour",modèle.getVolCourrantRetour(modèle.indiceVolRetour).toString())
            Log.d("what is reservation retour ", modèle.réservationRetour.toString())
            vue.redirigerChoixInfo()
        }

    }

    override fun traiterDemandeVolSuivant() {
        modèle.avancerVolCourrant()
        traiterDémarage()
    }

    override fun traiterDemandeVolPrécédant() {
        modèle.reculerVolCourrant()
        traiterDémarage()
    }

    override fun traterRadioÉconomiqueCliqué() {
        classeChoisis = "Économique"
    }

    override fun traterRadioAffaireCliqué() {
        classeChoisis = "Affaire"
    }

    override fun traterRadioPremièreCliqué() {
        classeChoisis = "Première"
    }
}