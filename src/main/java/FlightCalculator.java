import Model.Ticket;
import Model.TicketsReport;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class FlightCalculator {
  public static void main(String[] args) throws FileNotFoundException {
    FlightCalculator flightCalculator = new FlightCalculator();
    File file = new File("src/main/resources/tickets.json");
    TicketsReport report = flightCalculator.getTicketReportFromJsonFile(file);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyH:mm");

      report.getTickets().stream()
              .map(ticket -> flightCalculator.getFlightDuration(ticket, formatter))
              .forEach(System.out::println);

    }




  public TicketsReport getTicketReportFromJsonFile(File file) throws FileNotFoundException {
    Gson gson = new Gson();
    return gson.fromJson(new FileReader(file), TicketsReport.class);
  }

  public long getFlightDuration(Ticket ticket, DateTimeFormatter formatter){
    LocalDateTime arrival_dateTime =
            LocalDateTime.parse(ticket.getArrival_date() + ticket.getArrival_time(),
                    formatter);
    LocalDateTime departure_dateTime =
            LocalDateTime.parse(ticket.getDeparture_date() + ticket.getDeparture_time(),
                    formatter);
    return ChronoUnit.MINUTES.between(departure_dateTime, arrival_dateTime);
  }

}
