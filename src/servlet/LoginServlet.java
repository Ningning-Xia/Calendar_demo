package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import table.eventTable;
import table.userTable;

import management.RDSManagement;
import model.Event;
import model.User;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userName = request.getParameter("account");
		String password = request.getParameter("password");
		User user = null;
		if (userName!=null)
		{
			user = userTable.getUserByUsername(userName);
			if (user == null) {
				try {
					String error = "Wrong accout or password, try again.";
					request.setAttribute("error",error);
					RequestDispatcher view = request.getRequestDispatcher("Login.jsp");
					view.forward(request,response);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			else if(!password.equals(user.getPassword()))
			{
				System.out.println(password);
				System.out.println(user.getPassword());
				user=null;	
			}
		}
		else
		{
			throw new ServletException("user was not specified");
		}
		
		if(user != null){
			//System.out.println("user");
			try {
				request.setAttribute("user",user);
			    request.getSession().setAttribute("user", user);
				int uid = user.getUid();
				
				ArrayList<Event> eventList = new ArrayList<Event>();
				//RDSManagement rds = new RDSManagement();
				eventList = eventTable.getEventsByTime(uid);
				request.getSession().setAttribute("eventList", eventList);
				
				 response.sendRedirect("calendar.jsp");
				
		/*		RequestDispatcher view = request.getRequestDispatcher("calendar.jsp");
				view.forward(request,response);*/
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else
		{
			try {
				String error = "Wrong accout or password, try again.";
				request.setAttribute("error",error);
				RequestDispatcher view = request.getRequestDispatcher("Login.jsp");
				view.forward(request,response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
