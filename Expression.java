package assignments2017.A2PostedCode;

/*

 */

import java.util.Stack;
import java.util.ArrayList;
import java.util.Arrays;

public class Expression  {
	private ArrayList<String> tokenList;

	//  Constructor    
	/**
	 * The constructor takes in an expression as a string
	 * and tokenizes it (breaks it up into meaningful units)
	 * These tokens are then stored in an array list 'tokenList'.
	 */

	Expression(String expressionString) throws IllegalArgumentException{
		tokenList = new ArrayList<String>();
		//StringBuilder token = new StringBuilder();

		//ADD YOUR CODE BELOW HERE
		int i = 0;
		while(i < expressionString.length())
		{
			//if it is a number
			String tempList = "";
			//while loop groups together digits to form an integer (multi-digit)
			while(i<expressionString.length() && Character.isDigit(expressionString.charAt(i)))
			{
				tempList += expressionString.charAt(i);
				
				if(i < expressionString.length())
					i++;
			}
			
			if (!tempList.isEmpty())
			{
							tokenList.add(tempList);
							tempList = "";
			}
			
			//if it is an operator
			//while loop groups together consequently occurring operators (++ or --)
			while(i<expressionString.length() && (expressionString.charAt(i) == '+' || expressionString.charAt(i) == '-' || expressionString.charAt(i) == '*' || expressionString.charAt(i) == '/' || expressionString.charAt(i) == '[' || expressionString.charAt(i) == ']'))
			{
				tempList += expressionString.charAt(i);
				
				if(i < expressionString.length())
					i++;
			}
			if (!tempList.equals(""))
			{
							tokenList.add(tempList);
							tempList = "";
			}

			
			//if it is a parenthesis
			if(i<expressionString.length() && (expressionString.charAt(i) == '(' || expressionString.charAt(i) == ')'))
			{
				tempList = Character.toString(expressionString.charAt(i));
				tokenList.add(tempList);
				
				if(i < expressionString.length())
					i++;
			}
			
			//if it is a blank space
			if(i<expressionString.length() && expressionString.charAt(i) == ' ')
				i++;

		}
		
		//ADD YOUR CODE ABOVE HERE
	}

	/**
	 * This method evaluates the expression and returns the value of the expression
	 * Evaluation is done using 2 stack ADTs, operatorStack to store operators
	 * and valueStack to store values and intermediate results.
	 * - You must fill in code to evaluate an expression using 2 stacks
	 */
	public Integer eval(){
		Stack<String> operatorStack = new Stack<String>();
		Stack<Integer> valueStack = new Stack<Integer>();
		
		//ADD YOUR CODE BELOW HERE
		
		int i = 0;
		while(i<tokenList.size())
		{
			if(isInteger(tokenList.get(i)))
				valueStack.push(Integer.valueOf(tokenList.get(i)));
			else if(tokenList.get(i).equals("+") || tokenList.get(i).equals("-") ||tokenList.get(i).equals("*") ||tokenList.get(i).equals("/") ||tokenList.get(i).equals("++") ||tokenList.get(i).equals("--"))
				operatorStack.push(tokenList.get(i));
			else if(tokenList.get(i).equals(")"))
			{
				String operator = operatorStack.pop();
				if(operator.equals("+"))
				{
					int operand2 = valueStack.pop();
					int operand1 = valueStack.pop();
					valueStack.push(operand2 + operand1);
				}
				else if(operator.equals("-"))
				{
					//note the order of popping - operand2 is popped first
					int operand2 = valueStack.pop();
					int operand1 = valueStack.pop();
					valueStack.push(operand1 - operand2);
				}
				else if(operator.equals("*"))
				{
					int operand2 = valueStack.pop();
					int operand1 = valueStack.pop();
					valueStack.push(operand1 * operand2);
				}
				else if(operator.equals("/"))
				{
					//note the order of popping - operand2 is popped first
					int operand2 = valueStack.pop();
					int operand1 = valueStack.pop();
					valueStack.push(operand1 / operand2);
		  		}
				else if(operator.equals("++"))
				{
					 int operand = valueStack.pop();
					 valueStack.push(++operand);
				}
				else if(operator.equals("--"))
				{
					 int operand = valueStack.pop();
					 valueStack.push(--operand);
				}
			}

			else if(tokenList.get(i).equals("["))
			{
				String expr = "";
				
				//moves cursor to input after [
				i++;
				
				//combines everything inside [ and ] to form a new expression
				while(!tokenList.get(i).equals("]"))
				{
					expr = expr.concat(tokenList.get(i));
					i++;
				}
				
				//gets rid of ")", which is the term after ]
				i++;
				
				Expression newExpr = new Expression(expr);
				int ans = newExpr.eval();
				if(ans>0)
					valueStack.push(ans);
				else if (ans<0)
					valueStack.push(-ans);
			}
			
			//to move along to the next token in the while loop
			i++;
		}
		
		//computed answer is the remaining value
		return valueStack.pop();
		
		//ADD YOUR CODE ABOVE HERE
	}

	//Helper methods
	/**
	 * Helper method to test if a string is an integer
	 * Returns true for strings of integers like "456"
	 * and false for string of non-integers like "+"
	 * - DO NOT EDIT THIS METHOD
	 */
	private boolean isInteger(String element){
		try{
			Integer.valueOf(element);
		}catch(NumberFormatException e){
			return false;
		}
		return true;
	}

	/**
	 * Method to help print out the expression stored as a list in tokenList.
	 * - DO NOT EDIT THIS METHOD    
	 */

	@Override
	public String toString(){	
		String s = new String(); 
		for (String t : tokenList )
			s = s + "~"+  t;
		return s;		
	}

}

