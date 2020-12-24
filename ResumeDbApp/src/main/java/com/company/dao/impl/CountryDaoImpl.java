/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.dao.impl;

import com.company.dao.inter.AbstractDao;
import com.company.dao.inter.CountryDaoInter;
import com.company.entity.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author UlviAshraf
 */
public class CountryDaoImpl extends AbstractDao implements CountryDaoInter {

    public Country getCountry(ResultSet rs) throws Exception {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String nationality = rs.getString("nationality");
        Country country = new Country(id, name, nationality);
        return country;
    }

    @Override
    public List<Country> getAll() {
        List<Country> result = new ArrayList();
        try (Connection c = connect()) {
            Statement stmt = c.createStatement();
            stmt.execute("select * from country");
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                Country country = getCountry(rs);
                result.add(country);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(Country country) {
        try (Connection con = connect()) {
            PreparedStatement stmt = con.prepareStatement("update country set name=?,nationality=? where id=?");
            stmt.setString(1, country.getName());
            stmt.setString(2, country.getNationality());
            stmt.setInt(3, country.getId());
            return stmt.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remove(int id) {
        try (Connection con = connect()) {
            PreparedStatement stmt = con.prepareStatement("delete from country where id=?");
            stmt.setInt(0, id);
            return stmt.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean add(Country country) {
        try (Connection con = connect()) {
            PreparedStatement stmt = con.prepareStatement("insert into country (name,nationality)values(?,?)");
            stmt.setString(1, country.getName());
            stmt.setString(2, country.getNationality());
            return stmt.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
