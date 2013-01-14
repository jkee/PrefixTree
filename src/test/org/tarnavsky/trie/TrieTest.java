package org.tarnavsky.trie;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author jkee
 */

public class TrieTest {

    private static final StringFunnel stringFunnel = new StringFunnel();
    private static class StringFunnel implements Funnel<String, Character> {
        @Override
        public Character getPrefix(String object, int level) {
            if (level + 1 > object.length()) return null;
            return object.charAt(level);
        }

        @Override
        public String create(List<Character> path) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Character character : path) {
                stringBuilder.append(character);
            }
            return stringBuilder.toString();
        }

        @Override
        public String toString() {
            return "StringFunnel";
        }
    }

    @Test
    public void testAdding() throws Exception {
        Trie<String, Character> trie = Trie.newTrie(stringFunnel);
        assertTrue(trie.add("a"));
        assertTrue(trie.contains("a"));
        assertTrue(trie.add("abc"));
        assertTrue(trie.contains("abc"));
        assertTrue(!trie.contains("ab"));
        assertTrue(trie.add("ab"));
        assertTrue(trie.contains("ab"));
        assertFalse("a", trie.add("a"));
        assertFalse("ab", trie.add("ab"));
        assertFalse("abc", trie.add("abc"));
        System.out.println(trie);
    }
}
