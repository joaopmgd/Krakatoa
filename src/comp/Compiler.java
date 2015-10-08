package comp;

import ast.*;
import lexer.*;

import java.io.*;
import java.util.*;

public class Compiler {

	// compile must receive an input with an character less than
	// p_input.lenght
	public Program compile(char[] input, PrintWriter outError) {

		ArrayList<CompilationError> compilationErrorList = new ArrayList<>();
		signalError = new SignalError(outError, compilationErrorList);
		symbolTable = new SymbolTable();
		lexer = new Lexer(input, signalError);
		signalError.setLexer(lexer);

		lexer.nextToken();
		return program(compilationErrorList);
	}

	// declaração do program foi para o fim do metodo para a incrementação da lista de kraclass
	private Program program(ArrayList<CompilationError> compilationErrorList) {
		// Program ::= KraClass { KraClass }
		ArrayList<MetaobjectCall> metaobjectCallList = new ArrayList<>();
		ArrayList<KraClass> kraClassList = new ArrayList<>();
		try {
			while (lexer.token == Symbol.MOCall) {
				metaobjectCallList.add(metaobjectCall());
			}
			if ( lexer.token == Symbol.RIGHTPAR )
				lexer.nextToken();
			kraClassList.add(classDec());
			while (lexer.token == Symbol.CLASS || lexer.token == Symbol.FINAL || lexer.token == Symbol.STATIC) {
				kraClassList.add(classDec());
			}
			if (lexer.token != Symbol.EOF) {
				signalError.show("'class' expected");
			}
		} catch (RuntimeException e) {
			for(CompilationError compilationError: compilationErrorList){
//				System.out.print("\nMensagem Correta : "+metaobjectCallList.get(0).getParamList().get(2) + " ");
//				System.out.println(metaobjectCallList.get(0).getParamList().get(3));
				System.out.println("\nMensagem Compiler: " + compilationError.getMessage() + "\n\nLinha do Código: " + compilationError.getLineWithError() + "\n\nNúmero da Linha: " + compilationError.getLineNumber() + "\n");
			}
		}
		return new Program(kraClassList, metaobjectCallList, compilationErrorList);
	}

	@SuppressWarnings("incomplete-switch")
	private MetaobjectCall metaobjectCall() {
		String name = lexer.getMetaobjectName();
		lexer.nextToken();
		ArrayList<Object> metaobjectParamList = new ArrayList<>();
		if ( lexer.token == Symbol.LEFTPAR ) {
			// metaobject call with parameters
			lexer.nextToken();
			while ( lexer.token == Symbol.LITERALINT || lexer.token == Symbol.LITERALSTRING ||
					lexer.token == Symbol.IDENT ) {
				switch ( lexer.token ) {
				case LITERALINT:
					metaobjectParamList.add(lexer.getNumberValue());
					break;
				case LITERALSTRING:
					metaobjectParamList.add(lexer.getLiteralStringValue());
					break;
				case IDENT:
					metaobjectParamList.add(lexer.getStringValue());
				}
				lexer.nextToken();
				if ( lexer.token == Symbol.COMMA ) 
					lexer.nextToken();
				else
					break;
			}
			if ( lexer.token != Symbol.RIGHTPAR ) {
				signalError.show("')' expected after metaobject call with parameters");
			}
		}
		if ( name.equals("ne") ) {
			if ( metaobjectParamList.size() != 0 )
				signalError.show("Metaobject 'nce' does not take parameters");
		}
		else if ( name.equals("ce") ) {
			if ( metaobjectParamList.size() != 3 && metaobjectParamList.size() != 4 )
				signalError.show("Metaobject 'ce' take three or four parameters");
			if ( !( metaobjectParamList.get(0) instanceof Integer)  )
				signalError.show("The first parameter of metaobject 'ce' should be an integer number");
			if ( !( metaobjectParamList.get(1) instanceof String) ||  !( metaobjectParamList.get(2) instanceof String) )
				signalError.show("The second and third parameters of metaobject 'ce' should be literal strings");
			if ( metaobjectParamList.size() >= 4 && !( metaobjectParamList.get(3) instanceof String) )  
				signalError.show("The fourth parameter of metaobject 'ce' should be a literal string");
		}
		return new MetaobjectCall(name, metaobjectParamList);
	}

//	classdec agora retorna uma kraclass, no fim agora faz update no symboltable
//	verifica o extends se a classe existe e se é diferente da classe atual que esta sendo declarada
//	verifica o static e final em qualquer ordem apenas uma vez

//	KraClass ::= [ "final" ] [ "static" ] ``class'' Id [ ``extends'' Id ] "{" MemberList "}"
//	MemberList ::= { Qualifier Member }
//	Qualifier ::= [ "final" ] [ "static" ]  ( "private" | "public" )
//	Member ::= InstVarDec | MethodDec

	private KraClass classDec() {

		boolean classIsStatic = false, classIsFinal = false;
		while (lexer.token == Symbol.STATIC || lexer.token == Symbol.FINAL) {
			switch (lexer.token){
				case STATIC:
					if (!classIsStatic){
						classIsStatic = true;
					}else{
						signalError.show("'static' has already been declared");
					}
					break;
				case FINAL:
					if (!classIsFinal){
						classIsFinal = true;
					}else{
						signalError.show("'final' has already been declared");
					}
					break;
			}
			lexer.nextToken();
		}
		if ( lexer.token != Symbol.CLASS )
			signalError.show("'class' expected");
		lexer.nextToken();
		if ( lexer.token != Symbol.IDENT )
			signalError.show(SignalError.ident_expected);
		String className = lexer.getStringValue();
		if (symbolTable.getInGlobal(className) != null){
			signalError.show("'"+className+"' has already been declared");
		}
		KraClass kraClass = new KraClass(className,classIsFinal,classIsStatic);
		symbolTable.putInGlobal(className, kraClass);
		lexer.nextToken();
		if ( lexer.token == Symbol.EXTENDS ) {
			lexer.nextToken();
			if ( lexer.token != Symbol.IDENT )
				signalError.show(SignalError.ident_expected);
			String superclassName = lexer.getStringValue();
			KraClass superclass = symbolTable.getInGlobal(superclassName);
			if(superclass == null){
				signalError.show("'"+superclassName+"' was not declared");
			}
			if(superclass != null && superclass.isFinal()){
				signalError.show("'"+superclassName+"' is 'final' and can not be extended");
			}
			if(superclassName.equals(className)){
				signalError.show("class can not extend itself");
			}
			kraClass.setSuperclass(superclass);
			lexer.nextToken();
		}
		if ( lexer.token != Symbol.LEFTCURBRACKET )
			signalError.show("{ expected", true);
		currentClass = className;
		lexer.nextToken();

		while (lexer.token == Symbol.PRIVATE || lexer.token == Symbol.PUBLIC || lexer.token == Symbol.STATIC || lexer.token == Symbol.FINAL) {
			Symbol qualifier;
			boolean memberIsStatic = false, memberIsFinal = false;
			if (classIsFinal){
				memberIsFinal = true;
			}
			while (lexer.token == Symbol.STATIC || lexer.token == Symbol.FINAL) {
				switch (lexer.token){
					case STATIC:
						if (!memberIsStatic){
							memberIsStatic = true;
						}else{
							signalError.show("member is already static");
						}
						break;
					case FINAL:
						if (!memberIsFinal){
							memberIsFinal = true;
						}else{
							signalError.show("member is already final");
						}
						break;
				}
				lexer.nextToken();
			}
			switch (lexer.token) {
				case PRIVATE:
					lexer.nextToken();
					qualifier = Symbol.PRIVATE;
					break;
				case PUBLIC:
					lexer.nextToken();
					qualifier = Symbol.PUBLIC;
					break;
				default:
					signalError.show("private, or public expected");
					qualifier = Symbol.PUBLIC;
			}
			Type t = type();
			if ( lexer.token != Symbol.IDENT )
				signalError.show("Identifier expected");
			String name = lexer.getStringValue();
			lexer.nextToken();
			if ( lexer.token == Symbol.LEFTPAR )
				if(qualifier == Symbol.PRIVATE){
					if(memberIsFinal){
						signalError.show("final method must be public");
					}
					kraClass.addPrivateMethod(methodDec(t, name, memberIsStatic, memberIsFinal));
				}else{
					kraClass.addPublicMethod(methodDec(t, name, memberIsStatic, memberIsFinal));
				}
			else if ( qualifier != Symbol.PRIVATE )
				signalError.show("Attempt to declare public instance variable");
			else if (t.getName().equals("void"))
				signalError.show("Attempt to declare void instance variable");
			else
				kraClass.addInstanceVariableList(instanceVarDec(t, name, memberIsStatic, memberIsFinal));
		}
		if ( lexer.token != Symbol.RIGHTCURBRACKET )
			signalError.show("public/private or \"}\" expected");
		lexer.nextToken();
		symbolTable.removeInstanceIdent();
		return kraClass;
	}

//	InstVarDec ::= Type IdList ";"
// 	InstVarDec ::= [ "static" ] "private" Type IdList ";"
	private InstanceVariableList instanceVarDec(Type type, String name, boolean isStatic, boolean isFinal) {

		if (symbolTable.getInInstance(name) != null && symbolTable.getInInstance(name).isStatic() == isStatic){
			signalError.show(name + " has already been declared");
		}
		symbolTable.putInInstance(name, new InstanceVariable(type, name, isStatic, isFinal));
		InstanceVariableList instanceVariableList = new InstanceVariableList();
		InstanceVariable instanceVariable = new InstanceVariable(type, name, isStatic, isFinal);
		instanceVariableList.addElement(instanceVariable);
		if (isFinal) {
			if (lexer.token != Symbol.ASSIGN) {
				signalError.show("Final variable must be initialized");
			}
			Expr expr = expr();
			if (expr.getType() != instanceVariable.getType()){
				signalError.show("Assignment must be of the same tipe as instance variable");
			}
		}
		while (lexer.token == Symbol.COMMA) {
			lexer.nextToken();
			if ( lexer.token != Symbol.IDENT )
				signalError.show("Identifier expected");
			String variableName = lexer.getStringValue();
			instanceVariableList.addElement(new InstanceVariable(type, variableName, isStatic, isFinal));
			lexer.nextToken();
		}
		if ( lexer.token != Symbol.SEMICOLON )
			signalError.show(SignalError.semicolon_expected);
		lexer.nextToken();
		return instanceVariableList;
	}

	private KraClass searchMethods(String name, ParamList paramList, boolean isStatic, String clss){
		KraClass kraClass = symbolTable.getInGlobal(clss);
		return (kraClass.searchMethods(name, paramList.getTypeList(), isStatic, false));
	}

//	MethodDec ::= Qualifier Type Id "("[ FormalParamDec ] ")" "{" StatementList "}"
	private Method methodDec(Type type, String name, boolean isStatic, boolean isFinal) {

		lexer.nextToken();
		ParamList paramList = new ParamList();
		if ( lexer.token != Symbol.RIGHTPAR ){
			paramList =  formalParamDec();
		}
		if ( lexer.token != Symbol.RIGHTPAR ){
			signalError.show(") expected");
		}
		KraClass kraClass = searchMethods(name, paramList, isStatic, currentClass);
		if ( kraClass != null ){
			Method declaredMethod = kraClass.getMethod(name, paramList.getTypeList());
			if (kraClass.getName().equals(currentClass)){
				signalError.show("Method '" + name + "' is already declared in " + currentClass);
			}else if (declaredMethod.isFinal()){
				signalError.show("Method '" + name + "' is Final and can notoverride");
			}
		}
		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTCURBRACKET ) signalError.show("{ expected");
		lexer.nextToken();
		Method method = new Method(type,name,isStatic,isFinal, paramList);
		currentMethod = method;
		StatementList statementList = statementList();
		method.setStatementList(statementList);
		if ( lexer.token != Symbol.RIGHTCURBRACKET ) signalError.show("} expected");
		if ((type == Type.voidType) && (currentMethod.hasReturn())){
			signalError.show("Void type does not have return");
		}else if ((!currentMethod.hasReturn()) && (type != Type.voidType)){
			signalError.show("Missing 'return' statement in method '"+name+"'");
		}
		lexer.nextToken();
		symbolTable.removeLocalIdent();
		return method;
	}

//	recebimento de parametro, o parametro deve ser o filho da classe declarada
//  FormalParamDec ::= ParamDec { "," ParamDec }
	private ParamList formalParamDec() {
		ParamList paramList = new ParamList();
		paramList.addElement(paramDec());
		while (lexer.token == Symbol.COMMA) {
			lexer.nextToken();
			paramList.addElement(paramDec());
		}
		return paramList;
	}

//	ParamDec ::= Type Id
	private Variable paramDec() {

		Type type = type();
		if ( lexer.token != Symbol.IDENT ){
			signalError.show("Identifier expected");
		}
		String variableName = lexer.getStringValue();
		if (symbolTable.getInLocal(variableName) != null){
			signalError.show("'"+variableName+"' has already been declared");
		}
		Variable variable = new Variable(variableName, type);
		symbolTable.putInLocal(variableName,variable);
		lexer.nextToken();
		return variable;
	}

//	Type ::= BasicType | Id
	private Type type() {
		Type result;

		switch (lexer.token) {
		case VOID:
			result = Type.voidType;
			break;
		case INT:
			result = Type.intType;
			break;
		case BOOLEAN:
			result = Type.booleanType;
			break;
		case STRING:
			result = Type.stringType;
			break;
		case IDENT:
			if (symbolTable.getInGlobal(lexer.getStringValue()) == null){
				signalError.show("Valid Identifier expected, "+lexer.getStringValue()+" is not a type");
			}
			result = symbolTable.getInGlobal(lexer.getStringValue());
			break;
		default:
			signalError.show("Valid Type expected '"+lexer.token+"' is not a Type");
			result = Type.undefinedType;
		}
		lexer.nextToken();
		return result;
	}

//	CompStatement ::= "{" { Statement } "}"
	private CompositeStatement compositeStatement() {
		lexer.nextToken();
		CompositeStatement compositeStatement =  new CompositeStatement(statementList());
		if ( lexer.token != Symbol.RIGHTCURBRACKET ) {
			signalError.show("} expected");
		}else {
			lexer.nextToken();
		}
		return compositeStatement;
	}

//	statements always begin with an identifier, if, read, write, ...
//	statementList ::= { Statement }
	private StatementList statementList() {
		Symbol tk = lexer.token;
		StatementList statementList = new StatementList();
		while (tk != Symbol.RIGHTCURBRACKET) {
			statementList.addElement(statement());
			tk = lexer.token;
		}
		return statementList;
	}

//	Statement ::= Assignment ``;'' | IfStat | WhileStat | MessageSend ``;'' |
//  ReturnStat ``;'' | ReadStat ``;'' | WriteStat ``;'' |
//  ``break''  ``;'' | ``;'' | CompStatement | LocalDec
	private Statement statement() {

		Statement statement = null;
		switch (lexer.token) {
		case THIS:
		case IDENT:
		case SUPER:
		case INT:
		case BOOLEAN:
		case STRING:
			statement = assignExprLocalDec();
			if(lexer.token != Symbol.SEMICOLON) {
				signalError.show("Expected Semicolon",true);
			}else{
				lexer.nextToken();
			}
			break;
		case RETURN:
			statement = returnStatement();
			if(lexer.token != Symbol.SEMICOLON) {
				signalError.show("Expected Semicolon",true);
			}else{
				if (!currentMethod.checkReturnType(statement))
					signalError.show("Return must be the same type as the method");
				lexer.nextToken();
			}
			break;
		case READ:
			statement = readStatement();
			if(lexer.token != Symbol.SEMICOLON) {
				signalError.show("Expected Semicolon",true);
			}else{
				lexer.nextToken();
			}
			break;
		case WRITE:
			statement = writeStatement();
			if(lexer.token != Symbol.SEMICOLON) {
				signalError.show("Expected Semicolon",true);
			}else{
				lexer.nextToken();
			}
			break;
		case WRITELN:
			statement = writelnStatement();
			if(lexer.token != Symbol.SEMICOLON) {
				signalError.show("Expected Semicolon",true);
			}else{
				lexer.nextToken();
			}
			break;
		case IF:
			statement = ifStatement();
			break;
		case BREAK:
			statement = breakStatement();
			if(lexer.token != Symbol.SEMICOLON) {
				signalError.show("Expected Semicolon",true);
			}else{
				lexer.nextToken();
			}
			break;
		case WHILE:
			statement = whileStatement();
			break;
		case SEMICOLON:
			nullStatement();
			break;
		case LEFTCURBRACKET:
			statement = compositeStatement();
			break;
		default:
			signalError.show("Statement expected");
		}
		return statement;
	}

//	retorne true se 'name' é uma classe declarada anteriormente. É necessário fazer uma busca na tabela de símbolos para isto.
	private boolean isType(String name) {
		return this.symbolTable.getInGlobal(name) != null;
	}


//	LocalDec ::= Type IdList ";"
	private DeclarationStatement localDec() {

		Type type = type();
		if ( lexer.token != Symbol.IDENT ) signalError.show("Identifier expected");
		String name = lexer.getStringValue();
		VariableList variableList = new VariableList();
		Variable variable = new Variable(name, type);
		if ( symbolTable.getInLocal(name) != null ) signalError.show("Variable already declared");
		variableList.addElement(variable);
		symbolTable.putInLocal(name, variable);
		lexer.nextToken();
		while (lexer.token == Symbol.COMMA) {
			lexer.nextToken();
			if ( lexer.token != Symbol.IDENT )
				signalError.show("Identifier expected");
			name = lexer.getStringValue();
			variable = new Variable(name, type);
			if ( symbolTable.getInLocal(name) != null ){
				signalError.show("Variable already declared");
			}
		variableList.addElement(variable);
			symbolTable.putInLocal(name,variable);
			lexer.nextToken();
		}
		return new DeclarationStatement(type, variableList);
	}

//  AssignExprLocalDec ::= Expression [ "=" Expression ] | LocalDec
	private Statement assignExprLocalDec() {

		if ( lexer.token == Symbol.INT || lexer.token == Symbol.BOOLEAN || lexer.token == Symbol.STRING ) {
			 return localDec();
		}
		else {
			if ( lexer.token == Symbol.IDENT && isType(lexer.getStringValue()) && lexer.viewNextToken() == Symbol.IDENT){
				return localDec();
			}else {
				Expr exprLeft, exprRight = null;
				exprLeft = expr();
				if (lexer.token == Symbol.ASSIGN) {
					lexer.nextToken();
					exprRight = expr();
					if (!checkAssignment(exprLeft.getType(), exprRight.getType())) {
						signalError.show("Types do not match");
					}
				}
				if(exprRight == null && (exprLeft instanceof LiteralBoolean || exprLeft instanceof LiteralInt ||
					exprLeft instanceof LiteralString || exprLeft instanceof UnaryExpr || exprLeft instanceof  NullExpr ||
					exprLeft instanceof ParenthesisExpr || exprLeft instanceof VariableExpr || exprLeft instanceof ObjectExpr)){
					signalError.show("Statement Expected");
				}
				return new AssignmentStatement(exprLeft, exprRight);
			}
		}
	}

	private boolean checkAssignment(Type typeLeft, Type typeRight){
		if (typeLeft == typeRight && typeLeft.getName().equals(typeRight.getName())){
			return true;
		}else if (typeLeft instanceof KraClass && typeRight == Type.nullType){
			return true;
		}else if (typeLeft instanceof KraClass && typeRight instanceof KraClass && ((KraClass) typeRight).searchSuperClass(((KraClass)typeLeft))){
			return true;
		}
		return false;
	}

//	whileStatement ::= "while" "(" Expression ")" statement
	private WhileStatement whileStatement() {

		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTPAR ) signalError.show("'(' expected after 'while' command");
		lexer.nextToken();
		if ( lexer.token == Symbol.RIGHTPAR ) signalError.show("Command 'while' without arguments");
		Expr expr = expr();
		if ( lexer.token != Symbol.RIGHTPAR ) signalError.show("')' expected OR Unknown sequence of symbols in 'while' statement");
		lexer.nextToken();
		Statement statement = statement();
		return new WhileStatement(expr,statement);
	}

//	ifStatement ::= "if" "(" Expression ")" Statement [ "else" statement]
	private IfStatement ifStatement() {

		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTPAR ) signalError.show("'(' expected after 'if' command");
		lexer.nextToken();
		if ( lexer.token == Symbol.RIGHTPAR ) signalError.show("Command 'if' without arguments");
		Expr expr = expr();
		if ( lexer.token != Symbol.RIGHTPAR ) signalError.show("')' expected OR Unknown sequence of symbols in 'if' statement");
		lexer.nextToken();
		Statement ifStatement = statement();
		Statement elseStatement = null;
		if ( lexer.token == Symbol.ELSE ) {
			lexer.nextToken();
			elseStatement = statement();
		}
		return new IfStatement(expr, ifStatement, elseStatement);
	}

//	returnStatement ::= "return" Expression
	private ReturnStatement returnStatement() {

		lexer.nextToken();
		Expr expr = expr();
		return new ReturnStatement(expr);
	}

//	readStatement ::= "read" "(" LeftValue { "," LeftValue } ")"
	private ReadStatement readStatement() {

		ExprList exprList = new ExprList();
		Expr expr;
		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTPAR ) signalError.show("'(' expected after 'read' command");
		lexer.nextToken();
		if ( lexer.token== Symbol.THIS ) {
			expr = factor();
			if (expr instanceof ObjectExpr){
				signalError.show("Command 'read' argument can not be 'THIS'");
			}
			exprList.addElement(expr);
		}else if (lexer.token == Symbol.IDENT){
			expr = factor();
			exprList.addElement(expr);
		}else if (lexer.token == Symbol.RIGHTPAR){
			signalError.show("Command 'read' without arguments");
		}else{
			signalError.show("Command 'read' can not accept this type o arguments");
		}
		while (lexer.token == Symbol.COMMA) {
			lexer.nextToken();
			if ( lexer.token == Symbol.THIS ) {
				expr = factor();
				if (expr instanceof ObjectExpr){
					signalError.show("Command 'read' argument can not be 'THIS'");
				}
				exprList.addElement(expr);
			}else if (lexer.token == Symbol.IDENT){
				expr = factor();
				exprList.addElement(expr);
			}else if (lexer.token == Symbol.RIGHTPAR){
				signalError.show("Command 'read' without arguments");
			}else{
				signalError.show("Command 'read' can not accept this type o arguments");
			}
		}

		if ( lexer.token != Symbol.RIGHTPAR ){
			signalError.show("')' expected OR Unknown sequence of symbols in 'read' statement");
		}
		lexer.nextToken();
		return new ReadStatement(exprList);
	}

//	writeStatement ::= "write" "(" ExpressionList ")"
	private WriteStatement writeStatement() {
		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTPAR ) signalError.show("'(' expected after 'write' command");
		lexer.nextToken();
		if ( lexer.token == Symbol.RIGHTPAR ) signalError.show("Command 'write' without arguments");
		ExprList exprList = exprList();
		if ( lexer.token != Symbol.RIGHTPAR ) signalError.show("')' expected OR Unknown sequence of symbols in 'write' statement");
		lexer.nextToken();
		return new WriteStatement(exprList);
	}

//	writelnStatement ::= "writeln" "(" ExpressionList ")"
	private WriteLnStatement writelnStatement() {

		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTPAR ) signalError.show("'(' expected after 'writeln' command");
		lexer.nextToken();
		if ( lexer.token == Symbol.RIGHTPAR ) signalError.show("Command 'writeln' without arguments");
		ExprList exprList = exprList();
		if ( lexer.token != Symbol.RIGHTPAR ) signalError.show("')' expected OR Unknown sequence of symbols in 'writeln' statement");
		lexer.nextToken();
		return new WriteLnStatement(exprList);
	}

	private BreakStatement breakStatement() {
		lexer.nextToken();
		return new BreakStatement();
	}

	private void nullStatement() {
		lexer.nextToken();
	}

// ExpressionList ::= Expression { "," Expression }
	private ExprList exprList() {

		ExprList anExprList = new ExprList();
		anExprList.addElement(expr());
		while (lexer.token == Symbol.COMMA) {
			lexer.nextToken();
			anExprList.addElement(expr());
		}
		return anExprList;
	}

//	Expression ::= SimpleExpression [ Relation  SimpleExpression]
	private Expr expr() {

		Expr left = simpleExpr();
		Symbol op = lexer.token;
		if ( op == Symbol.EQ || op == Symbol.NEQ || op == Symbol.LE || op == Symbol.LT || op == Symbol.GE || op == Symbol.GT ) {
			lexer.nextToken();
			Expr right = simpleExpr();
			left = new CompositeExpr(left, op, right);
		}
		return left;
	}

//	SimpleExpression ::= Term { LowOperator Term}
	private Expr simpleExpr() {
		Symbol op;
		Expr left = term();
		while ((op = lexer.token) == Symbol.MINUS || op == Symbol.PLUS || op == Symbol.OR) {
			lexer.nextToken();
			Expr right = term();
			left = new CompositeExpr(left, op, right);
		}
		return left;
	}

// Term ::= SignalFactor { HighOperator SignalFactor }
	private Expr term() {

		Symbol op;
		Expr left = signalFactor();
		while ((op = lexer.token) == Symbol.DIV || op == Symbol.MULT || op == Symbol.AND) {
			lexer.nextToken();
			Expr right = signalFactor();
			left = new CompositeExpr(left, op, right);
		}
		return left;
	}

//	SignalFactor ::= [ Signal ] Factor
	private Expr signalFactor() {

		Symbol op;
		if ( (op = lexer.token) == Symbol.PLUS || op == Symbol.MINUS ) {
			lexer.nextToken();
			return new SignalExpr(op, factor());
		}
		else
			return factor();
	}

	/*
	 * Factor ::= BasicValue | "(" Expression ")" | "!" Factor | "null" | ObjectCreation | PrimaryExpr
	 * 
	 * BasicValue ::= IntValue | BooleanValue | StringValue 
	 * BooleanValue ::=  "true" | "false" 
	 * ObjectCreation ::= "new" Id "(" ")" 
	 * PrimaryExpr ::= "super" "." Id "(" [ ExpressionList ] ")"  | 
	 *                 Id  |
	 *                 Id "." Id | 
	 *                 Id "." Id "(" [ ExpressionList ] ")" |
	 *                 Id "." Id "." Id "(" [ ExpressionList ] ")" |
	 *                 "this" | 
	 *                 "this" "." Id | 
	 *                 "this" "." Id "(" [ ExpressionList ] ")"  | 
	 *                 "this" "." Id "." Id "(" [ ExpressionList ] ")"
	 */
	private Expr factor() {

		Expr e;
		ExprList exprList;
		String messageName, identifier;

		switch (lexer.token) {

//		IntValue
		case LITERALINT:
			return literalInt();

//		BooleanValue
		case FALSE:
			lexer.nextToken();
			return LiteralBoolean.False;

//		BooleanValue
		case TRUE:
			lexer.nextToken();
			return LiteralBoolean.True;

//		StringValue
		case LITERALSTRING:
			String literalString = lexer.getLiteralStringValue();
			lexer.nextToken();
			return new LiteralString(literalString);

//		"(" Expression ")" |
		case LEFTPAR:
			lexer.nextToken();
			e = expr();
			if ( lexer.token != Symbol.RIGHTPAR ) signalError.show(") expected");
			lexer.nextToken();
			return new ParenthesisExpr(e);

//		"null"
		case NULL:
			lexer.nextToken();
			return new NullExpr();

//		"!" Factor
		case NOT:
			lexer.nextToken();
			e = expr();
			return new UnaryExpr(e, Symbol.NOT);

//		ObjectCreation ::= "new" Id "(" ")"
		case NEW:
			lexer.nextToken();
			if ( lexer.token != Symbol.IDENT ) {
				signalError.show("Identifier expected");
			}
			String className = lexer.getStringValue();
			KraClass kraClass = symbolTable.getInGlobal(className);
			if (kraClass == null) signalError.show(className+" was not declared");
			lexer.nextToken();
			if ( lexer.token != Symbol.LEFTPAR ) signalError.show("'(' expected after 'new' command");
			lexer.nextToken();
			if ( lexer.token != Symbol.RIGHTPAR ) signalError.show("')' expected in 'new' statement");
			lexer.nextToken();
			return new ObjectExpr(kraClass);

// 		PrimaryExpr ::= "super" "." Id "(" [ ExpressionList ] ")"
		case SUPER:
			lexer.nextToken();
			if ( lexer.token != Symbol.DOT ) {
				signalError.show("'.' expected");
			}
			else
				lexer.nextToken();
			if ( lexer.token != Symbol.IDENT )
				signalError.show("Identifier expected");
			messageName = lexer.getStringValue();
			lexer.nextToken();
			exprList = realParameters();
			if (currentMethod.isStatic()){
				signalError.show("super can not be called in a static method");
			}
			MessageSendToSuper messageSendToSuper = new MessageSendToSuper(exprList, messageName);
			if (!messageSendToSuper.validate(symbolTable.getInGlobal(currentClass))){
				signalError.show("method was not declared in the scope of "+currentClass);
			}
			return messageSendToSuper;

// 		PrimaryExpr ::= Id  |
// 		PrimaryExpr ::= Id "." Id |
//		PrimaryExpr ::= Id "." Id "(" [ ExpressionList ] ")" |
// 		PrimaryExpr ::= Id "." Id "." Id "(" [ ExpressionList ] ")"
		case IDENT:

			MessageSendToVariable messageSendToVariable = null;
			String firstId = lexer.getStringValue();
			lexer.nextToken();
			Variable firstVariable = symbolTable.getInLocal(firstId);
			if ( lexer.token != Symbol.DOT ) {
				// Id
				return new VariableExpr(firstVariable);
			}
			else {
				lexer.nextToken();
				if ( lexer.token != Symbol.IDENT ) {
					signalError.show("Identifier expected");
				}
				else {

					identifier = lexer.getStringValue();
					lexer.nextToken();
					if ( lexer.token == Symbol.DOT ) {
						lexer.nextToken();
						if ( lexer.token != Symbol.IDENT )
							signalError.show("Identifier expected");
						messageName = lexer.getStringValue();
						lexer.nextToken();
						exprList = this.realParameters();
						// Id "." Id "." Id "(" [ ExpressionList ] ")"
						messageSendToVariable =  new MessageSendToVariable(firstId, identifier, messageName,exprList);
						if (symbolTable.getInGlobal(firstId) == null){
							signalError.show(firstId+" was not declared");
						}
						if (!messageSendToVariable.validateInstance(symbolTable.getInGlobal(firstId))){
							signalError.show(firstId+" does not have an static instance called "+ identifier);
						}
						if (!messageSendToVariable.validateIndentifierMessage()){
							signalError.show(identifier+" does not have a method called "+ messageName);
						}

					}
					else if ( lexer.token == Symbol.LEFTPAR ) {

						exprList = this.realParameters();
						// Id "." Id "(" [ ExpressionList ] ")" ok
						messageSendToVariable =  new MessageSendToVariable(firstId, identifier, exprList);
						if (symbolTable.getInGlobal(firstId) == null && symbolTable.getInLocal(firstId) == null){
							signalError.show(firstId + " was not declared");
						}
						if (!messageSendToVariable.validateMethodMessage(symbolTable.getInGlobal(firstId), symbolTable.getInGlobal(symbolTable.getInLocal(firstId).getType().getName()),currentMethod)){
							if (symbolTable.getInGlobal(firstId) != null){
							signalError.show(identifier + " method was not declared inside the scope of '" + symbolTable.getInGlobal(firstId).getName()+"'");
							}else{
								signalError.show(identifier + " method was not declared inside the scope of'" + symbolTable.getInGlobal(symbolTable.getInLocal(firstId).getType().getName()).getName()+"'");
							}
						}
					}
					else {// Id "." Id
						messageSendToVariable =  new MessageSendToVariable(firstId, identifier);
						if (symbolTable.getInGlobal(firstId) == null){
							signalError.show(firstId+" was not declared");
						}
						if (!messageSendToVariable.validateInstance(symbolTable.getInGlobal(firstId))){
							signalError.show(firstId+" does not have an static instance called "+ identifier);
						}
					}
				}
			}
			return messageSendToVariable;

// 		PrimaryExpr ::= "this" |
// 		PrimaryExpr ::= "this" "." Id |
// 		PrimaryExpr ::= "this" "." Id "(" [ ExpressionList ] ")"  |
// 		PrimaryExpr ::= "this" "." Id "." Id "(" [ ExpressionList ] ")"
		case THIS:

			if (currentMethod.isStatic()){
				signalError.show("This can not be called in a static method");
			}
			MessageSendToSelf messageSendToSelf;
			lexer.nextToken();
			if ( lexer.token != Symbol.DOT ) { //PrimaryExpr ::= "this" ok
				return new ObjectExpr(symbolTable.getInGlobal(currentClass));
			}
			else {
				lexer.nextToken();
				if ( lexer.token != Symbol.IDENT )
					signalError.show("Identifier expected");
				identifier = lexer.getStringValue();
				lexer.nextToken();
				if ( lexer.token == Symbol.LEFTPAR ) {
					exprList = this.realParameters();
					//PrimaryExpr ::= "this" "." Id "(" [ ExpressionList ] ")" ok
					messageSendToSelf = new MessageSendToSelf(symbolTable.getInGlobal(currentClass),identifier,exprList);
					if (!messageSendToSelf.validateClassMessage(currentMethod)){
						signalError.show(currentClass+" does not have a private method called "+identifier);
					}
				}
				else if ( lexer.token == Symbol.DOT ) {
					lexer.nextToken();
					if ( lexer.token != Symbol.IDENT )
						signalError.show("Identifier expected");
					messageName = lexer.getStringValue();
					lexer.nextToken();
					exprList = this.realParameters();
					if (symbolTable.getInInstance(identifier) == null){
						signalError.show("Instance Variable "+identifier+" not declared");
					}
					// PrimaryExpr ::= "this" "." Id "." Id "(" [ ExpressionList ] ")" ok
					messageSendToSelf = new MessageSendToSelf(symbolTable.getInGlobal(currentClass),symbolTable.getInInstance(identifier), messageName, exprList, symbolTable.getInGlobal(symbolTable.getInInstance(identifier).getType().getName()));
					if (!messageSendToSelf.validateInstanceMessage()){
						signalError.show("Instance Variable "+identifier+" can not access method "+ messageName);
					}
				}
				else {
					if (symbolTable.getInInstance(identifier) == null){
						signalError.show("Instance Variable "+identifier+" not declared");
					}
					// PrimaryExpr ::= "this" "." Id ok
					messageSendToSelf = new MessageSendToSelf(symbolTable.getInGlobal(currentClass),symbolTable.getInInstance(identifier));
				}
			}
			return messageSendToSelf;

		default:
			signalError.show("Expression expected OR Unknown sequence of symbols");
		}
		return null;
	}

	private LiteralInt literalInt() {

		int value = lexer.getNumberValue();
		lexer.nextToken();
		return new LiteralInt(value);
	}

	private static boolean startExpr(Symbol token) {

		return token == Symbol.FALSE || token == Symbol.TRUE
				|| token == Symbol.NOT || token == Symbol.THIS
				|| token == Symbol.LITERALINT || token == Symbol.SUPER
				|| token == Symbol.LEFTPAR || token == Symbol.NULL
				|| token == Symbol.IDENT || token == Symbol.LITERALSTRING;

	}

	private ExprList realParameters() {

		ExprList anExprList = null;
		if ( lexer.token != Symbol.LEFTPAR ) signalError.show("( expected");
		lexer.nextToken();
		if ( startExpr(lexer.token) ) anExprList = exprList();
		if ( lexer.token != Symbol.RIGHTPAR ) signalError.show(") expected");
		lexer.nextToken();
		return anExprList;
	}


	private Method			currentMethod;
	private String			currentClass;
	private SymbolTable		symbolTable;
	private Lexer			lexer;
	private SignalError		signalError;

}