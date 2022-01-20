package org.ioopm.calculator.parser;


import org.ioopm.calculator.ast.*;

import java.io.StreamTokenizer;
import java.io.StringReader;
import java.io.IOException;
import java.io.*;

import java.util.*;

/**
 * Represents the parsing of strings into valid expressions defined in the AST.
 */
public class CalculatorParser {
    private StreamTokenizer st;
    private Environment vars;
    private static char MULTIPLY = '*';
    private static char ADDITION = '+';
    private static char SUBTRACTION = '-';
    private static char DIVISION = '/';
    private static String NEG = "Neg";
    private static char NEGATION = '-';
    private static String SIN = "Sin";
    private static String COS = "Cos";
    private static String LOG = "Log";
    private static String EXP = "Exp";
    private static char ASSIGNMENT = '=';
    private static String EQUAL = "==";
    private static FunctionCall functions;

    // unallowerdVars is used to check if variabel name that we
    // want to assign new meaning to is a valid name eg 3 = Quit
    // or 10 + x = L is not allowed
    private final ArrayList < String > unallowedVars = new ArrayList < String > (Arrays.asList("Quit",
        "Vars",
        "Clear",
        "Sin",
        "Cos",
        "Exp",
        "if",
        "else",
        "end",
        "Neg", 
        "function"));
        
    private FunctionDeclaration currentFunction;
    private FunctionDeclaration currentFunction2;
    public static LinkedList<FunctionDeclaration> functionDecs = new LinkedList<FunctionDeclaration>();
    private ArrayList <String> functionNames = new ArrayList <String>();

    /**
     * Used to parse the inputted string by the Calculator program
     * @param inputString the string used to parse
     * @param vars the Environment in which the variables exist
     * @return a SymbolicExpression to be evaluated
     * @throws IOException by nextToken() if it reads invalid input
     */
    public SymbolicExpression parse(String inputString, Environment vars, FunctionCall functions) throws IOException {
        this.st = new StreamTokenizer(new StringReader(inputString)); // reads from inputString via stringreader.
        this.vars = vars;
        this.st.ordinaryChar('-');
        this.st.ordinaryChar('/');
        this.functions = functions;
        this.st.eolIsSignificant(true);                                         
        SymbolicExpression result = statement(); // calls to statement
        return result; // the final result
    }

    /**
     * Checks wether the token read is a command or an assignment
     * @return a SymbolicExpression to be evaluated
     * @throws IOException by nextToken() if it reads invalid input
     * @throws SyntaxErrorException if the token parsed cannot be turned into a valid expression
     */
    private SymbolicExpression statement() throws IOException {                 
        SymbolicExpression result;
        this.st.nextToken();                                                    //kollar på nästa token som ligger på strömmen
        if (this.st.ttype == this.st.TT_EOF) 
        {
            throw new SyntaxErrorException("Error: Expected an expression");    // now we are done
        }
        if (this.st.ttype == this.st.TT_WORD)                                   // vilken typ det senaste tecken vi lÃ¤ste in hade.
        {                                 
            if (this.st.sval.equals("Quit") || this.st.sval.equals("Vars") || this.st.sval.equals("Clear"))         // sval = string Variable
            {                            
                result = command();
            }
            else if(this.st.sval.equals("function"))                            //tittar efter strängen function
            {                         
                result = functionDef();                                         //går i så fall vidare till functionDef() 
            }
            /*else if(functionNames.contains(this.st.sval)){
                result = functionCall();
            }*/
            else 
            {
                result = assignment();                                          // går vidare med uttrycket.
            }
        } else 
        {
            result = assignment();                                              // om inte == word, gå till assignment ändå (kan vara tt_number)
        }

        if (this.st.nextToken() != this.st.TT_EOF) {                            // token should be an end of stream token if we are done
            if (this.st.ttype == this.st.TT_WORD) {
                throw new SyntaxErrorException("Error: Unexpected '" + this.st.sval + "'");
            } else {
                throw new SyntaxErrorException("Error: Unexpected '" + String.valueOf((char) this.st.ttype) + "'");
            }
        }
        return result;
    }

    /**
     * Checks what kind of command that should be returned
     * @return an instance of Quit, Clear or Vars depending on the token parsed
     * @throws IOException by nextToken() if it reads invalid input
     */
    private SymbolicExpression command() throws IOException {
        if (this.st.sval.equals("Quit")) {
            return Quit.instance();
        } else if (this.st.sval.equals("Clear")) {
            return Clear.instance();
        } else {
            return Vars.instance();
        }
    }

    /**
     * Checks wether the token read is an assignment between 2 expression and 
     * descend into the right hand side of '='
     * @return a SymbolicExpression to be evaluated
     * @throws IOException by nextToken() if it reads invalid input
     * @throws SyntaxErrorException if the token parsed cannot be turned into a valid expression,
     *         the variable on rhs of '=' is a number or invalid variable
     */
    private SymbolicExpression assignment() throws IOException {
        SymbolicExpression result = expression();
        this.st.nextToken();
        while (this.st.ttype == ASSIGNMENT) {
            if(this.st.nextToken() == '='){
                this.st.pushBack();
                return result;
            }
            else{
                this.st.pushBack();
            }
            this.st.nextToken();
            if (this.st.ttype == this.st.TT_NUMBER) {
                throw new SyntaxErrorException("Error: Numbers cannot be used as a variable name");
            } else if (this.st.ttype != this.st.TT_WORD) {
                throw new SyntaxErrorException("Error: Not a valid assignment of a variable"); //this handles faulty inputs after the equal sign eg. 1 = (x etc
            } else {
                if (this.st.sval.equals("ans")) {
                    throw new SyntaxErrorException("Error: ans cannot be redefined");
                }
                SymbolicExpression key = identifier();
                result = new Assignment(result, key);
            }
            this.st.nextToken();
        }
        this.st.pushBack();
        return result;
    }

    /**
     * Check if valid identifier for variable and return that if so
     * @return a SymbolicExpression that is either a named constant or a new variable
     * @throws IOException by nextToken() if it reads invalid input
     * @throws IllegalExpressionException if you try to redefine a string that isn't allowed
     */
    private SymbolicExpression identifier() throws IOException {
        SymbolicExpression result;

        if (this.unallowedVars.contains(this.st.sval)) {
            throw new IllegalExpressionException("Error: cannot redefine " + this.st.sval);
        }

        if (Constants.namedConstants.containsKey(this.st.sval)) {
            result = new NamedConstant(st.sval, Constants.namedConstants.get(st.sval));
        } else {
            result = new Variable(this.st.sval);
        }
        return result;
    }

    /**
     * Checks wether the token read is an addition or subtraction
     * and then continue on with the right hand side of operator
     * @return a SymbolicExpression to be evaluated
     * @throws IOException by nextToken() if it reads invalid input
     */
    private SymbolicExpression expression() throws IOException {
        SymbolicExpression result = term();
        this.st.nextToken();
        while (this.st.ttype == ADDITION || this.st.ttype == SUBTRACTION) {
            int operation = st.ttype;
            this.st.nextToken();
            if (operation == ADDITION) {
                result = new Addition(result, term());
            } else {
                result = new Subtraction(result, term());
            }
            this.st.nextToken();
        }
        this.st.pushBack();
        return result;
    }

    /**
     * Checks wether the token read is an Multiplication or
     * Division and then continue on with the right hand side of
     * operator
     * @return a SymbolicExpression to be evaluated
     * @throws IOException by nextToken() if it reads invalid input
     */
    private SymbolicExpression term() throws IOException {
        SymbolicExpression result = primary();
        this.st.nextToken();
        while (this.st.ttype == MULTIPLY || this.st.ttype == DIVISION) {
            int operation = st.ttype;
            this.st.nextToken();

            if (operation == MULTIPLY) {
                result = new Multiplication(result, primary());
            } else {
                result = new Division(result, primary());
            }
            this.st.nextToken();
        }
        this.st.pushBack();
        return result;
    }

    /**
     * Checks wether the token read is a parantheses and then
     * continue on with the expression inside of it or if the
     * operation is an unary operation and then continue on with
     * the right hand side of that operator else if it's a
     * number/identifier
     * @return a SymbolicExpression to be evaluated
     * @throws IOException by nextToken() if it reads invalid input
     * @throws SyntaxErrorException if the token parsed cannot be turned into a valid expression,
     *         missing right parantheses
     */
    private SymbolicExpression primary() throws IOException {
        SymbolicExpression result;
        if (this.st.ttype == '(') {
            this.st.nextToken();
            result = assignment();
            /// This captures unbalanced parentheses!
            if (this.st.nextToken() != ')') {
                throw new SyntaxErrorException("expected ')'");
            }
        }
        else if (this.st.ttype == '{'){
            this.st.nextToken();
            result = assignment();
            /// This captures unbalanced parentheses!
            if (this.st.nextToken() != '}') {
                throw new SyntaxErrorException("expected '}'");
            }
            result = new Scope(result);
        }
        else if(functionNames.contains(this.st.sval)){
                result = functionCall();
            }
        
        
        else if (this.st.sval != null && this.st.sval.equals("if")){
            this.st.nextToken();
            boolean equalsequals = false;
            Scope ifStatement = null;
            Scope elseStatement = null;
            SymbolicExpression idL = assignment();
            this.st.nextToken();
            String operator = "";
            if(this.st.ttype == '=') operator = "==";
            else if(this.st.ttype == '>' || this.st.ttype == '<'){
                operator += (char) this.st.ttype;
                this.st.nextToken();
                if(this.st.ttype == '='){
                    operator += (char) this.st.ttype;
                }
                else{
                this.st.pushBack();
                }
            }
            else{
                throw new SyntaxErrorException("Invalid operator");
            }
            this.st.nextToken();
            SymbolicExpression idR = assignment();
            this.st.nextToken();
            if(this.st.ttype == '{'){
                this.st.nextToken();
                SymbolicExpression tmp = assignment();
                if (this.st.nextToken() != '}') {
                    throw new SyntaxErrorException("expected '}'");
                }
                ifStatement = new Scope(tmp);
            }
            else throw new SyntaxErrorException("expected Scope");
            this.st.nextToken();
            if(this.st.sval.equals("else") == false){
                throw new SyntaxErrorException("Expected: 'else'");
            }
            
            this.st.nextToken();
            if(this.st.ttype == '{'){
                this.st.nextToken();
                SymbolicExpression tmp = assignment();
                if (this.st.nextToken() != '}') {
                    throw new SyntaxErrorException("expected '}'");
                }
                elseStatement = new Scope(tmp);
            }
            else throw new SyntaxErrorException("expected Scope");
            result = new Conditional(idL, idR, operator, ifStatement, elseStatement);

        }
        else if (this.st.ttype == NEGATION) {
            result = unary();
        } else if (this.st.ttype == this.st.TT_WORD) {
            if (st.sval.equals(SIN) ||
                st.sval.equals(COS) ||
                st.sval.equals(EXP) ||
                st.sval.equals(NEG) ||
                st.sval.equals(LOG)) {

                result = unary();
            } else if (functionNames.contains(this.st.sval)) {
                result = functionCall();
            } else {
                result = identifier();
            }
        } else {
            this.st.pushBack();
            result = number();
        }
        return result;
    }

    /**
     * Checks what type of Unary operation the token read is and
     * then continues with the expression that the operator holds
     * @return a SymbolicExpression to be evaluated
     * @throws IOException by nextToken() if it reads invalid input
     */
    private SymbolicExpression unary() throws IOException {
        SymbolicExpression result;
        int operationNeg = st.ttype;
        String operation = st.sval;
        this.st.nextToken();
        if (operationNeg == NEGATION || operation.equals(NEG)) {
            result = new Negation(primary());
        } else if (operation.equals(SIN)) {
            result = new Sin(primary());
        } else if (operation.equals(COS)) {
            result = new Cos(primary());
        } else if (operation.equals(LOG)) {
            result = new Log(primary());
        } else {
            result = new Exp(primary());
        }
        return result;
    }
    
    /**
     * Checks if the token read is a number - should always be a number in this method
     * @return a SymbolicExpression to be evaluated
     * @throws IOException by nextToken() if it reads invalid input
     * @throws SyntaxErrorException if the token parsed cannot be turned into a valid expression,
     *         expected a number which is not present
     */
    private SymbolicExpression number() throws IOException {
        this.st.nextToken();
        if (this.st.ttype == this.st.TT_NUMBER) {
            return new Constant(this.st.nval);
        } 
        else {
            throw new SyntaxErrorException("Error: Expected number");
        }
    }
    
    private SymbolicExpression functionCall() throws IOException {
            FunctionCall functions = this.functions;                                
            LinkedList<FunctionDeclaration> funcs = functions.getList();
            for(FunctionDeclaration x : funcs){
                if(x.getName().equals(this.st.sval)){
                    currentFunction2 = x;
                }
            }
            String name = this.st.sval;
            this.st.nextToken();
            LinkedList<SymbolicExpression> constants = new LinkedList<SymbolicExpression>();
            if(this.st.ttype == '('){
                while(true){
                    this.st.nextToken();
                    if(this.st.ttype == ')'){
                        break;
                    }
                    else if(this.st.ttype == this.st.TT_NUMBER){
                        SymbolicExpression result = assignment();
                        constants.add(result);
                        this.st.nextToken();
                        if (this.st.ttype == ','){
                        }
                        else if(this.st.ttype == ')'){
                            break;
                        }
                        else{
                            throw new SyntaxErrorException("Expected ',' or ')'");
                        }
                    } else if(this.st.ttype == this.st.TT_WORD){
                        SymbolicExpression result = assignment();
                        constants.add(result);
                        this.st.nextToken();
                        if (this.st.ttype == ','){
                        }
                        else if(this.st.ttype == ')'){
                            break;
                        }
                        else{
                            throw new SyntaxErrorException("Expected ',' or ')'");
                        }
                    }
                    else{
                        throw new SyntaxErrorException ("Expected number or variable");
                    }
                    
                }
                FunctionConstants function = new FunctionConstants(currentFunction2, constants);
                function.name = name;
                return function;
            }
            else{
                throw new SyntaxErrorException("Expected '('");
            }
        }
    /*
     * Finds function name
     * 
     */
    private SymbolicExpression functionDef() throws IOException {
        this.st.nextToken();                                                    //om inte gå till nästa token
        if(this.st.ttype == StreamTokenizer.TT_WORD)                            //om ttype är ett word (funktions namn)
        {
            String name = this.st.sval;                                         //spara det namnet i en string variabel
            if(this.st.nextToken() == '(')                                      //om nästa token är ett '('...
            {
                functionNames.add(name);
                currentFunction = new FunctionDeclaration(name, FunctionDefArgs());
                Sequence sequence = createSequence();
                currentFunction.setBody(sequence);
                return currentFunction;
                                                                                    //skapa en ny FunctionDeclaration
            } 
            else {
                throw new SyntaxErrorException("Error: expected '('");                 //Om ttype inte är '(', kasta ett exception
            }
        }
        else {
            throw new SyntaxErrorException("Error: expected function name");           //om det inte finns ett funktions namn
        }
         
    }

    public Sequence createSequence() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        LinkedList<SymbolicExpression> expressions = new LinkedList<SymbolicExpression>();
        System.out.println("\nPlease enter the function definition line by line, finish with end.");
        System.out.print("  ");
        String input = br.readLine();
        while(!input.equals("end")){
            SymbolicExpression result = this.parse(input, vars, functions);
            expressions.add(result);
            System.out.print("  ");
            input = br.readLine();
        }
        
        return new Sequence(expressions);
    }
    
    public Variable[] FunctionDefArgs() throws IOException {
        List<Variable> VarList = new ArrayList<Variable>();                     //skapar en ny ArrayList av Variables som kallas dummy just nu, kommer göra om denna till en Variable[] senare
        
        this.st.nextToken();                                                    //nästa token
        if(this.st.ttype == ')')                                                //om nästa token är ')'...
        {
            return new Variable[0];                                             //returnera en tom Variable[]
        }
        while(true)                                                             //loopa detta 
        {
            if(this.st.ttype == StreamTokenizer.TT_EOF)
            {
                    throw new SyntaxErrorException("Error: expected argument or ')'");
            }    
            if(this.st.ttype == StreamTokenizer.TT_WORD                         //om nuvarande token är ett ord
            && !this.unallowedVars.contains(this.st.ttype)                      //och inte en unallowedVars
            && !Constants.namedConstants.containsKey(this.st.sval))             //och inte finns i namedConstants...
            {                     
                Variable Var = new Variable(this.st.sval);                      //skapa en ny variabel av ordet
                VarList.add(Var);                                               //lägg till i arrayen
                this.st.nextToken();
                if(this.st.ttype == ')')                                        //om nästa token inte är en )..
                {                                      
                    break;                                                      //avsluta loopen
                }
                else if(this.st.ttype != ',')                                   //om nästa token inte är ,
                {                                 
                    throw new SyntaxErrorException("Error: expected ',' or ')'");      //kasta ett exception
                }
                else if(this.st.ttype == ',')                                   //om nuvarande token är en ','...
                {                                
                    this.st.nextToken();                                        //gå till nästa token
                }
            } 
                else 
            {
                throw new SyntaxErrorException("Error: Unallowed word");
            }
            
        }
        return VarList.toArray(new Variable[VarList.size()]);                                //gör om min arraylist till en variable list och returnerar den
    }
    
}