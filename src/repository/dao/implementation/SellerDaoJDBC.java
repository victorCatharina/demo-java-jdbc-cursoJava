package repository.dao.implementation;

import repository.dao.SellerDao;
import repository.entities.Seller;

import java.util.List;
import java.util.Optional;

public class SellerDaoJDBC implements SellerDao {

    @Override
    public void insert(Seller seller) {

    }

    @Override
    public void update(Seller seller) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Optional<Seller> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<Seller> findAll() {
        return null;
    }
}
