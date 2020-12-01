package be.lennert.werkstuk.utils;

import java.util.List;

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
}
