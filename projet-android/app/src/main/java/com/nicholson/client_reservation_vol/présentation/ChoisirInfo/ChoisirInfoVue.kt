package com.nicholson.client_reservation_vol.présentation.ChoisirInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.nicholson.client_reservation_vol.R
import com.nicholson.client_reservation_vol.présentation.OTD.ClientModifiableOTD
import com.nicholson.client_reservation_vol.présentation.OTD.ClientOTD


class ChoisirInfoVue : Fragment(), ContratVueChoisirInfo.IChoisirInfoVue {

    var présentateur: ContratVueChoisirInfo.IChoisirInfoPrésentateur? = ChoisirInfoPresentateur(this)
    lateinit var navController: NavController

    lateinit var btnSaveInfo: MaterialButton
    lateinit var imageViewVilleChoisirInformation: ImageView
    lateinit var ChoisirNom: EditText
    lateinit var ChoisirPrenom: EditText
    lateinit var ChoisirNumPasseport: EditText
    lateinit var ChoisirTéléphone: EditText
    lateinit var ChoisirAdresse: EditText
    lateinit var textViewInfoVoyage : TextView
    private lateinit var layoutBarChargement : ConstraintLayout

    override fun afficherMessageErreur(message: String) {
        requireActivity().runOnUiThread {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_choisir_info_vue, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        imageViewVilleChoisirInformation = view.findViewById(R.id.imageViewVilleChoisirInformation)
        ChoisirNom = view.findViewById(R.id.ChoisirNom)
        ChoisirPrenom = view.findViewById(R.id.ChoisirPrenom)
        ChoisirNumPasseport = view.findViewById(R.id.ChoisirNumPasseport)
        ChoisirAdresse = view.findViewById(R.id.ChoisirAdresse)
        ChoisirTéléphone = view.findViewById(R.id.ChoisirTéléphone)
        textViewInfoVoyage = view.findViewById( R.id.textViewInfoVoyage )
        layoutBarChargement = view.findViewById(R.id.barDeChargement)

        btnSaveInfo = view.findViewById(R.id.btnSaveInfo)
        btnSaveInfo.setOnClickListener {
            présentateur?.traiterDemandeRedirectionChoisirSiege()
        }

        présentateur?.traiterDémarage()
    }

    override fun miseEnPlace(
        nomVilleDépart : String,
        nomVilleArrivée: String,
        urlPhoto: String,
        clientOTD: ClientOTD ) {

        textViewInfoVoyage.text = getString( R.string.vol_de, nomVilleDépart, nomVilleArrivée )

        Glide.with( requireContext() )
            .load( urlPhoto )
            .into( imageViewVilleChoisirInformation )

        ChoisirNom.setText( clientOTD.nom )
        ChoisirPrenom.setText( clientOTD.prénom )
        ChoisirAdresse.setText( clientOTD.adresse )
        ChoisirNumPasseport.setText( clientOTD.numéroPasseport )
        ChoisirTéléphone.setText( clientOTD.téléphone )
        layoutBarChargement.visibility = View.GONE
    }

    override fun obtenirInfoClient() {
        val clientOTD = ClientModifiableOTD(
            ChoisirNom.text.toString(),
            ChoisirPrenom.text.toString(),
            ChoisirAdresse.text.toString(),
            ChoisirNumPasseport.text.toString(),
            ChoisirTéléphone.text.toString()
        )
        présentateur?.traiterObtenirInfo(clientOTD)
    }

    override fun redirigerAChoisirSiege() {
        navController.navigate(R.id.action_choisirInfoVue_to_choisirSiegeVue)
    }

    override fun montrerChargement() {
        layoutBarChargement.visibility = View.VISIBLE
    }

    override fun redirigerBienvenueErreur() {
        navController.navigate( R.id.action_choisirInfoVue_vers_bienvenueVue )
    }
}
