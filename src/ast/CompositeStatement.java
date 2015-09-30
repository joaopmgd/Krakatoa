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
    }

    @Override
    public void genC(PW pw) {

    }
}
