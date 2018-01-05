package com.js.support.util;
import java.security.Key;  
import java.security.SecureRandom;  
import sun.misc.BASE64Decoder;  
import sun.misc.BASE64Encoder; 
import javax.crypto.Cipher;  
import javax.crypto.KeyGenerator;  
import javax.crypto.SecretKey; 
public class AesUtil {
	    public static String DES = "AES";
	      
	    public static String CIPHER_ALGORITHM = "AES";  
	    
	    public static String KEY="union";
	      
	    public static Key getSecretKey(String key) throws Exception{  
	        SecretKey securekey = null;  
	        if(key == null){  
	            key = "";  
	        }  
	        //1.构造密钥生成器，指定为AES算法,不区分大小写
	        KeyGenerator keyGenerator = KeyGenerator.getInstance(DES);  
	        //生成一个128位的随机源,根据传入的字节数组
	        keyGenerator.init(new SecureRandom(key.getBytes()));  
	        //3.产生原始对称密钥
	        securekey = keyGenerator.generateKey();  
	        //返回对称秘钥
	        return securekey;  
	    }  
	    public static String encrypt(String password) { 
			try {
				//获取对称秘钥
				Key	securekey = getSecretKey(KEY);
//		    	//4.获得原始对称密钥的字节数组
//	            byte [] raw=securekey.getEncoded();
//	          //5.根据字节数组生成AES密钥
//	            SecretKey key=new SecretKeySpec(raw, "AES");
	            //6.根据指定算法AES自成密码器
				Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);  
				//7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
		        cipher.init(Cipher.ENCRYPT_MODE, securekey);

		        //8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
	            byte [] byte_encode=password.getBytes("utf-8");
	          //9.根据密码器的初始化方式--加密：将数据加密
		        byte[] bt = cipher.doFinal(byte_encode);  
		      //10.将加密后的数据转换为字符串
		        String strs = new BASE64Encoder().encode(bt);
		        return strs;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	        return null;  
	    }  
	      
	      
	   public static String detrypt(String password) throws Exception{  ;  
	        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);  
	        Key securekey = getSecretKey(KEY);  
	        cipher.init(Cipher.DECRYPT_MODE, securekey);  
	        byte[] res = new BASE64Decoder().decodeBuffer(password);  
	        res = cipher.doFinal(res);  
	        return new String(res);  
	    }  
	      
	    public static void main(String[] args)throws Exception{  
	        String message = "238238";  
	        String entryptedMsg = encrypt(message); 
	        System.out.println(entryptedMsg);
//	        System.out.println("encrypted message is below :");  
//	        System.out.println(entryptedMsg);  
	          
	        String decryptedMsg = detrypt(entryptedMsg);  
	        System.out.println("decrypted message is below :");  
            System.out.println(decryptedMsg);  
	    }  
 
	
	

}
