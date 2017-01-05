
package Logic;
 
import java.util.*;
/**
 * Avalia a validade de um email.
 * @author lpro1612
 */

public class ValidateEmail {
    /**
     * Valida um email.
     * @param email Email que se pretende validar.
     * @return True caso o email seja válido
     *         False caso o email não seja válido
     */
    public static boolean isValidEmailAddress(String email) {
        String regex = "^[\\w-]+(\\.[\\w-]+)*@" + "[A-Za-z0-9]+(\\.[\\w-]+)*(.[A-Za-z]{2,6})$";
        return email.matches(regex);
    }
 
}