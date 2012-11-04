package com.supinfo.youfood.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.supinfo.youfood.dao.AdministratorDAO;
import com.supinfo.youfood.dao.MealDAO;
import com.supinfo.youfood.dao.MenuDAO;
import com.supinfo.youfood.dao.ProductTypeDAO;
import com.supinfo.youfood.dao.RestaurantDAO;
import com.supinfo.youfood.dao.WaiterDAO;
import com.supinfo.youfood.entity.Administrator;
import com.supinfo.youfood.entity.Meal;
import com.supinfo.youfood.entity.Menu;
import com.supinfo.youfood.entity.ProductType;
import com.supinfo.youfood.entity.Restaurant;
import com.supinfo.youfood.entity.Waiter;

/**
 * Application Lifecycle Listener implementation class PersistenceManager
 *
 */
@WebListener
public class PersistenceManager implements ServletContextListener {
	
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("YouFood-PU");
	private static final String PASS_SENTENCE= "youfood@inc";

	
	public static EntityManagerFactory getEntityManagerFactory(){
		return emf;
	}

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent servcon) {

    	if (RestaurantDAO.getInstance().getAllRestaurant().isEmpty())
    	{
    		RestaurantDAO.getInstance().create(new Restaurant("Tan nou Mem","Guadeloupe","Singo",5));
    		RestaurantDAO.getInstance().create(new Restaurant("Fouquets","Paris","Mizio",10));
    		RestaurantDAO.getInstance().create(new Restaurant("Riviera","Lille","Jenovah"));
    		RestaurantDAO.getInstance().create(new Restaurant("La tourelle","Montreal","Meztel"));
    		AdministratorDAO.getInstance().create(new Administrator("admin", "admin", "admin", "Admin"));
    		WaiterDAO.getInstance().create(new Waiter("waiter","waiter","waiter","Waiter"));
    		ProductTypeDAO.getInstance().create(new ProductType("Dessert"));
    		ProductTypeDAO.getInstance().create(new ProductType("Entrée"));
    		MealDAO.getInstance().create(new Meal("Saumon","Poisson gras",new Float(29.9),true));
    		MealDAO.getInstance().create(new Meal("Steak","Steak haché de boeuf aux épices du champ",new Float(19.9),false));
    		MenuDAO.getInstance().create(new Menu("Le médiévale"));
    		MenuDAO.getInstance().create(new Menu("Le mexécian"));
    	}
    	
    	/*Securite sec = new Securite();
    	System.out.println("crypted : " + sec.crypter(PASS_SENTENCE, "trr"));
    	System.out.println(" ts" + sec.decrypter(PASS_SENTENCE,sec.crypter(PASS_SENTENCE, "test")));
    	DesEncrypterText.getInstance().encrypt("text");
    	System.out.println(DesEncrypterText.getInstance().decrypt(DesEncrypterText.getInstance().encrypt("text")))
    	
    	File keyFile = new File("C:\\Users\\Charlie\\Documents\\test.txt");
    	SecretKey key = null;
        if(keyFile.isFile())
        {
        	try {
				key = DesEncrypter.loadKey(keyFile);
				System.out.println("load");
			} catch (IOException e) {
				System.out.println("fail1");
			}
        }
        else
        {
        	try {
        		System.out.println("c1");
				keyFile.createNewFile();
				System.out.println("c2");
				key = DesEncrypter.generateKey();
				DesEncrypter.saveKey(key, keyFile);
				System.out.println("new");
			} catch (IOException | NoSuchAlgorithmException e) {
				System.out.println("fail2");
			}
        }
        */
        
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        emf.close();
    }

	public static String getPassSentence() {
		return PASS_SENTENCE;
	}
	
}
