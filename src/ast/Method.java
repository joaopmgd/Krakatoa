package ast;

import java.util.ArrayList;

/**
 * Created by joao on 29/09/15.
 */
public class Method {

    private Type type;
    private String name;
    private boolean still;
    private boolean finale;
    private ParamList paramList;
    private LocalVariableList localVariableList;
    private StatementList statementList;

    public Method(Type type, String name, boolean still, boolean finale, ParamList paramList, StatementList statementList){
        this.type = type;
        this.name = name;
        this.still = still;
        this.finale = finale;
        this.paramList = paramList;
        this.statementList = statementList;
    }

    public String getName() {
        return name;
    }

    public ParamList getParamList() {
        return paramList;
    }

    public void setStatementList(StatementList statementList) {
        this.statementList = statementList;
    }
}
