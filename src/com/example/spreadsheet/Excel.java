package com.example.spreadsheet;

import java.util.*;

public class Excel {
    HashMap<String,Cell> excelSheet;
    int rows;
    int columns;

    public Excel(int rows, int columns) {
        this.columns = columns;
        this.rows = rows;
        excelSheet =  new HashMap<>();
    }

    public void fillExcel(Scanner scanner) {
        for(int i = 0; i < rows; i++) {
            for(int j = 1; j <= columns; j++) {
                int c = i + 65;
                String input = scanner.nextLine();
                Cell presentCell = new Cell(input);
                excelSheet.put(Character.toString((char) c ) + j,presentCell);
            }
        }
    }

    public void printExcelValues() {
        TreeMap<String, Cell> sorted = new TreeMap<>();
        sorted.putAll(excelSheet);
        System.out.println(columns + " " + rows);
        for(Map.Entry<String,Cell> item :sorted.entrySet()){
            System.out.println(String.format("%.5f",item.getValue().value));
        }

    }

    public void evaluateSheet() {
        for(Map.Entry<String,Cell> item :excelSheet.entrySet()){
            evaluateCell(item.getValue(),null);
        }
    }

    private Double evaluateCell(Cell cell, Set<Cell> currentStack) {
        if(currentStack == null) {
            currentStack = new LinkedHashSet<Cell>();
        }

        if(cell.isEvaluated) {
            // do nothing. Just return the value.
        } else if(!cell.isEvaluated && !currentStack.contains(cell)) {
            currentStack.add(cell);

            String[] expValues = cell.cellValue.split(" ");
            Stack<Double> operands = new Stack<>();

            for(int i=0;i<expValues.length;i++) {
                String presentValue = expValues[i];

                if (presentValue.equalsIgnoreCase("+")) {
                    operands.push(operands.pop() + operands.pop());
                }
                else if (presentValue.equalsIgnoreCase("*")) {
                    operands.push(operands.pop() * operands.pop());
                }
                else if (presentValue.equalsIgnoreCase("/")){
                    double operand1 = operands.pop();
                    double operand2 = operands.pop();
                    if(operand1 == 0) {
                        System.out.println("Divide by zero error");
                        System.exit(1);
                    }
                    operands.push( operand2 / operand1);
                }
                else if (presentValue.equalsIgnoreCase("-")){
                    double operand1 = operands.pop();
                    double operand2 = operands.pop();
                    operands.push( operand2 - operand1);
                }
                else if (presentValue.equalsIgnoreCase("++")) {
                    double operand = operands.pop();
                    operands.push(++operand);
                }
                else if (presentValue.equalsIgnoreCase("--")) {
                    double operand = operands.pop();
                    operands.push(--operand);
                }
                else if (Util.isDoubleValue(presentValue)) {
                    operands.push(Double.parseDouble(presentValue));
                }
                else {
                    operands.push(evaluateCell(excelSheet.get(presentValue),currentStack));
                }
            }
            cell.value = operands.pop();
            cell.isEvaluated = true;
        } else {
            System.out.println("Cyclic dependency detected while evaluating spreadsheet");
            System.out.println("Stack trace: ");
            for(Cell item : currentStack) {
                System.out.println("cell with content : " + item.cellValue);
            }
            System.exit(1);
        }

        return cell.value;
    }
}
