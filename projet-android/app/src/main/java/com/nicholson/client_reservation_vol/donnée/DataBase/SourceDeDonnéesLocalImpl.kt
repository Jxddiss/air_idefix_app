package com.nicholson.client_reservation_vol.donnée.DataBase

import android.content.Context
import android.util.Log
import com.nicholson.client_reservation_vol.domaine.entité.Historique
import com.nicholson.client_reservation_vol.donnée.ISourceDeDonnéesHistorique
import java.time.LocalDate
class SourceDeDonnéesLocalImpl( context: Context ) : ISourceDeDonnéesHistorique {

    private val dbHelper = DataBaseClass(context)

    override fun ajouterHistorique(historique: Historique) {
        Log.d("SourceDeDonnéesLocalImpl", "Inserting into database")
        dbHelper.insertHistorique(historique)
    }

    override fun obtenirListHistorique(): List<Historique> {
        val db = dbHelper.readableDatabase
        val cursor = db.query("Historique", null, null, null, null, null, null)
        val listHistorique = mutableListOf<Historique>()

        try {
            if (cursor.moveToFirst()) {
                do {

                    val villeDe = cursor.getString(cursor.getColumnIndexOrThrow("villeDe"))
                    val villeVers = cursor.getString(cursor.getColumnIndexOrThrow("villeVers"))
                    val aeroportDe = cursor.getString(cursor.getColumnIndexOrThrow("aeroportDe"))
                    val aeroportVers = cursor.getString(cursor.getColumnIndexOrThrow("aeroportVers"))


                    // Conversion de la date a  LocalDate
                    val dateDepartString = cursor.getString(cursor.getColumnIndexOrThrow("dateDepart"))
                    val dateDepart: LocalDate = try {
                        LocalDate.parse(dateDepartString)
                    } catch (e: Exception) {
                        Log.e("Historique", "Error parsing dateDepart", e)
                        LocalDate.now()
                    }

                    // Conversion de la date a  LocalDate
                    val dateRetourIndex = cursor.getColumnIndex("dateRetour")
                    val dateRetour: LocalDate? = if (dateRetourIndex >= 0) {
                        val dateRetourString = cursor.getString(dateRetourIndex)
                        try {
                            LocalDate.parse(dateRetourString)
                        } catch (e: Exception) {
                            null
                        }
                    } else {
                        null
                    }

                    val historique = Historique(
                        villeDe = villeDe,
                        villeVers = villeVers,
                        aeroportDe = aeroportDe,
                        aeroportVers = aeroportVers,
                        dateDepart = dateDepart,
                        dateRetour = dateRetour
                    )
                    listHistorique.add(historique)
                } while (cursor.moveToNext())
            } else {
                Log.d("Historique", "pas data trouve")
            }
        } catch (e: Exception) {
            Log.e("Historique", "Erreur", e)
        } finally {
            cursor.close()
            db.close()
        }

        return listHistorique
    }

}