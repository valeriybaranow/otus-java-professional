package ru.otus;

public class ClassTest {
    @Test
    public void test1() {
        System.out.print("Метод: test1 ");
        System.out.println("Экземпляр тестового класса: " + Integer.toHexString(hashCode()));
    }

    @After
    public void after1() {
        System.out.print("Метод: after1 ");
        System.out.println("Экземпляр тестового класса: " + Integer.toHexString(hashCode()));
    }



    @Before
    public void before1() {
        System.out.print("Метод: before1 ");
        System.out.println("Экземпляр тестового класса: " + Integer.toHexString(hashCode()));
    }

    @Test
    public void test2() {
        System.out.print("Метод: test2 ");
        System.out.println("Экземпляр тестового класса: " + Integer.toHexString(hashCode()));
    }

    @After
    public void after2() {
        System.out.print("Метод: after2 ");
        System.out.println("Экземпляр тестового класса: " + Integer.toHexString(hashCode()));
    }

    @Before
    public void before2() {
        System.out.print("Метод: before2 ");
        System.out.println("Экземпляр тестового класса: " + Integer.toHexString(hashCode()));
    }
}
