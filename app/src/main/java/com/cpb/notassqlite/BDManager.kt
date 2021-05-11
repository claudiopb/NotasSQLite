package com.cpb.notassqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast

class BDManager {
    val dbNombre = "MisNotas"
    val dbTabla = "Notas"
    val columnaID = "ID"
    val columnaTitulo = "Titulo"
    val columnaDescripcion = "Descripcion"
    val dbVersion = 1
//CREATE TABLE IF NOT EXISTS + Notas + (ID INTEGER PRIMARY KEY AUTOINCREMENT,Titulo TEXT NOT NULL, Descripcion TEXT NOT NULL)
    val sqlCrearTabla = "CREATE TABLE IF NOT EXISTS " + dbTabla + " ("+
            columnaID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            columnaTitulo + " TEXT NOT NULL," +
            columnaDescripcion + " TEXT NOT NULL)"

    var sqlDB: SQLiteDatabase? = null

    constructor(context: Context){
        val db = DBHelperNotas(context)
        sqlDB = db.writableDatabase
    }

    inner class DBHelperNotas(context: Context) : SQLiteOpenHelper(context,dbNombre,null,dbVersion){
        var context : Context? = context

        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(sqlCrearTabla)
            Toast.makeText(this.context, "Base de datos creada",Toast.LENGTH_LONG).show()
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("Drop table IF EXISTS $dbTabla")
        }
    }

    fun insert (values: ContentValues) : Long{
        val ID = sqlDB!!.insert(dbTabla,"",values)
        return ID
    }

    /*fun query(projection: Array<String>, selection: String,
              selectionArgs: Array<String>, orderBy: String) : Cursor {
        val consulta = SQLiteQueryBuilder()
        consulta.tables = dbTabla
        val cursor = consulta.query(sqlDB, projection,selection, selectionArgs, null, null, orderBy)
        return cursor
    }*/
    
    fun query(projection: Array<String>, selecection: String, selectionArgs: Array<String>, orderBy: String) : Cursor{
        val consulta = SQLiteQueryBuilder()
        consulta.tables = dbTabla
        val cursor = consulta.query(sqlDB,projection,selecection,selectionArgs,null,null,orderBy)
        return cursor

    }

    fun borrar(selection: String, selectionArgs:
    Array<String>) : Int{
        val contador = sqlDB!!.delete(dbTabla, selection,selectionArgs)
        return contador
    }

    fun actualizar (values : ContentValues,selection: String,selectionArgs: Array<String>) : Int {
        val contador = sqlDB!!.update(dbTabla, values,selection, selectionArgs)
        return contador
    }
}

