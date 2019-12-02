// class which is used to create random numbers
class MyRand
{
   private int seed;
   private int firstSeed;
   private int start;
   private int stop;
   
   // default constructor sets generic values to variables
   public MyRand()
   {
      seed = 0;
      start = 0;
      stop = 100;
      firstSeed = seed;
   }
   
   // constructor which allows for an initialzation seed while 
   // using generic values for the bounds.
   public MyRand(int seed)
   {
      this.seed = seed;
      start = 0;
      stop = 100;
      firstSeed = seed;
   }
   
   // constructor which allows for the preset of seed value as well as
   // the preset of the boundary values.
   public MyRand(int seed, int start, int stop)
   {
      this.seed = seed;
      this.start = start;
      this.stop = stop;
      firstSeed = seed;
   }
   
   // method which allows for the bounds of the random number to be changed
   public void setBounds(int start, int stop)
   {
      this.start = start;
      this.stop = stop;
   }
   
   // version of getInt() that uses the preset table size
   public int getInt()
   {
      // set r to seed value (step 1)
      int r = seed;
      
      // r = r * 5 (step 2)
      r *= 5;
      
      // r = r % tableSize (step 3)
      r %= stop;
      
      // change value of seed for next use
      seed = r;
      
      // returns int value of r / 4 (step 4)
      return r/4;
   }
   
   // version of getInt() which allows for different table size
   public int getInt(int size)
   {
      // set r to seed value (step 1)
      int r = seed;
      
      // r = r * 5 (step 2)
      r *= 5;
      
      // r = r % tableSize (step 3)
      r %= size;
      
      // change value of seed for next use
      seed = r;
      
      // returns int value of r / 4 (step 4)
      return r/4;
   }
   
   // version of getInt() which allows for negative ranges
   public int getInt(int first, int last)
   {
      // use the absolute value function and add start with stop
      // to initialize table size.
      int size = absVal(first) + absVal(last);
      
      // set r to seed value (step 1)
      int r = seed;
      
      // r = r * 5 (step 2)
      r *= 5;
      
      // r = r % tableSize (step 3)
      r %= size;
      
      // change value of seed for next use
      seed = r;
      
      // returns int value of r / 4 (step 4)
      return r / 4;
   }
   
   // method to reset seed to its origional value
   public void resetSeed()
   {
      // uses the origional seed value set by constructor
      seed = firstSeed;
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
}