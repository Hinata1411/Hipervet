package com.HipervetCRUDSQL.Hipervet.CRUDS;
import com.HipervetCRUDSQL.Hipervet.Conexion.PersonaDAO;
import com.HipervetCRUDSQL.Hipervet.Entidades.Persona;

import java.util.List;
import java.util.Scanner;

public class PersonaCRUD {
    private PersonaDAO personaDAO;
    public PersonaCRUD() {
        this.personaDAO = new PersonaDAO();
    }
    public void realizarCRUD() {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("\n--- CRUD de Persona ---");
            System.out.println("1. Crear Persona");
            System.out.println("2. Leer Personas");
            System.out.println("3. Actualizar Persona");
            System.out.println("4. Eliminar Persona");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();
//
//            switch (opcion) {
//                case 1: // Crear Persona
//                    Persona nuevaPersona = new Persona();
//                    System.out.print("Ingrese Código de Persona: ");
//                    int codigoPersona = scanner.nextInt();
//                    scanner.nextLine();
//
//                    if (personaDAO.obtenerPersonaPorId(codigoPersona) != null) {
//                        System.out.println("El código de persona ya está en uso. Por favor, ingrese uno diferente.");
//                        break;
//                    }
//                    nuevaPersona.setCodigoPersona(codigoPersona); // Asignar el código ingresado
//                    System.out.print("Ingrese Primer Nombre: ");
//                    nuevaPersona.setPrimerNombre(scanner.nextLine());
//                    System.out.print("Ingrese Segundo Nombre: ");
//                    nuevaPersona.setSegundoNombre(scanner.nextLine());
//                    System.out.print("Ingrese Tercer Nombre: ");
//                    nuevaPersona.setTercerNombre(scanner.nextLine());
//                    System.out.print("Ingrese Primer Apellido: ");
//                    nuevaPersona.setPrimerApellido(scanner.nextLine());
//                    System.out.print("Ingrese Segundo Apellido: ");
//                    nuevaPersona.setSegundoApellido(scanner.nextLine());
//                    System.out.print("Ingrese Tercer Apellido: ");
//                    nuevaPersona.setTercerApellido(scanner.nextLine());
//                    System.out.print("Ingrese Tipo de Persona: ");
//                    nuevaPersona.setTipoPersona(scanner.nextLine());
//                    System.out.print("Ingrese Razón Social: ");
//                    nuevaPersona.setRazonSocial(scanner.nextLine());
//                    System.out.print("Ingrese Fecha de Nacimiento (YYYY-MM-DD): ");
//                    String fechaNacimientoStr = scanner.nextLine();
//                    nuevaPersona.setFechaNacimiento(java.sql.Date.valueOf(fechaNacimientoStr));
//                    System.out.print("Ingrese Género: ");
//                    nuevaPersona.setGenero(scanner.nextLine());
//
//                    if (personaDAO.crearPersona(nuevaPersona)) {
//                        System.out.println("Persona creada exitosamente.");
//                    } else {
//                        System.out.println("Error al crear la persona.");
//                    }
//                    break;
//
//                case 2: // Leer Personas
//                    List<Persona> personas = personaDAO.obtenerPersonas();
//                    System.out.println("\n--- Lista de Personas ---");
//                    for (Persona persona : personas) {
//                        System.out.println(persona); // Mostrar detalles de la persona
//                    }
//                    break;
//
//                case 3: // Actualizar Persona
//                    System.out.print("Ingrese el código de la persona a actualizar: ");
//                    int codigoActualizar = scanner.nextInt();
//                    scanner.nextLine(); // Consumir el salto de línea
//                    Persona personaActualizar = personaDAO.obtenerPersonaPorId(codigoActualizar);
//                    if (personaActualizar != null) {
//                        System.out.println("Detalles actuales de la persona: " + personaActualizar);
//                        System.out.print("Ingrese Nuevo Primer Nombre (dejar vacío para no cambiar): ");
//                        String nuevoPrimerNombre = scanner.nextLine();
//                        if (!nuevoPrimerNombre.isEmpty()) personaActualizar.setPrimerNombre(nuevoPrimerNombre);
//                        System.out.print("Ingrese Nuevo Segundo Nombre (dejar vacío para no cambiar): ");
//                        String nuevoSegundoNombre = scanner.nextLine();
//                        if (!nuevoSegundoNombre.isEmpty()) personaActualizar.setSegundoNombre(nuevoSegundoNombre);
//                        System.out.print("Ingrese Nuevo Tercer Nombre (dejar vacío para no cambiar): ");
//                        String nuevoTercerNombre = scanner.nextLine();
//                        if (!nuevoTercerNombre.isEmpty()) personaActualizar.setTercerNombre(nuevoTercerNombre);
//                        System.out.print("Ingrese Nuevo Primer Apellido (dejar vacío para no cambiar): ");
//                        String nuevoPrimerApellido = scanner.nextLine();
//                        if (!nuevoPrimerApellido.isEmpty()) personaActualizar.setPrimerApellido(nuevoPrimerApellido);
//                        System.out.print("Ingrese Nuevo Segundo Apellido (dejar vacío para no cambiar): ");
//                        String nuevoSegundoApellido = scanner.nextLine();
//                        if (!nuevoSegundoApellido.isEmpty()) personaActualizar.setSegundoApellido(nuevoSegundoApellido);
//                        System.out.print("Ingrese Nuevo Tercer Apellido (dejar vacío para no cambiar): ");
//                        String nuevoTercerApellido = scanner.nextLine();
//                        if (!nuevoTercerApellido.isEmpty()) personaActualizar.setTercerApellido(nuevoTercerApellido);
//                        System.out.print("Ingrese Nuevo Tipo de Persona (dejar vacío para no cambiar): ");
//                        String nuevoTipoPersona = scanner.nextLine();
//                        if (!nuevoTipoPersona.isEmpty()) personaActualizar.setTipoPersona(nuevoTipoPersona);
//                        System.out.print("Ingrese Nueva Razón Social (dejar vacío para no cambiar): ");
//                        String nuevaRazonSocial = scanner.nextLine();
//                        if (!nuevaRazonSocial.isEmpty()) personaActualizar.setRazonSocial(nuevaRazonSocial);
//                        System.out.print("Ingrese Nueva Fecha de Nacimiento (YYYY-MM-DD, dejar vacío para no cambiar): ");
//                        String nuevaFechaNacimientoStr = scanner.nextLine();
//                        if (!nuevaFechaNacimientoStr.isEmpty()) {
//                            personaActualizar.setFechaNacimiento(java.sql.Date.valueOf(nuevaFechaNacimientoStr));
//                        }
//                        System.out.print("Ingrese Nuevo Género (dejar vacío para no cambiar): ");
//                        String nuevoGenero = scanner.nextLine();
//                        if (!nuevoGenero.isEmpty()) personaActualizar.setGenero(nuevoGenero);
//
//                        if (personaDAO.actualizarPersona(personaActualizar)) {
//                            System.out.println("Persona actualizada exitosamente.");
//                        } else {
//                            System.out.println("Error al actualizar la persona.");
//                        }
//                    } else {
//                        System.out.println("Persona no encontrada.");
//                    }
//                    break;
//
//                case 4: // Eliminar Persona
//                    System.out.print("Ingrese el código de la persona a eliminar: ");
//                    int codigoEliminar = scanner.nextInt();
//                    if (personaDAO.eliminarPersona(codigoEliminar)) {
//                        System.out.println("Persona eliminada exitosamente.");
//                    } else {
//                        System.out.println("Error al eliminar la persona.");
//                    }
//                    break;
//
//                case 5: // Salir
//                    System.out.println("Saliendo...");
//                    break;
//
//                default:
//                    System.out.println("Opción no válida. Intente de nuevo.");
//                    break;
//            }
          } while (opcion != 5);
//        scanner.close();
    }
}