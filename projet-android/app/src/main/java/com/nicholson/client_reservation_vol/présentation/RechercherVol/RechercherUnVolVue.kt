package com.nicholson.client_reservation_vol.présentation.RechercherVol

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.nicholson.client_reservation_vol.R

class RechercherUnVolVue : Fragment(), ContractRechercherVol.IRechercheVolVue {
    private lateinit var choisirDate: EditText
    private lateinit var calendrier: Calendar
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var btnAllerEtRetourn: Button
    private lateinit var btnAllerSimple: Button
    private lateinit var choisirDateRetour: EditText
    private lateinit var btnRechercher : Button
    private lateinit var navController: NavController
    private val présentateur = RechercherVolPresentateur()
    private lateinit var choisirVilleDe: AutoCompleteTextView
    private lateinit var choisirVilleVers: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_rechercher_un_vol_vue, container, false)

        //Initialization de mes objets
        choisirDate = view.findViewById(R.id.ChosirDate)
        choisirDateRetour = view.findViewById(R.id.ChosirDateRetour)
        btnAllerEtRetourn = view.findViewById(R.id.btnAllerEtRetourn)
        btnAllerSimple=view.findViewById(R.id.btnAllerSimple)
        choisirVilleDe = view.findViewById(R.id.ChosirVilleDe)
        choisirVilleVers = view.findViewById(R.id.ChosirVilleVers)
        btnRechercher = view.findViewById(R.id.btnRechercher)

        présentateur.attacherVue(this)
        présentateur.obtenirListeVilles()

        // Set up pour le date (chosir date)
        calendrier = Calendar.getInstance()
        choisirDate.setOnClickListener {
            afficherDatePicker(choisirDate) //afficher le calendrier
        }

        // Set OnClickListener pour "Aller Simple" button
        btnAllerSimple.setOnClickListener {
            // Changement du color de btnAllerSimple quand il est click
            btnAllerSimple.setBackgroundColor(resources.getColor(R.color.couleurAppuyée, null))
            btnAllerEtRetourn.setBackgroundColor(resources.getColor(R.color.couleur_btn_typeVol, null))
            // Disable le EditText pour date selection
            choisirDateRetour.isEnabled = false
            choisirDateRetour.requestFocus()
        }

        // Set OnClickListener pour "Aller-et-Retourn" button
        btnAllerEtRetourn.setOnClickListener {
            // Changement du color de btnAllerEtRetourn quand il est click
            btnAllerEtRetourn.setBackgroundColor(resources.getColor(R.color.couleurAppuyée, null))
            btnAllerSimple.setBackgroundColor(resources.getColor(R.color.couleur_btn_typeVol, null))
            // Enable le EditText pour date selection
            choisirDateRetour.isEnabled = true
            choisirDateRetour.requestFocus()
        }
        choisirDateRetour.setOnClickListener {
            afficherDatePicker(choisirDateRetour) //afficher le calendrier
        }

        btnRechercher.setOnClickListener {
            redirigerVersListeVols()
        }
        return view
    }

    override fun redirigerVersListeVols() {
        navController.navigate(R.id.action_rechercherUnVolVue_vers_listeDeVolsVue)
    }


    override fun onViewCreated(vue: View, savedInstanceState: Bundle?) {
        super.onViewCreated(vue, savedInstanceState)
        navController = Navigation.findNavController(vue)
    }

    // Fonction pour afficher le calendrier
    private fun afficherDatePicker(editText: EditText) {
        val year = calendrier.get(Calendar.YEAR)
        val month = calendrier.get(Calendar.MONTH)
        val day = calendrier.get(Calendar.DAY_OF_MONTH)

        datePickerDialog =
            DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                val dateSelectione = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                editText.setText(dateSelectione)
            }, year, month, day)
        datePickerDialog.show()
    }

    // method pour aficher la liste de villes avec code aerport dans les dropdowns
    override fun afficherListeVilles(aéroports: List<String>) {
        val dropDown = ArrayAdapter(requireContext(), R.layout.liste_villes,aéroports )
        choisirVilleVers.setAdapter(dropDown)
        choisirVilleDe.setAdapter(dropDown)

        // Toast selection pour "De" dropdown
        choisirVilleDe.setOnItemClickListener { adapterView, _, i, _ ->
            val itemSelected = adapterView.getItemAtPosition(i) as String
            Toast.makeText(requireContext(), "Ville: $itemSelected", Toast.LENGTH_SHORT).show()
        }

        // Toast selection pour "Vers" dropdown
        choisirVilleVers.setOnItemClickListener { adapterView, _, i, _ ->
            val itemSelected = adapterView.getItemAtPosition(i) as String
            Toast.makeText(requireContext(), "Ville: $itemSelected", Toast.LENGTH_SHORT).show()
        }
    }
}