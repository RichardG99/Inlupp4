all:
	javac -d classes *.java
	
run:
	java -cp classes org.ioopm.calculator.Calculator
	
test:
	javac Test.java
	java Test

testtext:
	javac TestWithText.java
	java TestWithText < input1.txt > output1.txt

tests:
	javac -cp hamcrest-core-1.3.jar:junit-4.13.1.jar:. Tests.java TestConstant.java TestScope.java
	java -cp hamcrest-core-1.3.jar:junit-4.13.1.jar:. org.junit.runner.JUnitCore Tests TestConstant TestScope

parsertest:
	javac -cp hamcrest-core-1.3.jar:junit-4.13.1.jar:. ParserTest.java
	java -cp hamcrest-core-1.3.jar:junit-4.13.1.jar:. org.junit.runner.JUnitCore ParserTest
	
clean:
	rm *.class