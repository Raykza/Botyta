package me.tyza;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;


public class Report {
    private int tps;
    private String datetime;


    public Report(int tps, String datetime)
    {
        this.tps = tps;
        this.datetime = datetime;
    }

    public int getTps() {
        return tps;
    }

    public String getDatetime() {
        return datetime;
    }
}
