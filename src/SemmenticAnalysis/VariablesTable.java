package SemmenticAnalysis;

import static SyntaxAnalysis.SyntaxAnalysis.BLUE;
import java.util.LinkedList;

/**
 * @author aftab
 */

 public class VariablesTable
{
     static LinkedList variables = new LinkedList();
     
     public static String lookUp(String name, LinkedList scope)
    {
         //System.out.println("I GOT "+name);
         for (int i = 0; i < scope.size(); i++) {
           //  System.out.println("Checking in "+scope.get(i));
        }
         
         LinkedList scope2 = new LinkedList();
         for (int i = 0; i < scope.size(); i++) {
            scope2.add(scope.get(scope.size()-i-1));
        }
         
         for (int i = 0; i < scope2.size(); i++)
        {
             for (int j = 0; j < variables.size(); j++)
            {
                 Variable v = (Variable) variables.get(j);
                 if(v.getName().compareTo(name)==0)
                {
                     int s = (int) scope2.get(i);
                     if(v.getScope() == s)
                     {
                         //System.out.println("RETURNUING "+v.getDataType());
                         return v.getDataType();
                     }
                }
            }
        }
         //System.out.println("RETURNING NULL");
         return null;
    }
     
     public static String lookUp(String name, int scope, String class_) 
    {
         //System.out.println("Finding Variable "+name+" in class "+class_);
         for (int j = 0; j < variables.size(); j++)
        {
             Variable v = (Variable) variables.get(j);
             if(v.getName().compareTo(name)==0)
            {
                 if(v.getClass_().compareTo(class_)==0)
                {
                     if(v.getScope() == scope)
                    {
                         return v.getDataType();
                    }
                }
            }
        }
         return null;
    }
     
       public static String lookUp(String name, String class_) 
    {
         //System.out.println("Finding Variable "+name+" in class "+class_);
         for (int j = 0; j < variables.size(); j++)
        {
             Variable v = (Variable) variables.get(j);
             if(v.getName().compareTo(name)==0)
            {
                 if(v.getClass_().compareTo(class_)==0)
                {
                     if(v.getAM().compareTo("secret")==0)
                    {
                         return null;
                    }
                     return v.getDataType();
                }
            }
        }
         return null;
    }
     
     public static void insert(String name, int scope, String dataType, String AM, String Class)
    {
         Variable v = new Variable(name, scope, dataType, AM, Class);
         variables.add(v);
         System.out.println(BLUE+"INSERTED "+name+" OF TYPE "+dataType+" IN SCOPE "+scope+" AND IN CLASS "+Class+"\n------------------------");
    } 
   
     public static void insert(String name, int scope, String dataType, String AM, int size, String Class)
    {
         Variable v = new Variable(name, scope, dataType, AM, size, Class);
         variables.add(v);
         System.out.println(BLUE+"INSERTED "+name+" OF TYPE "+dataType+" IN SCOPE "+scope+" AND IN CLASS "+Class+"\n------------------------");
    }
     public static int getSize(String name, LinkedList scope)
     {
         //System.out.println("I GOT "+name);
         for (int i = 0; i < scope.size(); i++) {
             //System.out.println("Checking in "+scope.get(i));
        }
         
         LinkedList scope2 = new LinkedList();
         for (int i = 0; i < scope.size(); i++) {
            scope2.add(scope.get(scope.size()-i-1));
        }
         
         for (int i = 0; i < scope2.size(); i++)
        {
             for (int j = 0; j < variables.size(); j++)
            {
                 Variable v = (Variable) variables.get(j);
                 if(v.getName().compareTo(name)==0)
                {
                     int s = (int) scope2.get(i);
                     if(v.getScope() == s)
                     {
                         //System.out.println("RETURNUING "+v.getDataType());
                         return v.getSize();
                     }
                }
            }
        }
         //System.out.println("RETURNING NULL");
         return 0;
    }
}