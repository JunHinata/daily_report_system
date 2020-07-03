package controllers.follows;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow;
import utils.DBUtil;

/**
 * Servlet implementation class FollowsNewServlet
 */
@WebServlet("/follows/create")
public class FollowsCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowsCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            EntityManager em = DBUtil.createEntityManager();

            Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");
            Employee followed_employee = em.find(Employee.class, Integer.parseInt(request.getParameter("id")));

            List<Follow> duplication = em.createNamedQuery("checkDuplication", Follow.class)
                                         .setParameter("login", login_employee)
                                         .setParameter("id", followed_employee)
                                         .getResultList();

            if(duplication.size() >= 1) {
                em.close();
                request.getSession().setAttribute("flush", "対象の従業員を既にフォローしています。");

                response.sendRedirect(request.getContextPath() + "/follows/index");
            }
            else {
                Follow f = new Follow();
                f.setFollow(login_employee);
                f.setFollowed(followed_employee);

                em.getTransaction().begin();
                em.persist(f);
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("flush", "フォローが完了しました。");

                response.sendRedirect(request.getContextPath() + "/follows/index");
            }
    }

}
