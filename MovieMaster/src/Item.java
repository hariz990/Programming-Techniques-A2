public class Item extends ExceptionClass
{
	private String id = "";
	private String title = "";
	private String genre = "";
	private String description = "";
	
	private String type = "";
	private int rentalPeriod;
	private String loan = "";
	
	private int records = 0;
	private int diff;
	private double fee;
	
	private boolean onLoan;
	private boolean extended;
	
	private HiringRecord currentlyBorrowed;
	private HiringRecord[] hireHistory = new HiringRecord[10];
	
	public Item(String id, String title, String genre, String description)//ITEM constructor
	{
		this.id = id;
		this.title = title; 
		this.genre = genre; 
		this.description = description;
		loan = "NO";
	}
	
	public double borrow(String memberID, int date)
	{
		currentlyBorrowed = new HiringRecord(id, memberID, fee, new DateTime(date));
		if(records == 10)//Engaged when hireHistory reaches max value
			{
				/*
				 * Adjust records position in hireHistory to -1 
				 */
				hireHistory[0] = null;
				for(int i = 1; i < 9; i++)
				{
					hireHistory[i] = hireHistory[i + 1];
				}	
					records = 9;
					hireHistory[records] = currentlyBorrowed;
					records++;
			}
		else
			{
				hireHistory[records] = currentlyBorrowed;
				records++;
			}
		
			loan = "YES";
			onLoan = true;
			return currentlyBorrowed.getRentalFee();
	}
	
	public double returnItem(DateTime returnDate)
	{
		if(onLoan == true)
		{
			diff = DateTime.diffDays(returnDate, currentlyBorrowed.getBorrowDate());
			int diff2 = diff - rentalPeriod;
			double total = 0;
			
			if(fee == 20)//GAME calculation
				{
					if(diff2 <= 0)//Returned on the same date
						{
							currentlyBorrowed.returnItem(returnDate, fee, true);
						}
					else if(diff2 < 6)//Less than 6 days
						{
							total = diff2 * 1;
						}
					else if(diff2 > 6 && extended == false)
						{
							/*
							 * Storing remainder, multiplication values for total calculation
							 */
							double remainder = diff2 % 7;
							double subtract = diff2 - remainder;
							int x = 0;
							double number = 0;//multiplication value used to divide date difference
							
							while(x != subtract)
							{
								x += 7;
								number += 1;
							}
							
							//Total value of games
							total = (remainder * 1) + (number * 5);
						}
					else if(diff > 6 && extended == true)
						{
							double remainder = diff2 % 7;
							double subtract = diff2 - remainder;
							int x = 0;
							double number = 0;
							
							while(x != subtract)
							{
								x += 7;
								number += 1;
							}
							
							//50% discount for total when same movie is extended
							total = ((remainder * 1) + (number * 5)) / 2;
						}
					
					currentlyBorrowed.returnItem(returnDate, total, false);
				}
			
			
			else if(fee != 20 && diff2 > 0)//MOVIE calculation
				{
					total = diff2 * (fee/2);
					currentlyBorrowed.returnItem(returnDate, total, false);
				}
			else
				{
					currentlyBorrowed.returnItem(returnDate, fee, true);
				}
			
			loan = "NO";
			onLoan = false;
			return currentlyBorrowed.getTotalFee();
		}
		
		else
		{
			return Double.NaN;
		}
	}
	
	public String getDetails()//Printing movie details
	{
		String details = "";
		
		details += "MOVIE RECORDS\n";
		details += "--------------------------------\n";
		details += String.format("%-20s %s\n", "ID:", id);
		details += String.format("%-20s %s\n", "Title:", title);
		details += String.format("%-20s %s\n", "Genre:", genre);
		details += String.format("%-20s %s\n", "Description:", description);
		details += String.format("%-20s %s%.2f\n", "Standard Fee:", "$", fee);
		details += String.format("%-20s %s\n", "On Loan:", loan);
		details += String.format("%-20s %s\n", "Movie Type:", type);
		details += String.format("%-20s %s\n", "Rental Period:", rentalPeriod + " days");
		
		if(records > 0)//Engage when there is hire record
		{
			for(int counter= records - 1; counter >= 0; counter--)
			{
				details += hireHistory[counter].getDetails();
			}
		}
		else
		{
			details += "\n";
			details += String.format("%-20s %s\n", "", "BORROWING RECORD");
			details += String.format("%-20s %s\n", "", "NONE");
		}

		return details;
	}
	
	public String getGameDetails()//Printing game details
	{
		String details = "";
		
		details += "GAME RECORDS\n";
		details += "--------------------------------\n";
		details += String.format("%-20s %s\n", "ID:", id);
		details += String.format("%-20s %s\n", "Title:", title);
		details += String.format("%-20s %s\n", "Genre:", genre);
		details += String.format("%-20s %s\n", "Description:", description);
		details += String.format("%-20s %s%.2f\n", "Standard Rental Fee:", "$", fee);
		details += String.format("%-20s %s\n", "Platforms:", type);
		details += String.format("%-20s %s\n", "Rental Period:", rentalPeriod + " days");
		details += String.format("%-20s %s\n", "On Loan:", loan);
		
		if(records > 0)//Engage when there is hire record
		{
			for(int counter= records - 1; counter >= 0; counter--)//Printing hireHistory
			{
				details += hireHistory[counter].getDetails();
			}
		}
		else
		{
			details += "\n";
			details += String.format("%-20s %s\n", "", "BORROWING RECORD");
			details += String.format("%-20s %s\n", "", "NONE");
		}

		return details;
	}
	
	public String toString()
	{
		String s = "";
		String status = onLoan ? "Y":"N";
		
		if(fee == 3)//Weekly Movies toString
		{
			s = id + ":" + title + ":" + description + ":" + genre + ":" + fee + ":" + "WK" + ":" + status;
		}
		else if(fee == 5)//New Release Movies toString
		{
			s = id + ":" + title + ":" + description + ":" + genre + ":" + fee + ":" + "NR" + ":" + status;
		}
		else if(fee == 20 && extended == false)//Game toString 
		{
			s = id + ":" + title + ":" + description + ":" + genre + ":" + type + ":" + fee + ":" + status;
		}
		else if(fee == 20 && extended == true)//Extended Game toString
		{
			s = id + ":" + title + ":" + description + ":" + genre + ":" + type + ":" + fee + ":" + "E";
		}
		
		return s;
	}
	
	//GET methods
	public String getID()
	{
		return id;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public boolean getOnLoan()
	{
		return onLoan;
	}
	
	public String getBorrowID()
	{
		return currentlyBorrowed.getBorrowID();
	}
	
	//SET methods
	public void setRentalPeriod(int rentalPeriod)
	{
		this.rentalPeriod = rentalPeriod;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public void setOnLoan(boolean onLoan)
	{
		this.onLoan = onLoan;
	}
	
	public void setFee(double fee)
	{
		this.fee = fee;
	}
	
	public void setExtended(boolean extended)
	{
		this.extended = extended;
	}
	
	public void setLoan(String loan)
	{
		this.loan = loan;
	}
	
	//EXCEPTION methods
	public boolean movieORgame(String selection)
	{
		return super.movieORgame(selection);
	}
	
	public boolean yesORno(String selection)
	{
		return super.yesORno(selection);
	}
	
	public boolean YesOrNO(String selection)
	{
		return super.YesOrNO(selection);
	}
	
	public boolean numberOrNot(String input)
	{
		return super.numberOrNot(input);
	}
	

}
