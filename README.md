The program reads the src/main/resources/tickets.json file and, based on it, calculates and output the following
information:
1. The average flight time between Vladivostok and Tel Aviv.
2. 90th percentile of flight time between Vladivostok and Tel Aviv.

Quick start:
1. Clone project and start it in Intellij Idea.
2. Also, you can build .jar file and run it in command line in Linux.
   In this case you need clone project, go to project directory and write in console:
    - mvn clean package
    - cd target\
    - java -jar flight_calculator-1.0-jar-with-dependencies.jar
