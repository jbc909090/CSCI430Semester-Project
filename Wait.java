import java.util.*;
import java.io.*;
import java.lang.*;
public class Wait implements Serializable {
  private static final long serialVersionUID = 1L;
  //for ID generation
  private WaitIdServer waitIdServer;
  private Product product;
  private Client client;
  private int quantity;
  private int id;

  public Wait(Client client, Product product, int quantity){
    this.client   = client;
    this.product  = product;
    this.quantity = quantity;
	waitIdServer = WaitIdServer.instance();
	this.id = waitIdServer.getId();
  }

  public int getId() {
	  return id;
  }
  public Product getProduct(){
    return product;
  }

  public Client getClient(){
    return client;
  }

  public int getQuantity(){
    return quantity;
  }

  public void setQuantity(int newQuantity){
    this.quantity = newQuantity;
  }
}