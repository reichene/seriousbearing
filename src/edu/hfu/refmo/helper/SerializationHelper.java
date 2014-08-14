package edu.hfu.refmo.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class SerializationHelper {
	
	
public static byte[] serialize(Object paraObject){	
	ByteArrayOutputStream bos = new ByteArrayOutputStream();
	ObjectOutput out = null;
	byte[] yourBytes= null;
	try {
	  out = new ObjectOutputStream(bos);   
	  out.writeObject(paraObject);
	  yourBytes = bos.toByteArray();
	  
	  
	} catch (IOException e) {
	
	}
	
	
	finally {
	  try {
	    if (out != null) {
	      out.close();
	    }
	  } catch (IOException ex) {
	    // ignore close exception
	  }
	  try {
	    bos.close();
	  } catch (IOException ex) {
	    // ignore close exception
	  }
	}
	 return  yourBytes;	
}


public static Object  deserialize(byte[] byteObject){	
	ByteArrayInputStream bis = new ByteArrayInputStream(byteObject);
	Object o = null;
	ObjectInput in = null;
	try {
	  in = new ObjectInputStream(bis);
	   o = in.readObject(); 
	
	} catch (IOException e) {
		
		e.printStackTrace();
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	} finally {
	  try {
	    bis.close();
	  } catch (IOException ex) {
	    // ignore close exception
	  }
	  try {
	    if (in != null) {
	      in.close();
	    }
	  } catch (IOException ex) {
	    // ignore close exception
	  }
	}
	 return  o;	
}

}
