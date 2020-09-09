import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;


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
    String[] options = {". Add a new item", ". View wishlist", ". Exit"};
    while(true){
      for (int i = 0 ; i < options.length; i++){
        System.out.println((i + 1) + options[i]);
      }
      System.out.println("CHOOSE A NUMBER HUMAN!");
      int choice = input.nextInt();
      if (choice == options.length){
        System.out.println("Goodbye");
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
  public static void addProduct() {
    String name = "";
    String price = "";
    String url = "";
    String category = "";

    System.out.println("Name of product");
    Scanner userInputName = new Scanner(System.in);
    name = userInputName.nextLine();

    System.out.println("Price of product");
    Scanner userInputPrice = new Scanner(System.in);
    price = userInputPrice.nextLine();
    Float floatPrice = Float.parseFloat(price);

    System.out.println("Url to product");
    Scanner userInputUrl = new Scanner(System.in);
    url = userInputUrl.nextLine();

    System.out.println("Would you like to add a category? (type 'none' for no");
    Scanner inputCategory = new Scanner(System.in);
    category = inputCategory.nextLine();

    Product product = new Product();
    Category newCategory = new Category();

    TypedQuery<Category> categoryQuery = em.createQuery("SELECT c FROM Category c", Category.class);
    List<Category> categories = categoryQuery.getResultList();

    try{

      newCategory.setName(category);
      em.getTransaction().begin();
      em.persist(newCategory);
      em.getTransaction().commit();

      em.getTransaction().begin();
      product.setName(name);
      product.setPrice(floatPrice);
      product.setUrl(url);
      product.setCategory(newCategory);

      em.persist(product);
      em.getTransaction().commit();

    } catch (Exception ex){
      System.out.println("Duplicate Category Name Error");
      em.getTransaction().begin();
      product.setName(name);
      product.setPrice(floatPrice);
      product.setUrl(url);
      for (Category eachCategory : categories){
        if(category.equals(eachCategory.getName())){
          product.setCategory(eachCategory);
        }
      }
      em.persist(product);
      em.getTransaction().commit();
    }
  }

  //List all products in wishlist
  private static void listWishes(){
    TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p", Product.class);
    List<Product> products = query.getResultList();

    for (Product product : products){
      Category eachCategory = product.getCategory();
      System.out.println("Product: " + product.getName() + "\n" + "Price: " + product.getPrice()
      + "\n" + "Url: " + product.getUrl() + "\n" + " Category: " + eachCategory.getName() + "\n");
    }
  }
}
