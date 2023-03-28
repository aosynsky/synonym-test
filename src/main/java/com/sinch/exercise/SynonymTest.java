package com.sinch.exercise;

import com.google.common.collect.Streams;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SynonymTest {

  private static final List<Pair<String, String>> SYNONYMS =
      Arrays.asList(
          Pair.of("approval", "popularity"),
          Pair.of("rate", "ratings")
      );

  private static final List<Pair<String, String>> SENTENCES =
      Arrays.asList(
          Pair.of("trump approval rate", "trump popularity ratings"),
          Pair.of("trump approval rates", "trump popularity ratings"),
          Pair.of("trump approval rate", "popularity ratings trump"),
          //reverse left and right side
          Pair.of( "trump popularity ratings", "trump approval rate"),
          Pair.of("trump popularity ratings", "trump approval rates"),
          Pair.of( "popularity ratings trump", "trump approval rate"),
          //add pairs with different amount of words
          Pair.of( "trump approval", "trump approval rate"),
          Pair.of( "trump approval rate", "trump approval"),
          //empty strings
          Pair.of( "", "trump approval rate"),
          Pair.of( " ", "trump approval rate"),
          Pair.of( "trump approval rate",""),
          Pair.of( "trump approval rate"," ")

      );

  private static final List<Pair<String, String>> EXPECTATION = List.of(Pair.of("trump approval rate", "trump popularity ratings"),
      Pair.of( "trump popularity ratings", "trump approval rate"));



  public static void main(String[] args) {
    try {
      SynonymousFilter underTest = new SynonymousFilter(SYNONYMS);
      List<Pair<String, String>> result = underTest.filter(SENTENCES);
      if (result.isEmpty()) {
        result = underTest.filter(SENTENCES);
      }
      if (EXPECTATION.size() == result.size() && Streams.zip(
          EXPECTATION.stream(),
          result.stream(),
          (expected, actual) -> expected.getKey().equals(actual.getKey()) && expected.getValue()
              .equals(actual.getValue())).allMatch(eq -> eq)) {
        System.out.println("Correct result!");
      } else {
        System.out.println(
            "Expected: \n" + sentencesToString(EXPECTATION) + "\nBut was: \n" + sentencesToString(
                result));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static String sentencesToString(List<Pair<String, String>> sentences) {
    return "{" + String.join("},\n{", sentences.stream().map(
        pair -> "'" + String.join(" ", pair.getKey()) + "', '" + String.join(" ", pair.getValue())
            + "'").collect(
        Collectors.toList())) + "}";
  }
}
