import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Wilks {
    // ---- Коэффициенты Уилкса (Wilks Original), многочлен 5-й степени ----
    static final double[] WILKS_MEN = {
            47.46178854, 8.472061379, 0.07369410346,
            -0.001395833811, 7.07665973070743E-06, -1.20804336482315E-08
    };
    static final double[] WILKS_WOMEN = {
            -125.4255398, 13.71219419, -0.03307250631,
            -0.001050400051, 9.38773881462799E-06, -2.3334613884954E-08
    };

    // ---- Коэффициенты DOTS 2020, многочлен 4-й степени ----
    static final double[] DOTS_MEN = {
            -307.75076, 24.0900756, -0.1918759221, 0.0007391293, -0.000001093
    };
    static final double[] DOTS_WOMEN = {
            -57.96288, 13.6175032, -0.1126655, 0.0005158568, -0.0000010706
    };

    // ---- Коэффициенты IPF GL, классический пауэрлифтинг ----
    static final double IPF_GL_A_MEN = 1199.72839;
    static final double IPF_GL_B_MEN = 1025.18162;
    static final double IPF_GL_C_MEN = 0.009210;
    static final double IPF_GL_A_WOMEN = 610.32796;
    static final double IPF_GL_B_WOMEN = 1045.59282;
    static final double IPF_GL_C_WOMEN = 0.03048;

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("1 - Вычислить результат одного спортсмена");
        System.out.println("2 - Рассчитать итоговый результат соревнований");
        System.out.print("Выберите пункт: ");
        String choice = scanner.next();

        if (choice.equals("1")) {
            calculateSingleAthlete();
        } else if (choice.equals("2")) {
            calculateCompetition();
        } else {
            System.out.println("Неверный выбор.");
        }

        scanner.close();
    }

    // ---------- Один спортсмен ----------

    static void calculateSingleAthlete() {
        Athlete athlete = readAthlete();

        System.out.println();
        System.out.printf("Очки Уилкса=%.3f%n", athlete.wilksScore);
        System.out.printf("Очки DOTS=%.3f%n", athlete.dotsScore);
        System.out.printf("Коэффициент IPF=%.6f%n", athlete.ipfCoefficient);
    }

    // ---------- Соревнования (несколько спортсменов) ----------

    static void calculateCompetition() {
        List<Athlete> athletes = new ArrayList<>();

        while (true) {
            Athlete athlete = readAthlete();
            athletes.add(athlete);

            System.out.print("Добавить ещё спортсмена? (да/нет): ");
            String answer = scanner.next().trim().toLowerCase();

            if (!answer.equals("да")) {
                break;
            }
        }

        athletes.sort(Comparator.comparingDouble((Athlete a) -> a.wilksScore).reversed());

        printTable(athletes);
    }

    static Athlete readAthlete() {
        System.out.print("Введите фамилию спортсмена: ");
        String lastName = scanner.next();

        System.out.print("Введите имя спортсмена: ");
        String firstName = scanner.next();

        char sex = readSex();
        double bodyWeight = readPositiveDouble("Введите вес спортсмена (кг): ");
        double result = readPositiveDouble("Введите результат, сумму (кг): ");

        double[] wilksCoeffs = (sex == 'M') ? WILKS_MEN : WILKS_WOMEN;
        double wilksCoefficient = 600 / evaluatePolynomial(bodyWeight, wilksCoeffs);
        double wilksScore = result * wilksCoefficient;

        double[] dotsCoeffs = (sex == 'M') ? DOTS_MEN : DOTS_WOMEN;
        double dotsCoefficient = 500 / evaluatePolynomial(bodyWeight, dotsCoeffs);
        double dotsScore = result * dotsCoefficient;

        double ipfCoefficient = calculateIpfGlCoefficient(bodyWeight, sex);

        return new Athlete(lastName, firstName, bodyWeight, result, sex,
                wilksScore, dotsScore, ipfCoefficient);
    }

    // ---------- Таблица результатов ----------

    static void printTable(List<Athlete> athletes) {
        System.out.println();
        System.out.printf("%-4s %-25s %-8s %-10s %-5s %-10s %-10s %-12s%n",
                "№", "ФИО", "Вес", "Результат", "Пол", "Wilks", "DOTS", "IPF коэфф.");
        System.out.println("-".repeat(90));

        for (int i = 0; i < athletes.size(); i++) {
            Athlete a = athletes.get(i);
            String fullName = a.lastName + " " + a.firstName;
            System.out.printf("%-4d %-25s %-8.1f %-10.1f %-5s %-10.2f %-10.2f %-12.6f%n",
                    i + 1, fullName, a.bodyWeight, a.result, a.sex,
                    a.wilksScore, a.dotsScore, a.ipfCoefficient);
        }
    }

    // ---------- Формулы ----------

    static double evaluatePolynomial(double x, double[] coeffs) {
        double result = 0;
        for (int i = 0; i < coeffs.length; i++) {
            result += coeffs[i] * Math.pow(x, i);
        }
        return result;
    }

    static double calculateIpfGlCoefficient(double bodyWeight, char sex) {
        double A = (sex == 'M') ? IPF_GL_A_MEN : IPF_GL_A_WOMEN;
        double B = (sex == 'M') ? IPF_GL_B_MEN : IPF_GL_B_WOMEN;
        double C = (sex == 'M') ? IPF_GL_C_MEN : IPF_GL_C_WOMEN;

        return 100 / (A - B * Math.exp(-C * bodyWeight));
    }

    // ---------- Ввод данных ----------

    static char readSex() {
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

    static double readPositiveDouble(String prompt) {
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

    // ---------- Класс спортсмена ----------

    static class Athlete {
        String lastName;
        String firstName;
        double bodyWeight;
        double result;
        char sex;
        double wilksScore;
        double dotsScore;
        double ipfCoefficient;

        Athlete(String lastName, String firstName, double bodyWeight, double result, char sex,
                double wilksScore, double dotsScore, double ipfCoefficient) {
            this.lastName = lastName;
            this.firstName = firstName;
            this.bodyWeight = bodyWeight;
            this.result = result;
            this.sex = sex;
            this.wilksScore = wilksScore;
            this.dotsScore = dotsScore;
            this.ipfCoefficient = ipfCoefficient;
        }
    }
}