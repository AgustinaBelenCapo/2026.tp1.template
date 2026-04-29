package com.bibliotech;

import com.bibliotech.exception.BibliotecaException;
import com.bibliotech.model.CategoriaRecurso;
import com.bibliotech.model.EBook;
import com.bibliotech.model.LibroFisico;
import com.bibliotech.model.Prestamo;
import com.bibliotech.model.Recurso;
import com.bibliotech.model.Socio;
import com.bibliotech.model.SocioDocente;
import com.bibliotech.model.SocioEstudiante;
import com.bibliotech.model.TipoSocio;
import com.bibliotech.repository.ImplementacionPrestamoRepository;
import com.bibliotech.repository.ImplementacionRecursoRepository;
import com.bibliotech.repository.ImplementacionSocioRepository;
import com.bibliotech.repository.PrestamoRepository;
import com.bibliotech.repository.RecursoRepository;
import com.bibliotech.repository.SocioRepository;
import com.bibliotech.service.PrestamoService;
import com.bibliotech.service.PrestamoServiceImpl;
import com.bibliotech.service.RecursoService;
import com.bibliotech.service.RecursoServiceImpl;
import com.bibliotech.service.SocioService;
import com.bibliotech.service.SocioServiceImpl;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        RecursoRepository recursoRepository = new ImplementacionRecursoRepository();
        SocioRepository socioRepository = new ImplementacionSocioRepository();
        PrestamoRepository prestamoRepository = new ImplementacionPrestamoRepository();

        RecursoService recursoService = new RecursoServiceImpl(recursoRepository);
        SocioService socioService = new SocioServiceImpl(socioRepository);
        PrestamoService prestamoService = new PrestamoServiceImpl(recursoRepository, socioRepository, prestamoRepository);

        cargarDatosIniciales(recursoService, socioService);
        ejecutarMenu(recursoService, socioService, prestamoService);
    }

    private static void cargarDatosIniciales(RecursoService recursoService, SocioService socioService) {
        try {
            recursoService.registrarRecurso(new LibroFisico("978-950-00-0001-1", "Patrones de Diseno", "Gamma", 1994,
                    CategoriaRecurso.TECNOLOGIA, "EST-01-A"));
            recursoService.registrarRecurso(new EBook("978-950-00-0002-8", "Arquitectura Limpia", "Robert C. Martin", 2017,
                    CategoriaRecurso.TECNOLOGIA, "PDF"));
            socioService.registrarSocio(new SocioEstudiante(1, "40111222", "Ana Perez", "ana@uni.edu"));
            socioService.registrarSocio(new SocioDocente(2, "22333444", "Carlos Ruiz", "carlos@uni.edu"));
        } catch (BibliotecaException ex) {
            System.err.println("Error cargando datos iniciales: " + ex.getMessage());
        }
    }

    private static void ejecutarMenu(RecursoService recursoService, SocioService socioService, PrestamoService prestamoService) {
        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            String opcion = SCANNER.nextLine().trim();
            try {
                switch (opcion) {
                    case "1" -> altaRecurso(recursoService);
                    case "2" -> altaSocio(socioService);
                    case "3" -> buscarRecursos(recursoService);
                    case "4" -> registrarPrestamo(prestamoService);
                    case "5" -> registrarDevolucion(prestamoService);
                    case "6" -> verHistorial(prestamoService);
                    case "0" -> continuar = false;
                    default -> System.out.println("Opcion invalida.");
                }
            } catch (BibliotecaException ex) {
                System.err.println("Error de negocio: " + ex.getMessage());
            } catch (RuntimeException ex) {
                System.err.println("Entrada invalida. Intenta nuevamente.");
            }
        }
        System.out.println("Hasta luego.");
    }

    private static void mostrarMenu() {
        System.out.println("\n=== BiblioTech ===");
        System.out.println("1. Alta de recurso");
        System.out.println("2. Alta de socio");
        System.out.println("3. Buscar recursos");
        System.out.println("4. Registrar prestamo");
        System.out.println("5. Registrar devolucion");
        System.out.println("6. Ver historial de socio");
        System.out.println("0. Salir");
        System.out.print("Selecciona una opcion: ");
    }

    private static void altaRecurso(RecursoService recursoService) {
        System.out.print("Tipo (FISICO/EBOOK): ");
        String tipo = SCANNER.nextLine().trim().toUpperCase();
        System.out.print("ISBN: ");
        String isbn = SCANNER.nextLine().trim();
        System.out.print("Titulo: ");
        String titulo = SCANNER.nextLine().trim();
        System.out.print("Autor: ");
        String autor = SCANNER.nextLine().trim();
        System.out.print("Año de publicacion: ");
        int año = Integer.parseInt(SCANNER.nextLine().trim());
        System.out.print("Categoria (CIENCIA, TECNOLOGIA, HISTORIA, LITERATURA, ARTE, MATEMATICA): ");
        CategoriaRecurso categoria = CategoriaRecurso.valueOf(SCANNER.nextLine().trim().toUpperCase());

        if ("FISICO".equals(tipo)) {
            System.out.print("Ubicacion en estante: ");
            String ubicacion = SCANNER.nextLine().trim();
            recursoService.registrarRecurso(new LibroFisico(isbn, titulo, autor, año, categoria, ubicacion));
        } else {
            System.out.print("Formato de archivo (PDF/EPUB/etc): ");
            String formato = SCANNER.nextLine().trim();
            recursoService.registrarRecurso(new EBook(isbn, titulo, autor, año, categoria, formato));
        }
        System.out.println("Recurso registrado correctamente.");
    }

    private static void altaSocio(SocioService socioService) throws BibliotecaException {
        System.out.print("ID: ");
        int id = Integer.parseInt(SCANNER.nextLine().trim());
        System.out.print("DNI: ");
        String dni = SCANNER.nextLine().trim();
        System.out.print("Nombre: ");
        String nombre = SCANNER.nextLine().trim();
        System.out.print("Mail: ");
        String email = SCANNER.nextLine().trim();
        System.out.print("Tipo (ESTUDIANTE/DOCENTE): ");
        TipoSocio tipo = TipoSocio.valueOf(SCANNER.nextLine().trim().toUpperCase());

        Socio socio = tipo == TipoSocio.DOCENTE
                ? new SocioDocente(id, dni, nombre, email)
                : new SocioEstudiante(id, dni, nombre, email);
        socioService.registrarSocio(socio);
        System.out.println("Socio registrado correctamente.");
    }

    private static void buscarRecursos(RecursoService recursoService) {
        System.out.println("Buscar por: 1) Titulo  2) Autor  3) Categoria");
        String criterio = SCANNER.nextLine().trim();
        List<Recurso> resultados = switch (criterio) {
            case "1" -> {
                System.out.print("Titulo: ");
                yield recursoService.buscarPorTitulo(SCANNER.nextLine().trim());
            }
            case "2" -> {
                System.out.print("Autor: ");
                yield recursoService.buscarPorAutor(SCANNER.nextLine().trim());
            }
            case "3" -> {
                System.out.print("Categoria: ");
                CategoriaRecurso categoria = CategoriaRecurso.valueOf(SCANNER.nextLine().trim().toUpperCase());
                yield recursoService.buscarPorCategoria(categoria);
            }
            default -> List.of();
        };

        if (resultados.isEmpty()) {
            System.out.println("No se encontraron recursos.");
            return;
        }
        resultados.forEach(r -> System.out.println("- " + r.isbn() + " | " + r.titulo() + " | " + r.autor()));
    }

    private static void registrarPrestamo(PrestamoService prestamoService) throws BibliotecaException {
        System.out.print("ISBN: ");
        String isbn = SCANNER.nextLine().trim();
        System.out.print("ID socio: ");
        int socioId = Integer.parseInt(SCANNER.nextLine().trim());
        Prestamo prestamo = prestamoService.registrarPrestamo(isbn, socioId, LocalDate.now());
        System.out.println("Prestamo registrado. Vence: " + prestamo.fechaVencimiento());
    }

    private static void registrarDevolucion(PrestamoService prestamoService) throws BibliotecaException {
        System.out.print("ISBN: ");
        String isbn = SCANNER.nextLine().trim();
        Prestamo prestamo = prestamoService.registrarDevolucion(isbn, LocalDate.now());
        System.out.println("Devolucion registrada. Dias de retraso: " + prestamo.diasRetraso());
    }

    private static void verHistorial(PrestamoService prestamoService) {
        System.out.print("ID socio: ");
        int socioId = Integer.parseInt(SCANNER.nextLine().trim());
        List<Prestamo> historial = prestamoService.historialPorSocio(socioId);
        if (historial.isEmpty()) {
            System.out.println("Sin historial para ese socio.");
            return;
        }
        historial.forEach(p -> System.out.println("- ISBN: " + p.isbn() + " | Prestamo: " + p.fechaPrestamo()
                + " | Vence: " + p.fechaVencimiento() + " | Devuelto: "
                + p.fechaDevolucion().map(LocalDate::toString).orElse("NO")));
    }
}
