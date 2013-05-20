package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.services.s3.model.CannedAccessControlList;

import management.S3Class;
import management.Transcoder;

/**
 * Servlet implementation class TransVideoServlet
 */
public class TransVideoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static String bucketName = "ningxia91-bucket";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransVideoServlet() {
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
		String eid = (String)request.getParameter("eid");
		String key = (String)request.getParameter("key");
		
		if (eid != null) {
			request.setAttribute("eid", eid);
		}
		
		Transcoder transcoder = new Transcoder();
		String newkey = transcoder.CreatJob(key);
		String url = "https://s3.amazonaws.com/ningxia91-bucket/" + newkey;
		request.setAttribute("url", url);
		
		//S3Obj.setCannedAcl(CannedAccessControlList.PublicRead);
		S3Class s3 = new S3Class();
		s3.s3.setObjectAcl(bucketName, newkey, CannedAccessControlList.PublicRead);
		
		RequestDispatcher view = request.getRequestDispatcher("ListVideoServlet");
		view.forward(request, response);
		
	}

}
