package ru.otus;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ATMTest {
    private static ATM atm5750;
    private static ATM atm1000000;
    private static final int sumCash5750 = 5750;
    private static final int sumCash1000000 = 1000000;

    // Подготовительные мероприятия. Метод выполнится один раз, перед всеми тестами
    @BeforeAll
    public static void globalSetUp() throws CashException {
        // 5750 руб.
        atm5750 = new ATM();
        atm5750.putCash(createCash(sumCash5750));
        // 1000000
        atm1000000 = new ATM();
        atm1000000.putCash(createCash(sumCash1000000));
    }

    @Test
    void putCash() {
        assertThrows(CashException.class, () -> {
            createCash(-10000);
            createCash(0);
            createCash(10);
            createCash(10010);
        });

        // 5750 руб.
        for(Map.Entry<Denominations, Stack<Banknote>> cell : atm5750.getCells().entrySet()) {
            Denominations denomination = cell.getKey();
            int cellValueSize = cell.getValue().size();
            switch (denomination.toString()) {
                case "ONE_THOUSAND" -> assertEquals(5, cellValueSize);
                case "FIVE_HUNDRED", "FIFTY" -> assertEquals(1, cellValueSize);
                case "ONE_HUNDRED" -> assertEquals(2, cellValueSize);
                default -> {
                }
            }
        }

        // 1000000 руб.
        for(Map.Entry<Denominations, Stack<Banknote>> cell : atm1000000.getCells().entrySet()) {
            Denominations denomination = cell.getKey();
            int cellValueSize = cell.getValue().size();
            switch (denomination.toString()) {
                case "ONE_THOUSAND" -> assertEquals(1000000/5, cellValueSize);
                case "FIVE_HUNDRED", "FIFTY", "ONE_HUNDRED" -> assertEquals(0, cellValueSize);
                default -> {
                }
            }
        }
    }

    @Test
    void getCash() throws CashException {
        // 5750 руб.
        assertThrows(CashException.class, () -> atm5750.getCash(sumCash5750+1));
        Cash atm5750Cash = atm5750.getCash(sumCash5750);
        assertEquals(sumCash5750, atm5750Cash.getSum());
        assertEquals(0, atm5750.getSumCash());
        atm5750Cash.getBanknotes().forEach((banknote) -> {
            Denominations denominations = banknote.getDenomination();
            switch (denominations.toString()) {
                case "ONE_THOUSAND" -> assertEquals(5, atm5750Cash.getCountBanknote(denominations));
                case "FIVE_HUNDRED", "FIFTY" -> assertEquals(1, atm5750Cash.getCountBanknote(denominations));
                case "ONE_HUNDRED" -> assertEquals(2, atm5750Cash.getCountBanknote(denominations));
                default -> {
                }
            }
        });

        // 1000000 руб.
        assertThrows(CashException.class, () -> atm1000000.getCash(sumCash1000000+1));
        Cash atm1000000Cash = atm1000000.getCash(sumCash1000000);
        assertEquals(1000000, atm1000000Cash.getSum());
        assertEquals(0, atm1000000.getSumCash());
        atm1000000Cash.getBanknotes().forEach((banknote) -> {
            Denominations denominations = banknote.getDenomination();
            switch (denominations.toString()) {
                case "ONE_THOUSAND" -> assertEquals(sumCash1000000/5, atm1000000Cash.getCountBanknote(denominations));
                case "FIVE_HUNDRED", "FIFTY", "ONE_HUNDRED" -> assertEquals(0, atm1000000Cash.getCountBanknote(denominations));
                default -> {
                }
            }
        });
    }

    @Test
    void getSumCash() {
        assertEquals(sumCash5750, atm5750.getSumCash());
        assertEquals(sumCash1000000, atm1000000.getSumCash());
    }

    private static Cash createCash(int sumCash) throws CashException {
        if(sumCash%Denominations.FIFTY.getCash() != 0) {
            throw new CashException("Для формирования кэша принимаются купюры кратные " + Denominations.FIFTY.getCash(), sumCash);
        }
        if(sumCash <= 0) {
            throw new CashException("Нельзя сформировать кэш из наличности меньшей или равной нулю.", sumCash);
        }
        Cash cash = new Cash();
        for (Denominations denominations : Denominations.values()) {
            int cellCash = denominations.getCash();
            if(sumCash > 0) {
                int sumBanknotes = sumCash - sumCash%cellCash;
                int numBanknotes = sumBanknotes/cellCash;
                cash.createBanknotes(denominations, numBanknotes);
                sumCash -= sumBanknotes;
            }
        }
        System.out.printf("Сформирован кэш общей суммой %d руб. %n", cash.getSum());
        if(sumCash > 0) {
            System.out.printf("Выдана сдача в размере %d руб. %n", sumCash);
        }
        return cash;
    }
}