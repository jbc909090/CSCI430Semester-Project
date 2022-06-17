import java.util.*;
import java.text.*;
import java.io.*;

public class QueryState extends WareState {
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private WaitList waitList;
	private ClientList clientList;
	private ProductList productList;
	private SupplierList supplierList;
	private static QueryState instance;
	private static final int LOGOUT = 0;
	private static final int ALL = 1;
	private static final int WITH_BALANCE = 2;
	private static final int NO_HISTORY = 3;
	private static final int HELP = 4;
	
	private QueryState() {
		super();
		supplierList = SupplierList.instance();
		productList = ProductList.instance();
		clientList = ClientList.instance();
		waitList = WaitList.instance();
	}
	
	public static QueryState instance() {
		if (instance == null) {
			instance = new QueryState();
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
        if (value >= LOGOUT && value <= HELP) {
          return value;
        }
      } catch (NumberFormatException nfe) {
        System.out.println("Enter a number");
      }
    } while (true);
  }

	public void help() {
		System.out.println("Enter a number between 0 and 3 as explained below:");
		System.out.println(LOGOUT + " to return to Clerk");
		System.out.println(ALL + " to prrint all clients.");
		System.out.println(WITH_BALANCE + " to prints only clients with outstanding balance");
		System.out.println(NO_HISTORY + " to print only clients with no transaction history.");
	}
	
	public void all() {
		Iterator<Client> C_Iterator = clientList.getClients();
        System.out.println("Printing Client List");		
		while (C_Iterator.hasNext()) {
			Client item = C_Iterator.next();
			System.out.println("ID: " + item.getId() + " Name: " + item.getName() + " Address: " + item.getAddress());
		}
    }
	
	public void bal() {
		Iterator<Client> C_Iterator = clientList.getClients();
        System.out.println("Printing Client List With only Outstanding balances");		
		while (C_Iterator.hasNext()) {
			Client item = C_Iterator.next();
			if (item.boolBalance())
				System.out.println("ID: " + item.getId() + " Name: " + item.getName() + " Address: " + item.getAddress());
		}
    }

	public void no() {
		Iterator<Client> C_Iterator = clientList.getClients();
        System.out.println("Printing Client List with only no transaction histories");		
		while (C_Iterator.hasNext()) {
			Client item = C_Iterator.next();
			if (item.boolHistory())
				System.out.println("ID: " + item.getId() + " Name: " + item.getName() + " Address: " + item.getAddress());
		}
    }
	
	public void logout() {
		(WareContext.instance()).changeState(2);
	}
	
	public void process() {
		int command;
		help();
		while ((command = getCommand()) != LOGOUT){
			switch (command) {
				case ALL:
					all();
					break;
				case WITH_BALANCE:
					bal();
					break;
				case NO_HISTORY:
					no();
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