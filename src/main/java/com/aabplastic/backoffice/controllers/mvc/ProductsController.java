package com.aabplastic.backoffice.controllers.mvc;

import com.aabplastic.backoffice.exceptions.ResourceNotFoundException;
import com.aabplastic.backoffice.models.BillOfMaterials;
import com.aabplastic.backoffice.models.Product;
import com.aabplastic.backoffice.services.BillOfMaterialsService;
import com.aabplastic.backoffice.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;

@Controller
public class ProductsController {

    @Autowired
    private ProductService productService;

    @Autowired
    private BillOfMaterialsService billOfMaterialsService;

    @RequestMapping("/products")
    public String listProducts(Model model) throws Exception {

        int page = 1;
        int limit = 200;
        String search = "";

        Page<Product> pagedProducts = productService.listProducts(search, page, limit, "name", Sort.Direction.ASC);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
        String jsonProducts = objectMapper.writeValueAsString(pagedProducts.getContent());

        model.addAttribute("totalPages", pagedProducts.getTotalPages());
        model.addAttribute("totalProducts", pagedProducts.getTotalElements());
        model.addAttribute("products", jsonProducts);
        model.addAttribute("page", page);
        model.addAttribute("limit", limit);

        return "list-products";
    }

    @RequestMapping("/products/new")
    public String newProduct(Model model) throws Exception {

        Product product = new Product();

        Iterable<BillOfMaterials> boms = billOfMaterialsService.listBOMs();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));

        String jsonProducts = objectMapper.writeValueAsString(product);
        String jsonBOMs = objectMapper.writeValueAsString(boms);

        model.addAttribute("headerTitle", "New product");
        model.addAttribute("mode", "new");
        model.addAttribute("product", jsonProducts);
        model.addAttribute("boms", jsonBOMs);

        return "edit-product";
    }

    @RequestMapping("/products/edit/{id}")
    public String editProduct(@PathVariable long id, Model model) throws Exception {

        Product product = productService.getProductById(id);

        if (product == null) {
            throw new ResourceNotFoundException("Not found");
        }

        Iterable<BillOfMaterials> boms = billOfMaterialsService.listBOMs();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));

        String jsonProducts = objectMapper.writeValueAsString(product);
        String jsonBOMs = objectMapper.writeValueAsString(boms);

        model.addAttribute("headerTitle", "Edit product");
        model.addAttribute("mode", "edit");
        model.addAttribute("product", jsonProducts);
        model.addAttribute("boms", jsonBOMs);

        return "edit-product";
    }
}
