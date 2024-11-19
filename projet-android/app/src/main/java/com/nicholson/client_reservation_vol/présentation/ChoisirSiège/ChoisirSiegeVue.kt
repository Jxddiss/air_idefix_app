package com.nicholson.client_reservation_vol.présentation.ChoisirSiège

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.nicholson.client_reservation_vol.R
import com.nicholson.client_reservation_vol.présentation.ChoisirSiège.ContratVuePrésentateurChoisirSiège.*

class ChoisirSiegeVue : Fragment(), IChoisirSiègeVue {
    lateinit var textViewNomDestination: TextView
    lateinit var imageViewVillechoisirInformation: ImageView
    lateinit var btnConfirmerRéservation : Button
    lateinit var navController: NavController
    var présentateur : IChoisirSiègePrésentateur? = ChoisirSiègePrésentateur( this )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_choisir_siege_vue, container, false)
        imageViewVillechoisirInformation = view.findViewById(R.id.imageViewVillechoisirInformation)
        return view
    }

    override fun onViewCreated( vue : View, savedInstanceState: Bundle? ) {
        super.onViewCreated( vue, savedInstanceState )
        textViewNomDestination = vue.findViewById( R.id.textViewNomDestination )
        for ( i in 0..23 ){
            val imageViewSiège : ImageView = vue
                .findViewById(
                    resources
                        .getIdentifier("imageViewSiège"+(i+1),
                            "id",requireContext().packageName)
                )
            présentateur?.vérifierStatutSiège( imageViewSiège.id,
                imageViewSiège.contentDescription.toString() )
        }
        btnConfirmerRéservation = vue.findViewById( R.id.btnConfirmerRéservation )
        btnConfirmerRéservation.setOnClickListener {
            présentateur?.traiterConfirmerRéservation()
        }
        navController = vue.findNavController()
        présentateur?.traiterDémarage()
    }

    override fun miseEnPlace( nomVilleDépart : String, nomVilleArrivée : String, urlPhoto : String ) {
        textViewNomDestination.text = "Vol de $nomVilleDépart à $nomVilleArrivée"

        Glide.with( requireContext() )
            .load( urlPhoto )
            .into( imageViewVillechoisirInformation )
    }

    override fun miseÀjourSiègeCliquéVersSélectionnée(id : Int ) {
        this.view?.findViewById<ImageView>( id )
            ?.setColorFilter(Color.argb(255, 255, 205, 0))
    }

    override fun miseÀjourSiègeCliquéVersDéSélectionné(id: Int) {
        this.view?.findViewById<ImageView>( id )
            ?.colorFilter = null
    }

    override fun placerStatutSiègeOccupée( id: Int ) {
        this.view?.findViewById<ImageView>( id )
            ?.setBackgroundResource( R.drawable.siege_occupe )
    }

    override fun placerStatutSiègeDisponible( id: Int ) {
        val imageSiège = this.view?.findViewById<ImageView>( id )
        imageSiège?.setOnClickListener {
            présentateur?.traiterSiègeCliqué( imageSiège.id,
                imageSiège.contentDescription.toString() )
        }
    }

    override fun afficherErreur( message: String ) {
        requireActivity().runOnUiThread {
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        }
    }

    override fun redirigerVersMesRéservation() {
        navController.navigate( R.id.action_choisirSiegeVue_vers_listeRéservationsVue )
    }

}