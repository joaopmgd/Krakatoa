package ast;

public class LiteralString extends Expr {
    
    public LiteralString( String literalString ) { 
        this.literalString = literalString;
    }
    
    public void genC( PW pw, boolean putParenthesis ) {
        pw.print(literalString);
    }
    
    public Type getType() {
        return Type.stringType;
    }

    @Override
    public void genKra(PW pw, boolean putParenthesis) {
        if(this.literalString.equals(""))
            pw.print("\"\"");
        else
            pw.print("\""+  this.literalString +"\"");
    }
    
    private String literalString;
}
