package ast;


public class MessageSendToSelf extends MessageSend {

    private InstanceVariable instanceVariable;
    private KraClass instanceVariableClass;
    private KraClass currentClass;
    private String messageName;
    private ExprList exprList;

    public MessageSendToSelf(KraClass currentClass, InstanceVariable instanceVariable){
        this.currentClass = currentClass;
        this.instanceVariableClass = null;
        this.instanceVariable = instanceVariable;
        this.messageName = null;
        this.exprList = new ExprList();
    }

    public MessageSendToSelf(KraClass currentClass, String messageName, ExprList exprList){
        this.currentClass = currentClass;
        this.instanceVariableClass = null;
        this.instanceVariable = null;
        this.messageName = messageName;
        if (exprList == null){
            this.exprList = new ExprList();
        }else{
            this.exprList = exprList;
        }
    }

    public MessageSendToSelf(KraClass currentClass, InstanceVariable instanceVariable, String messageName, ExprList exprList, KraClass instanceVariableClass){
        this.currentClass = currentClass;
        this.instanceVariableClass = instanceVariableClass;
        this.instanceVariable = instanceVariable;
        this.messageName = messageName;
        if (exprList == null){
            this.exprList = new ExprList();
        }else{
            this.exprList = exprList;
        }
    }

    public boolean validateInstanceMessage() {
        return (this.instanceVariableClass.searchMethods(this.messageName,this.exprList.getTypeList(),false,true) != null);
    }

    public boolean validateClassMessage(Method currentMethod){
        return (this.currentClass.searchMethods(this.messageName,this.exprList.getTypeList(),false,false) != null
        || (this.currentClass.compareCurrentMethod(this.messageName,this.exprList.getTypeList(),false,currentMethod)));
    }

    public Type getType() { 
        return this.instanceVariable.getType();
    }

    private void genKra(PW pw){

    }
    
    public void genC( PW pw, boolean putParenthesis ) {
    }
    
    
}