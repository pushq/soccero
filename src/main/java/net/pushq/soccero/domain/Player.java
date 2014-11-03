package net.pushq.soccero.domain;

/**
 * Created by Michal on 2014-10-10.
 */
public class Player {

    private Integer id;

    private String name;

    public Player() {

    }

    public Player(String name) {
        this.name = name;
    }

    public Player(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
