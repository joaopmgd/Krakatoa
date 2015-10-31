package ast;

/**
 * Created by joao on 29/09/15.
 */
public class WriteStatement extends Statement {

    private ExprList exprList;

    public WriteStatement (ExprList exprList){
        this.exprList = exprList;
    }

    @Override
    public void genKra(PW pw){
        pw.printIdent("write( ");
        this.exprList.genKra(pw);
        pw.println(" );");
    }

    @Override
    public void genC(PW pw, String className, boolean isStatic, String methodType) {
        this.exprList.genPrintC(pw, className);
    }
}
