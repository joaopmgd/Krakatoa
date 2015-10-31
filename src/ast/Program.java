package ast;

import java.util.*;
import comp.CompilationError;

public class Program {

	public Program(ArrayList<KraClass> classList, ArrayList<MetaobjectCall> metaobjectCallList, ArrayList<CompilationError> compilationErrorList) {
		this.classList = classList;
		this.metaobjectCallList = metaobjectCallList;
		this.compilationErrorList = compilationErrorList;
	}


	public void genKra(PW pw) {
		for(KraClass c: classList)
			c.genKra(pw);
	}

	public void genC(PW pw) {
		pw.println("#include <stdlib.h>");
		pw.println("#include <string.h>");
		pw.println("#include <stdio.h>\n");

		pw.println("typedef int boolean;");
		pw.println("#define true  1");
		pw.println("#define false 0\n");

		pw.println("typedef void (*Func)();\n");

		for (KraClass c: classList){
			c.genC(pw);
		}

		pw.printlnIdent("int main() {");
		pw.add();
		pw.printlnIdent("_class_Program *program;");
		pw.printlnIdent("program = new_Program();");
		pw.printlnIdent("( ( void (*)(_class_Program *) ) program->vt[0] )(program);");
		pw.printlnIdent("return 0;");
		pw.sub();
		pw.printIdent("}");
	}

	public ArrayList<MetaobjectCall> getMetaobjectCallList() {
		return metaobjectCallList;
	}
	

	public boolean hasCompilationErrors() {
		return compilationErrorList != null && compilationErrorList.size() > 0 ;
	}

	public ArrayList<CompilationError> getCompilationErrorList() {
		return compilationErrorList;
	}

	
	private ArrayList<KraClass> classList;
	private ArrayList<MetaobjectCall> metaobjectCallList;
	
	ArrayList<CompilationError> compilationErrorList;

	
}