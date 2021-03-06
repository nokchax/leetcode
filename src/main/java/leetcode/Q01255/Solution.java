package leetcode.Q01255;

import java.util.HashMap;
import java.util.Map;

public class Solution {
    private Map<String, Integer> cache = new HashMap<>();
    private Map<String, int[]> cacheCounts = new HashMap<>();

    public int maxScoreWords(String[] words, char[] letters, int[] score) {
        int[] letterCounts = createCountMap(letters);

        return Math.max(0, backtracking(0, 0, words, score, letterCounts));
    }


    private int backtracking(int curSum, int putIdx, String[] words, int[] scores, int[] counts) {
        if(putIdx >= words.length) {
            return curSum;
        }

        int max = curSum;

        //skip cur
        max = Math.max(max, backtracking(curSum, putIdx + 1, words, scores, counts));

        if(isValid(words[putIdx], counts)) {
            //skip
            //- counts;
            int[] tempCount = getCounts(words[putIdx]);
            calculateCounts(counts, tempCount, false);
            int score = calculateScore(words[putIdx], scores);

            max = Math.max(max, backtracking(curSum + score, putIdx + 1, words, scores, counts));

            calculateCounts(counts, tempCount, true);
        }

        return max;
    }

    private void calculateCounts(int[] counts, int[] tempCount, boolean isPlus) {
        for(int i = 0 ; i < 26 ; ++i) {
            int prevCount = counts[i];
            counts[i] = prevCount + (isPlus ? (tempCount[i]) : (-tempCount[i]));
        }
    }

    private int[] getCounts(String word) {
        if(cacheCounts.containsKey(word)) {
            return cacheCounts.get(word);
        }

        int[] count = new int[26];

        for(int i = 0 ; i < word.length() ; ++i) {
            count[word.charAt(i) - 'a']++;
        }

        cacheCounts.put(word, count);

        return count;
    }

    private boolean isValid(String word, int[] counts) {
        int[] count = new int[26];

        for(int i = 0 ; i < word.length() ; ++i) {
            count[word.charAt(i) - 'a']++;
        }

        for(int i = 0 ; i < 26 ; ++i) {
            if(counts[i] < count[i]) {
                return false;
            }
        }

        return true;
    }

    private int calculateScore(String word, int[] scores) {
        if(cache.containsKey(word)) {
            return cache.get(word);
        }

        int score = 0;

        for(char c : word.toCharArray()) {
            score += scores[c - 'a'];
        }
        cache.put(word, score);

        return score;
    }

    private int[] createCountMap(char[] letters) {
        int[] count = new int[26];

        for(char c : letters) {
            count[c - 'a']++;
        }

        return count;
    }
}
