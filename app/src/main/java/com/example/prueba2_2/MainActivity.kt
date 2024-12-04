package com.example.prueba2_2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.FirebaseDatabase

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

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = EventoAdapter(this, eventoList) { evento ->
            // Acción personalizada para editar (si es necesario)
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
        FirebaseDatabase.getInstance().getReference("eventos")
            .get()
            .addOnSuccessListener { snapshot ->
                eventoList.clear()
                snapshot.children.forEach { child ->
                    val evento = child.getValue(Evento::class.java)
                    evento?.id = child.key // Establecer el ID desde la clave de Firebase
                    if (evento != null) {
                        eventoList.add(evento)
                    }
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al cargar eventos", Toast.LENGTH_SHORT).show()
            }
    }
    override fun onResume() {
        super.onResume()
        loadEventos()
    }
}

