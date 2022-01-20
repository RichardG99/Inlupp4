package org.ioopm.calculator;

import org.ioopm.calculator.ast.*;
import org.ioopm.calculator.parser.*;
import java.util.Scanner;
import java.io.*;
import java.util.*;

public class Calculator{
    private static CalculatorParser parser; // = new CalculatorParser();
    private static Environment vars; // = new Environment();
    public static FunctionCall functions = new FunctionCall();
    
    public FunctionCall getFunctions(){
        return this.functions;
    }
    
    public void setFunctions(FunctionCall funcs){
        this.functions = funcs;
    }
    
    
    public static void main(String[] args){
        parser = new CalculatorParser();
        vars = new Environment();
        int choice;
        int totEvalExp = 0;
        int totDoneExp = 0;
        int totNotDoneExp = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        while(true){
            
            try{
                String input = br.readLine();
                if(input == null){
                    input = "Quit";
                }
                final SymbolicExpression expression = parser.parse(input, vars, functions); // from string to symblicexpression
                final Visitor evaluator = new EvaluationVisitor();
                final Visitor namedChecker = new NamedConstantChecker();
                final Visitor reassChecker = new ReassignmentChecker();
                if(expression instanceof Command) choice = 0;
                else choice = 1;
                
                switch(choice){  
                    case 0:
                        if(expression instanceof Quit)
                        {
                            System.err.println("Thank you for using our wonderful calculator!");
                            System.err.println("Total amount of expressions: " + totEvalExp);
                            System.err.println("Total fully evaluated expressions: " + totDoneExp);
                            System.err.println("Total not fully evaluated expressions: " + totNotDoneExp);
                            Quit.instance().run();
                        }
                        else if (expression instanceof Vars) Vars.instance().run(vars);
                        else Clear.instance().run(vars);
                        break;
                        
                    case 1:
                        boolean checkName = namedChecker.check(expression);
                        if (!checkName) break;
                        boolean checkReass = reassChecker.check(expression);
                        if(checkName && checkReass){
                            if(expression instanceof FunctionDeclaration){
                                System.out.println("Function successfully defined!\n");
                                FunctionDeclaration function = (FunctionDeclaration) expression;
                                functions.insertFunc(function);
                                CalculatorParser.functionDecs.add(function);
                            }
                            else{
                                SymbolicExpression result = evaluator.evaluate(expression, vars);
                                System.out.println(result);
                                if(result.isConstant()){
                                    totDoneExp = totDoneExp + 1;
                                }
                                else totNotDoneExp = totNotDoneExp + 1;
                                totEvalExp = totEvalExp + 1;
                                if (result instanceof Constant){
                                    Constants.namedConstants.put("ans", result.getValue());
                                }
                            }
                        }
                        break;
                }
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}