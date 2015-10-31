package ast;

/**
 * Created by joao on 29/09/15.
 */
public class ObjectExpr extends Expr {

    private KraClass kraClass;
    private boolean isNew;

    public ObjectExpr (KraClass kraClass, boolean isNew){
        this.kraClass = kraClass;
        this.isNew = isNew;
    }

    public boolean isNew() {
        return isNew;
    }

    @Override
    public void genC(PW pw, boolean putParenthesis, String className) {
        if(putParenthesis)
            pw.print("(");

        if(this.isNew){
            pw.print("new_");
            pw.print(this.kraClass.getName());
            pw.print("()");
        }
        else
            pw.print("this");
        if(putParenthesis)
            pw.print(")");
    }

    @Override
    public Type getType() {
        return kraClass;
    }

    @Override
    public void genKra(PW pw, boolean putParenthesis) {
        if(putParenthesis)
            pw.print("(");

        if(this.isNew){
            pw.print("new ");
            pw.print(this.kraClass.getName());
            pw.print("()");
        }
        else
            pw.print("this");
        if(putParenthesis)
            pw.print(")");
    }
}
