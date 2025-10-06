// SPDX-License-Identifier: AGPL-3.0-or-later

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import tests.Pair;
import tests.Redirect;

import java.io.IOException;
import java.util.Random;

@Slf4j
public final class MainTest {

    private static final String DATA_PATH = "resources/";
    private static final long begin_time = System.currentTimeMillis();
    private static final Random random = new Random();

    @AfterAll
    public static void last_one() throws IOException {
        log.info("cost {} ms\n", System.currentTimeMillis() - begin_time);
    }

    @BeforeEach
    public void beforeEach(TestInfo testInfo) {
        log.info("{} begin", testInfo.getDisplayName());
    }

    @AfterEach
    public void afterEach(TestInfo testInfo) {
        log.info("{} end", testInfo.getDisplayName());
    }


    @Test
    public void test_0() {
        for (int i = 0; i < 114; i++) {
            int x = random.nextInt(0x7777);
            Assertions.assertEquals(Main.brute_force(x), Main.cal(x));
        }
    }

    @Test
    public void test_2() throws IOException {
        Assertions.assertEquals(1, Main.cal(1));

        Assertions.assertEquals(4, Main.cal(2));

        Assertions.assertEquals(38 * 115 * 58, Main.cal(114));
    }

    @Test
    public void test_3() throws IOException {
        for (int i = 1; i <= 3; i++) {
            try (Redirect redirect = Redirect.from(DATA_PATH, String.format("0%d.data.in", i), String.format("0%d.test.out", i))) {
                Main.output(Main.cal_warpper(Main.reader()));
                final Pair<String, String> p = redirect.compare_double(String.format("0%d.data.out", i), String.format("0%d.test.out", i));
                Assertions.assertEquals(p.getFirst().length(), p.getSecond().length());
                Assertions.assertEquals(p.getFirst(), p.getSecond());
            }
        }
    }

}
