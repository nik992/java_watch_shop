package controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.DAO;
import model.ArticleHelper;
import model.Order;
import model.User;


@WebServlet("/OrderController")
public class OrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

 

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		 String action = request.getParameter("action");
			
			if (action != null &&  action.equals("createOrder")){
				
				//first of all we must check if user exist in session user 
				HttpSession s = request.getSession();
				
				User loggedIn = null;
				loggedIn = (User) s.getAttribute("logged in");
				
				if(loggedIn != null) {
					
					
					//to be continued... 
					ArrayList<ArticleHelper> cartList = (ArrayList<ArticleHelper>)s.getAttribute("cart");

					double sum = 0;
					
					  for(int i=0; i<cartList.size(); i++){
					      sum = sum + (cartList.get(i).getPrice() * cartList.get(i).getCartQty());
					  }
					
					/*  System.out.println(sum);
					  
					  System.out.println("----cart -----");
					  System.out.println(cartList);

					  
					  System.out.println("----user -----");
					  System.out.println(loggedIn);
					  */
					  			  
					  DAO dao = new DAO();
					  		  
					//  DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
					  LocalDate date = LocalDate.now();
					  LocalTime time = LocalTime.now();
					  				  
					  Order o = new Order(0, loggedIn.getId(), date, time, "active", sum);
					  
				int lastOrderId = dao.insertOrder(o);
				
				
				if(lastOrderId>0) {
					
					Boolean created = true;
					for (ArticleHelper article : cartList) {
		
					String orderMsg =	dao.insertArticlesOrder(article.getId(), lastOrderId, article.getCartQty());
					
						if(orderMsg == null) {
							created = false;
						}
						
					}
					
					if(created) {
						request.setAttribute("userError", "Thank you for your order, we will contact you.");
						request.getRequestDispatcher("checkout.jsp").forward(request, response);
					}else {
						request.setAttribute("userError", "ORDER IS NOT CREATED, PLEASE CONTACT US.");
						request.getRequestDispatcher("checkout.jsp").forward(request, response);
					}
					
					
					
				}else {
					
					request.setAttribute("userError", "Error order is not created.");
					request.getRequestDispatcher("checkout.jsp").forward(request, response);
				}
				
				

				System.out.println(lastOrderId);

				}else {
					
					request.setAttribute("userError", "You must be logged in, please log in.");
					request.getRequestDispatcher("checkout.jsp").forward(request, response);
				}
				
				
			}
		
		
		
		
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
