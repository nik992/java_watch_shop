<%@page import="java.util.HashMap"%>
<%@page import="model.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>


   <%
    HttpSession sessionUser = request.getSession();
    User loggedIn = null;
    loggedIn = (User)sessionUser.getAttribute("logged in");
    
    //kupimo usera koji nam stize iz kontrolera
 User u =  (User)request.getAttribute("user");
    
      HashMap<String, String> userErrors =null;
      userErrors = (HashMap<String, String>)request.getAttribute("userErrors");
      
      
    
    if(u !=null){ 
    %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>Aroma Shop - Login</title>
	<link rel="icon" href="img/Fevicon.png" type="image/png">
  <link rel="stylesheet" href="vendors/bootstrap/bootstrap.min.css">
  <link rel="stylesheet" href="vendors/fontawesome/css/all.min.css">
	<link rel="stylesheet" href="vendors/themify-icons/themify-icons.css">
	<link rel="stylesheet" href="vendors/linericon/style.css">
  <link rel="stylesheet" href="vendors/owl-carousel/owl.theme.default.min.css">
  <link rel="stylesheet" href="vendors/owl-carousel/owl.carousel.min.css">
  <link rel="stylesheet" href="vendors/nice-select/nice-select.css">
  <link rel="stylesheet" href="vendors/nouislider/nouislider.min.css">

  <link rel="stylesheet" href="css/style.css">
</head>
<body>
	<!--================ Start Header Menu Area =================-->
	<header class="header_area">
    <div class="main_menu">
      <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container">
        <h5>Admin page</h5>
          <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <div class="collapse navbar-collapse offset" id="navbarSupportedContent">
            <ul class="nav navbar-nav menu_nav ml-auto mr-auto">
              <li class="nav-item active"><a class="nav-link" href="AdminController?action=showAllUsers">Users</a></li>
              <li class="nav-item submenu dropdown">
                <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                  aria-expanded="false"> Orders</a></li>
              <li class="nav-item submenu dropdown">
                <a href="AdminController?action=showAllProducts" class="nav-link dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                  aria-expanded="false">Products</a></li>
			<li class="nav-item submenu dropdown">
                <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                  aria-expanded="false">Categories</a> </li>
              <li class="nav-item"><a class="nav-link" href="AdminController?action=showAllProducers">Producers</a></li>
            </ul>

            <ul class="nav-shop">
              <li class="nav-item"><button><i class="ti-shopping-cart"></i><span class="nav-shop__circle">0</span></button> </li>
              <li class="nav-item">
              <%              
                 if(loggedIn != null){       
              %>
              <h4> <%= loggedIn.getFirst_name() +" "+loggedIn.getLast_name() %></h4>
                         
               <% } %>
              </li>
              
                 <%              
                 if(loggedIn != null){       
                 %>
                    <li class="nav-item"><a class="button button-header" href="UserController?action=logOut">Log out</a></li>
                 <% } %>          
              
            </ul>
          </div>
        </div>
      </nav>
    </div>
  </header>
	<!--================ End Header Menu Area =================-->
  
  <!-- ================ start banner area ================= -->	

	<!-- ================ end banner area ================= -->
  
  <!--================Login Box Area =================-->
	<section class="login_box_area">
		<div class="container">
			<div class="row">
		
				<div class="col-lg-12">
					<div class="login_form_inner register_form_inner">
						<h3>Edit user</h3>
		              <form class="row login_form" action="AdminController" id="register_form" method="post">
							<div class="col-md-12 form-group">
								<input type="text" class="form-control" id="firstname" name="first_name" value="<%= u.getFirst_name() %>" placeholder="First name" onfocus="this.placeholder = ''" onblur="this.placeholder = 'First name'">
							<span style="color:red; font-size:10px;"><% if(userErrors != null && userErrors.get("firstNameError") != null) out.println(userErrors.get("firstNameError")); %></span>					
							</div>
							<div class="col-md-12 form-group">
								<input type="text" class="form-control" id="lastname" name="last_name" value="<%= u.getLast_name() %>" placeholder="Last name" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Last name'">
							<span style="color:red; font-size:10px;"><% if(userErrors != null && userErrors.get("lastNameError") != null) out.println(userErrors.get("lastNameError")); %></span>
							</div>
							<div class="col-md-12 form-group">
								<input type="text" class="form-control" id="phone" name="phone" value="<%= u.getPhone() %>" placeholder="Phone number" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Phone number'">
							    <span style="color:red; font-size:10px;"><% if(userErrors != null && userErrors.get("phoneError") != null) out.println(userErrors.get("phoneError")); %></span>
							</div>
							<div class="col-md-12 form-group">
								<input type="text" class="form-control" id="adress" name="adress" value="<%= u.getAdress() %>" placeholder="Adress" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Adress'">
							<span style="color:red; font-size:10px;"><% if(userErrors != null && userErrors.get("adressError") != null) out.println(userErrors.get("adressError")); %></span>
							</div>
							<div class="col-md-12 form-group">
								<input type="text" class="form-control" id="name" name="username" value="<%= u.getUsername() %>" placeholder="Username" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Username'">
                                <span style="color:red; font-size:10px;"><% if(userErrors != null && userErrors.get("usernameError") != null) out.println(userErrors.get("usernameError")); %></span>						
                        	</div>
							<div class="col-md-12 form-group">
								<input type="email" class="form-control" id="email" name="email" value="<%= u.getEmail() %>" placeholder="Email Address" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Email Address'">
          				   	     <span style="color:red; font-size:10px;"><% if(userErrors != null && userErrors.get("emailError") != null) out.println(userErrors.get("emailError")); %></span>
          				    </div>
            				  <div class="col-md-12 form-group">
								<input type="password" class="form-control" id="password" name="password" value="<%= u.getPassword() %>" placeholder="Password" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Password'">
           					    <span style="color:red; font-size:10px;"><% if(userErrors != null && userErrors.get("passwordError") != null) out.println(userErrors.get("passwordError")); %></span>
           					   </div>
		                         <input type="hidden" name="userId" value="<%= u.getId() %>">

							<div class="col-md-12 form-group">
								<button type="submit" name="action" value="editUser" class="button button-register w-100">Edit User</button>
							</div>
		       </form>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!--================End Login Box Area =================-->



  <!--================ Start footer Area  =================-->	
	
	<!--================ End footer Area  =================-->



  <script src="vendors/jquery/jquery-3.2.1.min.js"></script>
  <script src="vendors/bootstrap/bootstrap.bundle.min.js"></script>
  <script src="vendors/skrollr.min.js"></script>
  <script src="vendors/owl-carousel/owl.carousel.min.js"></script>
  <script src="vendors/nice-select/jquery.nice-select.min.js"></script>
  <script src="vendors/jquery.ajaxchimp.min.js"></script>
  <script src="vendors/mail-script.js"></script>
  <script src="js/main.js"></script>
  
  <% } %>        
</body>
</html>