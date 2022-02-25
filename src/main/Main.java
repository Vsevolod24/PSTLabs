package main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        String[] array = {"Я купил машину", "Я крутой парень", "Возмите на работу", "Я же говорил что",
                "Я крутой парень", "Я купил машину", "Ну", "Возмите на работу", "Ведь как я говорил", "Я крутой парень"};
        Map<String, Integer> sentences = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            if (sentences.containsKey(array[i])) {
                int count = sentences.get(array[i]);
                sentences.put(array[i], count + 1);
            } else {
                sentences.put(array[i], 1);
            }
        }
        String sentence = null;
        int counter = 0;
        for (String temp : sentences.keySet()) {
            if (counter < sentences.get(temp)) {
                counter = sentences.get(temp);
                sentence = temp;
            }
        }
        System.out.println("Подсчёт каждой строки в массиве: ");
        System.out.println(sentences);
        System.out.println(sentence + " - данная строка встречается: " + counter + " раз");

        System.out.println("При помощи Stream APi: ");

        List<Map.Entry<String, Integer>> theMostOftenSentence = sentences.entrySet().stream().sorted((a, b) -> {
            return b.getValue() - a.getValue();
        }).limit(1).collect(Collectors.toList());
        for (Map.Entry<String, Integer> entry : theMostOftenSentence) {
            System.out.println("Строка: " + " '" + entry.getKey() + "' " + " встречается " + entry.getValue() + " раз");
        }

    }

}
