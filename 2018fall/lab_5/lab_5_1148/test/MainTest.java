// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import tests.Pair;
import tests.Redirect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
public final class MainTest {
    private static final String DATA_PATH = "resources/";
    private static final long begin_time = System.currentTimeMillis();

    @AfterAll
    public static void last_one() {
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
            Main.main(new String[]{});
            final Pair<String, String> p = redirect.compare_double("01.data.out", "01.test.out");
            Assertions.assertEquals(p.getFirst(), p.getSecond());
        }
    }

    @Test
    public void test_2() throws IOException {
        try (Redirect redirect = Redirect.from(DATA_PATH, "02.data.in", "02.test.out")) {
            Main.main(new String[]{});
            final Pair<String, String> p = redirect.compare_double("02.data.out", "02.test.out");
            Assertions.assertEquals(p.getFirst().length(), p.getSecond().length());
            Assertions.assertEquals(p.getFirst(), p.getSecond());
        }
    }

    @Test
    void testCalSampleCases() {
        final List<Main.TestCase> inputs = new ArrayList<>();
        inputs.add(new Main.TestCase("ababab"));
        inputs.add(new Main.TestCase("abababa"));

        final List<Integer> expected = List.of(2, 1);
        final List<Integer> actual = Main.cal(inputs);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testNLessThan3() {
        final List<Main.TestCase> inputs = List.of(
                new Main.TestCase("a"),
                new Main.TestCase("ab")
        );
        final List<Integer> expected = List.of(0, 0);
        Assertions.assertEquals(expected, Main.cal(inputs));
    }

    @Test
    void testNoPunchline() {
        final List<Main.TestCase> inputs = List.of(
                new Main.TestCase("abcde")
        );
        final List<Integer> expected = List.of(0);
        Assertions.assertEquals(expected, Main.cal(inputs));
    }

    @Test
    void testOverlapRequiresShorterCandidate() {
        final List<Main.TestCase> inputs = List.of(
                new Main.TestCase("aaaaa")
        );
        final List<Integer> expected = List.of(1);
        Assertions.assertEquals(expected, Main.cal(inputs));
    }

    @Test
    void testLongestPossibleNonOverlapping() {
        final List<Main.TestCase> inputs = List.of(
                new Main.TestCase("abcabcabc")
        );
        final List<Integer> expected = List.of(3);
        Assertions.assertEquals(expected, Main.cal(inputs));
    }

    @Test
    void testComplexOverlap() {
        final List<Main.TestCase> inputs = List.of(
                new Main.TestCase("abacabacabac")
        );
        final List<Integer> expected = List.of(4);
        Assertions.assertEquals(expected, Main.cal(inputs));
    }

    @Test
    void testNoMiddleOccurrence() {
        final List<Main.TestCase> inputs = List.of(
                new Main.TestCase("abccba")
        );
        final List<Integer> expected = List.of(0);
        Assertions.assertEquals(expected, Main.cal(inputs));
    }

    @Test
    void testLongStringWithLongNextChain() {
        final List<Main.TestCase> inputs = List.of(
                new Main.TestCase("ababababab")
        );
        final List<Integer> expected = List.of(2);
        Assertions.assertEquals(expected, Main.cal(inputs));
    }
}
