package ast;

import java.util.*;

public class ParamList implements Comparable<ParamList> {

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

    private ArrayList<Variable> paramList;

    @Override
    public int compareTo(ParamList paramList) {
        if (this == paramList){
            return 0;
        }else{
            return 1;
        }
    }
}
