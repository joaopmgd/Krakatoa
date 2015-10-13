package ast;

import java.util.ArrayList;

/**
 * Created by joao on 30/09/15.
 */
public class DeclarationStatement extends Statement {

    private VariableList variableList;
    private Type type;

    public DeclarationStatement (Type type, VariableList variableList){
        this.variableList = variableList;
        this.type = type;
    }

    public void genKra(PW pw){
        pw.printIdent(this.type.getName());
        pw.print(" ");
        this.variableList.genKra(pw);
        pw.println(";");
    }

    @Override
    public void genC(PW pw) {

    }
}
