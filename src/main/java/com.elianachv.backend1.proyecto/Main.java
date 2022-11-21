package com.elianachv.backend1.proyecto;

import com.elianachv.backend1.proyecto.dao.util.ConfiguracionBd;
import com.elianachv.backend1.proyecto.entity.Odontologo;
import com.elianachv.backend1.proyecto.exception.DuplicadoException;
import com.elianachv.backend1.proyecto.exception.NoEncontradoException;
import com.elianachv.backend1.proyecto.service.OdontologoService;
import com.elianachv.backend1.proyecto.service.PacienteService;
import com.elianachv.backend1.proyecto.service.TurnoService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void crearOdontologo(Scanner scanner, OdontologoService odontologoService) throws SQLException, DuplicadoException {

        System.out.print("Digite nombre del odontologo:");
        String nombre = scanner.next();
        System.out.print("Digite apellido del odontologo:");
        String apellido = scanner.next();
        System.out.print("Digite matricula del odontologo:");
        int matricula = scanner.nextInt();

        odontologoService.crearOdontologo(new Odontologo(matricula, nombre, apellido));
        System.out.println("Odontologo creado con éxito");

    }

    public static void modificarOdontologo(Scanner scanner, OdontologoService odontologoService) {

        System.out.print("Digite matricula del odontologo que desea modificar:");
        int matricula = scanner.nextInt();
        System.out.print("Digite nuevo nombre del odontologo:");
        String nombre = scanner.next();
        System.out.print("Digite nuevo apellido del odontologo:");
        String apellido = scanner.next();

        odontologoService.modificarOdontologo(matricula, new Odontologo(matricula, nombre, apellido));
        System.out.println("Odontologo modificado con éxito");

    }

    public static void buscarOdontologo(Scanner scanner, OdontologoService odontologoService) throws NoEncontradoException {

        System.out.print("Digite matricula del odontologo:");
        int matricula = scanner.nextInt();
        Odontologo o = (Odontologo) odontologoService.buscarOdontologo(matricula).getData();

        if (o != null) {
            System.out.println(o);
        } else {
            System.out.println("Odontologo no registra en el sistema");
        }

    }

    public static void eliminarOdontologo(Scanner scanner, OdontologoService odontologoService) throws SQLException {

        System.out.print("Digite matricula del odontologo que desea eliminar:");
        int matricula = scanner.nextInt();
        odontologoService.eliminarOdontologo(matricula);
        System.out.println("Odontologo eliinado del sistema");

    }

    public static void verOdontologos(OdontologoService odontologoService) throws SQLException {
        List<Odontologo> odontologos = (List<Odontologo>) odontologoService.listarOdontologos();
        if (odontologos.size() > 0) {
            System.out.println("Lista de odontologos registrados");
            odontologos.forEach(o -> System.out.println(o));
        } else {
            System.out.println("Todavía no hay odontologos registrados");
        }

    }

    public static void salir() {
        System.out.println("Hasta pronto");
        System.exit(0);
    }

    public static void mostrarMenu() {
        System.out.println("");
        System.out.println("__________________________________________");
        System.out.println("Bienvenido al sistema de agendamiento de citas:");
        System.out.println("0. Salir");
        System.out.println("1. Crear odontologo");
        System.out.println("2. Listar odontologos");
        System.out.println("3. Buscar odontologo");
        System.out.println("4. Modificar odontologo");
        System.out.println("5. Eliminar odontologo");
        System.out.print("Digita tu opcion: ");
    }

    public static void main(String[] args) {

        ConfiguracionBd configuracionBd = new ConfiguracionBd(
                "org.h2.Driver",
                "jdbc:h2:~/test",
                "sa",
                ""
        );

        TurnoService turnoService = new TurnoService(configuracionBd);
        PacienteService pacienteService = new PacienteService(configuracionBd);
        OdontologoService odontologoService = new OdontologoService(configuracionBd);

        Scanner scanner = new Scanner(System.in);

        mostrarMenu();
        int opcion = scanner.nextInt();

        try{
            while (opcion != 0) {

                switch (opcion) {
                    case 1 -> {
                        crearOdontologo(scanner, odontologoService);
                        mostrarMenu();
                        opcion = scanner.nextInt();
                    }
                    case 2 -> {
                        verOdontologos(odontologoService);
                        mostrarMenu();
                        opcion = scanner.nextInt();
                    }
                    case 3 -> {
                        buscarOdontologo(scanner, odontologoService);
                        mostrarMenu();
                        opcion = scanner.nextInt();
                    }
                    case 4 -> {
                        modificarOdontologo(scanner, odontologoService);
                        mostrarMenu();
                        opcion = scanner.nextInt();
                    }
                    case 5 -> {
                        eliminarOdontologo(scanner, odontologoService);
                        mostrarMenu();
                        opcion = scanner.nextInt();
                    }
                    default -> {
                        System.out.println("Opcion invalida");
                        mostrarMenu();
                        opcion = scanner.nextInt();
                    }
                }
            }


        }catch (Exception e){
            System.out.println(e);
        }

        salir();


    }

}