package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;

import db.DAO;

/**
 * Servlet implementation class AdminController
 */
@WebServlet("/AdminController")
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public AdminController() {
        super();
        }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		if (action != null &&  action.equals("showAllUsers")){
			
			DAO dao = new DAO();
			ArrayList<User> usersList = new ArrayList<User>();
			usersList =  dao.getAllUsers();
			
			request.setAttribute("usersList", usersList);
			request.getRequestDispatcher("adminPage.jsp").forward(request, response);
			
		}else if (action != null &&  action.equals("showAllProducts")){
		
			// logika za prikaz svih proizvoda - vezba kuci 
			
			
			request.getRequestDispatcher("adminProducts.jsp").forward(request, response);
			
		}else if (action != null &&  action.equals("showAllProducers")){
		
			// logika za prikaz svih proizvodjaca - vezba kuci 
			
			
			request.getRequestDispatcher("adminProducers.jsp").forward(request, response);

		}else if (action != null && action.equals("deleteUser")){
			
			//brisanje korisnika na klik linka delete u tabeli 
			
			//1. kupljnje id-a
			// 2 .Validacija id-a tj provera da li je popunjen
			//3. Pretvaranje id-a u int 
			//4. Kreiranje metode u dao klasi za brisanje korisnika i poziv te metode u kontroleru 
			//5. Nakon brisanja redirekcija korisnika na prikaz svih korisnika 
			
			String userId = request.getParameter("userId");
			
			 if (userId != null && userId != "") {
				 
				try {
					
					 int user_id = Integer.parseInt(userId);
					 DAO dao = new DAO();
					 
					 dao.deleteUser(user_id);
					 
					 //nakon brisanja potrebno je da prosledimo listu usera na stranu
					    ArrayList<User> usersList = new ArrayList<User>();
					    usersList =  dao.getAllUsers();					
						request.setAttribute("usersList", usersList);
						request.getRequestDispatcher("adminPage.jsp").forward(request, response);
					 
					// response.sendRedirect("adminPage.jsp");
					 
				} catch (Exception e) {
					e.printStackTrace();
				}
			 }
			
			
			
			
			
			
		}else if(action != null && action.equals("showEditUser")) {
			
			//prikaz strane za izmenu korisnika  sa popunjenim podacima o korisniku 
			String userId = request.getParameter("userId");
			
			//BICE ZAVRSENO SLEDECI CAS
			if(userId != null && userId !="") {
				
				try {
					int id = Integer.parseInt(userId);
					
					DAO dao = new DAO();
					User u = null;
					
					u = dao.getUserById(id);
					
					//System.out.println(u);
					
					request.setAttribute("user", u);
					request.getRequestDispatcher("editUser.jsp").forward(request, response);
					
					
					
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				
			}
						
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//editUser
		
         String action = request.getParameter("action");
		
		if (action != null &&  action.equals("editUser")){
			
			HashMap<String, String> userErrors = new HashMap<String, String>();
			
			
			String firstName = request.getParameter("first_name");
			String lastName = request.getParameter("last_name");
			String phone = request.getParameter("phone");
			String adress = request.getParameter("adress");
			String username = request.getParameter("username");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String userId = request.getParameter("userId");
			
			
					if(firstName==null || firstName.length()==0) {
						// error for first name
						userErrors.put("firstNameError", "Please enter valid first name");
					}
				
					if(lastName==null || lastName.length()==0) {
						// error for last name
						userErrors.put("lastNameError", "Please enter valid last name");
					}
								
					if(phone==null || phone.length()==0) {
						// error for phone
						userErrors.put("phoneError", "Please enter valid phone number");
					}
						
					if(adress==null || adress.length()==0) {
							//adress error
						userErrors.put("adressError", "Please enter valid adress");
					}
							
					if(username == null || username.length()==0) {
								// error for username
						userErrors.put("usernameError", "Please enter valid username");
					}
								
					if(email == null || email.length()==0) {
								// error for email
						userErrors.put("emailError", "Please enter valid email");
					}
																	
					if(password==null || password.length()==0) {
							// error for password
						userErrors.put("passwordError", "Please enter valid password");
					}
									
					
					if(!userErrors.isEmpty()) {
					  	     // if some error exist	
						User u = new User(firstName,lastName,phone,adress,email,username,password,false, Integer.parseInt(userId));
						
						request.setAttribute("userErrors", userErrors);
						request.setAttribute("user", u);
						request.getRequestDispatcher("editUser.jsp").forward(request, response);
						
					}else {
				       		
										try {
											
											int uId = Integer.parseInt(userId);
											
											DAO dao = new DAO();
											
											// uzimamo iz baze usera po id-u kako bi proverili da li je u bazi sacuvan kao admin ili obican korisnik 
											
											User u = null;
											
											u = dao.getUserById(uId);
																
											//System.out.println("IZMENA KORISNIKA");
											//System.out.println(u);
											
											
											User editedUser = new User(firstName,lastName,phone,adress,email,username,password, u.isAdmin(), uId);
											
									        String result = dao.updateUser(editedUser);
									        
									        if(result.equals("Update success")) {
									        	
									        	ArrayList<User> usersList = new ArrayList<User>();
												usersList =  dao.getAllUsers();
												
												request.setAttribute("usersList", usersList);
												request.setAttribute("updateMsg", "Update success");
												request.getRequestDispatcher("adminPage.jsp").forward(request, response);
									        	
									        }else {
									        	userErrors.put("Error", "Update failed.");
									        }
											
											
										} catch (Exception e) {
											System.out.println("Error in data convert");
										}
									
					}			
						
				
		

			
		





			
			
			
			
		}
		}
		
	}


