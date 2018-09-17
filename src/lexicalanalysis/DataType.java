package lexicalanalysis;

/**
 * @author aftab
 */

 public class DataType
{
     private static final String INT = "int";
     private static final String CHAR = "char";
     private static final String FLOAT = "float";
     private static final String DOUBLE = "double";
     private static final String BOOLEAN = "boolean";
     private static final String SENTENCE = "Sentence";
     
     public static boolean check(String input)
    {
         return (input.compareTo(INT)==0 || input.compareTo(CHAR)==0 || input.compareTo(FLOAT)==0 || input.compareTo(DOUBLE)==0 || input.compareTo(BOOLEAN)==0 || input.compareTo(SENTENCE)==0);
    }
}