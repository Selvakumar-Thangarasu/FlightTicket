package FlightTicket;
import java.time.LocalDateTime;
import java.util.*;

public class FlightSystem {
    static Scanner scanner = new Scanner(System.in);
    static Map<Integer, Flight> flights = new HashMap<>();
    static Map<String, List<Ticket>> userBookings = new HashMap<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n1. Add Flight\n2. View Flights\n3. Book Ticket\n4. Cancel Ticket\n5. View Bookings\n6. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addFlight();
                case 2 -> viewFlights();
                case 3 -> bookTicket();
                case 4 -> cancelTicket();
                case 5 -> viewUserBookings();
                case 6 -> System.exit(0);
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void addFlight() {
        System.out.print("Flight Number: ");
        int flightNo = scanner.nextInt();
        scanner.nextLine();
        System.out.print("From: ");
        String from = scanner.nextLine();
        System.out.print("To: ");
        String to = scanner.nextLine();
        System.out.print("Departure (yyyy-MM-ddTHH:mm): ");
        LocalDateTime time = LocalDateTime.parse(scanner.nextLine());
        System.out.print("Available Seats: ");
        int seats = scanner.nextInt();

        flights.put(flightNo, new Flight(flightNo, from, to, time, seats));
        System.out.println("Flight added.");
    }

    static void viewFlights() {
        if (flights.isEmpty()) {
            System.out.println("No flights available.");
            return;
        }
        for (Flight f : flights.values()) {
            System.out.println(f);
        }
    }

    static void bookTicket() {
        System.out.print("User ID: ");
        String user = scanner.nextLine();
        System.out.print("Flight Number: ");
        int flightNo = scanner.nextInt();
        System.out.print("Seats to Book: ");
        int seats = scanner.nextInt();

        if (!flights.containsKey(flightNo)) {
            System.out.println("Flight not found.");
            return;
        }

        Flight flight = flights.get(flightNo);
        if (flight.seatsAvailable < seats) {
            System.out.println("Not enough seats available.");
            return;
        }

        flight.seatsAvailable -= seats;
        Ticket ticket = new Ticket(flightNo, user, seats);
        userBookings.computeIfAbsent(user, k -> new ArrayList<>()).add(ticket);
        System.out.println("Ticket booked.");
    }

    static void cancelTicket() {
        System.out.print("User ID: ");
        String user = scanner.nextLine();
        System.out.print("Flight Number to Cancel: ");
        int flightNo = scanner.nextInt();

        List<Ticket> bookings = userBookings.get(user);
        if (bookings == null) {
            System.out.println("No bookings found.");
            return;
        }

        Iterator<Ticket> iterator = bookings.iterator();
        while (iterator.hasNext()) {
            Ticket t = iterator.next();
            if (t.flightNumber == flightNo) {
                flights.get(flightNo).seatsAvailable += t.seatCount;
                iterator.remove();
                System.out.println("Ticket cancelled.");
                return;
            }
        }

        System.out.println("Booking not found.");
    }

    static void viewUserBookings() {
        System.out.print("Enter User ID: ");
        String user = scanner.nextLine();
        List<Ticket> tickets = userBookings.get(user);

        if (tickets == null || tickets.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            for (Ticket t : tickets) {
                System.out.println("Flight " + t.flightNumber + " | Seats: " + t.seatCount);
            }
        }
    }
}
