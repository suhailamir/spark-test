/**
 * Created by suhail on 5/22/15.
 */
import java.util.ArrayList;

public interface ContactDbService<T> {
    public Boolean create(T entity);
    public T readOne(int id);
    public ArrayList<T> readAll();
    public Boolean update(int id, String name, String email);
    public Boolean delete(int id);
}