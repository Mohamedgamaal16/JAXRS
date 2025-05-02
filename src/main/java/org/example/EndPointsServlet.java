package org.example;


import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.product.Product;
import org.example.product.ProductService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class EndPointsServlet extends HttpServlet {
    private ProductService service = new ProductService();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");
        resp.setContentType("application/json");

        if (idParam == null) {
            List<Product> products = service.getAllProducts();
            resp.getWriter().write(gson.toJson(products));
        } else {
            int id = Integer.parseInt(idParam);
            Product product = service.getProduct(id);
            if (product == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            resp.getWriter().write(gson.toJson(product));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        Product product = gson.fromJson(reader, Product.class);
        Product created = service.addProduct(product);

        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(created));
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        Product product = gson.fromJson(reader, Product.class);
        Product updated = service.updateProduct(product.getId(),product);

        if (updated == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(updated));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");
        if (idParam == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int id = Integer.parseInt(idParam);
        Product deleted = service.deleteProduct(id);
        if (deleted == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }
}
