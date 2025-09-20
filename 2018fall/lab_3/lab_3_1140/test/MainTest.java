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
    public void test_2() throws IOException {
        try (Redirect redirect = Redirect.from(DATA_PATH, "01.data.in", "01.test.out")) {
            Main.output(Main.cal(Main.read()));
            final Pair<String, String> p = redirect.compare_double("01.data.out", "01.test.out");
            Assertions.assertEquals(p.getFirst().length(), p.getSecond().length());
            Assertions.assertEquals(p.getFirst(), p.getSecond());
        }
        try (Redirect redirect = Redirect.from(DATA_PATH, "01.data.in", "01.test.out")) {
            Main.output(Main.cal(Main.reader()));
            final Pair<String, String> p = redirect.compare_double("01.data.out", "01.test.out");
            Assertions.assertEquals(p.getFirst().length(), p.getSecond().length());
            Assertions.assertEquals(p.getFirst(), p.getSecond());
        }
    }

    @Test
    public void test_negtives() throws IOException {

        try (Redirect redirect = Redirect.from(DATA_PATH, "02.data.in", "02.test.out")) {
            Main.output(Main.cal(Main.reader()));
            final Pair<String, String> p = redirect.compare_double("02.data.out", "02.test.out");
            Assertions.assertEquals(p.getFirst().length(), p.getSecond().length());
            Assertions.assertEquals(p.getFirst(), p.getSecond());
        }
    }

    @Test
    public void test_zeros() throws IOException {
        try (Redirect redirect = Redirect.from(DATA_PATH, "03.data.in", "03.test.out")) {
            Main.output(Main.cal(Main.reader()));
            final Pair<String, String> p = redirect.compare_double("03.data.out", "03.test.out");
            Assertions.assertEquals(p.getFirst().length(), p.getSecond().length());
            Assertions.assertEquals(p.getFirst(), p.getSecond());
        }
    }
}
