package ast;

/**
 * Created by joao on 29/09/15.
 */
public class WhileStatement extends Statement {

    private Expr expr;
    private Statement statement;

    public WhileStatement(Expr expr, Statement statement){
        this.expr = expr;
        this.statement = statement;
    }

    @Override
    public void genKra (PW pw){
        pw.printIdent("while( ");
        this.expr.genKra(pw, false);

        if(this.statement instanceof CompositeStatement){
            pw.print(" ) ");
            this.statement.genKra(pw);
        }
        else{
            pw.println(" )");
            pw.add();
            if(this.statement != null)
                this.statement.genKra(pw);
            else
                pw.printlnIdent(";");
            pw.sub();
        }

    }

    @Override
    public void genC(PW pw) {

    }
}
