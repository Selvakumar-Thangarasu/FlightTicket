package FlightTicket;
import java.time.LocalDateTime;

class Flight {
    int flightNumber;
    String from;
    String to;
    LocalDateTime departureTime;
    int seatsAvailable;

    Flight(int flightNumber, String from, String to, LocalDateTime departureTime, int seats) {
        this.flightNumber = flightNumber;
        this.from = from;
        this.to = to;
        this.departureTime = departureTime;
        this.seatsAvailable = seats;
    }

    @Override
    public String toString() {
        return "Flight " + flightNumber + ": " + from + " → " + to + " at " + departureTime + " | Seats: " + seatsAvailable;
    }
}
