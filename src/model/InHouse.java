package model;

/**
 * A part that is manufactured in house and
 * contains a machineId.
 *
 * @author Tabish Abbasi
 */
public class InHouse extends Part {
    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId){
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     *
     * @return the machineId
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     *
     * @param machineId the machineId to set
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}

