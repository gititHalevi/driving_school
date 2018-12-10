package com.company;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

//A class that defines a student
public class Student extends Person implements Writable {
    private Car car;
    private GreenForm greenForm;

    public Student(String id, String fName, String lName, Gender gender, String phoneNumber,
                   String address, String email, Car car, GreenForm greenForm) {
        super(id, fName, lName, gender, phoneNumber, address, email);
        this.car = new Car(car);
        this.greenForm = new GreenForm(greenForm);
    }
    public Student(Student student){
        this(student.getId(), student.getFName(), student.getLName(), student.getGender(), student.getPhoneNumber(), student.getAddress(), student.getEmail(), student.car, student.greenForm);
    }
    public Student(InputStream inputStream) throws IOException{
        super(inputStream);
        read(inputStream);
    }

    public Car getCar() {
        return new Car(car);
    }

    public void setCar(Car car) {
        this.car = new Car(car);
    }

    public GreenForm getGreenForm() {
        return new GreenForm(greenForm);
    }

    public void setGreenForm(GreenForm greenForm) {
        this.greenForm = new GreenForm(greenForm);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || !(object instanceof Student))
            return false;
        if (!super.equals(object))
            return false;
        Student student = (Student) object;
        return car.equals(student.car) && greenForm.equals(student.greenForm);
    }

    @Override
    public int hashCode() {
        return super.hashCode()*57 ^ greenForm.hashCode()*7 ^ car.hashCode()*17;
    }

    @Override
    public String toString() {
        return "STUDENT: \n    " + super.toString() +
                "\n    " + "validity green form: " + greenForm.getGreenFormValidity() +
                "\n    Study car: " + car.getCompany()+ " "+ car.getModel() + " " + car.getCarType() +
                "\n    Teacher teaches: " + car.getTeacher().getFName()+" "+car.getTeacher().getLName();
    }

    @Override
    public void write(OutputStream outputStream) throws IOException {
        super.writePerson(outputStream);
        car.write(outputStream);
        greenForm.write(outputStream);
    }

    @Override
    public void read(InputStream inputStream) throws IOException {
        car = new Car(inputStream);
        greenForm = new GreenForm(inputStream);
    }
    public void removeCar(){
        car = new Car();
    }
}
