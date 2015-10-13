package ast;

/**
 * Created by joao on 29/09/15.
 */
public class CompositeStatement extends Statement {

    private StatementList statementList;

    public CompositeStatement(StatementList statementList){
        this.statementList = statementList;
    }

    public void genKra(PW pw){
        pw.println(" {");
        pw.add();
        statementList.genKra(pw);
        pw.sub();
        pw.printlnIdent("}");
    }

    @Override
    public void genC(PW pw) {

    }
}
