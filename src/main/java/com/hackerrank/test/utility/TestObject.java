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

/**
 * @author Abhimanyu Singh
 * @author abhimanyusingh@hackerrank.com
 * @version 1.0.0
 * @since 1.0.0
 */
public class TestObject {

    private final String name;
    private final Long start;
    private Long end;
    private String status;
    private String trace;

    /**
     * @param name test name
     * @param start test start time in nanoseconds
     * @param end test finish time in nanoseconds
     * @param status test status
     */
    public TestObject(String name, Long start, Long end, String status) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.status = status;
        this.trace = null;
    }

    /** @return test name */
    public String getName() {
        return this.name;
    }

    /** @param end test finish time in nanoseconds */
    public void setEnd(Long end) {
        this.end = end;
    }

    /** @param status test status */
    public void setStatus(String status) {
        this.status = status;
    }

    /** @return test status */
    public String getStatus() {
        return this.status;
    }

    /** @param trace stack trace */
    public void setTrace(String trace) {
        this.trace = trace;
    }

    /** @return stack trace */
    public String getTrace() {
        return this.trace;
    }

    /** @return test duration in seconds */
    public double getExecutionTime() {
        return NumberFormatter.doubleValue((this.end - this.start) / 1_000_000_000.0, 3);
    }
}
