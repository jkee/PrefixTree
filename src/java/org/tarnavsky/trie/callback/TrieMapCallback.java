package org.tarnavsky.trie.callback;

/**
 * @author jkee
 */

public interface TrieMapCallback<T, V> {
    void onNode(T node, V value, int level);
}
