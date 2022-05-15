package pl.coderslab.reserveapooltable.entity;

import javax.persistence.*;

@Entity
public class TableToReserve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int tableNumber;

    public TableToReserve() {
    }

    public TableToReserve(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }
}
