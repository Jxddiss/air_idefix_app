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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nicholson.client_reservation_vol.R
import com.nicholson.client_reservation_vol.présentation.ChoisirSiège.ContratVuePrésentateurChoisirSiège.*

class ChoisirSiegeVue : Fragment(), IChoisirSiègeVue {
    private lateinit var textViewNomDestination: TextView
    private lateinit var textViewClasse : TextView
    private lateinit var imageViewVillechoisirInformation: ImageView
    private lateinit var btnConfirmerRéservation : Button
    private lateinit var navController: NavController
    private lateinit var dialogConfirmation: MaterialAlertDialogBuilder
    private var présentateur : IChoisirSiègePrésentateur? = ChoisirSiègePrésentateur( this )

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
        textViewClasse = vue.findViewById( R.id.textViewClasse )
        présentateur?.traiterDémarage()
        btnConfirmerRéservation = vue.findViewById( R.id.btnConfirmerRéservation )
        btnConfirmerRéservation.setOnClickListener {
            présentateur?.traiterConfirmerRéservation()
        }

        dialogConfirmation = MaterialAlertDialogBuilder( requireContext() )
        dialogConfirmation.setMessage( getString( R.string.res_confirmer_message ) )
        dialogConfirmation.setPositiveButton( getString( R.string.confirmer ) ) { _, _ ->
            présentateur?.traiterDialogConfirmer()
        }
        dialogConfirmation.setNegativeButton( getString( R.string.annuler ) ) { dialog, _ ->
            réactiverBtnConfirmer()
            dialog.dismiss()
        }

        navController = vue.findNavController()
    }

    override fun miseEnPlace( nomVilleDépart : String,
                              nomVilleArrivée : String,
                              urlPhoto : String,
                              classeChoisis : String ) {
        textViewNomDestination.text = getString( R.string.vol_de, nomVilleDépart, nomVilleArrivée )
        textViewClasse.text = classeChoisis

        Glide.with( requireContext() )
            .load( urlPhoto )
            .into( imageViewVillechoisirInformation )
    }

    override fun miseEnPlaceSièges() {
        for ( i in 0..23 ){
            val imageViewSiège : ImageView? = view
                ?.findViewById(
                    resources
                        .getIdentifier("imageViewSiège"+(i+1),
                            "id",requireContext().packageName)
                )
            if ( imageViewSiège != null ){
                présentateur?.vérifierStatutSiège( imageViewSiège.id,
                    imageViewSiège.contentDescription.toString() )
            }
        }
    }

    override fun redirigerÀBienvenueErreur() {
        navController.navigate( R.id.action_choisirSiegeVue_vers_bienvenueVue )
    }

    override fun désactiverBtnConfirmer() {
        btnConfirmerRéservation.isClickable = false
    }

    override fun réactiverBtnConfirmer() {
        btnConfirmerRéservation.isClickable = true
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

    override fun afficherErreurChampsVides() {
        val dialogErreur = MaterialAlertDialogBuilder( requireContext() )
        dialogErreur.setMessage( "Veuillez remplir tout les champs" )
        dialogErreur.setPositiveButton( "OK" ) { it, _ ->
            it.dismiss()
        }
        dialogErreur.show()
    }

    override fun afficherDialogConfirmer() {
        dialogConfirmation.show()
    }

    override fun redirigerVersMesRéservation() {
        navController.navigate( R.id.action_choisirSiegeVue_vers_listeRéservationsVue )
    }

    override fun redirigerVersChoisirSiegeRetour() {
        navController.navigate( R.id.action_choisirSiegeVue_self )
    }

}