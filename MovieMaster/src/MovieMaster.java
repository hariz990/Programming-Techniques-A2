import java.io.*;
import java.util.Scanner;

public class MovieMaster 
{
	ExceptionClass exception = new ExceptionClass();
	Item itemDetails;
	Item[] items = new Item[99];
	int records = 0;
	
	boolean idFound = false;
	boolean idFound2 = false;
	boolean borrowID = false;
	boolean onLoan = false;
	int position = 0;
	int date = 0;
	
	Scanner scanner = new Scanner(System.in);
	String filename = "ItemFile.txt";
	PrintWriter outputStream = null;
	
	public void printMenu()
	{
		boolean quit = false; 
		
		while(quit != true)
		{
		System.out.println("*** Item Master System Menu ***");
		System.out.println("Add Item" + "\t\t A");
		System.out.println("Borrow Item" + "\t\t B");
		System.out.println("Return Item" + "\t\t C");
		System.out.println("Display Item" + "\t\t D");
		System.out.println("Seed Data" + "\t\t E");
		System.out.println("Exit Program" + "\t\t X");
		
		System.out.print("Enter selection: ");
		String output = scanner.nextLine().toUpperCase();
	
			switch(output) 
			{
				case "A": 
					addItem();
				break;
				case "B": 
					borrowItem();
				break;
				case "C":
					returnItem();
				break;
				case "D":
					displayItem();
				break;
				case "E":
					seedData();
				break;
				case "X": 
					FileWriter();
					quit = true;
					System.exit(0);
				default:
					System.out.println("PLEASE ENTER CORRECT SELECTION.\n");
				break; 
			}
		}
	}
	
	public void addItem() 
	{
		String enterID = "";
		String enterNewReal = "";
		
		do{
		System.out.print("Enter ID (3 characters): ");
		enterID = scanner.nextLine().toUpperCase();
		}while(enterID.length() != 3);
		
		if(checkIDExist(enterID) == false)//CHECK ID
			{
				System.out.print("Enter title: ");
				String enterTitle = scanner.nextLine();
				System.out.print("Enter genre: ");
				String enterGenre = scanner.nextLine();
				System.out.print("Enter description: ");
				String enterDesc = scanner.nextLine();
				System.out.print("Movie or Game (M/G): ");
				String item = scanner.nextLine().toUpperCase();
				
				if(exception.movieORgame(item) == true)//EXCEPTION
				{
					if(item.equals("G"))//GAME
						{
							System.out.print("Enter Game Platform: ");
							String platform = scanner.nextLine().toUpperCase();
							
							itemDetails = new Game(enterID,enterTitle,enterGenre,enterDesc,platform);
							items[records] = itemDetails;
							records++;
							
							System.out.println("New game added succesfully for the game entitled: " + ((Game)items[records - 1]).getTitle() + "\n");
							
						}
					
					else if(item.equals("M"))//MOVIE
						{
							System.out.print("Enter new release (Y/N): ");
							enterNewReal = scanner.nextLine().toUpperCase();
						
							if(exception.yesORno(enterNewReal) == false)//EXCEPTION
							{
								System.out.println("ERROR: You must enter 'Y' or 'N'.");//2nd attempt
								System.out.print("Is New Release (Y/N)?: ");
								enterNewReal = scanner.nextLine().toUpperCase();
								
								if(exception.yesORno(enterNewReal) == false)//EXCEPTION
								{
									System.out.println("Exiting to main menu.\n");
								}//END of 2nd attempt	
								else
								{
									itemDetails = new Movie(enterID,enterTitle,enterGenre,enterDesc,enterNewReal);
									items[records] = itemDetails;
									records++;
									
									System.out.println("New movie added succesfully for the movie entitled: " + ((Movie)items[records - 1]).getTitle() + "\n");
								}
							}
							else
							{
								itemDetails = new Movie(enterID,enterTitle,enterGenre,enterDesc,enterNewReal);
								items[records] = itemDetails;
								records++;
								
								System.out.println("New movie added succesfully for the movie entitled: " + ((Movie)items[records - 1]).getTitle() + "\n");						
							}
						}
				}
			}
		else
			{
				System.out.println("ERROR: ID is already taken.");
				System.out.println("Exiting to main menu.\n");
			}
	}
	
	public void displayItem()
	{
		if(records == 0)//IF no records are added in Items Array
		{
			System.out.println("There are no movies inserted.\n");
		}
		else 
		{
			System.out.println();
			for(int i = 0; i <= records - 1; i++)
			{
				System.out.println(items[i].getDetails());
			}
		}
		
	}
	
	public void borrowItem()
	{
		if(records == 0)//IF no records are added in Items Array
		{
			System.out.println("There are no items inserted.\n");
		}
		else
		{
			System.out.print("Borrow Movie or Game (M/G)?: ");
			String answer = scanner.nextLine().toUpperCase();

			if(exception.movieORgame(answer) == true)//EXCEPTION
			{
				switch(answer)
				{
					case "M":
						getBorrowMovie();
						break;
					case "G":
						getBorrowGame();
						break;
				}
			}//END exception statement
		}//END else statement
	}
	
	public void returnItem()
	{
		int date = 0;
		String output = "";
		
		if(records == 0)
		{
			System.out.println("There are no items inserted.\n");
		}
		else
		{
			System.out.print("Movie or Game (M/G)?: ");
			String response = scanner.nextLine().toUpperCase();
			
			if(exception.movieORgame(response) == true)//EXCEPTION
			{
				if(response.equals("M"))
				{
					output += "M_";
					System.out.print("Enter ID: ");
					output += scanner.nextLine().toUpperCase();
				}
				else
				{
					output += "G_";
					System.out.print("Enter ID: ");
					output += scanner.nextLine().toUpperCase();
				}
				
				//CHECK Item status
				checkItem(output);
				
				if(idFound2 == true && onLoan == true)//RETURNING statement
				{
					System.out.print("Enter number of days on loan: ");
					String input = scanner.nextLine();
					
					if(exception.numberOrNot(input) == true)//INTEGER EXCEPTION
					{
						date = Integer.parseInt(input);
						System.out.println("Total Fees to be paid: $" + items[position].returnItem(new DateTime(date)) + "\n");
					}
					
				}
				else if(idFound2 == true && onLoan == false)//NOT ONLOAN statement
				{
					System.out.println("The item is NOT currently being loaned.\n");
				}
				else//Statement if ID is not found
				{
					System.out.println("ERROR: The item with id number: " + output + ", not found." + "\n");
				}
			}//END exception statement
		}//END else statement
	}
	
	public void seedData()
	{
		if(records == 0)//Engaged when items Array is not being used
		{
			System.out.println("ITEM RECORD INITIATED\n");
			//Weekly Movies
			Item a = new Movie("AAA", "title", "Action", "movie", "N");
			items[records] = a;
			records++;
			
			Item b = new Movie("BBB", "title", "Drama", "movie", "N");
			items[records] = b;
			items[records].borrow("JLK", 0);
			records++;
			
			Item c = new Movie("CCC", "title", "History", "movie", "N");
			items[records] = c;
			items[records].borrow("PPP", 0);
			items[records].returnItem(new DateTime(5));
			records++;
			
			Item d = new Movie("DDD", "title", "History", "movie", "N");
			items[records] = d;
			items[records].borrow("EEE", 0);
			items[records].returnItem(new DateTime(10));
			records++;
			
			Item e = new Movie("EEE", "title", "Drama", "movie", "N");
			items[records] = e;
			items[records].borrow("RRR", 0);
			items[records].returnItem(new DateTime(10));
			items[records].borrow("OOO", 0);
			records++;
			
			//New Release movies
			Item f = new Movie("FFF", "title", "Action", "movie", "Y");
			items[records] = f;
			records++;
			
			Item g = new Movie("GGG", "title", "History", "movie", "Y");
			items[records] = g;
			items[records].borrow("UUU", 0);
			records++;
			
			Item h = new Movie("HHH", "title", "History", "movie", "Y");
			items[records] = h;
			items[records].borrow("RRR", 0);
			items[records].returnItem(new DateTime(1));
			records++;
			
			Item j = new Movie("III", "title", "Drama", "movie", "Y");
			items[records] = j;
			items[records].borrow("WWW", 0);
			items[records].returnItem(new DateTime(3));
			records++;
			
			Item k = new Movie("JJJ", "title", "Action", "movie", "Y");
			items[records] = k;
			items[records].borrow("QQQ", 0);
			items[records].returnItem(new DateTime(3));
			items[records].borrow("OOO", 0);
			records++;
			
			//GAME
			Item l = new Game("KKK", "title", "Fighting", "game", "Xbox 360");
			items[records] = l;
			records++;
			
			Item m = new Game("LLL", "title", "Dancing", "game", "Xbox 360, PS4");
			items[records] = m;
			items[records].borrow("QWE", 0);
			records++;
			
			Item o = new Game("MMM", "title", "Action", "game", "PS4");
			items[records] = o;
			((Game)items[records]).borrow("WWW", 0, false);
			items[records].returnItem(new DateTime(19));
			records++;
			
			Item p = new Game("NNN", "title", "Fighting", "game", "PS4, Xbox 360");
			items[records] = p;
			((Game)items[records]).borrow("WWW", 0, false);
			items[records].returnItem(new DateTime(32));
			records++;
			
		}
		else
		{
			System.out.println("ERROR: Item Record is being used.\n");
		}
	}
	
	//GET methods
	public void getBorrowMovie()
	{
		//Borrowing Movies 
		System.out.print("Enter Movie ID: ");
		String output = "M_" + scanner.nextLine().toUpperCase();
		
		//CHECK Item status
		checkItem(output);
		
		if(idFound2 == true && onLoan == false)//BORROW statement
			{
				System.out.print("Enter member ID: ");
				String response = scanner.nextLine().toUpperCase();
	
				System.out.print("Advance borrow (days): ");
				String input = scanner.nextLine();
				if(exception.numberOrNot(input) == true)//INTEGER EXCEPTION
				{
					date = Integer.parseInt(input);
					System.out.println("The movie " + items[position].getTitle() + " costs $" + items[position].borrow(response, date) + " and is due on: " + new DateTime(date).getFormattedDate() + "\n");
				}
			}
		else if(idFound2 == true && onLoan == true)//Movie is being loaned statement
			{
				System.out.println("The movie is currently being loaned.\n");
			}
		else 
			{
				System.out.println("ERROR: The item with id number: " + output + ", not found." + "\n");
			}
	}
	
	public void getBorrowGame()
	{
		System.out.print("Enter Game ID: ");
		String output = "G_" + scanner.nextLine().toUpperCase();
		System.out.print("Enter member ID: ");
		String response = scanner.nextLine().toUpperCase(); 
		
		//CHECK Game status
		checkGame(output, response);
		
		if(idFound == true && onLoan == false)//BORROW statement
			{
				System.out.print("Advance borrow (days): ");
				String input = scanner.nextLine();
				if(itemDetails.numberOrNot(input) == true)//INTEGER EXCEPTION
				{
					date = Integer.parseInt(input);
					System.out.println("The game " + items[position].getTitle() + " costs $" + ((Game)items[position]).borrow(response, date, false) + " and is due on: " + new DateTime(date).getFormattedDate() + "\n");
				}
			}
		else if(idFound == true && onLoan == true & borrowID == true)//EXTEND statement
			{
				System.out.print("Extend Loan? (Y/N): ");
				String response2 = scanner.nextLine().toUpperCase();
				
				if(exception.YesOrNO(response2) == true)//EXCEPTION
				{
					if(response2.equals("Y"))
					{
						System.out.print("Enter number of days borrowed (days): ");
						String input = scanner.nextLine();
						
						if(exception.numberOrNot(input) == true)//INTEGER EXCEPTION
						{
							date = Integer.parseInt(input);
							items[position].setExtended(true);
							items[position].returnItem(new DateTime(date));//RETURN previous record
							((Game)items[position]).borrow(response, 0, false);//NEW hire record
							items[position].setLoan("EXTENDED");
							
							System.out.println("The game " + items[position].getTitle() + " has BEEN extended.\n");
						}
					}
					else if(response2.equals("N"))
					{
						System.out.println("The game " + items[position].getTitle() + " was NOT extended.\n");
					}
				}//END exception statement		
			}//END extend statement
		else if(idFound == true && onLoan == true & borrowID == false)//ONLOAN statement
			{
				System.out.println("The game is currently being loaned.\n");
			}
		else
			{
				System.out.println("ERROR: The item with id number: " + output + ", not found." + "\n");
			}
	}
	
	//CHECKING
	public void checkItem(String output)
	{
		/*
		 * For loop to check item ID status, Loan status & position in Items Array
		 */
		for(int i = 0; i <= records - 1; i++)
		{
			if(items[i].getID().equals(output) && items[i].getOnLoan() == true)
			{
				position = i;
				idFound2 = true;
				onLoan = true;
			}
			else if(items[i].getID().equals(output) && items[i].getOnLoan() != true)
			{
				position = i;
				idFound2 = true;
				onLoan = false;
			}
			
		}
	}
	
	public void checkGame(String output, String response)
	{
		/*
		 * For loop to check ID status, Borrower ID, Loan status & position in Items Array
		 */
		for(int i = 0; i <= records - 1; i++)
		{
			if(items[i].getID().equals(output) && items[i].getOnLoan() != true)
			{
				idFound = true;
				onLoan = false;
				position = i;
			}
			else if(items[i].getID().equals(output) && items[i].getOnLoan() == true && items[i].getBorrowID().equals(response))
			{
				idFound = true;
				onLoan = true;
				borrowID = true;
				position = i;
			}
			else if(items[i].getID().equals(output) && items[i].getOnLoan() == true && !items[i].getBorrowID().equals(response))
			{
				idFound = true;
				onLoan = true;
				borrowID = false;
				position = i;
			}
		}
	}
	
	public boolean checkIDExist(String enterID)
	{
		/*
		 * For loop to check ID taken or not in Items Array
		 */
		boolean exist = false;
		
		for(int i = 0; i <= records - 1; i++)
		{
			if(items[i].getID().equals("M_" + enterID) || items[i].getID().equals("G_" + enterID))
			{
				exist = true;
			}
			else
			{
				exist = false;
			}
		}
		
		return exist;
	}
	
	//FILE
	public void FileWriter()
	{
		try
		{
			outputStream = new PrintWriter(new FileOutputStream(filename, true));
			for(int i = 0; i <= items.length; i++)
			{
				if (items[i] == null) 
				{
					break;
				}
				outputStream.println(items[i].toString());
			}
			
			System.out.println("FILE HAS BEEN UPDATED");
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e);
		}
		
		outputStream.close();
	}
}

