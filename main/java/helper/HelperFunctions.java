package main.java.helper;

import java.util.Random;

public class HelperFunctions {
    private Random random;

    public static <T extends Enum<?>> String randomFromEnum(Class<T> enumClass) {
        final Random random = new Random();
        T[] enumValues = enumClass.getEnumConstants();
        return enumValues[random.nextInt(enumValues.length)].name();
    }

    public static boolean randomBoolean(int trueProbability, int falseProbability) {
        int total = trueProbability + falseProbability;
        Random random = new Random();
        int randInt = random.nextInt(total);
        return randInt < trueProbability;
    }
}
