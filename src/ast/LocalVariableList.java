package ast;

import java.util.*;

public class LocalVariableList {

    public LocalVariableList() {
       localList = new ArrayList<>();
    }

    public int getSize() {
        return localList.size();
    }

    public void genKra(PW pw) {

        for (Variable variable : localList) {
            pw.printIdent(variable.getType().getName());
            pw.print(" ");
            pw.print(variable.getName());
            pw.println(";");
        }
        pw.println("");
    }

    public void genC(PW pw){
        for (Variable variable : localList) {
            pw.printlnIdent(variable.getType().getName()+" "+variable.getName()+";");
        }
    }

    private ArrayList<Variable> localList;

}
