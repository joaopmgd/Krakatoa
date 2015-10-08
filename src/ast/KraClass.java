package ast;

import java.util.Currency;

/*
 * Krakatoa Class
 */
public class KraClass extends Type {

   private KraClass superClass;
   private boolean isStatic;
   private boolean isFinal;
   private InstanceVariableList instanceVariableList;
   private PublicMethodList publicMethodList;
   private PrivateMethodList privateMethodList;

   public KraClass( String name, boolean isFinal, boolean isStatic ) {
      super(name);
      this.isFinal = isFinal;
      this.isStatic = isStatic;
      this.superClass = null;
      this.instanceVariableList = new InstanceVariableList();
      this.publicMethodList = new PublicMethodList();
      this.privateMethodList = new PrivateMethodList();
   }

   public boolean isStatic() {
      return isStatic;
   }

   public boolean isFinal() {
      return isFinal;
   }

   public Type getMethodType(String methodName, TypeList typeList, boolean superclass){
      Method method = null;
      if (!superclass) {
         method = privateMethodList.search(methodName, typeList, isStatic);
      }
      if (method == null){
         method = publicMethodList.search(methodName, typeList, isStatic);
      }
      return method.getType();
   }

   public Method getMethod(String name, TypeList typelist){
      return publicMethodList.search(name, typelist, false);
   }

   public KraClass searchMethods(String name, TypeList typeList, boolean isStatic, boolean superclass){

      if(!superclass){
         if (privateMethodList.search(name,typeList, isStatic) != null){
            return this;
         }
      }
      if (publicMethodList.search(name, typeList, isStatic) != null){
         return this;
      }
      if (this.superClass != null && !isStatic){
         return this.superClass.searchMethods(name, typeList, isStatic, true);
      }
      return null;
   }

   public boolean compareCurrentMethod(String name, TypeList typeList, boolean isStatic,Method currentMethod){

      if (currentMethod.getName().equals(name)){
         if (isStatic && currentMethod.isStatic()){
            return true;
         }else if (typeList.compareTo(currentMethod.getParamList().getTypeList()) == 0) {
            return true;
         }
      }
      return false;
   }

   public boolean searchSuperClassName (String name){
      if (superClass != null && superClass.getName().equals(name)){
         return true;
      }else if (superClass != null){
         return superClass.searchSuperClassName(name);
      }else{
         return false;
      }
   }

   public boolean searchSuperClass (KraClass suposedSuperClass){
      if (this.superClass != null){
         if (superClass.getName().equals(suposedSuperClass.getName())){
            return true;
         }else{
            return searchSuperClass(this.superClass);
         }
      }
      return false;
   }

   public String getCname() {
      return getName();
   }

   @Override
   public String getName() {
      return super.getName();
   }

   public String getSuperClassName(){
      return superClass.getName();
   }

   public KraClass getSuperclass() {
      return superClass;
   }

   public void setSuperclass(KraClass superclass) {
      this.superClass = superclass;
   }

   public InstanceVariableList getInstanceVariableList() {
      return instanceVariableList;
   }

   public void setInstanceVariableList(InstanceVariableList instanceVariableList) {
      this.instanceVariableList = instanceVariableList;
   }

   public void addInstanceVariableList(InstanceVariableList instanceVariableList){
      this.instanceVariableList.addInstanceVariableList(instanceVariableList);
   }

   public PublicMethodList getPublicMethodList() {
      return publicMethodList;
   }

   public void setPublicMethodList(PublicMethodList publicMethodList) {
      this.publicMethodList = publicMethodList;
   }

   public void addPublicMethod(Method method){
      this.publicMethodList.addElement(method);
   }

   public PrivateMethodList getPrivateMethodList() {
      return privateMethodList;
   }

   public void setPrivateMethodList(PrivateMethodList privateMethodList) {
      this.privateMethodList = privateMethodList;
   }

   public void addPrivateMethod(Method method){
      this.privateMethodList.addElement(method);
   }


}
