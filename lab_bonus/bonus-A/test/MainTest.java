// SPDX-License-Identifier: Apache-2.0

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import tests.Pair;
import tests.Redirect;


import java.io.*;
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
            final int x = random.nextInt(0x3f3f3f3f);
            final int y = random.nextInt(0x3f3f3f3f);
            Assertions.assertEquals(x + y, Main.cal(new int[]{x, y}));
        }
    }

    @Test
    public void test_2() throws IOException {
        try (Redirect redirect = Redirect.from(DATA_PATH,"01.data.in", "01.test.out")){
            Main.output(Main.cal(Main.reader()));
            final Pair<String, String> p = redirect.compare_double("01.data.out", "01.test.out");
            Assertions.assertEquals(p.getFirst().length(), p.getSecond().length());
            Assertions.assertEquals(p.getFirst(), p.getSecond());
        }
    }

    @Test
    public void test_3() throws IOException {
        try (Redirect redirect = Redirect.from(DATA_PATH, "01.data.in", "")) {
            Assertions.assertEquals(628, Main.cal(Main.reader()));
        }
    }
}
