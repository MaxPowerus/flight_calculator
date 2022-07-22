package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
  private String origin;
  private String origin_name;
  private String destination;
  private String destination_name;
  private String departure_date;
  private String departure_time;
  private String arrival_date;
  private String arrival_time;
  private String carrier;
  private int stops;
  private int price;

}
