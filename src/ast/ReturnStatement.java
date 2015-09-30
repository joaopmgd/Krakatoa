package ast;

/**
 * Created by joao on 29/09/15.
 */
public class ReturnStatement extends Statement {

    private Expr expr;

    public ReturnStatement(Expr expr){
        this.expr = expr;
    }


    public void genKra(PW pw){

    }

    @Override
    public void genC(PW pw) {

    }
}