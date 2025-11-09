// SPDX-License-Identifier: Apache-2.0

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import tests.Pair;
import tests.Redirect;
import tests.Sequence;

import java.io.IOException;
import java.util.List;
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
    public void test_1() throws IOException {
        final List<Sequence.FileTriple> files = new Sequence(1, 5).getFiles(true);
        for (Sequence.FileTriple file : files) {
            try (Redirect redirect = Redirect.from(DATA_PATH, file.datain(), file.testout())) {
                Main.output(Main.cal(Main.reader()));
                final Pair<String, String> p = redirect.compare_double(file.dataout(), file.testout());
                Assertions.assertEquals(p.getFirst().length(), p.getSecond().length());
                Assertions.assertEquals(p.getFirst(), p.getSecond());
            }
        }
    }
}
