package ast;
import lexer.*;

/**
 * Created by joao on 29/09/15.
 */
public class Method {

    private Type type;
    private String name;
    private boolean isStatic;
    private boolean isFinal;
    private ParamList paramList;
    private LocalVariableList localVariableList;
    private StatementList statementList;
    private boolean hasReturn;
    private boolean classIsFinal;
    private boolean isPrivate;
    private boolean isPublic;

    public Method(Type type, String name, boolean isStatic, boolean isFinal, ParamList paramList, boolean classIsFinal, Symbol qualifier){
        this.type = type;
        this.name = name;
        this.isStatic = isStatic;
        this.isFinal = isFinal;
        this.paramList = paramList;
        if (qualifier == Symbol.PRIVATE){
            this.isPrivate = true;
            this.isPublic = false;
        }else{
            this.isPrivate = false;
            this.isPublic = true;
        }
        this.statementList = new StatementList();
        this.hasReturn = false;
        this.classIsFinal = classIsFinal;
        this.localVariableList = new LocalVariableList();
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public boolean checkReturnType(Statement statement){
        if (statement instanceof ReturnStatement && ((ReturnStatement) statement).getReturnType().getName().equals(this.type.getName())){
            hasReturn = true;
            return true;
        }else if (statement instanceof ReturnStatement && ((ReturnStatement) statement).getReturnType() instanceof KraClass){
            KraClass kraClass = ((KraClass)((ReturnStatement) statement).getReturnType()).getSuperclass();
            return (verifySuperClassType(kraClass,this.type.getName()));
        }
        return false;
    }

    public boolean verifySuperClassType(KraClass kraClass, String name){
        if (kraClass == null){
            return false;
        }
        else if (kraClass.getName().equals(name)){
            return true;
        }else if (kraClass.getSuperclass() != null){
            return verifySuperClassType(kraClass.getSuperclass(),name);
        }else{
            return false;
        }
    }

    public boolean hasReturn() {
        return hasReturn;
    }

    public String getName() {
        return name;
    }

    public ParamList getParamList() {
        return paramList;
    }

    public Type getType(){
        return this.type;
    }

    public void setStatementList(StatementList statementList) {
        if (statementList == null){
            this.statementList = new StatementList();
        }
        this.statementList = statementList;
    }

    public void genKra(PW pw, String scope) {

		pw.printIdent("");
        if(this.isFinal && !this.classIsFinal)
            pw.print("final ");
        if(this.isStatic)
            pw.print("static ");

        pw.print(scope);
        pw.print(" ");
        pw.print(this.type.getName());
        pw.print(" ");
        pw.print(this.name);
        pw.print("(");

        if(this.paramList != null)
            this.paramList.genKra(pw);

        pw.println(") {");
        pw.add();

        if(this.localVariableList != null)
            this.localVariableList.genKra(pw);

        if(this.statementList != null)
            this.statementList.genKra(pw);
        pw.sub();
        pw.printlnIdent("}");

    }

    public void genC(PW pw, String className){
        if (this.type == Type.stringType){
            pw.printIdent("char * _");
        }else if (this.type instanceof KraClass){
            pw.printIdent("_class_"+this.type.getName() + " _");
        }else{
            pw.printIdent(this.type.getName() + " _");
        }
        if (this.isStatic){
            pw.print("static_");
        }
        pw.print(className + "_" + this.name + "(");
        if (!this.isStatic){
            pw.print(" _class_"+className+" *this");
            this.paramList.genC(pw,true, this.isStatic());
        }else{
            this.paramList.genC(pw,false, this.isStatic());
        }
        pw.println(" ){");
        pw.add();
        this.localVariableList.genC(pw);
        if (this.statementList.searchForRead()){
            pw.printlnIdent("char __s[512];");
        }
        this.statementList.genC(pw,className, this.isStatic(), this.type.getName());
        pw.sub();
        pw.printlnIdent("}\n");
    }

    public void genMethodNameC(PW pw, String className){
        pw.printIdent("( void (*)() ) _" + className + "_" + this.getName());
    }
}
