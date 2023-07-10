import java.util.ArrayList;
import java.util.Scanner;

//Name of Class
public class Basics
{
	//this is where you declare class wide variables with the modifiers public, private, and protected
	
	public static int a = 0;
	private double b = 0;
	protected String animal = "Dolphin";
	
	// This is where the code runs
	public static void main(String [] args) 
	{
		
		//Declaration and instantiation of all Basics
		int x = 0; // a int
		double y = 0;// a double
		String word = "Hello World"; // a String
		char letter = 'a'; // a Char
		Object thing = new Object();   Object thing2 = new Object(); Object thing3 = new Object(); // objects
		Scanner userInput = new Scanner(System.in); // <-- Import Java.util a scanner
		
		Basics instance = new Basics(); // an instance of a class
		//Basic Scanner methods
		System.out.println("Give me a integer");
		// int userNum = userInput.nextInt();
		
		System.out.println("Give me a double");
		// double userDouble = userInput.nextDouble();
		
		System.out.println("Give me a word");
		// String userWord = userInput.next();
		
		System.out.println("Give me a phrase");
		// String userPhrase = userInput.nextLine();
		
		System.out.println("Give me a letter");
		// char userLetter = (char) userInput.next().charAt(0);
		
		
			
		//Declaration and instantiation of Arrays and ArrayList
		
		//Arrays <-- Set Size
		
		int[] numList = {1,2,3}; //  <-- with literal
		int[] numList2 = new int[20]; // <-- specific size non literal
		
		double[] decList = {1.0,2.0,3.0}; // <-- with literal
		double[] decList2 = new double[20];
		
		String[] wordList = {"Dog", "Cat", "Mouse"};// <-- with literal
		String[] wordList2 = new String[20];// <-- specific size non literal
		
		char[] letterList = {'a','b','c'}; // <-- with literal
		char[] letterList2 = new char[20]; // <-- specific size non literal
		
		//ArrayList <-- Expands with each new variable and needs import
		
		ArrayList<Integer> arrInt = new ArrayList<>();
			arrInt.add(1); //adds to the end
			arrInt.add(0,2); //adds to the specified index 
			arrInt.set(0, 3); //Changes value at specific index 
			arrInt.remove(1); //removes object at specified index 
		
		ArrayList<Double> arrDouble = new ArrayList<>();
			arrDouble.add(1.0); //adds to the end
			arrDouble.add(0,2.0); //adds to the specified index 
			arrDouble.set(0, 3.0); //Changes value at specific index 
			arrDouble.remove(1); //removes object at specified index 
		
		ArrayList<String> arrWord = new ArrayList<>();
			arrWord.add("Dog"); //adds to the end
			arrWord.add("Mouse");
			arrWord.add(0,"Cat"); //adds to the specified index 
			arrWord.set(0, "Green"); //Changes value at specific index 
			arrWord.remove(1); //removes object at specified index 
			arrWord.remove("Mouse");//removes first instance of object 
		
		ArrayList<Character> arrLetter = new ArrayList<>();
			arrLetter.add('a'); //adds to the end
			arrLetter.add(0,'b'); //adds to the specified index 
			arrLetter.set(0, 'z'); //Changes value at specific index 
			arrLetter.remove(1); //removes object at specified index 
		
		ArrayList<Object> arrObject = new ArrayList<>();
			arrObject.add(thing); //adds to the end
			arrObject.add(0,thing2);//adds to the specified index 
			arrObject.set(0, thing3);
			arrObject.remove(0); //removes object at specified index
			arrObject.remove(thing2);//removes first instance of object <-- need an object
			
			
		//Loops - For, While, Do While
			//Broken down
			for(int i = 0;//Declaring the counter variable
					i < 5;//Setting how long the loop will run
					i++) {//Setting how fast the counter increases
						}
			//proper form
			for(int i = 0;i < 5;i++) 
			{
				
			}
			
			//for each loop
			for(int runner: numList) { // uses the element itself, very useful for an array of objects
			}
		
			while(x < 5 ) //Loop runs while condition is true, will terminate when false
			{
				x++; //need a counter to prevent infinte loop
			}
			
			do {
			//will do one iteration before starting counter 	
			x++;	
			}while(x < 5); //Loop runs while condition is true, will terminate when false
			
		//Traversing an array
			for(int i = 0; i < numList.length; i++) 
			{
				System.out.println(numList[i]);//will hit every spot of the array
			}
		//Traversing an arrayList	
			arrDouble.add(2.8); arrDouble.add(8.6);
			for(int i = 0; i < arrDouble.size(); i++) //HAVE to use .size for arrayList
			{
				System.out.println(arrDouble.get(i));//will hit every spot of the array
			}
			
		// Calling a method
			//using an instance variable
				instance.addition();
				instance.addition(5,4);
			//using a static method
				subtraction();
				subtraction(3.1,9.8);
				change(2);
	}
	
	//Method Construction
	/*There are public, private, and protected methods. All methods need a return type. The return 
	 * types are int, double, String, char, void, and an object. Methods can also take in parameters
	 * 
	 */
	public int addition() // returns int
	{ 
		int x = 2;
		int y = 4;
		int z = x + y;
		return z;
	}
	
	public int addition(int x, int y) // returns int, an example of overloading
	{ 
		int z = x + y;
		return z;
	}
	
	private static double subtraction() // returns double
	{
		double x = 2.4;
		double y = 6.8;
		double z = x + y;
		return z;
	}
	
	private static double subtraction(double x, double y) //returns double, example of overloading
	{
		double z = x + y;
		return z;
	}
	
	public static void change(int x) // void returns nothing. It is used to changed values and other things
	{
		System.out.println("old a" + a);
		a = x;
		System.out.println("new a" + a);
	}
}
