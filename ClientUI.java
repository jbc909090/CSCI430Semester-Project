
import java.util.*;
import java.io.*;

public class ClientUI extends WareState {
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private WareContext context;
	private static ClientUI instance;
	private WaitList waitList;
	private ClientList clientList;
	private ProductList productList;
	private SupplierList supplierList;
	private static final int EXIT = 0;
    private static final int DETAILS = 1;
    private static final int PRODUCT_SHOW= 2;
    private static final int TRANSACTION = 3;
    private static final int WAIT = 4;
    private static final int CART = 5;
	private static final int ORDER = 6;
	private static final int PAY = 7;
    private static final int HELP = 8;
	
	private ClientUI() {
		super();
		supplierList = SupplierList.instance();
		productList = ProductList.instance();
		clientList = ClientList.instance();
		waitList = WaitList.instance();
	}
	
	public static ClientUI instance() {
		 if (instance == null) {
			 instance = new ClientUI();
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
	public void ClientUIDisplay ()
	{
		System.out.println("1: Show Client Details");
		System.out.println("2: Show Products");
		System.out.println("3: Show Transaction History");
		System.out.println("4: Show Waitlist");
		System.out.println("5: Edit Cart State");
		System.out.println("6: Turn cart into order");
		System.out.println("7: Pay an order");
		System.out.println("0: Logout");
	}
	
	
	public void ClientProcess () {
		int position = clientList.IDcheck((WareContext.instance()).getUID());
		if (position == -1 && (WareContext.instance()).getUID() == 1) {
			System.out.println("Adding dummy client as no client currently exist");
			Client dummy = new Client("Joe Schmoe", "123 Nowheres Ville");
			clientList.insertMember(dummy);
			position = 0;
		} else if (position == -1) {
			System.out.println("Invalid ID, logging you out, try 1");
			logout();
		}
		Client loggedInClient = clientList.get_listed_obj(position);
		int clientCommand = 1;
		ClientUIDisplay();
		while ((clientCommand = getCommand()) != 0)
		{
			switch(clientCommand) {
				case DETAILS:
					ClientInfo(loggedInClient);
					break;
				case PRODUCT_SHOW:
					ShowProducts();
					break;
				case TRANSACTION:
					ShowTransactionHistory(loggedInClient);
					break;
				case WAIT:
					ShowWaitlist(loggedInClient);
					break;
				case CART:
					CartState();
					break;
				case ORDER:
					addOrder(loggedInClient);
					break;
				case PAY:
					makePayment(loggedInClient);
					break;
			}
			
		}
		
		logout();
		
	}

	
	public void ClientInfo (Client client) {
		System.out.println("ID: " + client.getId());
		System.out.println("Name: " + client.getName());
		System.out.println("Address: " + client.getAddress());
	}
	
	
	public void ShowProducts() {
        Iterator<Product> P_Iterator = productList.getProducts();
        System.out.println("Printing Supplier List");
        while (P_Iterator.hasNext()) {
            Product item = P_Iterator.next();
			System.out.println("Name: " + item.getName());
			System.out.println("ID: " + item.getId());
			System.out.println("Price: " + item.getPrice());
			System.out.println("------------------------------");
        }
     }
	
	public void ShowTransactionHistory(Client client) {
		client.DisplayInvoices();
	}
	
	public void ShowWaitlist(Client client) {
		Iterator<Wait> W_Iterator = waitList.getWaits();
		int id = client.getId();
		System.out.println("Printing Client's wait list");		
		while (W_Iterator.hasNext()) {
			Wait item = W_Iterator.next();
			if ((item.getClient()).getId()== id)
				System.out.println("product id: " + (item.getProduct()).getId() + " product name: " + (item.getProduct()).getName() + " quantity: " + item.getQuantity());
		}
	}
	
	public void CartState() {
		(WareContext.instance()).changeState(4);
	}
	public void addOrder (Client item) {
		clientList.GenerateOrder(item);
	}
	public void makePayment (Client item) {
		int invoiceID = Integer.parseInt(getToken("Enter ID of invoice to pay"));
		item.PayInvoice(invoiceID);
	}
	
	public void logout() {
		(WareContext.instance()).changeState(0);
	}
	
	
	public void run() {
		ClientProcess();
	}

}
