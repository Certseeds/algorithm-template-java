// SPDX-License-Identifier: AGPL-3.0-or-later

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
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
        Assertions.assertEquals(0xFFFFFFFF, Main.cal(new int[]{0xFFFFFFFE, 1}));
        Assertions.assertEquals(0x00000001, Main.cal(new int[]{0x00000000, 1}));
        Assertions.assertEquals(0xFFFFFFF0, Main.cal(new int[]{0xFFFFFFEF, 1}));
    }

    @Test
    public void test_1() {
        Assertions.assertEquals(0xFFFFFFFE, Main.cal(new int[]{0xFFFFFFFF, -1}));
        Assertions.assertEquals(0xFFFFFFFE, Main.cal(new int[]{0xFFFFFFFF, -1}));
        Assertions.assertEquals(0xFFFFFFEE, Main.cal(new int[]{0xFFFFFFEF, -1}));
        Assertions.assertEquals(0x12345678 << 1, Main.cal(new int[]{0x12345678, 0x12345678}));
    }

    @Test
    public void test_2() {
        for (int i = 0; i < 114; i++) {
            int x = random.nextInt(0x3f3f3f3f);
            int y = random.nextInt(0x3f3f3f3f);
            Assertions.assertEquals(x + y, Main.cal(new int[]{x, y}));
        }
    }

    @Test
    public void test_3() throws IOException {
        try (Redirect redirect = Redirect.from(DATA_PATH, "01.data.in", "")) {
            Assertions.assertEquals(628, Main.cal(Main.reader()));
        }
        try (Redirect redirect = Redirect.from(DATA_PATH, "01.data.in", "")) {
            Assertions.assertEquals(628, Main.cal(Main.reader()));
        }
    }

}
