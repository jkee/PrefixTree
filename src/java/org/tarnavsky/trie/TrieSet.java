package org.tarnavsky.trie;

import org.tarnavsky.trie.callback.TrieMapCallbackFull;
import org.tarnavsky.trie.callback.TrieSetCallbackFull;

/**
 * @author jkee
 */

public class TrieSet <T, P> {

    private final TrieMap<T, P, Object> map;
    private final static Object PRESENT = new Object();

    public static <T, P> TrieSet<T, P> newSet(Funnel<T, P> funnel) {
        return new TrieSet<T, P>(funnel);
    }

    private TrieSet(Funnel<T, P> funnel) {
        map = TrieMap.newTrie(funnel);
    }

    public boolean add(T object) {
        return map.put(object, PRESENT) == null;
    }

    public boolean contains(T object) {
        return map.get(object) == PRESENT;
    }

    public void forEach(final TrieSetCallbackFull<T> callback) {
        map.forEach(new TrieMapCallbackFull<T, Object>() {
            @Override
            public void onNode(T node, Object value, int level) {
                callback.onNode(node, level);
            }

            @Override
            public void onMoveDown(int newLevel) {
                callback.onMoveDown(newLevel);
            }

            @Override
            public void onMoveUp(int newLevel) {
                callback.onMoveUp(newLevel);
            }
        });
    }

}
