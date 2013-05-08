package org.tarnavsky.trie;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author jkee
 */

public class TrieMapTest {

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
    public void testPut() throws Exception {
        TrieMap<String, Character, Integer> trie = TrieMap.newTrie(stringFunnel);
        assertNull(trie.put("a", 1));
        assertTrue(trie.contains("a"));
        assertNull(trie.put("abc", 3));
        assertTrue(trie.contains("abc"));
        assertTrue(!trie.contains("ab"));
        assertNull(trie.put("ab", 2));
        assertTrue(trie.contains("ab"));
        assertEquals(1, trie.put("a", 4).intValue());
        assertEquals(2, trie.put("ab", 5).intValue());
        assertEquals(3, trie.put("abc", 6).intValue());
        assertEquals(4, trie.get("a").intValue());
        assertEquals(5, trie.get("ab").intValue());
        assertEquals(6, trie.get("abc").intValue());
        System.out.println(trie);
    }
}
