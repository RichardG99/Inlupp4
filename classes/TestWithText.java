import java.io.*;

public class TestWithText {
    public static void main(String[] args) {
        // The expected string
        String expected = "Foo";

        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(buf);
        // Write output to "out" instead of System.out
        out.println("Foo");

        // Get the string written to out
        String output = buf.toString(); 

        // Compare with expected 
        System.out.print(output.equals(expected));
    }
}