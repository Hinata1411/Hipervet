package com.HipervetCRUDSQL.Hipervet.CRUDS;

import com.HipervetCRUDSQL.Hipervet.Conexion.FichaMascotaDAO;
import com.HipervetCRUDSQL.Hipervet.Entidades.FichaMascota;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class FichaMascotaCRUD {
    private FichaMascotaDAO fichaMascotaDAO;

    public FichaMascotaCRUD() {
        this.fichaMascotaDAO = new FichaMascotaDAO();
    }

    public void realizarCRUD() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- CRUD de Ficha de Mascota ---");
            System.out.println("1. Crear Ficha de Mascota");
            System.out.println("2. Leer Fichas de Mascota");
            System.out.println("3. Actualizar Ficha de Mascota");
            System.out.println("4. Eliminar Ficha de Mascota");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1: // Crear Ficha de Mascota
                    FichaMascota nuevaFicha = new FichaMascota();
                    System.out.print("Ingrese Número de Ficha: ");
                    int numeroFicha = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer

                    if (fichaMascotaDAO.obtenerFichaMascotaPorId(numeroFicha) != null) {
                        System.out.println("El número de ficha ya está en uso. Por favor, ingrese uno diferente.");
                        break;
                    }
                    nuevaFicha.setNumeroFicha(numeroFicha); // Asignar el número ingresado
                    System.out.print("Ingrese Código de Especie: ");
                    nuevaFicha.setCodigoEspecie(scanner.nextInt());
                    scanner.nextLine(); // Limpiar el buffer
                    System.out.print("Ingrese Código de Raza: ");
                    nuevaFicha.setCodigoRaza(scanner.nextInt());
                    scanner.nextLine(); // Limpiar el buffer
                    System.out.print("Ingrese Nombre de la Mascota: ");
                    nuevaFicha.setNombre(scanner.nextLine());
                    System.out.print("Ingrese Fecha de Nacimiento (YYYY-MM-DD): ");
                    String fechaNacimientoStr = scanner.nextLine();
                    nuevaFicha.setFechaNacimiento(Date.valueOf(fechaNacimientoStr).toLocalDate());
                    System.out.print("Ingrese Talla de la Mascota: ");
                    nuevaFicha.setTalla(scanner.nextLine());
                    System.out.print("Ingrese Género de la Mascota: ");
                    nuevaFicha.setGenero(scanner.nextLine());

                    if (fichaMascotaDAO.crearFichaMascota(nuevaFicha)) {
                        System.out.println("Ficha de mascota creada exitosamente.");
                    } else {
                        System.out.println("Error al crear la ficha de mascota.");
                    }
                    break;

                case 2: // Leer Fichas de Mascota
                    List<FichaMascota> fichas = fichaMascotaDAO.obtenerFichasMascotas();
                    System.out.println("\n--- Lista de Fichas de Mascota ---");
                    for (FichaMascota ficha : fichas) {
                        System.out.println(ficha); // Mostrar detalles de la ficha
                    }
                    break;

                case 3: // Actualizar Ficha de Mascota
                    System.out.print("Ingrese el número de ficha a actualizar: ");
                    int numeroActualizar = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer
                    FichaMascota fichaActualizar = fichaMascotaDAO.obtenerFichaMascotaPorId(numeroActualizar);
                    if (fichaActualizar != null) {
                        System.out.println("Detalles actuales de la ficha: " + fichaActualizar);
                        System.out.print("Ingrese Nuevo Código de Especie (dejar vacío para no cambiar): ");
                        String nuevoCodigoEspecieStr = scanner.nextLine();
                        if (!nuevoCodigoEspecieStr.isEmpty()) {
                            fichaActualizar.setCodigoEspecie(Integer.parseInt(nuevoCodigoEspecieStr));
                        }
                        System.out.print("Ingrese Nuevo Código de Raza (dejar vacío para no cambiar): ");
                        String nuevoCodigoRazaStr = scanner.nextLine();
                        if (!nuevoCodigoRazaStr.isEmpty()) {
                            fichaActualizar.setCodigoRaza(Integer.parseInt(nuevoCodigoRazaStr));
                        }
                        System.out.print("Ingrese Nuevo Nombre (dejar vacío para no cambiar): ");
                        String nuevoNombre = scanner.nextLine();
                        if (!nuevoNombre.isEmpty()) fichaActualizar.setNombre(nuevoNombre);
                        System.out.print("Ingrese Nueva Fecha de Nacimiento (YYYY-MM-DD, dejar vacío para no cambiar): ");
                        String nuevaFechaNacimientoStr = scanner.nextLine();
                        if (!nuevaFechaNacimientoStr.isEmpty()) {
                            fichaActualizar.setFechaNacimiento(Date.valueOf(nuevaFechaNacimientoStr).toLocalDate());
                        }
                        System.out.print("Ingrese Nueva Talla (dejar vacío para no cambiar): ");
                        String nuevaTalla = scanner.nextLine();
                        if (!nuevaTalla.isEmpty()) fichaActualizar.setTalla(nuevaTalla);
                        System.out.print("Ingrese Nuevo Género (dejar vacío para no cambiar): ");
                        String nuevoGenero = scanner.nextLine();
                        if (!nuevoGenero.isEmpty()) fichaActualizar.setGenero(nuevoGenero);

                        if (fichaMascotaDAO.actualizarFichaMascota(fichaActualizar)) {
                            System.out.println("Ficha de mascota actualizada exitosamente.");
                        } else {
                            System.out.println("Error al actualizar la ficha de mascota.");
                        }
                    } else {
                        System.out.println("Ficha de mascota no encontrada.");
                    }
                    break;

                case 4: // Eliminar Ficha de Mascota
                    System.out.print("Ingrese el número de ficha a eliminar: ");
                    int numeroEliminar = scanner.nextInt();
                    if (fichaMascotaDAO.eliminarFichaMascota(numeroEliminar)) {
                        System.out.println("Ficha de mascota eliminada exitosamente.");
                    } else {
                        System.out.println("Error al eliminar la ficha de mascota.");
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