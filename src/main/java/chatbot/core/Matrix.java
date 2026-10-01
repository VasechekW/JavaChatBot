package main.java.chatbot.core;

public class Matrix {
    private final double[][] matrix;
    private final int rows;
    private final int cols;

    public Matrix(double[][] source) {
        if (source == null || source.length == 0 || source[0].length == 0) {
            throw new IllegalArgumentException("Матрица не может быть пустой");
        }

        rows = source.length;
        cols = source[0].length;

        for (int i = 0; i < source.length; i++) {
            if (source[i].length != cols) {
                throw new IllegalArgumentException("Неверная длина строки");
            }
        }

        matrix = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(source[i], 0, matrix[i], 0, cols);
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public double get(int row, int col) {
        return matrix[row][col];
    }

    public Matrix add(Matrix matrix1) {
        double[][] res = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                res[i][j] = matrix[i][j]+matrix1.matrix[i][j];
            }
        }
        return new Matrix(res);
    }

    public void rowsColsCheck(int[] size2){
        if (this.getRows()!=size2[0] || this.getCols() != size2[1]){
            throw new IllegalArgumentException("Размеры матриц не совпадают, сложение невозможно.");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            sb.append("[");
            for (int j = 0; j < cols; j++) {
                sb.append(String.format("%7.2f ", matrix[i][j]));
            }
            sb.append("]\n");
        }
        return sb.toString();
    }
}
