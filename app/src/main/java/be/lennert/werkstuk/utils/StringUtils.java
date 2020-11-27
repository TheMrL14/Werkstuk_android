package be.lennert.werkstuk.utils;

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
}
