"This assignment's submission is my own work and I did not discuss with any other past or current student, 
nor did I have access to a previous submission of this assignment by another student."
Nagarajan Gopal
 [Date:05/06/2018]
------------------------------------------------------------------------
Assuming you are in the directory containing this README:

## To clean:
ant -buildfile src/build.xml clean

-----------------------------------------------------------------------
## To compile:
ant -buildfile src/build.xml all

-----------------------------------------------------------------------
## To run by specifying arguments from command line
## We will use this to run your code
ant -buildfile src/build.xml run -Darg0=serdeser -Darg1=checkpoint.txt -Darg2=4 -Darg3=1

arg0 - mode - serdeser or deser //serdeser by run default for other inputs
arg1 - filename to read or write
arg2 - Number of objects to generate or read. In serdeser N - MyAllTypesFirst
and 2N- MyAllTypesSecond  are randomly created. In Deser - Read N objects
arg3 - Debug level : 
        0 -  To print None
        1 -  To print debugging in order to view Mismatch count   
-----------------------------------------------------------------------
##To generate a javadoc from command line
ant -buildfile src/build.xml doc

-----------------------------------------------------------------------

Data Structure used:
ArrayList - To hold SerializableObject which used in comparison to figure out mismatch count. 


