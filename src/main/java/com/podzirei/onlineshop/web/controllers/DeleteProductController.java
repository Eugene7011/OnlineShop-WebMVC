package com.podzirei.onlineshop.web.controllers;

import com.podzirei.onlineshop.service.ProductService;
import com.podzirei.onlineshop.web.util.PageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(path = "/products/delete")
public class DeleteProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public void deleteGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = createPageVariablesMap(request);
        PageGenerator pageGenerator = PageGenerator.getInstance();
        String page = pageGenerator.getPage("deleteproduct.html", pageVariables);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(page);
    }

    @PostMapping
    public void deletePost(HttpServletRequest request, HttpServletResponse response) {
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
