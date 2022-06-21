import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class HorseTest {



    Horse horse = new Horse("Вишня", 3, 1);


    @Test
    @DisplayName("Проверить, что при передаче в конструктор первым параметром null, будет выброшено IllegalArgumentException. " +
            "Проверить, что при передаче в конструктор первым параметром null, " +
            "выброшенное исключение будет содержать сообщение Name cannot be null")
    void whenHorseException() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Horse horse = new Horse(null,0,0);
        });

        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\n", "\t"})
    @DisplayName("Проверить, что при передаче в конструктор первым параметром пустой строки или строки содержащей " +
            "только пробельные символы (пробел, табуляция и т.д.), будет выброшено IllegalArgumentException. " +
            "Чтобы выполнить проверку с разными вариантами пробельных символов, нужно сделать тест параметризованным;\n" +
            "Проверить, что при передаче в конструктор первым параметром пустой строки или строки содержащей " +
            "только пробельные символы (пробел, табуляция и т.д.), выброшенное исключение будет содержать " +
            "сообщение \"Name cannot be blank.\";")
    void whenEmptyHorseException(String argument) {
       if (argument.contains(" ")) {
           IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
               Horse horse = new Horse(argument,0,0);
           });

           assertEquals("Name cannot be blank.", exception.getMessage());
       }
    }

    @Test
    @DisplayName("Проверить, что при передаче в конструктор вторым параметром отрицательного числа, " +
            "будет выброшено IllegalArgumentException;\n" +
            "Проверить, что при передаче в конструктор вторым параметром отрицательного числа, " +
            "выброшенное исключение будет содержать сообщение \"Speed cannot be negative.\";")
    void whenNegativeSpeedHorseException() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("My",-1,0);
        });

        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    @DisplayName("Проверить, что при передаче в конструктор третьим параметром отрицательного числа, " +
            "будет выброшено IllegalArgumentException;\n" +
            "Проверить, что при передаче в конструктор третьим параметром отрицательного числа, " +
            "выброшенное исключение будет содержать сообщение \"Distance cannot be negative.\";")
    void whenNegativeDistanceHorseException() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Horse horse = new Horse("My",1,-1);
        });

        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    @DisplayName("Проверить, что метод возвращает строку, которая была передана первым параметром в конструктор")
    void getName() {
        assertEquals("Вишня", horse.getName());
    }

    @Test
    @DisplayName("Проверить, что метод возвращает число, которое было передано вторым параметром в конструктор")
    void getSpeed() {
        assertEquals(3, horse.getSpeed());
    }

    @Test
    @DisplayName("Проверить, что метод возвращает число, которое было передано третьим параметром в конструктор;\n" +
            "Проверить, что метод возвращает ноль, если объект был создан с помощью конструктора с двумя параметрами;")
    void getDistance() {
        assertEquals(1, horse.getDistance());
        if (!horse.equals(horse)) {
            assertEquals(0, horse.getDistance());
        }
    }

    @Test
    @DisplayName("Проверить, что метод вызывает внутри метод getRandomDouble с параметрами 0.2 и 0.9. " +
            "Для этого нужно использовать MockedStatic и его метод verify;\n" +
            "- Проверить, что метод присваивает дистанции значение высчитанное по формуле: " +
            "distance + 31 * getRandomDouble(0.2, 0.9). " +
            "Для этого нужно замокать getRandomDouble, чтобы он возвращал определенные значения, " +
            "которые нужно задать параметризовав тест.;")
    void moveTest() {
        try (MockedStatic<Horse> utilities = Mockito.mockStatic(Horse.class)) {
            utilities.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(0.999);
            horse.move();
            utilities.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @CsvSource({
            "2.0"
    })
    void moveSetRightDistance(double speed){
        Horse horse = new Horse("Вишня", 31, 2);
        MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class);
        horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(speed);
        double expected = horse.getDistance() + 31 * speed;

        horse.move();

        assertEquals(expected, horse.getDistance());
    }



}
