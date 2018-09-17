
package SemmenticAnalysis;

import java.util.LinkedList;
import static SyntaxAnalysis.SyntaxAnalysis.RED;
import static SyntaxAnalysis.SyntaxAnalysis.BLUE;
import static SyntaxAnalysis.SyntaxAnalysis.GREEN;
import static SyntaxAnalysis.SyntaxAnalysis.PURPLE;
import static SyntaxAnalysis.SyntaxAnalysis.tokens;
import static SemmenticAnalysis.MethodTable.insert;
import static SemmenticAnalysis.VariablesTable.lookUp;
import static SemmenticAnalysis.VariablesTable.insert;
import static SemmenticAnalysis.VariablesTable.getSize;
import static SemmenticAnalysis.MethodTable.method_lookUp;
import static SemmenticAnalysis.CompabilityChecker.checkCom;;

/**
 * @author aftab
 */

 public class SymenticAnalysis
{
     private int i = 0;
     private boolean error;
     LinkedList scopeList;
     private int C_SCOPE = 0;
     
     private final LinkedList names;
     private final LinkedList classes;
     private String AM;
     
     private String returnType;
     private final LinkedList argsList;
     
     private String methodReturn;
     private boolean pushback;
    
     private final LinkedList operand;
     private final LinkedList operandResults;
     
     private String result;
     
     private final LinkedList anzLL;
     private final LinkedList ID_;
     private final LinkedList MID_;
     
     private final LinkedList arraySizes;
     private final LinkedList arrayList;
     private int arraySize = 0;
     
     private final LinkedList objectList;
     
     private LinkedList methodArgsList;
     private LinkedList methodArgsCount;
     private int argsCount = 0;
     
     private int count = 0;
     
     public boolean finalresult;
     
     public SymenticAnalysis()
    {
         ID_ = new LinkedList();
         MID_ = new LinkedList();
         names = new LinkedList();
         anzLL = new LinkedList();
         classes = new LinkedList();
         operand = new LinkedList();
         argsList = new LinkedList();
         arrayList = new LinkedList();
          scopeList = new LinkedList();
         arraySizes = new LinkedList();
         objectList = new LinkedList();
         operandResults = new LinkedList();
         methodArgsList = new LinkedList();
         methodArgsCount = new LinkedList();
         
         
         scopeList.add(0);
       
         if(SymmenticTrue())
        {
             System.out.print(BLUE+"BUILD SUCCESSFUL "+GREEN+"\n");
             finalresult = true;
        }
         else
        {
              System.out.print(RED+"BUILD FAILED ");
              finalresult = false;
        }
    }
     private boolean SymmenticTrue()
    {
         if(tokens[i].CP.compareTo("A_M")==0)
        {
             AM = tokens[i].VP;
             i++;
             if(tokens[i].CP.compareTo("STABLE")==0 || tokens[i].CP.compareTo("COLLECTION")==0)
            {
                 if(tokens[i].CP.compareTo("STABLE")==0)
                {
                     i++;
                }
                 if(tokens[i].CP.compareTo("COLLECTION")==0)
                {
                     i++;
                     if(tokens[i].CP.compareTo("ID")==0)
                    {   
                         insert(tokens[i].VP,(int) scopeList.getLast(),"COLLECTION", AM,"MAINCLASS");
                         C_SCOPE++;
                         scopeList.add(C_SCOPE);
                         classes.add(tokens[i].VP);
                        
                         i++;
                         if(S0())return true;
                    }//error("ID");
                }  
            }//error("collection");
        }//error("access modifier");
         return false;
    }
     private boolean S0()
    {
        //System.out.println("cmnt-->S0 RECEIVE "+tokens[i].CP);
         if(tokens[i].CP.compareTo("A_M")==0)
        { 
             AM = tokens[i].VP;
             i++;
             //System.out.println("cmnt-->CALLING S1 FROM S0");
             if(S1())
             {
                 //System.out.println("cmnt-->S0 RETURN TRUE");
                 return true;
             }
        }//error("access modifier");
         //System.out.println("cmnt-->S0 RETURNS FALSE");
         return false;
    }
     private boolean S1()
    {
        //System.out.println("cmnt-->S1 RECEIVE "+tokens[i].CP);
         if(tokens[i].CP.compareTo("STABLE")==0)
        {
             i++;
             //System.out.println("cmnt-->CALLING S2 FROM S1");
             if(S2())
            {
                 //System.out.println("cmnt-->S1 RETURNS true");
                 return true;
            }
        }
         else if(tokens[i].CP.compareTo("TYP_SPS")==0 || tokens[i].CP.compareTo("METHOD")==0)
        {
             //System.out.println("cmnt-->CALLING SR FROM S1");
             if(SR())
            {
                 //System.out.println("cmnt-->S1 RETURNING TRUE");
                 
                 return true;
            }
        }
         //System.out.println("cmnt-->S1 RETURNING FALSE");
         return false;
    }
     private boolean S2()
    {
         //System.out.println("cmnt-->s2 RECEIVED "+tokens[i].CP);
         if(tokens[i].CP.compareTo("TYP_SPS")==0)
        {
             //System.out.println("cmnt-->S2 CALLING DECL");
             if(decl())
            {
                 //System.out.println("cmnt-->DECL PASSED S2 CALLING S0");
                 if(S0()) {
                     //System.out.println("cmnt-->S2 RETURNING TRUE");
                     return true;
                 }
            }
        }
         if(tokens[i].CP.compareTo("METHOD")==0)
        {
             C_SCOPE++;
             scopeList.add(C_SCOPE);
             i++;
             //System.out.println("cmnt-->S2 CALLING S3");
             if(S3()){
                 //System.out.println("cmnt-->S2 RETURNING TRUE");
                 return true;
             }
        }//error("method");
         //System.out.println("cmnt-->S2 RETURNING FALSE");
         return false;
    }
     private boolean S3()
    {
         //System.out.println("cmnt-->S3 RECEIVE "+tokens[i].CP);
         if(tokens[i].CP.compareTo("ID")==0)
        {
            names.add(tokens[i].VP);
             i++;
             //System.out.println("cmnt-->S3 CALLING S4");
             if(S4()) {
                 //System.out.println("cmnt-->S3 RETURNING TRUE");
                 return true;
             }
        }
         else if(tokens[i].CP.compareTo("STARTER")==0)
        {
             names.add("STARTER");
             //System.out.println("cmnt-->ITS STARTER");
             i++;
             //System.out.println("cmnt-->THE TOKEN IS "+tokens[i].CP);
             if(tokens[i].CP.compareTo("RETURNS")==0)
             {
                 //System.out.println("cmnt-->PASSED RETURNS");
                 i++;
                 //System.out.println("cmnt-->S3 CALLING type");
                 if(type())
                 {
                     //System.out.println("cmnt-->PASSED TYPE");
                     if(tokens[i].CP.compareTo("EXCEPTS")==0)
                     {
                         i++;
                         //System.out.println("cmnt-->S3 calling LIST with token "+tokens[i].CP);
                         
                         if(FDM_IL())
                        {
                             String[] args = new String[argsList.size()];
                             for (int j = 0; j < argsList.size(); j++)
                            {
                                 args[j] = (String) argsList.get(j);
                            }
                             String Name = (String) names.getLast(), class_Name = (String) classes.getLast();
                             //System.out.println("cmnt-->DOING LOOKUP");
                             if(method_lookUp(Name,(int)scopeList.get(scopeList.size()-2),class_Name,argsList)==null)
                            {
                                //System.out.println("cmnt-->LOOK UP PASSED");
                                insert(Name, (int)scopeList.get(scopeList.size()-2), class_Name, returnType, args);
                                argsList.clear();
                                names.clear();
                            }
                            else
                           {
                                //System.out.println("cmnt-->LOOKUP  NOT PASSED");
                                m_error((String)names.getLast());
                                return false;
                           }
                            
                            
                            
                             //System.out.println("cmnt-->PASSED LIST");
                             //System.out.println("cmnt-->S3 CALLING mst for token "+tokens[i].CP);
                             if(mst())
                            {
                                 //System.out.println("cmnt-->FROM PUSHBACK -----------------"+methodReturn);  
                                 if(methodReturn != null)
                                 if(methodReturn.compareTo("nothing")!=0)
                                 {
                                     System.out.println(PURPLE+"starter "+RED+"MUST HAVE NOTHING IN PUSHBACK.");
                                     return false;
                                 }
                                 if(tokens[i].CP.compareTo("DOT")==0)
                                {
                                     //System.out.println("cmnt-->PASSED DOT");
                                    
                                     scopeList.removeLast();
                                     //System.out.println("cmnt-->SCOPE IS "+scopeList.getLast());
                                    
                                     i++;
                                     if(args())
                                     {
                                         //System.out.println("cmnt-->PASSED ARGS");
                                         if(tokens[i].CP.compareTo("DOT")==0)
                                        {
                                             //System.out.println("cmnt-->RETURN TRUE");
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
         //System.out.println("cmnt-->S4 RECEIVE "+tokens[i].CP);
         //System.out.println("cmnt-->S4 CALLING method");
         if(method())
        {
             //System.out.println("cmnt-->method PASSED S4 CALLINf S0");
             if(S0()){
                 //System.out.println("cmnt-->S4 RETURNING TRUE");
                 return true;
             }
        }
         //System.out.println("cmnt-->S4 RETURNING FALSE");
         return false;
    }
     private boolean SR()
    {
         //System.out.println("cmnt-->SR RECEIVE "+tokens[i].CP);
         if(tokens[i].CP.compareTo("TYP_SPS")==0)
        {    
            //System.out.println("cmnt-->SR CALLING decl");
             if(decl())
            {
                 //System.out.println("cmnt-->DECL PASSED SR CALLING S0");
                 if(S0()) {
                     //System.out.println("cmnt-->SR RETURNING TRUE");
                     return true;
                 }
            }
        }
        else if(tokens[i].CP.compareTo("METHOD")==0)
        {
             //System.out.println("cmnt-->MATCH METHOD");
             i++;
             if(tokens[i].CP.compareTo("ID")==0)
            {
                 names.add(tokens[i].VP);
                 i++;
                 C_SCOPE++;
                 scopeList.add(C_SCOPE);
                 //System.out.println("cmnt-->SR CALLING S4");
                 if(S4()){
                     //System.out.println("cmnt-->SR RETURNING TRUE");
                     return true;
                 }
            }//error("ID");
        }//error("method or TYP_SPS");
         //System.out.println("cmnt-->SR RETURNING FALSE");
        return false;
     }
     private boolean F_D_M()
    {
         //System.out.println("cmnt-->F_D_M RECEIVE "+tokens[i].CP);
         if(tokens[i].CP.compareTo("RETURNS")==0)
        {
            
            String name = (String) names.getLast();
            //System.out.println("cmnt-->Name is "+name);
            String class_ = (String) classes.getLast();
            //System.out.println("cmnt-->Class_ is "+class_);
            if(name.compareTo(class_)==0)
            {
                 System.out.println(RED+"METHOD WITH SAME NAME AS OF CLASS IS PROHIBITED\n"+PURPLE+name+RED+" MUST BE OF SOME OTHER NAME IN LINE "+PURPLE+tokens[i].LineNumber+RED+" .");
                 return false;
            }
            
             //System.out.println("cmnt-->MATCHED RETURNS");
             i++;
             //System.out.println("cmnt-->F_D_M CALLING TYPE");
             if(type())
            {
                //System.out.println("cmnt-->PASSED type");
                 if(tokens[i].CP.compareTo("EXCEPTS")==0)
                {
                     i++;
                     //System.out.println("cmnt-->MATCHED EXCEPTS");
                     //System.out.println("cmnt-->F_D_M CALLING FDM_IL");
                     if (FDM_IL())
                    {
                        //System.out.println("cmnt-->PASSED FDM_IL");
                        //System.out.println("cmnt-->F_D_M CALLING mst");
                         int mScope = (int) scopeList.get(scopeList.size()-2);
                         String[] args  = new String[argsList.size()];
                         //System.out.println("cmnt-->argsList size is "+argsList.size());
                         for (int j = 0; j < argsList.size(); j++)
                        {
                            args[j] = (String) argsList.get(j);
                            //System.out.println("cmnt-->args = "+args[j]);
                        }
                         if(method_lookUp((String)names.getLast(),mScope,(String)classes.getLast(),argsList) == null)
                        {
                             insert((String)names.getLast(),mScope,(String)classes.getLast(),returnType,args);
                             //System.out.println("cmnt-->LOOKUP PASSED 111 ");
                        }
                         else
                        {
                             //System.out.println("cmnt-->LOOKUP  NOT PASSED");
                             m_error((String)names.getLast());
                             return false;
                        }
                         argsList.clear();
                         names.clear();
                         if(mst())
                        {
                             //System.out.println("cmnt-->HERE RETURN TYPE IS "+returnType);
                             if(returnType.compareTo("nothing")==0)
                            {
                                //System.out.println("cmnt-->IN NOTHING!!! and Method RETURN IS "+methodReturn);
                                 if(methodReturn != null)
                                {
                                     //System.out.println("cmnt-->I RUN");
                                     System.out.println(RED+"METHOD RETURN TYPE IS NOTHING REMOVE INVALID RETURN TYPE AT LINE "+PURPLE+tokens[i].LineNumber+RED+" .");
                                     return false;
                                }
                                 
                            }
                             else
                            {
                                 if(methodReturn == null)
                                {
                                     System.out.println(RED+"METHOD RETURN TYPE REQUIRED AT LINE "+PURPLE+tokens[i].LineNumber+RED+" .");
                                     return false;
                                }
                                 else
                                {
                                    // System.out.println("cmnt-->RT IS "+returnType +" and MR is "+methodReturn);
                                     if(returnType.compareTo("Sentence")==0 || returnType.compareTo("variable_Sentence")==0)
                                    {
                                         if(methodReturn.compareTo("variable_Sentence")!=0)
                                        {
                                                System.out.println(RED+"METHOD RETURN TYPE IS "+PURPLE+"Sentence"+RED+" REMOVE INVALID RETURN TYPE OF "+methodReturn+" AT LINE "+PURPLE+tokens[i].LineNumber+RED+" .");
                                                return false;
                                        }
                                    }
                                     else if(returnType.compareTo("int")==0 || returnType.compareTo("variable_int")==0)
                                    {
                                         if(methodReturn.compareTo("variable_int")!=0)
                                        {
                                                System.out.println(RED+"METHOD RETURN TYPE IS "+PURPLE+"int"+RED+" REMOVE INVALID RETURN TYPE OF "+PURPLE+methodReturn+RED+" AT LINE "+PURPLE+tokens[i].LineNumber+RED+" .");
                                                return false;
                                        }
                                    }
                                      else if(returnType.compareTo("float")==0 || returnType.compareTo("variable_float")==0)
                                    {
                                          if(methodReturn.compareTo("variable_float")!=0)
                                        {
                                                System.out.println(RED+"METHOD RETURN TYPE IS "+PURPLE+"float"+RED+" REMOVE INVALID RETURN TYPE OF "+methodReturn+" AT LINE "+PURPLE+tokens[i].LineNumber+RED+" .");
                                                return false;
                                        }
                                    }
                                     else if(returnType.compareTo("char")==0 || returnType.compareTo("variable_char")==0)
                                    {
                                         if(methodReturn.compareTo("variable_char")!=0)
                                        {
                                                System.out.println(RED+"METHOD RETURN TYPE IS "+PURPLE+"char"+RED+" REMOVE INVALID RETURN TYPE OF "+methodReturn+" AT LINE "+PURPLE+tokens[i].LineNumber+RED+" .");
                                                return false;
                                        }
                                    }
                                      else if(returnType.compareTo("boolean")==0 || returnType.compareTo("variable_boolean")==0)
                                    {
                                         if(methodReturn.compareTo("variable_boolean")!=0)
                                        {
                                                System.out.println(RED+"METHOD RETURN TYPE IS "+PURPLE+"boolean"+RED+" REMOVE INVALID RETURN TYPE OF "+methodReturn+" AT LINE "+PURPLE+tokens[i].LineNumber+RED+" .");
                                                return false;
                                        }
                                    }
                                     else if(returnType.compareTo("array_boolean")==0)
                                    {
                                         if(methodReturn.compareTo("array_boolean")!=0)
                                        {
                                                System.out.println(RED+"METHOD RETURN TYPE IS "+PURPLE+"array_boolean"+RED+" REMOVE INVALID RETURN TYPE OF "+methodReturn+" AT LINE "+PURPLE+tokens[i].LineNumber+RED+" .");
                                                return false;
                                        }
                                    }
                                     else if(returnType.compareTo("array_Sentence")==0)
                                    {
                                         if(methodReturn.compareTo("array_Sentence")!=0)
                                        {
                                                System.out.println(RED+"METHOD RETURN TYPE IS "+PURPLE+"array_Sentence"+RED+" REMOVE INVALID RETURN TYPE OF "+methodReturn+" AT LINE "+PURPLE+tokens[i].LineNumber+RED+" .");
                                                return false;
                                        }
                                    }
                                     else if(returnType.compareTo("array_int")==0)
                                    {
                                         if(methodReturn.compareTo("array_int")!=0)
                                        {
                                                System.out.println(RED+"METHOD RETURN TYPE IS "+PURPLE+"array_int"+RED+" REMOVE INVALID RETURN TYPE OF "+methodReturn+" AT LINE "+PURPLE+tokens[i].LineNumber+RED+" .");
                                                return false;
                                        }
                                    }
                                     else if(returnType.compareTo("array_float")==0)
                                    {
                                         if(methodReturn.compareTo("array_float")!=0)
                                        {
                                                System.out.println(RED+"METHOD RETURN TYPE IS "+PURPLE+"array_float"+RED+" REMOVE INVALID RETURN TYPE OF "+methodReturn+" AT LINE "+PURPLE+tokens[i].LineNumber+RED+" .");
                                                return false;
                                        }
                                    }
                                     else if(returnType.compareTo("array_char")==0)
                                    {
                                         if(methodReturn.compareTo("array_char")!=0)
                                        {
                                                System.out.println(RED+"METHOD RETURN TYPE IS "+PURPLE+"array_char"+RED+" REMOVE INVALID RETURN TYPE OF "+methodReturn+" AT LINE "+PURPLE+tokens[i].LineNumber+RED+" .");
                                                return false;
                                        }
                                    }
                                     else
                                    {
                                         String MR[] = methodReturn.split("_");
                                         if(MR[0].compareTo("object")!=0)
                                        {
                                             System.out.println(RED+"METHOD RETURN TYPE IS "+PURPLE+returnType+RED+" REMOVE INVALID RETURN TYPE OF "+PURPLE+methodReturn+RED+" AT LINE "+PURPLE+tokens[i].LineNumber+RED+" .");
                                             return false;
                                        }
                                         else if(MR[1].compareTo(returnType)!=0)
                                        {
                                             System.out.println(RED+"METHOD RETURN TYPE IS "+PURPLE+returnType+RED+" REMOVE INVALID RETURN TYPE OF "+methodReturn+" AT LINE "+PURPLE+tokens[i].LineNumber+RED+" .");
                                             return false;
                                        }
                                    }
                                }
                            }
                             returnType = null;
                             methodReturn = null;
                             if(tokens[i].CP.compareTo("DOT")==0)
                            {
                                 scopeList.removeLast();
                                 //System.out.println("cmnt-->SCOPE IS "+scopeList.getLast());
                                 i++;
                                 return true;
                            }//error("dot");
                        }
                    }
                }//error("accepts");
            }//error("return type");
        }//error("returns");
         //System.out.println("cmnt-->F_D_M REURNING FALSE");
         return false;
    }
     private boolean FDM_IL()
    {
         //System.out.println("cmnt-->FDM_IL RECEIVE "+tokens[i].CP);
        
         if(tokens[i].CP.compareTo("NOTHING")==0)
        {
             //System.out.println("cmnt-->PASSED NOTHING IN FDM_IL RETRN TRUE");
             argsList.add("NOTHING");
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
         
         //System.out.println("cmnt-->FDM_IL RETURN FALSE");
         return false;
    }
     private boolean FDM_IL_1()
     {
         //System.out.println("cmnt-->FDM_IL_1 RECEIVEED "+tokens[i].VP);
         
         String name ;
         String dataType;
         
          if(tokens[i].VP.compareTo("variable")==0)
        {
             i++;
             //System.out.println("cmnt-->ITS VARIABLE/ARRAY IN FDM_IL");
             if(tokens[i].CP.compareTo("ID")==0)
            {
                 name = tokens[i].VP;
                 //System.out.println("cmnt-->PASSED ID AFTER VARIABLE");
                 i++;
                  if(tokens[i].CP.compareTo("OF_TYPE")==0)
                {
                     //System.out.println("cmnt-->PASSED OF_TYPE");
                     i++;
                     if(tokens[i].CP.compareTo("DT")==0)
                    {
                         dataType = "variable_"+tokens[i].VP;
                        
                         if(lookUp(name, (int) scopeList.getLast(), (String) classes.getLast()) == null)
                        {
                             insert(name, (int) scopeList.getLast(),dataType,null,(String) classes.getLast());
                              argsList.add(dataType);
                        }
                         else
                         {
                             error(name);
                             return false;
                         }
                        
                         //System.out.println("cmnt-->PASSED DT AFTER OF_TYPE CALLING FDMIL_1");
                         i++;
                         //System.out.println("cmnt-->CALLIN M_IL");
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
             i++;
             //System.out.println("cmnt-->ITS ARRAY IN FDM_IL");
             if(tokens[i].CP.compareTo("ID")==0)
            {
                 //System.out.println("cmnt-->PASSED ID AFTER VARIABLE");
                 name = tokens[i].VP;
                 i++;
                 if(tokens[i].CP.compareTo("OF_TYPE")==0)
                {
                     //System.out.println("cmnt-->PASSED OF_TYPE");
                     i++;
                     if(tokens[i].CP.compareTo("ID")==0 || tokens[i].CP.compareTo("DT")==0)
                    {
                         //System.out.println("cmnt-->PASSED ID AFTER OF_TYPE");
                         dataType = "array_"+tokens[i].VP;
                         if(lookUp(name, (int) scopeList.getLast(), (String) classes.getLast()) == null)
                        {
                             insert(name, (int) scopeList.getLast(),dataType, null,(String) classes.getLast());
                             argsList.add(dataType);
                        }
                         else
                         {
                             error(name);
                             return false;
                         }
                         i++;
                         //System.out.println("cmnt-->CALLIN M_IL");
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
             i++;
             //System.out.println("cmnt-->ITS VARIABLE IN FDM_IL");
             if(tokens[i].CP.compareTo("ID")==0)
            {
                 //System.out.println("cmnt-->PASSED ID AFTER VARIABLE");
                 name = tokens[i].VP;
                 i++;
                  if(tokens[i].CP.compareTo("OF_TYPE")==0)
                {
                     //System.out.println("cmnt-->PASSED OF_TYPE");
                     i++;
                     if(tokens[i].CP.compareTo("ID")==0)
                    {
                         dataType = tokens[i].VP+tokens[i].VP;
                         //System.out.println("cmnt-->PASSED ID AFTER OF_TYPE CALLING FDMIL_1");
                         if(lookUp(name, (int) scopeList.getLast(), (String) classes.getLast()) == null)
                        {
                             insert(name, (int) scopeList.getLast(),dataType, null, (String) classes.getLast());
                              argsList.add(dataType);
                        }
                         else
                         {
                             error(name);
                             return false;
                         }
                         i++;
                         //System.out.println("cmnt-->CALLIN M_IL");
                         if(M_IL())
                         {
                             return true;
                         }
                   }//error("ID");
                }//error("oftype");
            }//error("ID");
        }
         //error("TYP_SPS");
         
         //System.out.println("cmnt-->FDM_IL_1 RETURN FALSE");
         return false;
     }
     private boolean M_IL()
     {
         //System.out.println("cmnt-->M_IL REC "+tokens[i].CP);
         if(tokens[i].CP.compareTo("AND")==0)
        {
             i++;
             //System.out.println("cmnt-->CALLING FDM_IL_1 FROM M_IL AFTER READIN GAND");
             if(FDM_IL_1())
            {
                 return true;
            }
             
        }
         else if(first_mst())
        {
             return true;
        }
         
         //System.out.println("cmnt-->M_IL RETURN FALSE");
         return false;
     }
     private boolean type()
    {
         //System.out.println("cmnt-->TYPE RECEIVE "+tokens[i].CP);
         if(tokens[i].CP.compareTo("NOTHING")==0 || tokens[i].CP.compareTo("ID")==0 || tokens[i].CP.compareTo("DT")==0)
        {
             //if(tokens[i].CP.compareTo("ID")==0)
             //{
               //  String r = lookUp(tokens[i].VP,scopeList);
                // if(r==null)
                 //{
                    // u_error(tokens[i].VP);
                  ///   return false;
                 //}
             //}
             returnType = tokens[i].VP;
             i++;
             //System.out.println("cmnt-->TYPE RETURN TRUE with token "+tokens[i].CP);
             return true;
        }error("nothing or ID or DataType");
         //System.out.println("cmnt-->TYPE RETURN FALSE");
         return false;
    }
     private boolean mst()
    {
         //System.out.println("cmnt-->IN MST WITH "+tokens[i].CP);
         if(tokens[i].CP.compareTo("DOT")==0)
        {
             //System.out.println("cmnt-->RETURNING TRUE FROM MST");
             return true;
        }
         else if(tokens[i].CP.compareTo("TYP_SPS")==0)
        {
             //System.out.println("cmnt-->CALLING decl from MST");
             if(decl())
            {
                 //System.out.println("cmnt-->ONE STATEMENT PASSED CALLING AGAIN MST");
                 if(mst())
                {
                    return true;
                }
            }
             
        }
         else if(tokens[i].CP.compareTo("IS")==0)
        {
             i++;
             //System.out.println("cmnt-->CALLING E FROM MST AFTER READING IS");
             if(E())
            {
                 String r = (String) operand.removeLast();
                 if(r.compareTo("variable_boolean")!=0)
                {
                     d_error(r,"variable_boolean");
                     return false;
                }
                 if(mst())
                {
                     //System.out.println("cmnt-->MST PASSED ");
                     if(tokens[i].CP.compareTo("DOT")==0)
                    {
                         i++;
                         //System.out.println("cmnt-->CALLING ELSE");
                         if(else_())
                         {
                             //System.out.println("cmnt-->RETURNED FROM ELSE_");
                             //System.out.println("cmnt-->IS CALLING MST");
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
             C_SCOPE++;
             scopeList.add(C_SCOPE);
             i++;
             //System.out.println("cmnt-->CALLING E FROM ANLZ");
             if(E())
            {
                 //System.out.println("E RETURNED "+operand.getLast());
                 String p = (String) operand.getLast();
                 String pp[] = p.split("_");
                 if(pp[0].compareTo("variable")!=0)
                {
                     System.out.println(RED+"SWITCH CASE WORKS ONLY ON VARIABLES REMOVE INVALID EXPREESION AT LINE "+PURPLE+tokens[i].LineNumber+RED+" .");
                     return false;
                }
                 if(!(pp[1].compareTo("int")==0 || pp[1].compareTo("char")==0 || pp[1].compareTo("Sentence")==0 || pp[1].compareTo("boolean")==0))
                {
                     System.out.println(RED+"SWITCH CASE WORKS ONLY ON CONSTANTS REMOVE INVALID VALUE AT LINE "+PURPLE+tokens[i].LineNumber+RED+" .");
                     return false;
                }
                 anzLL.add((String) operand.removeLast());
                 //System.out.println("cmnt-->E PASSED CALLIND S_BODY");
                 if(s_body())
                {
                     //System.out.println("cmnt-->CHECKING DOT OF ANLZ");
                     if(tokens[i].CP.compareTo("DOT")==0)
                    {
                         scopeList.removeLast();
                         anzLL.removeLast();
                         i++;
                         //System.out.println("cmnt-->ANLZ PASSED CHECKING OTHER LINE");
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
             C_SCOPE++;
             scopeList.add(C_SCOPE);
             i++;
             //System.out.println("cmnt-->CALLING E FROM REP_WHL");
             if(E())
            {
                 String result = (String) operand.removeLast();
                 if(result.compareTo("variable_boolean")!=0)
                {
                     d_error(result,"variable_boolean");
                }
                 //System.out.println("cmnt-->E PASSED CALLING MST");
                 if(mst())
                 {
                     //System.out.println("cmnt-->CALLING MST");
                     if(tokens[i].CP.compareTo("DOT")==0)
                    {    
                         scopeList.removeLast();
                         i++;
                         //System.out.println("cmnt-->RPT_WHL PASSED CHECKING OTHER LINES");
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
             C_SCOPE++;
             scopeList.add(C_SCOPE);
             i++;
             //System.out.println("cmnt-->CALLING MST FROM RPT");
             if(mst())
            {
                 if(tokens[i].CP.compareTo("DOT")==0)
                {
                     i++;
                     if(tokens[i].CP.compareTo("RPT_WHL")==0)
                    {
                         i++;
                         //System.out.println("cmnt-->CALLING E FROM RPT_WHL");
                         if(E())
                        {
                             String result = (String) operand.removeLast();
                             if(result.compareTo("variable_boolean")!=0)
                            {
                                 d_error(result,"variable_boolean");
                            }
                             if(tokens[i].CP.compareTo("DOT")==0)
                            {
                                 scopeList.removeLast();
                                 i++;
                                 //System.out.println("cmnt-->RPT PASSED");
                                 //System.out.println("cmnt-->CALLIMG MST");
                                 if(mst())
                                {
                                     //System.out.println("cmnt-->RETURNING TRUE");
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
             C_SCOPE++;
             scopeList.add(C_SCOPE);
             //System.out.println("cmnt-->IN RPT_IT");
             i++;
             //System.out.println("cmnt-->CALLING R_FOR");
             if(r_for())
            {
                 scopeList.removeLast();
                 if(tokens[i].CP.compareTo("TYP_SPS")==0 || tokens[i].CP.compareTo("IS")==0 || tokens[i].CP.compareTo("ANLZ")==0  || tokens[i].CP.compareTo("RPT_WHL")==0  || tokens[i].CP.compareTo("RPT")==0  || tokens[i].CP.compareTo("RPT_IT")==0  || tokens[i].CP.compareTo("RETURN")==0 || tokens[i].CP.compareTo("DOT")==0)
                {
                    //System.out.println("cmnt-->CALLIN MST AFTER RPT_IT");
                    if(mst())
                    {
                         //System.out.println("cmnt-->RETURN TRUE FROM RPT_IT");
                         return true;
                    }//error("dot");
                }//error("dot");
            }
        }
         else if(tokens[i].CP.compareTo("EXIT")==0)
        {
             i++;
             if(tokens[i].CP.compareTo("DOT")==0)
             {
                 i++;
                 return true;
             }error("dot");
         }
         else if(tokens[i].CP.compareTo("RETURN")==0)
        {    pushback = true;
             i++;
             //System.out.println("cmnt-->IN RETURN ");
             //System.out.println("cmnt-->CALLING RT");
             if(RT())
            {
                 //System.out.println("cmnt-->RT RETURNED --> "+methodReturn);
                 //System.out.println("cmnt-->CALLING MST");
                     return true;
                 //error("dot");
            }//error("nothing or return type");
        }
         else if(tokens[i].CP.compareTo("ID")==0)
        {
             if(lookUp(tokens[i].VP,scopeList)==null)
            {
                 u_error(tokens[i].VP);
                 return false;
            }
             MID_.add(tokens[i].VP);
             
             i++;
             
             //System.out.println("cmnt-->CALLING M_ID");
             if(M_ID())
             {
                  if(mst())
                {
                     return true;
                }
             }
         }
         else if(tokens[i].CP.compareTo("INCDEC")==0)
        {
             String oop = tokens[i].VP;
             i++;
             if(tokens[i].CP.compareTo("ID")==0)
            {
                 String dt = lookUp(tokens[i].VP,scopeList);
                 i++;
                 //System.out.println("cmnt-->dt is "+dt);
                 if(dt==null)
                {
                     u_error(tokens[i].VP);
                     return false;
                }
                 String result = checkCom(dt,oop);
                 if(result==null)
                 {
                     c_error((String)ID_.removeLast(),oop);
                     return false;
                 }
                 
                 if(mst())
                 {
                     return true;
                 }
             }
        }
         //error("dot");
         //System.out.println("cmnt-->RETURNING FALSE FROM MST");
         return false;
    }
     private boolean M_ID()
    {
         //System.out.println("cmnt-->M_ID RECEIVE "+tokens[i].CP);
         if(tokens[i].CP.compareTo("EQUALS")==0)
        {
             i++;
             //System.out.println("cmnt-->EQUALS AFTER ID");
             //System.out.println("cmnt-->CALLING E");
             if(E())
            {
                 String result = (String) operand.removeLast();
                 String oper = (String) MID_.removeLast();
                 String type = lookUp(oper,scopeList);
                 if(type.compareTo(result)!=0)
                {
                     d_error(oper,result);
                }
                 //System.out.println("cmnt-->E RETURNED SO RETURNING TRUE");
                 return true;
            }//error("Expression");
        }
         if(tokens[i].CP.compareTo("POS")==0)
        {
             String oper = (String) MID_.removeLast();
             String result = lookUp(oper,scopeList);
             //System.out.println("cmnt-->result is "+result);
             String rparts[] = result.split("_");
             //System.out.println(rparts[0]);
             
             boolean array = false;
             boolean object = false;
             
             if(rparts[0].compareTo("array")==0)
            {
                array = true;
            }else if(rparts[0].compareTo("object")==0)
            {
                object = true;
            }
          
             if(!array && !object)
            {
                 d_error(oper,"Array or Object");
                 return false;
            }
             //System.out.println("cmnt-->ITS ARRAY OR OBJECT");
             i++;
             //System.out.println("cmnt-->GOT AFTER POS "+tokens[i].CP);
             
             if(tokens[i].CP.compareTo("INT_CNST")==0 || tokens[i].CP.compareTo("ID")==0)
            {
                 boolean intConst = false;
                 if(array)
                {
                     //System.out.println("cmnt-->ITS ARRAY");
                     if(tokens[i].CP.compareTo("INT_CNST")==0)
                    {
                         intConst = true;
                    }
                     else if(tokens[i].CP.compareTo("ID")==0)
                    {
                         String type = lookUp(tokens[i].VP,scopeList);
                         if(type == null)
                         {
                             u_error(tokens[i].VP);
                             return false;
                         }
                         else
                         {
                             if(type.compareTo("variable_int")==0)
                             {
                                 intConst = true;
                             }
                         }
                    }
                     if(!intConst)
                     {
                         d_error(tokens[i].VP,"int");
                         
                         return false;
                     }
                }
                 else if(object)
                {
                     //System.out.println("cmnt-->ITS OBJECT");
                     if(tokens[i].CP.compareTo("INT_CNST")==0)
                    {
                        d_error(tokens[i].VP,RED+"ANY FIELD OF OBJECT "+PURPLE+oper);
                        return false;
                    }
                     else
                    {
                         result = lookUp(tokens[i].VP,rparts[1]);
                         if(result == null)
                         {
                             u_c_error(tokens[i].VP,rparts[1]);
                         }
                         //System.out.println("cmnt-->result is "+result);
                    }
                }
                 
                i++;
                //System.out.println("cmnt-->HERE WITH "+tokens[i].VP);
                
                
                 if(tokens[i].CP.compareTo("EQUALS")==0)
                {
                    i++;
                    //System.out.println("cmnt-->CALLING E");
                    if(E())
                    {
                         //System.out.println("cmnt-->E RETURNED "+operand.getLast());
                         if(array)
                        {
                             String EResult = (String) operand.removeLast();
                             if(EResult.compareTo("variable_"+rparts[1])!=0)
                            {
                                 d_error(EResult,rparts[1]);
                                 return false;
                            }
                            
                        }
                         if(object)
                        {
                             String EResult = (String) operand.removeLast();
                             if(EResult.compareTo(result)!=0)
                            {
                                 d_error(EResult,result);
                                 return false;
                            }
                        }
                         
                        //System.out.println("cmnt-->E PASSED RETURNING TRUE");
                        return true;
                    }//error("Expression");
                }//error("equals");
            
            }//error("int_const or ID");
             
        }
         if(tokens[i].CP.compareTo("INCDEC")==0)
        {
             String oper = (String) MID_.removeLast();
             //System.out.println("cmnt-->oper is "+oper);
             String dt = lookUp(oper,scopeList);
             String result = checkCom(dt,tokens[i].VP);
             if(result == null)
            {
                 d_error(oper,tokens[i].VP);
                 return false;
            }
             
             
             
             //System.out.println("cmnt-->IM MID WITH INCDEC AFTER ID");
             i++;
             return true;
        } //error("equals or ' or incdec");
         //System.out.println("cmnt-->M_ID RETURN FALSE");
         return false;
     }
     private boolean RT()
    {
         //System.out.println("cmnt-->RT RECEIVE "+tokens[i].CP);
         if(E())
        {
             methodReturn = (String) operand.removeLast();
 
             if(tokens[i].CP.compareTo("DOT")==0)
            {
                 i++;
                 return true;
            }//error("dot");
        }
        
         else if(tokens[i].CP.compareTo("DOT")==0)
        {
                 i++;
                 return true;
        }
         //error("ID or Const or dot");
         //System.out.println("cmnt-->RT RETURNING FALSE");
         return false;
     }
     private boolean const_()
    {
         if(tokens[i].CP.compareTo("INT_CNST")==0 || tokens[i].CP.compareTo("BOOL_CNST")==0 || tokens[i].CP.compareTo("FLOAT_CNST")==0 || tokens[i].CP.compareTo("STRN_CNST")==0|| tokens[i].CP.compareTo("CHAR_CNST")==0)
        {
             if(tokens[i].CP.compareTo("INT_CNST")==0)
            {
                 operand.add("variable_int");
                 ID_.add(tokens[i].VP);
            }
             else if(tokens[i].CP.compareTo("BOOL_CNST")==0)
            {
                 operand.add("variable_boolean");
                 ID_.add(tokens[i].VP);
            }
             else if(tokens[i].CP.compareTo("FLOAT_CNST")==0)
            {
                 operand.add("variable_float");
                 ID_.add(tokens[i].VP);
            }
             else if(tokens[i].CP.compareTo("STRN_CNST")==0)
            {
                 operand.add("variable_Sentence");
                 ID_.add(tokens[i].VP);
            }
             else if(tokens[i].CP.compareTo("CHAR_CNST")==0)
            {
                 operand.add("variable_char");
                 ID_.add(tokens[i].VP);
            }
             
             i++;
             //System.out.println("cmnt-->CONST RETURN TRUE");
             return true;
        }//error("const");
         //System.out.println("cmnt-->RETURN FALSE FROM CONST");
         return false;
    }  
     private boolean r_for()
    {
         //System.out.println("cmnt-->IN R_FOR With "+tokens[i].CP);
         if(E())
        {
            String dt = (String) operand.removeLast();
            if(dt.compareTo("variable_int")!=0)
            {
                c_error(dt,"For-Loop");
            }
            /* if(tokens[i].CP.compareTo("ID")==0)
            {
                 String result = lookUp(tokens[i].VP,scopeList);
                 if(result==null)
                {
                     u_error(tokens[i].VP);
                     return false;
                }
                 if(result.compareTo("variable_int")!=0)
                {
                     c_error(tokens[i].VP,"int");
                }
            }*/
            // i++;
             if(tokens[i].CP.compareTo("TIMES")==0)
            {
                 i++;
                 if(mst())
                 {
                     if(tokens[i].CP.compareTo("DOT")==0)
                     {
                         i++;
                         //System.out.println("cmnt-->RETURNING TRUE FROM R_FOR");
                         return true;
                     }//error("dot");
                 }//error("dot");
             } //error("times");
        }
         else if(tokens[i].CP.compareTo("FOREACH")==0)
        {
             //System.out.println("cmnt-->PASSED FOREACH");
             i++;
             //System.out.println("cmnt-->CALLING F_R");
             if(f_r())
             {
                 //System.out.println("cmnt-->RETURNIN TRUE FROM R_For");
                 //System.out.println("cmnt-->TOKEN IS "+tokens[i].CP);
                 return true;
             }
        }
         //error("int_const or foreach");
         
         //System.out.println("cmnt-->R_For return false");
         return false;
     }
     private boolean f_r()
    {
         
         //System.out.println("cmnt-->FR RECEIVE "+tokens[i].CP);
         if(tokens[i].VP.compareTo("variable")==0)
        {
             i++;
             //System.out.println("cmnt-->VARIABLE PASSED CHECKING ID");
             if(tokens[i].CP.compareTo("ID")==0)
            {
                 String name = tokens[i].VP;
                 //System.out.println("cmnt-->ID PASSED ");
                 i++;
                 if(tokens[i].CP.compareTo("OF_TYPE")==0)
                 {
                     i++;
                     //System.out.println("cmnt-->OFTYPE PASSED");
                     if(tokens[i].CP.compareTo("DT")==0)
                     {
                         String dt = tokens[i].VP;
                         insert(name,C_SCOPE,"variable_"+tokens[i].VP,null,(String)classes.getLast());
                         i++;
                         //System.out.println("cmnt-->DT PASSED ");
                         if(tokens[i].CP.compareTo("BLNG_TO")==0)
                        {
                             //System.out.println("cmnt-->BELONGS TO PASSED");
                             i++;
                             if(tokens[i].CP.compareTo("ID")==0)
                            {
                                 String result = lookUp(tokens[i].VP,scopeList);
                                 if(result == null)
                                {
                                     u_error(tokens[i].VP);
                                     return false;
                                }
                                 String typeA[] = result.split("_");
                                 //System.out.println("cmnt-->TYPE IS  "+typeA[0]);
                                 if(typeA[0].compareTo("array")!=0)
                                 {
                                     d_error(tokens[i].VP,"ARRAY OF TYPE "+dt);
                                 }
                                 i++;
                                 //System.out.println("cmnt-->CALLING MST");
                                 if(mst())
                                 {
                                     if(tokens[i].CP.compareTo("DOT")==0)
                                     {
                                         i++;
                                         //System.out.println("cmnt-->RETURNING TRUE FROM F_R");
                                         return true;
                                     }//error("dot");
                                 }//error("dot");
                             }//error("ID");
                        }//error("belongto");
                     }//error("datatype");
                 }//error("oftype");
             }//error("id");
        }//error("variable");
         
         //System.out.println("cmnt-->F_R RETURNING FALSE");
         return false;
     }
     private boolean s_body()
    {
         //System.out.println("cmnt-->S_BoDY REC "+tokens[i].CP);
         
         if(tokens[i].CP.compareTo("MATCHES")==0)
        {
             //System.out.println("cmnt-->CALLING CASES FROM S_BODY");
             if(cases())
             {
                 //System.out.println("cmnt-->S_BODY RETURN TRUE");
                 return true;
             }
         }
         else if(tokens[i].CP.compareTo("ALTERNATIVE")==0)
         {
             //System.out.println("cmnt-->CALLING DEFAULT FROM S_BODY");
             if(default_())
             {
                 //System.out.println("cmnt-->RETURNING TRUE FROM S_BODY");
                 return true;
             }
         }
         else if(tokens[i].CP.compareTo("DOT")==0)
        {
             //System.out.println("cmnt-->RETURNING TRUE FOR DOT");
             return true;
        }//error("matches or alternative or dot");
         
         
         //System.out.println("cmnt-->S_BODY RETURN FALSE");
         return false;
    }
     private boolean cases()
    {
         //System.out.println("cmnt-->IN CASSES WITH "+tokens[i].CP);
         if(tokens[i].CP.compareTo("MATCHES")==0)
        {
             C_SCOPE++;
             scopeList.add(C_SCOPE);
             i++;
             //System.out.println("cmnt-->CALLING S_CNST FOR MATCHES ");
             if(S_const())
             {
                 if(mst())
                 {
                     if(tokens[i].CP.compareTo("DOT")==0)
                     {
                         scopeList.removeLast();
                         i++;
                         //System.out.println("cmnt-->CALLING S_BODY FROM CASES");
                         if(s_body())
                         {
                             //System.out.println("cmnt-->S_BODY IS TRUE RETURN TUE");
                             return true;
                         }
                     }//error("dot");
                 }
             }
        }//error("matches");
         //System.out.println("cmnt-->CASSES RETURN FALSE");
         return false;
     }
     private boolean S_const()
     {
         //System.out.println("cmnt-->S_CONST RECEIVE "+tokens[i].CP);
         
         if(tokens[i].CP.compareTo("INT_CNST")==0 || tokens[i].CP.compareTo("CHAR_CNST")==0 ||tokens[i].CP.compareTo("STRN_CNST")==0 ||tokens[i].CP.compareTo("BOOL_CNST")==0)
        {
             if(tokens[i].CP.compareTo("INT_CNST")==0)
            {
                 result = (String) anzLL.getLast();
                 if(result.compareTo("variable_int")!=0)
                {
                     d_error(tokens[i].VP,result);
                }
            }
             if(tokens[i].CP.compareTo("CHAR_CNST")==0)
            {
                 result = (String) anzLL.getLast();
                 if(result.compareTo("variable_char")!=0)
                {
                     d_error(tokens[i].VP,result);
                }
            }
             if(tokens[i].CP.compareTo("BOOL_CNST")==0)
            {
                 result = (String) anzLL.getLast();
                 if(result.compareTo("variable_boolean")!=0)
                {
                     d_error(tokens[i].VP,result);
                }
            }
              if(tokens[i].CP.compareTo("STRN_CNST")==0)
            {
                 result = (String) anzLL.getLast();
                 if(result.compareTo("variable_Sentence")!=0)
                {
                     result = (String) anzLL.getLast();
                     d_error(tokens[i].VP,result);
                }
            }
            
             i++;
             //System.out.println("cmnt-->S_CNST RETURN TRUE");
             return true;
         }//error("const");
         //System.out.println("cmnt-->S_CONST RETURN FALSE");
         return false;
     }
     private boolean default_()
    {
         //System.out.println("cmnt-->DEFAULT_ REC "+tokens[i].CP);
         if(tokens[i].CP.compareTo("ALTERNATIVE")==0)
         {
             C_SCOPE++;
             scopeList.add(C_SCOPE);
             i++;
             if(mst())
             {
                 if(tokens[i].CP.compareTo("DOT")==0)
                 {
                     scopeList.removeLast();
                     i++;
                     //System.out.println("cmnt-->RETURNING TRUE FROM DEFAULT_");
                     return true;
                 }//error("dot");
             }
         }//error("default");
         //System.out.println("cmnt-->RETURN FALSE FROM DEFAULT_");
         return false;
     }
     private boolean else_()
    {
         //System.out.println("cmnt-->ELSE RECEIVED "+tokens[i].CP);
         
         if(tokens[i].CP.compareTo("NOT_SO")==0)
        {
            i++;
             //System.out.println("cmnt-->CALLING ELSE 1");
             if(else_1())
             {
                 //System.out.println("cmnt-->RETURNIG TRUE FROM IFELSE");
                 return true;
             }
        }
         else if(first_mst())
         {   
             //System.out.println("cmnt-->RETURNING TRUE FROM ELSE_");
             return true;
         }//error("notso or dot");
         
         
         //System.out.println("cmnt-->else_ returning false ");
         return false;
     }
     private boolean else_1()
    {
         //System.out.println("cmnt-->ELSE 1 REC "+tokens[i].CP);
         
         if(tokens[i].CP.compareTo("IS")==0)
         {
             i++;
             //System.out.println("cmnt-->CALLING e FROM ELSE_1");
             if(E())
             {
                     String r = (String) operand.removeLast();
                     if(r.compareTo("variable_boolean")!=0)
                    {
                         d_error(r,"variable_boolean");
                         return false;
                    }
                 //System.out.println("cmnt-->CALLING MST FROM ELSE1");
                 if(mst())
                 {
                     //System.out.println("cmnt-->CHECKING DOT");
                     if(tokens[i].CP.compareTo("DOT")==0)
                     {
                         i++;
                         //System.out.println("cmnt-->CALLING ELSE_ FROM ELSE_!");
                         if(else_())
                         {
                             return true;
                         }
                         
                     }//error("dot");
                 }//error("dot");
             }//error("expression");
             
         }
         else if(first_mst())
         {
             //System.out.println("cmnt-->CALLING MST FROM ELSE_1");
             if(mst())
             {
                 if(tokens[i].CP.compareTo("DOT")==0)
                 {
                     i++;
                     return true;
                 }
             }
         }//error("is ot dot");
         
         //System.out.println("cmnt-->else_1 return false");
         return false;
     }
     private boolean args()
    {
         //System.out.println("cmnt-->ARGS RECEIVED "+tokens[i].CP);
         if(tokens[i].CP.compareTo("DOT")==0)
        {
             //System.out.println("cmnt-->ARGS RETURNING TRUE");
             return true;
        }
         else if(tokens[i].CP.compareTo("A_M")==0)
        {
             AM = tokens[i].VP;
             //System.out.println("cmnt-->PASSED AM IN ARGS");
             i++;
             if(tokens[i].CP.compareTo("STABLE")==0)
            {
                 i++;
            }
             //System.out.println("cmnt-->CALLING D_M_C FROM ARGS");
             if(D_M_C())
            {
                 //System.out.println("cmnt-->D_M_C PASSED IN ARGS CALLING OTHER ARGS");
                 if(args())
                 {
                     //System.out.println("cmnt-->RETURNING TRUE");
                     return true;
                 }
                 
            }
        }
         //System.out.println("cmnt-->ARGS RETURNING FALSE");
         return false;
    } 
     private boolean class_()
    {
         //System.out.println("cmnt-->IN CLASS WITH "+tokens[i].CP);
         if(tokens[i].CP.compareTo("COLLECTION")==0)
        {
             i++;
             //System.out.println("cmnt-->CHECKED COLLECTION CHECKING ID");
             if(tokens[i].CP.compareTo("ID")==0)
            {
                
                 if(lookUp(tokens[i].VP, (int) scopeList.getLast(), (String) classes.getLast())!=null)
                {
                     error(tokens[i].VP);
                     return false;
                }
                
                 
                 for (int j = 0; j < classes.size(); j++) {
                   if(tokens[i].VP.compareTo((String) classes.get(j))==0){
                       System.out.println(RED+"CLASS "+PURPLE+tokens[i].VP+RED+" AT LINE "+PURPLE+tokens[i].LineNumber+RED+" IS ALREADY DEFINED.");
                       return false;
                   }
                }

                 insert(tokens[i].VP, (int) scopeList.getLast(),"COLLECTION", AM,(String) classes.getLast());
                 classes.add(tokens[i].VP);
                 C_SCOPE++;
                 scopeList.add(C_SCOPE);
                 i++;
                 //System.out.println("cmnt-->PASSED ID ");
                 //System.out.println("cmnt-->CALLING CLASS CONTECTS FROM CLASSS ");
                 
                 if(tokens[i].CP.compareTo("A_CHILD_OF")==0)
                {
                     i++;
                     //System.out.println("cmnt-->INHERITANCE PASSSED");
                     if(tokens[i].CP.compareTo("ID")==0)
                    {
                         //System.out.println("cmnt-->CHECKING ID OF PARENT");
                         i++;
                    }
                }
                 if(class_contents())
                {
                     //System.out.println("cmnt-->PASSED CLASS CONTENTS IN CLASS_");
                     //System.out.println("cmnt-->CLASS CHECKING DOT");
                     if(tokens[i].CP.compareTo("DOT")==0)
                    {
                         scopeList.removeLast();
                         classes.removeLast();
                         i++;
                         //System.out.println("cmnt-->CLASS RETURNING TRUE");
                         return true;
                    }//error("dot");
                }
            }//error("ID");
        }//error("collection");
         //System.out.println("cmnt-->CLASS RETURNING FALSE");
         return false;
    }
     private boolean class_contents()
    {
         //System.out.println("cmnt-->CLASS_CONTENTS RECEIVED "+ tokens[i].CP);
         if(tokens[i].CP.compareTo("A_M")==0)
        {
             AM = tokens[i].VP;
             i++;
             if(tokens[i].CP.compareTo("STABLE")==0)
             {
                 i++;
             }
             //System.out.println("cmnt-->CALLING C_C FROM CLASS_CONTENTS");
             if(C_C())
             {
                 //System.out.println("cmnt-->RETURNING TRUE FROM CLASS_CONTENTS");
                 return true;
             }
        }
         else if(tokens[i].CP.compareTo("DOT")==0)
        {
             //System.out.println("cmnt-->RETURNING TRUE FROM CLASS CONTENTS ");
             return true;
        }//error("AM or dot");
         
         //System.out.println("cmnt-->RETURN FALSE FROM CLASS_CONTENT");
         return false;
    }
     private boolean C_C()   
    {
         //System.out.println("cmnt-->C_C RECEIVE "+tokens[i].CP);
         if(tokens[i].CP.compareTo("TYP_SPS")==0)
        {
             //System.out.println("cmnt-->CALLING DECL FROM C_C");
             if(decl())
            {
                 //System.out.println("cmnt-->CALLING MORE FROM C_C");
                 if(more())
                {
                     //System.out.println("cmnt-->RETURNING TRUE FROM C_C");
                     return true;
                }
            }
        }
         else if(tokens[i].CP.compareTo("METHOD")==0)
        { 
             C_SCOPE++;
             scopeList.add(C_SCOPE);
             //System.out.println("cmnt-->ITS METHOD IN C_C");
             i++;
             if(tokens[i].CP.compareTo("ID")==0)
            {
                 names.add(tokens[i].VP);
                 i++;
                 //System.out.println("cmnt-->CALLING METHOD FROM C_C");
                 if(method())
                 {
                     //System.out.println("cmnt-->METHOD PASSED");
                     //System.out.println("cmnt-->CALLING MORE FROM C_C");
                    if(more())
                    {
                         //System.out.println("cmnt-->RETURNING TRUE FROM C_C");
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
         //System.out.println("cmnt-->RETURNING FALSE FROM C_C");
         return false;
     }
     private boolean method()
    {
         //System.out.println("cmnt-->METHOD RECEIVE "+tokens[i].CP);
         if(tokens[i].CP.compareTo("EXCEPTS")==0)
        {
             
             String Name = (String) names.getLast(), class_Name = (String) classes.getLast();
             if(Name.compareTo(class_Name)!=0)
            {
                 System.out.println(RED+"CONSTRUCTER MUST BE THE SAME NAME AS OF CLASS IN LINE "+PURPLE+tokens[i].VP+RED+" .");
                 return false;
            }
            
             i++;
           // System.out.println("CALLING FDM_IL FROM MRTHOD");
             if(FDM_IL())
            {
                 pushback = false;
                 //System.out.println("cmnt-->IL PASSED CALLING MST");

                 String[] args = new String[argsList.size()];
                 for (int j = 0; j < argsList.size(); j++) {
                     args[j] = (String) argsList.get(j);
                }
  
                 //System.out.println("cmnt-->DOING LOOKUP");
                 if(method_lookUp(Name,(int)scopeList.get(scopeList.size()-2),class_Name,argsList)==null)
                {
                     //System.out.println("cmnt-->LOOK UP PASSED for scope " +(scopeList.size()-2));
                     insert(Name, (int)scopeList.get(scopeList.size()-2), class_Name, class_Name, args);
                     argsList.clear();
                     names.clear();
                }
                 else
                {
                     //System.out.println("cmnt-->LOOKUP  NOT PASSED");
                     m_error((String)names.getLast());
                     return false;
                }
                
                
                 if(mst())
                {
                     //System.out.println("cmnt-->MST PASSED ");
                     if(tokens[i].CP.compareTo("DOT")==0)
                    {
                         scopeList.removeLast();
                         //System.out.println("cmnt-->SCOPE IS "+scopeList.getLast());
                         //System.out.println("cmnt-->I AM HERE");
                         if(pushback)
                        {
                             System.out.println(RED+"THERE MUST BE NO RETURN STATEMENT IN CONSTRUCTOR AT LINE "+PURPLE+tokens[--i].LineNumber+" .");
                             return false;
                        }
                         i++;
                         //System.out.println("cmnt-->RETURNING TRUE FROM MOETHOD");
                         return true;
                    }
                }
            }
        }
         else if(F_D_M())
        {
             //System.out.println("cmnt-->CALLED F_D_M FROM METHOD IT PASSED RETURNING TRUE");
             return true;
        }
         //System.out.println("cmnt-->RETURNING FALSE FROM METHOD");
         return false;
    }
     private boolean more()
    {
         //System.out.println("cmnt-->in more with "+tokens[i].CP);
         if(tokens[i].CP.compareTo("A_M")==0)
        {
             AM = tokens[i].VP;
             i++;
             //System.out.println("cmnt-->CALLING C_C FROM MORE");
             if(C_C())
            {
                 //System.out.println("cmnt-->RETURNING TRUE FROM MORE");
                 return true;
            }
        }
         else if(tokens[i].CP.compareTo("DOT")==0)
        {
             //System.out.println("cmnt-->RETURNING TRUE FROM MORE ");
             return true;
        }//error("AM or dot");
         //System.out.println("cmnt-->RETURNING FALSE FROM MORE");
         return false;
    }
     private boolean D_M_C()
    {
         //System.out.println("cmnt-->D_M_C RECEIVE "+tokens[i].CP);
         if(tokens[i].CP.compareTo("TYP_SPS")==0)
        {
             //System.out.println("cmnt-->CALLING DECL FROM D_M_C");
             if(decl())
            {
                 //System.out.println("cmnt-->RETURNING TRUE FROM D_M_C");
                 return true;
            }
        }
         else if(tokens[i].CP.compareTo("METHOD")==0)
        {
             C_SCOPE++;
             scopeList.add(C_SCOPE);
             //System.out.println("cmnt-->SETTING SCOPE AS "+C_SCOPE);
             i++;
             //System.out.println("cmnt-->ITS METHOD IN D_M_C CHECKING ID ");
             if(tokens[i].CP.compareTo("ID")==0)
            {
               
                names.add(tokens[i].VP);
               
                 //System.out.println(tokens[i].VP + " AND "+classes.getLast());
                 if(tokens[i].VP.compareTo((String) classes.getLast())==0)
                {
                     System.out.println(RED+"METHOD WITH SAME NAME AS OF CLASS IS PROHIBITED\n"+PURPLE+tokens[i].VP+RED+" MUST BE OF SOME OTHER NAME .");
                }
                  i++;
                //System.out.println("cmnt-->CALLING F_D_M FROM D_M_C");
                 if(F_D_M())
                {
                     //System.out.println("cmnt-->RETURNING TRUE FROM D_M_C");
                     return true;
                }
            }//error("ID");
        }
         else if(tokens[i].CP.compareTo("COLLECTION")==0)
        {
             //System.out.println("cmnt-->CALLING CLASS FROM D_M_C");
             if(class_())
            {
                 //System.out.println("cmnt-->RETURNING TRUE FROM ARGS AFTER CHECKING CLASS");
                 return true;
            }
        }//error("TYP_SPS or method or collection");
         //System.out.println("cmnt-->RETURNING FALSE FROM D_M_C");
        return false;
    }
     private boolean decl()
    {    
         if(tokens[i].CP.compareTo("TYP_SPS")==0)
        {
            //System.out.println("cmnt-->Token is "+tokens[i].CP+" "+tokens[i].VP);
             if(tokens[i].VP.compareTo("array")==0)
            {
                 i++;
                 //System.out.println("cmnt-->ITS ARRAY  NEXT IS "+tokens[i].CP+" "+tokens[i].VP);
                 if(tokens[i].CP.compareTo("ID")==0)
                {
                     names.add(tokens[i].VP);
                     //System.out.println("cmnt-->HEREEEEEEE");
                     i++;
                     //System.out.println("cmnt-->Token is "+tokens[i].CP+" "+tokens[i].VP);
                     //System.out.println("cmnt-->CALLING A FROM DECL WITH "+tokens[i].CP);
                     if(A())
                     {
                         //System.out.println("cmnt-->A returning true");
                         if(tokens[i].CP.compareTo("OF_TYPE")==0)
                         {
                             i++;
                             if(tokens[i].CP.compareTo("DT")==0 || tokens[i].CP.compareTo("ID")==0)
                             {
                                int totalElements = 0; 
                                 for (int j = 0; j < names.size(); j++)
                                {
                                     //System.out.println("cmnt-->NAME IS "+names.get(j)+" SIZE IS "+arraySizes.get(j));
                                     String si = String.valueOf(arraySizes.get(j));
                                     totalElements = totalElements + Integer.valueOf(si);
                                }
                                 String Type;
                                 for (int j = 0; j < arrayList.size(); j++)
                                {
                                     Type = (String) arrayList.get(j);
                                     if(checkCom(Type,"variable_"+tokens[i].VP,0) == null)
                                    {
                                         c_error(Type,tokens[i].VP);
                                         return false;
                                    }
                                }
                                for (int j = 0; j < names.size(); j++)
                                {
                                     if(lookUp((String) names.get(j), (int) scopeList.getLast(), (String) classes.getLast())!=null)
                                    {
                                         error((String) names.get(j));
                                         return false;
                                    }
                                     String n = (String) names.get(j);
                                     insert(n,(int) scopeList.getLast(),"array_"+tokens[i].VP, AM,(int)arraySizes.get(j),(String) classes.getLast());
                                }
                                 names.clear();
                                 arrayList.clear();
                                 arraySizes.clear();
                                 i++;
                                 //System.out.println("cmnt-->RETURNING TRUE FROM DECL");
                                 return true;
                             }//error("DT or ID");
                         }//error("oftype");
                     }
                 }//error("ID");
                
             }
             else if(tokens[i].VP.compareTo("variable")==0)
            {
                 i++;
                 if(tokens[i].CP.compareTo("ID")==0)
                {
                     names.add(tokens[i].VP);
                     count++;
                     i++;
                     //System.out.println("cmnt-->CALLING V FROM DECL");
                     if(V())
                     {
                         //System.out.println("cmnt-->V Passed");
                         if(tokens[i].CP.compareTo("OF_TYPE")==0)
                         {
                             i++;
                             if(tokens[i].CP.compareTo("DT")==0)
                             {
                                 
                                for (int j = 0; j < names.size(); j++)
                                {
                                     if(lookUp((String) names.get(j), (int) scopeList.getLast(), (String) classes.getLast())!=null)
                                    {
                                         error((String) names.get(j));
                                         return false;
                                    }
                                     
                                     if(!operandResults.isEmpty())
                                     {
                                         operand.add(operandResults.removeFirst());
                                     }
                                     
                                     if(operand.isEmpty())
                                    {
                                         //System.out.println("cmnt-->ITS NULL");
                                    }
                                     else if(operand.size() == 1)
                                    {
                                         String type = (String) operand.getLast();
                                         String result = checkCom("variable_"+tokens[i].VP,type,"equals");
                                         if(result ==null || result.compareTo("variable_"+tokens[i].VP)!=0)
                                        {
                                             String typep[] = type.split("_");
                                             d_error(typep[typep.length-1],tokens[i].VP);
                                             return false;
                                        }
                                       
                                         operand.removeLast();
                                    }
                                     else
                                    {
                                         System.out.println(RED+"AN EXTREME ERROR HAS OCCURED !!!!!!!!!!!!!!!!");
                                    }
                                     String n = (String) names.get(j);
                                     insert(n,(int) scopeList.getLast(),"variable_"+tokens[i].VP, AM,(String) classes.getLast());
                                }
                                 names.clear();
                                 operandResults.clear();
                                 count = 0;
                                 //System.out.println("cmnt-->ALL VARIABLE  CLEAR!!!!!!");
                                 i++;
                                 //System.out.println("cmnt-->RETURNING TRUE FROM DECL");
                                 return true;
                             }//error("DT");
                         }//error("oftype");
                     }
                 }//error("ID");
            }
             else if(tokens[i].VP.compareTo("object")==0)
            {
                 i++;
                 if(tokens[i].CP.compareTo("ID")==0)
                 {
                     names.add(tokens[i].VP);
                     count++;
                     i++;
                     //System.out.println("cmnt-->CALLING O FROM DECL");
                     if(O())
                     {
                         //System.out.println("cmnt-->O Passed sixe is "+objectList.size());
                         if(tokens[i].CP.compareTo("OF_TYPE")==0)
                         {
                             i++;
                             if(tokens[i].CP.compareTo("ID")==0)
                            {
                                 String Class = lookUp(tokens[i].VP,scopeList);
                                 //System.out.println("cmnt-->CLASS = "+Class);
                                 if(Class == null)
                                {
                                     u_error(tokens[i].VP);
                                     return false;
                                }

                                // //System.out.println("cmnt-->BEFORE LOOK UP sixe is "+argsList.size());
                                 String lookUpResult = method_lookUp(tokens[i].VP,objectList);
                                 // //System.out.println("cmnt-->AFTER LOOK UP sixe is "+argsList.size());
                                 //System.out.println("cmnt-->lookUpResult are "+lookUpResult);
                                 if(lookUpResult == null)
                                {
                                     String first = (String) objectList.removeLast();
                                     //System.out.println("cmnt-->firsr is "+first);
                                     if(!(first.compareTo("nothing")==0 && objectList.isEmpty()))
                                    {
                                         u_u_error("SUCH CONSTRUCTOR");
                                         return false;
                                    }
                                }
                                
                                 for (int j = 0; j < names.size(); j++)
                                {
                                     if(lookUp((String) names.get(j), (int) scopeList.getLast(), (String) classes.getLast())!=null)
                                    {
                                         error((String) names.get(j));
                                         return false;
                                    }
                                     String n = (String) names.get(j);
                                     insert(n,(int) scopeList.getLast(),"object_"+tokens[i].VP, AM,(String) classes.getLast());
                                }
                                 names.clear();
                                 i++;
                                 //System.out.println("cmnt-->RETURNING TRUE FROM DECL");
                                 return true;
                             }//error("ID");
                         }//error("oftype");
                     }
                 }//error("ID");
            }
        }//error("TYP_SPS");
         //System.out.println("cmnt-->DECL RETURNING FLASE");
         return false;
    }
     private boolean O()
     {
         //System.out.println("cmnt-->O received "+tokens[i].CP);
         
         if(tokens[i].CP.compareTo("BUILD_WITH")==0)
         {
             i++;
             //System.out.println("cmnt-->BUILD WITH PASSED");
             if(tokens[i].CP.compareTo("NOTHING")==0)
            {
                 //System.out.println("cmnt-->ADING nothing");
                 objectList.add("nothing");
                 //System.out.println("cmnt-->sixe is "+argsList.size());
                 i++;
                 //System.out.println("cmnt-->RETURN TRUE FOR NOTHIN G");
                 return true;
            }
             else if(list_O())
            {
                 //System.out.println("cmnt-->E RETURNED TRUE ");
                 return true;
            }//error("nothing or argument list");
         }//error("buildwith");
         
         //System.out.println("cmnt-->O returning false");
         return false;
         
     }
     private boolean V()
    {
         //System.out.println("cmnt-->V RECEIVE "+ tokens[i].CP);
         
         if(tokens[i].CP.compareTo("OF_TYPE")==0)
         {
             //System.out.println("cmnt-->Simply return from V");
             return true;
         }
         else if(tokens[i].CP.compareTo("EQUALS")==0)
         {
             //System.out.println("cmnt-->CALLINH INIT FROM V");
             if(init())
             {
                 //System.out.println("cmnt-->INIT PASSED CALLING V1");
                 if(V1())
                 {
                     //System.out.println("cmnt-->V1 passed returning true");
                     return true;
                 }
             }
         }
         else if(tokens[i].CP.compareTo("AND")==0)
         {
             
             //System.out.println("cmnt-->CALLING V1 FROM V");
             if(V1())
             {
                 //System.out.println("cmnt-->RETURNING TRUE FROM V");
                 return true;
             }
         }//error("and or equals or oftype");
         //System.out.println("cmnt-->V returning false");
         return false;
     }
     private boolean init()
    {
         //System.out.println("cmnt-->In init with "+tokens[i].CP);
         if(tokens[i].CP.compareTo("EQUALS")==0)
         {
             i++;
             //System.out.println("cmnt-->ITS EQUALS CHECKING CALLING E");
             if(E())
            {
                 String r = (String) operand.removeLast();
                 //System.out.println("cmnt-->Inserting "+r+" "+count+" times.");
                 for (int j = 0; j <count; j++)
                {
                     operandResults.add(r);
                }
                 count = 0;
                 //System.out.println("cmnt-->Operands HAS "+operand.size()+" elements");
                 //System.out.println("cmnt-->init returning true");
                 return true;
            }
         }
         else if(tokens[i].CP.compareTo("AND")==0)
         {
             //System.out.println("cmnt-->CALLING V1 FROM INIT");
             if(V1())
             {
                 //System.out.println("cmnt-->INIT RETURN TRUE");
                 return true;
             }
         }
         else if(tokens[i].CP.compareTo("OF_TYPE")==0)
        {
             //System.out.println("cmnt-->INIT RETURN TRUE");
             return true;
        }//error("equals or and or oftype");
         //System.out.println("cmnt-->init returning false");
         return false;
     }
     private boolean V1()
    {
         //System.out.println("cmnt-->V1 receive "+tokens[i].CP);
         if(tokens[i].CP.compareTo("OF_TYPE")==0)
         {
             //System.out.println("cmnt-->V1 returns true");
             return true;
         }
         else if(tokens[i].CP.compareTo("AND")==0)
        {
             i++;
             count ++;
             if(tokens[i].CP.compareTo("ID")==0)
             {
                 names.add(tokens[i].VP);
                 i++;
                 //System.out.println("cmnt-->ID PASSSED calling init");
                 if(init())
                 {
                     //System.out.println("cmnt-->INIT PASSED CALLING V1 FROM V1");
                     if(V1())
                     {
                         //System.out.println("cmnt-->V1 passed in V1 return true");
                         return true;
                     }
                 }
             }//error("ID");
         }//error("oftyupe or and");
         //System.out.println("cmnt-->V1 returning false");
         return false;
     }
     private boolean A()
    {
         //System.out.println("cmnt-->IN A with "+tokens[i].CP);
         if (tokens[i].CP.compareTo("BUILD_WITH")==0)
        {
             i++;
             if(tokens[i].CP.compareTo("SIZE")==0)
            {
                 i++;
                 if(tokens[i].CP.compareTo("INT_CNST")==0)
                {
                     if(Integer.valueOf(tokens[i].VP)<=0)
                    {
                         System.out.println(RED+"INVALID SIZE FOR ARRAY "+PURPLE+names.getLast()+RED+" .");
                         return false;
                    }
                     arraySizes.add(Integer.valueOf(tokens[i].VP));
                     i++;
                     //System.out.println("cmnt-->Calling A1 with size "+arraySizes.getLast());
                     if(A1((int)arraySizes.getLast()))
                    {
                         //System.out.println("cmnt-->A returning true");
                         return true;
                    }
                }//error("int_const");
             }//error("size");
         }
         else if (tokens[i].CP.compareTo("HVNG_ELMNT")==0)
        {
             i++;
             arraySize = 0;
             //System.out.println("cmnt-->CALLING LIST FROM A");
             if(list_A())
            {
                 arraySizes.add(arraySize);
                 //System.out.println("cmnt-->CALLING A2 FROM A");
                 if(A2())
                {
                     //System.out.println("cmnt-->RETURNING TRUE FROM A");
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
         }//error("buildwith or havingelement or and or oftype");
         //System.out.println("cmnt-->A returning false");
         return false;
     }
     private boolean A1(int sizeP)
    {
         
         //System.out.println("cmnt-->A1 receive "+tokens[i].CP);
         if(tokens[i].CP.compareTo("ANOTHER")==0 || tokens[i].CP.compareTo("OF_TYPE")==0)
         {
             //System.out.println("cmnt-->CALLING A2");
             
             if(A2())
             {
                 //System.out.println("cmnt-->A1 returning teue");
                    return true;
             }
         }
         else if(tokens[i].CP.compareTo("HVNG_ELMNT")==0)
         {
             i++;
             arraySize = 0;
             //System.out.println("cmnt-->CALLING LIST FROM A1");
             if(list_A())
             {
                 //System.out.println("cmnt-->RETURNED SIZE IS "+arraySize);
                 if(arraySize > sizeP)
                 {
                     System.out.println(RED+"MORE ELEMENTS COUNTED AS COMPARED TO GIVEN SIZE IN ARRAY "+PURPLE+names.getLast());
                     return false;
                 }
                 if(arraySize < sizeP)
                 {
                     System.out.println(RED+"LESS ELEMENTS COUNTED AS COMPARED TO GIVEN SIZE IN ARRAY "+PURPLE+names.getLast());
                     return false;
                 }
                 //System.out.println("cmnt-->CALLING A2 from A1");
                 if(A2())
                 {
                         //System.out.println("cmnt-->RETURNING TRUE FROM A1");
                         return true;
                 }
             }
         }//error("and or oftype or havingelemant");
         //System.out.println("cmnt-->A1 returning false");
         return false;
     }
     private boolean A2()
    {
         
         //System.out.println("cmnt-->IN A2 with "+tokens[i].CP);
         if(tokens[i].CP.compareTo("OF_TYPE")==0)
         {
             //System.out.println("cmnt-->RETURNUNG TRUE FROM A2");
             return true;
         }
         else if(tokens[i].CP.compareTo("ANOTHER")==0)
         {
             i++;
             if(tokens[i].CP.compareTo("ID")==0)
             {
                 names.add(tokens[i].VP);
                 i++;
                 //System.out.println("cmnt-->CALLING A FROM A2");
                 if(A())
                 {
                     //System.out.println("cmnt-->returning true from A2");
                             return true;
                 }
             }//error("ID");
         }//error("oftype or and");
         
         //System.out.println("cmnt-->RETURNING FALSE FROM A2");
         return false;
     }
     private boolean list()
    {
         //System.out.println("cmnt-->list receive "+tokens[i].CP);
         //System.out.println("cmnt-->Calling E From List");
         if(E())
        {
             argsCount = (int) methodArgsCount.removeLast();
             argsCount++;
             methodArgsCount.add(argsCount);
             methodArgsList.add(operand.removeLast());
             //System.out.println("cmnt-->E PASSED CALLING LIST_M");
             if(list_m())
            {
                 //System.out.println("cmnt-->list_m passed returning true from list");
                 return true;
            }
        }
         //System.out.println("cmnt-->list returning false");
         return false;
     }
     private boolean list_m()
    {
         //System.out.println("cmnt-->list_m received "+tokens[i].CP);
         if(tokens[i].CP.compareTo("AND")==0)
        {
             i++;
             //System.out.println("cmnt-->Calling E from list_m");
             if(E())
            {
                 argsCount = (int) methodArgsCount.removeLast();
                 argsCount++;
                 methodArgsCount.add(argsCount);
                 methodArgsList.add(operand.removeLast());
                 //System.out.println("cmnt-->calling list_m from list_m");
                 if(list_m())
                {
                     //System.out.println("cmnt-->Returning true from list_m");
                     return true;
                }
            }//error("Expression");
        }
         else if(first_mst() ||tokens[i].CP.compareTo("END")==0 || tokens[i].CP.compareTo("OF_TYPE")==0)
        {
             //System.out.println("cmnt-->Returning true from list_m");
             return true;
        }//error("end or and or oftype or dot");
         //System.out.println("cmnt-->Returning false from list_m");
         return false;
    } 
      private boolean list_A()
    {
         if(E())
        {
             arraySize++;
             //System.out.println("cmnt-->SIZE BECOME "+arraySize+" FOR "+operand.getLast());
             arrayList.add(operand.removeLast());
             //System.out.println("cmnt-->E PASSED CALLING LIST_M");
             if(list_m_a())
            {
                 //System.out.println("cmnt-->list_m passed returning true from list");
                 return true;
            }
        }
         //System.out.println("cmnt-->list returning false");
         return false;
     }
     private boolean list_m_a()
    {
         //System.out.println("cmnt-->list_m received "+tokens[i].CP);
         if(tokens[i].CP.compareTo("AND")==0)
        {
             i++;
             //System.out.println("cmnt-->Calling E from list_m");
             if(E())
            {
                 arraySize++;
                 //System.out.println("cmnt-->SIZE BECOME "+arraySize+" FOR "+operand.getLast());
                 arrayList.add(operand.removeLast());
                 //System.out.println("cmnt-->calling list_m from list_m");
                 if(list_m_a())
                {
                     //System.out.println("cmnt-->Returning true from list_m");
                     return true;
                }
            }//error("Expression");
        }
         else if(tokens[i].CP.compareTo("OF_TYPE")==0 || tokens[i].CP.compareTo("ANOTHER")==0)
        {
             //System.out.println("cmnt-->Returning true from list_m");
             return true;
        }//error("end or and or oftype or dot");
         //System.out.println("cmnt-->Returning false from list_m");
         return false;
    } 
     private boolean list_O()
    {
         //System.out.println("cmnt-->list receive "+tokens[i].CP);
         //System.out.println("cmnt-->Calling E From List");
         if(E())
        {
             objectList.add(operand.removeLast());
             //System.out.println("cmnt-->E PASSED CALLING LIST_M");
             if(list_m_o())
            {
                 //System.out.println("cmnt-->list_m passed returning true from list");
                 return true;
            }
        }
         //System.out.println("cmnt-->list returning false");
         return false;
     }
     private boolean list_m_o()
    {
         //System.out.println("cmnt-->list_m received "+tokens[i].CP);
         if(tokens[i].CP.compareTo("AND")==0)
        {
             i++;
             //System.out.println("cmnt-->Calling E from list_m");
             if(E())
            {
                 objectList.add(operand.removeLast());
                 //System.out.println("cmnt-->calling list_m from list_m");
                 if(list_m_o())
                {
                     //System.out.println("cmnt-->Returning true from list_m");
                     return true;
                }
            }//error("Expression");
        }
         else if(first_mst() ||tokens[i].CP.compareTo("END")==0 || tokens[i].CP.compareTo("OF_TYPE")==0)
        {
             //System.out.println("cmnt-->Returning true from list_m");
             return true;
        }//error("end or and or oftype or dot");
         //System.out.println("cmnt-->Returning false from list_m");
         return false;
    } 
     private boolean E() 
    {
         //System.out.println("cmnt-->E HERE WITH "+tokens[i].CP);
         
         if(first_k())
        {
             //System.out.println("cmnt-->CALLING F ROM E");
             if(F())
            {
                 //System.out.println("cmnt-->F PASSED CALLING E1");
                 if(E1())
                {
                     //System.out.println("cmnt-->E1 PASSED IN E RETURNING TRUE");
                     return true;
                }
            }
        }
       //else if(foll_E())
       // {
       //      //System.out.println("cmnt-->E EXPECT FOLLOW RETURN TRUE");
       //      return true;
       // }
         //System.out.println("cmnt-->E returning false");
         return false;
    }
     private boolean E1()
    {
         //System.out.println("cmnt-->E1 receive "+tokens[i].CP);
         if(tokens[i].CP.compareTo("AO")==0)
        {
             String opp = tokens[i].VP;
             i++;
             //System.out.println("cmnt-->AO PASSED IN E1 CALLING F");
             if(F())
            {
                 String op1 = (String)operand.getLast();
                 String op2 = (String)operand.get(operand.size()-2);
                 result = checkCom(op1,op2,opp);
                 //System.out.println("cmnt-->CHECK COM IS --->>>>>>>>>>>>>>>>>>>>"+result);
                 if(result == null)
                {
                     c_error((String)ID_.removeLast(), (String) ID_.removeLast(),opp);
                     return false;
                }
                 
                 operand.removeLast();
                 operand.removeLast();
                 operand.add(result);
                 
                 ID_.removeLast();
                 ID_.removeLast();
                 ID_.add(result);
                 //System.out.println("cmnt-->F PASSED IN E1 CALLING E1");
                 if(E1())
                {
                     //System.out.println("cmnt-->E1 RETURNING TRUE");
                     return true;
                }
            }
       }
         else if(foll_E())
        {
             //System.out.println("cmnt-->E1 expect Follow return true");
             return true;
        }
         //System.out.println("cmnt-->E1 RETURNING FALSE");
         return false;
     }
     private boolean F()
    {
        //System.out.println("cmnt-->F RECEIVE "+tokens[i].CP);
        if(first_k())
        {
             //System.out.println("cmnt-->CALLING G FROM F");
             if(G())
             {
                  //System.out.println("cmnt-->G RETURNED TRUE CALLING F1");
                  if(F1())
                  {
                       //System.out.println("cmnt-->F1 returned true F ");
                       //System.out.println("cmnt-->F1 PASSED IN F RETURNING TRUE");
                       return true;
                  }
             }
        }
        // else if( foll_E() ||tokens[i].CP.compareTo("AO")==0)
       // {
        //    //System.out.println("cmnt-->F RETURN TRUE FS");
        //    return true;
        //}
        //System.out.println("cmnt-->F RETURNING FALSE");
        return false;
    }
     private boolean F1()
    {
         //System.out.println("cmnt-->F1 RECEIVE "+tokens[i].CP);
         
         if(tokens[i].CP.compareTo("OR_WITH")==0)
         {
             String opp = tokens[i].VP;
             i++;
             //System.out.println("cmnt-->OR_WITH PASSED IN F1 CALLING G");
             if(G())
             {
                 String op1 = (String)operand.getLast();
                 String op2 = (String)operand.get(operand.size()-2);
                 result = checkCom(op1,op2,opp);
                 //System.out.println("cmnt-->CHECK COM IS --->>>>>>>>>>>>>>>>>>>>"+result);
                 if(result == null)
                {
                     c_error((String)ID_.removeLast(), (String) ID_.removeLast(),opp);
                     return false;
                }
                 
                 operand.removeLast();
                 operand.removeLast();
                 operand.add(result);
                 
                 ID_.removeLast();
                 ID_.removeLast();
                 ID_.add(result);
                 //System.out.println("cmnt-->G PASSED IN F1 CALLING F1");
                 if(F1())
                 {
                     //System.out.println("cmnt-->F1 PASSED IN F1 RETURNIN GTRUE");
                     return true;
                 }
             }
         }
         else if(foll_E() ||tokens[i].CP.compareTo("AO")==0)
        {
             //System.out.println("cmnt-->f1 expecting follow return true");
             return true;
        }
         
         //System.out.println("cmnt-->F1 returning FALSE");
         return false;
     }
     private boolean G()
    {
         //System.out.println("cmnt-->G received "+tokens[i].CP);
         
          if(first_k())
        {
            //System.out.println("cmnt-->G calling H");
            if(H())
            {
                 //System.out.println("cmnt-->H PASSED IN G CALLING G1");
                 if(G1())
                 {
                     //System.out.println("cmnt-->PASSED G1 IN G , G RETURNING TRUE");
                     return true;
                 }
            }
            
        }
       //  else if(foll_E() ||tokens[i].CP.compareTo("AO")==0 || tokens[i].CP.compareTo("OR_WITH")==0)
       // {
       //       //System.out.println("cmnt-->G RETURN TRUE EXPECTING FOLLOW");
       //       return true;
      //  }
         //System.out.println("cmnt-->G returning false");
         return false;
     }
     private boolean G1()
    {
         //System.out.println("cmnt-->G1 received "+tokens[i].CP);
         
         if(tokens[i].CP.compareTo("AND_WITH")==0)
         {
             String opp = tokens[i].VP;
             i++;
             //System.out.println("cmnt-->PASSE DAND_WITH IN G1  CALLING H FROM G1");
             if(H())
             {
                 String op1 = (String)operand.getLast();
                 String op2 = (String)operand.get(operand.size()-2);
                 result = checkCom(op1,op2,opp);
                 //System.out.println("cmnt-->CHECK COM IS --->>>>>>>>>>>>>>>>>>>>"+result);
                 if(result == null)
                {
                     c_error((String)ID_.removeLast(), (String) ID_.removeLast(),opp);
                     return false;
                }
                 
                 operand.removeLast();
                 operand.removeLast();
                 operand.add(result);
                 
                 ID_.removeLast();
                 ID_.removeLast();
                 ID_.add(result);
                 //System.out.println("cmnt-->H passed in G1 calling G1");
                 if(G1())
                 {
                     //System.out.println("cmnt-->G1 passed in G1 returnign true");
                     return true;
                 }
             }
         }
         else if(foll_E() ||tokens[i].CP.compareTo("AO")==0 || tokens[i].CP.compareTo("OR_WITH")==0)
        {
              //System.out.println("cmnt-->G1 RETURN TRUE EXPECTING FOLLOW");
              return true;
        }
         //System.out.println("cmnt-->G1 RETURNING FALSE");
         return false;
     }
     private boolean H()
    {
          //System.out.println("cmnt-->H received "+tokens[i].CP);
          
         if(first_k())
        {
            //System.out.println("cmnt-->H calling I");
            if(I())
            {
                 //System.out.println("cmnt-->I PASSED IN H CALLING H1");
                 if(H1())
                 {
                     //System.out.println("cmnt-->PASSED H1 IN H , H RETURNING TRUE");
                     return true;
                 }
            }
            
        }
       // else if(foll_E() ||tokens[i].CP.compareTo("AO")==0 || tokens[i].CP.compareTo("OR_WITH")==0 || tokens[i].CP.compareTo("AND_WITH")==0)
       // {
       //       //System.out.println("cmnt-->H RETURN TRUE EXPECTING FOLLOW");
       //       return true;
       // }
          //System.out.println("cmnt-->H returning false");
          return false;
     }
     private boolean H1()
    {
         //System.out.println("cmnt-->H1 received "+tokens[i].CP);
         
         if(tokens[i].CP.compareTo("RO")==0)
        {
             String opp = tokens[i].VP;
             i++;
             //System.out.println("cmnt-->PASSE RO IN H1  CALLING I FROM H1");
             if(I())
            {
                 
                 String op1 = (String)operand.getLast();
                 String op2 = (String)operand.get(operand.size()-2);
                 result = checkCom(op1,op2,opp);
                 //System.out.println("cmnt-->CHECK COM IS --->>>>>>>>>>>>>>>>>>>>"+result);
                 if(result == null)
                {
                     c_error((String)ID_.removeLast(), (String) ID_.removeLast(),opp);
                     return false;
                }
                 
                 operand.removeLast();
                 operand.removeLast();
                 operand.add(result);
                 
                 ID_.removeLast();
                 ID_.removeLast();
                 ID_.add(result);
                 //System.out.println("cmnt-->I passed in H1 calling H1");
                 if(H1())
                 {
                     //System.out.println("cmnt-->H1 passed in H1 returnign true");
                     return true;
                 }
             }
         }
         else if(foll_E() ||tokens[i].CP.compareTo("AO")==0 || tokens[i].CP.compareTo("OR_WITH")==0 || tokens[i].CP.compareTo("AND_WITH")==0)
        {
              //System.out.println("cmnt-->H1 RETURN TRUE EXPECTING FOLLOW");
              return true;
        }
         //System.out.println("cmnt-->H1 RETURNING FALSE");
         return false;
     }
     private boolean I()
    {
        //System.out.println("cmnt-->I received "+tokens[i].CP);
          
         if(first_k())
        {
            //System.out.println("cmnt-->I calling J");
            if(J())
            {
                 //System.out.println("cmnt-->J PASSED IN I CALLING I1");
                 if(I1())
                 {
                     //System.out.println("cmnt-->PASSED I1 IN I , I RETURNING TRUE");
                     return true;
                 }
            }   
        }
      //   else if(foll_E() ||tokens[i].CP.compareTo("AO")==0 || tokens[i].CP.compareTo("OR_WITH")==0 || tokens[i].CP.compareTo("AND_WITH")==0 || tokens[i].CP.compareTo("RO")==0)
       // {
       //       //System.out.println("cmnt-->I RETURN TRUE EXPECTING FOLLOW");
       //       return true;
      //  }
          //System.out.println("cmnt-->I returning false");
          return false;
     }
     private boolean I1()
    {
         //System.out.println("cmnt-->I1 received "+tokens[i].CP);
         
         if(tokens[i].CP.compareTo("ADDSUB")==0)
         {
             String opp = tokens[i].VP;
             i++;
             //System.out.println("cmnt-->PASSE ADDSUB IN I1  CALLING J FROM I1");
             if(J())
            {
                 String op1 = (String)operand.getLast();
                 String op2 = (String)operand.get(operand.size()-2);
                 result = checkCom(op1,op2,opp);
                 //System.out.println("cmnt-->CHECK COM IS --->>>>>>>>>>>>>>>>>>>>"+result);
                 if(result == null)
                {
                     c_error((String)ID_.removeLast(), (String) ID_.removeLast(),opp);
                     return false;
                }
                 
                 operand.removeLast();
                 operand.removeLast();
                 operand.add(result);
                 
                 ID_.removeLast();
                 ID_.removeLast();
                 ID_.add(result);
                 //System.out.println("cmnt-->J passed in I1 calling I1");
                 if(I1())
                {
                     //System.out.println("cmnt-->I1 passed in I1 returnign true");
                     return true;
                }
            }
        }
         else if(foll_E() ||tokens[i].CP.compareTo("AO")==0 || tokens[i].CP.compareTo("OR_WITH")==0 || tokens[i].CP.compareTo("AND_WITH")==0 || tokens[i].CP.compareTo("RO")==0)
        {
              //System.out.println("cmnt-->I1 RETURN TRUE EXPECTING FOLLOW");
              return true;
        }
         //System.out.println("cmnt-->I1 RETURNING FALSE");
         return false;
     }
     private boolean J()
    {
          //System.out.println("cmnt-->J received "+tokens[i].CP);
          
         if(first_k())
        {
            //System.out.println("cmnt-->J calling K");
             if(K())
            {
                 //System.out.println("cmnt-->K PASSED IN J CALLING J1");
                 if(J1())
                {
                     //System.out.println("cmnt-->PASSED J1 IN J , J RETURNING TRUE");
                     return true;
                }
            }
            
        }
         
     //    else if(foll_E() ||tokens[i].CP.compareTo("ADDSUB")==0 || tokens[i].CP.compareTo("RO")==0 || tokens[i].CP.compareTo("AND_WITH")==0 || tokens[i].CP.compareTo("OR_WITH")==0 ||tokens[i].CP.compareTo("AO")==0 )
     //   {
     //        //System.out.println("cmnt-->J WXPECTING FOOLOW RETURN TRUE");
     //        return true;
     //   }
          //System.out.println("cmnt-->J returning false");
          return false;
     }
     private boolean J1()
    {
         if(tokens[i].CP.compareTo("DIVMUL")==0)
        {
             String opp = tokens[i].VP;
             i++;
             //System.out.println("cmnt-->PASSE DIVMUL IN J1  CALLING K FROM J1");
             if(K())
            {
                 String op1 = (String)operand.getLast();
                 String op2 = (String)operand.get(operand.size()-2);
                 result = checkCom(op1,op2,opp);
                 //System.out.println("cmnt-->CHECK COM IS --->>>>>>>>>>>>>>>>>>>>"+result);
                 if(result == null)
                {
                     c_error((String)ID_.removeLast(), (String) ID_.removeLast(),opp);
                     return false;
                }
                 
                 operand.removeLast();
                 operand.removeLast();
                 operand.add(result);
                
                 ID_.removeLast();
                 ID_.removeLast();
                 ID_.add(result);
                 //System.out.println("cmnt-->K passed in J1 calling J1");
                 if(J1())
                {
                     //System.out.println("cmnt-->J1 passed in J1 returnign true");
                     return true;
                }
            }
        }
         else if(foll_E() ||tokens[i].CP.compareTo("ADDSUB")==0 || tokens[i].CP.compareTo("RO")==0 || tokens[i].CP.compareTo("AND_WITH")==0 || tokens[i].CP.compareTo("OR_WITH")==0 ||tokens[i].CP.compareTo("AO")==0 )
        {
             //System.out.println("cmnt-->J1 eXPECTING FOOLOW RETURN TRUE");
             return true;
        }
         //System.out.println("cmnt-->J1 RETURNING FALSE");
         return false;
     }     
     private boolean K()
    {
        //System.out.println("cmnt-->K received "+tokens[i].CP);
          
         if(tokens[i].CP.compareTo("ID")==0)
        {
             ID_.add(tokens[i].VP);
             String dt = lookUp(tokens[i].VP, scopeList);
             if(dt==null)
            {
                 u_error(tokens[i].VP);
                 return false;
            }
             operand.add(dt);
             
             i++;
             //System.out.println("cmnt-->ID PASSED IN K CALLING ID1");
             if(ID1())
            {
                 //System.out.println("cmnt-->K returnin true");
                 return true;
            }
        }
         else if(const_())
        {
             //System.out.println("cmnt-->IT WAS CONSTANT IN K ");
             return true;
        }
         else if(tokens[i].CP.compareTo("BY")==0)
        {
             i++;
             //System.out.println("cmnt-->IT WAS BY");
             //System.out.println("cmnt-->calling expression");
             if(E())
            {
                 //System.out.println("cmnt-->OPERAND HAS "+operand.size()+" ELEMENTS");
                 //System.out.println("cmnt-->EXPRESSION PASSED IN E");
                //System.out.println("cmnt-->CHECKING THEN");
                if(tokens[i].CP.compareTo("THEN")==0)
                {
                     i++;
                     //System.out.println("cmnt-->THEN PASSED RETURING TRUE");
                     return true;
                }
                else if(foll_K())
                {
                    //System.out.println("cmnt-->FOLL_K PASSED RETURN TRUE FROM BY");
                    return true;
                }
            }
        }
         else if(tokens[i].CP.compareTo("INCDEC")==0)
        {
             String opp = tokens[i].VP;
             i++;
             //System.out.println("cmnt-->INCDEC PASSED IN K");
             if(tokens[i].CP.compareTo("ID")==0)
            {
                 ID_.add(tokens[i].VP);
                 String ID_T = lookUp(tokens[i].VP,scopeList);
                 if(ID_T == null)
                 {
                     u_error(opp);
                     return false;
                 }
                 String result = checkCom(ID_T,opp);
                 //System.out.println("cmnt-->RESULT IS "+result);
                 if(result == null)
                 {
                     c_error(ID_T,opp);
                     return false;
                 }
                 operand.add(result);
                 i++;
                 //System.out.println("cmnt-->ID PASSED RETURN TRUE FROM K");
                 return true;
            }
        }
         else if(tokens[i].CP.compareTo("METHOD")==0)
        {
             //System.out.println("cmnt-->IN METHOD IN K CALLIN METHOD");
             if(method_call())
            {
                 operand.add(methodArgsList.removeLast());
                 //System.out.println("cmnt-->METHOD PASSED RETURN TRUE");
                 return true;
            }
        }
         else if(tokens[i].CP.compareTo("INV")==0)
        {
             //System.out.println("cmnt-->IN INV ");
             String result;
             i++;
             //System.out.println("cmnt-->REC INV CALLING E");
             if(E())
            {
                 result = (String) operand.removeLast();
                 //System.out.println("cmnt-->RESULT IS "+result);
                 if(result == null)
                {
                     d_error("Expression","Inverse");
                     return false;
                }
                 
                 String result2 = checkCom(result,"inv");
                 if(result2 == null)
                {
                     d_error(result,"Inverse");
                     return false;
                }
                 operand.add(result2);
                // //System.out.println("cmnt-->RESULT IS "+result);
                 return true;
            }//System.out.println("cmnt-->E FAILED");
         }             
          //System.out.println("cmnt-->K returning false");
          return false;
     }  
     private boolean ID1()
    {
         //System.out.println("cmnt-->ID1 RECEIVED "+tokens[i].CP);
         
         if(tokens[i].CP.compareTo("INCDEC")==0)
        {
             String dt_oper = (String) operand.getLast();
            
             if(dt_oper.compareTo("variable_int")==0)
            {
                 //System.out.println("cmnt-->INCDEC APPLIED ON "+dt_oper);
                 operand.removeLast();
                 operand.add("variable_int");
            }
             else if(dt_oper.compareTo("variable_float")==0)
            {
                 //System.out.println("cmnt-->INCDEC APPLIED ON "+dt_oper);
                 operand.removeLast();
                 operand.add("variable_float");
            }
             else
            {
                 c_error((String) ID_.removeLast(),tokens[i].VP);
                 return false;
            }
             i++;
             //System.out.println("cmnt-->ITS INCDEC IN ID1 RETURNING TRUE");
             return true;
        }
         else if(tokens[i].CP.compareTo("POS")==0)
        {
             i++;
             //System.out.println("cmnt-->ITS POS CALLING C_M");
             if(C_M())
            {
                 //System.out.println("cmnt-->C_M PASSEED RETURNING TRUE FROM ID1");
                 return true;
            }
        }
         else if(foll_K())
         {
             //System.out.println("cmnt-->RETURN TRUE FROM ID1 FOR FOL_K");
             return true;
         }
         
         //System.out.println("cmnt-->ID1 RETURN FALSE");
         return false;
    } 
     private boolean C_M()
    {
         //System.out.println("cmnt-->C_M RECEIVED "+tokens[i].CP);
         
         if(tokens[i].CP.compareTo("INT_CNST")==0)
        {
             int s = Integer.valueOf(tokens[i].VP);
             if(s<0)
            {
                 System.out.println(RED+"INVALID LOCATION INDEX OF "+PURPLE+s+RED+" FOR "+PURPLE+ID_.removeLast()+RED +" IN LINE "+PURPLE+tokens[i].LineNumber+RED+" .");
                 return false;
            }
            
             String name = (String) ID_.getLast();
             int D_T = getSize(name,scopeList);
             if(D_T<=s)
             {
                 System.out.println(RED+"INVALID LOCATION INDEX OF "+PURPLE+s+RED+" FOR "+PURPLE+ID_.removeLast()+RED +" IN LINE "+PURPLE+tokens[i].LineNumber+RED+" .");
                 return false;
                 
             }
                     
             
             
             String ID = (String) operand.removeLast();
             //System.out.println("ID HERE IS "+ID);
             String ID_P[] = ID.split("_");
             if(ID_P[0].compareTo("array")!=0)
            {
                 d_error(ID,"array");
                 return false;
            }
             
             String ID_T = lookUp(tokens[i].VP,scopeList);
             
             
             operand.add("variable_"+ID_P[1]);
             
             i++;
             //System.out.println("cmnt-->IT WAS INT CONSTANT RETURN TRUE");
             return true;
        }
         else if(tokens[i].CP.compareTo("ID")==0)
        {
           
             String type = (String) operand.removeLast();
             String typeP[] = type.split("_");
             if(typeP[0].compareTo("object")!=0)
            {
                 d_error(typeP[1],"object");
                 return false;
            }
             String field = lookUp(tokens[i].VP,typeP[1]);
             //System.out.println("cmnt-->Field is -----"+field);
             if(field == null)
             {
                 u_c_error(tokens[i].VP,typeP[1]);
                 return false;
             }
             else
            {
                 operand.add(field);
            }
             
             
             i++;
             //System.out.println("cmnt-->IT WAS ID CONSTANT RETURN TRUE");
             return true;
        }
         else if(tokens[i].CP.compareTo("METHOD")==0)
        {
            
             //System.out.println("cmnt-->IT WAS METHOD CALLING METHOD FROM C_M");
             if(method_call())
            {
                 //System.out.println("cmnt-->METHOD CALL PASSED RETURN TRUE");
                 return true;
            }
         }//error("int_const or Id or method");
         //System.out.println("cmnt-->C_M return false");
         return false;
    }
     private boolean method_call()
    {
        //System.out.println("cmnt-->METHOD CALL RECEIVED "+tokens[i].CP);
         if(tokens[i].CP.compareTo("METHOD")==0)
        {
             //System.out.println("cmnt-->IN METHOD WITH METHOD ARGSCOUNT ===> "+methodArgsCount);
             //methodArgsCount.add(argsCount);
             argsCount = 0;
             methodArgsCount.add(argsCount);
             i++;
             //System.out.println("cmnt-->IT WAS  METHOD CHECKING ID");
             if(tokens[i].CP.compareTo("ID")==0)
            {
                 String M_NAME = tokens[i].VP;
                 String NAME = null ;
                 boolean ofObject = false;
                 
                 //System.out.println("cmnt-->RETRIVING FROM OPERAND___>");
                 if(!operand.isEmpty())
                {
                     NAME = (String) operand.getLast();
                     
                     if(NAME.split("_")[0].compareTo("object")==0)
                     {
                         operand.removeLast();
                         ofObject = true;
                     }
                }
                 
                 i++;
                 //System.out.println("cmnt-->ID PASSED ");
                 if(tokens[i].CP.compareTo("WITH")==0)
                {
                     i++;
                     //System.out.println("cmnt-->PASSED WITH ");
                     if(tokens[i].CP.compareTo("NOTHING")==0)
                    {
                         methodArgsList.add("NOTHING");
                         if(ofObject)
                        {
                             String result = method_lookUp(M_NAME,NAME.split("_")[1],methodArgsList);
                             if(result == null)
                             {
                                 u_error(M_NAME);
                                 return false;
                             }
                            // operand.add(result);
                             methodArgsList.removeLast();
                             methodArgsList.add(result);
                        }
                        else
                        {
                             String result = method_lookUp(M_NAME,scopeList,methodArgsList);
                             if(result == null)
                            {
                                 u_error(M_NAME);
                                 return false;
                            }
                            // operand.add(result);
                             methodArgsList.removeLast();
                             methodArgsList.add(result);
                        }
                         i++;
                         //System.out.println("cmnt-->METHOD WITH NOTHING RETURN TRUE");
                         return true;
                    }
                     else if(first_k())
                    {
                         //System.out.println("cmnt-->ITS E AFTER WITH CALLING LIST");
                         if(list())
                        {
                             //System.out.println("cmnt-->Now ArgsList= "+methodArgsCount);
                             //System.out.println("cmnt-->TOTALL ARGS IN METHODLIST "+methodArgsList.size());
                             int elements = (int) methodArgsCount.removeLast();
                             //System.out.println("cmnt-->elemets is "+elements);
                             int elementToRemove = methodArgsList.size()-elements;
                             //System.out.println("cmnt-->elements to remove  "+elementToRemove);
                             LinkedList elementsList = new LinkedList();
                             for (int j = 0; j < elements; j++) 
                            {
                                 //System.out.println("cmnt-->removing for j = "+j+" = "+methodArgsList.get(elementToRemove));
                                 elementsList.add(methodArgsList.remove(elementToRemove));
                            }
                             //System.out.println("cmnt-->ELEMENT LIST IS "+elementsList);
                             String result_;
                             if(ofObject)
                            {
                                 result_ = method_lookUp(M_NAME,NAME.split("_")[1],elementsList);
                                 if(result_ == null)
                                 {
                                     u_m_error(M_NAME);
                                     return false;
                                 }
                                // operand.add(result);
                            }
                            else
                            {
                                 result_ = method_lookUp(M_NAME,scopeList,elementsList);
                                 if(result_ == null)
                                {
                                    //System.out.println("cmnt-->RESULT SI NULL");
                                     u_m_error(M_NAME);
                                     return false;
                                }
                                // operand.add(result);
                            }
                             //System.out.println("cmnt-->result is "+result_);
                             methodArgsList.add(result_);
                             //System.out.println("cmnt-->MethodArgscount ====== > "+methodArgsCount);
                            // argsCount = (int) methodArgsCount.removeLast();
                            // argsCount++;
                            // methodArgsCount.add(argsCount);
                             if(tokens[i].CP.compareTo("END")==0)
                            {
                                 i++;
                                 //System.out.println("cmnt-->END PASSED RETURN TRUE FROM CALL _ METHOD");
                                 return true;
                            }//error("end");
                        }
                    }//error("nothing oe rxpression");
                }//error("with");
            }//error("Id");
         }//error("method");
         //System.out.println("cmnt-->METHOD_CALL RETURNING FALSE");
         return false;
     }  
     private boolean first_mst()
    {
         //System.out.println("cmnt-->FIRST_MST ANDRECEIVE "+tokens[i].CP);
         if( tokens[i].CP.compareTo("DOT")==0 || tokens[i].CP.compareTo("IS")==0 ||tokens[i].CP.compareTo("ANLZ")==0 ||tokens[i].CP.compareTo("RPT_WHL")==0 ||tokens[i].CP.compareTo("RPT")==0 ||tokens[i].CP.compareTo("RPT_IT")==0 ||tokens[i].CP.compareTo("RETURN")==0 ||tokens[i].CP.compareTo("TYP_SPS")==0 ||tokens[i].CP.compareTo("INCDEC")==0 ||tokens[i].CP.compareTo("ID")==0)
        {
             //System.out.println("cmnt-->MST RETURN TRUE");
             return true;
        }
         //System.out.println("cmnt-->MST RETURN FALSE");
         return false;
    }
     private boolean foll_E()
    {
         //System.out.println("cmnt-->Foll_E Receive "+tokens[i].CP);
         if( first_mst() ||  tokens[i].CP.compareTo("EXIT")==0  ||  tokens[i].CP.compareTo("TIMES")==0 || tokens[i].CP.compareTo("THEN")==0 ||tokens[i].CP.compareTo("AND")==0 ||tokens[i].CP.compareTo("ANOTHER")==0 ||tokens[i].CP.compareTo("END")==0 ||tokens[i].CP.compareTo("OF_TYPE")==0 ||tokens[i].CP.compareTo("MATCHES")==0 ||tokens[i].CP.compareTo("EQUALS")==0)
        {
             //System.out.println("cmnt-->fol_e RETURN TRUE");
             return true;
        }
         //System.out.println("cmnt-->fol_e RETURN FALSE");
         return false;
    }
     private boolean first_k()
    {
         //System.out.println("cmnt-->First_k Receive "+tokens[i].CP);
         if(tokens[i].CP.compareTo("ID")==0 || tokens[i].CP.compareTo("BY")==0 || tokens[i].CP.compareTo("INCDEC")==0 || tokens[i].CP.compareTo("METHOD")==0 || tokens[i].CP.compareTo("INV")==0 ||tokens[i].CP.compareTo("INT_CNST")==0 || tokens[i].CP.compareTo("BOOL_CNST")==0 || tokens[i].CP.compareTo("FLOAT_CNST")==0 || tokens[i].CP.compareTo("STRN_CNST")==0|| tokens[i].CP.compareTo("CHAR_CNST")==0)
        {
             //System.out.println("cmnt-->first_K RETURN TRUE");
             return true;
        }
         //System.out.println("cmnt-->first_K RETURN FALSE");
         return false;
    } 
     private boolean foll_K()
    {
         //System.out.println("cmnt-->FOLL_K RECEIVED " + tokens[i].CP);
           if(foll_E()||tokens[i].CP.compareTo("DIVMUL")==0 ||tokens[i].CP.compareTo("ADDSUB")==0 || tokens[i].CP.compareTo("RO")==0 || tokens[i].CP.compareTo("AND_WITH")==0 || tokens[i].CP.compareTo("OR_WITH")==0 ||tokens[i].CP.compareTo("AO")==0 )
        {
             //System.out.println("cmnt-->J1 eXPECTING FOOLOW RETURN TRUE");
             return true;
        }
           //System.out.println("cmnt-->FOLL_K RETURN FALSE");
           return false;
    }
     private void error(String required)
    {
         if(!error)
        {
             i--;
             System.out.println(RED+"VARIABLE "+PURPLE+required+RED+" ALREADY DEFINED IN THIS/PARENT SCOPE OF LINE NUMBER "+PURPLE+tokens[i].LineNumber+RED+".");  
        }
         error = true;
    } 
     private void m_error(String required)
    {
         if(!error)
        {
             i--;
             System.out.println(RED+"METHOD "+PURPLE+required+RED+" ALREADY DEFINED IN THIS/PARENT SCOPE OF LINE NUMBER "+PURPLE+tokens[i].LineNumber+RED+".");  
        }
         error = true;
    } 
     private void u_error(String required)
    {
         if(!error)
        {
             i--;
             System.out.println(RED+"VARIABLE "+PURPLE+required+RED+" NOT DEFINED IN THIS/PARENT SCOPE OF LINE NUMBER "+PURPLE+tokens[i].LineNumber+RED+".");  
        }
         error = true;
    } 
     private void u_m_error(String required)
    {
         if(!error)
        {
             i--;
             System.out.println(RED+"METHOD "+PURPLE+required+RED+" NOT DEFINED (WITH SUCH SIGNATURE) IN THIS/PARENT SCOPE OF LINE NUMBER "+PURPLE+tokens[i].LineNumber+RED+".");  
        }
         error = true;
    } 
     private void u_c_error(String required,String class_)
    {
         if(!error)
        {
             i--;
             System.out.println(RED+"FIELD "+PURPLE+required+RED+" NOT DEFINED/ACCESSABLE IN CLASS "+PURPLE+class_+RED+" IN LINE NUMBER "+PURPLE+tokens[i].LineNumber+RED+".");  
        }
         error = true;
    } 
     private void u_u_error(String required)
    {
         if(!error)
        {
             i--;
             System.out.println(PURPLE+required+RED+" NOT DEFINED IN THIS/PARENT SCOPE OF LINE NUMBER "+PURPLE+tokens[i].LineNumber+RED+".");  
        }
         error = true;
    } 
     private void c_error(String operand, String Opp)
    {
         if(!error)
        {
             i--;
             System.out.println(PURPLE+operand+RED+" INCOMPATIBLE FOR "+PURPLE+Opp+RED+" IN LINE "+PURPLE+tokens[i].LineNumber+RED+".");  
        }
         error = true;
    }
     private void c_error(String LeftOpp, String RightOpp, String Opp)
    {
         if(!error)
        {
             i--;
             System.out.println(PURPLE+RightOpp+RED+" AND "+PURPLE+LeftOpp+RED+" INCOMPATIBLE FOR "+PURPLE+Opp+RED+" IN LINE "+PURPLE+tokens[i].LineNumber+RED+".");  
        }
         error = true;
    } 
     private void d_error(String type, String type2)
    {
         if(!error)
        {
             i--;
             System.out.println(PURPLE+type+RED+" CANNOT BE CONVERTED INTO "+PURPLE+type2+RED+" IN LINE "+PURPLE+tokens[i].LineNumber+RED+".");  
        }
         error = true;
    }
}