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

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author UlviAshraf
 */
public class UserDaoImpl implements UserDaoInter {

    private User getUser(ResultSet rs) throws Exception {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String email = rs.getString("email");
        String phone = rs.getString("phone");
        String profileDesc = rs.getString("profile_description");
        String address = rs.getString("address");
        Date birthdate = rs.getDate("birthdate");
        int birthplaceId = rs.getInt("birthplace_id");
        int nationalityId = rs.getInt("nationality_id");
        String nationalityStr = rs.getString("nationality");
        String birthplaceStr = rs.getString("birthplace");
        Country nationality = new Country(nationalityId, null, nationalityStr);
        Country birthplace = new Country(birthplaceId, birthplaceStr, null);
        return (new User(id, name, surname, email, phone, profileDesc, address, birthdate, nationality, birthplace));
    }

    private User getUserSimple(ResultSet rs) throws Exception {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String email = rs.getString("email");
        String phone = rs.getString("phone");
        String profileDesc = rs.getString("profile_description");
        String address = rs.getString("address");
        Date birthdate = rs.getDate("birthdate");

        return (new User(id, name, surname, email, phone, profileDesc, address, birthdate, null, null));
    }

    @Override
    public List<User> getAllUser(String name, String surname, Integer nationalityId) {
        List<User> result = new ArrayList();
        try {
            Connection c = connect();
            String sql = "SELECT " +
                    "\tu.*, " +
                    "\tn.nationality, " +
                    "\tc.NAME AS birthplace  " +
                    "FROM " +
                    "\tUSER u " +
                    "\tLEFT JOIN country n ON u.nationality_id = n.id " +
                    "\tLEFT JOIN country c ON u.birthplace_id = c.id where 1=1";
            if (name != null && !name.trim().isEmpty()) {
                sql += " and u.name=? ";
            }
            if (surname != null && !surname.trim().isEmpty()) {
                sql += " and u.surname=? ";
            }
            if (nationalityId != null) {
                sql += " and u.nationality_id=? ";
            }
            PreparedStatement stmt = c.prepareStatement(sql);
            int i = 1;
            if (name != null && !name.trim().isEmpty()) {
                stmt.setString(i, name);
                i++;
            }
            if (surname != null && !surname.trim().isEmpty()) {
                stmt.setString(i, surname);
                i++;
            }
            if (nationalityId != null) {
                stmt.setInt(i, nationalityId);
            }

            stmt.execute();
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
    public User findByEmailAndPassword(String email, String password) {
        User u = null;
        try (Connection c = connect();) {
            PreparedStatement stmt = c.prepareStatement("select * from user where email=? and password=?");
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                u = getUserSimple(rs);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return u;
    }

    @Override
    public boolean update(User u) {
        try {
            Connection c = connect();
            PreparedStatement stmt = c.prepareStatement("update user set name=?,surname=?,email=?,phone=?,profile_description=?,address=?,birthdate=?,birthplace_id=?,nationality_id=? where id=?");
            stmt.setString(1, u.getName());
            stmt.setString(2, u.getSurname());
            stmt.setString(3, u.getEmail());
            stmt.setString(4, u.getPhone());
            stmt.setString(5, u.getProfileDesc());
            stmt.setString(6, u.getAddress());
            stmt.setDate(7, u.getBirthDate());
            stmt.setInt(8, u.getBirthPlace().getId());
            stmt.setInt(9, u.getNationality().getId());
            stmt.setInt(10, u.getId());
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
            PreparedStatement stmt = c.prepareStatement("insert into user (name,surname,email,phone,profile_description)values(?,?,?,?,?)");
            stmt.setString(1, u.getName());
            stmt.setString(2, u.getSurname());
            stmt.setString(3, u.getEmail());
            stmt.setString(4, u.getPhone());
            stmt.setString(5, u.getProfileDesc());
            return stmt.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
