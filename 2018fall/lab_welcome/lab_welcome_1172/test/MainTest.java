// SPDX-License-Identifier: AGPL-3.0-or-later 
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import tests.Pair;
import tests.Redirect;

import java.io.IOException;

@Slf4j
public final class MainTest {
    private static final String DATA_PATH = "resources/";
    private static final long begin_time = System.currentTimeMillis();

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
    public void test_cal() throws IOException {
        try (Redirect redirect = Redirect.from(DATA_PATH, "02.data.in", "02.test.out")) {
            Main.output(Main.cal(Main.reader()));
            final Pair<String, String> p = redirect.compare_double("02.data.out", "02.test.out");
            Assertions.assertEquals(p.getFirst().length(), p.getSecond().length());
            Assertions.assertEquals(p.getFirst(), p.getSecond());
        }
    }
}
