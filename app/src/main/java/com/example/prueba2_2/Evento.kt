package com.example.prueba2_2

data class Evento(
    var id: String? = null,
    var nombre: String = "",
    var descripcion: String = "",
    var direccion: String = "",
    var precio: Double = 0.0,
    var fecha: String = "",
    var aforo: Int = 0
)