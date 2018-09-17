package SyntaxAnalysis;

/*
 * @author aftab
 */

 public class Token
{
     public String CP;
     public String VP;
     public int LineNumber;
     
     public Token(String CP, String VP, String LineNumber)
    {
         this.CP = CP;
         this.VP = VP;
         this.LineNumber = Integer.valueOf(LineNumber);
    }
}
