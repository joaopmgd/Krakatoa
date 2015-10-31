package ast;

import java.util.ArrayList;

/**
 * Created by joao on 29/09/15.
 */
public class MethodList {

    public Method searchNameMethod(String run){
        for(Method method : methodList){
            if (method.getName().equals(run)) {
                return method;
            }
        }
        return null;
    }

    public Method search(String name, TypeList typeList, boolean isStatic){
        for(Method method : methodList){
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
        for(Method method : methodList){
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

    public void genMethodNameC(PW pw, String className, boolean commaFirst){
        int size = this.methodList.size();
        for(Method m: methodList) {
            if (!m.isStatic()) {
                if (size > 0 && commaFirst) {
                    pw.println(",");
                    m.genMethodNameC(pw, className);
                } else if (!commaFirst) {
                    m.genMethodNameC(pw, className);
                    if (--size > 0 ) {
                        pw.println(",");
                    }
                }
            }
            else{
                --size;
            }
        }
    }

    public boolean checkIfThereIsElements (){
        for (Method method: methodList){
            if (!(method.isStatic())){
                return true;
            }
        }
        return false;
    }

    public void genMethodListC(PW pw, String className, boolean commaFirst){
        int size = this.methodList.size();
        for ( Method m : methodList ) {
            if (!m.isStatic()) {
                if (size > 0 && commaFirst)
                    pw.print(", _enum_" + className + "_" + m.getName());
                else if (!commaFirst) {
                    pw.print("_enum_" + className + "_" + m.getName());
                    if (--size > 0)
                        pw.print(", ");
                }
            }else{
                --size;
            }
        }
    }

    public void genSuperMethodListC(PW pw, String superClassName, boolean commaFirst, String className){
        int size = this.methodList.size();
        for ( Method m : methodList ) {
            if (!m.isStatic()) {
                if (size > 0 && commaFirst)
                    pw.print(", _enum_" + superClassName + "_" + className + "_" + m.getName());
                else if (!commaFirst) {
                    pw.print("_enum_" + superClassName + "_" + className + "_" + m.getName());
                    if (--size > 0)
                        pw.print(", ");
                }
            }else{
                --size;
            }
        }
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

    public ArrayList<Method> getMethodList() {
        return methodList;
    }

    private ArrayList<Method> methodList;
}
