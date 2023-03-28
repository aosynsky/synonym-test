package com.sinch.exercise;

import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SynonymousFilter {

  private final Map<String, String> synonyms = new HashMap<>();

  public SynonymousFilter(List<Pair<String, String>> synonymPairs) {
    for (var p : synonymPairs) {
      synonyms.put(p.getLeft(), p.getRight());
      synonyms.put(p.getRight(), p.getLeft());
    }
  }

  /**
   * Filter a list of sentence pairs, removing those that are not synonymous.
   *
   * @param sentencePairs A list of sentence pairs, either synonymous or not
   * @return A list consisting of only the synonymous sentence pairs
   */
  public List<Pair<String, String>> filter(List<Pair<String, String>> sentencePairs) {
    // TODO: implement
    return sentencePairs.stream()
        .filter(this::isSynonym)
        .collect(Collectors.toList());
  }

  private boolean isSynonym(Pair<String, String> s) {
    var l = s.getLeft().split(" ");
    var r = s.getRight().split(" ");
    if (l.length != r.length) {
      return false;
    }
    for (int i = 0; i < l.length; i++) {
      if (!l[i].equals(r[i]) && !synonyms.containsKey(l[i])) {
        return false;
      }
      if (synonyms.containsKey(l[i])
          && !r[i].equals(synonyms.get(l[i]))) {
        return false;
      }
    }
    return true;
  }

  /**
   * Filter a stream of sentence pairs, removing those that are not synonymous.
   *
   * @param candidateSentencePairs A stream of sentence pairs, either synonymous or not
   * @return A stream consisting of only the synonymous sentence pairs
   */
  public Stream<Pair<String, String>> filter(Stream<Pair<String, String>> candidateSentencePairs) {
    // TODO: implement
    return candidateSentencePairs
        .filter(this::isSynonym);
  }
}