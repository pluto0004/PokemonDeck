package com.example.pokedecks

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

private const val TAG = "AppDatabase"
private const val DATABASE = "PokemonDecks.db"

// update this version when updating the database
private const val DATABASE_VERSION = 1

internal class AppDatabase private constructor(context: Context) :
    SQLiteOpenHelper(context, DATABASE, null, DATABASE_VERSION) {

    init {
        Log.d(TAG, "AppDatabase: initialising, version is $DATABASE_VERSION")
    }

    override fun onCreate(db: SQLiteDatabase) {
        Log.d(TAG, "onCreate: starts")
        val sSQL = """
            CREATE TABLE ${TrainerContract.TABLE_NAME} (
            ${TrainerContract.Columns.ID} INTEGER PRIMARY KEY NOT NULL,
            ${TrainerContract.Columns.TRAINER_NAME} STRING NOT NULL,
            ${TrainerContract.Columns.TRAINER_COUNTRY} STRING,
            ${TrainerContract.Columns.TRAINER_LEVEL} INTEGER NOT NULL,
            ${TrainerContract.Columns.TRAINER_FAV_POKEMON} STRING);
        """.trimIndent()

        Log.d(TAG, sSQL)
        db.execSQL(sSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.d(TAG, "onUpgrade: starts")
    }
}
