package ast;

import java.util.*;

public class ExprList{

    private ArrayList<Expr> exprList;

    public ExprList() {
        this.exprList = new ArrayList<Expr>();
    }

    public void addElement( Expr expr ) {
        exprList.add(expr);
    }

    public boolean checkWriteExprListForBoolean(){
        for (Expr expr: this.exprList){
            Type type = expr.getType();
            if (type == Type.booleanType){
                return false;
            }
        }
        return true;
    }

    public boolean checkWriteExprListForNull(){
        for (Expr expr: this.exprList){
            Type type = expr.getType();
            if (type == Type.nullType){
                return false;
            }
        }
        return true;
    }

    public boolean checkWriteExprListForVoid(){
        for (Expr expr: this.exprList){
            Type type = expr.getType();
            if (type == Type.voidType){
                return false;
            }
        }
        return true;
    }

    public boolean checkWriteExprListForUndefined(){
        for (Expr expr: this.exprList){
            Type type = expr.getType();
            if (type == Type.undefinedType){
                return false;
            }
        }
        return true;
    }

    public boolean checkWriteExprListForObject(){
        for (Expr expr: this.exprList){
            if (expr.getType() instanceof KraClass){
                return false;
            }
        }
        return true;
    }

    public String getTypeNames(){
        String names = "";
        int i = exprList.size()-1;
        for (Expr expr: this.exprList){
            names = names + expr.getType().getName();
            if (i > 0){
                names = names + ", ";
                i--;
            }
        }
        return names;
    }

    public boolean checkReadExprList(){
        for (Expr expr: this.exprList){
            if (expr.getType() != Type.stringType && expr.getType() != Type.intType){
                return false;
            }
        }
        return true;
    }

    public void genC( PW pw, String className, boolean commaFirst ) {
        int size = exprList.size();
        for ( Expr e : exprList ) {
            if (size > 0 && commaFirst) {
                pw.print(", ");
                e.genC(pw, false, className);
            }
            else if (!commaFirst) {
                e.genC(pw, false, className);
                if ( --size > 0 )
                    pw.print(", ");
            }
        }
    }



    public void genSuperC( PW pw, String className, boolean commaFirst, String superClassName ) {
        int size = exprList.size();
        for ( Expr e : exprList ) {
            if (size > 0 && commaFirst) {
                pw.print(", ");
                if (e.getType() instanceof KraClass && (e instanceof MessageSendToSelf) || (e instanceof ObjectExpr && !((ObjectExpr)e).isNew())){
                    pw.print("(_class_"+superClassName+" *) ");
                }
                e.genC(pw, false, className);
            }
            else if (!commaFirst) {
                if (e.getType() instanceof KraClass && (e instanceof MessageSendToSelf) || (e instanceof ObjectExpr && !((ObjectExpr)e).isNew())){
                    pw.print("(_class_"+superClassName+" *) ");
                }
                e.genC(pw, false, className);
                if ( --size > 0 )
                    pw.print(", ");
            }
        }
    }

    public void genReadC(PW pw, String className){
        for (Expr expr:exprList){
            if (expr.getType() == Type.intType){
                pw.printIdent("sscanf(__s,\"%d\",&");
                expr.genC(pw, false, className);
                pw.println(");");
            }else{
                pw.printIdent("");
                expr.genC(pw, false, className);
                pw.println(" = malloc(strlen(__s)+1);");
                pw.printIdent("strcpy(");
                expr.genC(pw, false, className);
                pw.println(",__s);");
            }
        }
    }

    public void genPrintC(PW pw, String className){
        int count = 0;
        for (Expr expr:exprList){
            if (expr.getType() == Type.intType){
                if (count > 0){
                    pw.printIdent("");
                }
                pw.print("printf(\"%d\",");
                expr.genC(pw, false, className);
                pw.println(");");
                count++;
            }else{
                if (count > 0){
                    pw.printIdent("");
                }
                pw.print("puts(");
                expr.genC(pw,false,className);
                pw.println(");");
                count++;
            }
        }
    }

    public void genTypeNameC(PW pw, boolean commaFirst, Method method){
        int size = exprList.size();
        int id = 0;
        for ( Expr e : exprList ) {
            if (size > 0 && commaFirst)
                if (e.getType() == Type.stringType){
                    pw.print(", char *");
                }else if (e.getType() instanceof KraClass){
                    pw.print(", _class_"+e.getType().getName()+"*");
                }else{
                    pw.print(", ");
                    if (e instanceof NullExpr){
                        if(method.getParamList().getParamList().get(id).getType() == Type.stringType){
                            pw.print("char *");
                        }else if(method.getParamList().getParamList().get(id).getType() instanceof KraClass){
                            pw.print("_class_"+method.getParamList().getParamList().get(id).getType().getName()+"*");
                        }
                    }else{
                        pw.print(e.getType().getName());
                    }
                }
            else if (!commaFirst) {
                if (e.getType() == Type.stringType){
                    pw.print("char *");
                }else if (e.getType() instanceof KraClass){
                    pw.print("_class_"+e.getType().getName()+"*");
                }else{
                    if (e instanceof NullExpr){
                        if(method.getParamList().getParamList().get(id).getType() == Type.stringType){
                            pw.print("char *");
                        }else if(method.getParamList().getParamList().get(id).getType() instanceof KraClass){
                            pw.print("_class_"+method.getParamList().getParamList().get(id).getType().getName());
                        }
                    }else{
                        pw.print(e.getType().getName());
                    }
                }
                if (--size > 0)
                    pw.print(", ");
            }
            size--;
            id++;
        }
    }

    public TypeList getTypeList(){
        TypeList typeList = new TypeList();
        for (Expr expr: this.exprList){
            typeList.addElement(expr.getType());
        }
        return typeList;
    }

    public void genKra(PW pw){
        int size = exprList.size();
        for ( Expr e : exprList ) {
            e.genKra(pw, false);
            if ( --size > 0 )
                pw.print(", ");
        }
    }
}
