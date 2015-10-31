package ast;

public class ParenthesisExpr extends Expr {
    
    public ParenthesisExpr( Expr expr ) {
        this.expr = expr;
    }
    
    public void genC( PW pw, boolean putParenthesis, String className ) {
        pw.print("(");
        expr.genC(pw, false, className);
        pw.printIdent(")");
    }
    
    public Type getType() {
        return expr.getType();
    }

    @Override
    public void genKra(PW pw, boolean putParenthesis) {
        pw.print("(");
        expr.genKra(pw, false);
        pw.printIdent(")");
    }
    
    private Expr expr;
}