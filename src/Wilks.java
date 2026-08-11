
import java.util.Scanner;

public class Wilks {
    // ---- Коэффициенты Уилкса (Wilks Original), многочлен 5-й степени ----
    static final double[] WILKS_MEN = {
            47.46178854,
            8.472061379,
            0.07369410346,
            -0.001395833811,
            7.07665973070743E-06,
            -1.20804336482315E-08
    };

    static final double[] WILKS_WOMEN = {
            -125.4255398,
            13.71219419,
            -0.03307250631,
            -0.001050400051,
            9.38773881462799E-06,
            -2.3334613884954E-08
    };

    // ---- Коэффициенты DOTS 2020, многочлен 4-й степени ----
    static final double[] DOTS_MEN = {
            -307.75076,
            24.0900756,
            -0.1918759221,
            0.0007391293,
            -0.000001093
    };

    static final double[] DOTS_WOMEN = {
            -57.96288,
            13.6175032,
            -0.1126655,
            0.0005158568,
            -0.0000010706
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        char sex = readSex(scanner);
        double bodyWeight = readPositiveDouble(scanner, "Введите вес спортсмена: ");
        double liftedWeight = readPositiveDouble(scanner, "Введите поднятый вес (сумму): ");

        // Расчёт по Уилксу
        double[] wilksCoeffs = (sex == 'M') ? WILKS_MEN : WILKS_WOMEN;
        double wilksCoefficient = 600 / evaluatePolynomial(bodyWeight, wilksCoeffs);
        double wilksScore = liftedWeight * wilksCoefficient;

        // Расчёт по DOTS
        double[] dotsCoeffs = (sex == 'M') ? DOTS_MEN : DOTS_WOMEN;
        double dotsCoefficient = 500 / evaluatePolynomial(bodyWeight, dotsCoeffs);
        double dotsScore = liftedWeight * dotsCoefficient;

        System.out.println();
        System.out.printf("Коэффициент Уилкса=%.3f%n", wilksCoefficient);
        System.out.printf("Очки Уилкса=%.3f%n", wilksScore);
        System.out.println();
        System.out.printf("Коэффициент DOTS=%.3f%n", dotsCoefficient);
        System.out.printf("Очки DOTS=%.3f%n", dotsScore);

        scanner.close();
    }

    // Универсальное вычисление многочлена: c[0] + c[1]*x + c[2]*x^2 + ... + c[n]*x^n
    static double evaluatePolynomial(double x, double[] coeffs) {
        double result = 0;
        for (int i = 0; i < coeffs.length; i++) {
            result += coeffs[i] * Math.pow(x, i);
        }
        return result;
    }

    // Ввод и проверка пола
    static char readSex(Scanner scanner) {
        while (true) {
            System.out.print("Введите пол спортсмена (M/F или М/Ж): ");
            String input = scanner.next().toUpperCase();

            if (input.equals("M") || input.equals("М")) {
                return 'M';
            } else if (input.equals("F") || input.equals("Ж")) {
                return 'F';
            } else {
                System.out.println("Ошибка: введите M/F (англ.) или М/Ж (рус.).");
            }
        }
    }

    // Ввод и проверка положительного числа (вес спортсмена / поднятый вес)
    static double readPositiveDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.next().replace(',', '.');

            try {
                double value = Double.parseDouble(input);

                if (value <= 0) {
                    System.out.println("Ошибка: значение должно быть положительным числом больше нуля.");
                } else {
                    return value;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Ошибка: введите корректное число (например, 82.5).");
            }
        }
    }
}