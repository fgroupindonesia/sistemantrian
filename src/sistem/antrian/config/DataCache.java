package sistem.antrian.config;

/**
 *
 * @author staff
 */
public class DataCache {

    private String companyName;
    private int clientNumberOrder;
    private String usedAlphabet[];

    public DataCache(String cname, int number, String[] alphabets){
        companyName = cname;
        clientNumberOrder = number;
        usedAlphabet = alphabets;
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

    /**
     * @return the usedAlphabet
     */
    public String[] getUsedAlphabet() {
        return usedAlphabet;
    }

    /**
     * @param usedAlphabet the usedAlphabet to set
     */
    public void setUsedAlphabet(String[] usedAlphabet) {
        this.usedAlphabet = usedAlphabet;
    }
    
    
}
