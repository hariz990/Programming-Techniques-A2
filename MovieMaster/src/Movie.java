public class Movie extends Item
{
	private boolean isNewReleased;
	private final double NEW_RELEASE_SURCHARGE = 5.00;
	private final double STANDARD_FEE = 3.00;
	
	public Movie(String id, String title, String genre, String description, String isNew)//MOVIE constructor
	{
		super("M_" + id, title, genre, description);
		if(isNew.equals("N")) 
			{
				super.setType("Weekly");
				isNewReleased = false;
				super.setOnLoan(false);
				super.setFee(STANDARD_FEE);
				super.setRentalPeriod(7);
			}
		else
			{
				super.setType("New Release");
				isNewReleased = true;
				super.setOnLoan(false);
				super.setFee(NEW_RELEASE_SURCHARGE);
				super.setRentalPeriod(2);
			}
	}
	
	public double borrow(String memberID, int date) 
	{
		return super.borrow(memberID, date);
	}
	
	public double returnItem(DateTime returnDate) 
	{
		return super.returnItem(returnDate);
	}
	
	public String getDetails()
	{
		return super.getDetails();
	}
	
	public String toString()
	{
		return super.toString();
	}

	
	//GET methods
	public String getID()
	{
		return super.getID();
	}
	
	public String getTitle()
	{
		return super.getTitle();
	}
	
	public boolean getOnLoan()
	{
		return super.getOnLoan();
	}
	
}	
