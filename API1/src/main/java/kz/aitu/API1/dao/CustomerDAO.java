package kz.aitu.API1.dao;

import kz.aitu.API1.model.Customer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public List<Customer> findAll() {
        List<Customer> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM customers")) {

            while (rs.next()) {
                list.add(new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public Customer findById(int id) {

        Customer customer = null;

        String sql = "SELECT * FROM customers WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                customer = new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address")
                );
            }

            if (customer == null) {
                throw new RuntimeException("Customer not found");
            }

            return customer;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void save(Customer c) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps =
                     conn.prepareStatement("INSERT INTO customers VALUES (?, ?, ?)")) {

            ps.setInt(1, c.getId());
            ps.setString(2, c.getName());
            ps.setString(3, c.getAddress());

            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Customer c) {

        String sql = "UPDATE customers SET name = ?, address = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getName());
            ps.setString(2, c.getAddress());
            ps.setInt(3, c.getId());

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new RuntimeException("Customer not found for update");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteById(int id) {

        String sql = "DELETE FROM customers WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new RuntimeException("Customer not found for delete");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<Customer> findAllSorted() {

        List<Customer> list = findAll();

        list.sort((a, b) ->
                a.getName().compareToIgnoreCase(b.getName()));

        return list;
    }

}
