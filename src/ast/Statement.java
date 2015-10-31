package ast;

abstract public class Statement {

	abstract public void genC(PW pw, String className, boolean isStatic, String methodName);

	abstract public void genKra(PW pw);

}
