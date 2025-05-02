package org.example;

import org.example.product.Product;
import org.example.product.ProductService;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EndPointsJAXRS {

    private ProductService service = new ProductService();

    @GET
    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }

    @GET
    @Path("/{id}")
    public Response getProduct(@PathParam("id") int id) {
        Product product = service.getProduct(id);
        if (product == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(product).build();
    }

    @POST
    public Response addProduct(Product product, @Context UriInfo uriInfo) {
        Product newProduct = service.addProduct(product);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(Integer.toString(newProduct.getId()));
        return Response.created(builder.build()).entity(newProduct).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateProduct(@PathParam("id") int id, Product product) {
        Product updated = service.updateProduct(id, product);
        if (updated == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") int id) {
        Product deleted = service.deleteProduct(id);
        if (deleted == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok().build();
    }
}
