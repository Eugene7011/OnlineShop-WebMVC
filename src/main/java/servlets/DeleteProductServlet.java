package servlets;

import dao.JdbcProductDao;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pagegenerator.PageGenerator;
import security.SecurityService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeleteProductServlet extends HttpServlet {
    SecurityService securityService = new SecurityService();
    private List<String> userTokens;

    public DeleteProductServlet(List<String> userTokens) {
        this.userTokens = userTokens;
    }

    public DeleteProductServlet() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        boolean isAuth = securityService.isAuth(request, userTokens);
        if (isAuth) {
            Map<String, Object> pageVariables = createPageVariablesMap(request);

            PageGenerator pageGenerator = PageGenerator.instance();

            String page = pageGenerator.getPage("deleteproduct.html", pageVariables);

            try {
                response.getWriter().println(page);
            } catch (IOException e) {
                throw new RuntimeException("Cant get data from request about delete product");
            }
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            try {
                response.sendRedirect("/login");
            } catch (IOException exception) {
                throw new RuntimeException("You have to log in");
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        boolean isAuth = securityService.isAuth(request, userTokens);
        if (isAuth) {
            String id = request.getParameter("id");

            response.setContentType("text/html;charset=utf-8");

            JdbcProductDao jdbcProductDao = new JdbcProductDao();
            try {
                jdbcProductDao.remove(Integer.parseInt(id));
            } catch (NumberFormatException e) {
                throw new RuntimeException("Cant remove if from database. Try to write id correctly");
            }
            try {
                response.sendRedirect("/products");
            } catch (IOException e) {
                throw new RuntimeException("Cant show page with deleted user");
            }
            try {
                response.getWriter().close();
            } catch (IOException exception) {
                throw new RuntimeException("Cant show update products");
            }
        } else {
            try {
                response.sendRedirect("/login");
            } catch (IOException exception) {
                throw new RuntimeException("You have to log in");
            }
        }
    }

    static Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("id", request.getParameter("id"));

        return pageVariables;
    }
}
