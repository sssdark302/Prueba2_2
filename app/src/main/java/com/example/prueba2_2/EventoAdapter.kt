package com.example.prueba2_2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase

class EventoAdapter(
    private val eventos: MutableList<Evento>,
    private val onEdit: (Evento) -> Unit
) : RecyclerView.Adapter<EventoAdapter.EventoViewHolder>() {

    inner class EventoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(evento: Evento) {
            itemView.txtNombre.text = evento.nombre
            itemView.txtDescripcion.text = evento.descripcion
            itemView.txtPrecio.text = "${evento.precio} €"

            // Botón Editar
            itemView.btnEdit.setOnClickListener {
                onEdit(evento) // Llama a la función de edición
            }

            // Botón Eliminar
            itemView.btnDelete.setOnClickListener {
                deleteEvento(evento)
            }
        }

        private fun deleteEvento(evento: Evento) {
            evento.id?.let {
                FirebaseDatabase.getInstance().getReference("eventos").child(it).removeValue()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(itemView.context, "Evento eliminado", Toast.LENGTH_SHORT).show()
                            eventos.remove(evento)
                            notifyDataSetChanged()
                        } else {
                            Toast.makeText(itemView.context, "Error al eliminar", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_evento, parent, false)
        return EventoViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventoViewHolder, position: Int) {
        holder.bind(eventos[position])
    }

    override fun getItemCount(): Int = eventos.size
}