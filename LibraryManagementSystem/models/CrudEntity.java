package models;
public interface CrudEntity<T> {
    /**
     * Create/persist the entity. Returns the created entity (or this instance).
     */
    T create();

    /**
     * Read/retrieve the entity. Returns the entity (or this instance).
     */
    T read();

    /**
     * Update this entity with values from another instance.
     */
    void update(T other);

    /**
     * Delete or mark the entity as deleted.
     */
    void delete();
}
