package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

//A class that defines a car
public class Car implements Writable {
    private String carNumber;
    private String carType;
    private String company;
    private String model;
    private Teacher teacher;

    public Car(String carNumber, String carType, String company, String model, Teacher teacher) {
        this.carNumber = carNumber;
        this.carType = carType;
        this.company = company;
        this.model = model;
        this.teacher = new Teacher(teacher);
    }
    public Car(Car car){
        this(car.carNumber, car.carType, car.company, car.model, car.teacher);
    }
    public Car(InputStream inputStream) throws IOException{
        read(inputStream);
    }

    //delete car but not do it null
    public Car(){
        carNumber = " ";
        carType = " ";
        company = "NO CAR";
        model = " ";
        removeTeacher();
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Teacher getTeacher() {
        return new Teacher(teacher);
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = new Teacher(teacher);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || !(object instanceof Car))
            return false;
        Car car = (Car) object;
        return carNumber.equals(car.carNumber) && carType.equals(car.carType) &&
                company.equals(car.company) && model.equals(car.model) &&
                teacher.equals(car.teacher);
    }

    @Override
    public int hashCode() {
        return carNumber.hashCode()*57 ^ carType.hashCode()*17*17 ^ company.hashCode()*7
                ^ model.hashCode()*model.hashCode()*3 ^ teacher.hashCode()*17;
    }

    @Override
    public String toString() {
        return "CAR: \n    "  + company + " " + model + " " + carType + " " + carNumber + " "
                + "Teacher name: " + this.teacher.getFName()+" "+this.teacher.getLName();
    }

    @Override
    public void write(OutputStream outputStream) throws IOException {
        ReadAndWrite.writeShortString(outputStream, carNumber);
        ReadAndWrite.writeShortString(outputStream, carType);
        ReadAndWrite.writeShortString(outputStream, company);
        ReadAndWrite.writeShortString(outputStream, model);
        teacher.write(outputStream);
    }

    @Override
    public void read(InputStream inputStream) throws IOException {
        carNumber = ReadAndWrite.readShortString(inputStream);
        carType = ReadAndWrite.readShortString(inputStream);
        company = ReadAndWrite.readShortString(inputStream);
        model = ReadAndWrite.readShortString(inputStream);
        teacher = new Teacher(inputStream);
    }

    public void removeTeacher(){
        teacher = new Teacher();
    }
}
