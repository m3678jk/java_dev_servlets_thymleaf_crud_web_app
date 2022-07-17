package model.serviceDAO.DAO;

import java.util.List;

interface DAO<V> {
    void delete(long id);

    List<V> getList();

    V getById(long id);


}
