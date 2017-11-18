import com.packing.BinaryTree;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BynaryTest {
    @Test
    public void testSubSet(){
        BinaryTree<Integer> tree=new BinaryTree<>();
        List<Integer> lis = Arrays.asList(54,5,6,2,1,4,90,8);
        tree.addAll(lis);
        SortedSet<Integer> set= new TreeSet<>();
        assertEquals(set,tree.subSet(0,0));
        assertEquals(set,tree.subSet(999,999));
        List<Integer> list = Arrays.asList(5,2,1,4);
        set.addAll(list);

        assertEquals(set,tree.subSet(1,6));
    }

    @Test
    public void testTailSet(){
        BinaryTree<Integer> tree=new BinaryTree<>();
        List<Integer> list = Arrays.asList(2,4,8,6,5,7,1,9);
        tree.addAll(list);
        SortedSet<Integer> set= new TreeSet<>(Arrays.asList(7,8));
        assertEquals(set,tree.tailSet(7));
    }

}
