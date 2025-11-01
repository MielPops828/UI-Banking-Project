package utilities;

import java.util.Random;

public class ValueGenerator {
    private static Random rnd = new Random();

    public static String generatePostCode(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++){
            sb.append(rnd.nextInt(10));
        }
        return sb.toString();
    }

    public static String postCodeToName(String postCode){
        if (postCode == null || postCode.length() != 10){
            throw new IllegalArgumentException("Exc");
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < postCode.length(); i+=2){
            String pair = postCode.substring(i, i + 2);
            int num = Integer.parseInt(pair);
            int mapped = num % 26;
            char ch = (char) ('a' + mapped);
            sb.append(ch);
        }
        return sb.toString();
    }
}
