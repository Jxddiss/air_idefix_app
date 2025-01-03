package com.nicholson.client_reservation_vol.donnée.DataBase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.nicholson.client_reservation_vol.domaine.entité.Historique


class DataBaseClass(context: Context) : SQLiteOpenHelper(context, "HistoriqueDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE IF NOT EXISTS Historique (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                villeDe TEXT,
                villeVers TEXT,
                aeroportDe TEXT,
                aeroportVers TEXT,
                dateDepart TEXT,
                dateRetour TEXT
            )
        """
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Historique")
        onCreate(db)
    }

    fun insertHistorique(historique: Historique) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("villeDe", historique.villeDe)
            put("villeVers", historique.villeVers)
            put("aeroportDe", historique.aeroportDe)
            put("aeroportVers", historique.aeroportVers)
            put("dateDepart", historique.dateDepart.toString()) // Conversion LocalDate a String
            put("dateRetour", historique.dateRetour?.toString())
        }
        val result = db.insert("Historique", null, values)
        if (result == -1L) {
            Log.e("Database", "Insert erreur")
        } else {
            Log.d("Database", " Insert correct")
        }
        db.close()
    }


    fun supprimerHistorique(historique: Historique) {
        val db = writableDatabase
        try {
            val rowsDeleted = db.delete(
                "Historique",
                "villeDe = ? AND villeVers = ? AND aeroportDe = ? AND aeroportVers = ? AND dateDepart = ?",
                arrayOf(
                    historique.villeDe,
                    historique.villeVers,
                    historique.aeroportDe,
                    historique.aeroportVers,
                    historique.dateDepart.toString()
                )
            )
            if (rowsDeleted > 0) {
                Log.d("Database", "Historique effacé avec succès")
            } else {
                Log.d("Database", "Aucun historique correspondant trouvé à supprimer")
            }
        } catch (e: Exception) {
            Log.e("Database", "Erreur lors de la suppression de l'historique", e)
        } finally {
            db.close()
        }
    }



}