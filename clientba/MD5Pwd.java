 
package clientba;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Encripta a password utilizando o método MD5.
 * @author lpro1612
 */

public class MD5Pwd {

    private static MD5Pwd THIS = null;
    private Cipher cipher = null;
 /**
  * Inicializa uma nova encriptação de password.
  */
    private MD5Pwd() {
        try{
            cipher = Cipher.getInstance("DES");	
        } catch( NoSuchPaddingException e ) {
            System.err.println( "MD5Pwd constructor failed: " + e );
        }catch( NoSuchAlgorithmException e ){
            System.err.println( "MD5Pwd constructor failed: " + e );
        }
    }
    /**
     * Encripta uma password com base no username.
     * @param userid Username associado à password a encriptar.
     * @param password Password a encriptar.
     * @return Uma String com a password encriptada.
     */

    public String encode( String userid, String password ) {
        String ret = null;
        try{
            cipher.init( Cipher.ENCRYPT_MODE, makeKey(userid) );
            byte[] out = cipher.doFinal( password.getBytes() );
            ret = toHexString(out);
            
        } catch( InvalidKeyException e ) {
            System.err.println( "MD5Pwd encode failed: " + e );
        } catch( IllegalBlockSizeException e ) {
             System.err.println( "MD5Pwd encode failed: " + e );
        } catch( BadPaddingException e ){
            System.err.println( "MD5Pwd encode failed: " + e );
        } catch( NoSuchAlgorithmException e ){
            System.err.println( "MD5Pwd cannot make a key: " + e );
        }
        
        return ret;
    }
 
    
    protected SecretKey makeKey( String userid )  throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("md5");
        byte[] k = md5.digest(userid.getBytes());
        return new SecretKeySpec(k, 0, 8, "DES");
    }
 
    protected String toHexString( byte[] b )   {
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<b.length; i++){
            int v = b[i] < 0 ? (int)b[i] + 0x100 : (int)b[i];
            String hex = Integer.toHexString(v);
            if( hex.length()==1 )
                sb.append( '0' );
            sb.append( hex );
        }return sb.toString();
    }
 
   
    protected byte[] fromHexString( String hex ) {
        byte[] ret = new byte[ hex.length() / 2 ];
        for(int i=0, len=hex.length(); i<len; i+=2){
            int v = Integer.parseInt(hex.substring(i, i+2), 16);
            ret[i/2] = (byte)v;
        }
        return ret;
    }
 

    public static MD5Pwd getInstance() {
        if( THIS!=null )
            return THIS;
 
        try{
            Class.forName("javax.crypto.Cipher");
            THIS = new MD5Pwd();
        }
        catch( ClassNotFoundException e ){
            System.err.println( "MD5Pwd getInstance failed: " + e );
        }
        return THIS;
    }
    
}
