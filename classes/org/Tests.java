import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Tests {

    @Test
    public void testCase1() {
        
        Constant c1 = new Constant(5);
        Constant c2 = new Constant(10);
        Addition a1 = new Addition(c1, c2);
        
        // assert statements
        assertEquals(15.0, a1.eval(), "10 x 0 must be 0");
     
           
    }
}