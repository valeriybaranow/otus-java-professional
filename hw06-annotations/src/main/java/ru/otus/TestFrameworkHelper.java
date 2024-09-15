package ru.otus;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestFrameworkHelper {
    /**
     * Выполняет метод класса
     */
    public void callMethod(Class<ClassTest> testFrameworkClass, Method method, Object... args) {
        try {
            Constructor<ClassTest> constructor = testFrameworkClass.getConstructor();
            ClassTest object = constructor.newInstance();
            method.setAccessible(true);
            method.invoke(object, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Возвращает методы клавва отсортированные в зависимости от аннотиции метода в следующем порядке </pre>@Before @Test @After
     */
    public Map<Class, ArrayList<Method>> getSortedMethods(Class<ClassTest> testFrameworkClass) {
        Map<Class, ArrayList<Method>> sortedMethods = new LinkedHashMap<>();
        ArrayList<Method> listBefore = new ArrayList<>();
        ArrayList<Method> listTest = new ArrayList<>();
        ArrayList<Method> listAfter = new ArrayList<>();
        try {
            for (final Method declaredMethod : testFrameworkClass.getDeclaredMethods()) {
                addMethod(declaredMethod, listBefore, Before.class);
                addMethod(declaredMethod, listTest, Test.class);
                addMethod(declaredMethod, listAfter, After.class);
            }
            sortedMethods.put(Before.class, listBefore);
            sortedMethods.put(Test.class, listTest);
            sortedMethods.put(After.class, listAfter);
            return sortedMethods;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void addMethod(Method declaredMethod, ArrayList<Method> listMethod, Class classAnnotation) {
        Annotation[] annotations = declaredMethod.getAnnotationsByType(classAnnotation);
        if(annotations.length > 0) {
            listMethod.add(declaredMethod);
        }
    }

}
