package ast;

import java.util.*;

public class ParamList {

    private ArrayList<Variable> paramList;

    public ArrayList<Variable> getParamList() {
        return paramList;
    }

    public String getParamListNames() {
        String names = "";
        int i = paramList.size()-1;
        for (Variable variable: this.paramList){
            names = names + variable.getType().getName();
            if (i > 0){
                names = names + ", ";
                i--;
            }
        }
        return names;
    }

    public ParamList() {
       paramList = new ArrayList<Variable>();
    }

    public void addElement(Variable v) {
       paramList.add(v);
    }

    public int getSize() {
        return paramList.size();
    }

    public TypeList getTypeList(){
        TypeList typeList = new TypeList();
        for (Variable variable: this.paramList){
            typeList.addElement(variable.getType());
        }
        return typeList;
    }

    public void genKra(PW pw) {

        for (Iterator<Variable> iterator = paramList.iterator(); iterator.hasNext();) {
            Variable variable = (Variable) iterator.next();
            pw.print(variable.getType().getName());
            pw.print(" ");
            pw.print(variable.getName());
            if(iterator.hasNext())
                pw.print(", ");
        }
    }

    public void genC(PW pw, boolean commaFirst, boolean isStatic){
        int size = this.paramList.size();
        for(Variable variable: paramList) {
            if (size > 0 && commaFirst) {
                if (variable.getType() == Type.stringType) {
                    pw.print(", char *");
                }else if (variable.getType() instanceof KraClass){
                    pw.print(", _class_" + variable.getType().getName() + " *");
                }else{
                    pw.print(", "+variable.getType().getName()+" ");
                }
                variable.genC(pw);
            }else if (!commaFirst) {
                if (variable.getType() == Type.stringType){
                    pw.print("char *");
                }else if (variable.getType() instanceof KraClass){
                    pw.print("_class_"+variable.getType().getName()+" *");
                }else{
                    pw.print(variable.getType().getName()+" ");
                }
                variable.genC(pw);
                if ( --size > 0 )
                    pw.print(", ");
            }
        }
    }
}
