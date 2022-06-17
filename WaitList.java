import java.util.*;
import java.io.*;
import java.lang.*;
public class WaitList implements Serializable {
	private LinkedList<Wait> waits = new LinkedList<Wait>();
    private static WaitList waitList;

    private WaitList() {}

    public static WaitList instance() {
        if (waitList == null) {
            return (waitList = new WaitList());
        }
        else {
            return waitList;
        }
    }

    public boolean insertWait(Wait wait) {
        waits.add(wait);
        return true;
    }

    public Iterator<Wait> getWaits() {
        return waits.iterator();
    }

    private void writeObject(java.io.ObjectOutputStream output) {
        try {
            output.defaultWriteObject();
            output.writeObject(waitList);
        }
        catch(IOException ioe) {
            System.out.println(ioe);
        }
    }

    private void readObject(java.io.ObjectInputStream input) {
        try {
            if (waitList != null) {
                return;
            }
            else {
                input.defaultReadObject();
                if (waitList == null) {
                    waitList = (WaitList) input.readObject();
                }
                else {
                    input.readObject();
                }
            }
        }
        catch(IOException ioe) {
            System.out.println("in WaitList readObject \n" + ioe);
        }
        catch(ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }

    public String toString() {
        return waits.toString();
    }
    
    //adding a function for going through the linked list finding a specfic ID
    //takes a int, returns a int (both are to be supplier IDs)
    public int IDcheck (int ID) {
        for (int i=0; i < waits.size(); i++) {
            Wait temp = waits.get(i);
            if (ID == temp.getId()) {
                System.out.println("ID found");
                return i;
            }
        }
        System.out.println("ERROR: ID is not in database");
        return -1;
    }
    public Wait get_listed_obj (int position) {
        return waits.get(position);
    }
	public void delete_listed_obj (Wait position) {
		waits.remove(position);
	}
    public void set_listed_obj (int position, Wait update) {
        waits.set(position, update);
    }
}
