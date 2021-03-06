package ast;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by joao on 29/09/15.
 */
public class VariableList {

    public VariableList() {
        variableList = new ArrayList<Variable>();
    }

    public void addElement(Variable variable) {
        variableList.add(variable);
    }

    public int getSize() {
        return variableList.size();
    }

    public void genKra(PW pw){

        for (int i = 0; i < this.variableList.size(); i++) {
            Variable variable = this.variableList.get(i);
            pw.print(variable.getName());
            if(!( i == (this.variableList.size() - 1) ) ) //if it's not the last element
                pw.print(", ");

        }
    }

    public void genCharC(PW pw){
        int size = this.variableList.size();
        for (Variable variable: variableList){
            if (size != this.variableList.size()){
                pw.printIdent("");
            }
            pw.print("char *");
            variable.genC(pw);
            pw.println(";");
            size--;
        }
    }

    public void genC(PW pw, String className, int type){
        int size = this.variableList.size();
        if (type == 0){ // declaration
            for (Variable variable: variableList){
                if (variable.getType() instanceof KraClass){
                    pw.print("*");
                }
                variable.genC(pw);
                if ( --size > 0 )
                    pw.print(", ");
            }
        }
    }

    private ArrayList<Variable> variableList;
}
