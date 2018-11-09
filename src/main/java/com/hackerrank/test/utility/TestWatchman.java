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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import static org.apache.commons.lang.exception.ExceptionUtils.getStackTrace;
import org.junit.AssumptionViolatedException;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 *
 * @author Abhimanyu Singh
 * @author abhimanyusingh@hackerrank.com
 * @version 1.0
 * @since 1.0
 */
public class TestWatchman extends TestWatcher {

    public static final TestWatchman watchman = new TestWatchman();
    private static final Map<String, List<TestObject>> tests = new HashMap<>();

    private String clazz = null;
    private TestObject test = null;

    /**
     *
     * @param clazz test class
     */
    public void registerClass(Class<?> clazz) {
        this.clazz = clazz.getCanonicalName();

        TestWatchman.tests.put(this.clazz, new ArrayList<>());
    }

    /**
     *
     * @param description test description
     */
    @Override
    protected void starting(Description description) {
        Long start = System.nanoTime();

        this.test = new TestObject(description.getMethodName(), start, 0l, "failed");
    }

    /**
     *
     * @param description test description
     */
    @Override
    protected void finished(Description description) {
        this.test.setEnd(System.nanoTime());

        TestWatchman.tests.get(this.clazz).add(this.test);
    }

    /**
     *
     * @param e assumption violation exception
     * @param description test description
     */
    @Override
    protected void skipped(AssumptionViolatedException e, Description description) {
        this.test.setTrace(getStackTrace(e));
        this.test.setEnd(System.nanoTime());
        this.test.setStatus("skipped");

        TestWatchman.tests.get(this.clazz).add(this.test);

        System.out.println("\n" + Color.RED + description.toString() + Color.RESET);
        System.out.println(
                String.join(
                        "\n",
                        Stream.of(getStackTrace(e).split("\n"))
                                .map(str -> Color.RED + str + Color.RESET)
                                .collect(toList())
                )
        );
    }

    /**
     *
     * @param e failure exception
     * @param description test description
     */
    @Override
    protected void failed(Throwable e, Description description) {
        this.test.setTrace(getStackTrace(e));
        this.test.setEnd(System.nanoTime());

        System.out.println("\n" + Color.RED + description.toString() + Color.RESET);
        System.out.println(
                String.join(
                        "\n",
                        Stream.of(getStackTrace(e).split("\n"))
                                .map(str -> Color.RED + str + Color.RESET)
                                .collect(toList())
                )
        );
    }

    /**
     *
     * @param description test description
     */
    @Override
    protected void succeeded(Description description) {
        this.test.setEnd(System.nanoTime());
        this.test.setStatus("succeeded");
    }

    /**
     *
     * @param clazz test class
     */
    public void createReport(Class<?> clazz) {
        ReportGenerator.createReport(clazz, TestWatchman.tests);
    }
}
