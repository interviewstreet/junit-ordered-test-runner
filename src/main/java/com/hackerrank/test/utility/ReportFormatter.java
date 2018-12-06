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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * @author Abhimanyu Singh
 * @author abhimanyusingh@hackerrank.com
 * @version 1.0.1
 * @since 1.0.1
 */
public class ReportFormatter {
    /**
     * @param reportPath XML report path relative to project root
     * @throws java.io.IOException no such file exception
     * @throws org.jdom.JDOMException XML document build exception
     */
    public static void format(String reportPath) throws JDOMException, IOException {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(reportPath);

        Document report = (Document) builder.build(xmlFile);

        List<Element> testcases = report.getRootElement().getChildren("testcase");

        for (int i = 0; i < testcases.size(); i++) {
            Element testcase = testcases.get(i);

            String testname = testcase.getAttributeValue("name");
            String testclass = testcase.getAttributeValue("classname");

            Attribute test = new Attribute("name", testclass + "." + testname);

            testcase.setAttribute(test);

            System.out.println(testcase.getAttributeValue("name"));
        }

        XMLOutputter outputter = new XMLOutputter();

        outputter.setFormat(Format.getPrettyFormat());
        outputter.output(report, new FileWriter(xmlFile));
    }
}
