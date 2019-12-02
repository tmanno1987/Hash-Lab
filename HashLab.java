import java.util.*;
import java.io.*;

public class HashLab
{
   private final int SIZE = 128;
   private int hashAddr;
   private String data;
   private Hash [] table;
   private boolean probeType;
   private boolean hashFun;
   private Hash nullCheck;
   
   public HashLab (boolean probeType, boolean hashFun)
   {
      hashAddr = -1;
      data = "";
      table = new Hash[SIZE];
      this.probeType = probeType;
      this.hashFun = hashFun;
      populate(table, SIZE);
      nullCheck = new Hash();
   }
   
   // populate the hash table with null data
   public void populate(Hash [] tab, int size)
   {
      // loop through every index in hash table
      for (int i = 0; i < size; i++)
      {
         // initialize each index to avoid a Null Pointer Exception!!
         tab[i] = new Hash();
      }
   }
   
   // adds data to the hash table
   public void insert(String s)
   {
      data = s;
      
      // check to see which hash function to run
      if (hashFun)
      {
         // run personal hash function
         myHashFunction();
      }
      else
      {
         // run the professors hash function
         hashFunctionProfessor();
      }
   }
   
   // method for my hash function
   private void myHashFunction()
   {
      // initialize integer num by calling addMultChars() method
      int num = addMultChars();
      
      // set hash address 
      hashAddr = num % SIZE;
      
      // check to see if probing is required or not
      if (table[hashAddr].getInfo() == null) 
      {
         // add data to array and exit function
         table[hashAddr].setInfo(data);
         table[hashAddr].setOHA(hashAddr);
         table[hashAddr].setProbes(1);
         return;
      }
      else
      {
         // check to see which probe technique will be used
         if (probeType)
         {
            // if true run the linear probe technique
            linearProbe();
         }
         else
         {
            // if false run the random probe technique
            randomProbe();
         }
      }
   }
   
   // a function which breaks a string into a char array then returns the sum
   // of the chars multiplied by their indexes.
   private int addMultChars()
   {
      // turn data into char array and set integer num to zero
      char [] c = data.toCharArray();
      int num = 0;
      
      // loop through char array
      for (int i = 0; i < c.length; i++)
      {
         // multiply each char value by its index then add them to num variable
         num += (c[i] * i);
      }
      
      // return the value of the num variable
      return num;
   }
   
   // uses the linear probe method to handle collisions
   private void linearProbe()
   {
      // sets num to the origional hash address
      int num = hashAddr;
      int val = 1;
      //boolean chk = table[hashAddr].getInfo() == null;
      
      // loops until position is found or same hash address is reached
      do
      {
         // increments hash address and probe counter by 1
         hashAddr = (hashAddr + 1) % SIZE;
         val++;
      }
      while (table[hashAddr].getInfo() != null);
      
      // check to see if table is full
      if (num == hashAddr)
      {
         return;
      }
      
      // record total probes and origional hash address
      table[hashAddr].setOHA(num);
      table[hashAddr].setProbes(val);
      table[hashAddr].setInfo(data);
   }
   
   private void randomProbe()
   {
      // creates random numbers using random number generator and integer val used
      // to store how many times a probe is required
      MyRand rand = new MyRand(1);
      int val = 1;
      
      // record origional hash address
      int orig = hashAddr;
      
      // loops through table searching for open position
      while (table[hashAddr].getInfo() != null)
      {
         // OHA = OHA + random number; val is incremented by 1
         hashAddr = (hashAddr + rand.getInt(SIZE)) % SIZE;
         val++;
      }
      
      // record data into the hash table
      table[hashAddr].setProbes(val);
      table[hashAddr].setOHA(orig);
      table[hashAddr].setInfo(data);
   }
   
   // use HA = {abs[ slice(4..5) ] + abs[slice(13..14) ] } / 65,535 + abs[slice(10..10)]
   private void hashFunctionProfessor()
   {
      // simplify hash function by breaking it into smaller more managable slices
      final int VAL = 65535;
      int a = absVal(slice(data,4,5));
      int b = absVal(slice(data,13,14));
      int c = absVal(slice(data,10,10));
      
      // compute the value of the hash address using professors bad method
      hashAddr = (a + b) / VAL + c;
      
      // check to see if index is full or empty
      if (table[hashAddr].getInfo() == null) 
      {
         // add data to array and exit function
         table[hashAddr].setInfo(data);
         table[hashAddr].setOHA(hashAddr);
         table[hashAddr].setProbes(1);
         return;
      }
      else
      {
         // check to see which probe technique will be used
         if (probeType)
         {
            // if true run the linear probe technique
            linearProbe();
         }
         else
         {
            // if false run the random probe technique
            randomProbe();
         }
      }
   }
   
   // slice method takes string and returns its char values at the given integer positions
   private int slice(String str, int num1, int num2)
   {
      // turn string into char array and declare 2 integers
      char [] c = str.toCharArray();
      int a;
      int b;
      
      // check to see if values are equal
      if ( num1 == num2 )
      {
         // if they are then only 1 operation is needed setting int a to
         // the value of the char in the given char array
         a = c[num1];
         
         // return the int value received
         return a;
      }
      else // if not then 2 operations are required
      {
         // b and a are both set to the int values of the chars that are indexed
         // in the given char array
         a = c[num1];
         b = c[num2];
         
         // the values of a + b is then returned
         return a + b;
      }
   }
   
   // function that turns negative values into their positive counterparts
   private int absVal(int num)
   {
      // declare integer and initialize to num
      int v = num;
      
      // if v is less than zero change value
      if ( v < 0 )
      {
         // change value by multiplying by negative 1
         v *= (-1);
      }
      
      // returns a positive value
      return v;
   }
   
   // function that returns, either the first or last, hash objects of
   // an amount specified by the user stored in the hash table.
   public Hash [] getData(int numOfObjs, boolean dir)
   {
      // create Hash array of size numOfObjs, a hash stack and a count variable
      Hash [] temp = new Hash[numOfObjs];
      MyStk<Hash> hstk = new MyStk<>();
      int c = 0;
      
      // either loop from back or front based on boolean dir
      if ( dir )
      {
         // load data from start and loop through array
         for (int i = 0; i < table.length; i++)
         {
            // if number of objects is reached break out of the loop
            if (c == numOfObjs)
            {
               break;
            }
            
            // check to see if there is data present
            if (table[i].getInfo() != null && c < numOfObjs)
            {
               // data has been found add to temp Hash table and increment count var
               temp[c++] = table[i];
            }
         }
      }
      else
      {
         // load data from back
         for (int i = table.length - 1; i > 0; i--)
         {
            // if number of objects is reached break out of the loop
            if (c == numOfObjs)
            {
               break;
            }
            
            // check to see if data is there or not
            if (table[i].getInfo() != null)
            {
               // data has been found push to stack and increment count var
               hstk.push(table[i]);
               c++;
            }
         }
      }
      
      // pop data from the stack so that it appears in proper order
      if (dir == false)
      {
         // loop through all items stored in the stack
         for (int i = 0; i < numOfObjs; i++)
         {
            // add each item to temp hash table to be returned
            temp[i] = hstk.pop();
         }
      }
      
      // return data stored in the temp hash table
      return temp;
   }
   
   public void printTable()
   {
      System.out.println("Index\t|\t\tData\t\t\t\tOrigional Addr\t\tProbes");
      for (int i = 0; i < SIZE; i++)
      {
         if ( i < 100 )
         {
            System.out.println(i + "\t\t|\t\t" + table[i]);
         }
         else
         {
            System.out.println(i + "\t|\t\t" + table[i]);
         }
      }
   }
   
   public Hash findAtIndex(int i)
   {
      return table[i];
   }
}

class HashDriver
{
   public static void main(String [] args)
   {
      // declare objects and data
      Scanner sc = new Scanner(System.in);
      Hash [] tabFirst30;
      Hash [] tabLast30;
      String [] words;
      FileProcessor fp;
      HashLab proFunLinear50;
      HashLab proFunRandom50;
      HashLab myFunLinear50;
      HashLab myFunRandom50;
      HashLab proFunLinear90;
      HashLab proFunRandom90;
      HashLab myFunLinear90;
      HashLab myFunRandom90;
      final int SIZE = 128;
      final String FNAME = "hash_words.txt";
      final int C1 = 64;
      final int C2 = 115;
      
      fp = new FileProcessor(FNAME);
      words = fp.getList(SIZE);
      
      proFunLinear50 = new HashLab(true,false);
      proFunRandom50 = new HashLab(false,false);
      myFunLinear50 = new HashLab(true,true);
      myFunRandom50 = new HashLab(false,true);
      
      proFunLinear90 = new HashLab(true,false);
      proFunRandom90 = new HashLab(false,false);
      myFunLinear90 = new HashLab(true,true);
      myFunRandom90 = new HashLab(false,true);
      
      // load the half way full 
      for (int i = 0; i < C1; i++)
      {
         proFunLinear50.insert(words[i]);
         proFunRandom50.insert(words[i]);
         myFunLinear50.insert(words[i]);
         myFunRandom50.insert(words[i]);
      }
      
      // load 90 percent full
      for (int i = 0; i < C2; i++)
      {
         proFunLinear90.insert(words[i]);
         proFunRandom90.insert(words[i]);
         myFunLinear90.insert(words[i]);
         myFunRandom90.insert(words[i]);
      }
      
      /* Below holds parts A through D using the Professors hash function */
      
      // search for first 30 keys and last 30 keys
      tabFirst30 = proFunLinear50.getData(30, true);
      tabLast30 = proFunLinear50.getData(30, false);
      
      // calculate min, max and average for first 30 data entries
      System.out.println("First 30 data entries Min, Max, and Average probes");
      System.out.println("Max: " + findMinOrMax(tabFirst30,true));
      System.out.println("Min: " + findMinOrMax(tabFirst30,false));
      System.out.println("Avg: " + findAvg(tabFirst30));
      System.out.println();
      
      // calculate min, max and average for last 30 data entries
      System.out.println("Last 30 data entries Min, Max, and Average probes");
      System.out.println("Max: " + findMinOrMax(tabLast30,true));
      System.out.println("Min: " + findMinOrMax(tabLast30,false));
      System.out.println("Avg: " + findAvg(tabLast30));
      System.out.println();
      
      // print the whole hash table
      proFunLinear50.printTable();
      
      // search for first/last 30 keys
      tabFirst30 = proFunRandom50.getData(30, true);
      tabLast30 = proFunRandom50.getData(30, false);
      
      // calculate min, max and average for first 30 data entries
      System.out.println("First 30 data entries Min, Max, and Average probes");
      System.out.println("Max: " + findMinOrMax(tabFirst30,true));
      System.out.println("Min: " + findMinOrMax(tabFirst30,false));
      System.out.println("Avg: " + findAvg(tabFirst30));
      System.out.println();
      
      // calculate min, max and average for last 30 data entries
      System.out.println("Last 30 data entries Min, Max, and Average probes");
      System.out.println("Max: " + findMinOrMax(tabLast30,true));
      System.out.println("Min: " + findMinOrMax(tabLast30,false));
      System.out.println("Avg: " + findAvg(tabLast30));
      System.out.println();
      
      // print the whole hash table
      proFunRandom50.printTable();
      
      // data for tables that are 90 percent full using the professors function
      // search for first 30 keys and last 30 keys
      tabFirst30 = proFunLinear90.getData(30, true);
      tabLast30 = proFunLinear90.getData(30, false);
      
      // calculate min, max and average for first 30 data entries
      System.out.println("First 30 data entries Min, Max, and Average probes");
      System.out.println("Max: " + findMinOrMax(tabFirst30,true));
      System.out.println("Min: " + findMinOrMax(tabFirst30,false));
      System.out.println("Avg: " + findAvg(tabFirst30));
      System.out.println();
      
      // calculate min, max and average for last 30 data entries
      System.out.println("Last 30 data entries Min, Max, and Average probes");
      System.out.println("Max: " + findMinOrMax(tabLast30,true));
      System.out.println("Min: " + findMinOrMax(tabLast30,false));
      System.out.println("Avg: " + findAvg(tabLast30));
      System.out.println();
      
      // print the whole hash table
      proFunLinear90.printTable();
      
      // search for first/last 30 keys
      tabFirst30 = proFunRandom90.getData(30, true);
      tabLast30 = proFunRandom90.getData(30, false);
      
      // calculate min, max and average for first 30 data entries
      System.out.println("First 30 data entries Min, Max, and Average probes");
      System.out.println("Max: " + findMinOrMax(tabFirst30,true));
      System.out.println("Min: " + findMinOrMax(tabFirst30,false));
      System.out.println("Avg: " + findAvg(tabFirst30));
      System.out.println();
      
      // calculate min, max and average for last 30 data entries
      System.out.println("Last 30 data entries Min, Max, and Average probes");
      System.out.println("Max: " + findMinOrMax(tabLast30,true));
      System.out.println("Min: " + findMinOrMax(tabLast30,false));
      System.out.println("Avg: " + findAvg(tabLast30));
      System.out.println();
      
      // print the whole hash table
      proFunRandom90.printTable();
      
      /* Below holds the code for parts A through D using my Function */
      
      // search for first 30 keys and last 30 keys
      tabFirst30 = myFunLinear50.getData(30, true);
      tabLast30 = myFunLinear50.getData(30, false);
      
      // calculate min, max and average for first 30 data entries
      System.out.println("First 30 data entries Min, Max, and Average probes");
      System.out.println("Max: " + findMinOrMax(tabFirst30,true));
      System.out.println("Min: " + findMinOrMax(tabFirst30,false));
      System.out.println("Avg: " + findAvg(tabFirst30));
      System.out.println();
      
      // calculate min, max and average for last 30 data entries
      System.out.println("Last 30 data entries Min, Max, and Average probes");
      System.out.println("Max: " + findMinOrMax(tabLast30,true));
      System.out.println("Min: " + findMinOrMax(tabLast30,false));
      System.out.println("Avg: " + findAvg(tabLast30));
      System.out.println();
      
      // print the whole hash table
      myFunLinear50.printTable();
      
      // search for first/last 30 keys
      tabFirst30 = myFunRandom50.getData(30, true);
      tabLast30 = myFunRandom50.getData(30, false);
      
      // calculate min, max and average for first 30 data entries
      System.out.println("First 30 data entries Min, Max, and Average probes");
      System.out.println("Max: " + findMinOrMax(tabFirst30,true));
      System.out.println("Min: " + findMinOrMax(tabFirst30,false));
      System.out.println("Avg: " + findAvg(tabFirst30));
      System.out.println();
      
      // calculate min, max and average for last 30 data entries
      System.out.println("Last 30 data entries Min, Max, and Average probes");
      System.out.println("Max: " + findMinOrMax(tabLast30,true));
      System.out.println("Min: " + findMinOrMax(tabLast30,false));
      System.out.println("Avg: " + findAvg(tabLast30));
      System.out.println();
      
      // print the whole hash table
      myFunRandom50.printTable();
      
      // data for tables that are 90 percent full using the professors function
      // search for first 30 keys and last 30 keys
      tabFirst30 = myFunLinear90.getData(30, true);
      tabLast30 = myFunLinear90.getData(30, false);
      
      // calculate min, max and average for first 30 data entries
      System.out.println("First 30 data entries Min, Max, and Average probes");
      System.out.println("Max: " + findMinOrMax(tabFirst30,true));
      System.out.println("Min: " + findMinOrMax(tabFirst30,false));
      System.out.println("Avg: " + findAvg(tabFirst30));
      System.out.println();
      
      // calculate min, max and average for last 30 data entries
      System.out.println("Last 30 data entries Min, Max, and Average probes");
      System.out.println("Max: " + findMinOrMax(tabLast30,true));
      System.out.println("Min: " + findMinOrMax(tabLast30,false));
      System.out.println("Avg: " + findAvg(tabLast30));
      System.out.println();
      
      // print the whole hash table
      myFunLinear90.printTable();
      
      // search for first/last 30 keys
      tabFirst30 = myFunRandom90.getData(30, true);
      tabLast30 = myFunRandom90.getData(30, false);
      
      // calculate min, max and average for first 30 data entries
      System.out.println("First 30 data entries Min, Max, and Average probes");
      System.out.println("Max: " + findMinOrMax(tabFirst30,true));
      System.out.println("Min: " + findMinOrMax(tabFirst30,false));
      System.out.println("Avg: " + findAvg(tabFirst30));
      System.out.println();
      
      // calculate min, max and average for last 30 data entries
      System.out.println("Last 30 data entries Min, Max, and Average probes");
      System.out.println("Max: " + findMinOrMax(tabLast30,true));
      System.out.println("Min: " + findMinOrMax(tabLast30,false));
      System.out.println("Avg: " + findAvg(tabLast30));
      System.out.println();
      
      // print the whole hash table
      myFunRandom90.printTable();
   }
   
   public static int findMinOrMax(Hash [] h, boolean maxMin)
   {
      int num = h[0].getProbes();
      
      for (int i = 1; i < h.length; i++)
      {
         if ( maxMin )
         {
            // set number to max
            if ( num < h[i].getProbes() )
            {
               // change the value of max variable
               num = h[i].getProbes();
            }
         }
         else
         {
            // set number to min
            if ( num > h[i].getProbes() )
            {
               // change the value of min variable
               num = h[i].getProbes();
            }
         }
      }
      
      return num;
   }
   
   public static double findAvg(Hash [] h)
   {
      int total = 0;
      
      for (int i = 0; i < h.length; i++)
      {
         total += h[i].getProbes();
      }
      
      return (double)total / h.length;
   }
}