package com.nicholson.client_reservation_vol.présentation.choisirclasse

import com.nicholson.client_reservation_vol.présentation.OTD.VolChoixClassOTD

interface ContratVuePrésentateurChoisirClasse {
    interface IChoisirClasseVue{
        fun miseEnPlace( volChoixClassOTD : VolChoixClassOTD )
        fun obtenirChoixClasse() : String
        fun redirigerChoixInfo()
        fun placerVolPrécédent( date : String, prixÉconomique : String )
        fun placerVolSuivant( date : String, prixÉconomique : String )
        fun choisirVolRetour()
    }

    interface IChoisirClassePrésentateur{
        fun traiterDémarage()
        fun traiterContinuer()
        fun traiterDemandeVolSuivant()
        fun traiterDemandeVolPrécédant()
        fun traterRadioÉconomiqueCliqué()
        fun traterRadioAffaireCliqué()
        fun traterRadioPremièreCliqué()
    }
}