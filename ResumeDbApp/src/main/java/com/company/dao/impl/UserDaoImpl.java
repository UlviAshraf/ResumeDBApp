/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.dao.impl;

import com.company.bean.User;
import static com.company.dao.inter.AbstractDao.connect;
import com.company.dao.inter.UserDaoInter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author UlviAshraf
 */
public class UserDaoImpl implements UserDaoInter {

    @Override
    public List<User> getAllUser() {
        List<User> result = new ArrayList();
        try {
            Connection c = connect();
            Statement stmt = c.createStatement();
            stmt.execute("select * from user");
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                result.add(new User(id, name, surname, email, phone));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(User u) {
        try {
            Connection c = connect();
            PreparedStatement stmt = c.prepareStatement("update user set name=?,surname=?,email=?,phone=? where id=?");
            stmt.setString(1, u.getName());
            stmt.setString(2, u.getSurname());
            stmt.setString(3, u.getEmail());
            stmt.setString(4, u.getPhone());
            stmt.setInt(5, u.getId());
            return stmt.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public void remove(int id) {
        try {
            Connection c = connect();
            Statement stmt = c.createStatement();
            stmt.execute("delete from useer where id=" + id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public User getById(int userId) {
        User u = null;
        try {
            Connection c = connect();
            Statement stmt = c.createStatement();
            stmt.execute("select * from user where id="+userId);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                u = new User(id, name, surname, email, phone);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return u;
    }

}
