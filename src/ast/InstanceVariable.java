package ast;

public class InstanceVariable extends Variable {

    private boolean isStatic;
    private boolean isFinal;

    public boolean isStatic() {
        return isStatic;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public InstanceVariable( Type type, String name, boolean isStatic, boolean isFinal ) {
        super(name, type);
        this.isStatic = isStatic;
        this.isFinal = isFinal;
    }

    public void genKra(PW pw) {
        pw.printIdent("");
        if(this.isFinal)
            pw.print("final ");
        if(this.isStatic)
            pw.print("static ");
        pw.print("private ");
        pw.print(this.getType().getName());
        pw.print(" ");
        pw.print(this.getName());
        pw.println(";");
    }
}