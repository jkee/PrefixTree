package org.tarnavsky.trie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jkee
 */

public class Node<T, P> {

    private boolean contains;
    private Map<P, Node<T, P>> children;

    public Node(boolean contains) {
        this.contains = contains;
    }

    public Node<T, P> getChild(P prefix) {
        return children.get(prefix);
    }

    public boolean add(T object, Funnel<T, P> funnel, int level) {
        P prefix = funnel.getPrefix(object, level);
        if (prefix == null) {//current level is object level
            if (contains) return false;
            else {
                contains = true;
                return true;
            }
        }
        if (children == null) children = new HashMap<P, Node<T, P>>();
        Node<T, P> child = children.get(prefix);
        if (child == null) { //there are no such way yet, creating
            Node<T, P> newNode = new Node<T, P>(false);
            children.put(prefix, newNode);
            return newNode.add(object, funnel, level + 1);
        } else {
            return child.add(object, funnel, level + 1);
        }
    }

    public boolean contains(T object, Funnel<T, P> funnel, int level) {
        P prefix = funnel.getPrefix(object, level);
        if (prefix == null) return contains;
        if (children == null) return false;
        Node<T, P> child = children.get(prefix);
        if (child == null) return false;
        else return child.contains(object, funnel, level + 1);
    }

    public void preOrderDepthForEach(List<P> path, Funnel<T, P> funnel, TrieCallback<T> callback, int level) {
        if (contains) {
            T object = funnel.create(path);
            callback.onNode(object, level);
        }
        if (children == null) return;
        for (Map.Entry<P, Node<T, P>> pNodeEntry : children.entrySet()) {
            path.add(pNodeEntry.getKey());
            pNodeEntry.getValue().preOrderDepthForEach(path, funnel, callback, level + 1);
            path.remove(path.size() - 1);
        }
    }
}
