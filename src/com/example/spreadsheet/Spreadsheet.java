package com.example.spreadsheet;

import java.util.Scanner;

public class Spreadsheet {
    private static Scanner scanner = new Scanner(System.in);

    public static void main( String[] args) {
        System.out.println("Program says taking input and evaluating!!");
        int columns = Integer.parseInt(args[0]);
        int rows = Integer.parseInt(args[1]);

        Excel excel = new Excel(rows,columns);
        excel.fillExcel(scanner);

        excel.evaluateSheet();
        excel.printExcelValues();
    }
}
