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
			kraClassList.add(classDec());
			while (lexer.token == Symbol.CLASS)
				kraClassList.add(classDec());
			if (lexer.token != Symbol.EOF) {
				signalError.show("End of file expected");
			}
		} catch (RuntimeException e) {
			// if there was an exception, there is a compilation signalError
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
			if ( lexer.token != Symbol.RIGHTPAR ) 
				signalError.show("')' expected after metaobject call with parameters");
			else
				lexer.nextToken();
		}
		if ( name.equals("nce") ) {
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

		boolean still = false, finale = false;
		while (lexer.token == Symbol.STATIC || lexer.token == Symbol.FINAL) {
			switch (lexer.token){
				case STATIC:
					if (!still){
						still = true;
					}else{
						signalError.show("'static' has already been declared");
					}
					break;
				case FINAL:
					if (!finale){
						finale = true;
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
		KraClass kraClass = new KraClass(className,finale,still);
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
			if(superclass.isFinale()){
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
			still = false;
			finale = false;

			while (lexer.token == Symbol.STATIC || lexer.token == Symbol.FINAL) {
				switch (lexer.token){
					case STATIC:
						if (!still){
							still = true;
						}else{
							signalError.show("'static' has already been declared");
						}
						break;
					case FINAL:
						if (!finale){
							finale = true;
						}else{
							signalError.show("'final' has already been declared");
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
					if(finale){
						signalError.show("final method must be public");
					}
					kraClass.addPrivateMethod(methodDec(t, name, still, finale));
				}else{
					kraClass.addPublicMethod(methodDec(t, name, still, finale));
				}
			else if ( qualifier != Symbol.PRIVATE )
				signalError.show("Attempt to declare a public instance variable");
			else if (t.getName().equals("void"))
				signalError.show("Attempt to declare a void instance variable");
			else
				kraClass.addInstanceVariableList(instanceVarDec(t, name, still, finale));
		}
		if ( lexer.token != Symbol.RIGHTCURBRACKET )
			signalError.show("public/private or \"}\" expected");
		lexer.nextToken();
		symbolTable.removeInstanceIdent();
		return kraClass;
	}

//	InstVarDec ::= Type IdList ";"
// 	InstVarDec ::= [ "static" ] "private" Type IdList ";"
	private InstanceVariableList instanceVarDec(Type type, String name, boolean still, boolean finale) {

		if (symbolTable.getInInstance(name) != null){
			signalError.show(name+" has already been declared");
		}
		symbolTable.putInInstance(name, new Variable(name, type));
		ArrayList<InstanceVariable> instanceVariables = new ArrayList<>();
		InstanceVariable instanceVariable = new InstanceVariable(type, name, still, finale);
		instanceVariables.add(instanceVariable);
		if (instanceVariable.isFinale()) {
			if (lexer.token != Symbol.ASSIGN) {
				signalError.show("Final variable must be initialized");
			}
// TODO: chamar o assignment quando a variavel for final
		}
		while (lexer.token == Symbol.COMMA) {
			lexer.nextToken();
			if ( lexer.token != Symbol.IDENT )
				signalError.show("Identifier expected");
			String variableName = lexer.getStringValue();
			instanceVariables.add(new InstanceVariable(type, variableName, still, finale));
			lexer.nextToken();
		}
		if ( lexer.token != Symbol.SEMICOLON )
			signalError.show(SignalError.semicolon_expected);
		lexer.nextToken();
		return new InstanceVariableList(instanceVariables);
	}

	private boolean searchMethods(String name, ParamList paramList, String clss, boolean superclass){
		KraClass kraClass = symbolTable.getInGlobal(clss);
		if (kraClass.searchMethods(name, paramList, superclass)){
			return true;
		}
		if(kraClass.getSuperclass() != null){
			searchMethods(name, paramList, kraClass.getSuperClassName(), true );
		}
		return false;
	}

//	MethodDec ::= Qualifier Type Id "("[ FormalParamDec ] ")" "{" StatementList "}"
	private Method methodDec(Type type, String name, boolean still, boolean finale) {

		lexer.nextToken();
		ParamList paramList = new ParamList();
		if ( lexer.token != Symbol.RIGHTPAR ){
			paramList =  formalParamDec();
		}
		if ( lexer.token != Symbol.RIGHTPAR ){
			signalError.show(") expected");
		}
		if (searchMethods(name, paramList, currentClass, false)){
			signalError.show(name+" is already defined in "+ currentClass);
		}
		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTCURBRACKET ) signalError.show("{ expected");
		lexer.nextToken();
		Method method = new Method(type,name,still,finale, paramList, null);
		currentMethod = method;
		StatementList statementList = statementList();
		method.setStatementList(statementList);
		if ( lexer.token != Symbol.RIGHTCURBRACKET ) signalError.show("} expected");
		lexer.nextToken();
		symbolTable.removeLocalIdent();
		return method;
	}

	// LocalDec ::= Type IdList ";"
	private void localDec() {

		Type type = type();
		if ( lexer.token != Symbol.IDENT ) signalError.show("Identifier expected");
		Variable v = new Variable(lexer.getStringValue(), type);
		lexer.nextToken();
		while (lexer.token == Symbol.COMMA) {
			lexer.nextToken();
			if ( lexer.token != Symbol.IDENT )
				signalError.show("Identifier expected");
			v = new Variable(lexer.getStringValue(), type);
			lexer.nextToken();
		}
	}

//	recebimento de parametro, o parametro deve ser o filho da classe declarada
// FormalParamDec ::= ParamDec { "," ParamDec }
	private ParamList formalParamDec() {
		ParamList paramList = new ParamList();
		paramList.addElement(paramDec());
		while (lexer.token == Symbol.COMMA) {
			lexer.nextToken();
			paramList.addElement(paramDec());
		}
		return paramList;
	}

//	 ParamDec ::= Type Id
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

//	 Type ::= BasicType | Id
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
				signalError.show("Is not a type");
			}
			result = new TypeClass(lexer.getStringValue());
			break;
		default:
			signalError.show("Type expected");
			result = Type.undefinedType;
		}
		lexer.nextToken();
		return result;
	}

//	 CompStatement ::= "{" { Statement } "}"
	private CompositeStatement compositeStatement() {
		lexer.nextToken();
		CompositeStatement compositeStatement =  new CompositeStatement(statementList());
		if ( lexer.token != Symbol.RIGHTCURBRACKET )
			signalError.show("} expected");
		else
			lexer.nextToken();
		return compositeStatement;
	}

//	 statements always begin with an identifier, if, read, write, ...
//	 statementList ::= { Statement }
	private StatementList statementList() {
		Symbol tk = lexer.token;
		StatementList statementList = new StatementList();
		while (tk != Symbol.RIGHTCURBRACKET) {
			statementList.addElement(statement());
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
			assignExprLocalDec();
			break;
		case RETURN:
			statement = returnStatement();
			break;
		case READ:
			readStatement();
			break;
		case WRITE:
			statement = writeStatement();
			break;
		case WRITELN:
			statement = writelnStatement();
			break;
		case IF:
			statement = ifStatement();
			break;
		case BREAK:
			statement = breakStatement();
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

	private Expr assignExprLocalDec() {
//	 AssignExprLocalDec ::= Expression [ =" Expression ] | LocalDec
		if ( lexer.token == Symbol.INT || lexer.token == Symbol.BOOLEAN
				|| lexer.token == Symbol.STRING ||
				// token é uma classe declarada textualmente antes desta
				// instrução
				(lexer.token == Symbol.IDENT && isType(lexer.getStringValue())) ) {
			/*
			 * uma declaração de variável. 'lexer.token' é o tipo da variável
			 * 
			 * AssignExprLocalDec ::= Expression [ "=" Expression ] | LocalDec
			 * LocalDec ::= Type IdList ``;''
			 */
			localDec();
		}
		else {
			/*
			 * AssignExprLocalDec ::= Expression [ "=" Expression ]
			 */
			expr();
			if ( lexer.token == Symbol.ASSIGN ) {
				lexer.nextToken();
				expr();
				if ( lexer.token != Symbol.SEMICOLON )
					signalError.show("';' expected", true);
				else
					lexer.nextToken();
			}
		}
		return null;
	}

//	real é o tipo que manda na chamada de parametros, ele deve ser a subclasse
	private ExprList realParameters() {
		ExprList anExprList = null;

		if ( lexer.token != Symbol.LEFTPAR ) signalError.show("( expected");
		lexer.nextToken();
		if ( startExpr(lexer.token) ) anExprList = exprList();
		if ( lexer.token != Symbol.RIGHTPAR ) signalError.show(") expected");
		lexer.nextToken();
		return anExprList;
	}

//	whileStatement ::= "while" "(" Expression ")" statement
	private WhileStatement whileStatement() {

		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTPAR ) signalError.show("( expected");
		lexer.nextToken();
		Expr expr = expr();
		if ( lexer.token != Symbol.RIGHTPAR ) signalError.show(") expected");
		lexer.nextToken();
		Statement statement = statement();
		return new WhileStatement(expr,statement);
	}

//	ifStatement ::= "if" "(" Expression ")" Statement [ "else" statement]
	private IfStatement ifStatement() {

		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTPAR ) signalError.show("( expected");
		lexer.nextToken();
		Expr expr = expr();
		if ( lexer.token != Symbol.RIGHTPAR ) signalError.show(") expected");
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
		if ( lexer.token != Symbol.SEMICOLON )
			signalError.show(SignalError.semicolon_expected);
		lexer.nextToken();
		return new ReturnStatement(expr);
	}

//	readStatement ::= "read" "(" LeftValue { "," LeftValue } ")"
	private ReadStatement readStatement() {

		VariableList variableList = new VariableList();
		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTPAR ) signalError.show("( expected");
		lexer.nextToken();
		while (true) {
			if ( lexer.token == Symbol.THIS ) {
				lexer.nextToken();
				if ( lexer.token != Symbol.DOT ) signalError.show(". expected");
				lexer.nextToken();
			}
			if ( lexer.token != Symbol.IDENT )
				signalError.show(SignalError.ident_expected);

			String name = lexer.getStringValue();
			variableList.addElement(symbolTable.getInLocal(name));
			lexer.nextToken();
			if ( lexer.token == Symbol.COMMA )
				lexer.nextToken();
			else
				break;
		}

		if ( lexer.token != Symbol.RIGHTPAR ){
			signalError.show(") expected");
		}
		lexer.nextToken();
		if ( lexer.token != Symbol.SEMICOLON )
			signalError.show(SignalError.semicolon_expected);
		lexer.nextToken();
		return new ReadStatement(variableList);
	}

//	writeStatement ::= "write" "(" ExpressionList ")"
	private WriteStatement writeStatement() {

		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTPAR ) signalError.show("( expected");
		lexer.nextToken();
		ExprList exprList = exprList();
		if ( lexer.token != Symbol.RIGHTPAR ) signalError.show(") expected");
		lexer.nextToken();
		if ( lexer.token != Symbol.SEMICOLON )
			signalError.show(SignalError.semicolon_expected);
		lexer.nextToken();
		return new WriteStatement(exprList);
	}

//	writelnStatement ::= "writeln" "(" ExpressionList ")"
	private WriteLnStatement writelnStatement() {

		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTPAR ) signalError.show("( expected");
		lexer.nextToken();
		ExprList exprList = exprList();
		if ( lexer.token != Symbol.RIGHTPAR ) signalError.show(") expected");
		lexer.nextToken();
		if ( lexer.token != Symbol.SEMICOLON )
			signalError.show(SignalError.semicolon_expected);
		lexer.nextToken();
		return new WriteLnStatement(exprList);
	}

	private BreakStatement breakStatement() {
		lexer.nextToken();
		if ( lexer.token != Symbol.SEMICOLON )
			signalError.show(SignalError.semicolon_expected);
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
		String messageName, ident;

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
			if ( lexer.token != Symbol.IDENT )
				signalError.show("Identifier expected");

			String className = lexer.getStringValue();
			KraClass kraClass = symbolTable.getInGlobal(className);
			if (kraClass == null) signalError.show(className+" was not declared");
			lexer.nextToken();
			if ( lexer.token != Symbol.LEFTPAR ) signalError.show("( expected");
			lexer.nextToken();
			if ( lexer.token != Symbol.RIGHTPAR ) signalError.show(") expected");
			lexer.nextToken();
			return new ObjectExpr(kraClass);
			/*
          	 * PrimaryExpr ::= "super" "." Id "(" [ ExpressionList ] ")"  | 
          	 *                 Id  | Id "." Id | Id "." Id "(" [ ExpressionList ] ")" | Id "." Id "." Id "(" [ ExpressionList ] ")" |
          	 *                 "this" | "this" "." Id | "this" "." Id "(" [ ExpressionList ] ")"  | "this" "." Id "." Id "(" [ ExpressionList ] ")"
			 */
//		PrimaryExpr ::= "super" "." Id "(" [ ExpressionList ] ")"
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
// TODO: Procurar o message name na superclasse / superclasse / superclasse
// TODO: Checa se não está em um método static
			lexer.nextToken();
			exprList = realParameters();
//			return new SuperExpr(exprList, messageName);
			break;

		case IDENT:
			/*
          	 * PrimaryExpr ::=  
          	 *                 Id  |
          	 *                 Id "." Id | 
          	 *                 Id "." Id "(" [ ExpressionList ] ")" |
          	 *                 Id "." Id "." Id "(" [ ExpressionList ] ")" |
			 */

			String firstId = lexer.getStringValue();
			lexer.nextToken();
			if ( lexer.token != Symbol.DOT ) {
				// Id
				// retorne um objeto da ASA que representa um identificador
				return null;
			}
			else { // Id "."
				lexer.nextToken(); // coma o "."
				if ( lexer.token != Symbol.IDENT ) {
					signalError.show("Identifier expected");
				}
				else {
					// Id "." Id
					lexer.nextToken();
					ident = lexer.getStringValue();
					if ( lexer.token == Symbol.DOT ) {
						// Id "." Id "." Id "(" [ ExpressionList ] ")"
						/*
						 * se o compilador permite variáveis estáticas, é possível
						 * ter esta opção, como
						 *     Clock.currentDay.setDay(12);
						 * Contudo, se variáveis estáticas não estiver nas especificaçães,
						 * sinalize um erro neste ponto.
						 */
						lexer.nextToken();
						if ( lexer.token != Symbol.IDENT )
							signalError.show("Identifier expected");
						messageName = lexer.getStringValue();
						lexer.nextToken();
						exprList = this.realParameters();

					}
					else if ( lexer.token == Symbol.LEFTPAR ) {
						// Id "." Id "(" [ ExpressionList ] ")"
						exprList = this.realParameters();
						/*
						 * para fazer as conferências semânticas, procure por
						 * método 'ident' na classe de 'firstId'
						 */
					}
					else {
						// retorne o objeto da ASA que representa Id "." Id
					}
				}
			}
			break;
		case THIS:
			/*
			 * Este 'case THIS:' trata os seguintes casos: 
          	 * PrimaryExpr ::= 
          	 *                 "this" | 
          	 *                 "this" "." Id | 
          	 *                 "this" "." Id "(" [ ExpressionList ] ")"  | 
          	 *                 "this" "." Id "." Id "(" [ ExpressionList ] ")"
			 */
			lexer.nextToken();
			if ( lexer.token != Symbol.DOT ) {
				// only 'this'
				// retorne um objeto da ASA que representa 'this'
				// confira se n�o estamos em um método estático
				return null;
			}
			else {
				lexer.nextToken();
				if ( lexer.token != Symbol.IDENT )
					signalError.show("Identifier expected");
				ident = lexer.getStringValue();
				lexer.nextToken();
				// j� analisou "this" "." Id
				if ( lexer.token == Symbol.LEFTPAR ) {
					// "this" "." Id "(" [ ExpressionList ] ")"
					/*
					 * Confira se a classe corrente possui um método cujo nome é
					 * 'ident' e que pode tomar os parâmetros de ExpressionList
					 */
					exprList = this.realParameters();
				}
				else if ( lexer.token == Symbol.DOT ) {
					// "this" "." Id "." Id "(" [ ExpressionList ] ")"
					lexer.nextToken();
					if ( lexer.token != Symbol.IDENT )
						signalError.show("Identifier expected");
					lexer.nextToken();
					exprList = this.realParameters();
				}
				else {
					// retorne o objeto da ASA que representa "this" "." Id
					/*
					 * confira se a classe corrente realmente possui uma
					 * vari�vel de inst�ncia 'ident'
					 */
					return null;
				}
			}
			break;
		default:
			signalError.show("Expression expected");
		}
		return null;
	}

	private LiteralInt literalInt() {

		int value = lexer.getNumberValue();
		lexer.nextToken();
		return new LiteralInt(value);
	}

//	é utilizado no parametro real
	private static boolean startExpr(Symbol token) {

		return token == Symbol.FALSE || token == Symbol.TRUE
				|| token == Symbol.NOT || token == Symbol.THIS
				|| token == Symbol.LITERALINT || token == Symbol.SUPER
				|| token == Symbol.LEFTPAR || token == Symbol.NULL
				|| token == Symbol.IDENT || token == Symbol.LITERALSTRING;

	}

	private Method			currentMethod;
	private String			currentClass;
	private SymbolTable		symbolTable;
	private Lexer			lexer;
	private SignalError		signalError;

}