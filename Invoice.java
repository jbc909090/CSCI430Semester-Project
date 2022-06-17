import java.util.*;
import java.time.LocalDate;

public class Invoice {
	cart products = new cart();
	int total;
	int id;
	LocalDate date = LocalDate.now();
	boolean paid = false;
	
	
	public int getId () {
		return id;
	}
	public boolean getPaid() {
		return paid;
	}
	public Invoice (cart userCart, int total)
	{
		products = userCart;
		this.total = total;
		this.id = (InvoiceIdServer.instance()).getId();
	}
	
	public void printInvoice()
	{
		System.out.println("Date: " + date);
		System.out.println("Products: " + products);
		System.out.println("total: " + total);
	}
	
	public void payInvoice()
	{
		System.out.println("Invoice has been paid.");
		paid = true;
	}

}