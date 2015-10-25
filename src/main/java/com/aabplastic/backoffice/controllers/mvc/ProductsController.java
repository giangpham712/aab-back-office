package com.aabplastic.backoffice.controllers.mvc;

import com.aabplastic.backoffice.exceptions.ResourceNotFoundException;
import com.aabplastic.backoffice.models.Product;
import com.aabplastic.backoffice.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;

@Controller
public class ProductsController {

    @Autowired
    private ProductService productService;

    @RequestMapping("/products")
    public String listProducts(Model model, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) throws Exception {

        if (page == null) {
            page = 1;
        }

        if (limit == null) {
            limit = 20;
        }

        Page<Product> pagedProducts = productService.listProducts(page - 1, limit, "name", Sort.Direction.ASC);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
        String jsonProducts = objectMapper.writeValueAsString(pagedProducts.getContent());

        model.addAttribute("totalPages", pagedProducts.getTotalPages());
        model.addAttribute("totalProducts", pagedProducts.getTotalElements());
        model.addAttribute("products", jsonProducts);

        return "list-products";
    }

    @RequestMapping("/products/new")
    public String newProduct(Model model) {

        model.addAttribute("headerTitle", "New product");
        model.addAttribute("mode", "new");
        model.addAttribute("product", "{}");
        return "edit-product";
    }

    @RequestMapping("/products/edit/{id}")
    public String editProduct(@PathVariable long id, Model model) throws Exception {

        Product product = productService.getProductById(id);

        if (product == null) {
            throw new ResourceNotFoundException("Not found");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));

        String jsonProducts = objectMapper.writeValueAsString(product);

        model.addAttribute("headerTitle", "Edit product");
        model.addAttribute("mode", "edit");
        model.addAttribute("product", jsonProducts);

        return "edit-product";
    }
}
