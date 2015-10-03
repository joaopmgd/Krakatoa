package ast;

import java.util.*;

public class ParamList {

    private ArrayList<Variable> paramList;

    public ParamList() {
       paramList = new ArrayList<Variable>();
    }

    public void addElement(Variable v) {
       paramList.add(v);
    }

    public Iterator<Variable> elements() {
        return paramList.iterator();
    }

    public int getSize() {
        return paramList.size();
    }

    public ArrayList<Variable> getParamList() {
        return paramList;
    }

    public TypeList getTypeList(){
        TypeList typeList = new TypeList();
        for (Variable variable: this.paramList){
            typeList.addElement(variable.getType());
        }
        return typeList;
    }
}
