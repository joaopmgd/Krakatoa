package ast;

import java.util.*;

public class InstanceVariableList {

    private ArrayList<InstanceVariable> instanceVariableList;

    public Type searchStaticInstance(String identifier){
        for (InstanceVariable instanceVariable: instanceVariableList){
            if (instanceVariable.getName().equals(identifier) && instanceVariable.isStatic()){
                return instanceVariable.getType();
            }
        }
        return null;
    }

    public InstanceVariableList() {
       instanceVariableList = new ArrayList<InstanceVariable>();
    }

    public void addElement(InstanceVariable instanceVariable) {
       instanceVariableList.add(instanceVariable);
    }

    public void addInstanceVariableList(InstanceVariableList instanceVariableList) {
        this.instanceVariableList.addAll(instanceVariableList.getInstanceVariableList());
    }

    public int getSize() {
        return instanceVariableList.size();
    }

    public ArrayList<InstanceVariable> getInstanceVariableList() {
        return instanceVariableList;
    }

    public void genKra(PW pw) {
        for (InstanceVariable instanceVariable: instanceVariableList)
            instanceVariable.genKra(pw);
        if(!this.instanceVariableList.isEmpty())
            pw.println("");
    }

    public void genC(PW pw, String className, boolean isStatic) {
        for (InstanceVariable instanceVariable : instanceVariableList){
            if (!isStatic) {
                if (!(instanceVariable.isStatic())) {
                    instanceVariable.genC(pw, className);
                }
            } else {
                if (instanceVariable.isStatic()) {
                    instanceVariable.genC(pw, className);
                }
            }
        }
        if(isStatic){
            pw.println("");
        }
    }
}
