package com.company;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

enum Type{
    TEACHER, CAR, STUDENT, GREEN_FORM
}

public class ClientThread extends Thread {
    public static final int GET_TEACHER_BY_ID = 10;
    public static final int GET_STUDENT_BY_ID = 11;
    public static final int GET_CAR_BY_CAR_NUMBER = 12;
    public static final int DEL_TEACHER_BY_ID = 20;
    public static final int DEL_STUDENT_BY_ID = 21;
    public static final int DEL_CAR_BY_CAR_NUMBER = 22;
    public static final int ADD_TEACHER = 30;
    public static final int ADD_STUDENT = 31;
    public static final int ADD_CAR = 32;
    public static final int CHANGE_TEACHER = 40;
    public static final int CHANGE_STUDENT = 41;
    public static final int CHANGE_GREEN_FORM = 42;

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private Map<String, Teacher> teacherMap = new HashMap<>();
    private Map<String, Car> carMap = new HashMap<>();
    private Map<String, Student> studentMap = new HashMap<>();
    private File teachersFile;
    private File carsFile;
    private File studentsFile;

    public ClientThread(Socket socket, Map<String, Teacher> teacherMap, Map<String, Car> carMap,
                        Map<String, Student> studentMap, File teachersFile, File carsFile, File studentsFile) {
        this.socket = socket;
        this.teacherMap = teacherMap;
        this.carMap = carMap;
        this.studentMap = studentMap;
        this.teachersFile = teachersFile;
        this.carsFile = carsFile;
        this.studentsFile = studentsFile;
    }

    //Connection to client, receiving a request from the customer and transferring it to the appropriate function
    @Override
    public void run() {
        inputStream = null;
        outputStream = null;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            int action = inputStream.read();
            switch (action){
                case GET_TEACHER_BY_ID:
                    getObject(Type.TEACHER);
                    break;
                case GET_STUDENT_BY_ID:
                    getObject(Type.STUDENT);
                    break;
                case GET_CAR_BY_CAR_NUMBER:
                    getObject(Type.CAR);
                    break;
                case DEL_TEACHER_BY_ID:
                    delObject(Type.TEACHER);
                    break;
                case DEL_STUDENT_BY_ID:
                    delObject(Type.STUDENT);
                    break;
                case DEL_CAR_BY_CAR_NUMBER:
                    delObject(Type.CAR);
                    break;
                case ADD_TEACHER:
                    addObject(Type.TEACHER);
                    break;
                case ADD_STUDENT:
                    addObject(Type.STUDENT);
                    break;
                case ADD_CAR:
                    addObject(Type.CAR);
                    break;
                case CHANGE_TEACHER:
                    changeObject(Type.TEACHER);
                    break;
                case CHANGE_STUDENT:
                    changeObject(Type.STUDENT);
                    break;
                case CHANGE_GREEN_FORM:
                    changeObject(Type.GREEN_FORM);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //get key from client, check if the value exist and return an appropriate answer
    private void getObject(Type type) throws IOException{
        String key = ReadAndWrite.readShortString(inputStream);
        switch (type){
            case TEACHER:
                Teacher teacher = teacherMap.get(key);
                if (teacher != null){
                    outputStream.write(1);
                    teacher.write(outputStream);
                }else
                    outputStream.write(0);
                break;
            case STUDENT:
                Student student = studentMap.get(key);
                if (student != null){
                    outputStream.write(1);
                    student.write(outputStream);
                }else
                    outputStream.write(0);
                break;
            case CAR:
                Car car = carMap.get(key);
                if (car != null){
                    outputStream.write(1);
                    car.write(outputStream);
                }else
                    outputStream.write(0);
                break;
        }
    }

    //get key from client, check if the value exist and remove the value from the map. save the change to file.
    private void delObject(Type type) throws IOException{
        String key = ReadAndWrite.readShortString(inputStream);
        switch (type){
            case TEACHER:
                Teacher teacher = teacherMap.get(key);
                if (teacher != null){
                    teacherMap.remove(key);
                    for (Map.Entry<String,Car> carEntry:carMap.entrySet()) {
                        Car carDelTeacher = carEntry.getValue();
                        if (carDelTeacher.getTeacher().getId().equals(teacher.getId())){
                            carDelTeacher.removeTeacher();
                        }
                    }
                    ReadAndWrite.writeTeacherToFile(teacherMap, teachersFile);
                    outputStream.write(1);
                    ReadAndWrite.writeShortString(outputStream, teacher.getFName()+ " " + teacher.getLName());
                }else
                    outputStream.write(0);
                break;
            case STUDENT:
                Student student = studentMap.get(key);
                if (student != null){
                    studentMap.remove(key);
                    ReadAndWrite.writeStudentToFile(studentMap,studentsFile);
                    outputStream.write(1);
                    ReadAndWrite.writeShortString(outputStream, student.getFName()+ " " + student.getLName());
                }else
                    outputStream.write(0);
                break;
            case CAR:
                Car car = carMap.get(key);
                if (car != null){
                    carMap.remove(key);
                    for (Map.Entry<String,Student> studentEntry:studentMap.entrySet()) {
                        Student studentDelCar = studentEntry.getValue();
                        if (studentDelCar.getCar().getCarNumber().equals(car.getCarNumber())){
                            studentDelCar.removeCar();
                        }
                    }
                    ReadAndWrite.writeCarsToFile(carMap,carsFile);
                    outputStream.write(1);
                    ReadAndWrite.writeShortString(outputStream, car.getCompany()+ " " + car.getModel());
                }else
                    outputStream.write(0);
                break;
        }
    }

    //get value from client, check if the value not exist and add it to the map. save the change to file.
    private void addObject(Type type) throws IOException{
        switch (type){
            case TEACHER:
                Teacher teacher = new Teacher(inputStream);
                synchronized (teacherMap) {
                    boolean isExist = teacherMap.containsKey(teacher.getId());
                    if (isExist) {
                        outputStream.write(0);
                    } else {
                        teacherMap.put(teacher.getId(), teacher);
                        ReadAndWrite.writeTeacherToFile(teacherMap, teachersFile);
                        outputStream.write(1);
                    }
                }
                break;
            case STUDENT:
                Student student = new Student(inputStream);
                synchronized (studentMap) {
                    boolean isExist = studentMap.containsKey(student.getId());
                    if (isExist) {
                        outputStream.write(0); //the student already exist
                    } else {
                        if (!carMap.containsKey(student.getCar().getCarNumber()))
                            outputStream.write(-1); //its not exist car for the student...
                        else {
                            studentMap.put(student.getId(), student);
                            ReadAndWrite.writeStudentToFile(studentMap, studentsFile);
                            outputStream.write(1); //student add
                        }
                    }
                }
                break;
            case CAR:
                Car car = new Car(inputStream);
                synchronized (studentMap) {
                    boolean isExist = carMap.containsKey(car.getCarNumber());
                    if (isExist) {
                        outputStream.write(0); //the car already exist
                    } else if (!teacherMap.containsKey(car.getTeacher().getId()))
                        outputStream.write(-1); //its not exist teacher for the car...
                    else {
                        carMap.put(car.getCarNumber(), car);
                        ReadAndWrite.writeCarsToFile(carMap, carsFile);
                        outputStream.write(1);
                    }
                }
                break;
        }
    }

    //get value from client, check if the value exist and change it in the map. save the change to file.
    private void changeObject(Type type) throws IOException{
        switch (type){
            case TEACHER:
                Teacher teacher = new Teacher(inputStream);
                synchronized (teacherMap) {
                    boolean isExist = teacherMap.containsKey(teacher.getId());
                    if (isExist) {
                        teacherMap.put(teacher.getId(), teacher);
                        ReadAndWrite.writeTeacherToFile(teacherMap, teachersFile);
                        outputStream.write(1);
                    } else {
                        outputStream.write(0);
                    }
                }
                break;
            case STUDENT:
                Student student = new Student(inputStream);
                synchronized (studentMap) {
                    boolean isExist = studentMap.containsKey(student.getId());
                    if (isExist) {
                        studentMap.put(student.getId(), student);
                        ReadAndWrite.writeStudentToFile(studentMap, studentsFile);
                        outputStream.write(1);
                    } else {
                        outputStream.write(0);
                    }

                }
                break;
            case GREEN_FORM:
                String idOfStudent = ReadAndWrite.readShortString(inputStream);
                GreenForm greenForm = new GreenForm(inputStream);
                synchronized (studentMap) {
                    student = studentMap.get(idOfStudent);
                    if (student != null) {
                        student.setGreenForm(greenForm);
                        ReadAndWrite.writeStudentToFile(studentMap, studentsFile);
                        outputStream.write(1);
                    } else
                        outputStream.write(0);
                }
                break;
        }
    }


}
