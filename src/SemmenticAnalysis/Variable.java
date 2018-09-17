package SemmenticAnalysis;

/**
 * @author aftab
 */

 public class Variable
{
     private final String name;
     private final int scope;
     private final String dataType;
     private final String Class_;
     private final String AM;
     private final int size;

    public String getClass_() {
        return Class_;
    }

    public String getName() {
        return name;
    }

    public int getScope() {
        return scope;
    }

    public String getAM() {
        return AM;
    }

    public int getSize() {
        return size;
    }
    
    public String getDataType() {
        return dataType;
    }
     
     public Variable(String name, int scope, String dataType, String AM, String Class)
    {
         this.name = name;
         this.scope = scope;
         this.dataType = dataType;
         this.Class_ = Class;
         this.AM = AM;
         this.size = 0;
    }
     
     public Variable(String name, int scope, String dataType, String AM, int size, String Class) 
    {
         this.name = name;
         this.scope = scope;
         this.dataType = dataType;
         this.Class_ = Class;
         this.AM = AM;
         this.size = size;
    }
}
