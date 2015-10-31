package ast;

/**
 * Created by joao on 30/09/15.
 */
public class AssignmentStatement extends Statement {

    private Expr leftExpr, rightExpr;

    public AssignmentStatement (Expr leftExpr, Expr rightExpr){
        this.leftExpr = leftExpr;
        this.rightExpr = rightExpr;
    }


    public void genKra(PW pw){
        pw.printIdent("");
        this.leftExpr.genKra(pw, false);
        if(this.rightExpr != null){
            pw.print(" = ");
            this.rightExpr.genKra(pw, false);
        }
        pw.println(";");
    }

    @Override
    public void genC(PW pw, String className, boolean isStatic, String methodName) {
        if (this.leftExpr.getType() == Type.stringType) {
            pw.print("strcpy(");
            this.leftExpr.genC(pw, false, className);
            pw.print(",");
            this.rightExpr.genC(pw, false, className);
            pw.println(");");
        } if ((this.leftExpr instanceof MessageSendToSelf || this.leftExpr instanceof MessageSendToVariable)
                && (this.rightExpr instanceof ObjectExpr && ((ObjectExpr)this.rightExpr).isNew())) {
            if (this.leftExpr instanceof MessageSendToSelf){
                pw.print("*");
            }
            this.leftExpr.genC(pw, false, className);
            pw.print(" = *");
            this.rightExpr.genC(pw, false, className);
            pw.println(";");
        }else if ((this.rightExpr instanceof MessageSendToSelf && ((MessageSendToSelf)this.rightExpr).getMessageName() != null && ((MessageSendToSelf)this.rightExpr).getMethod().getType() instanceof KraClass )){
            if (((MessageSendToSelf) this.rightExpr).getMethod().getType().getName().equals(this.leftExpr.getType().getName())){
                pw.print("*");
                this.leftExpr.genC(pw, false, className);
                pw.print(" = ");
                this.rightExpr.genC(pw, false, className);
                pw.println(";");
            }else{
                this.leftExpr.genC(pw, false, className);
                pw.print(" = ");
                pw.print("(_class_" + leftExpr.getType().getName() + "*)");
                this.rightExpr.genC(pw, false, className);
                pw.println(";");
            }
        }else if ((this.rightExpr instanceof MessageSendToVariable && ((MessageSendToVariable)this.rightExpr).getMessageName() != null && ((MessageSendToVariable)this.rightExpr).getMethod().getType() instanceof KraClass )){
            if (((MessageSendToVariable) this.rightExpr).getMethod().getType().getName().equals(this.leftExpr.getType().getName()) && ((MessageSendToVariable) this.rightExpr).getMethod().isStatic()){
                pw.print("*");
                this.leftExpr.genC(pw, false, className);
                pw.print(" = ");
                this.rightExpr.genC(pw, false, className);
                pw.println(";");
            }else{
                this.leftExpr.genC(pw, false, className);
                pw.print(" = ");
                pw.print("(_class_" + leftExpr.getType().getName() + "*)");
                this.rightExpr.genC(pw, false, className);
                pw.println(";");
            }
        }else{
            this.leftExpr.genC(pw, false, className);
            if (this.rightExpr != null) {
                pw.print(" = ");
                if ((rightExpr.getType() instanceof KraClass && leftExpr.getType() instanceof KraClass) && (leftExpr instanceof VariableExpr) && (!(leftExpr.getType().getName().equals(rightExpr.getType().getName())))) {
                    pw.print("(_class_" + leftExpr.getType().getName() + "*)");
                    this.rightExpr.genC(pw, false, className);
                } else {
//                    if (isStatic && this.rightExpr.getType() instanceof KraClass) {
//                    OK-GER20.KRA
//                        pw.print("*");
//                    }
                    this.rightExpr.genC(pw, false, className);
                }
            }
            pw.println(";");
        }
    }
}
