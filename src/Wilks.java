
import java.util.Scanner;

public class Wilks {
    // Коэффициенты для мужчин

    // Коэффициенты для мужчин: {a, b, c, d, e, f}
    static final double[] MEN_COEFFICIENTS = {
            47.46178854,
            8.472061379,
            0.07369410346,
            -0.001395833811,
            7.07665973070743E-06,
            -1.20804336482315E-08
    };

    // Коэффициенты для женщин: {a, b, c, d, e, f}
    static final double[] WOMEN_COEFFICIENTS = {
            -125.4255398,
            13.71219419,
            -0.03307250631,
            -0.001050400051,
            9.38773881462799E-06,
            -2.3334613884954E-08
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        char sex = readSex(scanner);
        double bodyWeight = readPositiveDouble(scanner, "Введите вес спортсмена: ");
        double liftedWeight = readPositiveDouble(scanner, "Введите поднятый вес (сумму): ");

        double[] coeffs = (sex == 'M') ? MEN_COEFFICIENTS : WOMEN_COEFFICIENTS;

        double wilksCoefficient = calculateWilksCoefficient(bodyWeight, coeffs);
        double wilksScore = liftedWeight * wilksCoefficient;

        System.out.println("Коэффициент Уилкса=" + wilksCoefficient);
        System.out.println("Очки Уилкса=" + wilksScore);

        scanner.close();
    }

    // Вычисление коэффициента Уилкса по весу и набору коэффициентов
    static double calculateWilksCoefficient(double bodyWeight, double[] c) {
        double denominator = c[0]
                + c[1] * bodyWeight
                + c[2] * Math.pow(bodyWeight, 2)
                + c[3] * Math.pow(bodyWeight, 3)
                + c[4] * Math.pow(bodyWeight, 4)
                + c[5] * Math.pow(bodyWeight, 5);

        return 600 / denominator;
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