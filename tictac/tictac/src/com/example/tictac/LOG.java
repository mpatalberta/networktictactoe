package com.example.tictac;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.Date;

import android.util.Log;

public class LOG
{
	
    public  static boolean VERBOSE = true;// global value
    public  static boolean VERBOSE_BLUETOOTH = true;
    public  static boolean DEBUG = true;
    public  static boolean DEBUG_BLUETOOTH = true;
    public  static boolean LOG2FILE = true;
    static  long basetime = -1;
    Date d;
    public  static void set(boolean vverbose
    		       ,boolean vverbose_bluetooth
    		       ,boolean vdebug
    		       ,boolean vdebug_bluetooth
    		       ,boolean vlog2file)
    {
    	Calendar currentDate = Calendar.getInstance();
    	VERBOSE = vverbose;
    	VERBOSE_BLUETOOTH = vverbose_bluetooth;
    	DEBUG = vdebug;
    	DEBUG_BLUETOOTH = vdebug_bluetooth;
    	LOG2FILE = vlog2file;
    	if(basetime == -1)
    	{
    		basetime = currentDate.getTimeInMillis();
    	}
    	
    }
    public final static String getDeltaT()
    {
    	Calendar currentDate = Calendar.getInstance();
    	long myt;
    	myt = currentDate.getTimeInMillis() - basetime;
    	return myt+"";
    }
    @SuppressWarnings("unused")
	public final static void v(String tag,String message)
    {

        if (VERBOSE)
        {
        	
            String fullClassName = Thread.currentThread().getStackTrace()[3].getClassName();            
            if(fullClassName.contains("com.bluetoothexample"))
            {
             if(VERBOSE_BLUETOOTH !=true) 
             {
            	 return;
             }
             
            }
        	String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
            String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
            int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();
        
            Log.d(getDeltaT()+":"+className + "." + methodName + "():" + lineNumber,":"+tag+":"+message);
            
            if(LOG2FILE == true)
            {
            	if(bRes==false)
            	{
            		bRes = openFile(getDeltaT()+":"+className + "." + methodName + "():" + lineNumber + ":"+tag+":"+message+"\r\n");

            	}
            	else
            	{
            		writeToFile(getDeltaT()+":"+className + "." + methodName + "():" + lineNumber + ":"+tag+":"+message+"\r\n",filename,bRes);
            	}           	
            }
        }
    }
    
    @SuppressWarnings("unused")
	public final static void d(String tag,String message)
    {
        if (DEBUG)
        {
            String fullClassName = Thread.currentThread().getStackTrace()[3].getClassName();  
            
            if(fullClassName.contains("com.bluetoothexample"))
            {
             if(DEBUG_BLUETOOTH !=true) 
             {
            	 return;
             }
             
            }
            String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
            String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
            int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();
            String s;
           
            Log.d(getDeltaT()+":"+className + "." + methodName + "():" + lineNumber,":"+tag+":"+message);
            if(LOG2FILE==true)
            {
            	if(bRes==false)
            	{
            		bRes = openFile(getDeltaT()+":"+className + "." + methodName + "():" + lineNumber + ":"+tag+":"+message+"\r\n");
            	}
            	else
            	{
            		writeToFile(getDeltaT()+":"+className + "." + methodName + "():" + lineNumber + ":"+tag+":"+message+"\r\n",filename,bRes);
            	}


            }
        }
    }
    
    static public boolean openFile(String sVal) {
        String timestamp = ""+Calendar.getInstance().getTimeInMillis();

        filename = timestamp + "log.txt";


            writeToFile(sVal, filename,false);
        return true;

    }
    static boolean bRes = false;
    static BufferedWriter bos = null;
    static String localPath = "/sdcard/newarene1000";
    static String filename= null;
    public String setlocalpath(String sLocalPath)
    {
    	localPath = sLocalPath;
    	return localPath;
    }
    
    public void close()
    {
    	if(bos!=null)
    	{
    		try {
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();// watch out for recursion no Log. or LOG allowed
			}
    	}
    }
    
    static private void writeToFile(String stacktrace, String filename,boolean bOpen) {
        try {
        	if(bOpen == false)
        	{
        		File emsDirectory = new File(localPath);
        		emsDirectory.mkdir();
        	
                 bos = new BufferedWriter(new FileWriter(
                    localPath + "/" + filename));
        	}
            bos.write(stacktrace);
            bos.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
