import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.Serializable;

// Clase principal del sistema del hospital
public class SistemaHospital {
    private List<Doctor> doctores = new ArrayList<>(); // Lista de doctores
    private List<Paciente> pacientes = new ArrayList<>(); // Lista de pacientes

    public static void main(String[] args) {
        SistemaHospital sistema = new SistemaHospital();
        sistema.cargarDoctores(); // Carga algunos doctores predefinidos
        sistema.cargarPacientes(); // Carga algunos pacientes predefinidos
        sistema.mostrarMenu(); // Muestra el menú principal
    }

    // Método para cargar algunos doctores predefinidos
    private void cargarDoctores() {
        Doctor doctor1 = new Doctor("Dr. Juan", "12345678", "01/01/1970", "Cardiología");
        Doctor doctor2 = new Doctor("Dra. María", "87654321", "02/02/1980", "Pediatría");
        doctores.add(doctor1);
        doctores.add(doctor2);
    }

    // Método para cargar algunos pacientes predefinidos
    private void cargarPacientes() {
        Paciente paciente1 = new Paciente("Paciente 1", "31159753", "01/01/1990", "3624752222", 1);
        pacientes.add(paciente1);
    }

    // Método principal que muestra el menú y maneja las opciones
    private void mostrarMenu() {
        while (true) {
            LeerFichero accesoExternos = new LeerFichero(); // Instancia para leer un archivo
            System.out.println(accesoExternos.leeDatos()); // Muestra el contenido de archivo.txt
            System.out.println("Menú:");
            System.out.println("1. Listar Doctores");
            System.out.println("2. Registrar un nuevo paciente");
            System.out.println("3. Actualizar información personal de un paciente");
            System.out.println("4. Consultar el historial médico de un paciente");
            System.out.println("5. Nuevo historial para un paciente");
            System.out.println("6. Guardar Historial de pacientes en archivo");
            System.out.println("7. Cargar Historial de pacientes desde archivo");
            System.out.println("8. Mostrar pacientes registrados");
            System.out.println("9. Salir");

            Scanner scanner = new Scanner(System.in);
            int opcion = scanner.nextInt(); // Lee la opción seleccionada
            scanner.nextLine(); // Limpia el buffer

            switch (opcion) {
                case 1:
                    listarDoctores(); // Lista los doctores
                    break;
                case 2:
                    registrarNuevoPaciente(); // Registra un nuevo paciente
                    break;
                case 3:
                    actualizarInformacionPaciente(); // Actualiza la información de un paciente
                    break;
                case 4:
                    consultarHistorialMedico(); // Consulta el historial médico de un paciente
                    break;
                case 5:
                    cargarNuevoHistorial(); // Carga un nuevo evento en el historial médico de un paciente
                    break;
                case 6:
                    guardarPacientesEnArchivo(); // Guarda los pacientes en un archivo
                    break;
                case 7:
                    cargarPacientesDesdeArchivo(); // Carga los pacientes desde un archivo
                    break;
                case 8:
                    mostrarPacientesRegistrados(); // Muestra todos los pacientes registrados
                    break;
                case 9:
                    System.out.println("Saliendo del programa."); // Sale del programa
                    return;
                default:
                    System.out.println("Opción no válida. Por favor, elija una opción válida.");
            }
        }
    }

    // Método para listar los doctores
    private void listarDoctores() {
        for (Doctor doctor : doctores) {
            System.out.println("Nombre: " + doctor.nombre);
            System.out.println("DNI: " + doctor.DNI);
            System.out.println("Fecha de Nacimiento: " + doctor.fechaNacimiento);
            System.out.println("Especialidad: " + doctor.especialidad);
            System.out.println();
        }
    }

    // Método para registrar un nuevo paciente
    private void registrarNuevoPaciente() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese el nombre del paciente:");
        String nombre = scanner.nextLine();

        System.out.println("Ingrese el DNI del paciente:");
        String DNI = scanner.nextLine();

        System.out.println("Ingrese la fecha de nacimiento del paciente (dd/mm/yyyy):");
        String fechaNacimiento = scanner.nextLine();

        System.out.println("Ingrese el número de teléfono del paciente:");
        String numeroTelefono = scanner.nextLine();

        System.out.println("Ingrese el tipo de sangre del paciente:");
        int tipoSangre = scanner.nextInt();

        Paciente nuevoPaciente = new Paciente(nombre, DNI, fechaNacimiento, numeroTelefono, tipoSangre);
        pacientes.add(nuevoPaciente);
        System.out.println("Nuevo paciente registrado correctamente.");
    }

    // Método para actualizar la información de un paciente
    private void actualizarInformacionPaciente() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese el DNI del paciente para actualizar su información:");
        String dniBusqueda = scanner.nextLine();

        for (Paciente paciente : pacientes) {
            if (paciente.DNI.equals(dniBusqueda)) {
                System.out.println("Ingrese el nuevo número de teléfono del paciente:");
                String nuevoNumero = scanner.nextLine();
                scanner.nextLine(); // Limpiar el buffer

                paciente.numeroTelefono = nuevoNumero;

                System.out.println("Información del paciente actualizada correctamente.");
                return;
            }
        }

        System.out.println("Paciente no encontrado.");
    }

    // Método para consultar el historial médico de un paciente
    private void consultarHistorialMedico() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese el DNI del paciente para ver su historial médico:");
        String dniBusqueda = scanner.nextLine();

        for (Paciente paciente : pacientes) {
            if (paciente.DNI.equals(dniBusqueda)) {
                System.out.println("Historial médico del paciente " + paciente.nombre + ":");
                paciente.verHistorialDeEventos();
                return;
            }
        }

        System.out.println("Paciente no encontrado.");
    }

    // Método para cargar un nuevo evento en el historial médico de un paciente
    private void cargarNuevoHistorial() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese el DNI del paciente para agregar un nuevo evento al historial:");
        String dniBusqueda = scanner.nextLine();

        for (Paciente paciente : pacientes) {
            if (paciente.DNI.equals(dniBusqueda)) {
                System.out.println("Ingrese el nuevo evento para el historial médico:");
                String nuevoEvento = scanner.nextLine();

                paciente.historialMedico.add(nuevoEvento);
                System.out.println("Evento agregado al historial médico correctamente.");
                return;
            }
        }

        System.out.println("Paciente no encontrado.");
    }

    // Método para guardar los pacientes en un archivo
    private void guardarPacientesEnArchivo() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("pacientes.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(pacientes);
            objectOutputStream.close();
            fileOutputStream.close();
            System.out.println("Pacientes guardados en el archivo pacientes.txt.");
        } catch (IOException e) {
            System.out.println("Error al guardar pacientes en el archivo.");
        }
    }

    // Método para cargar los pacientes desde un archivo
    private void cargarPacientesDesdeArchivo() {
        try {
            FileInputStream fileInputStream = new FileInputStream("pacientes.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            pacientes = (List<Paciente>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            System.out.println("Pacientes cargados desde el archivo pacientes.txt.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar pacientes desde el archivo.");
        }
    }

    // Método para mostrar todos los pacientes registrados
    private void mostrarPacientesRegistrados() {
        if (pacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados.");
        } else {
            System.out.println("Pacientes registrados:");
            for (Paciente paciente : pacientes) {
                System.out.println("Nombre: " + paciente.nombre);
                System.out.println("DNI: " + paciente.DNI);
                System.out.println("Fecha de Nacimiento: " + paciente.fechaNacimiento);
                System.out.println("Número de Teléfono: " + paciente.numeroTelefono);
                System.out.println("Tipo de Sangre: " + paciente.tipoSangre);
                System.out.println();
            }
        }
    }
}

// Clase base que define a una persona
class Persona {
    String nombre;
    String DNI;
    String fechaNacimiento;
}

// Clase Doctor que hereda de Persona e implementa Serializable
class Doctor extends Persona implements Serializable {
    String especialidad;

    public Doctor(String nombre, String DNI, String fechaNacimiento, String especialidad) {
        this.nombre = nombre;
        this.DNI = DNI;
        this.fechaNacimiento = fechaNacimiento;
        this.especialidad = especialidad;
    }
}

// Interfaz para la información médica
interface Informacion {
    void verHistorialDeEventos();
}

// Clase Paciente que hereda de Persona, implementa Informacion e implementa Serializable
class Paciente extends Persona implements Informacion, Serializable {
    String numeroTelefono;
    int tipoSangre;
    List<String> historialMedico;

    public Paciente(String nombre, String DNI, String fechaNacimiento, String numeroTelefono, int tipoSangre) {
        this.nombre = nombre;
        this.DNI = DNI;
        this.fechaNacimiento = fechaNacimiento;
        this.numeroTelefono = numeroTelefono;
        this.tipoSangre = tipoSangre;
        this.historialMedico = new ArrayList<>();
    }

    public void verHistorialDeEventos() {
        for (String evento : historialMedico) {
            System.out.println(evento);
        }
    }
}

// Clase para leer un archivo de texto
class LeerFichero {
    public String leeDatos() {
        StringBuilder contenido = new StringBuilder();
        try {
            FileReader entrada = new FileReader("archivo.txt");
            int caracter = entrada.read();

            while (caracter != -1) {
                char letra = (char) caracter;
                contenido.append(letra);
                caracter = entrada.read();
            }
            entrada.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contenido.toString();
    }
}
