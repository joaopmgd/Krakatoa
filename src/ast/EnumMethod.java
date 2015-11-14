package ast;

/**
 * Created by joao on 13/11/15.
 */
public class EnumMethod {

    private String methodName;
    private String enumName;
    private String vtName;

    public EnumMethod (String methodName,String className, String superClassName){
        this.methodName = methodName;
        this.enumName = "_enum_";
        this.vtName = "( void (*)() ) _";
        if (superClassName != null){
            this.vtName += superClassName+"_";
            this.enumName += superClassName+"_";
        }else{
            this.vtName += className+"_";
        }
        this.vtName += methodName;
        this.enumName += className+"_"+methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void genCEnum(PW pw){
        pw.print(this.enumName);
    }

    public void genCVT(PW pw){
        pw.printIdent(this.vtName);
    }
}
