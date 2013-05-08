package org.tarnavsky.trie;

import org.tarnavsky.trie.callback.TrieMapCallback;
import org.tarnavsky.trie.callback.TrieMapCallbackFull;

import java.util.ArrayList;

/**
 * @author jkee
 */

public class TrieMap<T, P, V> {

    private final Funnel<T, P> funnel;
    private final Node<T, P, V> root;

    public static <T, P, V> TrieMap<T, P, V> newTrie(Funnel<T, P> funnel) {
        return new TrieMap<T, P, V>(funnel);
    }

    private TrieMap(Funnel<T, P> funnel) {
        this.funnel = funnel;
        root = new Node<T, P, V>();
    }

    public V put(T key, V value) {
        return root.put(key, value, funnel, 0);
    }

    public V get(T key) {
        return root.get(key, funnel, 0);
    }

    public boolean contains(T object) {
        return root.contains(object, funnel, 0);
    }

    public void forEach(final TrieMapCallback<T, V> callback) {
        root.preOrderDepthForEach(new ArrayList<P>(), funnel, new TrieMapCallbackFull<T, V>() {
            @Override
            public void onNode(T node, V value, int level) {
                callback.onNode(node, value, level);
            }

            @Override
            public void onMoveDown(int newLevel) {
            }

            @Override
            public void onMoveUp(int newLevel) {
            }
        }, 0);
    }

    public void forEach(TrieMapCallbackFull<T, V> callback) {
        root.preOrderDepthForEach(new ArrayList<P>(), funnel, callback, 0);
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        final String separator = System.getProperty("line.separator");
        stringBuilder.append("TrieMap, funnel: ").append(funnel.toString()).append(separator);
        forEach(new TrieMapCallback<T, V>() {
            @Override
            public void onNode(T node, V value, int level) {
                for (int i = 0; i < level; i++) {
                    stringBuilder.append(' ');
                }
                stringBuilder.append(node.toString()).append(separator);
            }
        });
        return stringBuilder.toString();
    }
}
