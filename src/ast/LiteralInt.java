package ast;

public class LiteralInt extends Expr {
    
    public LiteralInt( int value ) { 
        this.value = value;
    }

    public void genC( PW pw, boolean putParenthesis, String className ) {
        pw.print(""+ value);
    }
    
    public Type getType() {
        return Type.intType;
    }

    @Override
    public void genKra(PW pw, boolean putParenthesis) {
        if(putParenthesis)
            pw.print("(");

        pw.print("" + this.value);

        if(putParenthesis)
            pw.print(")");
    }
    
    private int value;
}
