package ast;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by joao on 29/09/15.
 */
public class StatementList {

    public StatementList() {
        statementList = new ArrayList<Statement>();
    }

    public void addElement(Statement statement) {
        statementList.add(statement);
    }

    public int getSize() {
        return statementList.size();
    }

    public void genKra(PW pw) {
        if(!statementList.isEmpty()){
            for (Statement statement : statementList) {
                if (statement != null)
                    statement.genKra(pw);
            }
        }
    }

    private ArrayList<Statement> statementList;
}
