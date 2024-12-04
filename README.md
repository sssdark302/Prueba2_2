# Documentación de la Aplicación de Gestión de Eventos

## Descripción General

- Hecho por Augusto Perrone
- Link al repo: https://github.com/sssdark302/Prueba2_2.git

La aplicación permite gestionar eventos a través de las siguientes funcionalidades:
- **Crear eventos**: Permite registrar nuevos eventos con detalles como nombre, descripción, dirección, precio, fecha y aforo.
- **Editar eventos**: Los usuarios pueden modificar los datos de eventos existentes.
- **Eliminar eventos**: Los eventos pueden ser eliminados permanentemente.
- **Visualizar eventos**: Los eventos se muestran en una lista que se actualiza automáticamente.

La aplicación está desarrollada en **Kotlin** y utiliza **Firebase Realtime Database** como backend para almacenar y sincronizar los datos de los eventos.

---

## Clases Principales

### 1. `MainActivity`

#### Descripción
Es la actividad principal que muestra la lista de eventos y permite acceder a las funcionalidades de crear, editar y eliminar eventos.

#### Métodos Clave
- **`onCreate`**:
    - Configura el RecyclerView para mostrar la lista de eventos.
    - Asocia un adaptador al RecyclerView.
    - Configura el botón flotante para redirigir a la pantalla de creación de eventos.

- **`onResume`**:
    - Recarga la lista de eventos cada vez que el usuario regresa a esta actividad.

- **`loadEventos`**:
    - Carga los datos de los eventos desde Firebase Realtime Database.
    - Asocia cada evento con su ID único y lo añade a la lista local para mostrarlo.

---

### 2. `EventoAdapter`

#### Descripción
Es el adaptador utilizado para vincular los datos de la lista de eventos con el RecyclerView.

#### Métodos Clave
- **`onCreateViewHolder`**:
    - Infla el diseño del elemento individual de la lista (`item_evento.xml`).

- **`onBindViewHolder`**:
    - Vincula los datos de un evento específico con las vistas del elemento en el RecyclerView.

- **`deleteEvento`**:
    - Elimina un evento de Firebase y lo elimina de la lista local.

- **`btnEdit`**:
    - Redirige a la pantalla de edición de eventos (`EventFormActivity`) pasando el ID del evento.

---

### 3. `EventFormActivity`

#### Descripción
Es la actividad encargada de gestionar el formulario para crear o editar eventos.

#### Métodos Clave
- **`onCreate`**:
    - Configura las vistas del formulario.
    - Detecta si el usuario está creando o editando un evento basado en el ID pasado como extra en el intent.
    - Carga los datos del evento en el formulario si se está editando.

- **`loadEvento`**:
    - Recupera los datos de un evento desde Firebase para mostrarlos en el formulario.

- **`saveEvento`**:
    - Guarda un nuevo evento o actualiza uno existente según el caso.

---

### 4. `Evento`

#### Descripción
Es una clase de datos que representa un evento en la aplicación.

#### Propiedades
- **`id`**: Identificador único del evento en Firebase.
- **`nombre`**: Nombre del evento.
- **`descripcion`**: Descripción detallada del evento.
- **`direccion`**: Ubicación del evento.
- **`precio`**: Precio del evento.
- **`fecha`**: Fecha del evento.
- **`aforo`**: Número máximo de asistentes permitidos.

## Flujo de Trabajo

1. **Inicio de la Aplicación**:
    - `MainActivity` carga los eventos desde Firebase y los muestra en un RecyclerView.

2. **Crear un Evento**:
    - El usuario pulsa el botón flotante para ir a `EventFormActivity`.
    - Completa el formulario y pulsa "Guardar".
    - Los datos se envían a Firebase y se actualizan en la lista.

3. **Editar un Evento**:
    - El usuario pulsa el botón "Editar" en un evento del listado.
    - `EventFormActivity` carga los datos del evento y permite su modificación.
    - Los cambios se guardan en Firebase.

4. **Eliminar un Evento**:
    - El usuario pulsa el botón "Eliminar".
    - El evento se elimina de Firebase y de la lista local.



