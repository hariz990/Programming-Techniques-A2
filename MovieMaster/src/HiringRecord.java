public class HiringRecord 
{
	private String id = "";
	private String borrowID = "";
	private double rentalFee;
	private double lateFee;
	private DateTime borrowDate;
	private DateTime returnDate;
	
	public HiringRecord(String id, String memberID, double rentalFee, DateTime borrowDate)// HiringRecord Constructor
	{
		this.id = id + "_" + memberID + "_"  + borrowDate.getEightDigitDate();
		this.rentalFee = rentalFee;
		this.borrowDate = borrowDate;
		borrowID = memberID;
	}
	
	public double returnItem(DateTime returnDate, double lateFee, boolean onTime)
	{
		if(lateFee < 0) 
		{
			System.out.println("ERROR: Late fee should not be zero.\n");
			return Double.NaN;
		}
		else
		{
			if(onTime == true)
			{
				this.lateFee = 0;
			}
			else
			{
				this.lateFee = lateFee;
			}
			
			this.returnDate = returnDate;
			return Double.NaN;
		}
		
	}
	
	public String getDetails()
	{
		String details = "\n";

		if(returnDate == null)
		{
			details += String.format("%-20s %s\n", "", "BORROWING RECORD");
			details += String.format("%-20s %s\n", "", "---------------------------");
			details += String.format("%-20s %s\n", "", "Borrow ID: " + id);
			details += String.format("%-20s %s\n", "", "Borrow Date: " + borrowDate.getFormattedDate());
		}
		else
		{
			details += String.format("%-20s %s\n", "", "BORROWING RECORD");
			details += String.format("%-20s %s\n", "", "---------------------------");
			details += String.format("%-20s %s\n", "", "Borrow ID: " + id);
			details += String.format("%-20s %s\n", "", "Borrow Date: " + borrowDate.getFormattedDate());
			details += String.format("%-20s %s\n", "", "Return Date: " + returnDate.getFormattedDate());
			details += String.format("%-20s %s%.2f\n", "", "Fee: $", rentalFee);
			details += String.format("%-20s %s%.2f\n", "", "Late Fee: $", lateFee);
			details += String.format("%-20s %s%.2f\n", "", "Total Fees: $", (rentalFee + lateFee));
		}
		
		return details;
	}
	
	public String toString()
	{
		String s = "";
		if(returnDate == null)
		{
			s = id + ":" + borrowDate.getEightDigitDate() + ":" + "none" + ":" + "none" + ":" + "none";
		}
		else
		{
			s = id + ":" + borrowDate.getEightDigitDate() + ":" + returnDate.getEightDigitDate() + ":" + rentalFee + ":" + lateFee;
		}
		
		return s;
	}
	
	
	//GET methods
	public double getRentalFee()
	{
		return rentalFee;
	}
	
	public double getTotalFee()
	{
		double total = rentalFee + lateFee;
		return total;
	}
	
	public DateTime getBorrowDate()
	{
		return borrowDate;
	}
	
	public String getBorrowID()
	{
		return borrowID;
	}

}
