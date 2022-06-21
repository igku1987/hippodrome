import com.sun.tools.javac.Main;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

public class MainTest {

    @Test
    @Disabled("Тест временно отключен")
    @Timeout(value = 21, unit = TimeUnit.SECONDS)
    void mainTest() throws Exception {
        String[] args = new String[]{};
        Main.main(args);
    }
}
