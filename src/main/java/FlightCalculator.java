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
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class FlightCalculator {
  private static final String JSON_FILE_NAME = "src/main/resources/tickets.json";

  public static void main(String[] args) {
    FlightCalculator flightCalculator = new FlightCalculator();

    try {
      TicketsReport report = flightCalculator.getTicketReportFromJsonFile(new File(JSON_FILE_NAME));

      System.out.printf("The average flight time between Vladivostok and Tel Aviv: %.0f minutes.%n"
          , flightCalculator.getAverageFlightTime(report));
      System.out.printf("The 90th percentile flight time between Vladivostok and Tel Aviv: %.0f minutes.%n"
          , flightCalculator.getPercentile(report, 90));

    } catch (FileNotFoundException e) {
      System.out.println("File not found!");
    }


  }


  public TicketsReport getTicketReportFromJsonFile(File file) throws FileNotFoundException {
    Gson gson = new Gson();
    return gson.fromJson(new FileReader(file), TicketsReport.class);
  }

  public long getFlightDuration(Ticket ticket) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyH:mm");
    LocalDateTime arrival_dateTime =
        LocalDateTime.parse(ticket.getArrival_date() + ticket.getArrival_time(),
            formatter);
    LocalDateTime departure_dateTime =
        LocalDateTime.parse(ticket.getDeparture_date() + ticket.getDeparture_time(),
            formatter);
    return ChronoUnit.MINUTES.between(departure_dateTime, arrival_dateTime);
  }

  public double getAverageFlightTime(TicketsReport report) {
    return report.getTickets()
        .stream()
        .mapToLong(this::getFlightDuration)
        .average()
        .orElseThrow(NoSuchElementException::new);
  }

  public double getPercentile(TicketsReport report, int percentile) {
    List<Long> list = report.getTickets()
        .stream()
        .map(this::getFlightDuration)
        .sorted()
        .collect(Collectors.toList());
    int index = (int) Math.ceil(percentile / 100.0 * list.size());
    return list.get(index - 1);
  }

}
