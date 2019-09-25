package cs445.a5;

import java.util.Iterator;
import java.util.NoSuchElementException;

import cs445.StackAndQueuePackage.*; // Needed by tree iterators

/**
 * A class that implements the ADT ternary tree.
 * @author Christian Ford
 * @version 4.5
 */
public class TernaryTree<E> implements TernaryTreeInterface<E> {
    private TernaryNode<E> root;

    public TernaryTree() {
        root = null;
    }

    public TernaryTree(E rootData) {
        root = new TernaryNode<>(rootData);
    }

    public TernaryTree(E rootData, TernaryTree<E> leftTree, TernaryTree<E> middleTree,
                      TernaryTree<E> rightTree) {
        privateSetTree(rootData, leftTree, middleTree, rightTree);
    }

    public void setTree(E rootData) {
        root = new TernaryNode<>(rootData);
    }

    public void setTree(E rootData, TernaryTreeInterface<E> leftTree, TernaryTreeInterface<E> middleTree,
                        TernaryTreeInterface<E> rightTree) {
        privateSetTree(rootData, (TernaryTree<E>)leftTree, (TernaryTree<E>)middleTree,
                       (TernaryTree<E>)rightTree);
    }

    private void privateSetTree(E rootData, TernaryTree<E> leftTree, TernaryTree<E> middleTree,
                                TernaryTree<E> rightTree) {
        TernaryNode<E> root = new TernaryNode<>(rootData);

        if ((leftTree != null) && !leftTree.isEmpty()) {
            root.setLeftChild(leftTree.root);
        }

        if((middleTree != null) && !middleTree.isEmpty())
        {
            if(middleTree != leftTree)
            {
                root.setMiddleChild(middleTree.root);
            }
            else
            {
                root.setMiddleChild(middleTree.root.copy());
            }
        }

        if ((rightTree != null) && !rightTree.isEmpty()) {
            if (rightTree != leftTree && rightTree != middleTree) {
                root.setRightChild(rightTree.root);
            } else {
                root.setRightChild(rightTree.root.copy());
            }
        }

        this.root = root;

        if ((leftTree != null) && (leftTree != this)) {
            leftTree.clear();
        }

        if ((middleTree != null) && (middleTree != this)) 
        {
            middleTree.clear();
        }

        if ((rightTree != null) && (rightTree != this)) {
            rightTree.clear();
        }
    }

    /** Gets the data in the root node
     *  @return  the data from the root node
     *  @throws EmptyTreeException  if the tree is empty */
    public E getRootData()
    {
        if(root.getData() == null)
        {
            throw new EmptyTreeException();
        }
        return root.getData();
    }

    /** Gets the height of the tree (i.e., the maximum number of nodes passed
     *  through from root to leaf, inclusive)
     *  @return  the height of the tree */
    public int getHeight()
    {
        return root.getHeight();
    }

    /** Counts the total number of nodes in the tree
     *  @return  the number of nodes in the tree */
    public int getNumberOfNodes()
    {
        return root.getNumberOfNodes();
    }

    /** Determines whether the tree is empty (i.e., has no nodes)
     *  @return  true if the tree is empty, false otherwise */
    public boolean isEmpty()
    {
        return root == null;
    }

    /** Removes all data and nodes from the tree */
    public void clear()
    {
        root = null;
    }

    protected void setRootData(E rootData) {
        root.setData(rootData);
    }

    protected void setRootNode(TernaryNode<E> rootNode) {
        root = rootNode;
    }

    protected TernaryNode<E> getRootNode() {
        return root;
    }

    /** Creates an iterator to traverse the tree in preorder fashion
    *  @return  the iterator */
    public Iterator<E> getPreorderIterator()
    {
        return new PreorderIterator();
    }

   /** Creates an iterator to traverse the tree in postorder fashion
    *  @return  the iterator */
    public Iterator<E> getPostorderIterator()
    {
        return new PostorderIterator();
    }

   /** Creates an iterator to traverse the tree in inorder fashion
    *  @return  the iterator */
    public Iterator<E> getInorderIterator()
    {
        throw new java.lang.UnsupportedOperationException("This program does not support the Inorder Iterator.  Please use the Preorder or Postorder Iterator instead.");
    }

   /** Creates an iterator to traverse the tree in level-order fashion
    *  @return  the iterator */
    public Iterator<E> getLevelOrderIterator() 
    {
        return new LevelOrderIterator();
    }

    private class PreorderIterator implements Iterator<E> {
        private StackInterface<TernaryNode<E>> nodeStack;

        public PreorderIterator() {
            nodeStack = new LinkedStack<>();
            if (root != null) {
                nodeStack.push(root);
            }
        }

        public boolean hasNext() {
            return !nodeStack.isEmpty();
        }

        public E next() {
            TernaryNode<E> nextNode;

            if (hasNext()) {
                nextNode = nodeStack.pop();
                TernaryNode<E> leftChild = nextNode.getLeftChild();
                TernaryNode<E> middleChild = nextNode.getMiddleChild();
                TernaryNode<E> rightChild = nextNode.getRightChild();

                // Push into stack in reverse order of recursive calls
                if (rightChild != null) {
                    nodeStack.push(rightChild);
                }

                if(middleChild != null)
                {
                    nodeStack.push(middleChild);
                }

                if (leftChild != null) {
                    nodeStack.push(leftChild);
                }
            } else {
                throw new NoSuchElementException();
            }

            return nextNode.getData();
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException("This program does not allow the user to remove a specific node from the tree.");
        }
    }

    private class PostorderIterator implements Iterator<E> {
        private StackInterface<TernaryNode<E>> nodeStack;
        //private TernaryNode<E> node;

        public PostorderIterator() {
            nodeStack = new LinkedStack<>();
            nodeStack.push(root);
            
        }

        public boolean hasNext() {
            return !nodeStack.isEmpty();
        }

        public E next()
        {
            TernaryNode<E> node = nodeStack.pop();

            while(node != null)
            {
                nodeStack.push(node);

                TernaryNode<E> child = node.getLeftChild();
                if(child == null)
                {
                    child = node.getMiddleChild();
                }
                if(child == null)
                {
                    child = node.getRightChild();
                }
                node = child;
            }

            TernaryNode<E> ret = nodeStack.pop();
            node = ret;

            if(!nodeStack.isEmpty())
            {
                TernaryNode<E> parent = nodeStack.peek();

                if(parent.getLeftChild() == node)
                {
                    node = parent.getMiddleChild();
                    if(node == null)
                    {
                        node = parent.getRightChild();
                    }
                }
                else if(parent.getMiddleChild() == node)
                {
                    node = parent.getRightChild();
                }
                else
                {
                    node = null;
                }

                nodeStack.push(node);
            }
            
            return ret.getData();
        }
        

        // public E next() {
        //     boolean foundNext = false;
        //     TernaryNode<E> leftChild, middleChild, rightChild, nextNode = null;


        //     // Find leftmost leaf
        //     while (currentNode != null) {
        //         nodeStack.push(currentNode);
        //         leftChild = currentNode.getLeftChild();
        //         if (leftChild == null) {
        //             currentNode = currentNode.getMiddleChild();
        //         } 
        //         if(middleChild == null)
        //         {
        //             currentNode = currentNode.getRightChild();
        //         }
        //         else {
        //             currentNode = leftChild;
        //         }
        //     }

        //     // Stack is not empty either because we just pushed a node, or
        //     // it wasn'E empty to begin with since hasNext() is true.
        //     // But Iterator specifies an exception for next() in case
        //     // hasNext() is false.

        //     if (!nodeStack.isEmpty()) {
        //         nextNode = nodeStack.pop();
        //         // nextNode != null since stack was not empty before pop

        //         TernaryNode<E> parent = null;
        //         if (!nodeStack.isEmpty()) {
        //             parent = nodeStack.peek();
        //             if (nextNode == parent.getLeftChild()) {
        //                 currentNode = parent.getRightChild();
        //             } else {
        //                 currentNode = null;
        //             }
        //         } else {
        //             currentNode = null;
        //         }
        //     } else {
        //         throw new NoSuchElementException();
        //     }

        //     return nextNode.getData();
        // }
    


        public void remove() {
            throw new java.lang.UnsupportedOperationException("This program does not allow the user to remove a specific node from the tree.");
        }
    }

    private class LevelOrderIterator implements Iterator<E> {
        private QueueInterface<TernaryNode<E>> nodeQueue;

        public LevelOrderIterator() {
            nodeQueue = new LinkedQueue<>();
            if (root != null) {
                nodeQueue.enqueue(root);
            }
        }

        public boolean hasNext() {
            return !nodeQueue.isEmpty();
        }

        public E next() {
            TernaryNode<E> nextNode;

            if (hasNext()) {
                nextNode = nodeQueue.dequeue();
                TernaryNode<E> leftChild = nextNode.getLeftChild();
                TernaryNode<E> middleChild = nextNode.getMiddleChild();
                TernaryNode<E> rightChild = nextNode.getRightChild();

                // Add to queue in order of recursive calls
                if (leftChild != null) {
                    nodeQueue.enqueue(leftChild);
                }

                if(middleChild != null)
                {
                    nodeQueue.enqueue(middleChild);
                }

                if (rightChild != null) {
                    nodeQueue.enqueue(rightChild);
                }
            } else {
                throw new NoSuchElementException();
            }

            return nextNode.getData();
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException("This program does not allow the user to remove a specific node from the tree.");
        }
    }

    public String toString()
    {
        return root.toString();
    }

}