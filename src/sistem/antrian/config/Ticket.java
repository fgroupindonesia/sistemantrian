package sistem.antrian.config;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author staff
 */
public class Ticket extends Antrian {

    private String companyLogo;
    private String companyName;
    private Date tanggal;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

    public Ticket(String companyN, String companyL) {
        companyLogo = companyL;
        companyN = companyN;
        
        tanggal = new Date();
    }

    public String getTanggalAsText() {
        return sdf.format(tanggal);
    }

    public Ticket() {
        tanggal = new Date();

    }

    /**
     * @return the tanggal
     */
    public Date getTanggal() {
        return tanggal;
    }

    /**
     * @param tanggal the tanggal to set
     */
    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    /**
     * @return the companyLogo
     */
    public String getCompanyLogo() {
        return companyLogo;
    }

    /**
     * @param companyLogo the companyLogo to set
     */
    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public File getCompanyLogoAsFile() {
        return new File(this.companyLogo);
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

}
