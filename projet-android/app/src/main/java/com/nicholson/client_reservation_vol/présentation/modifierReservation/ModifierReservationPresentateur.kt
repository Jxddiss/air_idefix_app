package com.nicholson.client_reservation_vol.présentation.modifierReservation

class ModifierReservationPresentateur(
    var vue: ContratVueModifierReservation.IModifierReservationVue = ModifierReservationVue()
) : ContratVueModifierReservation.IModifierReservationPrésentateur {

    override fun traiterDemandeRedirectionChoisirSiege() {
        vue.redirigerAChoisirSiege()
    }

    override fun traiterDemandeRedirectionChoisirClasse() {
        vue.redirigerAChoisirClasse()
    }

    override fun traiterDemandeRedirectionChoisirInformation() {
        vue.redirigerAChoisirInformation()
    }
}
