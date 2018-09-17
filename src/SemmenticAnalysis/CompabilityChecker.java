 package SemmenticAnalysis;

 public class CompabilityChecker
{
     public static String checkCom(String leftOperand, String rightOperand, String Opp)
    {
        // System.out.println("Operand 1 --> "+leftOperand+" operand 2 --> "+rightOperand+" Opp ->> "+Opp);
         switch(Opp)
        {
             case "plus"    :
                            {
                                 if(leftOperand.compareTo("variable_int")==0 && rightOperand.compareTo("variable_int")==0)
                                {
                                     return "variable_int";
                                }
                                 if(leftOperand.compareTo("variable_int")==0 && rightOperand.compareTo("variable_float")==0)
                                {
                                     return "variable_float";
                                }
                                 if(leftOperand.compareTo("variable_float")==0 && rightOperand.compareTo("variable_int")==0)
                                {
                                     return "variable_float";
                                }
                                 if(leftOperand.compareTo("variable_float")==0 && rightOperand.compareTo("variable_float")==0)
                                {
                                     return "variable_float";
                                }
                                 if(leftOperand.compareTo("variable_Sentence")==0 && rightOperand.compareTo("variable_Sentence")==0)
                                {
                                     return "variable_Sentence";
                                }
                            }
             break;
             case "minus"   :
             case "multiply":
             case "divide"  :
             case "remender":
             case "increadedby": 
             case "decreasedby": 
             case "amplifiedby": 
             case "shrinkby": 
                            {
                                 if(leftOperand.compareTo("variable_int")==0 && rightOperand.compareTo("variable_int")==0)
                                {
                                     return "variable_int";
                                }
                                 if(leftOperand.compareTo("variable_int")==0 && rightOperand.compareTo("variable_float")==0)
                                {
                                     return "variable_float";
                                }
                                 if(leftOperand.compareTo("variable_float")==0 && rightOperand.compareTo("variable_int")==0)
                                {
                                     return "variable_float";
                                }
                                 if(leftOperand.compareTo("variable_float")==0 && rightOperand.compareTo("variable_float")==0)
                                {
                                     return "variable_float";
                                }
                            }
             break;
             case "andwith": 
             case "orwith":
                            {
                                 if(leftOperand.compareTo("variable_boolean")==0 && rightOperand.compareTo("variable_boolean")==0)
                                {
                                     return "variable_boolean";
                                }
                            }
             break;
             case "greaterthan"   : 
             case "lessthanequalto":
             case "greaterthanequalto":
             case "lessthan"      : 
             case "equalto"       :
             case "notequalto"    : 
                            {
                                 if(leftOperand.compareTo("variable_int")==0 && rightOperand.compareTo("variable_int")==0)
                                {
                                     return "variable_boolean";
                                }
                                 if(leftOperand.compareTo("variable_int")==0 && rightOperand.compareTo("variable_float")==0)
                                {
                                     return "variable_boolean";
                                }
                                 if(leftOperand.compareTo("variable_float")==0 && rightOperand.compareTo("variable_int")==0)
                                {
                                     return "variable_boolean";
                                }
                                 if(leftOperand.compareTo("variable_float")==0 && rightOperand.compareTo("variable_float")==0)
                                {
                                     return "variable_boolean";
                                }
                                 if(leftOperand.compareTo("variable_char")==0 && rightOperand.compareTo("variable_char")==0)
                                {
                                     return "variable_boolean";
                                }
                            }
             case "equals"    :
                            {
                                 if(leftOperand.compareTo("variable_int")==0 && rightOperand.compareTo("variable_int")==0)
                                {
                                     return "variable_int";
                                }
                                 if(leftOperand.compareTo("variable_int")==0 && rightOperand.compareTo("variable_float")==0)
                                {
                                     return "variable_float";
                                }
                                 if(leftOperand.compareTo("variable_float")==0 && rightOperand.compareTo("variable_int")==0)
                                {
                                     return "variable_float";
                                }
                                 if(leftOperand.compareTo("variable_float")==0 && rightOperand.compareTo("variable_float")==0)
                                {
                                     return "variable_float";
                                }
                                 if(leftOperand.compareTo("variable_char")==0 && rightOperand.compareTo("variable_char")==0)
                                {
                                     return "variable_char";
                                }
                                 if(leftOperand.compareTo("variable_boolean")==0 && rightOperand.compareTo("variable_boolean")==0)
                                {
                                     return "variable_boolean";
                                }
                                 if(leftOperand.compareTo("variable_Sentence")==0 && rightOperand.compareTo("variable_Sentence")==0)
                                {
                                     return "variable_Sentence";
                                }
                            }
        }
         return null;
    }
      public static String checkCom(String leftOperand, String rightOperand, int a)
    {
         //System.out.println("Operand 1 --> "+leftOperand+" operand 2 --> "+rightOperand);
         
         if(leftOperand.compareTo(rightOperand)==0)
        {
             return "PASSED";
        }
         else if(leftOperand.compareTo("variable_float")==0)
        {
             if(rightOperand.compareTo("variable_int")==0)
            {
                 return "PASSED";
            }
        }
        
         return null;
    }
     
     public static String checkCom(String operand, String Opp)
    {
         //System.out.println("Operand is "+operand+" and Opp is "+Opp);
         switch(Opp)
         {
             case "inc":
             case "dec":
                        {
                             if(operand.compareTo("variable_int")==0)
                                 return "variable_int";
                             else if(operand.compareTo("variable_float")==0)
                                   return "variable_float";
                        }
             case "inv":
                        {
                             if(operand.compareTo("variable_boolean")==0)
                             return "variable_boolean";
                        }
             default: return null;
         }
    }
}
