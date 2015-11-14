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

    public boolean searchForRead(){
        if (this.statement instanceof CompositeStatement){
            return ((CompositeStatement) statement).searchForRead();
        }
        if (this.statement instanceof IfStatement){
            return ((IfStatement) statement).searchForRead();
        }
        if (statement instanceof WhileStatement){
            return ((WhileStatement) statement).searchForRead();
        }
        return false;
    }

    @Override
    public void genC(PW pw, String className, boolean isStatic, String methodName) {
        pw.print("while( ");
        this.expr.genC(pw, false, className);
        pw.println(" )");
        if (!(this.statement instanceof CompositeStatement)){
            pw.add();
            pw.printIdent("");
            if (this.statement != null){
                this.statement.genC(pw, className, isStatic, methodName);
            }else{
                pw.println(";");
            }
            pw.sub();
        } else {
            this.statement.genC(pw, className, isStatic, methodName);
        }
    }
}
