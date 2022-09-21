import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;
import java.lang.Math;

public class Main {
    private static String[] splitWords(String text) {
        if (text == null) return null;

        Pattern p1 = Pattern.compile("(\\W+)");
        String[] res = p1.split(text);

        if (res.length == 0) res = null;
        return res;
    }

    private static void count_vowel_parts(String[] unique_words, Map<String, Integer> map) {
        String vowels = "aeiouy";
        for (int i = 0; i < unique_words.length; i++) {
            if (unique_words[i].length() > 30) unique_words[i] = unique_words[i].substring(0, 30);

            Double vowel_counter = 0.0;
            for (int j = 0; j < unique_words[i].length(); j++){
                for (int k = 0; k < 6; k++){
                    if (unique_words[i].charAt(j) == vowels.charAt(k)) {
                        vowel_counter++;
                        break;
                    }
                }
            }
            Double vowel_part_double =  100000*vowel_counter/unique_words[i].length(); // 10^-3 precision
            int vowel_part_integer = (int) Math.ceil(vowel_part_double);
            map.put(unique_words[i], vowel_part_integer);
        }
    }

    private static HashMap<String, Integer> sort_by_vowel_part(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> list_of_words = new LinkedList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(list_of_words, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> x : list_of_words) {
            temp.put(x.getKey(), x.getValue());
        }
        return temp;
    }

    private static void print_result(Map<String, Integer> map) {
        for (Map.Entry<String, Integer> en : map.entrySet()) {
            double vowel_part_value = (double) en.getValue();
            System.out.println("Word = " + en.getKey() +
                    ", Vowel part (10^-3) = " + vowel_part_value/1000 + "%");
        }
    }

    public static void main(String[] args)throws IOException {
        Path fileName = Path.of("src/main/resources/test.txt");
        String str = Files.readString(fileName);
        String[] unique_words = splitWords(str);

        Map<String, Integer> map = new HashMap<>();
        count_vowel_parts(unique_words, map);
        map = sort_by_vowel_part(map);
        print_result(map);
    }
}