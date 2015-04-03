public interface ICurrency 
{
	public int getAmount();
	public String toString();

	/**
	 * Because of the nature of Currency objects,
	 * they must be arithmetic'd with custom functions.
	 **/
	public void add(Currency currency);
	public void subtract(Currency currency);
	public void interest(double rate);
}
