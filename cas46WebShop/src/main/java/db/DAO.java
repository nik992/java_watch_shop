package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import model.ArticleHelper;
import model.Order;
import model.User;




public class DAO {

	DbManager db = new DbManager();
	
	//DEFINICIJU SVIH SQL UPITA KOJE KORISTIMO NAD BAZOM 
     private static String SELECTUSERBYUSERNAMEANDPASSWORD = "SELECT * FROM users WHERE username=? AND password=? ";
	 private static String SELECTALLUSERS = "SELECT * FROM users";
	 private static String DELETEUSER = "DELETE FROM users WHERE user_id=?";
	 private static String GETUSERBYID = "SELECT * FROM users WHERE user_id=?";
	private static String UPDATEUSER ="UPDATE users SET first_name=?,last_name=?, phone=?, adress=?, email=?, username=?, password=?, admin=? WHERE user_id=?";
	private static String SELECTALLARTICLES = "SELECT articles.article_id, articles.article_name, articles.article_desc,articles.article_price, articles.article_stock_num,articles.article_color,articles.article_image, categories.category_name FROM articles JOIN articles_categories ON articles.article_id = articles_categories.article_id JOIN categories on articles_categories.category_id = categories.category_id";
	 private static String GETARTICLEBYID = "SELECT * FROM articles WHERE article_id=?";
	 private static String INSERTORDER = "INSERT INTO orders VALUES(null,?,?,?,?,?)";
	 private static String INSERTARTTICLEORDER = "INSERT INTO articles_orders VALUES(null,?,?,?)";

	
	
	
	
	
	
	
	
	//DEFINICIJA METODA - SVAKI GORE DEFINISAN UPIT CE IMATI SVOJU METODU 
     public User logIn(String username, String password) {
    	 Connection conn = null;
    	 PreparedStatement pstm = null;
    	 ResultSet result =null;
    	   	 
    	 User u = null;
    	 	 
    	 try {
			
    		 conn = db.getConnection();
    		 
    		 pstm = conn.prepareStatement(SELECTUSERBYUSERNAMEANDPASSWORD);
    		 pstm.setString(1, username);
    		 pstm.setString(2, password);
    		 pstm.execute();
    		 
    		 result =pstm.getResultSet();
    		
    		 if(result.next()) {
    			 u = new User();
    			 
    			 u.setId(result.getInt("user_id"));
    			 u.setFirst_name(result.getString("first_name"));
    			 u.setLast_name(result.getString("last_name"));
    			 u.setPhone(result.getString("phone"));
    			 u.setAdress(result.getString("adress"));
    			 u.setEmail(result.getString("email"));
    			 u.setUsername(result.getString("username"));
    			 u.setPassword(result.getString("password"));
    			 u.setAdmin(result.getBoolean("admin"));

    		 }
    		     		 
		} catch (Exception e) {			
			e.printStackTrace();
					
		}
    	    	     	 
    	 return u; 	   	 
     }
     
   
	public ArrayList<User> getAllUsers(){
		
		 Connection conn = null;
    	 PreparedStatement pstm = null;
    	 ResultSet result =null;
    	 
    	 ArrayList<User> usersList = new ArrayList<User>();
    	 
    	 
    	 try {
              conn = db.getConnection(); 		 
    		  pstm = conn.prepareStatement(SELECTALLUSERS);
    	      pstm.execute();   		 
              result =pstm.getResultSet();
              
              while (result.next()) {
			      User u = new User();
			  
			         u.setId(result.getInt("user_id"));
	    			 u.setFirst_name(result.getString("first_name"));
	    			 u.setLast_name(result.getString("last_name"));
	    			 u.setPhone(result.getString("phone"));
	    			 u.setAdress(result.getString("adress"));
	    			 u.setEmail(result.getString("email"));
	    			 u.setUsername(result.getString("username"));
	    			 u.setPassword(result.getString("password"));
	    			 u.setAdmin(result.getBoolean("admin"));
	    			 
	    			 usersList.add(u);			
			}		 
    		 
		} catch (Exception e) {
			e.printStackTrace();			
		}    	 
    	 return usersList; 	 
	}
	
	
	public void deleteUser(int id) {
		
		 Connection conn = null;
    	 PreparedStatement pstm = null;
    	 
    	 try {
    		  conn = db.getConnection(); 		 
    		  pstm = conn.prepareStatement(DELETEUSER);
    		  
    		  pstm.setInt(1, id);
    		  pstm.execute();
    		    		 		
		} catch (Exception e) {
			e.printStackTrace();
			
		}finally {
			db.closeConnection(conn);
			db.closePrepareStmt(pstm);
		}
			
	}
	
	
	public User getUserById(int id) {
		
		//GETUSERBYID
		 Connection conn = null;
    	 PreparedStatement pstm = null;
    	 ResultSet result =null;
    	 
    	 
    	 User u = null;
    	 
    	 try {
			conn=db.getConnection();
			 pstm = conn.prepareStatement(GETUSERBYID);
			  pstm.setInt(1, id);
    		  pstm.execute();
    		  
    		  result =pstm.getResultSet();
    		    if (result.next()) {
    		    	u = new User();
    		    	u.setId(result.getInt("user_id"));
    		    	u.setFirst_name(result.getString("first_name"));
    		    	u.setLast_name(result.getString("last_name"));
    		    	u.setPhone(result.getString("phone"));
    		    	u.setAdress(result.getString("adress"));
    		    	u.setEmail(result.getString("email"));
    		    	u.setUsername(result.getString("username"));
    		    	u.setPassword(result.getString("password"));
    		    	u.setAdmin(result.getBoolean("admin"));

    		    }
	 
		} catch (Exception e) {
          e.printStackTrace();
          
        }finally {
			db.closeConnection(conn);
			db.closePrepareStmt(pstm);
		}
    	 
    	 return u;
	}
	
	
	public String updateUser(User u) {
		
		 Connection conn = null;
    	 PreparedStatement pstm = null;
    	 
    	 
    	 try {
    		 
    		 conn=db.getConnection();
			 pstm = conn.prepareStatement(UPDATEUSER);
			 
			 pstm.setString(1, u.getFirst_name());
			 pstm.setString(2, u.getLast_name());
			 pstm.setString(3, u.getPhone());
			 pstm.setString(4, u.getAdress());
			 pstm.setString(5, u.getEmail());
			 pstm.setString(6, u.getUsername());
			 pstm.setString(7, u.getPassword());
			 pstm.setBoolean(8, u.isAdmin());
			 pstm.setInt(9, u.getId());

    		 pstm.execute();
    		 
    		 return "Update success";
    		 
    		 
    		 
			
		} catch (Exception e) {
			e.printStackTrace();
			return "";
			
		}finally {
			db.closeConnection(conn);
			db.closePrepareStmt(pstm);
		}	
		
	}
	
	public ArrayList<ArticleHelper> getAllArticles(){
		
	 Connection conn = null;
   	 PreparedStatement pstm = null;
   	 ResultSet result =null;
   	 
   	 ArrayList<ArticleHelper> articlesList = new ArrayList<ArticleHelper>();
   	 
   	 
   	 try {
          conn = db.getConnection(); 		 
   		  pstm = conn.prepareStatement(SELECTALLARTICLES);
   	      pstm.execute();   		 
          result =pstm.getResultSet();
             
             while (result.next()) {
			      ArticleHelper a = new ArticleHelper();
			  
			         a.setId(result.getInt("article_id"));
	    			 a.setName(result.getString("article_name"));
	    			 a.setDesc(result.getString("article_desc"));
	    			 a.setCategories(result.getString("category_name"));
	    			 a.setPrice(result.getDouble("article_price"));
                     a.setStockNumber(result.getInt("article_stock_num"));
	    			 a.setColor(result.getString("article_color"));
	    			 a.setImage(result.getString("article_image"));
	    			 
	    			 
	    			 articlesList.add(a);			
			}		 
   		 
		} catch (Exception e) {
			e.printStackTrace();			
		}    	 
   	 
   	 
   	 return articlesList; 	 
	}
	
	
	public ArticleHelper getArticleById(int id) {
		
		//GETUSERBYID
		 Connection conn = null;
    	 PreparedStatement pstm = null;
    	 ResultSet result =null;
    	 
    	 
    	 ArticleHelper a = null;
    	 
    	 try {
			conn=db.getConnection();
			 pstm = conn.prepareStatement(GETARTICLEBYID);
			  pstm.setInt(1, id);
    		  pstm.execute();
    		  
    		  result =pstm.getResultSet();
    		    if (result.next()) {
    		    	a = new ArticleHelper();
    		    
    		    	  a.setId(result.getInt("article_id"));
 	    			 a.setName(result.getString("article_name"));
 	    			 a.setDesc(result.getString("article_desc"));
 	    			 //nemamo naziv kategorije jer nemamo join sql upit za sada
 	    			// a.setCategories(result.getString("category_name"));
 	    			 a.setPrice(result.getDouble("article_price"));
                      a.setStockNumber(result.getInt("article_stock_num"));
 	    			 a.setColor(result.getString("article_color"));
 	    			 a.setImage(result.getString("article_image"));

    		    }
	 
		} catch (Exception e) {
          e.printStackTrace();
          
        }finally {
			db.closeConnection(conn);
			db.closePrepareStmt(pstm);
		}
    	 
    	 return a;
	}
	
	
	
	   public int  insertOrder(Order o) {
		   
	    	 Connection conn = null;
	    	 PreparedStatement pstm = null;
	    	 ResultSet result =null;
	    	   	 
	          int lastInsertedId = 0;
	    	 	 
	    	 try {
				
	    			conn=db.getConnection();
	    			
	    			pstm = conn.prepareStatement(INSERTORDER, Statement.RETURN_GENERATED_KEYS);
	    			pstm.setDate(1, java.sql.Date.valueOf(o.getOrderDate()));
	    			pstm.setTime(2, java.sql.Time.valueOf(o.getOrderTime()));
	    			pstm.setString(3, o.getStatus());
	    		    pstm.setDouble(4, o.getOrderSum());
	    		    pstm.setInt(5, o.getUserId());

	    		    
	    		    pstm.executeUpdate();
	    		    result = pstm.getGeneratedKeys();
	    		    
	    		    while (result.next()) {
					     lastInsertedId = result.getInt(1);					
					}
   		 
	    		 
			} catch (Exception e) {			
				e.printStackTrace();
						
			}finally {
				db.closeConnection(conn);
				db.closePrepareStmt(pstm);
			}
	    	 
	    	    	     	 
	    	 return lastInsertedId; 	   	 
	     }
	     
	
	   public String  insertArticlesOrder(int articleId, int orderId, int qty) {
		   
	    	 Connection conn = null;
	    	 PreparedStatement pstm = null;
	    	   	     	 	 
	    	 try {			
	    			conn=db.getConnection();
	    			
	    			pstm = conn.prepareStatement(INSERTARTTICLEORDER);
	    		
	    		    pstm.setInt(1,articleId);
	    		    pstm.setInt(2,orderId);
	    		    pstm.setInt(3,qty);
	    		    
	    		    pstm.executeUpdate();
	    		    	    		
	    		 return "Success";
	    		 
			} catch (Exception e) {			
				e.printStackTrace();
						
				return null;
			}finally {
				db.closeConnection(conn);
				db.closePrepareStmt(pstm);
			}
	    	 
	    	    	     	 
	     }
	     
	
	

}
