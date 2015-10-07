package ast;

import java.util.ArrayList;

/**
 * Created by joao on 29/09/15.
 */
public class MethodList {

    public Method search(String name, TypeList typeList, boolean isStatic){
        for(Method method : methodList){
            if (method.getName().equals(name)) {
                if (isStatic && method.isStatic()){
                    return method;
                } else if (typeList.compareTo(method.getParamList().getTypeList()) == 0) {
                    return method;
                }
            }
        }
        return null;
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
