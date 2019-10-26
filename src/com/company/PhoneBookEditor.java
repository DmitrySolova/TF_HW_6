package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PhoneBookEditor {

    String[] stringsFromBook; //строки из файла
    String[] readyStrings; //строки для записи в файл
    String fileName;
    String phone;

    PhoneBookEditor(String fileName) { // fileName - имя файла с расширением
        this.fileName = fileName;
        this.StringsFromBookToArray();
        this.getNumberFromInput();
        this.setPhoneInBook();
    }

    public void StringsFromBookToArray() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            List<String> lines = new ArrayList<String>();
            String line;
            while ((line = reader.readLine()) != null){
                lines.add(line);
            }
            System.out.println(lines);
            String [] linesAsArray = lines.toArray(new String[lines.size()]);
            this.stringsFromBook = linesAsArray;
            reader.close();
        } catch (Throwable error) {
            System.out.println("Ошибка! - " + error);
        }
    }

    public void getNumberFromInput() {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите номер: ");
        String phone = in.nextLine();
        this.phone = phone;
        in.close();
    }

    public void setPhoneInBook() {
        try {
            boolean inPhoneBook;
            inPhoneBook = Arrays.stream(stringsFromBook).anyMatch(x -> x.equals(this.phone));
            if (inPhoneBook) {
                throw(new NumberIsAlreadyInBookException("Такой номер (" + this.phone + ") уже есть в телефонной книге, введите другой!"));
            } else {

                System.out.println("Добавляем телефон в файл");
                readyStrings = Arrays.copyOf(stringsFromBook, stringsFromBook.length + 1);
                readyStrings[readyStrings.length - 1] = this.phone;

                PrintWriter writer = new PrintWriter(new FileWriter(this.fileName));
                for (String phone : readyStrings) {
                    writer.println(phone);
                }
                writer.close();
            }
        } catch (NumberIsAlreadyInBookException e) {
            System.out.println(e.getMessage());
            // this.getNumberFromInput(); если "перезапустить" метод таким образом Scanner выводит
            // ошибку - мол нету строки на вход (он ее даже принимать во второй раз отказывается)
        } catch (IOException ex){
            System.out.println("Ошибка - " + ex.getMessage());
        }
    }
}
