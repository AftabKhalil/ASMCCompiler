package lexicalanalysis;

/**
 * @author aftab
 */

 public class ID
{
     private static int state;
     private static final int FINAL_STATE = 1;
     
     private static final int MATRIX[][] = new int[][]
                                          { {1, 2, 2, 2, 2},
                                            {1, 1, 1, 2, 2},
                                            {2, 2, 2, 2, 2}
                                          };
     
     public static boolean check(String input)
    {
        state = 0;
   
        int len = input.length();
        
         for (int i = 0; i < len; i++)
        {
             state = matrix(state, input.charAt(i));
        }
       
         return (state == FINAL_STATE);
    }
     
     private static int matrix(int oldState, char character)
    {
        if((character>='a'&&character<='z')||(character>='A'&&character<='Z'))
        {
            return MATRIX[oldState][0];
        }
         else if(character>='0'&&character<='9')
        {
             return MATRIX[oldState][1];
        }
         else if(character =='_')
        {
             return MATRIX[oldState][2];
        }
         return 2;                
    }
}