/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm;

/**
 *
 * @author jean pierre
 */
public class FormatChecker {

    private final String nameWithSpace = "[A-Za-z]{2,15}\\p{Space}+[A-Za-z]{2,15}+";
    private final String numbers = "[0-9]+";
   // private final String telephoneFormat = "(\\d{3}-)?(\\d{3}-)?(\\d{4})";
    private final String telephoneFormat = "([078]|[072]|[252])+\\-?(\\d{3}-)?(\\d{4})";
    private final String singleName = "[A-Za-z]{2,30}";
    private final String idFormat = "\\d{16}";
    private final String emailPattern = ".+@.+\\.([com]|[fr]|[org]|[rw])+";

    public boolean isTelephone(String telephone) {
        return telephone.matches(telephoneFormat);
    }

    public boolean areDigits(String digits) {
        return digits.matches(numbers);
    }

    public boolean isSingleName(String sName) {
        return sName.matches(singleName);
    }

    public boolean isNameWithSpace(String nWSpace) {
        return nWSpace.matches(nameWithSpace);
    }

    public boolean isID(String id) {
        return id.matches(idFormat);
    }
    public boolean isEmail(String email){
    return email.matches(emailPattern);
    }


    public FormatChecker() {
    }
}
