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

    public boolean searchForRead(){
        if (ifStatement instanceof CompositeStatement){
            return ((CompositeStatement) ifStatement).searchForRead();
        }
        if (ifStatement instanceof IfStatement){
            return ((IfStatement) ifStatement).searchForRead();
        }
        if (ifStatement instanceof WhileStatement){
            return ((WhileStatement) ifStatement).searchForRead();
        }
        if (elseStatement instanceof CompositeStatement){
            return ((CompositeStatement) elseStatement).searchForRead();
        }
        if (elseStatement instanceof IfStatement){
            return ((IfStatement) elseStatement).searchForRead();
        }
        if (elseStatement instanceof WhileStatement){
            return ((WhileStatement) elseStatement).searchForRead();
        }
        return false;
    }

    @Override
    public void genC(PW pw, String className, boolean isStatic, String methodName) {
        pw.print("if (");
        expr.genC(pw, false, className);
        pw.println(")");
        if (!(this.ifStatement instanceof CompositeStatement)){
            pw.add();
            pw.printIdent("");
            if (this.ifStatement != null){
                this.ifStatement.genC(pw, className, isStatic, methodName);
            }else{
                pw.println(";");
            }
            pw.sub();
        }else{
            this.ifStatement.genC(pw,className, isStatic, methodName);
        }
        if (this.elseStatement != null){
            pw.printlnIdent("else");
            if (!(this.elseStatement instanceof CompositeStatement)){
                pw.add();
                pw.printIdent("");
                if (this.ifStatement != null){
                    this.elseStatement.genC(pw, className, isStatic, methodName);
                }else{
                    pw.println(";");
                }
                pw.sub();
            }else{
                this.elseStatement.genC(pw,className, isStatic, methodName);
            }
        }
    }
}
