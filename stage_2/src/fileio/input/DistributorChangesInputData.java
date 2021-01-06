package fileio.input;

/**
 * contains cost changes information from input
 */
public final class DistributorChangesInputData {
    private int id;
    private int infrastructureCost;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getInfrastructureCost() {
        return infrastructureCost;
    }

    public void setInfrastructureCost(final int infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }
}
