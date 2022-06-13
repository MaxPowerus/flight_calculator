package Model;

import java.util.List;

public class TicketsReport {
  private List<Ticket> tickets;

  public TicketsReport() {
  }

  public TicketsReport(List<Ticket> tickets) {
    this.tickets = tickets;
  }

  public List<Ticket> getTickets() {
    return tickets;
  }

  public void setTickets(List<Ticket> tickets) {
    this.tickets = tickets;
  }

  @Override
  public String toString() {
    return "TicketsReport{" +
        "tickets=" + tickets +
        '}';
  }
}
