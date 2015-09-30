package ast;

/**
 * Created by joao on 29/09/15.
 */
public class SuperExpr extends Expr{

    private ExprList exprList;

    public SuperExpr (){

    }

    @Override
    public void genC(PW pw, boolean putParenthesis) {

    }

    @Override
    public Type getType() {
        return null;
    }
}
