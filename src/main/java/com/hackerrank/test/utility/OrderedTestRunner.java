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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

/**
 * @author Abhimanyu Singh
 * @author abhimanyusingh@hackerrank.com
 * @version 1.0.0
 * @since 1.0.0
 */
public class OrderedTestRunner extends BlockJUnit4ClassRunner {

    /**
     * @param clazz test class
     * @throws InitializationError initialization error
     */
    public OrderedTestRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }

    /** @return test methods ordered by execution order */
    @Override
    protected List<FrameworkMethod> computeTestMethods() {
        Map<FrameworkMethod, Integer> orders = new HashMap();

        List<FrameworkMethod> methods = super.computeTestMethods();

        int maxOrder = 0;

        for (FrameworkMethod method : methods) {
            Order order = method.getAnnotation(Order.class);
            maxOrder = Math.max(maxOrder, order == null ? 0 : order.value());

            orders.put(method, order == null ? null : order.value());
        }

        final int order = maxOrder + 1;

        methods.forEach(method -> orders.computeIfAbsent(method, value -> order));

        return methods.stream()
                .sorted((f1, f2) -> orders.get(f1) - orders.get(f2))
                .collect(toList());
    }
}
