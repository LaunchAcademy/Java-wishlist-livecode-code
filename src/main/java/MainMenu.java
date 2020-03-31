import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class MainMenu {
  private static Scanner input = new Scanner(System.in);
  private static EntityManagerFactory emf;
  private static EntityManager em;

  public static void main(String[] args) throws IOException {
    menu();
  }

  //Create a menu with options
  public static void menu(){
    emf = Persistence.createEntityManagerFactory("com.launchacademy.wishlist");
    em = emf.createEntityManager();
    String[] options = {". Add a new product", ". View wishlist", ". Exit"};
    while(true){
      for (int i = 0; i < options.length; i++){
        System.out.println((i + 1) + options[i]);
      }
      System.out.println("CHOOSE a number HUMAN");
      int choice = input.nextInt();
      if( choice == options.length){
        em.close();
        emf.close();
        System.exit(0);
      }
      else if (choice == 1){
        addProduct();
      }
      else if (choice == 2){
        listWishes();
      }
    }
  }

  //Add a product
  private static void addProduct(){
    try{
      Product newProduct = new Product();
      System.out.println("What product would you like to add?");
      newProduct.setName(input.next());
      System.out.println("What is the product's price?");
      newProduct.setPrice(input.nextFloat());
      System.out.println("What is the product's url?");
      newProduct.setUrl(input.next());
      em.getTransaction().begin();
      em.persist(newProduct);
      em.getTransaction().commit();
    } finally {
    }
  }

  //List all products in wishlist
  private static void listWishes(){
    List<Product> wishes = em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    for (Product product : wishes){
      System.out.println(product.getName() + " " + product.getPrice() + " " + product.getUrl());
    }
  }
}
