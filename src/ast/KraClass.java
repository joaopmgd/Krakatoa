package ast;

import javax.swing.plaf.nimbus.State;
import java.util.ArrayList;

/*
 * Krakatoa Class
 */
public class KraClass extends Type {

   private String name;
   private KraClass superclass;
   private boolean still;
   private boolean finale;
   private InstanceVariableList instanceVariableList;
   private PublicMethodList publicMethodList;
   private PrivateMethodList privateMethodList;

   public KraClass( String name, boolean finale, boolean still ) {
      super(name);
      this.finale = finale;
      this.still = still;
      this.superclass = null;
      this.instanceVariableList = new InstanceVariableList();
      this.publicMethodList = new PublicMethodList();
      this.privateMethodList = new PrivateMethodList();
   }

   public boolean isStill() {
      return still;
   }

   public boolean isFinale() {
      return finale;
   }

   public boolean searchMethods(String name, ParamList paramList, boolean superclass){
      if (publicMethodList.search(name, paramList)){
         return true;
      }
      if(!superclass){
         if (privateMethodList.search(name,paramList)){
            return true;
         }
      }
      return false;
   }

   public String getCname() {
      return getName();
   }

   @Override
   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getSuperClassName(){
      return superclass.getName();
   }

   public KraClass getSuperclass() {
      return superclass;
   }

   public void setSuperclass(KraClass superclass) {
      this.superclass = superclass;
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
