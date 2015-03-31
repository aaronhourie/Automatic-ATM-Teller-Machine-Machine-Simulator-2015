public class Currency implements ICurrency
{
	private int amount;

	//To convert user input into storable values
	public static int parse(double value) {
		return (int)(value * 100);
	}

	public Currency(int amount) 
	{	
		this.amount = amount;
	}

	public int getAmount()
	{
		return amount;
	}

	@Override
	public String toString()
	{
		int dollars = (int) (amount / 100);
		int cents = amount % 100;
		
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
		amount += (int)(amount*(rate/100));
	}
}
