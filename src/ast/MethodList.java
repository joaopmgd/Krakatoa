package ast;

import java.util.ArrayList;

/**
 * Created by joao on 29/09/15.
 */
public class MethodList {

    public boolean search(String name, ParamList paramList){
        for(Method method : methodList){
            if (method.getName().equals(name) && paramList.compareTo(method.getParamList()) == 0){
                return true;
            }
        }
        return false;
    }

    public MethodList() {
        methodList = new ArrayList<Method>();
    }

    public void addElement(Method method) {
        methodList.add(method);
    }

    public void addMethodList(MethodList methodList) {
        this.methodList.addAll(methodList.getMethodList());
    }

    public int getSize() {
        return methodList.size();
    }

    public ArrayList<Method> getMethodList() {
        return methodList;
    }

    private ArrayList<Method> methodList;
}
