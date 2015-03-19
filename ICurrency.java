//
public interface ICurrency 
{
	public int getAmount();
	public String toString();
	public void add(Currency currency);
	public void subtract(Currency currency);
	public void interest(double rate);
}
