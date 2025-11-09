// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import tests.Pair;
import tests.Redirect;
import tests.Triple;

import java.io.IOException;
import java.util.List;

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
    public void test_1() throws IOException {
        try (Redirect redirect = Redirect.from(DATA_PATH, "01.data.in", "01.test.out")) {
            Main.main(null);
            final Pair<String, String> p = redirect.compare_double("01.data.out", "01.test.out");
            Assertions.assertEquals(p.getFirst().length(), p.getSecond().length());
            Assertions.assertEquals(p.getFirst(), p.getSecond());
        }
    }

    @Test
    public void test_2() throws IOException {
        final List<Triple<String, String, String>> tests = List.of(
            new Triple<>("02.data.in", "02.data.out", "02.test.out")
            , new Triple<>("03.data.in", "03.data.out", "03.test.out")
            , new Triple<>("04.data.in", "04.data.out", "04.test.out")
            , new Triple<>("05.data.in", "05.data.out", "05.test.out")
            , new Triple<>("07.data.in", "07.data.out", "07.test.out")
            , new Triple<>("08.data.in", "08.data.out", "08.test.out")
            , new Triple<>("09.data.in", "09.data.out", "09.test.out")

        );
        for (final Triple<String, String, String> test : tests) {
            try (Redirect redirect = Redirect.from(DATA_PATH, test.getFirst(), test.getThird())) {
                log.info("{} {} {}", test.getFirst(), test.getSecond(), test.getThird());
                Main.main(null);
                final Pair<String, String> p = redirect.compare_double(test.getSecond(), test.getThird());
                Assertions.assertEquals(p.getFirst().length(), p.getSecond().length());
                Assertions.assertEquals(p.getFirst(), p.getSecond());
            }
        }
    }

    @Test
    public void test_3() throws IOException {
        final List<Triple<String, String, String>> tests = List.of(
            new Triple<>("06.data.in", "06.data.out", "06.test.out")
        );
        for (final Triple<String, String, String> test : tests) {
            try (Redirect redirect = Redirect.from(DATA_PATH, test.getFirst(), test.getThird())) {
                log.info("{} {} {}", test.getFirst(), test.getSecond(), test.getThird());
                Main.main(null);
                final Pair<String, String> p = redirect.compare_double(test.getSecond(), test.getThird());
                Assertions.assertEquals(p.getFirst().length(), p.getSecond().length());
                Assertions.assertEquals(p.getFirst(), p.getSecond());
            }
        }
    }


    @Test
    public void test_4() throws IOException {
        final List<Triple<String, String, String>> tests = List.of(
            new Triple<>("10.data.in", "10.data.out", "10.test.out")
        );
        for (final Triple<String, String, String> test : tests) {
            try (Redirect redirect = Redirect.from(DATA_PATH, test.getFirst(), test.getThird())) {
                log.info("{} {} {}", test.getFirst(), test.getSecond(), test.getThird());
                Main.main(null);
                final Pair<String, String> p = redirect.compare_double(test.getSecond(), test.getThird());
                Assertions.assertEquals(p.getFirst().length(), p.getSecond().length());
                Assertions.assertEquals(p.getFirst(), p.getSecond());
            }
        }
    }


    @Test
    public void test_5() throws IOException {
        final List<Triple<String, String, String>> tests = List.of(
              new Triple<>("11.data.in", "11.data.out", "11.test.out")
            , new Triple<>("12.data.in", "12.data.out", "12.test.out")
            , new Triple<>("13.data.in", "13.data.out", "13.test.out")
            , new Triple<>("14.data.in", "14.data.out", "14.test.out")
            , new Triple<>("15.data.in", "15.data.out", "15.test.out")
        );
        for (final Triple<String, String, String> test : tests) {
            try (Redirect redirect = Redirect.from(DATA_PATH, test.getFirst(), test.getThird())) {
                log.info("{} {} {}", test.getFirst(), test.getSecond(), test.getThird());
                Main.main(null);
                final Pair<String, String> p = redirect.compare_double(test.getSecond(), test.getThird());
                Assertions.assertEquals(p.getFirst().length(), p.getSecond().length());
                Assertions.assertEquals(p.getFirst(), p.getSecond());
            }
        }
    }
}
