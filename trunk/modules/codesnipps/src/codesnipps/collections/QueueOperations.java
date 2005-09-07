package codesnipps.collections;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Queues are new for java 5.
 * @author gepo
 *
 */
public class QueueOperations
{
    private static Queue q = new LinkedList();
    private static PriorityQueue<Integer> pq = new PriorityQueue<Integer>(20);
    
    private static void doStuff()
    {
        q.add("1");
        q.add("2");
        q.add("3");
        
        System.out.println(q.poll());
        System.out.println(q.poll());
        System.out.println(q.poll());
        System.out.println(q.poll());
        
        // Adding elements to a priorityqueue - the natural ordering makes
        // the elements added appear in reverse order in this case
        pq.add(3);
        pq.add(2);
        pq.add(1);

        System.out.println(pq.poll());
        System.out.println(pq.poll());
        System.out.println(pq.poll());
        System.out.println(pq.poll());
        
    }

    public static void main(String[] args)
    {
        doStuff();
    }

}
