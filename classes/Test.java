import org.ioopm.calculator.ast.*;
import java.lang.Math;

public class Test{
    private static Environment vars = new Environment();
    
    public static void testEvaluating(SymbolicExpression expected, SymbolicExpression e) {
        SymbolicExpression r = e.eval(vars);
        if (r.equals(expected)) {
            System.out.println("Passed: " + e);
        } 
        else {
            System.out.println("Error: expected '" + expected + "' but got '" + e + "'");
        }
    }
    
    public static void testPrinting(String expected, SymbolicExpression e) {
        if (expected.equals("" + e)) {
            System.out.println("Passed: " + e);
        } 
        else {
            System.out.println("Error: expected '" + expected + "' but got '" + e + "'");
        }
    }
    
    // public static void testGetValue(){
    //     Constant c1 = new Constant(5);
        
    //     void assertEquals(5, c1.getValue()); 
        
    // }
    
    public static void main(String [] args) {
        Constant c1 = new Constant(5);
        Constant c2 = new Constant(2);
        Constant c3 = new Constant(15);
        Constant c4 = new Constant(25);
        
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        Variable z = new Variable("z");
        Variable q = new Variable("q");
        
        Addition a = new Addition(c1, x);
        Addition add = new Addition(c1, c2);
        Addition add2 = new Addition(c3, c4);
        Addition add3 = new Addition(add, add2);
        Subtraction sub = new Subtraction(c4, c1);
        Division division = new Division(c3,c4);
        Multiplication m = new Multiplication(a, c2);
        Multiplication m2 = new Multiplication(division, sub);
        Sin sin = new Sin(sub);
        Cos cos = new Cos(sin);
        Log log = new Log(division);
        
        
        Constant PI = new Constant(Math.PI);
        Division div = new Division(PI, c2);
        Sin sin2 = new Sin(div);
        
        if (Constants.namedConstants.containsKey("L")) {
            SymbolicExpression result = new NamedConstant("L", Constants.namedConstants.get("L"));
            System.out.println(result);
            System.out.println(result.eval(vars));
            
            Assignment ass2 = new Assignment(c1, result);
            //  ass2.eval(vars);    Makes exception which is correct!
        }
        
        Assignment ass = new Assignment(c3, x);
        Assignment ass3 = new Assignment(c4, y);
        Assignment ass4 = new Assignment(c2, z);
        Assignment ass5 = new Assignment(c1, q);
        
        ass.eval(vars);         // ett måste.
        ass3.eval(vars); 
        ass4.eval(vars); 
        ass5.eval(vars); 
        
        testPrinting("(5.0 + x) * 2.0", m);
        // testPrinting("cos(sin(5.0 - z))", cos);
        testPrinting("log(15.0 / 25.0)", log);
        /////////////////////////////////////////////////////////////////////////////////////////////////
        Constant c50 = new Constant(50);
        Constant c7 = new Constant(7);
        Constant c8 = new Constant(47);
        Constant c9 = new Constant(12);
        Constant c10 = new Constant(1.0);
        
        testEvaluating(c7, add);
        testEvaluating(c8, add3);
        testEvaluating(c9, m2);

        testEvaluating(c10, sin2);
        testEvaluating(c3, x);
        
        Vars.instance().run(vars);
        
        Clear.instance().run(vars);
        
        Vars.instance().run(vars);
        
        Quit.instance().run();
        
        Vars.instance().run(vars);
    }
}
