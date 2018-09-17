package lexicalanalysis;

/**
 * @author aftab
 */

 class CHAR_STRING
{
     private static final int CHAR_MATRIX[][] = new int[][]{
                                                            {1,5,5,5},
                                                            {5,2,3,2},
                                                            {4,5,5,5},
                                                            {2,5,2,2},
                                                            {5,5,5,5},
                                                            {5,5,5,5}};
     private static final int CHAR_FINAL_STATE = 4;
     
     private static final int STRING_MATRIX[][] = new int[][]{
                                                              {1,5,5,5,5},
                                                              {2,1,3,1,5},
                                                              {5,5,5,5,5},
                                                              {4,5,4,4,4},
                                                              {2,1,3,1,5},
                                                              {5,5,5,5,5}};
     private static final int STRING_FINAL_STATE = 2;
    
     static boolean check(String input)
    {
         if(input.charAt(0)=='\'') return checkChar(input);
         return checkString(input);
    }
     
     private static boolean checkChar(String input)
    {
         int state = 0;
         
         for (int i = 0; i < input.length(); i++)
        {    
             state = nextCharState(state, input.charAt(i));
        }
         return(state == CHAR_FINAL_STATE);
    }
     
     private static int nextCharState(int oldState, char character)
    {
         switch (character)
        {
             case '\'':
                 return CHAR_MATRIX[oldState][0];
             case '\\':
                 return CHAR_MATRIX[oldState][2];
             case 'n':
             case 's':
             case 't':
             case 'r':
                 return CHAR_MATRIX[oldState][3];
             default:
                 return CHAR_MATRIX[oldState][1];
         }
    }
     
     private static boolean checkString(String input)
    {
         int state = 0;
         
         for (int i = 0; i < input.length(); i++)
        {    
             state = nextStringState(state, input.charAt(i));
        }
         return (state == STRING_FINAL_STATE);
    }
     
     private static int nextStringState(int oldState, char character)
    {
         switch(character)
        {
             case '\"': return STRING_MATRIX[oldState][0];
             case '\\': return STRING_MATRIX[oldState][2];
             case 'n' :
             case 't' :
             case 's' :
             case 'r' : return STRING_MATRIX[oldState][3];
             case '\'': return STRING_MATRIX[oldState][4];
             default  : return STRING_MATRIX[oldState][1];
        }
    }
}