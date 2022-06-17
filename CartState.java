import java.util.*;
import java.text.*;
import java.io.*;

public class CartState extends WareState {
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private WaitList waitList;
	private ClientList clientList;
	private ProductList productList;
	private SupplierList supplierList;
	private static CartState instance;
	private static final int EXIT = 0;
	private static final int ADD = 1;
	private static final int EDIT = 2;
	private static final int DELETE = 3;
	private static final int VIEW = 4;
	private static final int HELP = 5;
	
	private CartState() {
		super();
		supplierList = SupplierList.instance();
		productList = ProductList.instance();
		clientList = ClientList.instance();
		waitList = WaitList.instance();
	}
	
	public static CartState instance() {
		if (instance == null) {
			instance = new CartState();
		}
		return instance;
	}
	
	public String getToken(String prompt) {
		do {
			try {
				System.out.println(prompt);
				String line = reader.readLine();
				StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
				if (tokenizer.hasMoreTokens()) {
					return tokenizer.nextToken();
				}
			} catch (IOException ioe) {
				System.exit(0);
			}
		} while (true);
  }
  
  public int getCommand() {
    do {
      try {
        int value = Integer.parseInt(getToken("Enter command:" + HELP + " for help"));
        if (value >= EXIT && value <= HELP) {
          return value;
        }
      } catch (NumberFormatException nfe) {
        System.out.println("Enter a number");
      }
    } while (true);
  }

	public void help() {
		System.out.println("Enter a number between 0 and 4 as explained below:");
		System.out.println(EXIT + " to Client State\n");
		System.out.println(ADD + " to too add to cart.");
		System.out.println(EDIT + " to edit product in cart.");
		System.out.println(DELETE + " to remove a itme from the cart.");
		System.out.println(VIEW + " to view the cart.");
	}
	
	public void add() {
		int clientID = (WareContext.instance()).getUID();
		int position = clientList.IDcheck(clientID);
		Client item = clientList.get_listed_obj(position);
		int productToAddID = Integer.parseInt(getToken("Please enter the product ID you wish to add: "));
		Product productToAdd = productList.search(productToAddID);
		int quant = Integer.parseInt(getToken("Enter the quantity to be added to the cart: "));
		item.AddToCart(productToAdd, quant);
	}
	
	public void edit() {
		int clientID = (WareContext.instance()).getUID();
		int position = clientList.IDcheck(clientID);
		Client item = clientList.get_listed_obj(position);
		int productID = Integer.parseInt(getToken("Enter the product ID you wish to change the quantity of: "));
		int qty = Integer.parseInt(getToken("Enter the new quantity: "));
		item.ChangeQuantity(productID, qty);
	}
	
	public void delete() {
		int clientID = (WareContext.instance()).getUID();
		int position = clientList.IDcheck(clientID);
		Client item = clientList.get_listed_obj(position);
		int productID = Integer.parseInt(getToken("Please enter the product ID you wish to delete: "));
		item.RemoveCartProduct(productID);
	}
	public void view() {
		int clientID = (WareContext.instance()).getUID();
		int position = clientList.IDcheck(clientID);
		Client item = clientList.get_listed_obj(position);
		item.DisplayCart();
	}

	public void logout() {
		(WareContext.instance()).changeState(1);
	}
	
	public void process() {
		int command;
		help();
		while ((command = getCommand()) != EXIT){
			switch (command) {
				case ADD:
					add();
					break;
				case EDIT:
					edit();
					break;
				case DELETE:
					delete();
					break;
				case VIEW:
					view();
					break;
				default:
					System.out.println("Not a valid command");
			}
		}
		logout();
	}
	
	public void run() {
		process();
	}
}