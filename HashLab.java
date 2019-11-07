import java.util.*;
import java.io.*;

public class HashLab
{

}

// reads in data from a file and stores it into an array list
class FileProcessor
{
   private Scanner file;
   private ArrayList<String> als;
   
   public FileProcessor(String fName)
   {
      try
      {
         file = new Scanner(new File(fName));
         als = new ArrayList<>();
      }
      catch (FileNotFoundException fnfe)
      {
         System.err.println("File error: " + fnfe.toString());
      }
   }
   
   public void processData()
   {
      while (file.hasNext())
      {
         als.add(addSpace(file.nextLine()));
      }
   }
   
   // take string and add space chars until string is 16 chars
   private String addSpace(String s)
   {
      // create string and char array to manipulate
      char [] lets = new char[16];
      int i = 0;
      int size = s.length();
      
      // check to see if string is 16 chars
      if (size == 16)
      {
         return s;
      }
      else
      {
         // loop through chars in string adding them to char [] lets
         for (; i < 16; i++)
         {
            // check to see if index is less than size of string
            if ( i < size )
            {
               // add chars from string to lets [] array
               lets[i] = s.charAt(i);
            }
            else
            {
               // add spaces to pad the size of string
               lets[i] = ' ';
            }
         }
      }
      
      return new String(lets);
   }
}

class HashDriver
{
   public static void main(String [] args)
   {
      // main
   }
}