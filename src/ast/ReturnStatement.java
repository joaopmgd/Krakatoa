package ast;

/**
 * Created by joao on 29/09/15.
 */
public class ReturnStatement extends Statement {

    private Expr expr;

    public ReturnStatement(Expr expr){
        this.expr = expr;
    }

    public Type getReturnType(){
        return expr.getType();
    }

    @Override
    public void genKra(PW pw){
        pw.printIdent("return ");
        this.expr.genKra(pw, false);
        pw.println(";");
    }

    @Override
    public void genC(PW pw, String className, boolean isStatic, String methodType) {
        pw.print("return ");
        if (this.expr instanceof ObjectExpr && ((ObjectExpr)expr).isNew() || this.expr.getType() instanceof KraClass){
            pw.print("*");
        }
        if (!(this.expr.getType().getName().equals(methodType))){
            pw.print("(_class_"+methodType+"*)");
        }
        expr.genC(pw, false, className);
        pw.println(";");
    }
}