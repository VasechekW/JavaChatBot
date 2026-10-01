package main.java.chatbot.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {

    @Test
    @DisplayName("Конструктор сохраняет размеры и элементы")
    void constructor_storesDimensionsAndElements() {
        double[][] src = {
                {1, 2, 3},
                {4, 5, 6}
        };

        Matrix m = new Matrix(src);

        assertEquals(2, m.getRows());
        assertEquals(3, m.getCols());
        assertEquals(1, m.get(0, 0));
        assertEquals(6, m.get(1, 2));
    }

    @Test
    @DisplayName("Конструктор копирует данные: изменение исходного массива не влияет на матрицу")
    void constructor_defensivelyCopiesSource() {
        double[][] src = {{1, 2}, {3, 4}};
        Matrix m = new Matrix(src);

        src[0][0] = 999;

        assertEquals(1, m.get(0, 0),
                "Матрица должна хранить собственную копию данных");
    }

    @Test
    @DisplayName("Конструктор бросает исключение для null")
    void constructor_throwsOnNull() {
        assertThrows(IllegalArgumentException.class, () -> new Matrix(null));
    }

    @Test
    @DisplayName("Конструктор бросает исключение для пустого массива")
    void constructor_throwsOnEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Matrix(new double[0][0]));
    }

    @Test
    @DisplayName("Конструктор бросает исключение для 'рваной' матрицы (разная длина строк)")
    void constructor_throwsOnRaggedMatrix() {
        double[][] ragged = {
                {1, 2, 3},
                {4, 5}
        };
        assertThrows(IllegalArgumentException.class, () -> new Matrix(ragged));
    }

    @Test
    @DisplayName("Сложение: 2x2 + 2x2")
    void add_sumsSameSizeMatrices() {
        Matrix a = new Matrix(new double[][]{
                {1, 2},
                {3, 4}
        });
        Matrix b = new Matrix(new double[][]{
                {5, 6},
                {7, 8}
        });

        Matrix r = a.add(b);

        assertEquals(6, r.get(0, 0));
        assertEquals(8, r.get(0, 1));
        assertEquals(10, r.get(1, 0));
        assertEquals(12, r.get(1, 1));
    }

    @Test
    @DisplayName("Сложение: результат имеет те же размеры")
    void add_resultHasSameDimensions() {
        Matrix a = new Matrix(new double[][]{{1, 2, 3}});
        Matrix b = new Matrix(new double[][]{{4, 5, 6}});

        Matrix r = a.add(b);

        assertEquals(1, r.getRows());
        assertEquals(3, r.getCols());
    }

    @Test
    @DisplayName("Сложение с нулевой матрицей не меняет значения")
    void add_withZeroMatrix_isIdentity() {
        Matrix a = new Matrix(new double[][]{
                {1, -2},
                {3.5, 4}
        });
        Matrix zero = new Matrix(new double[][]{
                {0, 0},
                {0, 0}
        });

        Matrix r = a.add(zero);

        assertEquals(a.get(0, 0), r.get(0, 0));
        assertEquals(a.get(0, 1), r.get(0, 1));
        assertEquals(a.get(1, 0), r.get(1, 0));
        assertEquals(a.get(1, 1), r.get(1, 1));
    }

    @Test
    @DisplayName("Сложение коммутативно: A + B == B + A")
    void add_isCommutative() {
        Matrix a = new Matrix(new double[][]{{1, 2}, {3, 4}});
        Matrix b = new Matrix(new double[][]{{5, 6}, {7, 8}});

        Matrix ab = a.add(b);
        Matrix ba = b.add(a);

        for (int i = 0; i < ab.getRows(); i++) {
            for (int j = 0; j < ab.getCols(); j++) {
                assertEquals(ab.get(i, j), ba.get(i, j));
            }
        }
    }

    @Test
    @DisplayName("Сложение: исключение при разных размерах")
    void add_throwsOnDifferentSizes() {
        Matrix a = new Matrix(new double[][]{{1, 2}, {3, 4}});
        Matrix b = new Matrix(new double[][]{{1, 2, 3}, {4, 5, 6}});
        int[] sizeB = {b.getRows(), b.getCols()};

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> a.rowsColsCheck(sizeB));
        assertTrue(ex.getMessage().contains("Размеры матриц не совпадают, сложение невозможно."), "Сообщение должно объяснять причину");
    }

    @Test
    @DisplayName("Результат add — новый объект, не связанный с исходными")
    void add_returnsNewObject() {
        Matrix a = new Matrix(new double[][]{{1, 2}, {3, 4}});
        Matrix b = new Matrix(new double[][]{{5, 6}, {7, 8}});

        Matrix r = a.add(b);

        assertNotSame(a, r);
        assertNotSame(b, r);
    }
}
