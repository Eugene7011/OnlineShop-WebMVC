package servlets;

import dao.JdbcProductDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DeleteProductServletTest {
    DeleteProductServlet deleteProductServlet = new DeleteProductServlet();
    UpdateProductServlet updateProductServlet = new UpdateProductServlet();

    @Test
    public void testDeleteProductTest() throws IOException {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        JdbcProductDao mockJdbcProductDao = mock(JdbcProductDao.class);

        String id = new String();
        when(mockRequest.getParameter(id)).thenReturn("1");
    }
}
