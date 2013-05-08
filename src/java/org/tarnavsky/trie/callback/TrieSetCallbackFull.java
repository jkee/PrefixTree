package org.tarnavsky.trie.callback;

/**
 * @author jkee
 */

public interface TrieSetCallbackFull<T> {
    void onNode(T node, int level);
    void onMoveDown(int newLevel);
    void onMoveUp(int newLevel);
}
