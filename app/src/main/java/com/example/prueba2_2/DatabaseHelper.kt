package com.example.prueba2_2

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DatabaseHelper {

    private val database = FirebaseDatabase.getInstance()
    private val eventosRef = database.getReference("eventos")

    fun getEventos(callback: (List<Map<String, Any>>) -> Unit) {
        eventosRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val eventos = mutableListOf<Map<String, Any>>()
                snapshot.children.forEach { data ->
                    eventos.add(data.value as Map<String, Any>)
                }
                callback(eventos)
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar errores de lectura
                callback(emptyList())
            }
        })
    }

    fun addEvento(evento: Map<String, Any>, callback: (Boolean) -> Unit) {
        val newRef = eventosRef.push() // Crear una referencia con ID único
        newRef.setValue(evento).addOnCompleteListener { task ->
            callback(task.isSuccessful)
        }
    }

    fun updateEvento(eventoId: String, evento: Map<String, Any>, callback: (Boolean) -> Unit) {
        eventosRef.child(eventoId).setValue(evento).addOnCompleteListener { task ->
            callback(task.isSuccessful)
        }
    }

    fun deleteEvento(eventoId: String, callback: (Boolean) -> Unit) {
        eventosRef.child(eventoId).removeValue().addOnCompleteListener { task ->
            callback(task.isSuccessful)
        }
    }
}