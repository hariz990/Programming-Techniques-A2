public class ExceptionClass 
{
	
	public boolean movieORgame(String selection)//EXCEPTION for input M or G
	{
		if(selection.equals("M") || selection.equals("G"))
		{
			return true;
		}
		else
		{
			System.out.println("ERROR: Please enter correct selection M or G. Your input was " + selection + ".");
			System.out.println("Exiting to main menu.\n");
			return false;
		}
	}
		
	public boolean yesORno(String selection)//EXCEPTION for input Y or N 
	{
		if(selection.equals("Y") || selection.equals("N"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
		
	public boolean YesOrNO(String selection)//EXCEPTION for input Y or N with statements
	{
		if(selection.equals("Y") || selection.equals("N"))
		{
			return true;
		}
		else
		{
			System.out.println("ERROR: Please enter correct selection Y or N. Your input was " + selection + ".");
			System.out.println("Exiting to main menu.\n");
			return false;
		}
	}
		
	public boolean numberOrNot(String input)//EXCEPTION for integer input
	{
	    try
	    {
	        Integer.parseInt(input);
	        return true;
	    }
	    catch(NumberFormatException ex)
	    {
	        System.out.println("ERROR: Please enter an integer. Your input was '" + input + "'.");
	        System.out.println("Exiting to main menu.\n");
	    }
	      	return false;
	}
}
