/**
 * Title:   MyStk
 * Author:  Manno, Timothy
 * Purpose: Generic Stack Class created for general usage
 * Date:    11/21/2019
 * Version: 1.0
**/

public class MyStk<T>
{
   private class Node
   {
      private T info;
      private Node next;
   }
   
   // declare the node for top of stack
   private Node top;
   private int size;
   
   public MyStk()
   {
      top = null;
      size = 0;
   }
   
   // method that adds new data to top of the stack
   public void push(T obj)
   {
      // set Node temp to data held in the current top node
      Node temp = top;
      
      // re-initialize top node as a new node with obj in its info field
      top = new Node();
      top.info = obj;
      
      // aim tops next field to point to the address of the temp node
      top.next = temp;
      
      // increment the size field
      size++;
   }
   
   // method to remove data from top of the stack
   public T pop()
   {
      // declare element to hold data that will be returned
      T data = null;
      
      if ( top == null )
      {
         // error occurs due to lack of nodes to pop off stack
         System.err.println("Error: No data to pop off stack!");
      }
      else // data to be removed from top of stack
      {
         // retrieve data from the top nodes info field
         data = top.info;
         
         // set top node to the next nodes address
         top = top.next;
         
         // decrement the size field
         size--;
      }
      
      // return the data that was retrieved if any
      return data;
   }
   
   public int getSize()
   {
      return size;
   }
   
   public void popAll()
   {
      top = null;
      size = 0;
   }
}

class StkDriver
{
   public static void main(String [] args)
   {
      // stack driver for testing
      MyStk<Integer> stki = new MyStk<>();
      MyStk<String> stks = new MyStk<>();
      //Scanner sc = new Scanner(System.in);
      
      stki.push(15);
      stki.push(10);
      stki.push(5);
      
      stks.push("cat");
      stks.push("dog");
      stks.push("frog");
      
      for (int i = stki.getSize(); i > 0; i--)
      {
         System.out.println( stki.pop().toString() + " " + stks.pop() );
      }
   }
}