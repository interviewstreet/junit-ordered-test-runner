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

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang.exception.ExceptionUtils.getStackTrace;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * @author Abhimanyu Singh
 * @author abhimanyusingh@hackerrank.com
 * @version 1.0.0
 * @since 1.0.0
 */
public class ReportGenerator {

    /**
     * @param clazz test class
     * @param suiteTests test methods in the class
     */
    public static void createReport(Class<?> clazz, Map<String, List<TestObject>> suiteTests) {
        String className = clazz.getCanonicalName();

        Document report = new Document();

        if (suiteTests.containsKey(className)) {
            report.setRootElement(
                    ReportGenerator.createReport(className, suiteTests.get(className)));
        } else {
            Element testsuites = new Element("testsuites");

            int totalTest = 0;
            int failures = 0;
            int errors = 0;
            double executionTime = 0.0;

            for (Entry<String, List<TestObject>> entry : suiteTests.entrySet()) {
                String name = entry.getKey();
                List<TestObject> tests = entry.getValue();

                Element testsuite = ReportGenerator.createReport(name, tests);

                totalTest += Integer.parseInt(testsuite.getAttribute("tests").getValue());
                failures += Integer.parseInt(testsuite.getAttribute("failures").getValue());
                errors += Integer.parseInt(testsuite.getAttribute("errors").getValue());
                executionTime += Double.parseDouble(testsuite.getAttribute("time").getValue());

                testsuites.addContent(testsuite);
            }

            Attribute name = new Attribute("name", className);
            Attribute testCount = new Attribute("tests", String.valueOf(totalTest));
            Attribute failureCount = new Attribute("failures", String.valueOf(failures));
            Attribute errorCount = new Attribute("errors", String.valueOf(errors));
            Attribute skippedCount = new Attribute("skipped", "0");
            Attribute time = new Attribute("time", NumberFormatter.stringValue(executionTime, 3));

            testsuites.setAttribute(name);
            testsuites.setAttribute(testCount);
            testsuites.setAttribute(failureCount);
            testsuites.setAttribute(errorCount);
            testsuites.setAttribute(skippedCount);
            testsuites.setAttribute(time);

            report.setRootElement(testsuites);
        }

        String reportPath = "target/hackerrank-reports";
        File file = new File(reportPath);

        if (!file.exists()) {
            file.mkdirs();
        }

        XMLOutputter outputter = new XMLOutputter();
        outputter.setFormat(Format.getPrettyFormat());

        try {
            outputter.output(
                    report, new FileWriter(new File(reportPath + "/TEST-" + className + ".xml")));
        } catch (IOException ex) {
            System.out.println(
                    String.join(
                            "\n",
                            Stream.of(getStackTrace(ex).split("\n"))
                                    .map(str -> Color.RED + str + Color.RESET)
                                    .collect(toList())));
        }
    }

    /**
     * @param className test class
     * @param tests test methods in the class
     * @return XML element for a test report
     */
    private static Element createReport(String className, List<TestObject> tests) {
        Element testsuite = new Element("testsuite");

        int failures = 0;
        int errors = 0;
        double executionTime = 0.0;

        for (TestObject test : tests) {
            Element testcase = new Element("testcase");

            Attribute classname = new Attribute("classname", className);
            Attribute name = new Attribute("name", test.getName());

            double testTime = test.getExecutionTime();
            executionTime += testTime;

            Attribute time = new Attribute("time", String.valueOf(testTime));

            testcase.setAttribute(classname);
            testcase.setAttribute(name);
            testcase.setAttribute(time);

            String status = test.getStatus();

            if (status.equals("failed")) {
                failures++;

                Element failure = new Element("failure");

                String trace = test.getTrace();

                if (trace != null) {
                    failure.addContent(trace);
                }

                testcase.addContent(failure);
            } else if (status.equals("skipped")) {
                errors++;

                Element error = new Element("error");

                String trace = test.getTrace();

                if (trace != null) {
                    error.addContent(trace);
                }

                testcase.addContent(error);
            }

            testsuite.addContent(testcase);
        }

        Attribute name = new Attribute("name", className);
        Attribute testCount = new Attribute("tests", String.valueOf(tests.size()));
        Attribute failureCount = new Attribute("failures", String.valueOf(failures));
        Attribute errorCount = new Attribute("errors", String.valueOf(errors));
        Attribute skippedCount = new Attribute("skipped", "0");
        Attribute time = new Attribute("time", NumberFormatter.stringValue(executionTime, 3));

        testsuite.setAttribute(name);
        testsuite.setAttribute(testCount);
        testsuite.setAttribute(failureCount);
        testsuite.setAttribute(errorCount);
        testsuite.setAttribute(skippedCount);
        testsuite.setAttribute(time);

        return testsuite;
    }
}
