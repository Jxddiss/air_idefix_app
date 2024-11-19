package com.nicholson.client_reservation_vol.présentation.ChoisirSiège

interface ContratVuePrésentateurChoisirSiège {
    interface IChoisirSiègeVue {
        fun miseEnPlace( nomVilleDépart : String, nomVilleArrivée : String, urlPhoto : String )
        fun miseÀjourSiègeCliquéVersSélectionnée( id : Int )
        fun miseÀjourSiègeCliquéVersDéSélectionné( id : Int )
        fun placerStatutSiègeOccupée( id : Int )
        fun placerStatutSiègeDisponible( id : Int )
        fun afficherErreur( message : String )
        fun redirigerVersMesRéservation()
    }
    interface IChoisirSiègePrésentateur {
        fun traiterDémarage()
        fun traiterSiègeCliqué( id : Int, code : String )
        fun traiterConfirmerRéservation()
        fun vérifierStatutSiège( id : Int, code: String )
    }
}