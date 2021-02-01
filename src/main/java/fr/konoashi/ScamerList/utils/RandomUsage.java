package fr.konoashi.ScamerList.utils;

import java.util.Random;

public class RandomUsage {

    public static long RandomLong(){
        Random random = new Random();
        return +random.nextInt(999999999 + 1);
    }
}
