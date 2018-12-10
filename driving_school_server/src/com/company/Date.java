package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

//A class that defines a date
public class Date implements Writable{
    private int day;
    private int month;
    private int year;

    public Date(int day, int month, int year) {
        this.setDay(day);
        this.setMonth(month);
        this.setYear(year);
    }
    public Date(Date date){
        this(date.day, date.month, date.year);
    }
    public Date(InputStream inputStream) throws IOException{
        read(inputStream);
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        if (day > 0 && day <= 32)
            this.day = day;
        else
            throw new RuntimeException("invalid day of date");
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        if (month > 0 && month <= 12)
            this.month = month;
        else
            throw new RuntimeException("invalid month of date");

    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if (year > 1900 && year < 2099)
            this.year = year;
        else
            throw new RuntimeException("invalid year of date");
    }

    @Override
    public String toString() {
        return day + "/" + month +"/" + year;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || !(object instanceof Date))
            return false;
        Date date = (Date) object;
        return day == date.day && month == date.month && year == date.year;
    }

    @Override
    public int hashCode() {
        return day*day*57 ^ month*month*13 ^ year*7;
    }

    //Check which date is the longest
    public int compareTo(Date date){
        if (date == null)
            return -1;
        if (this.year > date.year)
            return 1;
        if (year == date.year && month > date.month)
            return 1;
        if (year == date.year && month == date.month && day > date.day)
            return 1;
        if (year == date.year && month == date.month && day == date.day)
            return 0;
        return -1;

    }

    //Returns the date in x years
    public Date enlargeByYears(int years){
        return new Date(day, month, year + years);
    }

    //Checks the lowest date in an array
    public static Date smallOut(Date[] dates){
        if (dates == null)
            return null;
        Date small = null;
        for (Date date:dates) {
            if (date.compareTo(small) == -1)
                small = date;

        }
        return new Date(small);
    }

    @Override
    public void write(OutputStream outputStream) throws IOException {
        outputStream.write(day);
        outputStream.write(month);
        byte[] intBytes = new byte[4];
        ByteBuffer.wrap(intBytes).putInt(year);
        outputStream.write(intBytes);
    }

    @Override
    public void read(InputStream inputStream) throws IOException {
        setDay(inputStream.read());
        setMonth(inputStream.read());
        byte[] intBytes = new byte[4];
        int actuallyRead = inputStream.read(intBytes);
        if(actuallyRead != 4)
            throw new IOException("Not four bytes for year");
        setYear(ByteBuffer.wrap(intBytes).getInt());

    }
}
