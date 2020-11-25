package com.company;

import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        File file1 = new File(System.getProperty("user.dir") + "/src/com/company", "a.txt");
        File file2 = new File(System.getProperty("user.dir") + "/src/com/company", "b.txt");

        if (!file1.exists()) {
            System.out.println("Файл " + file1.getName() + " не существует по заданному пути");
            System.exit(0);
        }
        if (!file2.exists()) {
            System.out.println("Файл " + file2.getName() + " не существует по заданному пути");
            System.exit(0);
        }

        if (!file1.canRead()) {
            System.out.println("Файл " + file1.getName() + " невозможно прочитать");
            System.exit(0);
        }
        if (!file2.canRead()) {
            System.out.println("Файл " + file2.getName() + " невозможно прочитать");
            System.exit(0);
        }

        BufferedReader reader1 = null;
        BufferedReader reader2 = null;
        try {
            reader1 = new BufferedReader(new FileReader(file1));
            reader2 = new BufferedReader(new FileReader(file2));

            int c1 = -1, c2 = -1, number_differences = 0;
            ArrayList<String> differences = new ArrayList<String>();
            while ((c1 = reader1.read()) != -1 && (c2 = reader2.read()) != -1) {
                // 10 - перевод строки (разделитель строк в Unix)
                // 13 - возврат каретки (перевод строки)
                // 32 - пробел
                while (c1 == 10 || c1 == 13 || c1 == 32) {
                    c1 = reader1.read();
                    if (c1 == -1) break;
                }
                if (c1 == -1) break;

                while (c2 == 10 || c2 == 13 || c2 == 32) {
                    c2 = reader2.read();
                    if (c2 == -1) break;
                }
                if (c2 == -1) break;

                if (c1 != c2) {
                    if (number_differences < 10) {
                        differences.add(file1.getName() + ": " + ((char) c1) + "; " + file2.getName() + ": " + ((char) c2));
                    }
                    number_differences++;
                }
            }
            if (c1 == -1 && c2 == -1) System.out.println("Файлы закончились одновременно");
            else {
                if (c1 == -1)
                    System.out.println("Файл " + file1.getName() + " закончился раньше, чем " + file2.getName());
                else System.out.println("Файл " + file2.getName() + " закончился раньше, чем " + file1.getName());
            }

            if (number_differences > 0) {
                System.out.println("=============Отличающиеся символы==============");
                System.out.println("Колличество: " + number_differences);
                if (number_differences > 10) System.out.println("Первые 10 символов:");
                for (int i = 0; i < number_differences && i < 10; i++) System.out.println(differences.get(i));
            } else {
                System.out.println("=============Отличающиеся символы не найдены==============");
            }

            reader1.close();
            reader2.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader1 != null) {
                try {
                    reader1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader2 != null) {
                try {
                    reader2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}