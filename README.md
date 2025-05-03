# Product Management API – JAX-RS vs Servlet

This repository contains two implementations of a simple Product Management REST API:
- One using **JAX-RS**
- One using **Java Servlets**

Both versions support basic CRUD operations (`Create`, `Read`, `Update`, `Delete`).

---

## 🔁 Key Differences

| Feature             | JAX-RS                                      | Servlet                                      |
|---------------------|---------------------------------------------|----------------------------------------------|
| **Framework Type**  | RESTful API framework                       | General-purpose HTTP API                     |
| **Routing**         | Declarative (`@Path`, `@GET`, etc.)         | Programmatic (`@WebServlet`, `doGet()`)      |
| **Request Handling**| Automatically maps to methods               | Manual parsing of `HttpServletRequest`       |
| **Response Handling**| Uses `Response` objects and annotations    | Uses `HttpServletResponse` directly          |
| **JSON Handling**   | Auto-serialization (if enabled)             | Manual (e.g., using Gson)                    |
| **Readability**     | Cleaner, more REST-focused                  | Verbose, more control                        |

---

## 🔍 Example: Get Product by ID

### JAX-RS
```java
@GET
@Path("/{id}")
public Response getProduct(@PathParam("id") int id) {
    Product product = service.getProduct(id);
    if (product == null)
        return Response.status(Response.Status.NOT_FOUND).build();
    return Response.ok(product).build();
}
```
### Servlet

```@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String idParam = req.getParameter("id");
    if (idParam != null) {
        int id = Integer.parseInt(idParam);
        Product product = service.get(id);
        if (product == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(product));
    }
}
