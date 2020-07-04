package controllers.follows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow;
import utils.DBUtil;

/**
 * Servlet implementation class FollowsIndexServlet
 */
@WebServlet("/follows/index")
public class FollowsIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowsIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

        List<Follow> follow_list = em.createNamedQuery("getMyAllFollows", Follow.class)
                                    .setParameter("id", login_employee)
                                    .getResultList();

        List<Follow> follower_list = em.createNamedQuery("getMyAllFollowers", Follow.class)
                                      .setParameter("id", login_employee)
                                      .getResultList();

        ArrayList<Integer> mutual_list = new ArrayList<Integer>();

        for(int i = 0; i < follower_list.size(); i++) {
            Integer f = follower_list.get(i).getFollow().getId();

            for(int j = 0; j < follow_list.size(); j++) {
                Integer f2 = follow_list.get(j).getFollowed().getId();
                if(f == f2) {
                    mutual_list.add(1);
                }
            }

            if(mutual_list.size() != i + 1) {
                mutual_list.add(0);
            }
        }

        em.close();

        request.setAttribute("follows", follow_list);
        request.setAttribute("followers", follower_list);
        request.setAttribute("follows_count", follow_list.size());
        request.setAttribute("followers_count", follower_list.size());
        request.setAttribute("mutual_list", mutual_list);
        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/follows/Index.jsp");
        rd.forward(request, response);
    }

}
