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

import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;

/**
 *
 * @author Abhimanyu Singh
 * @author abhimanyusingh@hackerrank.com
 */
@RunWith(OrderedTestRunner.class)
public class ThirdOrderedTest {

    @Rule
    public TestWatcher watchman = TestWatchman.watchman;

    public ThirdOrderedTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        TestWatchman.watchman.registerClass(ThirdOrderedTest.class);
    }

    @AfterClass
    public static void tearDownClass() {
        TestWatchman.watchman.createReport(ThirdOrderedTest.class);
    }

    @Test
    @Order(1)
    public void firstTest() {
        assertFalse(false);
    }

    @Test
    public void secondTest() {
        assertTrue(false);
    }

    @Test
    public void thirdTest() {
        assertNull(null);
    }

    @Test
    @Order(2)
    public void fourthTest() {
        assertNotEquals(1, 4);
    }

    @Test
    public void fifthTest() {
        assertTrue(null == null);
    }

    @Test
    @Order(3)
    public void sixthTest() {
        assertFalse(true == false);
    }
}
