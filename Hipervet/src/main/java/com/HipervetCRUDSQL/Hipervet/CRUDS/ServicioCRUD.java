package com.HipervetCRUDSQL.Hipervet.CRUDS;

import com.HipervetCRUDSQL.Hipervet.Conexion.ServicioDAO;
import com.HipervetCRUDSQL.Hipervet.Entidades.Servicio;

import java.util.List;
import java.util.Scanner;

public class ServicioCRUD {
    private ServicioDAO servicioDAO;

    public ServicioCRUD() {
        this.servicioDAO = new ServicioDAO();
    }

    public void realizarCRUD() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- CRUD de Servicio ---");
            System.out.println("1. Crear Servicio");
            System.out.println("2. Leer Servicios");
            System.out.println("3. Actualizar Servicio");
            System.out.println("4. Eliminar Servicio");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1: // Crear Servicio
                    Servicio nuevoServicio = new Servicio();
                    System.out.print("Ingrese Código de Servicio: ");
                    int codigoServicio = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer

                    if (servicioDAO.obtenerServicioPorId(codigoServicio) != null) {
                        System.out.println("El código de servicio ya está en uso. Por favor, ingrese uno diferente.");
                        break;
                    }
                    nuevoServicio.setCodigoServicio(codigoServicio); // Asignar el código ingresado
                    System.out.print("Ingrese Descripción del Servicio: ");
                    nuevoServicio.setDescripcion(scanner.nextLine());
                    System.out.print("Ingrese Tipo de Servicio: ");
                    nuevoServicio.setTipo(scanner.nextLine());
                    System.out.print("Ingrese Precio del Servicio: ");
                    nuevoServicio.setPrecio(scanner.nextDouble());
                    scanner.nextLine(); // Limpiar el buffer

                    if (servicioDAO.crearServicio(nuevoServicio)) {
                        System.out.println("Servicio creado exitosamente.");
                    } else {
                        System.out.println("Error al crear el servicio.");
                    }
                    break;

                case 2: // Leer Servicios
                    List<Servicio> servicios = servicioDAO.obtenerServicios();
                    System.out.println("\n--- Lista de Servicios ---");
                    for (Servicio servicio : servicios) {
                        System.out.println(servicio); // Mostrar detalles del servicio
                    }
                    break;

                case 3: // Actualizar Servicio
                    System.out.print("Ingrese el código del servicio a actualizar: ");
                    int codigoActualizar = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer
                    Servicio servicioActualizar = servicioDAO.obtenerServicioPorId(codigoActualizar);
                    if (servicioActualizar != null) {
                        System.out.println("Detalles actuales del servicio: " + servicioActualizar);
                        System.out.print("Ingrese Nueva Descripción (dejar vacío para no cambiar): ");
                        String nuevaDescripcion = scanner.nextLine();
                        if (!nuevaDescripcion.isEmpty()) servicioActualizar.setDescripcion(nuevaDescripcion);
                        System.out.print("Ingrese Nuevo Tipo (dejar vacío para no cambiar): ");
                        String nuevoTipo = scanner.nextLine();
                        if (!nuevoTipo.isEmpty()) servicioActualizar.setTipo(nuevoTipo);
                        System.out.print("Ingrese Nuevo Precio (dejar vacío para no cambiar): ");
                        String nuevoPrecioStr = scanner.nextLine();
                        if (!nuevoPrecioStr.isEmpty()) servicioActualizar.setPrecio(Double.parseDouble(nuevoPrecioStr));

                        if (servicioDAO.actualizarServicio(servicioActualizar)) {
                            System.out.println("Servicio actualizado exitosamente.");
                        } else {
                            System.out.println("Error al actualizar el servicio.");
                        }
                    } else {
                        System.out.println("Servicio no encontrado.");
                    }
                    break;

                case 4: // Eliminar Servicio
                    System.out.print("Ingrese el código del servicio a eliminar: ");
                    int codigoEliminar = scanner.nextInt();
                    if (servicioDAO.eliminarServicio(codigoEliminar)) {
                        System.out.println("Servicio eliminado exitosamente.");
                    } else {
                        System.out.println("Error al eliminar el servicio.");
                    }
                    break;

                case 5: // Salir
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        } while (opcion != 5);

        scanner.close();
    }
}
