JUNIT     = -cp .:/usr/share/java/junit4-4.5.jar:/usr/share/java/hamcrest/core-1.1.jar
COMPILER  = javac $(JUNIT)
EXECUTER  = java $(JUNIT)
TESTFILES = \
classes.Tests

all:
	javac -d classes *.java

run: all
	java -cp classes org.ioopm.calculator.Calculator
	
func: all
	java -cp classes org.ioopm.calculator.Calculator < func.txt

runTests:
	$(EXECUTER) org.junit.runner.JUnitCore $(TESTFILES)
	
inputtest1:
	java -cp classes org.ioopm.calculator.Calculator < input1.txt > output1.txt
	diff expected_output1.txt output1.txt
	
inputtest2:
	java -cp classes org.ioopm.calculator.Calculator < input2.txt > output2.txt
	diff expected_output2.txt output2.txt

inputtest3:
	java -cp classes org.ioopm.calculator.Calculator < input3.txt > output3.txt
	diff expected_output3.txt output3.txt
	
test:
	javac -d classes Test.java
	java -cp classes Test

tests:
	cd classes/
	javac -cp hamcrest-core-1.3.jar:junit-4.13.1.jar:. Tests.java
	java -cp hamcrest-core-1.3.jar:junit-4.13.1.jar:. org.junit.runner.JUnitCore Tests