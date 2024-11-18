package com.nicholson.client_reservation_vol.présentation.ChoisirInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.nicholson.client_reservation_vol.R
import com.nicholson.client_reservation_vol.donnée.fictive.SourceDonnéesFictive



class ChoisirInfoVue : Fragment(), ContratVueChoisirInfo.IChoisirInfoVue {

    var présentateur: ContratVueChoisirInfo.IChoisirInfoPrésentateur? = ChoisirInfoPresentateur(this)
    lateinit var navController: NavController

    lateinit var btnSaveInfo: MaterialButton
    lateinit var imageViewVillechoisirInformation: ImageView
    lateinit var ChoisirNom: TextView
    lateinit var ChoisirPrenom: TextView
    lateinit var ChoisirNumPasseport: TextView
    lateinit var ChoisirEmail: TextView
    lateinit var ChoisirTéléphone: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        attacherÉcouteurRedirectionChoisirSiege(view)
        imageViewVillechoisirInformation = view.findViewById(R.id.imageViewVillechoisirInformation)
        imageViewVillechoisirInformation = view.findViewById(R.id.imageViewVillechoisirInformation)
        ChoisirNom = view.findViewById(R.id.ChoisirNom)
        ChoisirPrenom = view.findViewById(R.id.ChoisirPrenom)
        ChoisirNumPasseport = view.findViewById(R.id.ChoisirNumPasseport)
        ChoisirEmail = view.findViewById(R.id.ChoisirEmail)
        ChoisirTéléphone = view.findViewById(R.id.ChoisirTéléphone)
        btnSaveInfo = view.findViewById(R.id.btnSaveInfo)

        val imageUrl = SourceDonnéesFictive.listVille[0].url_photo
        Glide.with(requireContext()).load(imageUrl).into(imageViewVillechoisirInformation)

        val réservation = SourceDonnéesFictive.listeRéservation[0]
        val client = réservation.clients[0]

        ChoisirNom.text = client.nom
        ChoisirPrenom.text = client.prénom
        ChoisirNumPasseport.text = client.numéroPasseport
        ChoisirEmail.text = client.email
        ChoisirTéléphone.text = client.numéroTéléphone
    }

    override fun redirigerAChoisirSiege() {
        navController.navigate(R.id.action_choisirInfoVue_to_choisirSiegeVue)
    }

    private fun attacherÉcouteurRedirectionChoisirSiege(view: View) {
        btnSaveInfo = view.findViewById(R.id.btnSaveInfo)
        btnSaveInfo.setOnClickListener {
            présentateur?.traiterDemandeRedirectionChoisirSiege()
        }
    }
}
