// Represents a user.

import java.util.ArrayList;
import java.util.List;

class User {
    private final String name;
    private final List<Headache> headaches;

    public User(String name) {
        this.name = name;
        this.headaches = new ArrayList<>();
    }

    public void addHeadache(Headache headache) {
        headaches.add(headache);
    }
    
    public List<Headache> getHeadaches() {
        return headaches;
    }
    
    public String getName() {
        return name;
    }
}
