package com.example.pokedecks.databse

import android.content.ContentUris
import android.net.Uri
import android.provider.BaseColumns

object TrainerContract {

    internal const val TABLE_NAME = "Trainers"

    /**
     * The URI to access the Trainers table.
     */

    val CONTENT_URI: Uri = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME)

    const val CONTENT_TYPE = "vnd.android.cursor.dir/vnd.$CONTENT_AUTHORITY.$TABLE_NAME"
    const val CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.$CONTENT_AUTHORITY.$TABLE_NAME"

    // Tasks fields
    object Columns {
        const val ID = BaseColumns._ID
        const val TRAINER_NAME = "Name"
        const val TRAINER_COUNTRY = "Country"
        const val TRAINER_LEVEL = 1
        const val TRAINER_FAV_POKEMON = "Pikachu"
    }

    fun getId(uri: Uri): Long {
        return ContentUris.parseId(uri)
    }

    fun buildUriFromId(id: Long): Uri {
        return ContentUris.withAppendedId(CONTENT_URI, id)
    }
}
