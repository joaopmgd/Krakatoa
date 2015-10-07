package ast;

public class NullExpr extends Expr {
    
   public void genC( PW pw, boolean putParenthesis ) {
      pw.printIdent("NULL");
   }

   public Type getType() {
      return type;
   }

   public void setType(Type type){
      this.type = type;
   }

   public NullExpr (){
      this.type = null;
   }

   private Type type;
}