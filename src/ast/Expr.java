package ast;

abstract public class Expr {
    abstract public void genC( PW pw, boolean putParenthesis, String className );
      // new method: the type of the expression
    abstract public Type getType();
    abstract public void genKra(PW pw, boolean putParenthesis );
}