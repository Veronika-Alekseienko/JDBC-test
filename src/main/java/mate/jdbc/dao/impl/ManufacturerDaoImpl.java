package mate.jdbc.dao.impl;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertSelectStatement = "INSERT INTO manufacturer (name, country) VALUES (?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertSelectStatement, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                manufacturer.setId(resultSet.getObject(1, Long.class));
            }
        } catch (SQLException e){
            throw new DataProcessingException("Can't add manufacturer to DB " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getSelectStatement = "SELECT * FROM manufacturer WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(getSelectStatement)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                parseResultSet(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer from DB with id " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
       String getAllStatement = "SELECT * FROM manufacturer";
       try (Connection connection = ConnectionUtil.getConnection();
       PreparedStatement statement = connection.prepareStatement(getAllStatement)) {
           ResultSet resultSet = statement.executeQuery();
           List<Manufacturer> manufacturers = new ArrayList<>();
           while(resultSet.next()) {
               manufacturers.add(parseResultSet(resultSet));
           }
           return manufacturers;
       } catch(SQLException e) {
           throw new DataProcessingException("Can't get all manufacturers from DB!", e);
       }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
       String updateStatement = "UPDATE manufacturer SET name = ?, country = ? WHERE id = ? AND is_deleted = false";
       try (Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(updateStatement)) {
           statement.setString(1, manufacturer.getName());
           statement.setString(2, manufacturer.getCountry());
           statement.setLong(3, manufacturer.getId());
           statement.executeUpdate();
           return manufacturer;
       } catch (SQLException e) {
           throw new DataProcessingException("Can't update manufacturer from DB " + manufacturer, e);
       }
    }

    @Override
    public boolean delete(Long id) {
        String deleteStatement = "UPDATE manufacturer SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteStatement) ) {
             statement.setLong(1, id);
             return statement.execute();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer from DB with id " + id , e);
        }
    }

    private Manufacturer parseResultSet (ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getObject(1, Long.class));
        manufacturer.setName(resultSet.getString(2));
        manufacturer.setCountry(resultSet.getString(3));
        return manufacturer;
    }
}
