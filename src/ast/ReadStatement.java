package ast;

/**
 * Created by joao on 29/09/15.
 */
public class ReadStatement extends Statement {

    private VariableList variableList;

    public ReadStatement (VariableList variableList){
        this.variableList = variableList;
    }

    public void genKra(PW pw){

    }

    @Override
    public void genC(PW pw) {

    }
}
