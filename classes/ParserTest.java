import org.ioopm.calculator.ast.*;
import org.ioopm.calculator.parser.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class ParserTest{
    @Test
    public void test1(){
        Constant c1 = new Constant(5);
        Constant c2 = new Constant(10);
        Addition a1 = new Addition(c1, c2);
        Subtraction s1 = new Subtraction(c2, c1);
        Division d1 = new Division(a1, s1);
        String input = d1.toString();
        System.out.print("\n");
        System.out.println(input);
        
    }
        
}