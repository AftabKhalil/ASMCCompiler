package ICG;

import static SyntaxAnalysis.SyntaxAnalysis.tokens;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author aftab
 */

 public class ICG
{
     private int i = 0;
     private int labelNo = -1;
     private int tempNo = -1;
     private String ct;
     private String cl;
     private String Line = "";
     private int tabs = 0;
     private String AM;
     
     private final LinkedList expTemps;
     private final LinkedList LabelList;
     
     public ICG()
    {
        expTemps = new LinkedList(); 
        LabelList = new LinkedList();
        SyntaxTrue();
    }
      
     private String createLabel()
    {
         labelNo++;
         return String.valueOf(labelNo);
    }
     private String createTemp()
    {
         tempNo++;
         return "t"+String.valueOf(tempNo);
    }
     private void generate(String printString)
    {
         String t="";
         for (int j = 0; j < tabs; j++) {
            t = t+"\t";
        }
         System.out.print("\n"+t+printString);
    }
     private void generateL(String l)
    {
         System.out.print(l+" ");
    }
     private void generateL(String l,int tabs)
    {
         String t="";
         for (int j = 0; j <tabs; j++) {
            t = t+"\t";
        }
         System.out.print(t+l+" ");
    }
     private void generateL(int tabs,String l)
    {
         System.out.println("");
         String t="";
         for (int j = 0; j <tabs; j++) {
            t = t+"\t";
        }
         System.out.print(t+l+" ");
    }
     public boolean SyntaxTrue()
    {
         if(tokens[i].CP.compareTo("A_M")==0)
        {
             generateL("\n"+tokens[i].VP);
             i++;
             if(tokens[i].CP.compareTo("STABLE")==0 || tokens[i].CP.compareTo("COLLECTION")==0)
            {
                 if(tokens[i].CP.compareTo("STABLE")==0)
                {
                     generateL(tokens[i].VP);
                     i++;
                }
                 if(tokens[i].CP.compareTo("COLLECTION")==0)
                {
                     generateL(tokens[i].VP);
                     i++;
                     if(tokens[i].CP.compareTo("ID")==0)
                    {
                         generateL(tokens[i].VP);
                         i++;
                         generateL(tabs,"{");
                         tabs++;
                         //System.out.println("TRUE HERE");
                         if(S0())return true;
                    }//error("ID");
                }  
            }//error("collection");
        }//error("access modifier");
         return false;
    }
     private boolean S0()
    {
        //System.out.println("S0 RECEIVE "+tokens[i].CP);
         if(tokens[i].CP.compareTo("A_M")==0)
        {
             AM = tokens[i].VP;
             i++;
             //System.out.println("CALLING S1 FROM S0");
             if(S1())
             {
                 //System.out.println("S0 RETURN TRUE");
                 return true;
             }
        }//error("access modifier");
         //System.out.println("S0 RETURNS FALSE");
         return false;
    }
     private boolean S1()
    {
        //System.out.println("S1 RECEIVE "+tokens[i].CP);
         if(tokens[i].CP.compareTo("STABLE")==0)
        {
             AM = AM+" "+tokens[i].VP;
             //generateL(tokens[i].VP);
             i++;
             //System.out.println("CALLING S2 FROM S1");
             if(S2())
            {
                 //System.out.println("S1 RETURNS true");
                 return true;
            }
        }
         else if(tokens[i].CP.compareTo("TYP_SPS")==0 || tokens[i].CP.compareTo("METHOD")==0)
        {
             //System.out.println("CALLING SR FROM S1");
             if(SR())
            {
                 //System.out.println("S1 RETURNING TRUE");
                 
                 return true;
            }
        }
         //System.out.println("S1 RETURNING FALSE");
         return false;
    }
     private boolean S2()
    {
         //System.out.println("s2 RECEIVED "+tokens[i].CP);
         if(tokens[i].CP.compareTo("TYP_SPS")==0)
        {
              generateL(tokens[i].VP);
             //System.out.println("S2 CALLING DECL");
             if(decl())
            {
                 //System.out.println("DECL PASSED S2 CALLING S0");
                 if(S0()) {
                     //System.out.println("S2 RETURNING TRUE");
                     return true;
                 }
            }
        }
         if(tokens[i].CP.compareTo("METHOD")==0)
        {
            generateL(tabs,AM);
             generateL(tokens[i].VP);
             i++;
             //System.out.println("S2 CALLING S3");
             if(S3()){
                 //System.out.println("S2 RETURNING TRUE");
                 return true;
             }
        }//error("method");
         //System.out.println("S2 RETURNING FALSE");
         return false;
    }
     private boolean S3()
    {
         //System.out.println("S3 RECEIVE "+tokens[i].CP);
         if(tokens[i].CP.compareTo("ID")==0)
        {
             generateL(tokens[i].VP);
             i++;
             //System.out.println("S3 CALLING S4");
             if(S4()) {
                 //System.out.println("S3 RETURNING TRUE");
                 return true;
             }
        }
         else if(tokens[i].CP.compareTo("STARTER")==0)
        {
             //System.out.println("ITS STARTER");
             generateL(tokens[i].VP);
             i++;
             //System.out.println("THE TOKEN IS "+tokens[i].CP);
             if(tokens[i].CP.compareTo("RETURNS")==0)
            {
                  generateL(tokens[i].VP);
                 //System.out.println("PASSED RETURNS");
                 i++;
                 //System.out.println("S3 CALLING type");
                 if(type())
                 {
                     //System.out.println("PASSED TYPE");
                     if(tokens[i].CP.compareTo("EXCEPTS")==0)
                     {
                         generateL(tokens[i].VP);
                         i++;
                         //System.out.println("S3 calling LIST with token "+tokens[i].CP);
                         
                         if(FDM_IL())
                        {
                             generateL(tabs,"{");
                             tabs++;
                             //System.out.println("PASSED LIST");
                             //System.out.println("S3 CALLING mst for token "+tokens[i].CP);
                             if(mst())
                            {
                                 //System.out.println("PASSED MST");
                                 //System.out.println("HERE TOKEN IS "+tokens[i].CP);
                                 if(tokens[i].CP.compareTo("DOT")==0)
                                {
                                    tabs--;
                                     generateL(tabs,"}");
                                     
                                     //System.out.println("PASSED DOT");
                                     i++;
                                     if(args())
                                     {
                                         //System.out.println("PASSED ARGS");
                                         if(tokens[i].CP.compareTo("DOT")==0)
                                        {
                                             //System.out.println("RETURN TRUE");
                                             return true;
                                        }
                                     }
                                }//error("dot");
                            }
                         }//error("Expression or nothing");
                     }//error("accepts");
                 }//error("return type");
             }//error("returns");
        }//error("id or starter");
         return false;
    }
     private boolean S4()
    {
         //System.out.println("S4 RECEIVE "+tokens[i].CP);
         //System.out.println("S4 CALLING method");
         if(method())
        {
             //System.out.println("method PASSED S4 CALLINf S0");
             if(S0()){
                 //System.out.println("S4 RETURNING TRUE");
                 return true;
             }
        }
         //System.out.println("S4 RETURNING FALSE");
         return false;
    }
     private boolean SR()
    {
         //System.out.println("SR RECEIVE "+tokens[i].CP);
         if(tokens[i].CP.compareTo("TYP_SPS")==0)
        {   
            //System.out.println("SR CALLING decl");
             if(decl())
            {
                 //System.out.println("DECL PASSED SR CALLING S0");
                 if(S0()) {
                     //System.out.println("SR RETURNING TRUE");
                     return true;
                 }
            }
        }
        else if(tokens[i].CP.compareTo("METHOD")==0)
        {
             generateL(tabs,AM);
             generateL(tokens[i].VP);
             //System.out.println("MATCH METHOD");
             i++;
             if(tokens[i].CP.compareTo("ID")==0)
            {
                 generateL(tokens[i].VP);
                 i++;
                 //System.out.println("SR CALLING S4");
                 if(S4()){
                     //System.out.println("SR RETURNING TRUE");
                     return true;
                 }
            }//error("ID");
        }//error("method or TYP_SPS");
         //System.out.println("SR RETURNING FALSE");
        return false;
     }
     private boolean F_D_M()
    {
         //System.out.println("F_D_M RECEIVE "+tokens[i].CP);
         if(tokens[i].CP.compareTo("RETURNS")==0)
        {
            generateL(tokens[i].VP);
             //System.out.println("MATCHED RETURNS");
             i++;
             //System.out.println("F_D_M CALLING TYPE");
             if(type())
            {
                //System.out.println("PASSED type");
                 if(tokens[i].CP.compareTo("EXCEPTS")==0)
                {
                    generateL(tokens[i].VP);
                     i++;
                     //System.out.println("MATCHED EXCEPTS");
                     //System.out.println("F_D_M CALLING FDM_IL");
                     if (FDM_IL())
                    {
                        //System.out.println("PASSED FDM_IL");
                        //System.out.println("F_D_M CALLING mst");
                        generateL(tabs,"{");
                        tabs++;
                         if(mst())
                        {
                            //System.out.println("MATCHED mst");
                             if(tokens[i].CP.compareTo("DOT")==0)
                            {
                                tabs--;
                                generateL(tabs,"}");
                                 i++;
                                 //System.out.println("F_D_M RETURNING TRUE");
                                 return true;
                            }//error("dot");
                        }
                    }
                }//error("accepts");
            }//error("return type");
        }//error("returns");
         //System.out.println("F_D_M REURNING FALSE");
         return false;
    }
     private boolean FDM_IL()
    {
         //System.out.println("FDM_IL RECEIVE "+tokens[i].CP);
        
         if(tokens[i].CP.compareTo("NOTHING")==0)
        {
             generateL(tokens[i].VP);
             //System.out.println("PASSED NOTHING IN FDM_IL RETRN TRUE");
             i++;
             return true;
        }
         else if(tokens[i].VP.compareTo("variable")==0 || tokens[i].VP.compareTo("array")==0 || tokens[i].VP.compareTo("object")==0)
        {
             if(FDM_IL_1())
             {
                 return true;
             }
         }
         
         //System.out.println("FDM_IL RETURN FALSE");
         return false;
    }
     private boolean FDM_IL_1()
     {
         //System.out.println("FDM_IL_1 RECEIVEED "+tokens[i].VP);
         
          if(tokens[i].VP.compareTo("variable")==0)
        {
             generateL(tokens[i].VP);
             i++;
             //System.out.println("ITS VARIABLE/ARRAY IN FDM_IL");
             if(tokens[i].CP.compareTo("ID")==0)
            {
                generateL(tokens[i].VP);
                 //System.out.println("PASSED ID AFTER VARIABLE");
                 i++;
                  if(tokens[i].CP.compareTo("OF_TYPE")==0)
                {
                    generateL(tokens[i].VP);
                     //System.out.println("PASSED OF_TYPE");
                     i++;
                     if(tokens[i].CP.compareTo("DT")==0)
                    {
                        generateL(tokens[i].VP);
                         //System.out.println("PASSED DT AFTER OF_TYPE CALLING FDMIL_1");
                         i++;
                         //System.out.println("CALLIN M_IL");
                         if(M_IL())
                        {
                             return true;
                        }
                   }//error("DataType");
                }//error("oftype");
            }//error("ID");
        }
        else if(tokens[i].VP.compareTo("array")==0)
        {
             generateL(tokens[i].VP);
             i++;
             //System.out.println("ITS ARRAY IN FDM_IL");
             if(tokens[i].CP.compareTo("ID")==0)
            {
                generateL(tokens[i].VP);
                 //System.out.println("PASSED ID AFTER VARIABLE");
                 i++;
                  if(tokens[i].CP.compareTo("OF_TYPE")==0)
                {
                    generateL(tokens[i].VP);
                     //System.out.println("PASSED OF_TYPE");
                     i++;
                     if(tokens[i].CP.compareTo("ID")==0 || tokens[i].CP.compareTo("DT")==0)
                    {
                        generateL(tokens[i].VP);
                         //System.out.println("PASSED ID AFTER OF_TYPE");
                         i++;
                         //System.out.println("CALLIN M_IL");
                         if(M_IL())
                         {
                             return true;
                         }
                   }//error("ID or DT");
                }//error("oftype");
            }//error("ID");
        }
          else if(tokens[i].VP.compareTo("object")==0)
        {
            generateL(tokens[i].VP);
             i++;
             //System.out.println("ITS VARIABLE IN FDM_IL");
             if(tokens[i].CP.compareTo("ID")==0)
            {
                generateL(tokens[i].VP);
                 //System.out.println("PASSED ID AFTER VARIABLE");
                 i++;
                  if(tokens[i].CP.compareTo("OF_TYPE")==0)
                {
                    generateL(tokens[i].VP);
                     //System.out.println("PASSED OF_TYPE");
                     i++;
                     if(tokens[i].CP.compareTo("ID")==0)
                    {
                        generateL(tokens[i].VP);
                         //System.out.println("PASSED ID AFTER OF_TYPE CALLING FDMIL_1");
                         i++;
                         //System.out.println("CALLIN M_IL");
                         if(M_IL())
                         {
                             return true;
                         }
                   }//error("ID");
                }//error("oftype");
            }//error("ID");
        }
         //error("TYP_SPS");
         
         //System.out.println("FDM_IL_1 RETURN FALSE");
         return false;
     }
     private boolean M_IL()
     {
         //System.out.println("M_IL REC "+tokens[i].CP);
         if(tokens[i].CP.compareTo("AND")==0)
        {
             generateL(", ");
             i++;
             //System.out.println("CALLING FDM_IL_1 FROM M_IL AFTER READIN GAND");
             if(FDM_IL_1())
            {
                 return true;
            }
             
        }
         else if(first_mst())
        {
             return true;
        }
         
         //System.out.println("M_IL RETURN FALSE");
         return false;
     }
     private boolean type()
    {
         //System.out.println("TYPE RECEIVE "+tokens[i].CP);
         if(tokens[i].CP.compareTo("NOTHING")==0 || tokens[i].CP.compareTo("ID")==0 || tokens[i].CP.compareTo("DT")==0)
        {
             generateL(tokens[i].VP);
             i++;
             //System.out.println("TYPE RETURN TRUE with token "+tokens[i].CP);
             return true;
        }//error("nothing or ID or DataType");
         //System.out.println("TYPE RETURN FALSE");
         return false;
    }
     private boolean mst()
    {
         //System.out.println("IN MST WITH "+tokens[i].CP);
         if(tokens[i].CP.compareTo("DOT")==0)
        {
             //System.out.println("RETURNING TRUE FROM MST");
             return true;
        }
         else if(tokens[i].CP.compareTo("TYP_SPS")==0)
        {
             if(decl())
            {
                 //System.out.println("ONE STATEMENT PASSED CALLING AGAIN MST");
                 if(mst())
                {
                    return true;
                }
            }
             
        }
         else if(tokens[i].CP.compareTo("IS")==0)
        {
             cl = "L"+createLabel();
             String cll = cl;
             i++;
             if(E())
            {
                 String ts = (String) expTemps.removeLast();
                 generate("IF "+ts+" == FALSE");
                 generateL(tabs,"JMP "+cl);
                 
                 tabs++;
                 if(mst())
                 {
                     tabs--;
                     //System.out.println("MST PASSED ");
                     if(tokens[i].CP.compareTo("DOT")==0)
                     {
                         i++;
                         //System.out.println("CALLING ELSE");
                         if(else_(cll))
                        {
                             //System.out.println("RETURNED FROM ELSE_");
                             //System.out.println("IS CALLING MST");
                             if(mst())
                            {
                                 return true;
                            }
                        }
                     }//error("dot");
                 }//error("dot");
            }//error("expression");
        }
         else if(tokens[i].CP.compareTo("ANLZ")==0)
        {
             String cl1 = "L"+createLabel();
             LabelList.add(cl1);
             String cl2 = "L"+createLabel();
             i++;
             //System.out.println("CALLING E FROM ANLZ");
             if(E())
            {
                 ct = (String) expTemps.removeLast();
                 //System.out.println("E PASSED CALLIND S_BODY");
                 if(s_body(ct,cl2))
                 {
                     //System.out.println("CHECKING DOT OF ANLZ");
                     if(tokens[i].CP.compareTo("DOT")==0)
                     {
                         generate(cl1+":");
                         LabelList.removeLast();
                         i++;
                         //System.out.println("ANLZ PASSED CHECKING OTHER LINE");
                         if(mst())
                         {    
                             return true;
                         }//error("dot");
                     }//error("dot");
                 }
            }//error("Expression");
        }
         else if(tokens[i].CP.compareTo("RPT_WHL")==0)
        {
             i++;
             //System.out.println("CALLING E FROM REP_WHL");
             if(E())
            {
                 //System.out.println("E PASSED CALLING MST");
                 if(mst())
                {
                     //System.out.println("CALLING MST");
                     if(tokens[i].CP.compareTo("DOT")==0)
                    {    i++;
                         //System.out.println("RPT_WHL PASSED CHECKING OTHER LINES");
                         if(mst())
                        {
                            return true;
                        }
                    }//error("dot");
                 }//error("dot");
             }//error("Expression");
             
         }
         else if(tokens[i].CP.compareTo("RPT")==0)
        {
             i++;
             //System.out.println("CALLING MST FROM RPT");
             if(mst())
            {
                 if(tokens[i].CP.compareTo("DOT")==0)
                 {
                     i++;
                     if(tokens[i].CP.compareTo("RPT_WHL")==0)
                     {
                         i++;
                         //System.out.println("CALLING E FROM RPT_WHL");
                         if(E())
                        {
                             if(tokens[i].CP.compareTo("DOT")==0)
                            {
                                 i++;
                                 //System.out.println("RPT PASSED");
                                 //System.out.println("CALLIMG MST");
                                 if(mst())
                                {
                                     //System.out.println("RETURNING TRUE");
                                     return true;
                                }//error("dot");
                            }//error("dot");
                        }//error("Expression");
                     }//error("repeatwhile");
                 }//error("dot");
            }//error("dot");
             
        }
         else if(tokens[i].CP.compareTo("RPT_IT")==0)
        {
             //System.out.println("IN RPT_IT");
             i++;
             //System.out.println("CALLING R_FOR");
             if(r_for())
            {
                 if(tokens[i].CP.compareTo("TYP_SPS")==0 || tokens[i].CP.compareTo("IS")==0 || tokens[i].CP.compareTo("ANLZ")==0  || tokens[i].CP.compareTo("RPT_WHL")==0  || tokens[i].CP.compareTo("RPT")==0  || tokens[i].CP.compareTo("RPT_IT")==0  || tokens[i].CP.compareTo("RETURN")==0 || tokens[i].CP.compareTo("DOT")==0)
                {
                    //System.out.println("CALLIN MST AFTER RPT_IT");
                    if(mst())
                    {
                         //System.out.println("RETURN TRUE FROM RPT_IT");
                         return true;
                    }//error("dot");
                }//error("dot");
            }
        }
         else if(tokens[i].CP.compareTo("RETURN")==0)
        {  
             generateL(tabs,tokens[i].VP);
             i++;
             //System.out.println("IN RETURN ");
             //System.out.println("CALLING RT");
             if(RT())
            {
                 //System.out.println("CALLING MST");
                 if(mst())
                 {
                     return true;
                 }//error("dot");
            }//error("nothing or return type");
        }
          else if(tokens[i].CP.compareTo("EXIT")==0)
        {
             generate("JMP "+LabelList.getLast());
             i++;
             if(tokens[i].CP.compareTo("DOT")==0)
             {
                 i++;
                 return true;
             }
         }
         else if(tokens[i].CP.compareTo("ID")==0)
        {
             generateL(tabs,tokens[i].VP);
             i++;
             //System.out.println("CALLING M_ID");
             if(M_ID())
            {
                 //System.out.println("M_ID PASSED CALLING MST AGAIN");
                 if(mst())
                {
                     return true;
                }
            }
         }
          else if(tokens[i].CP.compareTo("INCDEC")==0)
        {
             i++;
             if(tokens[i].CP.compareTo("ID")==0)
             {
                 i++;
                 generate(tokens[i].VP+" = "+tokens[i].VP+"+1");
                 if(mst())
                 {
                     return true;
                 }
             }
        }
         //error("dot");
         //System.out.println("RETURNING FALSE FROM MST");
         return false;
    }
     private boolean M_ID()
     {
         //System.out.println("M_ID RECEIVE "+tokens[i].CP);
         if(tokens[i].CP.compareTo("EQUALS")==0)
        {
            generateL("=");
             i++;
             //System.out.println("EQUALS AFTER ID");
             //System.out.println("CALLING E");
             if(E())
             {
                 generateL((String) expTemps.removeLast());
                 //System.out.println("E RETURNED SO RETURNING TRUE");
                 return true;
             }//error("Expression");
        }
         if(tokens[i].CP.compareTo("POS")==0)
        {
            generateL(".");
             i++;
             //System.out.println("RECEIVED POS");
             //System.out.println("RECEIVED GERE "+tokens[i].CP);
             if(tokens[i].CP.compareTo("INT_CNST")==0 || tokens[i].CP.compareTo("ID")==0)
            {
                generateL(tokens[i].VP);
                 i++;
                 if(tokens[i].CP.compareTo("EQUALS")==0)
                {
                    generateL(" = ");
                    i++;
                    //System.out.println("CALLING E");
                    if(E())
                    {
                        generateL((String) expTemps.removeLast());
                        //System.out.println("E PASSED RETURNING TRUE");
                        return true;
                    }//error("Expression");
                }//error("equals");
            
            }//error("int_const or ID");
             
        }
         if(tokens[i].CP.compareTo("INCDEC")==0)
        {
             i--;
             generateL("= "+tokens[i].VP+" + 1");
             i++;
             
             
             //System.out.println("IM MID WITH INCDEC AFTER ID");
             i++;
             return true;
        } //error("equals or ' or incdec");
         //System.out.println("M_ID RETURN FALSE");
         return false;
     }
     private boolean RT()
    {
         //System.out.println("RT RECEIVE "+tokens[i].CP);
         if(E())
         {
             generateL((String) expTemps.removeLast());
             if(tokens[i].CP.compareTo("DOT")==0)
             {
                 i++;
                 if(tokens[i].CP.compareTo("DOT")==0)
                {
                    return true;
                }
             }//error("dot");
         }
         else if(tokens[i].CP.compareTo("DOT")==0)
        {
                 i++;
                 if(tokens[i].CP.compareTo("DOT")==0)
                {
                    return true;
                }
        }
         //error("ID or Const or dot");
         //System.out.println("RT RETURNING FALSE");
         return false;
     }
     private boolean r_for()
    {
         String temp1 = createTemp();
         String cll = "L"+createLabel();
         String cll2 = "L"+createLabel();
         generate(temp1 +" = 0");
         generate(cll+":");
         tabs++;
         //System.out.println("IN R_FOR With "+tokens[i].CP);
         if(E())
        {
             String temp2 = (String) expTemps.removeLast();
             String temp3 = createTemp();
             generate(temp3+" = "+temp1+" < "+temp2);
             generate("IF ("+temp3+" == FALSE)");
             generate("JMP "+cll2);
             LabelList.add(cll2);
             if(tokens[i].CP.compareTo("TIMES")==0)
             {
                 i++;
                 if(mst())
                 {
                     
                     if(tokens[i].CP.compareTo("DOT")==0)
                     {
                         LabelList.removeLast();
                         generate(temp1+" = "+temp1+" + 1");
                         generate("JMP "+cll);
                         tabs--;
                         generate(cll2+":");
                         i++;
                         //System.out.println("RETURNING TRUE FROM R_FOR");
                         return true;
                     }//error("dot");
                 }//error("dot");
             } //error("times");
        }
         else if(tokens[i].CP.compareTo("FOREACH")==0)
        {
            //System.out.println("PASSED FOREACH");
             i++;
             //System.out.println("CALLING F_R");
             if(f_r())
             {
                 //System.out.println("RETURNIN TRUE FROM R_For");
                 //System.out.println("TOKEN IS "+tokens[i].CP);
                 return true;
             }
        }
         //error("int_const or foreach");
         
         //System.out.println("R_For return false");
         return false;
     }
     private boolean f_r()
    {
         
         //System.out.println("FR RECEIVE "+tokens[i].CP);
         if(tokens[i].VP.compareTo("variable")==0)
        {
             i++;
             //System.out.println("VARIABLE PASSED CHECKING ID");
             if(tokens[i].CP.compareTo("ID")==0)
             {
                 //System.out.println("ID PASSED ");
                 i++;
                 if(tokens[i].CP.compareTo("OF_TYPE")==0)
                 {
                     i++;
                     //System.out.println("OFTYPE PASSED");
                     if(tokens[i].CP.compareTo("DT")==0)
                     {
                         i++;
                         //System.out.println("DT PASSED ");
                         if(tokens[i].CP.compareTo("BLNG_TO")==0)
                        {
                             //System.out.println("BELONGS TO PASSED");
                             i++;
                             if(tokens[i].CP.compareTo("ID")==0)
                             {
                                 i++;
                                 //System.out.println("CALLING MST");
                                 if(mst())
                                 {
                                     if(tokens[i].CP.compareTo("DOT")==0)
                                     {
                                         i++;
                                         //System.out.println("RETURNING TRUE FROM F_R");
                                         return true;
                                     }//error("dot");
                                 }//error("dot");
                             }//error("ID");
                        }//error("belongto");
                     }//error("datatype");
                 }//error("oftype");
             }//error("id");
        }//error("variable");
         
         //System.out.println("F_R RETURNING FALSE");
         return false;
     }
     private boolean s_body(String ct, String label)
    {
         //System.out.println("S_BoDY REC "+tokens[i].CP);
         
         if(tokens[i].CP.compareTo("MATCHES")==0)
        {
             //System.out.println("CALLING CASES FROM S_BODY");
             if(cases(ct,label))
             {
                 //System.out.println("S_BODY RETURN TRUE");
                 return true;
             }
         }
         else if(tokens[i].CP.compareTo("ALTERNATIVE")==0)
         {
             //System.out.println("CALLING DEFAULT FROM S_BODY");
             if(default_(label))
             {
                 //System.out.println("RETURNING TRUE FROM S_BODY");
                 return true;
             }
         }
         else if(tokens[i].CP.compareTo("DOT")==0)
        {
             generate(label+":");
             //System.out.println("RETURNING TRUE FOR DOT");
             return true;
        }//error("matches or alternative or dot");
         
         
         //System.out.println("S_BODY RETURN FALSE");
         return false;
    }
     private boolean cases(String ct, String label)
    {
         //System.out.println("IN CASSES WITH "+tokens[i].CP);
         if(tokens[i].CP.compareTo("MATCHES")==0)
        {
             generate(label+":");
             i++;
             //System.out.println("CALLING S_CNST FOR MATCHES ");
             if(S_const())
            {
                 String ct1 = (String) expTemps.removeLast();
                 String ct2 = createTemp();
                 generate(ct2 +" = "+ct+" == "+ct1);
                 generate("IF "+ct2+" == FALSE");
                 String cl = "L"+createLabel();
                 generate("JMP "+cl);
                 tabs++;
                 if(mst())
                 {
                     tabs--;
                     if(tokens[i].CP.compareTo("DOT")==0)
                     {
                         i++;
                         //System.out.println("CALLING S_BODY FROM CASES");
                         if(s_body(ct,cl))
                         {
                             //System.out.println("S_BODY IS TRUE RETURN TUE");
                             return true;
                         }
                     }//error("dot");
                 }
             }
        }//error("matches");
         //System.out.println("CASSES RETURN FALSE");
         return false;
     }
     private boolean S_const()
     {
         //System.out.println("S_CONST RECEIVE "+tokens[i].CP);
         
         if(tokens[i].CP.compareTo("INT_CNST")==0 || tokens[i].CP.compareTo("CHAR_CNST")==0 ||tokens[i].CP.compareTo("STRN_CNST")==0 ||tokens[i].CP.compareTo("BOOL_CNST")==0)
         {
             String ct = createTemp();
             generate(ct +" == "+tokens[i].VP);
             expTemps.add(ct);
             i++;
             //System.out.println("S_CNST RETURN TRUE");
             return true;
         }//error("const");
         //System.out.println("S_CONST RETURN FALSE");
         return false;
     }
     private boolean default_(String label)
    {
         //System.out.println("DEFAULT_ REC "+tokens[i].CP);
         if(tokens[i].CP.compareTo("ALTERNATIVE")==0)
         {
             generate(label+":");
             i++;
             tabs++;
             if(mst())
             {
                 tabs--;
                 if(tokens[i].CP.compareTo("DOT")==0)
                 {
                     i++;
                     //System.out.println("RETURNING TRUE FROM DEFAULT_");
                     return true;
                 }//error("dot");
             }
         }//error("default");
         //System.out.println("RETURN FALSE FROM DEFAULT_");
         return false;
     }
     private boolean const_()
    {
         if(tokens[i].CP.compareTo("INT_CNST")==0 || tokens[i].CP.compareTo("BOOL_CNST")==0 || tokens[i].CP.compareTo("FLOAT_CNST")==0 || tokens[i].CP.compareTo("STRN_CNST")==0|| tokens[i].CP.compareTo("CHAR_CNST")==0)
         {
             expTemps.add(tokens[i].VP);
             i++;
             //System.out.println("CONST RETURN TRUE");
             return true;
         }
         //System.out.println("RETURN FALSE FROM CONST");
         return false;
    }
     private boolean else_(String label)
    {
         //System.out.println("ELSE RECEIVED "+tokens[i].CP);
         
         if(tokens[i].CP.compareTo("NOT_SO")==0)
        {
            cl = "L"+createLabel();
            generate("JMP "+cl);
            generate(label+":");
            i++;
             //System.out.println("CALLING ELSE 1");
             if(else_1(cl))
            {
                 //System.out.println("RETURNIG TRUE FROM IFELSE");
                 return true;
            }
        }
         else if(first_mst())
        {   
             generate(label+":");
             //System.out.println("RETURNING TRUE FROM ELSE_");
             return true;
        }//error("notso or dot");
         
         
         //System.out.println("else_ returning false ");
         return false;
     }
     private boolean else_1(String label)
    {
         //System.out.println("ELSE 1 REC "+tokens[i].CP);
         
         if(tokens[i].CP.compareTo("IS")==0)
         {
             cl = "L"+createLabel();
             String cll = cl;
             i++;
             //System.out.println("CALLING e FROM ELSE_1");
             if(E())
             {
                 String ts = (String) expTemps.removeLast();
                 generate("IF "+ts+" == FALSE");
                 generate("JMP "+cl);
                 //System.out.println("CALLING MST FROM ELSE1");
                 tabs++;
                 if(mst())
                {
                     //System.out.println("CHECKING DOT");
                     if(tokens[i].CP.compareTo("DOT")==0)
                     {
                         tabs--;
                         i++;
                         generate(label+":");
                         //System.out.println("CALLING ELSE_ FROM ELSE_!");
                         if(else_(cll))
                         {
                             return true;
                         }
                         
                     }//error("dot");
                 }//error("dot");
             }//error("expression");
             
         }
         else if(first_mst())
         {
             tabs++;
             if(mst())
             {
                 tabs--;
                 generate(label+":");
                 if(tokens[i].CP.compareTo("DOT")==0)
                 {
                     i++;
                     return true;
                 }
             }
         }//error("is ot dot");
         
         //System.out.println("else_1 return false");
         return false;
     }
     private boolean args()
    {
         //System.out.println("ARGS RECEIVED "+tokens[i].CP);
         if(tokens[i].CP.compareTo("DOT")==0)
        {
             tabs--;
            generateL(tabs,"}");
           
             //System.out.println("ARGS RETURNING TRUE");
             return true;
        }
         else if(tokens[i].CP.compareTo("A_M")==0)
        {
             AM = tokens[i].VP;
             //System.out.println("PASSED AM IN ARGS");
             i++;
             if(tokens[i].CP.compareTo("STABLE")==0)
            {
                 AM = AM +" "+tokens[i].VP;
                 i++;
            }
             //System.out.println("CALLING D_M_C FROM ARGS");
             if(D_M_C())
            {
                 //System.out.println("D_M_C PASSED IN ARGS CALLING OTHER ARGS");
                 if(args())
                 {
                     //System.out.println("RETURNING TRUE");
                     return true;
                 }
                 
            }
        }//error("A_M or dot");
         //System.out.println("ARGS RETURNING FALSE");
         return false;
    } 
     private boolean class_()
    {
         //System.out.println("IN CLASS WITH "+tokens[i].CP);
         if(tokens[i].CP.compareTo("COLLECTION")==0)
        {
             generateL(tabs,AM);
             AM = "";
             generateL(tokens[i].VP);
             i++;
             //System.out.println("CHECKED COLLECTION CHECKING ID");
             if(tokens[i].CP.compareTo("ID")==0)
            {
                 generateL(tokens[i].VP);
                 i++;
                 //System.out.println("PASSED ID ");
                 //System.out.println("CALLING CLASS CONTECTS FROM CLASSS ");
                 
                 if(tokens[i].CP.compareTo("A_CHILD_OF")==0)
                {
                     generateL(tokens[i].VP);
                     i++;
                     //System.out.println("INHERITANCE PASSSED");
                     if(tokens[i].CP.compareTo("ID")==0)
                    {
                         generateL(tokens[i].VP);
                         //System.out.println("CHECKING ID OF PARENT");
                         i++;
                    }
                }
                 generateL(tabs,"{");
                 tabs++;
                 if(class_contents())
                {
                     //System.out.println("PASSED CLASS CONTENTS IN CLASS_");
                     //System.out.println("CLASS CHECKING DOT");
                     if(tokens[i].CP.compareTo("DOT")==0)
                    {
                         tabs--;
                         generateL(tabs,"}");
                         
                         i++;
                         //System.out.println("CLASS RETURNING TRUE");
                         return true;
                    }//error("dot");
                }
            }//error("ID");
        }//error("collection");
         //System.out.println("CLASS RETURNING FALSE");
         return false;
    }
     private boolean class_contents()
    {
         //System.out.println("CLASS_CONTENTS RECEIVED "+ tokens[i].CP);
         if(tokens[i].CP.compareTo("A_M")==0)
        {
             AM = tokens[i].VP;
             i++;
             if(tokens[i].CP.compareTo("STABLE")==0)
            {
                 AM = AM + " "+tokens[i].VP;
                 i++;
            }
             if(C_C())
             {
                 //System.out.println("RETURNING TRUE FROM CLASS_CONTENTS");
                 return true;
             }
        }
         else if(tokens[i].CP.compareTo("DOT")==0)
        {
             //System.out.println("RETURNING TRUE FROM CLASS CONTENTS ");
             return true;
        }//error("AM or dot");
         
         //System.out.println("RETURN FALSE FROM CLASS_CONTENT");
         return false;
    }
     private boolean C_C()   
    {
         //System.out.println("C_C RECEIVE "+tokens[i].CP);
         if(tokens[i].CP.compareTo("TYP_SPS")==0)
        {
             if(decl())
            {
                 //System.out.println("CALLING MORE FROM C_C");
                 if(more())
                {
                     //System.out.println("RETURNING TRUE FROM C_C");
                     return true;
                }
            }
        }
         else if(tokens[i].CP.compareTo("METHOD")==0)
        { 
             generateL(tabs,AM);
             AM = "";
             generateL(tokens[i].VP);
             //System.out.println("ITS METHOD IN C_C");
             i++;
             if(tokens[i].CP.compareTo("ID")==0)
            {
                 generateL(tokens[i].VP);
                 i++;
                 //System.out.println("CALLING METHOD FROM C_C");
                 if(method())
                 {
                     //System.out.println("METHOD PASSED");
                     //System.out.println("CALLING MORE FROM C_C");
                    if(more())
                    {
                         //System.out.println("RETURNING TRUE FROM C_C");
                         return true;
                    }
                 }
            }//error("ID");
        }
         else if(tokens[i].CP.compareTo("COLLECTION")==0)
        { 
             if(class_())
            {
                 return true;
            }
        }
         //System.out.println("RETURNING FALSE FROM C_C");
         return false;
     }
     private boolean method()
    {
         //System.out.println("METHOD RECEIVE "+tokens[i].CP);
         if(tokens[i].CP.compareTo("EXCEPTS")==0)
        {
            generateL(tokens[i].VP);
            i++;
            //System.out.println("CALLING FDM_IL FROM MRTHOD");
             if(FDM_IL())
            {
                generateL(tabs,"{");
                tabs++;
                 //System.out.println("IL PASSED CALLING MST");
                 if(mst())
                {
                     //System.out.println("MST PASSED ");
                     if(tokens[i].CP.compareTo("DOT")==0)
                     {
                         tabs--;
                         generateL(tabs,"}");
                         
                         i++;
                         //System.out.println("RETURNING TRUE FROM MOETHOD");
                         return true;
                     }//error("dot");
                }
            }
        }
         else if(F_D_M())
        {
             //System.out.println("CALLED F_D_M FROM METHOD IT PASSED RETURNING TRUE");
             return true;
        }//error("expects or returns");
         //System.out.println("RETURNING FALSE FROM METHOD");
         return false;
    }
     private boolean more()
    {
         //System.out.println("in more with "+tokens[i].CP);
         if(tokens[i].CP.compareTo("A_M")==0)
        {
             AM = tokens[i].VP;
             i++;
             //System.out.println("CALLING C_C FROM MORE");
             if(C_C())
            {
                 //System.out.println("RETURNING TRUE FROM MORE");
                 return true;
            }
        }
         else if(tokens[i].CP.compareTo("DOT")==0)
        {
             //System.out.println("RETURNING TRUE FROM MORE ");
             return true;
        }//error("AM or dot");
         //System.out.println("RETURNING FALSE FROM MORE");
         return false;
    }
     private boolean D_M_C()
    {
         //System.out.println("D_M_C RECEIVE "+tokens[i].CP);
         if(tokens[i].CP.compareTo("TYP_SPS")==0)
        {
             //System.out.println("CALLING DECL FROM D_M_C");
             if(decl())
            {
                 //System.out.println("RETURNING TRUE FROM D_M_C");
                 return true;
            }
        }
         else if(tokens[i].CP.compareTo("METHOD")==0)
        {
            generateL(tabs,AM);
             generateL(tokens[i].VP);
             i++;
             //System.out.println("ITS METHOD IN D_M_C CHECKING ID ");
             if(tokens[i].CP.compareTo("ID")==0)
            {
                 generateL(tokens[i].VP);
                i++;
                //System.out.println("CALLING F_D_M FROM D_M_C");
                 if(F_D_M())
                {
                     //System.out.println("RETURNING TRUE FROM D_M_C");
                     return true;
                }
            }//error("ID");
        }
         else if(tokens[i].CP.compareTo("COLLECTION")==0)
        { 
             //System.out.println("CALLING CLASS FROM D_M_C");
             if(class_())
            {
                 //System.out.println("RETURNING TRUE FROM ARGS AFTER CHECKING CLASS");
                 return true;
            }
        }//error("TYP_SPS or method or collection");
         //System.out.println("RETURNING FALSE FROM D_M_C");
        return false;
    }
     private boolean decl()
    {
         //System.out.println("1076 Reading in decl from "+tokens[i].CP);
 
         if(tokens[i].CP.compareTo("TYP_SPS")==0)
        {
             if(tokens[i].VP.compareTo("array")==0)
             {
                 Line = AM + " "+tokens[i].VP;
                 i++;
                 //System.out.println("ITS ARRAY  NEXT IS "+tokens[i].CP+" "+tokens[i].VP);
                 if(tokens[i].CP.compareTo("ID")==0)
                 {
                     Line = Line +" "+tokens[i].VP;
                     //System.out.println("HEREEEEEEE");
                     i++;
                     //System.out.println("Token is "+tokens[i].CP+" "+tokens[i].VP);
                     //System.out.println("CALLING A FROM DECL WITH "+tokens[i].CP);
                     if(A())
                     {
                         generateL(tabs,Line);
                         Line = "";
                         //System.out.println("A returning true");
                         if(tokens[i].CP.compareTo("OF_TYPE")==0)
                         {
                             generateL(tokens[i].VP);
                             i++;
                             if(tokens[i].CP.compareTo("DT")==0 || tokens[i].CP.compareTo("ID")==0)
                             {
                                 generateL(tokens[i].VP);
                                 i++;
                                 //System.out.println("RETURNING TRUE FROM DECL");
                                 return true;
                             }//error("DT or ID");
                         }//error("oftype");
                     }
                 }//error("ID");
                
             }
             else if(tokens[i].VP.compareTo("variable")==0)
            {
                 Line = AM+ " " +tokens[i].VP;
                 i++;
                 if(tokens[i].CP.compareTo("ID")==0)
                 {
                     Line = Line + " "+tokens[i].VP;
                     i++;
                     //System.out.println("CALLING V FROM DECL");
                     if(V())
                     {
                         generateL(tabs,Line);
                         Line="";
                         if(tokens[i].CP.compareTo("OF_TYPE")==0)
                         {
                             generateL(tokens[i].VP);
                             i++;
                             if(tokens[i].CP.compareTo("DT")==0)
                             {
                                 generateL(tokens[i].VP);
                                 i++;
                                 //System.out.println("RETURNING TRUE FROM DECL");
                                 return true;
                             }//error("DT");
                         }//error("oftype");
                     }
                 }//error("ID");
            }
             else if(tokens[i].VP.compareTo("object")==0)
            {
                 generateL(tokens[i].VP);
                 i++;
                 if(tokens[i].CP.compareTo("ID")==0)
                 {
                     generateL(tokens[i].VP);
                     i++;
                     //System.out.println("CALLING O FROM DECL");
                     if(O())
                     {
                         //System.out.println("O Passed");
                         if(tokens[i].CP.compareTo("OF_TYPE")==0)
                         {
                             generateL(tokens[i].VP);
                             i++;
                             if(tokens[i].CP.compareTo("ID")==0)
                             {
                                 i++;
                                 //System.out.println("RETURNING TRUE FROM DECL");
                                 return true;
                             }//error("ID");
                         }//error("oftype");
                     }
                 }//error("ID");
            }
        }//error("TYP_SPS");
         //System.out.println("DECL RETURNING FLASE");
         return false;
    }
     private boolean O()
     {
         //System.out.println("O received "+tokens[i].CP);
         
         if(tokens[i].CP.compareTo("BUILD_WITH")==0)
         {
             generateL(tokens[i].VP);
             i++;
             //System.out.println("BUILD WITH PASSED");
             if(tokens[i].CP.compareTo("NOTHING")==0)
             {
                 generateL(tokens[i].VP);
                 i++;
                 //System.out.println("RETURN TRUE FOR NOTHIN G");
                 return true;
             }
             else if(list())
             {
                 //System.out.println("E RETURNED TRUE ");
                 return true;
             }//error("nothing or argument list");
         }//error("buildwith");
         
         //System.out.println("O returning false");
         return false;
         
     }
     private boolean V()
    {
         //System.out.println("V RECEIVE "+ tokens[i].CP);
         
         if(tokens[i].CP.compareTo("OF_TYPE")==0)
         {
             //System.out.println("Simply return from V");
             return true;
         }
         else if(tokens[i].CP.compareTo("EQUALS")==0)
        {
             //System.out.println("CALLINH INIT FROM V");
             if(init())
            {
                 //System.out.println("INIT PASSED CALLING V1");
                 if(V1())
                {
                     //System.out.println("V1 passed returning true");
                     return true;
                }
            }
        }
         else if(tokens[i].CP.compareTo("AND")==0)
        {
             
             //System.out.println("CALLING V1 FROM V");
             if(V1())
             {
                 //System.out.println("RETURNING TRUE FROM V");
                 return true;
             }
         }//error("and or equals or oftype");
         //System.out.println("V returning false");
         return false;
     }
     private boolean init()
    {
         //System.out.println("In init with "+tokens[i].CP);
         if(tokens[i].CP.compareTo("EQUALS")==0)
        {
             Line = Line +" =";
             i++;
             //System.out.println("ITS EQUALS CHECKING CALLING E");
             //System.out.println("TOKNN IS "+tokens[i].CP);
             if(first_k())
            {
                 if(E())
                {
                     String ts = (String) expTemps.removeLast();
                     Line = Line + " "+ts;
                     //System.out.println("init returning true");
                     return true;
                }
            }//error("expression");
         }
         else if(tokens[i].CP.compareTo("AND")==0)
         {
             //System.out.println("CALLING V1 FROM INIT");
             if(V1())
             {
                 //System.out.println("INIT RETURN TRUE");
                 return true;
             }
         }
         else if(tokens[i].CP.compareTo("OF_TYPE")==0)
         {
             //System.out.println("INIT RETURN TRUE");
             return true;
         }//error("equals or and or oftype");
         //System.out.println("init returning false");
         return false;
     }
     private boolean V1()
    {
         //System.out.println("V1 receive "+tokens[i].CP);
         if(tokens[i].CP.compareTo("OF_TYPE")==0)
         {
             //System.out.println("V1 returns true");
             return true;
         }
         else if(tokens[i].CP.compareTo("AND")==0)
         {
             Line = Line + ", ";
             i++;
             if(tokens[i].CP.compareTo("ID")==0)
             {
                 Line = Line + tokens[i].VP;
                 i++;
                 //System.out.println("ID PASSSED calling init");
                 if(init())
                 {
                     //System.out.println("INIT PASSED CALLING V1 FROM V1");
                     if(V1())
                     {
                         //System.out.println("V1 passed in V1 return true");
                         return true;
                     }
                 }
             }//error("ID");
         }//error("oftyupe or and");
         //System.out.println("V1 returning false");
         return false;
    }
     private boolean A()
    {
         //System.out.println("IN A with "+tokens[i].CP);
         if (tokens[i].CP.compareTo("BUILD_WITH")==0)
         {
             i++;
             if(tokens[i].CP.compareTo("SIZE")==0)
             {
                 i++;
                 if(tokens[i].CP.compareTo("INT_CNST")==0)
                 {
                     Line = Line+"["+tokens[i].VP+"]";
                     //System.out.println("Line is "+Line);
                     i++;
                     //System.out.println("Calling A1");
                     if(A1())
                    {
                         //System.out.println("A returning true");
                         return true;
                    }
                }//error("int_const");
             }//error("size");
         }
         else if (tokens[i].CP.compareTo("HVNG_ELMNT")==0)
         {
             Line = Line +" = {";
             i++;
             //System.out.println("CALLING LIST FROM A");
             if(list_a())
             {
                 Line = Line +"}";
                 //System.out.println("CALLING A2 FROM A");
                 if(A2())
                 {
                     //System.out.println("RETURNING TRUE FROM A");
                     return true;
                 }
             }
             
         }
         else if(A2())
        {
                 return true;
        }
         else if(tokens[i].CP.compareTo("Of_TYPE")==0)
         {
             return true;
         }//error("buildwith or havingelements or and or oftype");
         //System.out.println("A returning false");
         return false;
     }
     private boolean A1()
    {
         
         //System.out.println("A1 receive "+tokens[i].CP);
         if(tokens[i].CP.compareTo("ANOTHER")==0 || tokens[i].CP.compareTo("OF_TYPE")==0)
         {
             //System.out.println("CALLING A2");
             
             if(A2())
             {
                 //System.out.println("A1 returning teue");
                    return true;
             }
         }
         else if(tokens[i].CP.compareTo("HVNG_ELMNT")==0)
         {
             Line = Line +" = {";
             i++;
             //System.out.println("CALLING LIST FROM A1");
             if(list_a())
             {
                 Line = Line +" }";
                 if(A2())
                {
                     //System.out.println("RETURNING TRUE FROM A1");
                     return true;
                }
             }
         }//error("and or oftype or havingelemants");
         //System.out.println("A1 returning false");
         return false;
     }
     private boolean A2()
    {
         
         //System.out.println("IN A2 with "+tokens[i].CP);
         if(tokens[i].CP.compareTo("OF_TYPE")==0)
         {
             //System.out.println("RETURNUNG TRUE FROM A2");
             return true;
         }
         else if(tokens[i].CP.compareTo("ANOTHER")==0)
         {
             Line = Line +", ";
             i++;
             if(tokens[i].CP.compareTo("ID")==0)
             {
                 Line = Line +tokens[i].VP;
                 i++;
                 //System.out.println("CALLING A FROM A2");
                 if(A())
                 {
                     //System.out.println("returning true from A2");
                             return true;
                 }
             }//error("ID");
         }//error("oftype or and");
         
         //System.out.println("RETURNING FALSE FROM A2");
         return false;
     }
     private boolean list(String line)
    {
         //System.out.println("list receive "+tokens[i].CP);
         //System.out.println("Calling E From List");
         if(E())
        {
             String ts = (String) expTemps.removeLast();
             line = line + "("+ts; 
             //System.out.println("E PASSED CALLING LIST_M");
             if(list_m(line))
            {
                 //System.out.println("list_m passed returning true from list");
                 return true;
            }
        }
         //System.out.println("list returning false");
         return false;
     }
     private boolean list_m(String line)
    {
         //System.out.println("list_m received "+tokens[i].CP);
         if(tokens[i].CP.compareTo("AND")==0)
        {
             i++;
             //System.out.println("Calling E from list_m");
             if(E())
            {
                 String ts = (String) expTemps.removeLast();
                 line = line +", "+ ts; 
                 //System.out.println("calling list_m from list_m");
                 if(list_m(line))
                {
                     //System.out.println("Returning true from list_m");
                     return true;
                }
            }//error("Expression");
        }
         else if(first_mst() ||tokens[i].CP.compareTo("END")==0 || tokens[i].CP.compareTo("OF_TYPE")==0 || tokens[i].CP.compareTo("ANOTHER")==0)
        {
             line = line +")";
             ct = createTemp();
             generate(ct + " = " +line);
             expTemps.add(ct);
             line = "";
             //System.out.println("Returning true from list_m");
             return true;
        }//error("end or and or oftype or dot");
         //System.out.println("Returning false from list_m");
         return false;
    }  
      private boolean list_a()
    {
         //System.out.println("list receive "+tokens[i].CP);
         //System.out.println("Calling E From List");
         if(E())
        { 
             String ts = (String) expTemps.removeLast();
             //generate(ts);
             Line = Line +ts;
             //System.out.println("E PASSED CALLING LIST_M");
             if(list_m_a())
            {
                 return true;
            }
        }
         //System.out.println("list returning false");
         return false;
     }
     private boolean list_m_a()
    {
         //System.out.println("list_m received "+tokens[i].CP);
         if(tokens[i].CP.compareTo("AND")==0)
        {
             Line = Line +", ";
             i++;
             //System.out.println("Calling E from list_m");
             if(E())
            {
                 String ts = (String) expTemps.removeLast();
                // generate(ts);
                 Line = Line +ts;
                 //System.out.println("calling list_m from list_m");
                 if(list_m_a())
                {
                     //System.out.println("Returning true from list_m");
                     return true;
                }
            }//error("Expression");
        }
         else if(first_mst() ||tokens[i].CP.compareTo("END")==0 || tokens[i].CP.compareTo("OF_TYPE")==0 || tokens[i].CP.compareTo("ANOTHER")==0)
        {
             //System.out.println("Returning true from list_m");
             return true;
        }//error("end or and or oftype or dot");
         //System.out.println("Returning false from list_m");
         return false;
    } 
     private boolean list()
    {
         //System.out.println("list receive "+tokens[i].CP);
         //System.out.println("Calling E From List");
         if(E())
        { 
            generateL((String) expTemps.removeLast());
             //System.out.println("E PASSED CALLING LIST_M");
             if(list_m())
            {
                 //System.out.println("list_m passed returning true from list");
                 return true;
            }
        }
         //System.out.println("list returning false");
         return false;
     }
     private boolean list_m()
    {
         //System.out.println("list_m received "+tokens[i].CP);
         if(tokens[i].CP.compareTo("AND")==0)
        {
            generateL(",");
             i++;
             //System.out.println("Calling E from list_m");
             if(E())
            {
                 generateL((String) expTemps.removeLast());
                 //System.out.println("calling list_m from list_m");
                 if(list_m())
                {
                     //System.out.println("Returning true from list_m");
                     return true;
                }
            }//error("Expression");
        }
         else if(first_mst() ||tokens[i].CP.compareTo("END")==0 || tokens[i].CP.compareTo("OF_TYPE")==0 || tokens[i].CP.compareTo("ANOTHER")==0)
        {
             //System.out.println("Returning true from list_m");
             return true;
        }//error("end or and or oftype or dot");
         //System.out.println("Returning false from list_m");
         return false;
    }    
     private boolean E() 
    {
      //System.out.println("E HERE WITH "+tokens[i].CP);
         
         if(first_k())
        {
             //System.out.println("CALLING F ROM E");
             if(F())
            {
                 //System.out.println("F PASSED CALLING E1");
                 if(E1())
                {
                     //System.out.println("E1 PASSED IN E RETURNING TRUE");
                     return true;
                }
            }
        }
         else if(foll_E())
        {
             //System.out.println("E EXPECT FOLLOW RETURN TRUE");
             return true;
        }
         //System.out.println("E returning false");
         return false;
    }
     private boolean E1()
    {
         //System.out.println("E1 receive "+tokens[i].CP);
         if(tokens[i].CP.compareTo("AO")==0)
        {
             String s = tokens[i].VP;
             s = changeS(s);
             i++;
             //System.out.println("AO PASSED IN E1 CALLING F");
             if(F())
            {
                 String ts = (String) expTemps.removeLast();
                 String td = (String) expTemps.removeLast();
                
                 ct = createTemp();
                 generate(ct + " = " + td + " " + s + " " +ts);
                 expTemps.add(ct);
                 //System.out.println("F PASSED IN E1 CALLING E1");
                 if(E1())
                {
                     //System.out.println("E1 RETURNING TRUE");
                     return true;
                }
            }
       }
         else if(foll_E())
        {
             //System.out.println("E1 expect Follow return true");
             return true;
        }
         //System.out.println("E1 RETURNING FALSE");
         return false;
     }
     private boolean F()
    {
        //System.out.println("F RECEIVE "+tokens[i].CP);
        if(first_k())
        {
             //System.out.println("CALLING G FROM F");
             if(G())
             {
                  //System.out.println("G RETURNED TRUE CALLING F1");
                  if(F1())
                  {
                       //System.out.println("F1 returned true F ");
                       //System.out.println("F1 PASSED IN F RETURNING TRUE");
                       return true;
                  }
             }
        }
         else if( foll_E() ||tokens[i].CP.compareTo("AO")==0)
        {
            //System.out.println("F RETURN TRUE FS");
            return true;
        }
        //System.out.println("F RETURNING FALSE");
        return false;
    }
     private boolean F1()
    {
         //System.out.println("F1 RECEIVE "+tokens[i].CP);
         
         if(tokens[i].CP.compareTo("OR_WITH")==0)
         {
             String s = tokens[i].VP;             
             s = changeS(s);
             i++;
             //System.out.println("OR_WITH PASSED IN F1 CALLING G");
             if(G())
             {
                  String ts = (String) expTemps.removeLast();
                 String td = (String) expTemps.removeLast();
                
                 ct = createTemp();
                 generate(ct + " = " + td + " " + s + " " +ts);
                 expTemps.add(ct);
                 //System.out.println("G PASSED IN F1 CALLING F1");
                 if(F1())
                 {
                     //System.out.println("F1 PASSED IN F1 RETURNIN GTRUE");
                     return true;
                 }
             }
         }
         else if(foll_E() ||tokens[i].CP.compareTo("AO")==0)
        {
             //System.out.println("f1 expecting follow return true");
             return true;
        }
         
         //System.out.println("F1 returning FALSE");
         return false;
     }
     private boolean G()
    {
         //System.out.println("G received "+tokens[i].CP);
         
          if(first_k())
        {
            //System.out.println("G calling H");
            if(H())
            {
                 //System.out.println("H PASSED IN G CALLING G1");
                 if(G1())
                 {
                     //System.out.println("PASSED G1 IN G , G RETURNING TRUE");
                     return true;
                 }
            }
            
        }
         else if(foll_E() ||tokens[i].CP.compareTo("AO")==0 || tokens[i].CP.compareTo("OR_WITH")==0)
        {
              //System.out.println("G RETURN TRUE EXPECTING FOLLOW");
              return true;
        }
         //System.out.println("G returning false");
         return false;
     }
     private boolean G1()
    {
         //System.out.println("G1 received "+tokens[i].CP);
         
         if(tokens[i].CP.compareTo("AND_WITH")==0)
         {
             String s = tokens[i].VP;
             s = changeS(s);
             i++;
             //System.out.println("PASSE DAND_WITH IN G1  CALLING H FROM G1");
             if(H())
             {
                 String ts = (String) expTemps.removeLast();
                 String td = (String) expTemps.removeLast();
                
                 ct = createTemp();
                 generate(ct + " = " + td + " " + s + " " +ts);
                 expTemps.add(ct);
                 //System.out.println("H passed in G1 calling G1");
                 if(G1())
                 {
                     //System.out.println("G1 passed in G1 returnign true");
                     return true;
                 }
             }
         }
         else if(foll_E() ||tokens[i].CP.compareTo("AO")==0 || tokens[i].CP.compareTo("OR_WITH")==0)
        {
              //System.out.println("G1 RETURN TRUE EXPECTING FOLLOW");
              return true;
        }
         //System.out.println("G1 RETURNING FALSE");
         return false;
     }
     private boolean H()
    {
          //System.out.println("H received "+tokens[i].CP);
          
         if(first_k())
        {
            //System.out.println("H calling I");
            if(I())
            {
                 //System.out.println("I PASSED IN H CALLING H1");
                 if(H1())
                 {
                     //System.out.println("PASSED H1 IN H , H RETURNING TRUE");
                     return true;
                 }
            }
            
        }
        else if(foll_E() ||tokens[i].CP.compareTo("AO")==0 || tokens[i].CP.compareTo("OR_WITH")==0 || tokens[i].CP.compareTo("AND_WITH")==0)
        {
              //System.out.println("H RETURN TRUE EXPECTING FOLLOW");
              return true;
        }
          //System.out.println("H returning false");
          return false;
     }
     private boolean H1()
    {
         //System.out.println("H1 received "+tokens[i].CP);
         
         if(tokens[i].CP.compareTo("RO")==0)
         {
             String s = tokens[i].VP;
             s = changeS(s);
             i++;
             //System.out.println("PASSE RO IN H1  CALLING I FROM H1");
             if(I())
             {
                 String ts = (String) expTemps.removeLast();
                 String td = (String) expTemps.removeLast();
                
                 ct = createTemp();
                 generate(ct + " = " + td + " " + s + " " +ts);
                 expTemps.add(ct);
                 
                 //System.out.println("I passed in H1 calling H1");
                 if(H1())
                 {
                     //System.out.println("H1 passed in H1 returnign true");
                     return true;
                 }
             }
         }
         else if(foll_E() ||tokens[i].CP.compareTo("AO")==0 || tokens[i].CP.compareTo("OR_WITH")==0 || tokens[i].CP.compareTo("AND_WITH")==0)
        {
              //System.out.println("H1 RETURN TRUE EXPECTING FOLLOW");
              return true;
        }
         //System.out.println("H1 RETURNING FALSE");
         return false;
     }
     private boolean I()
    {
        //System.out.println("I received "+tokens[i].CP);
          
         if(first_k())
        {
            //System.out.println("I calling J");
            if(J())
            {
                 //System.out.println("J PASSED IN I CALLING I1");
                 if(I1())
                 {
                     //System.out.println("PASSED I1 IN I , I RETURNING TRUE");
                     return true;
                 }
            }   
        }
         else if(foll_E() ||tokens[i].CP.compareTo("AO")==0 || tokens[i].CP.compareTo("OR_WITH")==0 || tokens[i].CP.compareTo("AND_WITH")==0 || tokens[i].CP.compareTo("RO")==0)
        {
              //System.out.println("I RETURN TRUE EXPECTING FOLLOW");
              return true;
        }
          //System.out.println("I returning false");
          return false;
     }
     private boolean I1()
    {
         //System.out.println("I1 received "+tokens[i].CP);
         
         if(tokens[i].CP.compareTo("ADDSUB")==0)
        {
             String s = tokens[i].VP;
             s = changeS(s);
             i++;
             //System.out.println("PASSE ADDSUB IN I1  CALLING J FROM I1");
             if(J())
            {
                 String ts = (String) expTemps.removeLast();
                 String td = (String) expTemps.removeLast();
                
                 ct = createTemp();
                 generate(ct + " = " + td + " " + s + " " +ts);
                 expTemps.add(ct);
                 
                 //System.out.println("J passed in I1 calling I1");
                 if(I1())
                {
                     //System.out.println("I1 passed in I1 returnign true");
                     return true;
                }
            }
        }
         else if(foll_E() ||tokens[i].CP.compareTo("AO")==0 || tokens[i].CP.compareTo("OR_WITH")==0 || tokens[i].CP.compareTo("AND_WITH")==0 || tokens[i].CP.compareTo("RO")==0)
        {
              //System.out.println("I1 RETURN TRUE EXPECTING FOLLOW");
              return true;
        }
         //System.out.println("I1 RETURNING FALSE");
         return false;
     }
     private boolean J()
    {
          //System.out.println("J received "+tokens[i].CP);
          
         if(first_k())
        {
            //System.out.println("J calling K");
             if(K())
            {
                 //System.out.println("K PASSED IN J CALLING J1");
                 if(J1())
                {
                     //System.out.println("PASSED J1 IN J , J RETURNING TRUE");
                     return true;
                }
            }
            
        }
         
         else if(foll_E() ||tokens[i].CP.compareTo("ADDSUB")==0 || tokens[i].CP.compareTo("RO")==0 || tokens[i].CP.compareTo("AND_WITH")==0 || tokens[i].CP.compareTo("OR_WITH")==0 ||tokens[i].CP.compareTo("AO")==0 )
        {
             //System.out.println("J WXPECTING FOOLOW RETURN TRUE");
             return true;
        }
          //System.out.println("J returning false");
          return false;
     }
     private boolean J1()
    {
         //System.out.println("J1 received "+tokens[i].CP);
         
         if(tokens[i].CP.compareTo("DIVMUL")==0)
        {
             String s = tokens[i].VP;
             s = changeS(s);
             i++;
             //System.out.println("PASSE DIVMUL IN J1  CALLING K FROM J1");
             if(K())
            {
                 String ts = (String) expTemps.removeLast();
                 String td = (String) expTemps.removeLast();
                
                 ct = createTemp();
                 generate(ct + " = " + td + " " + s + " " +ts);
                 expTemps.add(ct);
                
                 if(J1())
                {
                     //System.out.println("J1 passed in J1 returnign true");
                     return true;
                }
            }
        }
         else if(foll_E() ||tokens[i].CP.compareTo("ADDSUB")==0 || tokens[i].CP.compareTo("RO")==0 || tokens[i].CP.compareTo("AND_WITH")==0 || tokens[i].CP.compareTo("OR_WITH")==0 ||tokens[i].CP.compareTo("AO")==0 )
        {
             //System.out.println("J1 eXPECTING FOOLOW RETURN TRUE");
             return true;
        }
         //System.out.println("J1 RETURNING FALSE");
         return false;
     }     
     private boolean K()
    {
        //System.out.println("K received "+tokens[i].CP);
          
         if(tokens[i].CP.compareTo("ID")==0)
        {
             expTemps.add(tokens[i].VP);
           
             i++;
             //System.out.println("ID PASSED IN K CALLING ID1");
             if(ID1())
             {
                 //System.out.println("K returnin true");
                 return true;
             }
        }
         else if(const_())
        {
             return true;
        }
         else if(tokens[i].CP.compareTo("BY")==0)
        {
             i++;
             //System.out.println("IT WAS BY");
             //System.out.println("calling expression");
             if(E())
            {
                 //System.out.println("EXPRESSION PASSED IN E");
                //System.out.println("CHECKING THEN");
                if(tokens[i].CP.compareTo("THEN")==0)
                {
                     i++;
                     //System.out.println("THEN PASSED RETURING TRUE");
                     return true;
                }
                else if(foll_K())
                {
                    //System.out.println("FOLL_K PASSED RETURN TRUE FROM BY");
                    return true;
                }
            }
        }
         else if(tokens[i].CP.compareTo("INCDEC")==0)
        {
             String s = tokens[i].VP;
             s = changeS(s);
             i++;
             //System.out.println("INCDEC PASSED IN K");
             if(tokens[i].CP.compareTo("ID")==0)
            {
                 String ts = tokens[i].VP+" = "+tokens[i].VP+" + 1";
                 generate(ts);
                 expTemps.add(tokens[i].VP);
                 
                 i++;
                 //System.out.println("ID PASSED RETURN TRUE FROM K");
                 return true;
            }
        }
         else if(tokens[i].CP.compareTo("METHOD")==0)
        {
             //System.out.println("IN METHOD IN K CALLIN METHOD");
             String line="";
             if(method_call(line))
            {
                 //System.out.println("METHOD PASSED RETURNIN TRUE");
                 return true;
            }
        }
         else if(tokens[i].CP.compareTo("INV")==0)
         {
             i++;
             //System.out.println("REC INV CALLING E");
             if(E())
            {
                 String ts = (String) expTemps.removeLast();
                 ct = createTemp();
                 generate(ct + " = ! " + ts);
                 expTemps.add(ct);
                 //System.out.println("PASSED E RETURN TRUE");
                 return true;
            }
         }             
          
          return false;
     }  
     private boolean ID1()
    {
         //System.out.println("ID1 RECEIVED "+tokens[i].CP);
         
         if(tokens[i].CP.compareTo("INCDEC")==0)
        {
             String s = tokens[i].VP;
             s = changeS(s);
             String ts = (String) expTemps.removeLast();
             
             generate(ts + " = " + ts + " " + s + " 1");
             expTemps.add(ts);
             
             i++;
             //System.out.println("ITS INCDEC IN ID1 RETURNING TRUE");
             return true;
        }
         else if(tokens[i].CP.compareTo("POS")==0)
        {
             i++;
             //System.out.println("ITS POS CALLING C_M");
             if(C_M())
            {
                 //System.out.println("C_M PASSEED RETURNING TRUE FROM ID1");
                 return true;
            }
        }
         else if(foll_K())
        {
             //System.out.println("RETURN TRUE FROM ID1 FOR FOL_K");
             return true;
        }
         
         //System.out.println("ID1 RETURN FALSE");
         return false;
    } 
     private boolean C_M()
    {
         //System.out.println("C_M RECEIVED "+tokens[i].CP);
         
         if(tokens[i].CP.compareTo("INT_CNST")==0)
        {
             String ts = (String) expTemps.removeLast();
             ts = ts+"["+tokens[i].VP+"]";
             ct = createTemp();
             generate(ct + " = " + ts);
             expTemps.add(ct);
             i++;
             //System.out.println("IT WAS INT CONSTANT RETURN TRUE");
             return true;
        }
         else if(tokens[i].CP.compareTo("ID")==0)
        {
             String ts = (String) expTemps.removeLast();
             ts = ts+"."+tokens[i].VP;
             ct = createTemp();
             generate(ct + " = " + ts);
             expTemps.add(ct);
             i++;
             //System.out.println("IT WAS ID CONSTANT RETURN TRUE");
             return true;
        }
         else if(tokens[i].CP.compareTo("METHOD")==0)
        {
             String ts = (String) expTemps.removeLast();
             String line = ts+".";
             //System.out.println("IT WAS METHOD CALLING METHOD FROM C_M");
             if(method_call(line))
             {
                 //System.out.println("METHOD CALL PASSED RETURN TRUE");
                 return true;
             }
         }//error("int_const or Id or method");
         //System.out.println("C_M return false");
         return false;
    }
     private boolean method_call(String line)
    {
         //System.out.println("METHOD CALL RECEIVED "+tokens[i].CP);
         if(tokens[i].CP.compareTo("METHOD")==0)
         {
             i++;
             //System.out.println("IT WAS  METHOD CHECKING ID");
             if(tokens[i].CP.compareTo("ID")==0)
             {
                 line = line + tokens[i].VP;
                 i++;
                 //System.out.println("ID PASSED ");
                 if(tokens[i].CP.compareTo("WITH")==0)
                {
                     i++;
                     //System.out.println("PASSED WITH ");
                     if(tokens[i].CP.compareTo("NOTHING")==0)
                     {
                         line = line + "( )";
                         ct = createTemp();
                         generate(ct +" = "+line);
                         expTemps.add(ct);
                         i++;
                         //System.out.println("METHOD WITH NOTHING RETURN TRUE");
                         return true;
                     }
                     else if(first_k())
                    {
                         //System.out.println("ITS E AFTER WITH CALLING LIST");
                         if(list(line))
                        {
                             //System.out.println("LIST PASSED RETUR TRUE");
                             //System.out.println("CHECKING END");
                             if(tokens[i].CP.compareTo("END")==0)
                            {
                                 i++;
                                 //System.out.println("END PASSED RETURN TRUE FROM CALL _ METHOD");
                                 return true;
                            }//error("end");
                        }
                    }//error("nothing oe rxpression");
                }//error("with");
            }//error("Id");
         }//error("method");
         //System.out.println("METHOD_CALL RETURNING FALSE");
         return false;
     }  
     private boolean first_mst()
    {
         //System.out.println("FIRST_MST ANDRECEIVE "+tokens[i].CP);
         if( tokens[i].CP.compareTo("DOT")==0 || tokens[i].CP.compareTo("IS")==0 ||tokens[i].CP.compareTo("ANLZ")==0 ||tokens[i].CP.compareTo("RPT_WHL")==0 ||tokens[i].CP.compareTo("RPT")==0 ||tokens[i].CP.compareTo("RPT_IT")==0 ||tokens[i].CP.compareTo("RETURN")==0 ||tokens[i].CP.compareTo("TYP_SPS")==0 ||tokens[i].CP.compareTo("INCDEC")==0 ||tokens[i].CP.compareTo("ID")==0)
        {
             //System.out.println("MST RETURN TRUE");
             return true;
        }
         //System.out.println("MST RETURN FALSE");
         return false;
    }
      private boolean foll_E()
    {
         //System.out.println("Foll_E Receive "+tokens[i].CP);
         if( first_mst()  ||  tokens[i].CP.compareTo("EXIT")==0 ||  tokens[i].CP.compareTo("TIMES")==0||  tokens[i].CP.compareTo("THEN")==0 ||tokens[i].CP.compareTo("AND")==0 || tokens[i].CP.compareTo("ANOTHER")==0 ||tokens[i].CP.compareTo("END")==0 ||tokens[i].CP.compareTo("OF_TYPE")==0 ||tokens[i].CP.compareTo("MATCHES")==0 ||tokens[i].CP.compareTo("EQUALS")==0)
        {
             //System.out.println("fol_e RETURN TRUE");
             return true;
        }
         //System.out.println("fol_e RETURN FALSE");
         return false;
    }
     private boolean first_k()
    {
         //System.out.println("First_k Receive "+tokens[i].CP);
         if(tokens[i].CP.compareTo("ID")==0 || tokens[i].CP.compareTo("BY")==0 || tokens[i].CP.compareTo("INCDEC")==0 || tokens[i].CP.compareTo("METHOD")==0 || tokens[i].CP.compareTo("INV")==0 ||tokens[i].CP.compareTo("INT_CNST")==0 || tokens[i].CP.compareTo("BOOL_CNST")==0 || tokens[i].CP.compareTo("FLOAT_CNST")==0 || tokens[i].CP.compareTo("STRN_CNST")==0|| tokens[i].CP.compareTo("CHAR_CNST")==0)
        {
             //System.out.println("first_K RETURN TRUE");
             return true;
        }
         //System.out.println("first_K RETURN FALSE");
         return false;
    }
      
     private boolean foll_K()
    {
         //System.out.println("FOLL_K RECEIVED " + tokens[i].CP);
           if(foll_E()||tokens[i].CP.compareTo("DIVMUL")==0 ||tokens[i].CP.compareTo("ADDSUB")==0 || tokens[i].CP.compareTo("RO")==0 || tokens[i].CP.compareTo("AND_WITH")==0 || tokens[i].CP.compareTo("OR_WITH")==0 ||tokens[i].CP.compareTo("AO")==0 )
        {
             //System.out.println("J1 eXPECTING FOOLOW RETURN TRUE");
             return true;
        }
           //System.out.println("FOLL_K RETURN FALSE");
           return false;
    }
     
     private String changeS(String s)
    {
         switch(s)
         {
             case "inc"             : return "+";
             case "dec"             : return "-";
             case "increadedby"     : return "+=";
             case "decreasedby"     : return "-=";
             case "amplifiedby"     : return "*=";
             case "shrinkby"        : return "/=";
             case "andwith"         : return "&&";
             case "orwith"          : return "||";
             case "greaterthan"          : return ">";
             case "greaterthanequalto"   : return ">=";
             case "lessthan"             : return "<";
             case "lesstnequalto"        : return "<=";
             case "equalto"              : return "==";
             case "notequalto"           : return "!=";
             case "plus"            : return "+";
             case "minus"           : return "-";
             case "multiply"        : return "*";
             case "divide"          : return "/";
             case "remender"        : return "%";
         }
        return null;
    }
 }