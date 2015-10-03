package ast;


public class MessageSendToVariable extends MessageSend {

    private String firstId;
    private String identifier;
    private String messageName;
    private ExprList exprList;
    private KraClass kraClass;
    private Type instanceVariableType;
    private Type methodType;
    private KraClass superClass;

    // Clock.currentDay;
    public MessageSendToVariable (String firstId, String identifier){
        this.firstId = firstId;
        this.identifier = identifier;
        this.messageName = null;
        this.exprList = null;
        this.instanceVariableType = null;
        this.methodType = null;
        this.superClass = null;
    }

    public boolean validateInstance (KraClass kraClass){
        this.kraClass = kraClass;
        this.instanceVariableType =  this.kraClass.getInstanceVariableList().searchStaticInstance(this.identifier);
        return (instanceVariableType == null);
    }

    // object.setDay(12);
    public MessageSendToVariable (String firstId, String messageName, ExprList exprList){
        this.firstId = firstId;
        this.identifier = null;
        this.messageName = messageName;
        this.exprList = exprList;
        this.instanceVariableType = null;
        this.methodType = null;
        this.superClass = null;
    }

    // Clock.currentDay.setDay(12);
    public MessageSendToVariable (String firstId, String identifier, String messageName, ExprList exprList){
        this.firstId = firstId;
        this.identifier = identifier;
        this.messageName = messageName;
        this.exprList = exprList;
        this.instanceVariableType = null;
        this.methodType = null;
        this.superClass = null;
    }

    public boolean validateMethodMessage (KraClass kraClass, KraClass classFromVariable){
        if (kraClass != null){
            this.kraClass = kraClass;
            if (this.kraClass.searchMethods(messageName, exprList.getTypeList(), true, false) != null) {
                methodType = this.kraClass.getMethodType(messageName, exprList.getTypeList());
                return true;
            }else{
                return false;
            }
        }else if (classFromVariable != null){
            this.kraClass = classFromVariable;
            if (this.kraClass.searchMethods(messageName, exprList.getTypeList(), true, false) != null) {
                methodType = this.kraClass.getMethodType(messageName, exprList.getTypeList());
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    public boolean validateIndentifierMessage (){
        KraClass superClass = kraClass.searchMethods(messageName, exprList.getTypeList(), false, false);
        if (superClass != kraClass && superClass != null){
            this.superClass = kraClass;
            methodType = superClass.getMethodType(messageName,exprList.getTypeList());
        }else if (superClass != null){
            methodType = kraClass.getMethodType(messageName,exprList.getTypeList());
        }else{
            return false;
        }
        return true;
    }

    public Type getType() {
        if (methodType != null){
            return this.methodType;
        }else{
            return this.instanceVariableType;
        }
    }

    public void genKra(){}
    
    public void genC( PW pw, boolean putParenthesis ) {
        
    }

    
}    