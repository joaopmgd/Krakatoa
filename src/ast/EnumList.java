package ast;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by joao on 13/11/15.
 */
public class EnumList {

    private ArrayList<EnumMethod> enumMethods;

    public EnumList(){
        this.enumMethods = new ArrayList<EnumMethod>();
    }

    public int size(){
        return this.enumMethods.size();
    }

    public ArrayList<EnumMethod> getEnumMethods() {
        return enumMethods;
    }

    public void genCEnum(PW pw){
        Collections.reverse(this.enumMethods);
        int size = this.enumMethods.size();
        for (EnumMethod enumMethod: this.enumMethods){
            enumMethod.genCEnum(pw);
            if (--size != 0){
                pw.print(", ");
            }
        }
    }

    public void genCVT(PW pw){
        int size = this.enumMethods.size();
        for (EnumMethod enumMethod: this.enumMethods){
            enumMethod.genCVT(pw);
            if (--size != 0){
                pw.println(", ");
            }
        }
    }

    public void addAll(EnumList enumList){
        this.enumMethods.addAll(enumList.getEnumMethods());
    }

    public void add(EnumMethod enumMethod){
        this.enumMethods.add(enumMethod);
    }

    public void getMethodName(String className,MethodList methodList){
        this.enumMethods = methodList.getEnum(className).getEnumMethods();
        Collections.reverse(this.enumMethods);
    }

    public void getMethodNameSuper(EnumList enumList ,String className, String superClassName,MethodList methodList){
        this.enumMethods = methodList.getEnumSuper(enumList,className, superClassName).getEnumMethods();
        Collections.reverse(this.enumMethods);
    }

    public boolean check(String methodName){
        for (EnumMethod enumMethod: this.enumMethods){
            if (enumMethod.getMethodName().equals(methodName)){
                return true;
            }
        }
        return false;
    }
}
