package com.example.prueba2_2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val dbHelper = DatabaseHelper()
    private val eventoList = mutableListOf<Evento>()
    private lateinit var adapter: EventoAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var fabAddEvent: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Vincular vistas con findViewById
        recyclerView = findViewById(R.id.recyclerView)
        fabAddEvent = findViewById(R.id.fab_add_event)

        // Configurar RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = EventoAdapter(eventoList) { evento ->
            // Aquí puedes implementar la acción de edición si es necesario
            Toast.makeText(this, "Editar evento: ${evento.nombre}", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = adapter

        // Configurar botón flotante
        fabAddEvent.setOnClickListener {
            val intent = Intent(this, EventFormActivity::class.java)
            startActivity(intent)
        }

        // Cargar eventos desde Realtime Database
        loadEventos()
    }

    private fun loadEventos() {
        dbHelper.getEventos { eventos ->
            eventoList.clear()
            eventos.forEach { eventoData ->
                val evento = Evento(
                    id = eventoData["id"] as? String,
                    nombre = eventoData["nombre"] as String,
                    descripcion = eventoData["descripcion"] as String,
                    direccion = eventoData["direccion"] as String,
                    precio = (eventoData["precio"] as Number).toDouble(),
                    fecha = eventoData["fecha"] as String,
                    aforo = (eventoData["aforo"] as Number).toInt()
                )
                eventoList.add(evento)
            }
            adapter.notifyDataSetChanged()
        }
    }
}
