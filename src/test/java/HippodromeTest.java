import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HippodromeTest {

    @Test
    @DisplayName("Проверить, что при передаче в конструктор null, будет выброшено IllegalArgumentException. " +
            "Проверить, что при передаче в конструктор null, выброшенное исключение будет содержать сообщение " +
            "\"Horses cannot be null.\"")
    void whenHippodromeException() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Hippodrome hippodrome = new Hippodrome(null);
        });

        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    @DisplayName("Проверить, что при передаче в конструктор пустого списка, будет выброшено IllegalArgumentException;\n" +
            "Проверить, что при передаче в конструктор пустого списка, " +
            "выброшенное исключение будет содержать сообщение \"Horses cannot be empty.\";")
    void whenHippodromeListException() {
        List<Horse> horses = new ArrayList<>();
        if (horses.isEmpty()) {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                Hippodrome hippodrome = new Hippodrome(horses);
            });
            assertEquals("Horses cannot be empty.", exception.getMessage());
        }
    }

    @Test
    @DisplayName("Проверить, что метод возвращает список, который содержит те же объекты и в той же последовательности, " +
            "что и список который был передан в конструктор. " +
            "При создании объекта Hippodrome передай в конструктор список из 30 разных лошадей;")
    void getHorsesTest() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(i,new Horse(String.valueOf(i),i+0.1));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    @DisplayName("Проверить, что метод вызывает метод move у всех лошадей. " +
            "При создании объекта Hippodrome передай в конструктор список из 50 моков лошадей и возпользуйся методом verify.")
    void testMove() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Horse horse = Mockito.mock(Horse.class);
            horses.add(i,horse);

        }
        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();

        for (Horse curHorse: horses) {
            Mockito.verify(curHorse).move();
        }
    }

    @Test
    @DisplayName("Проверить, что метод возвращает лошадь с самым большим значением distance")
    void getWinnerTest() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            horses.add(i,new Horse(String.valueOf(i),i,i+0.1));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(horses.stream()
                .max(Comparator.comparing(Horse::getDistance))
                .get(),hippodrome.getWinner());
    }

}
