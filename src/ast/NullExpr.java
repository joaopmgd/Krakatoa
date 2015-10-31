package ast;

public class NullExpr extends Expr {
    
   public void genC( PW pw, boolean putParenthesis, String className ) {
      pw.print("NULL");
   }

   public Type getType() {
      return type;
   }

   public NullExpr (){
      this.type = Type.nullType;
   }

   @Override
   public void genKra(PW pw, boolean putParenthesis) {
      pw.print("null");
   }

   private Type type;
}