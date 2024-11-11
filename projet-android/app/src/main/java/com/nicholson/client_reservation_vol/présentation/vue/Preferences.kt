package com.nicholson.client_reservation_vol.présentation.vue

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.fragment.findNavController
import com.nicholson.client_reservation_vol.MainActivity
import com.nicholson.client_reservation_vol.R
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.util.Locale


class Preferences : Fragment() {

    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var radioGroupLangues: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_preferences_vue, container, false)

        radioGroupLangues = view.findViewById(R.id.radioGroupLangues)

        preferences = requireContext().getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        editor = preferences.edit()


        setRadioButtonPourLanguage()

        radioGroupLangues.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButtonFrancais -> {
                    setLanguage("fr")
                }
                R.id.radioButtonAnglais -> {
                    setLanguage("en")
                }
                R.id.radioButtonEspagnol -> {
                    setLanguage("es")
                }
            }
        }

        // ajoute - btn  acepter pour aller dans le bienvenue
        val btnAccepter: Button = view.findViewById(R.id.btnAccepter)
        btnAccepter.setOnClickListener {
            findNavController().navigate(R.id.action_preferencesVue_vers_bienvenueVue)
        }

        return view
    }

    private fun setLanguage(languageCode: String) {
        editor.putString("language", languageCode)
        editor.apply()

        // dans la MainActivity il faut "notify" l'update de language
        (activity as? MainActivity)?.surChangementDeLangue(languageCode)
    }

    private fun setRadioButtonPourLanguage() {
        val langueEnregistrée = preferences.getString("language", "fr") ?: "fr"
        when (langueEnregistrée) {
            "fr" -> radioGroupLangues.check(R.id.radioButtonFrancais)
            "en" -> radioGroupLangues.check(R.id.radioButtonAnglais)
            "es" -> radioGroupLangues.check(R.id.radioButtonEspagnol)
        }
    }
}