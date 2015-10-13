package ast;

/**
 * Created by joao on 29/09/15.
 */
public class IfStatement extends Statement {

    private Expr expr;
    private Statement ifStatement;
    private Statement elseStatement;

    public IfStatement(Expr expr, Statement ifStatement, Statement elseStatement){
        this.expr = expr;
        this.ifStatement = ifStatement;
        this.elseStatement = elseStatement;
    }

    public void genKra(PW pw){
        pw.printIdent("if( ");
        this.expr.genKra(pw, false);

        if(this.ifStatement instanceof CompositeStatement){
            pw.print(") ");
            this.ifStatement.genKra(pw);
        }
        else{
            pw.println(")");
            pw.add();
            if(this.ifStatement != null)
                this.ifStatement.genKra(pw);
            else
                pw.printlnIdent(";");
            pw.sub();
        }

        if(this.elseStatement != null){
            pw.printIdent("else ");

            if(this.elseStatement instanceof CompositeStatement){
                this.elseStatement.genKra(pw);
            }
            else{
                pw.println("");
                pw.add();
                this.elseStatement.genKra(pw);
                pw.sub();
            }
        }
    }

    @Override
    public void genC(PW pw) {

    }
}
