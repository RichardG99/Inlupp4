package org.ioopm.calculator;
import java.util.*;
import org.ioopm.calculator.ast.*;
import org.ioopm.calculator.parser.*;

public class ReassignmentChecker implements Visitor {
    
    private List<Variable> variables = new ArrayList<>();
    
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
    
    private boolean arrayContains(Variable variable){
        if(this.variables.contains(variable)) return true;
        return false;
        
    }
    
    // Recurse down the AST tree
    public SymbolicExpression visit(Addition a) {
        a.getLhs().accept(this);
        a.getRhs().accept(this);
        return a; // No need to create a new tree
    }
    

    // When we hit an assignment, make sure to check!
    public SymbolicExpression visit(Assignment a) {
        SymbolicExpression right = a.getRhs().accept(this);
        SymbolicExpression left = a.getLhs().accept(this);
        if(arrayContains((Variable) right)){
            this.variables.clear();
            throw new IllegalExpressionException("Variable is reassigned: \n" + a.getLhs() + " = " + a.getRhs());
            
            }
        else{
            this.variables.add((Variable) right);  
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