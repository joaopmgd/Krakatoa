package ast;

public class VariableExpr extends Expr {
    
    public VariableExpr( Variable v ) {
        this.v = v;
    }
    
    public void genC( PW pw, boolean putParenthesis, String className ) {
        pw.print( "_"+this.v.getName() );
    }
    
    public Type getType() {
        return v.getType();
    }

    public void genKra( PW pw, boolean putParenthesis ) {
        pw.print( v.getName() );
    }
    
    private Variable v;
}