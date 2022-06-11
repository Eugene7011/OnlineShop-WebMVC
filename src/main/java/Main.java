import dao.jdbc.JdbcProductDao;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import service.ProductService;
import servlets.*;

public class Main {

    public static void main(String[] args) throws Exception {
        JdbcProductDao jdbcProductDao = new JdbcProductDao();
        ProductService productService = new ProductService(jdbcProductDao);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(new AllProductsServlet()), "/products");
        context.addServlet(new ServletHolder(new AddProductServlet(productService)), "/products/add");
        context.addServlet(new ServletHolder(new DeleteProductServlet()), "/products/delete");
        context.addServlet(new ServletHolder(new UpdateProductServlet()), "/products/update");
        context.addServlet(new ServletHolder(new SearchProductServlet()), "/products/search");

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
    }
}