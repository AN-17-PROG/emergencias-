# Sistema de Gestión de Emergencias Médicas (concurrencia en Java)

Simulación concurrente de un centro de emergencias: los operadores reciben llamadas,
los despachadores asignan ambulancia + médico + equipo, y un monitor muestra el
estado en tiempo real.

## Requisitos
- JDK 11 o superior
- NetBeans (opcional)

## Cómo ejecutar

**Con NetBeans:** abrir el proyecto `emergencias`, clic derecho sobre `Main.java`
y elegir *Run File* (Shift + F6).

**Con la línea de comandos** (desde la carpeta del proyecto):

Windows (PowerShell):

```
javac -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
java -cp out Main
```

Linux / Mac:

```
find src -name "*.java" > fuentes.txt
javac -d out @fuentes.txt
java -cp out Main
```

## Configuración
En `Main.java` se puede cambiar: `numOperadores`, `numDespachadores`,
`numRecursos`, `segundos` y `conAverias` (simula recursos que se dañan).

## Estructura
- `modelo`: Emergencia, Gravedad, Estado, Recurso, Asignacion
- `cola`: ColaEmergencias (prioridad con envejecimiento, lock + condition)
- `recursos`: GestorRecursos (reserva todo-o-nada con un solo lock)
- `hilos`: Operador, Despachador, Averias
- `comunicacion`: BusEventos (patrón Observer asíncrono), Evento, Observador
- `monitor`: Monitor, Estadisticas, RegistroConsola
- `hospital`: Hospital (observador)

## Mecanismos de concurrencia
- ExecutorService (pool de hilos)
- ReentrantLock con Condition (cola y gestor de recursos)
- Variables atómicas (id de emergencias y estadísticas)
- Reserva todo-o-nada y backoff aleatorio (evitan deadlock y livelock)
- Aging: la prioridad sube con el tiempo de espera (evita starvation)

## Integrantes y contribución
- (Jose Daniel Novoa Hernandez): (una sola persona)
