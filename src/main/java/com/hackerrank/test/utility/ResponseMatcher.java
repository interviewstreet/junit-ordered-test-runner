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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Abhimanyu Singh
 * @author abhimanyusingh@hackerrank.com
 * @version 1.0
 * @since 1.0
 */
public class ResponseMatcher {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     *
     * @param responseString JSON array string
     * @param expectedString expected JSON array string
     * @return boolean describing whether comparison failed or succeeded
     */
    public boolean compareJsonArray(String responseString, String expectedString) {
        try {
            List<JsonNode> response = OBJECT_MAPPER.readValue(
                    responseString, new TypeReference<List<JsonNode>>() {
            }
            );

            List<JsonNode> expected = OBJECT_MAPPER.readValue(
                    expectedString, new TypeReference<List<JsonNode>>() {
            }
            );

            if (response.size() != expected.size()) {
                return false;
            }

            for (int i = 0; i < response.size(); i++) {
                if (!response.get(i).equals(expected.get(i))) {
                    System.out.println(Color.RED
                            + "Expected <"
                            + expectedString
                            + "> but was <"
                            + responseString
                            + ">."
                            + Color.RESET
                    );

                    return false;
                }
            }

            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    /**
     *
     * @param responseString JSON string
     * @param expectedString expected JSON string
     * @return boolean describing whether comparison failed or succeeded
     */
    public boolean compareJson(String responseString, String expectedString) {
        try {
            JsonNode response = OBJECT_MAPPER.readTree(responseString);
            JsonNode expected = OBJECT_MAPPER.readTree(expectedString);

            if (!response.equals(expected)) {
                System.out.println(Color.RED
                        + "Expected <"
                        + expectedString
                        + "> but was <"
                        + responseString
                        + ">."
                        + Color.RESET
                );

                return false;
            }

            return true;
        } catch (IOException ex) {
            return false;
        }
    }
}
