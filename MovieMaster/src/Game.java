public class Game extends Item
{
	private String platform[] = new String[10];
	private boolean extended;
	private final double STANDARD_FEE_CHARGE = 20.00;
	
	public Game(String id, String title, String genre, String description, String platform)//GAME constructor
	{
		super("G_" + id, title, genre, description);
		super.setType(platform);
		super.setOnLoan(false);
		super.setFee(STANDARD_FEE_CHARGE);
		super.setRentalPeriod(22);
	}
	
	public double borrow(String memberID, int date, boolean extended)
	{
		super.setExtended(extended);
		return super.borrow(memberID, date);
	}
	
	public double returnItem(DateTime returnDate) 
	{
		return super.returnItem(returnDate);
	}
	
	public String getDetails()
	{
		return super.getGameDetails();
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
	
	public String getBorrowID()
	{
		return super.getBorrowID();
	}

}
