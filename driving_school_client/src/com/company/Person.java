package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

enum Gender{
    MALE, FEMALE
}

//A class that defines a person
public abstract class Person {

    private String id;
    private String fName;
    private String lName;
    private Gender gender;
    private String phoneNumber;
    private String address;
    private String email;

    public Person(String id, String fName, String lName, Gender gender,
                  String phoneNumber, String address, String email) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
    }
    public Person(InputStream inputStream) throws IOException{
        readPerson(inputStream);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getLName() {
        return lName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    private String getGenderWitString(){
        if (gender == Gender.MALE)
            return "male";
        return "female";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || !(object instanceof Person))
            return false;
        Person person = (Person) object;
        return gender == person.gender && id.equals(person.id) && fName.equals(person.fName) &&
                lName.equals(person.lName) && phoneNumber.equals(person.phoneNumber) &&
                address.equals(person.address) && email.equals(person.email);
    }

    @Override
    public int hashCode() {
        return id.hashCode()*id.hashCode()*7 ^ fName.hashCode()*fName.hashCode()*3
                ^ lName.hashCode()*57 ^ getGenderWitString().hashCode()*57
                ^ phoneNumber.hashCode()*phoneNumber.hashCode()*7
                ^ address.hashCode()*51 ^ email.hashCode()*email.hashCode()*17;
    }

    @Override
    public String toString() {
        return fName + " " + lName +  " " + getGenderWitString()
                + " ID: " + id + ", " + phoneNumber
                +" , " + address + " , " + email;
    }

    public void writePerson(OutputStream outputStream) throws IOException {
        ReadAndWrite.writeShortString(outputStream, id);
        ReadAndWrite.writeShortString(outputStream, fName);
        ReadAndWrite.writeShortString(outputStream, lName);
        ReadAndWrite.writeShortString(outputStream, phoneNumber);
        ReadAndWrite.writeShortString(outputStream, address);
        ReadAndWrite.writeShortString(outputStream, email);
        if (gender == Gender.MALE)
            outputStream.write(1);
        else
            outputStream.write(2);
    }

    public void readPerson(InputStream inputStream) throws IOException {
        id = ReadAndWrite.readShortString(inputStream);
        fName = ReadAndWrite.readShortString(inputStream);
        lName = ReadAndWrite.readShortString(inputStream);
        phoneNumber = ReadAndWrite.readShortString(inputStream);
        address = ReadAndWrite.readShortString(inputStream);
        email = ReadAndWrite.readShortString(inputStream);
        int gender = inputStream.read();
        if (gender != 1 && gender != 2)
            throw new IOException("invalid number to gender");
        if (gender == 1)
            this.gender = Gender.MALE;
        else
            this.gender = Gender.FEMALE;

    }
}
