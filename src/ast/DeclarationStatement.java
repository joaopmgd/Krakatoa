package ast;

/**
 * Created by joao on 30/09/15.
 */
public class DeclarationStatement extends Statement {

    private VariableList variableList;
    private Type type;

    public DeclarationStatement (Type type, VariableList variableList){
        this.variableList = variableList;
        this.type = type;
    }

    public void genKra(PW pw){
        pw.printIdent(this.type.getName());
        pw.print(" ");
        this.variableList.genKra(pw);
        pw.println(";");
    }

    @Override
    public void genC(PW pw, String className, boolean isStatic, String methodName) {
        if (type instanceof KraClass){
            pw.print("_class_"+type.getName()+" ");
        }else if (type == Type.stringType){
            this.variableList.genCharC(pw);
        }else{
            pw.print(this.type.getName()+" ");
        }
        if (type != Type.stringType) {
            this.variableList.genC(pw, className, 0);
            pw.println(";");
        }
    }
}
