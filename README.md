<img src="media/FlowIn/FlowIn.png" alt="FlowIn Logo" width="300"/>

Tu nueva red para compartir música en tiempo real.



**Tu nueva red para compartir música en tiempo real**

> Proyecto desarrollado para el curso **CS2031 – Desarrollo Basado en Plataforma**

> **Integrantes**:  
> - Benites Camacho, Alonso Aarón
> - Ladron de Guevara Aguirre, Diego Abraham 
> - Janampa Salvatierra, Raúl

---

## ❗ Identificación del Problema o Necesidad

### Descripción del Problema

En la actualidad, muchas plataformas de música carecen de una dimensión social activa. Los usuarios suelen escuchar música de forma individual, sin posibilidad de compartir en tiempo real con otros ni descubrir música de manera colaborativa.

### Justificación

Es importante ofrecer a los usuarios una experiencia musical más interactiva y comunitaria, donde compartir gustos, descubrir nueva música y conocer personas sea parte del valor agregado. FlowIn busca posicionarse como la solución que une streaming, redes sociales y escucha colectiva.

---

## 💡 Descripción de la Solución

### Funcionalidades Implementadas

- **Perfil de Usuario:** Visualización de gustos, artistas favoritos y tipo de oyente.
- **Salas Virtuales:** Espacios para compartir música en tiempo real con otros usuarios.
- **Chats Temáticos:** Canales de conversación enfocados en géneros, artistas o exploración musical aleatoria.
- **Recomendaciones Inteligentes:** Sugerencias de usuarios, salas y música según preferencias.
- **Login y Configuración Inicial:** Conexión con Spotify/Apple Music o selección manual de gustos.
- **Exploración de Salas:** Filtrado por género/artista o acceso aleatorio a salas activas.

### Tecnologías Utilizadas

- **Frontend:** React / Next.js
- **Estilos:** TailwindCSS
- **Backend & Auth:** Firebase, Firestore
- **APIs Externas:** Spotify API, Apple Music API
- **Pruebas:** Unit, Integration, End-to-End y Usabilidad
- **Servicios en Tiempo Real:** Base de datos en vivo para salas, chats y usuarios conectados

---

## 🗂️ Modelo de Entidades

### Diagrama

> Agregamos aqui el driagrama

### Descripción de Entidades

- **Usuario**: ID, nombre, email, contraseña, gustos musicales, tipo de oyente, artistas favoritos.
- **Sala**: ID, nombre, categoría (género/artista), estado, usuarios activos, canciones compartidas.
- **Canción**: ID, título, artista, álbum, duración, fuente (Spotify, Apple Music, manual).
- **Mensaje**: ID, contenido, autor, timestamp.
- **Recomendación**: ID, preferencias del usuario, perfiles similares, salas sugeridas.

### Relaciones

- Un usuario puede crear y unirse a múltiples salas (Many to Many).
- Una sala contiene muchas canciones y usuarios (Many to Many).
- Los usuarios se comunican mediante mensajes en salas (One to Many).
- Cada recomendación está asociada a un único usuario (One to One).

---
