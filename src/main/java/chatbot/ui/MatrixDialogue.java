package main.java.chatbot.ui;

import main.java.chatbot.io.UserInput;
import main.java.chatbot.io.UserOutput;
import main.java.chatbot.core.Matrix;

public class MatrixDialogue {

    private final UserInput input;
    private final UserOutput output;

    public MatrixDialogue(UserInput input, UserOutput output) {
        this.input = input;
        this.output = output;
    }

    public void run() {
        printMenu();
        try {
            output.print("Операция (1 — сложение): ");
            String op = input.readLine();

            Matrix result;


            switch (op) {
                case "1" : case "+":
                    int[] sizeA = readRowsCols("первую");
                    Matrix a = readMatrix(sizeA);
                    int[] sizeB = readRowsCols("вторую");
                    a.rowsColsCheck(sizeB);
                    Matrix b = readMatrix(sizeB);

                    result = a.add(b);
                    break;
                default:
                    output.println("Ошибка: неизвестная операция: " + op);
                    return;
            }

            output.println("Результат (" + result.getRows() + "x" + result.getCols() + "):");
            output.print(result.toString());

        }
        catch (IllegalArgumentException e) {
            output.println("Ошибка: " + e.getMessage());
        }
    }

    private void printMenu() {
        output.println("=== Операции с матрицами ===");
        output.println("1) Сложение");
        output.println("Формат: строка 'R C' (строки столбцы), затем R строк по C чисел через пробел.");
    }

    private int[] readRowsCols(String which){
        output.println("Введите " + which + " матрицу:");

        while (true) {
            int[] size = parseTwoInts(input.readLine(), "размер");
            output.print("Размер (строки столбцы): ");
            if (size[0] <= 0 || size[1] <= 0) {
                output.println("Размеры должны быть положительными");
                continue;
            }
            return size;
        }
    }

    private Matrix readMatrix(int[] size) {
        int r = size[0];
        int c = size[1];
        double[][] matrix = new double[r][c];
        for (int i = 0; i < r; i++) {
            matrix[i] = readRow(i+1, c);
        }
        return new Matrix(matrix);
    }

    private double[] readRow(int rowNumber, int c){
        while(true){
            output.print("Строка " + rowNumber + " (" + c + " чисел): ");
            String line = input.readLine();

            String[] tokens = line.split("\\s+");
            if (tokens.length != c){
                output.println("Ожидалось " + c + " чисел, получено " + tokens.length + ". Попробуйте снова.");
                continue;
            }

            double[] row = new double[c];
            boolean isOk = true;
            for (int j = 0; j<c; j++){
                try{
                    row[j] = Double.parseDouble(tokens[j]);
                }
                catch(NumberFormatException e){
                    output.println("Не число: \"" + tokens[j] + "\". Попробуйте снова.");
                    isOk = false;
                }
            }

            if(isOk){
                return row;
            }
        }
    }

    private int[] parseTwoInts(String line, String what) {
        String[] parts = line.split("\\s+");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Для " + what + " нужно два числа через пробел");
        }
        try {
            return new int[]{ Integer.parseInt(parts[0]), Integer.parseInt(parts[1]) };
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ожидались целые числа: \"" + line + "\"");
        }
    }
}