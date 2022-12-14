package com.podzirei.onlineshop.web.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.podzirei.onlineshop.service.ProductService;
import com.podzirei.onlineshop.service.ServiceLocator;
import com.podzirei.onlineshop.web.util.PageGenerator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DeleteProductServlet extends HttpServlet {

    private final ProductService productService = (ProductService) ServiceLocator.getBean("productService");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = createPageVariablesMap(request);
        PageGenerator pageGenerator = PageGenerator.getInstance();
        String page = pageGenerator.getPage("deleteproduct.html", pageVariables);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        productService.delete(Integer.parseInt(id));
        try {
            response.setContentType("text/html;charset=utf-8");
            response.sendRedirect("/products");
        } catch (IOException e) {
            throw new RuntimeException("Can not delete product by id", e);
        }
    }

    private Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("id", request.getParameter("id"));

        return pageVariables;
    }
}
