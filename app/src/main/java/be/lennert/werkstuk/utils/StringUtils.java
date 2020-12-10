package be.lennert.werkstuk.utils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import be.lennert.werkstuk.model.interfaces.IIngredient;

public  class StringUtils {

    //https://stackoverflow.com/questions/1892765/how-to-capitalize-the-first-character-of-each-word-in-a-string
    public static String toTitleCase(String givenString) {
        String[] arr = givenString.split(" ");
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < arr.length; i++) {
            sb.append(Character.toUpperCase(arr[i].charAt(0)))
                    .append(arr[i].substring(1)).append(" ");
        }
        return sb.toString().trim();
    }

    public static String roundToNearestTen(double nr){
        if(nr < 10) return Integer.toString((int) nr);
        int roundedNumber = (int) Math.round(nr/10.0) * 10;
        return Integer.toString(roundedNumber);
    }

    public static String generateStringId(String param){
        return param.replaceAll("\\s+", "");
    }

    public static String generateInternalImagePath(String param){
        return generateStringId(param) ;
    }

    //Concat all ingredients to 1 string seperated seperator
    public static String generateSummeryWithSeperator(List<IIngredient> ingredients, String seperator){
        StringBuilder ingredientsList = new StringBuilder(" ");
        if(ingredients.size() <= 0) return ingredientsList.toString();
        for (IIngredient i: ingredients) {
            ingredientsList.append(i.getName());
            ingredientsList.append(seperator);
        }
        return ingredientsList.toString();
    }


    public static String millisToString(long millis){
        long hours = millis / (1000 * 60 * 60);
        long minutes = (millis / (1000 * 60)) % 60;
        long seconds = (millis / 1000) % 60;
        return generateHMSTime(hours,minutes,seconds,":");
    }

    private static String generateHMSTime(long hours,long min, long s, String seperator){
        StringBuilder sb = new StringBuilder(64);
        sb.append(makeLong2Digits(hours));
        sb.append(seperator);
        sb.append(makeLong2Digits(min));
        sb.append(seperator);
        sb.append(makeLong2Digits(s));
        return sb.toString();
    }

    public static long timeToMilis(long h, long m, long s){
        long mili = 0;
        mili += TimeUnit.HOURS.toMillis(h);
        mili += TimeUnit.MINUTES.toMillis(m);
        mili += TimeUnit.SECONDS.toMillis(s);
        return mili;
    }

    private static String makeLong2Digits(long x){
        if(x>10)return String.valueOf(x);
        return "0"+ x;
    }
}
