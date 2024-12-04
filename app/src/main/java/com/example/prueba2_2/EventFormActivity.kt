package com.example.prueba2_2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class EventFormActivity : AppCompatActivity() {

    // Declaración de vistas
    private lateinit var edtNombre: EditText
    private lateinit var edtDescripcion: EditText
    private lateinit var edtDireccion: EditText
    private lateinit var edtPrecio: EditText
    private lateinit var edtFecha: EditText
    private lateinit var edtAforo: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnCerrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_form)

        // Vincular vistas con findViewById
        edtNombre = findViewById(R.id.edtNombre)
        edtDescripcion = findViewById(R.id.edtDescripcion)
        edtDireccion = findViewById(R.id.edtDireccion)
        edtPrecio = findViewById(R.id.edtPrecio)
        edtFecha = findViewById(R.id.edtFecha)
        edtAforo = findViewById(R.id.edtAforo)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnCerrar = findViewById(R.id.btnCerrar)

        // Obtener el ID del evento (si lo hay)
        val eventoId = intent.getStringExtra("evento_id")
        if (eventoId != null) {
            loadEvento(eventoId)
        }

        // Configurar el botón guardar
        btnGuardar.setOnClickListener {
            saveEvento(eventoId)
        }

        // Configurar el botón cerrar
        btnCerrar.setOnClickListener {
            finish()
        }
    }

    private fun loadEvento(eventoId: String) {
        FirebaseDatabase.getInstance().getReference("eventos").child(eventoId)
            .get().addOnSuccessListener { snapshot ->
                val evento = snapshot.getValue(Evento::class.java)
                if (evento != null) {
                    edtNombre.setText(evento.nombre)
                    edtDescripcion.setText(evento.descripcion)
                    edtDireccion.setText(evento.direccion)
                    edtPrecio.setText(evento.precio.toString())
                    edtFecha.setText(evento.fecha)
                    edtAforo.setText(evento.aforo.toString())
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Error al cargar el evento", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveEvento(eventoId: String?) {
        val evento = mapOf(
            "nombre" to edtNombre.text.toString(),
            "descripcion" to edtDescripcion.text.toString(),
            "direccion" to edtDireccion.text.toString(),
            "precio" to edtPrecio.text.toString().toDoubleOrNull(),
            "fecha" to edtFecha.text.toString(),
            "aforo" to edtAforo.text.toString().toIntOrNull()
        )

        val dbRef = FirebaseDatabase.getInstance().getReference("eventos")

        // Diferenciar entre crear y editar
        if (eventoId != null) {
            // Editar evento existente
            dbRef.child(eventoId).updateChildren(evento).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Evento actualizado", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            // Crear nuevo evento
            dbRef.push().setValue(evento).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Evento guardado", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
