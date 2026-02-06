package kz.aitu.API1.controllers;

import kz.aitu.API1.dao.CustomerDAO;
import kz.aitu.API1.model.Customer;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class MyController {

    private final CustomerDAO dao = new CustomerDAO();

    @GetMapping
    public List<Customer> getAll() {
        return dao.findAll();
    }

    @PostMapping
    public String add(@RequestBody Customer customer) {
        dao.save(customer);
        return "Customer added";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable int id,
                         @RequestBody Customer customer) {

        customer.setId(id);
        dao.update(customer);

        return "Customer updated";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        dao.deleteById(id);
        return "Customer deleted";
    }
}
