package com.cpb.notassqlite

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.cpb.notassqlite.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {

    var id = 0
    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            val bundle: Bundle? = intent.extras
            id = bundle!!.getInt("ID", 0)
            if (id !=0) {
                binding.editTextTitulo.setText(bundle.getString("titulo"))
                binding.editTextDescripcion.setText(bundle.getString("descrip"))
            }
        } catch (e: Exception) {

        }

    }
     fun btnAdd(view: View){
         val baseDatos = BDManager(this)
         val values = ContentValues()
         val editTextTitulo : EditText = findViewById(R.id.editTextTitulo)
         val editTextContenido : EditText = findViewById(R.id.editTextDescripcion)
         values.put("Titulo", editTextTitulo.text.toString())
         values.put("Descripcion", editTextContenido.text.toString())

         if (id == 0) {
             val ID = baseDatos.insert(values)
             if (ID > 0) {
                 Toast.makeText(this, "Nota agregada correctamente", Toast.LENGTH_LONG).show()
                 finish()
             } else {
                 Toast.makeText(this, "Nota no agregada", Toast.LENGTH_LONG).show()
             }
         } else {
             val selectionArgs = arrayOf(id.toString())
             val ID = baseDatos.actualizar(values, "ID = ?", selectionArgs)
             if (ID > 0) {
                 Toast.makeText(this, "Nota actualizada correctamente", Toast.LENGTH_LONG).show()
                 finish()
             } else {
                 Toast.makeText(this, "Nota no actualizada", Toast.LENGTH_LONG).show()
             }
         }
     }
}