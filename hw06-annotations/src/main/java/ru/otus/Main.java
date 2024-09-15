package ru.otus;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        runTest(ClassTest.class);
    }

    private static void runTest(Class<ClassTest> clazz) {
        TestFrameworkHelper testFrameworkHelper = new TestFrameworkHelper();
        Map<Class, ArrayList<Method>> sortedMethods = testFrameworkHelper.getSortedMethods(clazz);
        for (Map.Entry<Class, ArrayList<Method>> sortedMethod: sortedMethods.entrySet()) {
            sortedMethod.getValue().forEach((Method method) -> testFrameworkHelper.callMethod(ClassTest.class, method));
        }
    }
}
