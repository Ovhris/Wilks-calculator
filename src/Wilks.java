
import java.util.Scanner;

public class Wilks {

    // Коэффициенты для мужчин
    static final double a = -216.0475144;
    static final double b = 16.2606339;
    static final double c = -0.002388645;
    static final double d = -0.00113732;
    static final double e = 7.01863e-6;
    static final double f = -1.291e-8;

    // Коэффициенты для женщин
    static final double a1 = 594.31747775582;
    static final double b1 = -27.23842536447;
    static final double c1 = 0.82112226871;
    static final double d1 = -0.00930733913;
    static final double e1 = 4.731582e-5;
    static final double f1 = -9.054e-8;

    static char sex;
    static double sporsmenWeight;

    static double coefficientWilksMen;
    static double coefficientWilksWomen;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ввод и проверка пола
        while (true) {
            System.out.print("Введите пол спортсмена (M/W): ");
            String sexInput = scanner.next();

            if (sexInput.length() == 1 && (sexInput.charAt(0) == 'M' || sexInput.charAt(0) == 'W')) {
                sex = sexInput.charAt(0);
                break;
            } else {
                System.out.println("Ошибка: введите 'M' для мужчин или 'W' для женщин.");
            }
        }

        // Ввод и проверка веса
        while (true) {
            System.out.print("Введите вес спортсмена: ");
            String weightInput = scanner.next();

            try {
                double weightValue = Double.parseDouble(weightInput);

                if (weightValue <= 0) {
                    System.out.println("Ошибка: вес должен быть положительным числом больше нуля.");
                } else {
                    sporsmenWeight = weightValue;
                    break;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Ошибка: введите корректное число (например, 82.5).");
            }
        }

        // Расчёт коэффициента в зависимости от пола
        if (sex == 'M') {
            double x = sporsmenWeight;
            double denominator = a + b * x + c * Math.pow(x, 2) + d * Math.pow(x, 3)
                    + e * Math.pow(x, 4) + f * Math.pow(x, 5);
            coefficientWilksMen = 500 / denominator;

            System.out.println("Коэффициент вилкса=" + coefficientWilksMen);
        } else {
            double x = sporsmenWeight;
            double denominator = a1 + b1 * x + c1 * Math.pow(x, 2) + d1 * Math.pow(x, 3)
                    + e1 * Math.pow(x, 4) + f1 * Math.pow(x, 5);
            coefficientWilksWomen = 500 / denominator;

            System.out.println("Коэффициент вилкса=" + coefficientWilksWomen);
        }

        scanner.close();
    }
}





