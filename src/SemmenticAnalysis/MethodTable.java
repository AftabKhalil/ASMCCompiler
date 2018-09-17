package SemmenticAnalysis;

import static SyntaxAnalysis.SyntaxAnalysis.BLUE;
import static SyntaxAnalysis.SyntaxAnalysis.GREEN;
import java.util.LinkedList;

/**
 * @author aftab
 */

 public class MethodTable
{
     public static LinkedList methods = new LinkedList();
     static int name = 253;
 
     public static String method_lookUp(String name, int scope, String class_, LinkedList argsLList)
    {
         boolean sameMethod;
         ////System.out.println("METHOD _ LOOKUP CALLED "+name+" "+class_);
         String argsList[] = new String[argsLList.size()];
         for (int i = 0; i < argsLList.size(); i++) {
            argsList[i] = (String) argsLList.get(i);
        }
         
         for (int i = 0; i < methods.size(); i++)
        {
             sameMethod = true;
             Method m = (Method) methods.get(i);
             if(m.getName().compareTo(name)==0)
            {
                 ////System.out.println("FOUND SIMMILAR METHOD");
                 if(m.getClass_().compareTo(class_)==0)
                {
                     ////System.out.println("IN SAME CLASS");
                     ////System.out.println("COMPARING "+m.getScope()+" AND "+scope);
                     if(m.getScope() == scope)
                    {
                         ////System.out.println("IN SAME SCOPE");
                         if(m.getArgsList().length == argsList.length)
                        {
                             ////System.out.println("SAME ARGS LEN");
                             for (int j = 0; j <  argsList.length; j++)
                            {
                                 ////System.out.println("Comparing "+m.getArgsList()[j]+" and "+argsList[j]);
                                 if(m.getArgsList()[j].compareTo(argsList[j])==0)
                                {
                                     sameMethod = sameMethod & true;
                                }
                                 else
                                {
                                     sameMethod = false;
                                }
                            }
                             if(sameMethod)
                            {
                                 ////System.out.println("EXACTLY SAME ");
                                 ////System.out.println("RETURNING "+ m.getReturnType());
                                 if(m.getReturnType().split("_").length==1)
                                {
                                     ////System.out.println("len is 1 ");
                                     return "variable_"+m.getReturnType();
                                }
                                 return m.getReturnType();
                            }
                        }
                    }
                }
            }
        }
         return null;
    }
     
     public static String method_lookUp(String name, String class_,LinkedList argsLList)
    {
         ////System.out.println("METHOD _ LOOKUP CALLED "+name+" "+class_);
         boolean sameMethod;
         String argsList[] = new String[argsLList.size()];
         for (int i = 0; i < argsLList.size(); i++) {
            argsList[i] = (String) argsLList.get(i);
        }
         
         for (int i = 0; i < methods.size(); i++)
        {
             sameMethod = true;
             Method m = (Method) methods.get(i);
             if(m.getName().compareTo(name)==0)
            {
                 ////System.out.println("FOUND SIMMILAR METHOD");
                 if(m.getClass_().compareTo(class_)==0)
                {
                     ////System.out.println("IN SAME CLASS");
                     if(m.getArgsList().length == argsList.length)
                    {
                         ////System.out.println("SAME ARGS LEN");
                         for (int j = 0; j <  argsList.length; j++)
                        {
                             ////System.out.println("Comparing "+m.getArgsList()[j]+" and "+argsList[j]);
                             if(m.getArgsList()[j].compareTo(argsList[j])==0)
                            {
                                 sameMethod = sameMethod & true;
                            }
                             else
                            {
                                 sameMethod = false;
                            }
                        }
                         if(sameMethod)
                        {
                             ////System.out.println("EXACTLY SAME ");
                             ////System.out.println("RETURNING "+ m.getReturnType());
                             if(m.getReturnType().split("_").length==1)
                             {
                                 ////System.out.println("len is 1 ");
                                 return "variable_"+m.getReturnType();
                             }
                             return m.getReturnType();
                        }
                    }
                }
            }
        }
         return null;
    }
     
      public static String method_lookUp(String name, LinkedList scope, LinkedList ArgsList)
    {
         boolean sameMethod;
         LinkedList scope2 = new LinkedList();
         for (int i = 0; i < scope.size(); i++) {
            scope2.add(scope.get(scope.size()-i-1));
        }
         String[] argsList = new String[ArgsList.size()];
         for (int i = 0; i < ArgsList.size(); i++) {
            argsList[i] = (String) ArgsList.get(i);
        }
         
         for (int i = 0; i < methods.size(); i++)
        {
             sameMethod = true;
             Method m = (Method) methods.get(i);
             if(m.getName().compareTo(name)==0)
            {
                 ////System.out.println("FOUND SIMMILAR METHOD");
                 for (int k = 0; k < scope2.size(); k++)
                {
                     ////System.out.println("Checking scope "+k);
                     if(m.getScope() == (int)scope2.get(k))
                    {
                         ////System.out.println("IN SAME SCOPE");
                         if(m.getArgsList().length == argsList.length)
                        {
                             ////System.out.println("SAME ARGS LEN");
                             for (int j = 0; j <  argsList.length; j++)
                            {
                                 ////System.out.println("Comparing "+m.getArgsList()[j]+" and "+argsList[j]);
                                 if(m.getArgsList()[j].compareTo(argsList[j])==0)
                                {
                                     sameMethod = sameMethod & true;
                                }
                                 else
                                {
                                     sameMethod = false;
                                }
                            }
                             if(sameMethod)
                            {
                                 ////System.out.println("EXACTLY SAME ");
                                 ////System.out.println("RETURNING "+ m.getReturnType());
                                 if(m.getReturnType().split("_").length==1)
                                 {
                                     ////System.out.println("len is 1 ");
                                     return "variable_"+m.getReturnType();
                                 }
                                 return m.getReturnType();
                            }
                        }
                    }
                }
            }
        }
         return null;
    }
   
      
      public static String method_lookUp(String name, LinkedList ArgsList)
    {
         boolean sameMethod;
         
         String[] argsList = new String[ArgsList.size()];
         for (int i = 0; i < ArgsList.size(); i++) {
            argsList[i] = (String) ArgsList.get(i);
        }
         
         for (int i = 0; i < methods.size(); i++)
        {
             sameMethod = true;
             Method m = (Method) methods.get(i);
             if(m.getName().compareTo(name)==0)
            {
                 ////System.out.println("FOUND SIMMILAR METHOD");
                 if(m.getArgsList().length == argsList.length)
                {
                     ////System.out.println("SAME ARGS LEN");
                     for (int j = 0; j <  argsList.length; j++)
                    {
                         ////System.out.println("Comparing "+m.getArgsList()[j]+" and "+argsList[j]);
                         if(m.getArgsList()[j].compareTo(argsList[j])==0)
                        {
                             sameMethod = sameMethod & true;
                        }
                         else
                        {
                             sameMethod = false;
                        }
                    }
                     if(sameMethod)
                    {
                             ////System.out.println("EXACTLY SAME ");
                             ////System.out.println("RETURNING "+ m.getReturnType());
                             if(m.getReturnType().split("_").length==1)
                            {
                                 ////System.out.println("len is 1 ");
                                 return "variable_"+m.getReturnType();
                            }
                             return m.getReturnType();
                    }    
                }
            }
        }
         return null;
    }
   
     public static void insert(String name, int scope, String class_, String returnType, String[] argsList)
    {
         Method m = new Method(name, scope, class_, returnType, argsList);
         methods.add(m);
         for (String s: argsList) {
             ////System.out.println("s = "+s);
        }
         System.out.println(BLUE+"INSERTED METHOD "+name+" IN SCOPE "+scope+" AND IN CLASS "+class_+" WITH RT "+returnType+"\n------------------------");
    }
     
     public static class Method
    {
         private final String   name;
         private final int      scope ;
         private final String   class_;
         private final String[] argsList;
         private final String   returnType;

         public String getName()
        {
            return name;
        }

         public int getScope()
        {
            return scope;
        }

         public String getClass_()
        {
            return class_;
        }

         public String[] getArgsList()
        {
            return argsList;
        }
      
         public String getReturnType() 
        {
             return this.returnType;
        }
         
         public Method(String name, int scope, String class_, String returnType, String[] argsList)
        {
             this.name       = name;
             this.scope      = scope;
             this.class_     = class_;
             this.returnType = returnType;
             this.argsList   = argsList;
        }
     }   
}