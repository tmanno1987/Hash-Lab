// create a special class type for array
public class Hash
{
   // class contains info field for storing data, oha field for storing 
   // the origional hash address, and probes for storing probe count
   private String info;
   private int oha;
   private int probes;
   
   public Hash()
   {
      info = null;
      oha = -1;
      probes = 0;
   }
   
   // accessor methods for Hash class
   public String getInfo()
   {
      return info;
   }
   
   public int getOHA()
   {
      return oha;
   }
   
   public int getProbes()
   {
      return probes;
   }
   
   // mutator methods for Hash class
   public void setInfo(String data)
   {
      info = data;
   }
   
   public void setOHA(int oha)
   {
      this.oha = oha;
   }
   
   public void setProbes(int p)
   {
      probes = p;
   }
   
   // toString() method prints data to the screen
   public String toString()
   {
      if (info == null)
      {
         return info + "\t\t\t\t\t\t|\t" + oha + "\t\t\t|\t" + probes;
      }
      else
      {
         if (oha < 100)
         {
            return info + "\t\t|\t" + oha + "\t\t\t|\t" + probes;
         }
         else
         {
            return info + "\t\t|\t" + oha + "\t\t|\t" + probes;
         }
      }
   }
}