package com.aabplastic.backoffice.controllers.rest;

import com.aabplastic.backoffice.exceptions.ResourceNotFoundException;
import com.aabplastic.backoffice.models.Product;
import com.aabplastic.backoffice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductsRestController {

    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Product> listProducts(
            Model model,
            @RequestParam(value = "q", required = false) String search,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit) {

        if (page == null) {
            page = 1;
        }

        if (limit == null) {
            limit = 20;
        }

        if (search == null) {
            search = "";
        }

        Page<Product> products = productService.listProducts(search, page, limit, "name", Sort.Direction.ASC);
        return products.getContent();
    }

    /***
     * Get information of a specific product
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Product get(@PathVariable long id) {

        Product result = productService.getProductById(id);
        if (result == null)
            throw new ResourceNotFoundException(String.format("Product with id %d cannot be found", id));

        return result;
    }

    /***
     * Create a new product
     *
     * @param productDto
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody @Valid Product productDto) {

        Product created = productService.create(productDto);
        return created;
    }

    /***
     * Update a specific product
     *
     * @param productDto
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Product update(@PathVariable long id, @RequestBody @Valid Product productDto) {
        Product updated = productService.update(id, productDto);
        return updated;
    }

    /***
     * Delete a specific product
     *
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        productService.delete(id);

    }
}
