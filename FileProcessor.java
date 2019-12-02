import java.util.*;
import java.io.*;

// reads in data from a file and stores it into an array list
public class FileProcessor
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
   
   // get the list of data as an array of strings
   public String [] getList(int c)
   {
      processData(c);
      String [] s = new String[als.size()];
      
      for (int i = 0; i < s.length; i++)
      {
         s[i] = als.get(i);
      }
      
      return s;
   }
   
   // receives data from file and places it into an arraylist
   private void processData(int c)
   {
      int count = 0;
      
      // loops through file line by line til done
      while (file.hasNext() || count == c)
      {
         // each line contains 16 spaces because addSpace() method
         als.add(addSpace(file.nextLine()));
         count++;
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