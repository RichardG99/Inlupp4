package org.ioopm.calculator.ast;

import org.ioopm.calculator.ast.*;
import org.ioopm.calculator.parser.*;

public interface Visitor {
    
    public SymbolicExpression evaluate(SymbolicExpression topLevel, Environment env);
    public boolean check(SymbolicExpression a);
    
//Binary
    public SymbolicExpression visit(Subtraction n);
    public SymbolicExpression visit(Addition n);
    public SymbolicExpression visit(Assignment n);
    public SymbolicExpression visit(Division n);
    public SymbolicExpression visit(Multiplication n);


//Unary
    public SymbolicExpression visit(Cos n);
    public SymbolicExpression visit(Exp n);
    public SymbolicExpression visit(Log n);
    public SymbolicExpression visit(Negation n);
    public SymbolicExpression visit(Sin n);

//Atoms
    public SymbolicExpression visit(Constant n);
    public SymbolicExpression visit(NamedConstant n);
    public SymbolicExpression visit(Variable n);

//Commands
    public SymbolicExpression visit(Clear n);
    public SymbolicExpression visit(Quit n);
    public SymbolicExpression visit(Vars n);
//Extensions
    public SymbolicExpression visit(Scope n);
    public SymbolicExpression visit(Conditional n);
//Functions    
    public SymbolicExpression visit(FunctionCall n);
    public SymbolicExpression visit(Sequence n);
    public SymbolicExpression visit(FunctionDeclaration n);
    public SymbolicExpression visit(FunctionConstants n);
}
