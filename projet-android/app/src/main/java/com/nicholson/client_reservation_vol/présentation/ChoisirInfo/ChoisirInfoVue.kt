package com.nicholson.client_reservation_vol.présentation.ChoisirInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.nicholson.client_reservation_vol.R
import com.nicholson.client_reservation_vol.présentation.OTD.ClientOTD


class ChoisirInfoVue : Fragment(), ContratVueChoisirInfo.IChoisirInfoVue {

    var présentateur: ContratVueChoisirInfo.IChoisirInfoPrésentateur? = ChoisirInfoPresentateur(this)
    lateinit var navController: NavController

    lateinit var btnSaveInfo: MaterialButton
    lateinit var imageViewVilleChoisirInformation: ImageView
    lateinit var ChoisirNom: EditText
    lateinit var ChoisirPrenom: EditText
    lateinit var ChoisirNumPasseport: EditText
    lateinit var ChoisirEmail: EditText
    lateinit var ChoisirTéléphone: EditText
    lateinit var ChoisirAdresse: EditText
    lateinit var textViewInfoVoyage : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
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
        ChoisirEmail = view.findViewById(R.id.ChoisirEmail)
        ChoisirAdresse = view.findViewById(R.id.ChoisirAdresse)
        ChoisirTéléphone = view.findViewById(R.id.ChoisirTéléphone)
        textViewInfoVoyage = view.findViewById( R.id.textViewInfoVoyage )

        btnSaveInfo = view.findViewById(R.id.btnSaveInfo)
        btnSaveInfo.setOnClickListener {
            présentateur?.traiterDemandeRedirectionChoisirSiege()
        }

        présentateur?.traiterDémarage()
    }

    override fun miseEnPlace( nomVilleDépart : String, nomVilleArrivée: String, urlPhoto: String ) {
        textViewInfoVoyage.text = "Vol de $nomVilleDépart à $nomVilleArrivée"
        Glide.with( requireContext() )
            .load( urlPhoto )
            .into( imageViewVilleChoisirInformation )
    }

    override fun obtenirInfoClient() {
        val clientOTD = ClientOTD(
            ChoisirNom.text.toString(),
            ChoisirPrenom.text.toString(),
            ChoisirAdresse.text.toString(),
            ChoisirNumPasseport.text.toString(),
            ChoisirEmail.text.toString(),
            ChoisirTéléphone.text.toString()
        )
        présentateur?.traiterObtenirInfo(clientOTD)
    }

    override fun redirigerAChoisirSiege() {
        navController.navigate(R.id.action_choisirInfoVue_to_choisirSiegeVue)
    }
}
