import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class Main {

    private static final Logger logger = Logger.getLogger(Main.class);



    public static void main(String[] args) throws Exception {
        List<Horse> horses = List.of(
                new Horse("Буцефал", 2.4),
                new Horse("Туз Пик", 2.5),
                new Horse("Зефир", 2.6),
                new Horse("Пожар", 2.7),
                new Horse("Лобстер", 2.8),
                new Horse("Пегас", 2.9),
                new Horse("Вишня", 3)
        );
        PropertyConfigurator.configure("src/logs/log4j.properties");
        logger.info("Начало скачек. Количество участников: " + horses.size());
        Hippodrome hippodrome = new Hippodrome(horses);


        for (int i = 0; i < 1; i++) {
            hippodrome.move();
            watch(hippodrome);
            TimeUnit.MILLISECONDS.sleep(200);
        }

        String winnerName = hippodrome.getWinner().getName();
        System.out.println("Победил " + winnerName + "!");
        logger.info("Окончание скачек. Победитель: " + winnerName);
    }

    private static void watch(Hippodrome hippodrome) throws Exception {
        hippodrome.getHorses().stream()
                .map(horse -> ".".repeat((int) horse.getDistance()) + horse.getName())
                .forEach(System.out::println);
        System.out.println("\n".repeat(10));
    }
}
