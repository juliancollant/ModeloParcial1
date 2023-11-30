import java.io.*;
import java.util.*;

abstract class Persona implements Serializable {
    String nombre;
    int edad;

    public Persona(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }
}

class Huesped extends Persona {
    String correo;

    public Huesped(String nombre, int edad, String correo) {
        super(nombre, edad);
        this.correo = correo;
    }
}

class Habitacion implements Serializable {
    private int numero;
    private int cantidadCamas;
    private int capacidadHuespedes;
    private boolean ocupada;
    private List<String> nombresHuespedes;

    public Habitacion(int numero, int cantidadCamas) {
        this.numero = numero;
        this.cantidadCamas = cantidadCamas;
        this.capacidadHuespedes = cantidadCamas + 1; // Capacidad de huéspedes basada en la cantidad de camas
        this.ocupada = false;
        this.nombresHuespedes = new ArrayList<>();
    }

    public int getNumero() {
        return numero;
    }

    public int getCantidadCamas() {
        return cantidadCamas;
    }

    public int getCapacidadHuespedes() {
        return capacidadHuespedes;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public List<String> getNombresHuespedes() {
        return nombresHuespedes;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    public void agregarHuesped(String nombreHuesped) {
        nombresHuespedes.add(nombreHuesped);
    }
}

public class Hotelgtp {
    public static void main(String[] args) {
        List<Habitacion> habitaciones = cargarReservas(); // Cargar las reservas al iniciar la aplicación

        Scanner scanner = new Scanner(System.in);

        int opcion;
        do {
            System.out.println("Menú:");
            System.out.println("1. Ver la lista de habitaciones.");
            System.out.println("2. Reservar una habitación.");
            System.out.println("3. Cancelar una reserva.");
            System.out.println("4. Salir.");
            System.out.print("Ingrese una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el salto de línea

            switch (opcion) {
                case 1:
                    verHabitacionesDisponibles(habitaciones);
                    break;
                case 2:
                    reservarHabitacion(habitaciones, scanner);
                    guardarReservas(habitaciones); // Guardar reservas después de reservar una habitación
                    break;
                case 3:
                    cancelarReserva(habitaciones, scanner);
                    guardarReservas(habitaciones); // Guardar reservas después de cancelar una reserva
                    break;
                case 4:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        } while (opcion != 4);

        scanner.close();
    }

    public static void verHabitacionesDisponibles(List<Habitacion> habitaciones) {
        for (Habitacion habitacion : habitaciones) {
            System.out.println("Habitación " + habitacion.getNumero() + ":");
            System.out.println("Camas: " + habitacion.getCantidadCamas());
            System.out.println("Capacidad de Huéspedes: " + habitacion.getCapacidadHuespedes());
            System.out.println("Estado: " + (habitacion.isOcupada() ? "Reservada" : "Disponible"));
            if (habitacion.isOcupada()) {
                System.out.println("Huéspedes:");
                for (String nombre : habitacion.getNombresHuespedes()) {
                    System.out.println("- " + nombre);
                }
            }
            System.out.println();
        }
    }

    public static void reservarHabitacion(List<Habitacion> habitaciones, Scanner scanner) {
        System.out.println("Ingrese el número de habitación que desea reservar: ");
        int numeroReserva = scanner.nextInt();
        scanner.nextLine(); // Limpiar el salto de línea

        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getNumero() == numeroReserva && !habitacion.isOcupada()) {
                // Realizar la reserva de la habitación
                habitacion.setOcupada(true);

                System.out.println("Ingrese la cantidad de huéspedes: ");
                int cantidadHuespedes = scanner.nextInt();
                scanner.nextLine(); // Limpiar el salto de línea

                for (int i = 0; i < cantidadHuespedes; i++) {
                    System.out.println("Ingrese el nombre del huésped " + (i + 1) + ": ");
                    String nombreHuesped = scanner.nextLine();
                    habitacion.agregarHuesped(nombreHuesped);
                }

                System.out.println("Se ha reservado la habitación " + habitacion.getNumero() + " para los huéspedes.");
                return;
            }
        }
        System.out.println("La habitación no está disponible para reservar.");
    }

    public static void cancelarReserva(List<Habitacion> habitaciones, Scanner scanner) {
        System.out.println("Ingrese el número de habitación que desea cancelar la reserva: ");
        int numeroCancelacion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el salto de línea

        System.out.println("Ingrese el nombre del huésped que desea cancelar: ");
        String nombreCancelar = scanner.nextLine();

        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getNumero() == numeroCancelacion && habitacion.isOcupada() && habitacion.getNombresHuespedes().contains(nombreCancelar)) {
                // Cancelar la reserva de la habitación
                habitacion.getNombresHuespedes().remove(nombreCancelar);
                if (habitacion.getNombresHuespedes().isEmpty()) {
                    habitacion.setOcupada(false);
                }
                System.out.println("Se ha cancelado la reserva de la habitación " + habitacion.getNumero() + " para el huésped " + nombreCancelar);
                return;
            }
        }
        System.out.println("La habitación no está reservada.");
    }

    public static void guardarReservas(List<Habitacion> habitaciones) {
        try {
            FileOutputStream fileOut = new FileOutputStream("reservas.dat");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(habitaciones);
            objectOut.close();
            fileOut.close();
            System.out.println("Reservas guardadas en 'reservas.dat'");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Habitacion> cargarReservas() {
        List<Habitacion> habitaciones = new ArrayList<>();
        try {
            FileInputStream fileIn = new FileInputStream("reservas.dat");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            habitaciones = (List<Habitacion>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            System.out.println("Reservas cargadas desde 'reservas.dat'");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return habitaciones;
    }
}
