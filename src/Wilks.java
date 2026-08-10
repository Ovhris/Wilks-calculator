
import java.util.Scanner;

public class Wilks {
    // Коэффициенты для мужчин
    static final double a = 47.46178854;
    static final double b = 8.472061379;
    static final double c = 0.07369410346;
    static final double d = -0.001395833811;
    static final double e =  7.07665973070743E-06;
    static final double f =  -1.20804336482315E-08;

    // Коэффициенты для женщин
    static final double a1 =  -125.4255398;
    static final double b1 = 13.71219419;
    static final double c1 =  -0.03307250631;
    static final double d1 = -0.001050400051;
    static final double e1 =  9.38773881462799E-06;
    static final double f1 = -2.3334613884954E-08;

    static char sex;
    static double sporsmenWeight;
    static double liftedWeight;

    static double coefficientWilksMen;
    static double coefficientWilksWomen;
    static double wilcsScore;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ввод и проверка пола
        while (true) {
            System.out.print("Введите пол спортсмена (M/F или М/Ж): ");
            String sexInput = scanner.next().toUpperCase();

            if (sexInput.equals("M") || sexInput.equals("М")) {
                sex = 'M';
                break;
            } else if (sexInput.equals("F") || sexInput.equals("Ж")) {
                sex = 'F';
                break;
            } else {
                System.out.println("Ошибка: введите M/F (англ.) или М/Ж (рус.).");
            }
        }

        // Ввод и проверка веса спортсмена
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

        // Ввод и проверка поднятого веса
        while (true) {
            System.out.print("Введите поднятый вес (сумму): ");
            String liftedInput = scanner.next();

            try {
                double liftedValue = Double.parseDouble(liftedInput);

                if (liftedValue <= 0) {
                    System.out.println("Ошибка: поднятый вес должен быть положительным числом больше нуля.");
                } else {
                    liftedWeight = liftedValue;
                    break;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Ошибка: введите корректное число (например, 250.5).");
            }
        }

        // Расчёт коэффициента в зависимости от пола
        if (sex == 'M') {
            double x = sporsmenWeight;
            double denominator = a + b * x + c * Math.pow(x, 2) + d * Math.pow(x, 3)
                    + e * Math.pow(x, 4) + f * Math.pow(x, 5);
            coefficientWilksMen = 600 / denominator;
            wilcsScore = liftedWeight * coefficientWilksMen;

            System.out.println("Коэффициент вилкса=" + coefficientWilksMen);
        } else {
            double x = sporsmenWeight;
            double denominator = a1 + b1 * x + c1 * Math.pow(x, 2) + d1 * Math.pow(x, 3)
                    + e1 * Math.pow(x, 4) + f1 * Math.pow(x, 5);
            coefficientWilksWomen = 600 / denominator;
            wilcsScore = liftedWeight * coefficientWilksWomen;

            System.out.println("Коэффициент вилкса=" + coefficientWilksWomen);
        }

        System.out.println("Очки Уилкса=" + wilcsScore);

        scanner.close();
    }
}
