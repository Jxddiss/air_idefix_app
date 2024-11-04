package com.nicholson.client_reservation_vol.présentation.vue

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.nicholson.client_reservation_vol.R
import java.time.Year

class RechercherUnVolVue : Fragment() {
    private lateinit var choisirDate: EditText
    private lateinit var calendrier: Calendar
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var btnAllerEtRetourn:Button
    private lateinit var btnAllerSimple:Button
    private lateinit var choisirDateRetour: EditText



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_rechercher_un_vol_vue, container, false)

        //Initialization des objets
        choisirDate = view.findViewById(R.id.ChosirDate)
        choisirDateRetour = view.findViewById(R.id.ChosirDateRetour)
        btnAllerEtRetourn = view.findViewById(R.id.btnAllerEtRetourn)
        btnAllerSimple=view.findViewById(R.id.btnAllerSimple)

        val villes = listOf("Cancun", "Budapest", "Varsovie", "Busan")
        val choisirVilleDe: AutoCompleteTextView = view.findViewById(R.id.ChosirVilleDe)
        val choisirVilleVers: AutoCompleteTextView = view.findViewById(R.id.ChosirVilleVers)

        val dropDown = ArrayAdapter(requireContext(), R.layout.liste_villes, villes)
        choisirVilleVers.setAdapter(dropDown)
        choisirVilleDe.setAdapter(dropDown)
        //Le drop down pour afficher les villes dans 'DE', des qu'on click afficher avec Toast la ville selectioné
        choisirVilleDe.setOnItemClickListener { adapterView, _, i, _ ->
            val itemSelected = adapterView.getItemAtPosition(i) as String
            Toast.makeText(requireContext(), "Ville: $itemSelected", Toast.LENGTH_SHORT).show()
        }
        //Le drop down pour afficher les villes dans 'VERS', des qu'on click afficher avec Toast la ville selectioné
        choisirVilleVers.setOnItemClickListener { adapterView, _, i, _ ->
            val itemSelected = adapterView.getItemAtPosition(i) as String
            Toast.makeText(requireContext(), "Ville: $itemSelected", Toast.LENGTH_SHORT).show()
        }


        calendrier = Calendar.getInstance()
        choisirDate.setOnClickListener {
            afficherDatePicker(choisirDate) //afficher le calendrier
        }
        // Set OnClickListener pour  "Aller Simple" button
        btnAllerSimple.setOnClickListener {
            // Disable le EditText pour date selection
            choisirDateRetour.isEnabled = false
            choisirDateRetour.requestFocus()
        }

        // Set OnClickListener pour  "Aller-et-Retourn" button
        btnAllerEtRetourn.setOnClickListener {
            // Enable le EditText pour date selection
            choisirDateRetour.isEnabled = true
            choisirDateRetour.requestFocus()
        }
        choisirDateRetour.setOnClickListener {
            afficherDatePicker(choisirDateRetour) //afficher le calendrier
        }
        return view
    }

    //Fonction pour afficher le calendrier
    private fun afficherDatePicker(editText: EditText) {
        val year = calendrier.get(Calendar.YEAR)
        val month = calendrier.get(Calendar.MONTH)
        val day = calendrier.get(Calendar.DAY_OF_MONTH)

        datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            val dateSelectione = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            editText.setText(dateSelectione)
        }, year, month, day)
        datePickerDialog.show()
    }

}





