package org.tarnavsky.trie;

/**
 * @author jkee
 */

public interface TrieCallback<T> {
    void onNode(T node, int level);
}
