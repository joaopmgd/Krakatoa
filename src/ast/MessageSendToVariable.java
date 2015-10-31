package ast;


public class MessageSendToVariable extends MessageSend {

    private String firstId;
    private String identifier;
    private String messageName;
    private ExprList exprList;
    private KraClass kraClass;
    private Type instanceVariableType;
    private Type methodType;
    private KraClass methodClass;
    private Method method;

    public Method getMethod() {
        return method;
    }

    public String getFirstId() {
        return firstId;
    }

    public String getMessageName() {
        return messageName;
    }

    public String getExprListNames() {
        return exprList.getTypeNames();
    }

    public String getIdentifier() {
        return identifier;
    }

    // Clock.currentDay;
    public MessageSendToVariable (String firstId, String identifier){
        this.firstId = firstId;
        this.identifier = identifier;
        this.messageName = null;
        this.exprList = new ExprList();
        this.instanceVariableType = null;
        this.methodType = null;
        this.methodClass = null;
        this.method = null;
    }

    public boolean validateInstance (KraClass kraClass){
        this.kraClass = kraClass;
        this.instanceVariableType =  this.kraClass.getInstanceVariableList().searchStaticInstance(this.identifier);
        return (instanceVariableType != null);
    }

    // object.setDay(12);
    public MessageSendToVariable (String firstId, String messageName, ExprList exprList){
        this.firstId = firstId;
        this.identifier = null;
        this.messageName = messageName;
        if (exprList == null){
            this.exprList = new ExprList();
        }else{
            this.exprList = exprList;
        }
        this.instanceVariableType = null;
        this.methodType = null;
        this.methodClass = null;
        this.method = null;
    }

    // Clock.currentDay.setDay(12);
    public MessageSendToVariable (String firstId, String identifier, String messageName, ExprList exprList){
        this.firstId = firstId;
        this.identifier = identifier;
        this.messageName = messageName;
        if (exprList == null){
            this.exprList = new ExprList();
        }else{
            this.exprList = exprList;
        }
        this.instanceVariableType = null;
        this.methodType = null;
        this.methodClass = null;
        this.method = null;
    }

//    ok
    public boolean validateMethodMessage (KraClass kraClass, KraClass classFromVariable, Method currentMethod, String currentClass){

        if (classFromVariable != null){
            this.kraClass = classFromVariable;
            this.methodClass = this.kraClass.searchMethods(this.messageName, this.exprList.getTypeList(), false, true);
            if ( methodClass != null) {
                this.methodType = this.methodClass.getMethodType(this.messageName, this.exprList.getTypeList(), false, true);
                this.method = this.methodClass.getMessageMethod(this.messageName, this.exprList.getTypeList(), false, true);
                return true;
            }else{
                if (this.kraClass.compareCurrentMethod(this.messageName,this.exprList.getTypeList(),false,currentMethod)){
                    this.methodType = currentMethod.getType();
                    this.method = currentMethod;
                    return true;
                }
                return false;
            }
        }else if (kraClass != null) {
            boolean superClass = true;
            if (kraClass.getName().equals(currentClass)){
                superClass = false;
            }
            this.kraClass = kraClass;
            this.methodClass = this.kraClass.searchMethods(this.messageName, this.exprList.getTypeList(), true, superClass);
            if (methodClass != null) {
                this.methodType = this.methodClass.getMethodType(this.messageName, this.exprList.getTypeList(), true, false);
                this.method = this.methodClass.getMessageMethod(this.messageName, this.exprList.getTypeList(), true, false);
                return true;
            } else {
                if (this.kraClass.compareCurrentMethod(this.messageName, this.exprList.getTypeList(), false, currentMethod)) {
                    this.methodType = currentMethod.getType();
                    this.method = currentMethod;
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public boolean validateIndentifierMessage (){
        KraClass superClass = kraClass.searchMethods(messageName, exprList.getTypeList(), false, false);
        if (superClass != kraClass && superClass != null){
            this.methodClass = kraClass;
            this.methodType = superClass.getMethodType(this.messageName,this.exprList.getTypeList(),false,true);
            this.method = superClass.getMessageMethod(this.messageName, this.exprList.getTypeList(), false, true);
        }else if (superClass != null){
            this.methodType = kraClass.getMethodType(this.messageName,this.exprList.getTypeList(),false,true);
            this.method = kraClass.getMessageMethod(this.messageName, this.exprList.getTypeList(), false, true);
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

    @Override
    public void genKra(PW pw, boolean putParenthesis) {
        //id.
        pw.print(this.firstId);
        pw.print(".");

        //id.message( exprList )
        if(this.identifier == null){
            pw.print(this.messageName);
            pw.print("(");
            exprList.genKra(pw);
            pw.print(")");
            return;
        }

        //id.id
        pw.print(this.identifier);

        //id.id.message( exprList )
        if(this.messageName != null){
            pw.print(".");
            pw.print(messageName);
            pw.print("(");
            this.exprList.genKra(pw);
            pw.print(")");
        }

    }

//    ( ( ( void (*)(_class_A *, int ) ) _A->_staticAnA->vt[_enum_A_setAnInt] )( _A, 1) );
//    ( ( ( void (*)( int ) ) _static_A_staticAnA->vt[_enum_A_setAnInt] )( 1) );
//    ( ( ( void (*)(int ) )  _static_A_staticAnA->vt[_enum_A_setAnInt] )( _A, 1) );
    public void genC( PW pw, boolean putParenthesis, String className ) {
        if (methodType == null){
            pw.print("_static_"+kraClass.getName()+"_"+this.identifier);
        }else if (this.method.isStatic()) {
            pw.print("_static_"+this.kraClass.getName() + "_" + this.method.getName()+"(");
            this.exprList.genC(pw, className, false);
            pw.print(")");
        }else{
            if (this.methodType == Type.stringType){
                pw.print("( ( ( char * (*)(_class_" + kraClass.getName() + " *");
                this.exprList.genTypeNameC(pw, true, method);
                pw.print(" ) ) _" + this.firstId + "->");
            }else if (this.methodType instanceof KraClass){
                pw.print("( ( ( _class_"+this.methodType.getName()+" * (*)(_class_" + kraClass.getName() + " *");
                this.exprList.genTypeNameC(pw, true, method);
                pw.print(" ) ) _" + this.firstId + "->");
            }else if (this.identifier != null && this.messageName != null){
                pw.print("( ( ( " + this.methodType.getName() + " (*)( ");
                this.exprList.genTypeNameC(pw, false, method);
                pw.print(" ) ) _static_"+this.kraClass.getName()+"_"+ this.identifier + "->");
            }else{
                pw.print("( ( ( " + this.methodType.getName() + " (*)(_class_" + kraClass.getName() + " *");
                this.exprList.genTypeNameC(pw, true, method);
                pw.print(" ) ) _" + this.firstId + "->");
            }
            //id.message( exprList )
            if (this.identifier == null) {
                if (this.methodClass == null){
                    pw.print("vt[_enum_" + className + "_");
                }else{
                    pw.print("vt[_enum_" + this.methodClass.getName() + "_");
                    if (!(this.methodClass.getName().equals(kraClass.getName()))) {
                        pw.print(kraClass.getName() + "_");
                    }
                }
                pw.print(this.messageName + "] )( _" + this.firstId);
                this.exprList.genC(pw, className, true);
                pw.print(") )");
                return;
            }

            //id.id.message( exprList )
            if (this.messageName != null) {

                if (this.identifier != null && this.messageName != null){
                    pw.print("vt[_enum_");
                }else{
                    pw.print("_" + this.identifier);
                    pw.print("->vt[_enum_");
                }
                if (this.methodClass == null){
                    pw.print(className + "_");
                }else{
                    pw.print(this.methodClass.getName() + "_");
                }
                if (this.identifier != null && this.messageName != null){
                    pw.print(this.messageName + "] )(");
                    this.exprList.genC(pw, className, false);
                }else{
                    pw.print(this.messageName + "] )( _" + this.firstId);
                    this.exprList.genC(pw, className, true);
                }
                pw.print(") )");
            }
        }
    }
}