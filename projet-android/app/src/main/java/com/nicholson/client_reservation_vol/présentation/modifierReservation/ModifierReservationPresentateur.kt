package com.nicholson.client_reservation_vol.présentation.modifierReservation

import com.nicholson.client_reservation_vol.domaine.entité.Client
import com.nicholson.client_reservation_vol.donnée.exceptions.SourceDeDonnéesException
import com.nicholson.client_reservation_vol.donnée.http.exception.AuthentificationException
import com.nicholson.client_reservation_vol.présentation.Modèle
import com.nicholson.client_reservation_vol.présentation.OTD.ClientOTD
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ModifierReservationPresentateur(
    var vue: ContratVueModifierReservation.IModifierReservationVue = ModifierReservationVue(),
    private val iocontext : CoroutineContext = Dispatchers.IO
) : ContratVueModifierReservation.IModifierReservationPrésentateur {
    private val modèle = Modèle.obtenirInstance()
    private var job : Job? = null

    override fun traiterDémarage() {
        vue.montrerChargement()
        job = CoroutineScope( iocontext ).launch {
            var client : Client? = null
            try{
                client = modèle.obtenirReservationCourrante().client
            }
            catch(ex : AuthentificationException){
                CoroutineScope(Dispatchers.Main).launch {
                    vue.seConnecter(
                        réussite = {
                            modèle.effectuerLogin( it )
                            traiterDémarage()
                        },
                        échec = {
                            modèle.messageErreurRéseauExistant = true
                            vue.redirigerBienvenueErreur()
                        }

                    )
                }
            }
            catch(ex : SourceDeDonnéesException){
                modèle.messageErreurRéseauExistant = true
                CoroutineScope(Dispatchers.Main).launch {
                    vue.redirigerBienvenueErreur()
                }
            }
            client?.let{
                val clientOTD = ClientOTD(
                    nom = it.nom,
                    prénom = it.prénom,
                    adresse = it.adresse,
                    numéroPasseport = it.numéroPasseport,
                    email = it.email ?: "",
                    téléphone = it.numéroTéléphone ?: ""
                )
                CoroutineScope( Dispatchers.Main ).launch{
                    vue.masquerChargement()
                    vue.miseEnPlace(clientOTD)
                }
            }

        }
    }
}
