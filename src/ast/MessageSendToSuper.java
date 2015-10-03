package ast;

public class MessageSendToSuper extends MessageSend { 

    private ExprList exprList;
    private String name;
    private KraClass classOfMethod;

    public MessageSendToSuper (ExprList exprList, String name){
        this.exprList = exprList;
        this.name = name;
        this.classOfMethod = null;
    }

    public boolean validate(KraClass kraClass){
        KraClass superClass = kraClass.getSuperclass();
        this.classOfMethod = superClass.searchMethods(name, exprList.getTypeList(), false ,true);
        if (this.classOfMethod != null){
            return true;
        }
        return false;
    }


    public Type getType() { 
        return null;
    }

    public void genC( PW pw, boolean putParenthesis ) {
        
    }
    
}