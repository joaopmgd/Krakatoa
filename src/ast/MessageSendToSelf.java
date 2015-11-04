package ast;


public class MessageSendToSelf extends MessageSend {

    private InstanceVariable instanceVariable;
    private KraClass instanceVariableClass;
    private KraClass currentClass;
    private String messageName;
    private ExprList exprList;
    private Method method;
    private KraClass methodClass;

    public Method getMethod() {
        return method;
    }

    public String getMessageName() {
        return messageName;
    }

    public InstanceVariable getInstanceVariable() {
        return instanceVariable;
    }

    public String getExprListNames() {
        return exprList.getTypeNames();
    }

    public MessageSendToSelf(KraClass currentClass, InstanceVariable instanceVariable){
        this.currentClass = currentClass;
        this.instanceVariableClass = null;
        this.instanceVariable = instanceVariable;
        this.messageName = null;
        this.exprList = new ExprList();
        this.method = null;
        this.methodClass = null;
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
        this.method = null;
        this.methodClass = null;
    }
//
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
        this.method = null;
        this.methodClass = null;
    }

    public boolean validateInstanceMessage() {
        KraClass methodClass = this.instanceVariableClass.searchMethods(this.messageName,this.exprList.getTypeList(),false,true);
        if (methodClass != null){
            this.method = methodClass.getMethod(this.messageName,exprList.getTypeList());
            this.methodClass = methodClass;
            return true;
        }
        return false;
    }

    public boolean validateClassMessage(Method currentMethod){
        KraClass methodClass = this.currentClass.searchMethods(this.messageName,this.exprList.getTypeList(),false,false);
        if (methodClass != null){
            this.method = methodClass.getMethodFromThis(this.messageName,exprList.getTypeList());
            this.methodClass = methodClass;
            return true;
        }else{
            if (this.currentClass.compareCurrentMethod(this.messageName,this.exprList.getTypeList(),false,currentMethod)){
                this.method = currentMethod;
                this.methodClass = currentClass;
                return true;
            }
        }
        return false;
    }

    public Type getType() {
        if (this.method != null )
            return this.method.getType();
        else
            return this.instanceVariable.getType();
    }

    @Override
    public void genKra(PW pw, boolean putParenthesis) {
        if(putParenthesis)
            pw.print("(");

        pw.print("this");
        if(this.instanceVariable != null)
            pw.print("." + this.instanceVariable.getName());

        if(this.messageName != null){
            pw.print("."+this.messageName);
            pw.print("(");
            this.exprList.genKra(pw);
            pw.print(")");
        }

        if(putParenthesis)
            pw.print(")");
    }

//    this.m();
//    _a = (_class_A *) ( ( _class_B  * (*)() ) this->vt[0])(this);
//
    public void genC( PW pw, boolean putParenthesis, String className ) {
        if (putParenthesis){
            pw.print("(");
        }

        if (this.instanceVariable != null && this.messageName == null){
            pw.print("this");
            pw.print("->_"+className+"_"+ this.instanceVariable.getName());

        }

        if (this.messageName != null) {

            if (this.method.isPrivate()){
                pw.print("_"+this.methodClass.getName()+"_"+this.method.getName()+"(this");
                this.exprList.genC(pw, className, true);
                pw.print(")");
            }else {
                if (this.method.getType() == Type.stringType) {
                    pw.print("( ( ( char * (*) (_class_" + this.methodClass.getName() + " *");
                } else if (this.method.getType() instanceof KraClass) {
                    if (this.exprList.getSize() == 0){
                        pw.print("( ( ( _class_"+this.method.getType().getName()+" * (*) (");
                    }else{
                        pw.print("( ( ( _class_"+this.method.getType().getName()+" * (*) (_class_" + this.methodClass.getName() + " *");
                    }
                }else{
                    pw.print("( ( ( " + this.method.getType().getName() + " (*) (_class_" + this.methodClass.getName() + " *");
                }
                this.exprList.genTypeNameC(pw, true, this.method);
                if (this.instanceVariable != null && this.messageName != null){
                    pw.print(") ) this->_"+className+"_"+this.instanceVariable.getName()+"->");
                }else{
                    pw.print(") ) this->");
                }
                pw.print("vt[_enum_" + this.methodClass.getName() + "_");
                if ((!(methodClass.getName().equals(className))) && this.methodClass.searchSuperClassName(className)) {
                    pw.print(className + "_");
                }
                pw.print(this.method.getName() + "] )( (_class_" + this.methodClass.getName() + " *) this ");
                this.exprList.genC(pw, className, true);
                pw.print(") )");
            }
        }

        if(putParenthesis) {
            pw.print(")");
        }
    }
}