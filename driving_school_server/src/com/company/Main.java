package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class Main {

    public static final int PORT = 3000;

    public static void main(String[] args) {

        //Create a maps (teacherMap, carMap, studentMap) and load data from the files

        File teachersFile = new File("C:\\Users\\User\\Desktop\\driving school\\teacherMap.txt");
        File carsFile = new File("C:\\Users\\User\\Desktop\\driving school\\carMap.txt");
        File studentsFile = new File("C:\\Users\\User\\Desktop\\driving school\\studentMap.txt");
        Map<String, Teacher> teacherMap = loadingTeachersDetails(teachersFile);
        Map<String, Car> carMap = loadingCarsDetails(carsFile);
        Map<String, Student> studentMap = loadingStudentDetails(studentsFile);

        //connect to clients with Thread
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                new ClientThread(socket, teacherMap, carMap, studentMap, teachersFile, carsFile, studentsFile).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public static Map<String, Teacher> loadingTeachersDetails(File teachersFile){
        Map<String, Teacher> teacherMap = new HashMap<>();
        ReadAndWrite.readTeachersFromFile(teacherMap,teachersFile);
        return teacherMap;
    }
    public static Map<String, Car> loadingCarsDetails(File carsFile){
        Map<String, Car> carMap = new HashMap<>();
        ReadAndWrite.readCarsFromFile(carMap,carsFile);
        return carMap;

    }
    public static Map<String, Student> loadingStudentDetails(File studentsFile){
        Map<String, Student> studentMap = new HashMap<>();
        ReadAndWrite.readStudentsFromFile(studentMap,studentsFile);
        return studentMap;
    }
}
