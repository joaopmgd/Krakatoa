package ast;

/**
 * Created by joao on 29/09/15.
 */
public class BreakStatement extends Statement {

    public BreakStatement(){

    }


    public void genKra(PW pw){
        pw.printlnIdent("break;");
    }

    @Override
    public void genC(PW pw, String className, boolean isStatic, String methodName) {
        pw.println("break;");
    }
}
