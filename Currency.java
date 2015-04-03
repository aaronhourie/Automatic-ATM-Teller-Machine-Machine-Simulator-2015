public class Currency implements ICurrency
{
	private int amount;

	public Currency(int amount) 
	{	
		this.amount = amount;
	}

	//Converts user input into storable values
	public static int parse(double value) {
		return (int)(value * 100.00);
	}

	@Override //Converts amount into $12.34 format
	public String toString() {
		int dollars = (int) Math.floor(amount / 100);
		int centStep = amount % 100;

		//Deal with leading zeroes
		String cents = String.valueOf(centStep);
		if(centStep < 10) {
			cents = "0" + cents;
		}
		
		return "$" + dollars + "." + cents; 
	}

	@Override
	public void add(Currency currency) 
	{
		amount += currency.getAmount();
	}

	@Override
	public void subtract(Currency currency) 
	{
		amount -= currency.getAmount();
	}
	
	@Override
	public void interest(double rate) 
	{
		amount += amount * (int)(rate / 100.00);
	}

	@Override
	public int getAmount()
	{
		return amount;
	}
}
