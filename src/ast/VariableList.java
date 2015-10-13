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

    private ArrayList<Variable> variableList;
}
