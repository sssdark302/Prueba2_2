package com.example.prueba2_2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EventFormActivity : AppCompatActivity() {

    private lateinit var edtNombre: EditText
    private lateinit var edtDescripcion: EditText
    private lateinit var edtDireccion: EditText
    private lateinit var edtPrecio: EditText
    private lateinit var edtFecha: EditText
    private lateinit var edtAforo: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnCerrar: Button

    private val dbHelper = DatabaseHelper()

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

        // Configurar botón guardar
        btnGuardar.setOnClickListener {
            val evento = mapOf(
                "nombre" to edtNombre.text.toString(),
                "descripcion" to edtDescripcion.text.toString(),
                "direccion" to edtDireccion.text.toString(),
                "precio" to edtPrecio.text.toString().toDouble(),
                "fecha" to edtFecha.text.toString(),
                "aforo" to edtAforo.text.toString().toInt()
            )

            dbHelper.addEvento(evento) { success ->
                if (success) {
                    Toast.makeText(this, "Evento guardado exitosamente", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error al guardar el evento", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Configurar botón cerrar
        btnCerrar.setOnClickListener { finish() }
    }
}
