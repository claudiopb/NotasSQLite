package com.cpb.notassqlite

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import com.cpb.notassqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    var listaDeNotas = ArrayList<Notas>()
    var adapter : notasAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        listaDeNotas.add(Notas(1,"Título", "Descripción"))
        listaDeNotas.add(Notas(2,"Título", "Descripción"))
        listaDeNotas.add(Notas(3,"Título", "Descripción"))

        adapter = notasAdapter(this,listaDeNotas)
        binding.listView.adapter = adapter
        */

        binding.fab.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }


        cargarQuery("%")



    }

    override fun onResume() {
        super.onResume()
        cargarQuery("%")
    }

    fun cargarQuery(titulo: String) {
        var baseDatos = BDManager(this)
        val columnas = arrayOf("ID", "Titulo", "Descripcion")
        val selectionArgs = arrayOf(titulo)
        val cursor = baseDatos.query(columnas, "Titulo like ?", selectionArgs, "Titulo")
        listaDeNotas.clear()
        if (cursor.moveToFirst()) {
            do {
                val ID = cursor.getInt(cursor.getColumnIndex("ID"))
                val titulo = cursor.getString(cursor.getColumnIndex("Titulo"))
                val descripcion = cursor.getString(cursor.getColumnIndex("Descripcion"))
                listaDeNotas.add(Notas(ID, titulo, descripcion))
            } while (cursor.moveToNext())
        }
        adapter = notasAdapter(this, listaDeNotas)
        val listView: ListView = findViewById(R.id.listView)
        listView.adapter = adapter
        adapter!!.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        val buscar = menu!!.findItem(R.id.app_bar_search).actionView as SearchView
        val manejador = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        buscar.setSearchableInfo(manejador.getSearchableInfo(componentName))
        buscar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(applicationContext,query,Toast.LENGTH_LONG).show()
                cargarQuery("%$query%")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_agregar ->{
                val intent = Intent(this,AddActivity::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }



}