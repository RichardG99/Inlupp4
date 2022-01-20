package org.ioopm.calculator;

import org.ioopm.calculator.ast.*;
import org.ioopm.calculator.parser.*;

public class NamedConstantChecker implements Visitor {
    
    @Override
    public boolean check(SymbolicExpression a){
        try{
             SymbolicExpression top = a.accept(this);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true; 
        
    }
    // Recurse down the AST tree
    public SymbolicExpression visit(Addition a) {
        a.getLhs().accept(this);
        a.getRhs().accept(this);
        return a; // No need to create a new tree
    }
    

    // When we hit an assignment, make sure to check!
    @Override
    public SymbolicExpression visit(Assignment a) {
        a.getRhs().accept(this);
        if (a.getRhs().isConstant()) { // or maybe you used just isConstant
            throw new IllegalExpressionException("Right hand side is constant or named constant:\n " + a.getLhs() + " = " + a.getRhs());
            //System.out.println(a.getLhs() + "=" + a.getRhs());
        }
        return a;
    }
    
    public SymbolicExpression visit(Subtraction a) {
        a.getLhs().accept(this);
        a.getRhs().accept(this);
        return a;
    }
    
    public SymbolicExpression visit(Multiplication a) {
        a.getLhs().accept(this);
        a.getRhs().accept(this);
        return a;
    }  
    
    public SymbolicExpression visit(Division a) {
        a.getLhs().accept(this);
        a.getRhs().accept(this);
        return a;
    }
    
    public SymbolicExpression visit(Cos a) {
        a.getUnaryValue().accept(this);
        return a;
    }

    public SymbolicExpression visit(Sin a) {
        a.getUnaryValue().accept(this);
        return a;
    }

    public SymbolicExpression visit(Exp a) {
        a.getUnaryValue().accept(this);
        return a;
    }
    
    public SymbolicExpression visit(Log a) {
        a.getUnaryValue().accept(this);
        return a;
    }

    public SymbolicExpression visit(Negation a) {
        a.getUnaryValue().accept(this);
        return a;
    }
    
    public SymbolicExpression visit(Vars a) {
        throw new IllegalExpressionException("NamedConstantChecker called on method with no evaluation");
    }
    
    public SymbolicExpression visit(Clear a) {
        throw new IllegalExpressionException("NamedConstantChecker called on method with no evaluation");
    }
    
    public SymbolicExpression  visit(Quit a) {
        throw new IllegalExpressionException("NamedConstantChecker called on method with no evaluation");
    }
    
    public SymbolicExpression visit(Constant a) {
        return a;
    }
    
    public SymbolicExpression visit(NamedConstant a) {
        return a;
    }
    
    public SymbolicExpression visit(Variable a) {
        return a;
    }
    
    // fuskfunktion
    public SymbolicExpression evaluate(SymbolicExpression topLevel, Environment env){
        System.out.println("evaluate i checker");
        return null;
    }
    
    
    public SymbolicExpression visit(Scope n)
    {
        SymbolicExpression dummy = new Constant(1);
        return dummy;
    }
    
    public SymbolicExpression visit(Conditional n)
    {
        //SymbolicExpression expression = n.arg.accept(this);
        return n;
    }
    
    public SymbolicExpression visit(FunctionCall n)
    {
        SymbolicExpression dummy = new Constant(1);
        return dummy;
    }

    public SymbolicExpression visit(Sequence n)
    {
        SymbolicExpression dummy = new Constant(1);
        return dummy;
    }

    public SymbolicExpression visit(FunctionDeclaration n)
    {
        SymbolicExpression dummy = new Constant(1);
        return dummy;
    }
    public SymbolicExpression visit(FunctionConstants n)
    {
        SymbolicExpression dummy = new Constant(1);
        return dummy;
    }
}
