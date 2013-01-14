package org.tarnavsky.trie;

import java.util.ArrayList;

/**
 * @author jkee
 */

public class Trie <T, P> {

    private final Funnel<T, P> funnel;
    private final Node<T, P> root;

    public static <T, P> Trie<T, P> newTrie(Funnel<T, P> funnel) {
        return new Trie<T, P>(funnel);
    }

    private Trie(Funnel<T, P> funnel) {
        this.funnel = funnel;
        root = new Node<T, P>(false);
    }

    public boolean add(T object) {
        return root.add(object, funnel, 0);
    }

    public boolean contains(T object) {
        return root.contains(object, funnel, 0);
    }

    public void forEach(TrieCallback<T> callback) {
        root.preOrderDepthForEach(new ArrayList<P>(), funnel, callback, 0);
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        final String separator = System.getProperty("line.separator");
        stringBuilder.append("Trie, funnel: ").append(funnel.toString()).append(separator);
        forEach(new TrieCallback<T>() {
            @Override
            public void onNode(T node, int level) {
                for (int i = 0; i < level; i++) {
                    stringBuilder.append(' ');
                }
                stringBuilder.append(node.toString()).append(separator);
            }
        });
        return stringBuilder.toString();
    }
}
