package me.tyza;

public class Report {
    private int tps;
    private String date;
    private String time;


    public Report(int tps, String date, String time)
    {
        this.tps = tps;
        this.date = date;
        this.time = time;
    }

    public int getTps() {
        return tps;
    }

    public String getDate() {
        return date;
    }
    public String getTime() { return time; }
}
