package sistem.antrian.config;

/**
 *
 * @author staff
 */
public class DataCache {

    private String companyName;
    private int clientNumberOrder;

    public DataCache(String cname, int number){
        companyName = cname;
        clientNumberOrder = number;
    }
    
    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return the clientNumberOrder
     */
    public int getClientNumberOrder() {
        return clientNumberOrder;
    }

    /**
     * @param clientNumberOrder the clientNumberOrder to set
     */
    public void setClientNumberOrder(int clientNumberOrder) {
        this.clientNumberOrder = clientNumberOrder;
    }
    
    
}
