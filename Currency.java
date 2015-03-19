//
public class Currency implements ICurrency
{
	private int amount;
	public Currency()
	{	
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
