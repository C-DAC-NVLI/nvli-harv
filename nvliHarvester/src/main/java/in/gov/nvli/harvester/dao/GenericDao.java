package in.gov.nvli.harvester.dao;

import java.io.Serializable;
import java.util.List;

/**
 * A generic DAO interface definition. This interface should be extended even if
 * the new interface will add no additional functions.
 *
 * @author vootla
 * @version 1
 * @since 1
 * @param <T> The class of the pojo being persisted.
 * @param <ID> the class of the pojo's id property.
 */
public interface GenericDao<T, ID extends Serializable> {

    /**
     * Get the object with the id specified.
     *
     * @param id the id of the instance to retrieve.
     * @return the instance with the given id.
     */
    public T get(ID id);

   

    /**
     * Persist the given new bean.
     *
     * @param entity the bean to persist.
     * @return
     */
    public boolean createNew(T entity);

    /**
     * Get all instances of this bean that have been persisted.
     *
     * @return a list of all instances.
     */
    public List<T> list();

    /**
     * Persist updated bean
     *
     * @param entity the bean to persist
     */
    public void saveOrUpdate(T entity);

    /**
     * Delete the given bean
     *
     * @param entity
     */
    public void delete(T entity);

    /**
     * Checks for existence of an object of type T using the id argument.
     *
     * @param id the id of the instance to retrieve.
     * @return true if it exists, false if it doesn't
     */
    public boolean exist(ID id);

    /**
     * Update the given entity
     *
     * @param entity
     * @return
     */
    public T merge(T entity);

   

    /**
     *
     * @param queryName
     * @param b
     */
    public void update(String queryName, Object... b);

    
    /**
     * Flush all pending saves, updates and deletes to the database Only invoke
     * this for selective eager flushing, for example when JDBC code needs to
     * see certain changes within the same transaction Else, it is preferable to
     * rely on auto-flushing at transaction completion.
     */
    public void flush();
}

