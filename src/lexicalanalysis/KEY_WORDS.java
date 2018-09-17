package lexicalanalysis;

/**
 * @author aftab
 */

 public class KEY_WORDS
{
     public static String check(String input)
    {
         switch(input)
        {
             case "\'"              : return "POS";
             case "."               : return "DOT";
             case "blueprint"       : return "BLU_PRNT";
             case "exit"            : return "EXIT";
             case "matches"         : return "MATCHES";
             case "collection"      : return "COLLECTION";
             case "repeatagain"     : return "RPT_AGN";
             case "alternative"     : return "ALTERNATIVE";
             case "repeat"          : return "RPT";
             case "notso"           : return "NOT_SO";
             case "achildof"        : return "A_CHILD_OF";
             case "true"            :
             case "false"           : return "BOOL_CNST";
             case "final"           : return "FINAL";
             case "repeatit"        : return "RPT_IT";
             case "times"           : return "TIMES";
             case "if"              : return "IS";
             case "makereal"        : return "MAKE_REAL";
             case "bring"           : return "BRING";
             case "unreal"          : return "UNREAL";
             case "buildwith"       : return "BUILD_WITH";
             case "null"            : return "NULL";
             case "pushback"        : return "RETURN"; 
  
             case "stable"          : return "STABLE";
             case "toparent"        : return "TO_PRNT";
             case "analyze"         : return "ANLZ";
             case "this"            : return "THIS";
             case "throw"           : return "THROW";
             case "try"             : return "TRY";
             case "nothing"         : return "NOTHING";
             case "repeatwhile"     : return "RPT_WHL";
             case "display"         : return "DISPLY";
            
             case "accessable"      : return "A_M";
             case "unaccessable"    : return "A_M";
             case "secret"          : return "A_M";
            
             case "elements"        : return "ELMNTS";
             
             case "oftype"          : return "OF_TYPE";
             case "equals"          : return "EQUALS";
             case "and"             : return "AND";
             case "sentence"        : return "SENTENCE";
             case "havingelements"  : return "HVNG_ELMNT";
             case "variable"        : return "VARIABLE";
            // case "accepts"         : return "ACCEPTS";
             case "with"            : return "WITH";
             case "belongsto"       : return "BLNG_TO";
             
             case "greaterthan"          : return "RO";
             case "greaterthanequalto"   : return "RO";
             case "lessthan"             : return "RO";
             case "lesstnequalto"        : return "RO";
             case "equalto"              : return "RO";
             case "notequalto"           : return "RO";
             
             case "andwith"         : return "AND_WITH";
             case "orwith"          : return "OR_WITH";
             case "andbits"         : return "AND_BITS";
             case "orbits"          : return "OR_BITS";
             case "notbits"         : return "NOT_BITS";
             case "xorbits"         : return "XOR_BITS";
             
             case "plus"            : return "ADDSUB";
             case "minus"           : return "ADDSUB";
             case "multiply"        : return "DIVMUL";
             case "divide"          : return "DIVMUL";
             case "remender"        : return "DIVMUL";
             case "by"              : return "BY";
             case "then"            : return "THEN";
             
             case "method"          : return "METHOD";
             case "starter"         : return "STARTER";
             case "returns"         : return "RETURNS";
             
             case "accepts"         : return "EXCEPTS";
             
             case "size"            : return "SIZE";
             case "is"              : return "IS";
             case "foreach"         : return "FOREACH";
             case "inc"             : return "INCDEC";
             case "dec"             : return "INCDEC";
             case "inverseof"       : return "INV";
             
             case "increadedby"     : return "AO";
             case "decreasedby"     : return "AO";
             case "amplifiedby"     : return "AO";
             case "shrinkby"        : return "AO";
             case "childof"         : return "EXTENDS";
             case "end"             : return "END";
             
             case "another"         : return "ANOTHER";
        }
         return null;
    }
}