package com.nicholson.client_reservation_vol.présentation.ChoisirInfo

class ChoisirInfoPresentateur(var vue: ContratVueChoisirInfo.IChoisirInfoVue = ChoisirInfoVue()) :
    ContratVueChoisirInfo.IChoisirInfoPrésentateur {
    override fun traiterDemandeRedirectionChoisirSiege() {
        vue.redirigerAChoisirSiege()
    }
}
