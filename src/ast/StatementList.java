package ast;

import comp.Comp;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by joao on 29/09/15.
 */
public class StatementList {

    public StatementList() {
        this.statementList = new ArrayList<>();
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

    public boolean searchForRead(){
        boolean search = false;
        for (Statement statement: statementList){
            if (statement instanceof ReadStatement){
                return true;
            }
            if (statement instanceof CompositeStatement){
                search = ((CompositeStatement) statement).searchForRead();
            }
            if (statement instanceof IfStatement){
                search = ((IfStatement) statement).searchForRead();
            }
            if (statement instanceof WhileStatement){
                search = ((WhileStatement) statement).searchForRead();
            }
            if (search == true){
                return true;
            }
        }
        return false;
    }

    public void genC(PW pw, String className, boolean isStatic, String methodName){
        for (Statement statement : statementList){
            if (statement != null){
                pw.printIdent("");
                if (statement instanceof ReturnStatement){
                    statement.genC(pw, className, isStatic, methodName);
                }else{
                    statement.genC(pw, className, isStatic, methodName);
                }
            }
        }
    }

    private ArrayList<Statement> statementList;
}
