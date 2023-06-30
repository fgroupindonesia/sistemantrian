package sistem.antrian.config;

import sistem.antrian.fx.AudioPlayer.Language;

/**
 *
 * @author staff
 */
public class Antrian {

    private boolean firstTime;
    private Language language;
    private int clientNumberOrder;
    private String loket;
    private String alphabet;
    private int number;
    StringBuffer stb;

    
    
    public Antrian() {
        // this is the default for the queue
        firstTime = false;
    }

    public Antrian(boolean asFirstTime, Language usedLanguage) {
        // this is used for firsttime connection
        firstTime = asFirstTime;
        language = usedLanguage;
    }

    private String numberWithDigit(int numDigit, int val) {
        String stVal = null;

        if (numDigit == 2 && val > 9) {
            stVal = "" + val;
        } else if (numDigit == 2 && val < 10) {
            stVal = "0" + val;
        } else if (numDigit == 3 && val > 9 && val < 100) {
            stVal = "0" + val;
        } else if (numDigit == 3 && val < 10) {
            stVal = "00" + val;
        } else if (numDigit == 3 && val > 99) {
            stVal = "" + val;
        }

        return stVal;
    }

    public String asTicket() {
        stb = new StringBuffer();
        stb.append(alphabet);
        stb.append(".");
        stb.append(numberWithDigit(3, number));

        return stb.toString();
    }

    /**
     * @return the loket
     */
    public String getLoket() {
        return loket;
    }

    /**
     * @param loket the loket to set
     */
    public void setLoket(String loket) {
        this.loket = loket;
    }

    /**
     * @return the alphabet
     */
    public String getAlphabet() {
        return alphabet;
    }

    /**
     * @param alphabet the alphabet to set
     */
    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet;
    }

    /**
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(int number) {
        this.number = number;
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
     * @return the firstTime
     */
    public boolean isFirstTime() {
        return firstTime;
    }

    /**
     * @param firstTime the firstTime to set
     */
    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }

    /**
     * @return the language
     */
    public Language getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(Language language) {
        this.language = language;
    }
}
