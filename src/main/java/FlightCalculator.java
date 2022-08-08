import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import model.Ticket;
import model.TicketsReport;
import org.apache.commons.io.FileUtils;


public class FlightCalculator {

  public static void main(String[] args) {
    FlightCalculator flightCalculator = new FlightCalculator();

    try {
      InputStream initialStream = flightCalculator.getClass().getClassLoader().getResourceAsStream("tickets.json");
      File targetFile = new File("targetFile.json");
      if (initialStream != null) {
        FileUtils.copyInputStreamToFile(initialStream, targetFile);
      } else {
        throw new IOException();
      }

      TicketsReport report = flightCalculator.getTicketReportFromJsonFile(targetFile);

      System.out.printf("The average flight time between Vladivostok and Tel Aviv: %.0f minutes.%n"
          , flightCalculator.getAverageFlightTime(report));
      System.out.printf("The 90th percentile flight time between Vladivostok and Tel Aviv: %.0f minutes.%n"
          , flightCalculator.getPercentile(report, 90));

    } catch (IOException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
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
    if (report.getTickets().size() == 0) {
      return 0;
    }
    return report.getTickets()
        .stream()
        .mapToLong(this::getFlightDuration)
        .average()
        .orElseThrow(NoSuchElementException::new);
  }

  public double getPercentile(TicketsReport report, int percentile) {
    if (report.getTickets().size() == 0 || percentile == 0) {
      return 0;
    }
    List<Long> list = report.getTickets()
        .stream()
        .map(this::getFlightDuration)
        .sorted()
        .collect(Collectors.toList());
    int index = (int) Math.ceil(percentile / 100.0 * list.size());
    return list.get(index - 1);
  }

}
