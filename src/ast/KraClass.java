package ast;
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

   public int validateProgram(){
      if (!this.getName().equals("Program")){
         return 1;
      }
      Method run = this.getNameMethod("run");
      if (run == null) {
         return 2;
      }
      return 0;
   }

   public boolean isStatic() {
      return isStatic;
   }

   public boolean isFinal() {
      return isFinal;
   }

   public Type getMethodType(String methodName, TypeList typeList, boolean isStatic,boolean superclass){
      Method method = null;
      if (!superclass) {
         method = privateMethodList.search(methodName, typeList, isStatic);
      }
      if (method == null){
         method = publicMethodList.search(methodName, typeList, isStatic);
      }
      return method.getType();
   }

   public Method getMessageMethod(String methodName, TypeList typeList, boolean isStatic,boolean superclass){
      Method method = null;
      if (!superclass) {
         method = privateMethodList.search(methodName, typeList, isStatic);
      }
      if (method == null){
         method = publicMethodList.search(methodName, typeList, isStatic);
      }
      return method;
   }

   public Method getMethodDeclaration(Type type, String name, TypeList typelist){
      return publicMethodList.searchDeclaration(type, name, typelist, false, true);
   }

   public Method getMethod(String name, TypeList typelist){
      return publicMethodList.search(name, typelist, false);
   }

   public Method getNameMethod (String run){
      Method method = (privateMethodList.searchNameMethod(run));
      if (method == null){
         method = publicMethodList.searchNameMethod(run);
      }
      return method;
   }

   public Method getMethodFromThis(String name, TypeList typelist){
      Method method = (privateMethodList.search(name, typelist, false));
      if (method == null){
         method = publicMethodList.search(name, typelist, false);
      }
      return method;
   }

   public KraClass searchMethods(String name, TypeList typeList, boolean isStatic, boolean superclass){

      if(!superclass){
         if (privateMethodList.search(name, typeList, isStatic) != null){
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

   public KraClass searchDeclarationMethods(Type type, String name, TypeList typeList, boolean isStatic, boolean superclass){
      if(!superclass){
         if (privateMethodList.searchDeclaration(type, name, typeList, isStatic, superclass) != null){
            return this;
         }
      }
      if (publicMethodList.searchDeclaration(type, name, typeList, isStatic, superclass) != null){
         return this;
      }
      if (this.superClass != null && !isStatic){
         return this.superClass.searchDeclarationMethods(type, name, typeList, isStatic, true);
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

   public KraClass getSuperclass() {
      return superClass;
   }

   public void setSuperclass(KraClass superclass) {
      this.superClass = superclass;
   }

   public InstanceVariableList getInstanceVariableList() {
      return instanceVariableList;
   }

   public void addInstanceVariableList(InstanceVariableList instanceVariableList){
      this.instanceVariableList.addInstanceVariableList(instanceVariableList);
   }


   public void addPublicMethod(Method method){
      this.publicMethodList.addElement(method);
   }

   public void addPrivateMethod(Method method){
      this.privateMethodList.addElement(method);
   }

   public void genKra(PW pw){
      if(this.isFinal)
         pw.print("final ");
      if(this.isStatic)
         pw.print("static ");

      pw.print("class ");
      pw.print(this.getName());

      if(this.superClass != null)
         pw.print(" extends " + this.superClass.getName());

      pw.println(" {");
      pw.println("");
      pw.add();
      this.instanceVariableList.genKra(pw);
      this.privateMethodList.genKra(pw);
      this.publicMethodList.genKra(pw);
      pw.sub();
      pw.println("");
      pw.println("}");
      pw.println("");
   }

   public EnumList genEnum(String className){
      EnumList enumList = new EnumList();
      enumList.getMethodName(className, this.publicMethodList);
      if (this.superClass != null){
         enumList = this.superClass.genEnumSuper(enumList, className, this.superClass.getName());
      }
      return enumList;
   }

   public EnumList genEnumSuper(EnumList enumList, String className, String superClassName){
      EnumList newEnumList = new EnumList();
      newEnumList.getMethodNameSuper(enumList, className, superClassName, this.publicMethodList);
      enumList.addAll(newEnumList);
      if (this.superClass != null){
         return this.superClass.genEnumSuper(enumList, className, this.superClass.getName());
      }
      return enumList;
   }

   public void genC(PW pw) {
      pw.printlnIdent("typedef struct _class_"+ this.getName() +" _class_"+ this.getName() +";\n");
      pw.printlnIdent("struct _class_"+ this.getName() +"{");
      pw.add();
      pw.printlnIdent("Func *vt;");
      this.instanceVariableList.genC(pw, this.getName(), false);
      pw.sub();
      pw.printlnIdent("};\n");
      pw.printlnIdent("_class_" + this.getName() + " *new_" + this.getName() + "(void);\n");
      this.instanceVariableList.genC(pw, this.getName(), true);

      EnumList enumList = this.genEnum(this.getName());
      if (enumList.size() != 0){
         pw.printIdent("typedef enum {");
         enumList.genCEnum(pw);
         pw.println("} _class_" + this.getName() + "_methods;\n");
      }

      this.privateMethodList.genC(pw, this.getName());
      this.publicMethodList.genC(pw, this.getName());

      pw.printlnIdent("Func VTclass_" + this.getName() + "[] = {");
      if (enumList.size() != 0){
         pw.add();
         enumList.genCVT(pw);
         pw.sub();
      }
      pw.printlnIdent("\n};\n");

      pw.printlnIdent("_class_" + this.getName() + " *new_" + this.getName() + "(){");
      pw.add();
      pw.printlnIdent("_class_" + this.getName() + " *t;");
      pw.printlnIdent("if ( (t = malloc(sizeof(_class_" + this.getName() + "))) != NULL )");
      pw.add();
      pw.printlnIdent("t->vt = VTclass_" + this.getName() + ";");
      pw.sub();
      pw.printlnIdent("return t;");
      pw.sub();
      pw.printlnIdent("}\n");
   }
}