package org.ioopm.calculator;

import org.ioopm.calculator.ast.*;
import org.ioopm.calculator.parser.*;
import java.util.*;

public class EvaluationVisitor implements Visitor {
    //private Environment env = null;
    private LinkedList<Environment> envLocals = new LinkedList<Environment>();
    
    public boolean check(SymbolicExpression a){
        return false;
    }
    
    private boolean inRecursion = false;
    
    @Override
    public SymbolicExpression evaluate(SymbolicExpression topLevel, Environment env) {
        this.envLocals.push(env);
        return topLevel.accept(this);
    }
    
////////// BINARY ////////////

    // This method gets called from Addition.accept(Visitor v) -- you should
    // be able to see from the eval() methods how these should behave (i.e., 
    // compare this method with your Addition::eval() and Symbolic.addition) 
    @Override
    public SymbolicExpression visit(Addition n) {
        // Visit the left hand side and right hand side subexpressions
        SymbolicExpression left = n.getLhs().accept(this);
        SymbolicExpression right = n.getRhs().accept(this);
        // When we come back here, the visitor has visited all subexpressions, 
        // meaning left and right point to newly created trees reduced to 
        // the extent possible (best case -- both are constants)

        // If subexpressions are fully evaluated, replace them in
        // the tree with a constant whose value is the sub of the
        // subexpressions, if not, simply construct a new addition
        // node from the new subexpressions
        if (left.isConstant() && right.isConstant()) {
            return new Constant(left.getValue() + right.getValue());
            
        } else {
            return new Addition(left, right);
        }
    }
    
    @Override
    public SymbolicExpression visit(Subtraction n) {
     
        SymbolicExpression left = n.getLhs().accept(this);
        SymbolicExpression right = n.getRhs().accept(this);
        
        if (left.isConstant() && right.isConstant()) {
            return new Constant(left.getValue() - right.getValue());
        } else {
            return new Subtraction(left, right);
        }
    }
    
    
    @Override
    public SymbolicExpression visit(Division n) {
     
        SymbolicExpression left = n.getLhs().accept(this);
        SymbolicExpression right = n.getRhs().accept(this);
        
        if (left.isConstant() && right.isConstant()) {
            return new Constant(left.getValue() / right.getValue());
        } else {
            return new Division(left, right);
        }
    }
    
    @Override
    public SymbolicExpression visit(Multiplication n) {
     
        SymbolicExpression right = n.getRhs().accept(this);
        SymbolicExpression left = n.getLhs().accept(this);
        
        
        if (left.isConstant() && right.isConstant()) {
            return new Constant(left.getValue() * right.getValue());
        } else {
            return new Multiplication(left, right);
        }
    }
    
    
    @Override
    public SymbolicExpression visit(Assignment n) {
        
        SymbolicExpression left = n.getLhs().accept(this);
        SymbolicExpression right = n.getRhs();
        
        
        if(right.isConstant())
        {
           // throw new IllegalExpressionException("Error: Righthandside is named constant");
        }
        else{
            this.envLocals.peek().put((Variable) right, left);
        }
        return left;
    
        }
    
    
////////// UNARY ////////////

    @Override
    public SymbolicExpression visit(Cos n) {
     
        SymbolicExpression sub = n.getUnaryValue().accept(this);
        
        if (sub.isConstant()) {
            return new Constant(Math.cos(sub.getValue()));
        } else {
            return new Cos(sub);
        }
    }
    
    @Override
    public SymbolicExpression visit(Sin n) {
     
        SymbolicExpression sub = n.getUnaryValue().accept(this);
        
        if (sub.isConstant()) {
            return new Constant(Math.sin(sub.getValue()));
        } else {
            return new Sin(sub);
        }
    }
    @Override
    public SymbolicExpression visit(Exp n) {
     
        SymbolicExpression sub = n.getUnaryValue().accept(this);
        
        if (sub.isConstant()) {
            return new Constant(Math.exp(sub.getValue()));
        } else {
            return new Exp(sub);
        }
    }
    @Override
    public SymbolicExpression visit(Log n) {
     
        SymbolicExpression sub = n.getUnaryValue().accept(this);
        
        if (sub.isConstant()) {
            return new Constant(Math.log(sub.getValue()));
        } else {
            return new Log(sub);
        }
    }
    @Override
    public SymbolicExpression visit(Negation n) {
     
        SymbolicExpression sub = n.getUnaryValue().accept(this);
        
        if (sub.isConstant()) {
            return new Constant(sub.getValue() * -1);
        } else {
            return new Negation(sub);
        }
    }
    
////////// ATOMS  ////////////
    @Override
    public SymbolicExpression visit(Variable n) {
           Iterator<Environment> iter = envLocals.iterator();
            SymbolicExpression result = null;
            while(iter.hasNext()){
                Environment current = iter.next();
            if(!current.containsKey(n)) continue;
                result = current.get(n);
            }
        if(result != null) return result;
        return new Variable(n.toString());
    }
    
    @Override
    public SymbolicExpression visit(Constant n){
        return new Constant(n.getValue());
    }

    @Override
    public SymbolicExpression visit(NamedConstant n){
        return new NamedConstant(n.getName(), n.getValue());
    }

////////// COMMANDS  ////////////

    @Override
    public SymbolicExpression visit(Clear n) 
    {
        throw new IllegalExpressionException("evaluate called on expression with no evaluation");
    }

    @Override
    public SymbolicExpression visit(Quit n) 
    {
        throw new IllegalExpressionException("evaluate called on expression with no evaluation");
    }
    @Override
    public SymbolicExpression visit(Vars n) 
    {
        throw new IllegalExpressionException("evaluate called on expression with no evaluation");
    }
    
    @Override
    public SymbolicExpression visit(Scope n)
    {
        //envLocals.push(new Environment());
        SymbolicExpression expression = n.arg.accept(this);
        //envLocals.pop();
        return expression;
    }
    
    @Override
    public SymbolicExpression visit(Conditional n){
        SymbolicExpression idL = n.getidL().accept(this);
        SymbolicExpression idR = n.getidR().accept(this);
        String operator = n.getIdOperator();
        SymbolicExpression result = null;
        if(!idL.isConstant() || !idR.isConstant()){
            throw new IllegalExpressionException("Cannot evaluate undefined variables: " + idL + " " + operator + " " + idR);
        }
        switch(operator){
            case ">":
                if  (idL.getValue() > idR.getValue()){
                    result = n.getIf().accept(this);
                    return result;
                }
                else{
                    result = n.getElse().accept(this);
                    return result;
                }
            case "<":
                if (idL.getValue() < idR.getValue()){
                    result = n.getIf().accept(this);
                    return result;
                }
                else{
                    result = n.getElse().accept(this);
                    return result;
                }
            case ">=":
                if (idL.getValue() >= idR.getValue()){
                    result = n.getIf().accept(this);
                    return result;
                }
                else{
                    result = n.getElse().accept(this);
                    return result;
                }
            case "<=":
                if (idL.getValue() <= idR.getValue()){
                    result = n.getIf().accept(this);
                    return result;
                    
                }
                else{
                    result = n.getElse().accept(this);
                    return result;
                }
            case "==":
                if (idL.getValue() == idR.getValue()){
                    result = n.getIf().accept(this);
                    return result;
                }
                else{
                    result = n.getElse().accept(this);
                    return result;
                }
        }
        return result;
    }
    
    public SymbolicExpression visit(FunctionCall n)
    {
        SymbolicExpression dummy = new Constant(1);
        return dummy;
    }
    public SymbolicExpression visit(Sequence n)
    {
        SymbolicExpression result = null;
        LinkedList<SymbolicExpression> operations = n.getBody();
        
        for(int i = 0; i < operations.size(); i++)
        {
            result = operations.get(i).accept(this);
        }
        return result;
    }
    
    public SymbolicExpression visit(FunctionDeclaration n)
    {
        inRecursion = true;
        SymbolicExpression expression = n.body.accept(this);
        return expression;
    }
    public SymbolicExpression visit(FunctionConstants n)
    {
        String name = n.getname();
        FunctionDeclaration properFunc = null;
        for (FunctionDeclaration x : CalculatorParser.functionDecs){
            if (name.equals(x.getName())){
                properFunc = x;
            }
        }
        
        LinkedList<SymbolicExpression> constants = n.getConstants();
        Variable[] args = properFunc.getArgs();
        LinkedList<SymbolicExpression> operations = properFunc.getSequences();
        if(args.length != constants.size()){
           throw new SyntaxErrorException("Expected " + args.length + " arguments to function " + properFunc.getName() + ", got " + constants.size() + "."); 
        }
        Assignment ass;
        if(args.length > 0){
            for(int i = 0; i < args.length; i++){
                SymbolicExpression result = constants.get(i).accept(this);
                ass = new Assignment(result, args[i]);
                ass.accept(this);
            }
        }
        SymbolicExpression expression = properFunc.accept(this); 
        
        
        return expression;
    }
}