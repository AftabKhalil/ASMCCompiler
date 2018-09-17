       package lexicalanalysis;

import ICG.ICG;
import SemmenticAnalysis.SymenticAnalysis;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import SyntaxAnalysis.SyntaxAnalysis;
import static SyntaxAnalysis.SyntaxAnalysis.BLUE;
import static SyntaxAnalysis.SyntaxAnalysis.GREEN;
import java.util.Date;

/** 
 * @author aftab
 */

 public class LexicalAnalysis
{
     static int wordNumber = 1;
     
     static File file;
     static MyFrame frame ;
     static MyFileWritter fileWriter;
     static boolean line = false;
     static boolean word;
     
     public static LinkedList Tokens;

     public static void main(String[] args) throws IOException, InterruptedException
    {  
         long t1 = System.currentTimeMillis();
         Date d1 = new Date (t1);
         
         Tokens = new LinkedList();
         fileWriter = new MyFileWritter("E://Tokens.txt");
         readFile(new File("E://for.txt"));
         fileWriter.shutIt();
         if(new SyntaxAnalysis().result==true)
         if(new SymenticAnalysis().finalresult==true)
         new ICG();
         
         long t2 =  System.currentTimeMillis();
         Date d2 = new Date(t2);
         int ans = d2.getSeconds()-d1.getSeconds();
         System.out.print("\n"+GREEN+"BUILD SUCESSFULL (total time: "+(d2.getSeconds()-d1.getSeconds()));
         if(ans == 1)
        {
             System.out.println(" second)");
        }
         else
        {
              System.out.println(" seconds)");
        }
    }
     
     public static void readFile(File ASMCFile) throws FileNotFoundException, IOException
    {
         file = ASMCFile;
         String temp = "";
         long len = file.length();
         int lineNumber = 1;
         
        
         FileReader f = new FileReader(file);
        
         char p = 0;
         long i;
         for (i = 0; i <len; i++)
        {
             char c = (char) f.read();
            // System.out.println(c);
            
             switch (c)
            {
                 case '\t':
                 case '\n': 
                 case '\r':  line = true; word = true;
                 case ' ' :
                     switch(c)                      //LINE BREAK COME WITH \n AND \r ALL TOGETHR SO WE MUST SKIP NEXT CHARCER
                    {
                         case '\r': f.read(); i++; break; //IF NEXT IS \N INCREMENT LINE NUMBER
                    }
                     
                     wordNumber++;
                     
                     if(temp.compareTo("omit")==0)
                    {
                         while(true)
                        {
                             char a = (char) f.read();
                             i++;
                             p = a;
                             if (a=='\n' || a=='\r' ||i==len)
                            {
                                 f.read();
                                 i++;
                                 break;
                            }
                        }
                         temp = "";
                         line = true;
                    }
                     else if(temp.compareTo("dontread")==0)
                    {
                         String comment = "";
                         while(true)
                        {
                             char a = (char) f.read();
                             i++;
                             if(a==' ' || a=='\r')
                            {
                                 if(a=='\r')
                                {
                                     line = true;
                                     wordNumber = 1;
                                }
                                 if(comment.compareTo("tillnow")==0)
                                {
                                    break;
                                }
                                comment = "";
                            }
                             else
                            {
                                comment = comment+a;
                            }
                        }
                         temp = "";
                    }
                     else
                    {
                         TokenGenerator(temp, lineNumber);
                         temp = "";
                         p = c;
                         break;
                    }
                 break;
                 
                 case '.':
                     //System.out.println("HERE and temp is "+temp);
                     if(temp.compareTo("-")==0 || temp.compareTo("+")==0)
                    {
                         temp = temp+c;
                    }
                     else if(temp.compareTo("")==0)
                    {
                         //System.out.println("HERRRE");
                         p = c;
                         char a = (char)f.read();
                         //System.out.println("I READ |||"+a+"|||");
                         i++;
                         if(a>='0'&&a<='9')
                        {
                             temp = ".";
                             temp = temp+a;
                        }
                         else if(a=='\n' || a== '\r' || i==len)
                        {
                             if(line)
                            {
                                 line = false;
                                 lineNumber++;
                            }
                             generateToken("DOT",".",lineNumber);
                             line = true;
                             
                         }
                         else
                        {
                             generateToken("DOT",".",lineNumber);
                             temp = temp+a;//WROTE THIS LINE LASTTTTT
                        } 
                    }
                     else
                    {
                        
                         try
                        {
                            Integer.valueOf(temp);

                            char a = (char)f.read();
                            i++;
                            if(a>='0'&&a<='9')
                           {
                                temp = temp+".";
                                temp = temp+a;
                           }
                            else
                           {
                                //System.out.println("Sending "+temp);
                                TokenGenerator(temp,lineNumber);
                                TokenGenerator(".",lineNumber);
                                wordNumber = 0;
                                line = true;
                                temp="";
                                temp = temp+a;    
                           }
                        }
                        catch(NumberFormatException | IOException e)
                       {
                            TokenGenerator(temp,lineNumber);
                            temp="";
                            temp = temp+".";
                       }
                    }
                 break;
                           
                 case '+':
                 case '-': TokenGenerator(temp,lineNumber);
                           temp = "";
                           temp = temp+c;
                 break;
                     
                 case '\'':
                     
                     wordNumber++;
                     
                     if(p == ' ')
                    {
                         String data = "\'";
                         char a = (char) f.read();
                         i++;
                         p = a;
                         data = data+a;
                         if(a == '\\')
                        {
                             char b = (char) f.read();
                             i++;
                             p = b;
                             data = data+b;
                        }
                         char b = (char) f.read();
                         i++;
                         p = b;
                         data = data + b;
                         if(CHAR_STRING.check(data))
                        {
                             generateToken("CHAR_CNST",data,lineNumber);
                        }
                        else
                        {
                             //frame.appendError("\n\nTOKEN NOT GENERATED FOR |||"+data+"||| IN LINE NUMBER "+lineNumber+" WORD NUMBER "+wordNumber);
                             System.out.println("TOKEN NOT GENERATED FOR |||"+data+"||| IN LINE NUMBER "+lineNumber+" WORD NUMBER "+wordNumber);
                        }
                    }
                     else
                    {
                         TokenGenerator(temp,lineNumber);
                         TokenGenerator("\'",lineNumber);
                    }
                     temp = "";
                 break;
                 
                 case '"':
                     
                     wordNumber++;
                     
                     TokenGenerator(temp,lineNumber);
                     temp = "";   
                     String data = "\"";
                     while(true)
                    {
                         char a = (char) f.read();
                         data = data +a;
                         i++;
                         p = a;
                         if(a=='\\')
                        {
                             char b = (char) f.read();
                             i++;
                             p = b;
                             data = data+b;
                        }
                         if(a=='\"')
                        {
                             break;
                        }
                         if(file.length()-i==0)
                        {
                             //frame.appendError("\n\nTOKEN NOT GENERATED FOR |||"+data+"||| IN LINE NUMBER "+lineNumber+" WORD NUMBER "+wordNumber+" END OF FILE REACHED.");
                             System.out.println("TOKEN NOT GENERATED FOR |||"+data+"||| IN LINE NUMBER "+lineNumber+" WORD NUMBER "+wordNumber);
                             return;
                        }
                    }
                     
                     if(CHAR_STRING.check(data))
                    {
                         generateToken("STRN_CNST",data,lineNumber);
                    }
                     else
                    {
                         //frame.appendError("\n\nTOKEN NOT GENERATED FOR |||"+data+"||| IN LINE NUMBER "+lineNumber+" WORD NUMBER "+wordNumber);
                         System.out.println("TOKEN NOT GENERATED FOR |||"+data+"||| IN LINE NUMBER "+lineNumber+" WORD NUMBER "+wordNumber);
                    }
                     break;
                
                 default:
                     temp = temp+c;
                     p = c;
                     
                     
                     if(line)
                     {
                         lineNumber++ ;
                         line  = false;
                         wordNumber = 1;
                     }
                         
                 break;
            }
        }
         temp = temp.trim();
         TokenGenerator(temp, lineNumber);
    }
     
     private static void TokenGenerator(String input,int lineNumber)
    {
         //System.out.println("I receive "+input);
         String temp = input;
         if("".equals(temp)) {
         } else if(TYP_SPS.check(temp))
        {
             generateToken("TYP_SPS",temp,lineNumber);
        }
         else if(DataType.check(input))
        {
             generateToken("DT",temp,lineNumber);
        }
         else
        {
             String key = KEY_WORDS.check(input);
             if(key != null)
            {
                 generateToken(key,temp,lineNumber);
            }
             else if(ID.check(temp))
            {
                 generateToken("ID",temp,lineNumber);
            }
             else
            {
                 int isIntOrFloat = INT_AND_FLOAT.check(input);
                 //System.out.println(isIntOrFloat);
                 switch (isIntOrFloat) {
                     case 0:
                         generateToken("INT_CNST",temp,lineNumber);
                         break;
                     case 1:
                         generateToken("FLOAT_CNST",temp,lineNumber);
                         break;
                     default:
                        // frame.appendError("\n\nTOKEN NOT GENERATED FOR |||"+temp+"||| IN LINE NUMBER "+lineNumber+" WORD NUMBER "+wordNumber);
                         if(((int)temp.charAt(0))!=65535)
                         System.out.println("335 TOKEN NOT GENERATED FOR |||"+temp+"||| IN LINE NUMBER "+lineNumber+" WORD NUMBER "+wordNumber);    
                         break;
                 }
            }
        }
    }
     
     private static void generateToken(String type, String value, int lineNumber)
    {
         try {
             fileWriter.write("("+type+","+value+","+lineNumber+")");
             Tokens.add(new String[]{type,value,String.valueOf(lineNumber)});
             
         } catch (IOException ex) {
             JOptionPane.showMessageDialog(frame, ex,"ERROR",ERROR_MESSAGE);
         }
    }
}