# JUnit Ordered Test Runner

An application to provide support for executing tests in the specific order and generate the customized XML report.

## Sample Usage

- The `OrderedTestRunner` should be used to run the test. The order of each test can be set by the `@Order` annotation. The test with lower order value is run first.
- Run the tests with `TestWatcher` rule using `@Rule` annotation.
- Register the test class using the `registerClass` method of TestWatcher in the `@BeforeClass` setup.
- Finally, invoke the `createReport` method of TestWatcher in the `@AfterClass` setup.

For example,

```java
@RunWith(OrderedTestRunner.class)
public class SampleOrderedTest {

    @Rule
    public TestWatcher watchman = TestWatchman.watchman;

    public SampleOrderedTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        TestWatchman.watchman.registerClass(SampleOrderedTest.class);
    }

    @AfterClass
    public static void tearDownClass() {
        TestWatchman.watchman.createReport(SampleOrderedTest.class);
    }

    @Test
    @Order(1)
    public void firstTest() {
        assertTrue(0 == 0);
    }

    @Test
    @Order(2)
    public void secondTest() {
        assertEquals(1, 1);
    }

    @Test
    @Ordered(3)
    public void thirdTest() {
        assertNotEquals(1, null);
    }
}
```

Also,

- If a `Runner` is already being used to run the tests, then, an inner class can be used to run with `OrderedTestRunner`. Tests can be triggered using the `JUnitCore.runClasses` method.

For example,

```java
public class SampleOrderedTest {

    public SampleOrderedTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        TestWatchman.watchman.registerClass(SampleOrderedTest.class);
    }

    @AfterClass
    public static void tearDownClass() {
        TestWatchman.watchman.createReport(SampleOrderedTest.class);
    }

    @Test
    public void startTest() {
        JUnitCore.runClasses(TestHelper.class);
    }

    @RunWith(OrderedTestRunner.class)
    public static class TestHelper {

        @Rule
        public TestWatcher watchman = TestWatchman.watchman;

        @Test
        @Order(1)
        public void firstTest() {
            assertTrue(0 == 0);
        }

        @Test
        @Order(3)
        public void thirdTest() {
            assertNotEquals(1, null);
        }

        @Test
        @Order(2)
        public void secondTest() {
            assertEquals(1, 1);
        }
    }
}
```

And,

- When some tests are not assigned an order, then, these are run after the tests with an assigned order.

You can refer the given [test examples](src/test/java/com/hackerrank/test/utility) for better understanding of writing tests with `OrderedTestRunner`.

## Report Generation

- The `TestWatcher` generates an XML report in the `target/hackerrank-report` directory. The filename is `TEST-{test-class-canonical-name}.xml`.
- When running tests in a suite, suite report, as well as individual test reports, will be generated.

## Building Project

- Use `mvn clean build` to build the project.
- Use `mvn clean test` to run the tests.
- Use `mvn clean test -Dtest=TestSuite` to run the suite.
