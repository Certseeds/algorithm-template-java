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
}
