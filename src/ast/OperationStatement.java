package ast;
import lexer.*;

/**
 * Created by joao on 28/10/15.
 */
public class OperationStatement extends Statement {

    private Variable variable;
    private Symbol operation;

    public OperationStatement (Variable variable, Symbol operation){
        this.variable = variable;
        this.operation = operation;
    }

    public void genKra(PW pw){
        pw.printlnIdent(this.variable.getName()+this.operation.toString()+this.operation.toString()+";");
    }

    @Override
    public void genC(PW pw, String className, boolean isStatic, String methodName) {
        this.variable.genC(pw);
        pw.println(this.operation.toString()+this.operation.toString()+";");
    }
}
