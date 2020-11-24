/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.dao.impl;

import com.company.entity.Country;
import com.company.entity.User;
import static com.company.dao.inter.AbstractDao.connect;
import com.company.dao.inter.UserDaoInter;
import java.sql.Connection;
import java.sql.Date;
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
    
    private User getUser(ResultSet rs) throws Exception {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String email = rs.getString("email");
        String phone = rs.getString("phone");
        Date birthdate = rs.getDate("birthdate");
        int birthplaceId = rs.getInt("birthplace_id");
        int nationalityId = rs.getInt("nationality_id");
        String nationalityStr = rs.getString("nationality");
        String birthplaceStr = rs.getString("birthplace");
        Country nationality = new Country(nationalityId, null, nationalityStr);
        Country birthplace = new Country(birthplaceId, birthplaceStr, null);
        return (new User(id, name, surname, email, phone, birthdate, nationality, birthplace));
    }
    
    @Override
    public List<User> getAllUser() {
        List<User> result = new ArrayList();
        try {
            Connection c = connect();
            Statement stmt = c.createStatement();
            stmt.execute("SELECT USER "
                    + "	.*, "
                    + "	country.nationality, "
                    + "	c.name AS birthplace"
                    + "	 "
                    + "FROM "
                    + "	USER LEFT JOIN country ON USER.nationality_id = country.id "
                    + "	LEFT JOIN country c ON user.birthplace_id = c.id");
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                User u = getUser(rs);
                result.add(u);
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
            stmt.execute("delete from user where id=" + id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public User getById(int userId) {
        User result = null;
        try {
            Connection c = connect();
            Statement stmt = c.createStatement();
            stmt.execute("SELECT USER "
                    + "	.*, "
                    + "	country.nationality, "
                    + "	c.name AS birthplace"
                    + "	 "
                    + "FROM "
                    + "	USER LEFT JOIN country ON USER.nationality_id = country.id "
                    + "	LEFT JOIN country c ON user.birthplace_id = c.id where USER.id=" + userId);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                result = getUser(rs);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return result;
    }
    
    @Override
    public boolean addUser(User u) {
        try {
            Connection c = connect();
            PreparedStatement stmt = c.prepareStatement("insert into user (name,surname,email,phone,id)values(?,?,?,?,?)");
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
    
}
