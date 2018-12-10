package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

// Connects to the server, sends action code and relevant information,
// and receives the necessary information
public class Server {

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

    public static final String HOST = "127.0.0.1";
    public static final int PORT = 3000;


    // Send id of teacher to the server and receives the teacher if it exist,
    // using ObjectAnswerListener to receives the answer.
    public static void getTeacher(final String id, final ObjectAnswerListener listener){
        connect(new ConnectionListener() {
            @Override
            public void onConnect(InputStream inputStream, OutputStream outputStream) throws IOException {
                outputStream.write(GET_TEACHER_BY_ID);
                ReadAndWrite.writeShortString(outputStream, id);
                int isExist = inputStream.read();
                if (isExist != 0 && isExist != 1)
                    throw new IOException("invalid sing, it have to get 0 or 1");
                Teacher teacher = null;
                if (isExist != 0)
                    teacher = new Teacher(inputStream);
                listener.onAnswerReady(teacher);

            }
        });
    }


    // Send id of student to the server and receives the student if it exist,
    // using ObjectAnswerListener to receives the answer.
    public static void getStudent(final String id, final ObjectAnswerListener listener){
        connect(new ConnectionListener() {
            @Override
            public void onConnect(InputStream inputStream, OutputStream outputStream) throws IOException {
                outputStream.write(GET_STUDENT_BY_ID);
                ReadAndWrite.writeShortString(outputStream, id);
                int isExist = inputStream.read();
                if (isExist != 0 && isExist != 1)
                    throw new IOException("invalid sing, it have to get 0 or 1");
                Student student = null;
                if (isExist != 0)
                    student = new Student(inputStream);
                listener.onAnswerReady(student);

            }
        });
    }


    // Send number of car to the server and receives the car if it exist,
    // using ObjectAnswerListener to receives the answer.
    public static void getCar(final String carNumber, final ObjectAnswerListener listener){
        connect(new ConnectionListener() {
            @Override
            public void onConnect(InputStream inputStream, OutputStream outputStream) throws IOException {
                outputStream.write(GET_CAR_BY_CAR_NUMBER);
                ReadAndWrite.writeShortString(outputStream, carNumber);
                int isExist = inputStream.read();
                if (isExist != 0 && isExist != 1)
                    throw new IOException("invalid sing, it have to get 0 or 1");
                Car car = null;
                if (isExist != 0)
                    car = new Car(inputStream);
                listener.onAnswerReady(car);

            }
        });
    }

    // Send id of teacher to the server and delete the teacher if it exist,
    // using ObjectAnswerListener to receives the answer.
    public static void delTeacher(final String id, final ObjectAnswerListener listener){
        connect(new ConnectionListener() {
            @Override
            public void onConnect(InputStream inputStream, OutputStream outputStream) throws IOException {
                outputStream.write(DEL_TEACHER_BY_ID);
                ReadAndWrite.writeShortString(outputStream, id);
                int isDel = inputStream.read();
                if (isDel != 0 && isDel != 1)
                    throw new IOException("invalid sing, it have to get 0 or 1");
                if (isDel == 1){
                    String teacherName = ReadAndWrite.readShortString(inputStream);
                    listener.onAnswerReady(teacherName);
                }
                else
                    listener.onAnswerReady("");

            }
        });
    }

    // Send id of student to the server and delete the student if it exist,
    // using ObjectAnswerListener to receives the answer.
    public static void delStudent(final String id, final ObjectAnswerListener listener){
        connect(new ConnectionListener() {
            @Override
            public void onConnect(InputStream inputStream, OutputStream outputStream) throws IOException {
                outputStream.write(DEL_STUDENT_BY_ID);
                ReadAndWrite.writeShortString(outputStream, id);
                int isDel = inputStream.read();
                if (isDel != 0 && isDel != 1)
                    throw new IOException("invalid sing, it have to get 0 or 1");
                if (isDel == 1){
                    String studentName = ReadAndWrite.readShortString(inputStream);
                    listener.onAnswerReady(studentName);
                }
                else
                    listener.onAnswerReady("");

            }
        });
    }

    // Send number of car to the server and delete the car if it exist,
    // using ObjectAnswerListener to receives the answer.
    public static void delCar(final String numberOfCar, final ObjectAnswerListener listener){
        connect(new ConnectionListener() {
            @Override
            public void onConnect(InputStream inputStream, OutputStream outputStream) throws IOException {
                outputStream.write(DEL_CAR_BY_CAR_NUMBER);
                ReadAndWrite.writeShortString(outputStream, numberOfCar);
                int isDel = inputStream.read();
                if (isDel != 0 && isDel != 1)
                    throw new IOException("invalid sing, it have to get 0 or 1");
                if (isDel == 1) {
                    String carName = ReadAndWrite.readShortString(inputStream);
                    listener.onAnswerReady(carName);
                }
                else
                    listener.onAnswerReady("");

            }
        });
    }
    // Send teacher as Object to the server and add the teacher if its not exist,
    // using ObjectAnswerListener to receives the answer.
    public static void addTeacher(final Teacher teacher, ObjectAnswerListener<Boolean> listener){
        connect(new ConnectionListener() {
            @Override
            public void onConnect(InputStream inputStream, OutputStream outputStream) throws IOException {
                outputStream.write(ADD_TEACHER);
                teacher.write(outputStream);
                int isAdd = inputStream.read();
                if (isAdd != 0 && isAdd != 1)
                    throw new IOException("invalid sing, it have to get 0 or 1");
                if (isAdd == 1)
                    listener.onAnswerReady(true);
                else
                    listener.onAnswerReady(false);
            }
        });
    }

    // Send student as Object to the server and add the student if its not exist,
    // using ObjectAnswerListener to receives the answer.
    public static void addStudent(final Student student, ObjectAnswerListener<Integer> listener){
        connect(new ConnectionListener() {
            @Override
            public void onConnect(InputStream inputStream, OutputStream outputStream) throws IOException {
                outputStream.write(ADD_STUDENT);
                student.write(outputStream);
                int isAdd = inputStream.read();
                if (isAdd != 0 && isAdd != 1 && isAdd != -1)
                    throw new IOException("invalid sing, it have to get -1, 0 or 1");
                listener.onAnswerReady(isAdd); //-1:the car not exist, 0:the student already exist, 1:the student added.
            }
        });
    }

    // Send car as Object to the server and add the car if its not exist,
    // using ObjectAnswerListener to receives the answer.
    public static void addCar(final Car car, ObjectAnswerListener<Integer> listener){
        connect(new ConnectionListener() {
            @Override
            public void onConnect(InputStream inputStream, OutputStream outputStream) throws IOException {
                outputStream.write(ADD_CAR);
                car.write(outputStream);
                int isAdd = inputStream.read();
                if (isAdd != 0 && isAdd != 1 && isAdd != -1)
                    throw new IOException("invalid sing, it have to get -1, 0 or 1");
                listener.onAnswerReady(isAdd); //-1:the teacher not exist, 0:the car already exist, 1:the car added.
            }
        });
    }

    // Send teacher as Object to the server and change the teacher if it exist,
    // using ObjectAnswerListener to receives the answer.
    public static void changeTeacher(final Teacher teacher, ObjectAnswerListener<Integer> listener){
        connect(new ConnectionListener() {
            @Override
            public void onConnect(InputStream inputStream, OutputStream outputStream) throws IOException {
                outputStream.write(CHANGE_TEACHER);
                teacher.write(outputStream);
                int isChange = inputStream.read();
                if (isChange != 0 && isChange != 1)
                    throw new IOException("invalid sing, it have to get 0 or 1");
                if (isChange == 1)
                    listener.onAnswerReady(1);
                else
                    listener.onAnswerReady(0);
            }
        });
    }


    // Send student as Object to the server and change the student if it exist,
    // using ObjectAnswerListener to receives the answer.
    public static void changeStudent(final Student student, ObjectAnswerListener<Integer> listener){
        connect(new ConnectionListener() {
            @Override
            public void onConnect(InputStream inputStream, OutputStream outputStream) throws IOException {
                outputStream.write(CHANGE_STUDENT);
                student.write(outputStream);
                int isChange = inputStream.read();
                if (isChange != 0 && isChange != 1)
                    throw new IOException("invalid sing, it have to get 0 or 1");
                if (isChange == 1)
                    listener.onAnswerReady(1);
                else
                    listener.onAnswerReady(0);
            }
        });
    }


    // Send id of student and his green form as Object to the server and change the green form if the student is exist,
    // using ObjectAnswerListener to receives the answer.
    public static void changeGreenForm(final String idOfStudent, final GreenForm greenForm, ObjectAnswerListener<Integer> listener){
        connect(new ConnectionListener() {
            @Override
            public void onConnect(InputStream inputStream, OutputStream outputStream) throws IOException {
                outputStream.write(CHANGE_GREEN_FORM);
                ReadAndWrite.writeShortString(outputStream, idOfStudent);
                greenForm.write(outputStream);
                int isChange = inputStream.read();
                if (isChange != 0 && isChange != 1)
                    throw new IOException("invalid sing, it have to get 0 or 1");
                if (isChange == 1)
                    listener.onAnswerReady(1);
                else
                    listener.onAnswerReady(0);
            }
        });
    }

    //Performs a connection to the server before performing an operation,
    // using "ConnectionListener" to return communication variables
    public static void connect(ConnectionListener listener){
        InputStream inputStream = null;
        OutputStream outputStream = null;
        Socket socket = null;
        try{
            socket = new Socket(HOST, PORT);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            listener.onConnect(inputStream,outputStream);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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

    //Used for the "connect" function and helps to communicate from any function that invites "connect"
    public interface ConnectionListener{
            void onConnect(InputStream inputStream, OutputStream outputStream) throws IOException;
    }

    //Is used to receive the relevant data from the function that sends a call request and information from the server
    //The functions that request information reside in "Action"
    public interface ObjectAnswerListener<T>{
        void onAnswerReady(T object);
    }
}
