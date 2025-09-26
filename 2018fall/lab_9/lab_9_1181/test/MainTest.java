// SPDX-License-Identifier: AGPL-3.0-or-later 
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import tests.Pair;
import tests.Redirect;
import tests.Triple;


import java.io.*;
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
        final List<Triple<String, String, String>> tests = List.of(
            new Triple<>("01.data.in", "01.data.out", "01.test.out")
            , new Triple<>("02.data.in", "02.data.out", "02.test.out")
            , new Triple<>("03.data.in", "03.data.out", "03.test.out")
            , new Triple<>("04.data.in", "04.data.out", "04.test.out")
            , new Triple<>("05.data.in", "05.data.out", "05.test.out")
        );
        for (final Triple<String, String, String> test : tests) {
            try (Redirect redirect = Redirect.from(DATA_PATH, test.getFirst(), test.getThird())) {
                Main.output(Main.cal(Main.reader()));
                final Pair<String, String> p = redirect.compare_double(test.getSecond(), test.getThird());
                Assertions.assertEquals(p.getFirst().length(), p.getSecond().length());
                Assertions.assertEquals(p.getFirst(), p.getSecond());
            }
        }
    }

}
