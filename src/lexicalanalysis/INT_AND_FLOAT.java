package lexicalanalysis;

/**
 * @author aftab
 */
 public class INT_AND_FLOAT
{    
     private static final int[][] INT_MATRIX = new int[][]
                                                        {{0,1},
                                                         {2,1},
                                                         {2,2},
                                                        };
     
     private static final int[][] FLOAT_MATRIX = new int[][]
                                                        {{6, 3, 1, 5},
                                                         {5, 2, 1, 7},
                                                         {5, 5, 2, 7},
                                                         {5, 5, 4, 5},
                                                         {5, 5, 4, 7},
                                                         {5, 5, 5, 5},
                                                         {5, 3, 1, 5},
                                                         {8, 5, 9, 5},
                                                         {5, 5, 9, 5},
                                                         {5, 5, 9, 5},
                                                        };
     
     public static int check(String input)
    {
         if(input.contains("."))
        {
             if (checkFloat(input))
            {
                 return 1;
            }
        }
         else
        {
             if(checkInt(input))
            {
                 return 0;
            }
        }
        
         return -1;
         
    }
     
     private static boolean checkInt(String input)
    {
         int state = 0;
         int finalState = 1;
         
         for (int i = 0; i < input.length(); i++) {
             state = nextIntState(state, input.charAt(i));
              //System.out.println("state = "+state + " for "+input.charAt(i));
        }
         
         return (finalState == state);
    }
     private static int nextIntState(int oldState,char character)
    {
         if(character == '-' || character == '+')
        {
             return INT_MATRIX[oldState][0];
        }
         else if(character >='0' && character <='9')
        {
             return INT_MATRIX[oldState][1];
        }
          return 2;    
    }
     
     private static boolean checkFloat(String input)
    {
         int state = 0;
         int[] finalState = new int[]{2,4,9};
         
         for (int i = 0; i < input.length(); i++) {
            state = (nextFloatState(state, input.charAt(i)));
        }
         
         for(int x: finalState) 
         if(state == x)
         return true;
         return false;
    }
     
     private static int nextFloatState(int oldState, char character)
    {
         if(character == '-' || character == '+')
        {
             return FLOAT_MATRIX[oldState][0];
        }
         else if(character == '.')
        {
             return FLOAT_MATRIX[oldState][1];
        }
         else if(character >= '0' && character <= '9')
        {
             return FLOAT_MATRIX[oldState][2];
        }
         else if(character == 'e')
        {
             return FLOAT_MATRIX[oldState][3];
        }
         return 5;
    }
 }