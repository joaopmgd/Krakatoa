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

    public void genC( PW pw ) {

        int size = exprList.size();
        for ( Expr e : exprList ) {
        	e.genC(pw, false);
            if ( --size > 0 )
                pw.print(", ");
        }
    }

    public TypeList getTypeList(){
        TypeList typeList = new TypeList();
        for (Expr expr: this.exprList){
            typeList.addElement(expr.getType());
        }
        return typeList;
    }
}
