package com.company;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

//A class that defines a teacher
public class Teacher extends Person implements Writable{
    private String licenseNumber;

    public Teacher(String id, String fName, String lName, Gender gender,
                   String phoneNumber, String address, String email,String licenseNumber) {
        super(id,fName,lName,gender,phoneNumber,address,email);
        this.licenseNumber = licenseNumber;
    }
    public Teacher(Teacher teacher){
        this(teacher.getId(),teacher.getFName(),teacher.getLName(),teacher.getGender()
                ,teacher.getPhoneNumber(),teacher.getAddress(),teacher.getEmail(),teacher.licenseNumber);
    }
    public Teacher(InputStream inputStream) throws IOException{
        super(inputStream);
        read(inputStream);
    }

    //delete teacher but not do it null
    public Teacher(){
        super(" ","NO TEACHER"," ",Gender.FEMALE," "," "," ");
        licenseNumber = " ";
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || !(object instanceof Teacher))
            return false;
        if (!super.equals(object))
            return false;
        Teacher teacher = (Teacher) object;
        return licenseNumber.equals(teacher.licenseNumber);
    }

    @Override
    public int hashCode() {
        return super.hashCode()*17 ^ licenseNumber.hashCode()*13*13;
    }

    @Override
    public String toString() {
        return "TEACHER: \n    " + super.toString() +
                ", license number: " + licenseNumber;
    }

    @Override
    public void write(OutputStream outputStream) throws IOException {
        super.writePerson(outputStream);
        ReadAndWrite.writeShortString(outputStream, licenseNumber);
    }

    @Override
    public void read(InputStream inputStream) throws IOException {
        licenseNumber = ReadAndWrite.readShortString(inputStream);
    }
}
