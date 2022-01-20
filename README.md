# How to use
* Makefile
    -   This programme uses Makefile in order to compile and run the calculator 
        All of these commands are used with the prefix "make" e.g "make all".

* Commands
    - all           compiles all used .java files
    - run           Starts the calculator if all files have been compiled correctly
    - clean         Removes generated files

* User Interface
    - After running the programme you can type in any kind of expression and the calculator will evaluate it e.g (2 + 5) will print: 7.0.
    - You can also assign values to variables e.g (2 = x) stores the constant 2 as x in a hash map. If you then enter x as an expression and evaluate it the console will print: 2.0
    - If you want to enter more complicated expressions using variables dependent on each other you will have to declare the most independent variables first. 
      For example, if you want to enter (2*x) + Log(z) = y and you want to evaluate y you will have to give x and z a value before entering the entire expression e.g
      3 = x
      3.0
      2 = z
      2.0
      (2*x) + Log(z) = y
      4.477121254719663
    - All functions must be written with a capital first letter. "sin(5)" will not evaluate but "Sin(5)" will. All of these "special" functions are:
      Cos(x) - gets the cosine value of a x
      Sin(x) - gets the sine value of a x
      Log(x) - gets the logarithm (in base 10) of a x
      Exp(x) - This method returns the value e^x, where e is the base of the natural logarithms.
      The rest of the functions can be written as normal:
      Division: x / y
      Addition: x + y
      Subtraction: x - y
      Multiplication: x * y
    - The calculator also has fixed constants. These are:
      pi: 3.1415...
      e: Euler's number, 2.71...


# Error handling
* Wrong input
    If the user input is not within the boundaries of this calculator, the user will be prompted with an exception describing the error.

* Variables
    - If the user sets a variable x to another variable y that has not been evaluated, it will just hold that variable name x.
    Even if the user later changes the variable x to a evaluated expression, the y will still hold x since this is not reference semantics.

# Notes


