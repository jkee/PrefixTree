package org.tarnavsky.trie.callback;

/**
 * @author jkee
 */

public interface TrieMapCallbackFull<T, V> {
    void onNode(T node, V value, int level);
    void onMoveDown(int newLevel);
    void onMoveUp(int newLevel);
}
