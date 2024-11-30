package com.nicholson.client_reservation_vol.présentation.ChoisirSiège

interface ContratVuePrésentateurChoisirSiège {
    interface IChoisirSiègeVue {
        fun miseEnPlace( nomVilleDépart : String,
                         nomVilleArrivée : String,
                         urlPhoto : String,
                         classeChoisis : String )
        fun miseÀjourSiègeCliquéVersSélectionnée( id : Int )
        fun miseÀjourSiègeCliquéVersDéSélectionné( id : Int )
        fun placerStatutSiègeOccupée( id : Int )
        fun placerStatutSiègeDisponible( id : Int )
        fun afficherErreur( message : String )
        fun afficherDialogConfirmer()
        fun redirigerVersMesRéservation()
        fun redirigerVersChoisirSiegeRetour()
    }
    interface IChoisirSiègePrésentateur {
        fun traiterDémarage()
        fun traiterSiègeCliqué( id : Int, code : String )
        fun traiterDialogConfirmer()
        fun traiterConfirmerRéservation()
        fun vérifierStatutSiège( id : Int, code: String )
    }
}