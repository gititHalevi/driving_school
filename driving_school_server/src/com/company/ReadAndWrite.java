package com.company;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Map;


//Used for writing and reading ints and Strings with open communication
//Used to write to and read from a file
public class ReadAndWrite {
    public static void writeShortString(OutputStream outputStream, String string) throws IOException {
        outputStream.write(string.length());
        outputStream.write(string.getBytes());

    }

    public static String readShortString(InputStream inputStream) throws IOException {
        int stringLength = inputStream.read();
        if (stringLength == -1)
            throw new IOException("it have to get some string!!");
        byte[] buffer = new byte[stringLength];
        int actuallyRead = inputStream.read(buffer);
        if (actuallyRead != stringLength)
            throw new IOException("Wrong length of string");
        return new String(buffer);
    }

    public static void writeInt(OutputStream outputStream, int intToWrite) throws IOException {
        byte[] intByte = new byte[4];
        ByteBuffer.wrap(intByte).putInt(intToWrite);
        outputStream.write(intByte);
    }

    public static int readInt(InputStream inputStream) throws IOException {
        byte[] intByte = new byte[4];
        int actuallyRead = inputStream.read(intByte);
        if (actuallyRead != 4)
            throw new IOException("it have to be number with 4 bytes!");
        return ByteBuffer.wrap(intByte).getInt();
    }

    //read teachers to map
    public static void readTeachersFromFile(Map<String, Teacher> teacherMap, File file) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            Teacher teacher = new Teacher(inputStream);
            teacherMap.put(teacher.getId(), teacher);
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
        }
    }

    //read cars to map
    public static void readCarsFromFile(Map<String, Car> carMap, File file) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            Car car = new Car(inputStream);
            carMap.put(car.getCarNumber(), car);
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
        }
    }

    //read student to map
    public static void readStudentsFromFile(Map<String, Student> studentMap, File file) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            Student student = new Student(inputStream);
            studentMap.put(student.getId(), student);

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
        }
    }

    //save teacherMap to file
    public static void writeTeacherToFile(Map<String, Teacher> teacherMap,File file) {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            for (Map.Entry<String, Teacher> teacher : teacherMap.entrySet()) {
                teacher.getValue().write(outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public static void writeCarsToFile(Map<String, Car> carMap,File file) {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            for (Map.Entry<String, Car> car : carMap.entrySet()) {
                car.getValue().write(outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public static void writeStudentToFile(Map<String, Student> studentMap,File file) {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            for (Map.Entry<String, Student> student : studentMap.entrySet())
                student.getValue().write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
/*

    public static void readAllFromFile(Map<String, Teacher> teacherMap,
                                       Map<String, Car> carMap, Map<String, Student> studentMap) {
        //read teachers to map
        File file = new File("C:\\Users\\User\\Desktop\\myJava.txt");
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            int length = readInt(inputStream);
            for (int i = 0; i < length; i++) {
                Teacher teacher = new Teacher(inputStream);
                teacherMap.put(teacher.getId(), teacher);
            }
            //read cars to map
            length = readInt(inputStream);
            for (int i = 0; i < length; i++) {
                Car car = new Car(inputStream);
                carMap.put(car.getCarNumber(), car);
            }
            //read student to map
            length = readInt(inputStream);
            for (int i = 0; i < length; i++) {
                Student student = new Student(inputStream);
                studentMap.put(student.getId(), student);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/