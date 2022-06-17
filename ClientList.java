import java.util.*;
import java.io.*;
public class ClientList implements Serializable {
  private static final long serialVersionUID = 1L;
  private List<Client> clients = new LinkedList<Client>();
  private static ClientList clientList;
  private ClientList() {
  }
  public static ClientList instance() {
    if (clientList == null) {
      return (clientList = new ClientList());
    } else {
      return clientList;
    }
  }

  public boolean insertMember(Client client) {
    clients.add(client);
    return true;
  }

  public Iterator<Client> getClients(){
     return clients.iterator();
  }
  
  private void writeObject(java.io.ObjectOutputStream output) {
    try {
      output.defaultWriteObject();
      output.writeObject(clientList);
    } catch(IOException ioe) {
      ioe.printStackTrace();
    }
  }
  private void readObject(java.io.ObjectInputStream input) {
    try {
      if (clientList != null) {
        return;
      } else {
        input.defaultReadObject();
        if (clientList == null) {
          clientList = (ClientList) input.readObject();
        } else {
          input.readObject();
        }
      }
    } catch(IOException ioe) {
      ioe.printStackTrace();
    } catch(ClassNotFoundException cnfe) {
      cnfe.printStackTrace();
    }
  }
  public String toString() {
    return clients.toString();
  }
  
  public void GenerateOrder(Client client)
  {
	  client.newInvoice(client);
  }
  
  public int IDcheck (int ID) {
        for (int i=0; i < clients.size(); i++) {
            Client temp = clients.get(i);
            if (ID == temp.getId()) {
                System.out.println("ID found");
                return i;
            }
        }
        System.out.println("ERROR: ID is not in database");
        return -1;
    }
	public Client get_listed_obj (int position) {
        return clients.get(position);
    }
    public void set_listed_obj (int position, Client update) {
        clients.set(position, update);
    }
}