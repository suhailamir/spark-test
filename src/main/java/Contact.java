/**
 * Created by suhail on 5/22/15.
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Contact {
    private int id;
    private String name;
    private String email;
    private Date createdAt;
    private Boolean deleted;
    public Contact(String name, String email, Integer size) {
        this.id = size;
        this.name = name;
        this.email = email;
        this.createdAt = new Date();
        this.deleted = false;
    }

    public Contact(Integer id, String name, String email, Date createdAt, Boolean deleted) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.id = id;
        this.deleted = deleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}