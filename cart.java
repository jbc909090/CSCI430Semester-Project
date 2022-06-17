import java.util.*;
import java.io.*;
public class cart {
	private ProductList productList;
	private WaitList waitList;
	LinkedList<Product2> shoppingCart = new LinkedList<>();
	public void adder(Product pr, int quantity)
	{
		Product2 temp = new Product2(pr.getName(), quantity, pr.getPrice(), pr.getId());
		shoppingCart.add(temp);
	}
	
	public void printer()
	{
		for (int i = 0; i < shoppingCart.size(); i++) {
			Product2 item = shoppingCart.get(i);
			System.out.println(item.toString());
		}
	}
	
	public int total()
	{
		int total = 0;
		for (int i = 0; i < shoppingCart.size(); i++) {
			Product2 pr = shoppingCart.get(i);
			total += (pr.getQuantity() * pr.getPrice());
		}
		return total;
	}
	
	public void emptyCart(Client item3)
	{
		waitList = WaitList.instance();
		boolean successful;
		productList = ProductList.instance();
		Iterator<Product2> list = shoppingCart.iterator();
		while (list.hasNext()) {
			Product2 item = list.next();
			int inCart = item.getQuantity();
			int position = productList.IDcheck(item.getId());
			Product item2 = productList.get_listed_obj(position);
			int inventory = item2.getQuantity();
			System.out.println(inCart);
			System.out.println(inventory);
			if (inCart > inventory) {
				inCart -= inventory;
				item2.setQuantity(0);
				Wait newWait =  new Wait(item3, item2, inCart);
				successful = waitList.insertWait(newWait);
				if (successful) {
					System.out.println("You have a new item in waitlist");
				}
			} else {
				inventory -= inCart;
				item2.setQuantity(inventory);
			}
		}
		shoppingCart.clear();
		System.out.println("The client's cart has been emptied.");
	}
	
	public void RemoveProduct(Product pr)
	{
		Iterator<Product2> list = shoppingCart.iterator();
		while (list.hasNext()) {
			Product2 temp = list.next();
			if (temp.getId() == pr.getId()) {
				shoppingCart.remove(temp);
				System.out.println("Product has been removed.");
				break;
			}
		}
	}
	
	 public int IDcheck (int ID) {
	        for (int i=0; i < shoppingCart.size(); i++) {
	            Product2 temp = shoppingCart.get(i);
	            if (ID == temp.getId()) {
	                System.out.println("ID found");
	                return i;
	            }
	        }
	        System.out.println("ERROR: ID is not in database");
	        return -1;
	    }
	 
	public Product2 get_listed_obj (int position) {
		return shoppingCart.get(position);
	}
	public void set_listed_obj (int position, Product2 update) {
		shoppingCart.set(position, update);
	}
}

class Product2 {
   private String name;
   private int id;
   private int quantity;
   private int price;
   public Product2 (String name, int quantity, int price, int id) {
      this.name     = name;
      this.quantity = quantity;
	  this.price    = price;
	  this.id = id;
   }

   
   public String getName(){
      return name;
   }
   public int getId() {
      return id;
   }
   public int getQuantity() {
      return quantity;
   }
   public int getPrice() {
	  return price;
	}
	
   public String toString() {
      String PString = "Product id " + id + " Product name " + name + " quantity " + quantity + " price " + price;
      return PString;
   }
}
