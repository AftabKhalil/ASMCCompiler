package lexicalanalysis;

 public class TYP_SPS
{    
     private static final String VARIABLE = "variable";
     private static final String ARRAY    = "array";
     private static final String OBJECT   = "object";

     public static boolean check(String input)
    {
         return (input.compareTo(VARIABLE) == 0) || (input.compareTo(ARRAY) ==0) || (input.compareTo(OBJECT) == 0);
    }
}