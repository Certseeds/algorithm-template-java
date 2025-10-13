// SPDX-License-Identifier: Apache-2.0
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
        try (Redirect redirect = Redirect.from(DATA_PATH,"01.data.in", "01.test.out")){
            Main.output(Main.cal(Main.reader()));
            final Pair<String, String> p = redirect.compare_double("01.data.out", "01.test.out");
            Assertions.assertEquals(p.getFirst().length(), p.getSecond().length());
            Assertions.assertEquals(p.getFirst(), p.getSecond());
        }
    }

    @Test
    public void testComputeLPSArray() {
        // Test case 1: "ababa" -> [0, 0, 1, 2, 3]
        Assertions.assertArrayEquals(new int[]{0, 0, 1, 2, 3}, Main.computeLPSArray("ababa"));

        // Test case 2: "abcde" -> [0, 0, 0, 0, 0]
        Assertions.assertArrayEquals(new int[]{0, 0, 0, 0, 0}, Main.computeLPSArray("abcde"));

        // Test case 3: "aaaaa" -> [0, 1, 2, 3, 4]
        Assertions.assertArrayEquals(new int[]{0, 1, 2, 3, 4}, Main.computeLPSArray("aaaaa"));

        // Test case 4: "abcabcabc" -> [0, 0, 0, 1, 2, 3, 4, 5, 6]
        Assertions.assertArrayEquals(new int[]{0, 0, 0, 1, 2, 3, 4, 5, 6}, Main.computeLPSArray("abcabcabc"));

        // Test case 5: "aabaacaadaa" -> [0, 1, 0, 1, 2, 0, 1, 2, 0, 1, 2] (Corrected)
        Assertions.assertArrayEquals(new int[]{0, 1, 0, 1, 2, 0, 1, 2, 0, 1, 2}, Main.computeLPSArray("aabaacaadaa"));

        // Test case 6: Empty string -> []
        Assertions.assertArrayEquals(new int[]{}, Main.computeLPSArray(""));
    }


    @Test
    public void testKmpScenarios() {
        // 1. No match
        Assertions.assertEquals(0, Main.kmpSearch("abcde", "xyz"));

        // 2. Simple match
        Assertions.assertEquals(1, Main.kmpSearch("abcde", "bcd"));

        // 3. Multiple matches
        Assertions.assertEquals(3, Main.kmpSearch("abababa", "aba"));

        // 4. Overlapping matches
        Assertions.assertEquals(4, Main.kmpSearch("aaaaa", "aa"));

        // 5. Pattern at the beginning
        Assertions.assertEquals(1, Main.kmpSearch("abcde", "ab"));

        // 6. Pattern at the end
        Assertions.assertEquals(1, Main.kmpSearch("abcde", "de"));

        // 7. Text and pattern are identical
        Assertions.assertEquals(1, Main.kmpSearch("abcde", "abcde"));

        // 8. Empty text
        Assertions.assertEquals(0, Main.kmpSearch("", "a"));

        // 9. Empty pattern (as per implementation, returns 0)
        Assertions.assertEquals(0, Main.kmpSearch("abcde", ""));

        // 10. Pattern is longer than text
        Assertions.assertEquals(0, Main.kmpSearch("abc", "abcd"));

        // 11. Complex case from sample
        Assertions.assertEquals(3, Main.kmpSearch("chenljnbwowowoo", "wo"));
    }
}
