package cs445.a5;

import java.util.Iterator;

import cs445.a5.TernaryTree;
import jdk.nashorn.internal.ir.TernaryNode;

public class Test
{
    public static TernaryTree<Integer> l(int data)
    {
        return new TernaryTree<Integer>(data);
    }

    public static TernaryTree<Integer> b(int data, TernaryTree<Integer> left, TernaryTree<Integer> middle, TernaryTree<Integer> right)
    {
        return new TernaryTree<Integer>(data, left, middle, right);
    }

    public static void main(String[] args) 
    {
        TernaryTree<Integer> tree = b(1, b(2, l(3), l(4), l(5)), null, l(6));
        TernaryTree<Integer> nullTree;


        Iterator<Integer> postIterator = tree.getPostorderIterator();
        while(postIterator.hasNext())
        {
            System.out.println(postIterator.next());
        }

        System.out.println();

        Iterator<Integer> preIterator = tree.getPreorderIterator();
        while(preIterator.hasNext())
        {
            System.out.println(preIterator.next());
        }

        System.out.println();

        Iterator<Integer> levelIterator = tree.getLevelOrderIterator();
        while(levelIterator.hasNext())
        {
            System.out.println(levelIterator.next());
        }

        System.out.println();

        System.out.println(tree.getRootData());
        System.out.println(tree.getHeight());
        System.out.println(tree.getNumberOfNodes());
        tree.clear();
        System.out.println(tree.getRootData());
    }
}