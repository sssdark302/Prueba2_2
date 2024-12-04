package com.example.prueba2_2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prueba2.DatabaseHelper
import com.example.prueba2_2.EventoAdapter

class MainActivity : AppCompatActivity() {

    private val dbHelper = DatabaseHelper()
    private val eventoList = mutableListOf<Evento>()
    private lateinit var adapter: EventoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configurar RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = EventoAdapter(eventoList)
        recyclerView.adapter = adapter

        // Configurar botón flotante
        fab_add_event.setOnClickListener {
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
