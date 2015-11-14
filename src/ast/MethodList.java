package ast;

import java.util.ArrayList;

/**
 * Created by joao on 29/09/15.
 */
public class MethodList {

    public Method searchNameMethod(String run){
        for(Method method : this.methodList){
            if (method.getName().equals(run)) {
                return method;
            }
        }
        return null;
    }

    public Method search(String name, TypeList typeList, boolean isStatic){
        for(Method method : this.methodList){
            if (method.getName().equals(name)) {
                if (isStatic && method.isStatic()){
                    return method;
                } else if (typeList.compareTo(method.getParamList().getTypeList()) == 0) {
                    if (method.isStatic() == isStatic){
                        return method;
                    }
                }
            }
        }
        return null;
    }

    public Method searchDeclaration(Type type, String name, TypeList typeList, boolean isStatic, boolean superClass){
        for(Method method : this.methodList){
            if (method.getName().equals(name)) {
                if (superClass){
                    if(typeList.compareTo(method.getParamList().getTypeList()) != 0){
                        return method;
                    }
                    if (method.getType() != type){
                        return method;
                    }
                    if (method.isFinal()){
                        return method;
                    }
                }
                if (isStatic && method.isStatic()){
                    return method;
                } else if (typeList.compareTo(method.getParamList().getTypeList()) == 0) {
                    if (method.isStatic() == isStatic && !superClass){
                        return method;
                    }else if (method.isStatic() == isStatic ){
                        return null;
                    }
                }
            }
        }
        return null;
    }

    public void genKra(PW pw, String scope){
        for(Method m: methodList) {
            m.genKra(pw, scope);
        }
    }

    public void genC(PW pw, String className){
        for(Method m: methodList) {
            m.genC(pw, className);
        }
    }

    public EnumList getEnum(String className){
        EnumList enumList = new EnumList();
        for (Method m : this.methodList){
            if (!m.isStatic()) {
                enumList.add(new EnumMethod(m.getName(),className,null));
            }
        }
        return enumList;
    }

    public EnumList getEnumSuper(EnumList enumList, String className, String superClassName){
        EnumList newEnumList = new EnumList();
        for (Method m : this.methodList){
            if (!m.isStatic() && !enumList.check(m.getName())) {
                newEnumList.add(new EnumMethod(m.getName(),className,superClassName));
            }
        }
        return newEnumList;
    }

    public MethodList() {
        methodList = new ArrayList<Method>();
    }

    public void addElement(Method method) {
        methodList.add(method);
    }

    public int getSize() {
        return methodList.size();
    }

    private ArrayList<Method> methodList;
}
