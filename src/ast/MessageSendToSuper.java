package ast;

public class MessageSendToSuper extends MessageSend { 

    private ExprList exprList;
    private String name;
    private KraClass classOfMethod;
    private Type type;

    public String getExprListNames() {
        return exprList.getTypeNames();
    }

    public MessageSendToSuper (ExprList exprList, String name){
        if (exprList == null){
            this.exprList = new ExprList();
        }else{
            this.exprList = exprList;
        }
        this.name = name;
        this.classOfMethod = null;
        this.type = null;
    }

    public boolean validate(KraClass kraClass){
        KraClass superClass = kraClass.getSuperclass();
        if (superClass != null)
            this.classOfMethod = superClass.searchMethods(name, exprList.getTypeList(), false ,true);
        if (this.classOfMethod != null){
            type = this.classOfMethod.getMethodType(name,exprList.getTypeList(),false,true);
            return true;
        }
        return false;
    }


    public Type getType() { 
        return this.type;
    }

    public void genC( PW pw, boolean putParenthesis ) {
        
    }

    @Override
    public void genKra(PW pw, boolean putParenthesis) {
        if(putParenthesis)
            pw.print("(");

        pw.print("super.");
        pw.print(this.name);
        pw.print("(");
        this.exprList.genKra(pw);
        pw.print(")");

        if(putParenthesis)
            pw.print(")");

    }
    
}