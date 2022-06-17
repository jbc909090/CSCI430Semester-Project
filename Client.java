import java.util.*;
import java.io.*;
public class Client implements Serializable {
  private static final long serialVersionUID = 1L;
  private String name;
  private String address;
  private int id;
//  private static final String MEMBER_STRING = "M";
  private cart userCart = new cart();
  private InvoiceList invoices = new InvoiceList();
  
  public  Client (String name, String address) {
    this.name = name;
    this.address = address;
    this.id = (ClientIdServer.instance()).getId();
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }
  public int getId() {
    return id;
  }
  public void setName(String newName) {
    name = newName;
  }
  public void setAddress(String newAddress) {
    address = newAddress;
  }

 /* public boolean equals(String id) {
    return this.id.equals(id);
  }
 */
  public String toString() {
    String string = "Member name " + name + " address " + address + " id " + id;
    return string;
  }
  
    public void AddToCart(Product pr, int amount) {
		userCart.adder(pr, amount);
	}
  
  public void DisplayCart()
  {
		userCart.printer();
  }
  
	public int getTotal()
	{
		int total = userCart.total();
		return total;
	}
	
	public void newInvoice(Client item3)
	{
		invoices.AddInvoice(userCart, getTotal(), item3);
	}
	
	public void PayInvoice(int id)
	{
		int position = invoices.IDcheck(id);
		Invoice holder = invoices.get_listed_obj (position);
		holder.payInvoice();
	}
	
	public void DisplayInvoices()
	{
		Iterator<Invoice> print = invoices.getInvoices();
		while (print.hasNext()) {
			Invoice item = print.next();
			item.printInvoice();
		}
	}
	public boolean boolBalance()
	{
		Iterator<Invoice> print = invoices.getInvoices();
		while (print.hasNext()) {
			Invoice item = print.next();
			if (item.getPaid()) {
				//if it false they have unpaid transaction history
			} else {
				return true;
			}
		}
		return false;
	}
	public boolean boolHistory() {
		if (invoices.size()==0) {
			return true;
		}
		return false;
	}
	
	public void RemoveCartProduct(int id)
	{
		int position = userCart.IDcheck(id);
		Product item = userCart.get_listed_obj(position);
		userCart.RemoveProduct(item);
		
	}
	
	public void ChangeQuantity(int id, int quantity)
	{
		int position = userCart.IDcheck(id);
		Product item = userCart.get_listed_obj(position);
		item.setQuantity(quantity);
	}
  
}