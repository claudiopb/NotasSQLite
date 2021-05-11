package com.cpb.notassqlite

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.cpb.notassqlite.databinding.MoldeNotasBinding

class notasAdapter (context: Context,var listaDeNotas:ArrayList<Notas>): BaseAdapter(){

    var contexto : Context? = context

    override fun getCount(): Int {
        return listaDeNotas.size
    }

    override fun getItem(position: Int): Any {
        return listaDeNotas[position]
    }

    override fun getItemId(position: Int): Long {
     return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val nota = listaDeNotas[position]


        val inflater = contexto!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val miVista = MoldeNotasBinding.inflate(inflater,parent,false)
        val view = miVista.root

        miVista.textViewTitulo.text = nota.titulo
        miVista.textViewContenido.text = nota.descripcion

        miVista.imageViewBorrar.setOnClickListener {
            val dbManager = BDManager(this.contexto!!)
            val  selectionArgs = arrayOf(nota.notaID.toString())
            dbManager.borrar("ID = ?", selectionArgs)
            //MainActivity().cargarQuery("%")
            miVista.root
        }

        miVista.imageViewEditar.setOnClickListener {
            val intent = Intent(contexto, AddActivity::class.java)
            intent.putExtra("ID", nota.notaID)
            intent.putExtra("titulo", nota.titulo)
            intent.putExtra("descrip", nota.descripcion)
            contexto!!.startActivity(intent)
        }

        return view


    }



}