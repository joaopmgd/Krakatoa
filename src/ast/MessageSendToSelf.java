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
        this.exprList = null;
    }

    public MessageSendToSelf(KraClass currentClass, String messageName, ExprList exprList){
        this.currentClass = currentClass;
        this.instanceVariableClass = null;
        this.instanceVariable = null;
        this.messageName = messageName;
        this.exprList = exprList;
    }

    public MessageSendToSelf(KraClass currentClass, InstanceVariable instanceVariable, String messageName, ExprList exprList, KraClass instanceVariableClass){
        this.currentClass = currentClass;
        this.instanceVariableClass = instanceVariableClass;
        this.instanceVariable = instanceVariable;
        this.messageName = messageName;
        this.exprList = exprList;
    }

    public boolean validateInstanceMessage() {
        return (this.instanceVariableClass.searchMethods(messageName,exprList.getTypeList(),false,false) != null);
    }

    public boolean validateClassMessage(){
        return(this.currentClass.searchMethods(messageName,exprList.getTypeList(),false,false) != null);
    }

    public Type getType() { 
        return this.instanceVariable.getType();
    }

    private void genKra(PW pw){

    }
    
    public void genC( PW pw, boolean putParenthesis ) {
    }
    
    
}