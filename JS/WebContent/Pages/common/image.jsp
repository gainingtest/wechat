<%@ page language="java" contentType="text/html;charset=gbk" %>
<%@ page import = "java.awt.*" %>
<%@ page import = "java.awt.image.BufferedImage" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.sun.image.codec.jpeg.*" %>
<%@ page import = "com.js.support.util.RandomValidateCode" %>
<%
response.setHeader("Cache-Control","No-Cache");//HTTP 1.1
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>
<%!
private Font mFont     = new Font("Arial Black", Font.PLAIN, 12);
private int  lineWidth = 5; 
private int  width     = 80; 
private int  height    = 27; 
private int  count     = 25;

private Color getRandColor(int fc, int bc){
	Random random = new Random();
	if (fc > 255)fc = 255;
    if (bc > 255)bc = 255;
    int r = fc + random.nextInt (bc - fc);
    int g = fc + random.nextInt (bc - fc);
    int b = fc + random.nextInt (bc - fc);
    return new Color(random.nextInt(100), g, 255);
} 
%>
<%
		 out.clear();
		 response.reset();
		 response.setHeader("Pragma","No-cache");
		 response.setHeader("Cache-Control","no-cache");
		 response.setDateHeader("Expires", 0);
		 response.setContentType("image/jpeg"); 
         BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
         Graphics2D g = (Graphics2D) image.getGraphics ();
         Random random = new Random();
         //g.setColor(getRandColor(200,250)); 
         g.setColor(new Color(255,255,255)); 
  		 g.fillRect(0,0,width,height);
         g.setFont (mFont);
		 g.setColor (getRandColor (0, 20)); 
         g.drawRect (0, 0, width - 1, height - 1);
         for (int i = 0; i < count; i++){
         	g.setColor (getRandColor (150, 200));
            int x  = random.nextInt (width - lineWidth - 1) + 1;
            int y  = random.nextInt (height - lineWidth - 1) + 1;
            int xl = random.nextInt (lineWidth);
            int yl = random.nextInt (lineWidth);
            g.drawLine (x, y, x + xl, y + yl);
         }

         String sRand = "";
         for (int i = 0; i < 4; i++){
         	String rand = "";
         	int x = random.nextInt (10);	
         	rand = rand + x;
         	sRand += rand;
         	g.setFont (new Font("Arial", Font.PLAIN, 23));
            g.setColor (new Color(20 + random.nextInt (130),20 + random.nextInt (130), 255)); 
            g.drawString (rand, (13 * i) + 14, 21);   
         }
         session.setAttribute (RandomValidateCode.RANDOMCODEKEY, sRand);
         g.dispose ();
         ServletOutputStream outStream = response.getOutputStream();
         JPEGImageEncoder encoder =JPEGCodec.createJPEGEncoder(outStream);
         encoder.encode(image);
         outStream.close();
%>