package com.licensem.nitraintracker

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DbOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "nitraintracker", null, 1) {
    companion object {
        private var instance: DbOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DbOpenHelper {
            if (instance == null) {
                instance = DbOpenHelper(ctx.getApplicationContext())
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable("favourites", true, "origin" to TEXT, "destination" to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable("favourites", true)
    }
}
