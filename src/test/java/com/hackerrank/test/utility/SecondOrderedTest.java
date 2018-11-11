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
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;

/**
 * @author Abhimanyu Singh
 * @author abhimanyusingh@hackerrank.com
 */
public class SecondOrderedTest {

    public SecondOrderedTest() {}

    @BeforeClass
    public static void setUpClass() {
        TestWatchman.watchman.registerClass(SecondOrderedTest.class);
    }

    @AfterClass
    public static void tearDownClass() {
        TestWatchman.watchman.createReport(SecondOrderedTest.class);
    }

    @Test
    public void startTest() {
        JUnitCore.runClasses(SecondOrderedTest.TestHelper.class);

        assertTrue(TestWatchman.watchman.allTestsSucceeded());
    }

    @RunWith(OrderedTestRunner.class)
    public static class TestHelper {

        @Rule public TestWatcher watchman = TestWatchman.watchman;

        @Test
        @Order(1)
        public void firstTest() {
            Double a = (double) 1 / 0;

            assertNotEquals(a, 0, 0.01);
        }

        @Test
        @Ignore
        @Order(2)
        public void secondTest() {
            assertFalse(true);
        }

        @Test
        @Order(3)
        public void thirdTest() {
            assertFalse(
                    ResultMatcher.matchJsonArray(
                            "[{\"a\": 2}, {\"b\": 4}]", "[{\"b\": 2}, {\"a\": 4}]", false));
        }

        @Test
        @Order(4)
        public void fourthTest() {
            assertFalse(
                    ResultMatcher.matchJsonArray(
                            "[{\"a\": 4}, {\"b\": 2}]", "[{\"b\": 2}, {\"a\": 4}]", false));
        }

        @Test
        @Order(5)
        public void fifthTest() {
            assertTrue(
                    ResultMatcher.matchJsonArrayIgnoreOrder(
                            "[{\"a\": 4}, {\"b\": 2}]", "[{\"b\": 2}, {\"a\": 4}]", false));
        }
    }
}
