/*
 * Copyright 2018 HackerRank.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hackerrank.test.utility;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

/**
 * @author Abhimanyu Singh
 * @author abhimanyusingh@hackerrank.com
 */
public class FourthUnorderedTest {

    @Rule public TestWatcher watchman = TestWatchman.watchman;

    public FourthUnorderedTest() {}

    @BeforeClass
    public static void setUpClass() {
        TestWatchman.watchman.registerClass(FourthUnorderedTest.class);
    }

    @AfterClass
    public static void tearDownClass() {
        TestWatchman.watchman.createReport(FourthUnorderedTest.class);
    }

    @Test
    public void firstTest() {
        assertTrue(ResultMatcher.matchJson("{\"a\": 2, \"b\": 4}", "{\"a\": 2, \"b\": 4}", false));
    }

    @Test
    public void secondTest() {
        assertEquals(1, 1);
    }

    @Test
    public void thirdTest() {
        assertNotEquals(1, null);
    }
}
