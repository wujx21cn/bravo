package com.cutty.bravo.core.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/** *//**
 * zip UTILS
 * 
 * @author jason wu
 * @date --
 */
public class ZipUtil {
	
    /**
     * 递归压缩文件
     * @param fileList 文件列表
     * @param destinct  目标路径,压缩文件名
     * @throws IOException
     */
	public static void compress(List<String> fileList,String destinct) throws IOException {
        ZipOutputStream zos=new ZipOutputStream(new FileOutputStream(new File(destinct)));
        
        byte[] buffere=new byte[8192];
        int length;
        BufferedInputStream bis;
        
        for(int i=0;i<fileList.size();i++) {
            File file=new File(fileList.get(i));
            zos.putNextEntry(new ZipEntry(getEntryName(file.getAbsolutePath(),file)));
            bis=new BufferedInputStream(new FileInputStream(file));
            
            while(true) {
                length=bis.read(buffere);
                if(length==-1) break;
                zos.write(buffere,0,length);
            }
            bis.close();
            zos.closeEntry();
        }
        zos.close();
    }
    
    /**
     * 递归压缩文件
     * @param source 源路径,可以是文件,也可以目录
     * @param destinct  目标路径,压缩文件名
     * @throws IOException
     */
    public static void compress(String source,String destinct) throws IOException {
        List fileList=loadFilename(new File(source));
        ZipOutputStream zos=new ZipOutputStream(new FileOutputStream(new File(destinct)));
        
        byte[] buffere=new byte[8192];
        int length;
        BufferedInputStream bis;
        
        for(int i=0;i<fileList.size();i++) {
            File file=(File) fileList.get(i);
            zos.putNextEntry(new ZipEntry(getEntryName(source,file)));
            bis=new BufferedInputStream(new FileInputStream(file));
            
            while(true) {
                length=bis.read(buffere);
                if(length==-1) break;
                zos.write(buffere,0,length);
            }
            bis.close();
            zos.closeEntry();
        }
        zos.close();
    }
    /**
     * 递归获得该文件下所有文件名(不包括目录名)
     * @param file
     * @return
     */
    private static List loadFilename(File file) {
        List filenameList=new ArrayList();
        if(file.isFile()) {
            filenameList.add(file);
        }
        if(file.isDirectory()) {
            for(File f:file.listFiles()) {
                filenameList.addAll(loadFilename(f));
            }
        }
        return filenameList;
    }
    /**
     * 获得zip entry 字符串
     * @param base
     * @param file
     * @return
     */
    private static String getEntryName(String base,File file) {
        File baseFile=new File(base);
        String filename=file.getPath();
        //int index=filename.lastIndexOf(baseFile.getName());
        if(baseFile.getParentFile().getParentFile()==null)
            return filename.substring(baseFile.getParent().length());
        return filename.substring(baseFile.getParent().length()+1);
    }

    /**
     * 解压ZIP文件
     * @param zipFile  要解压的ZIP文件路径
     * @param destination  解压到哪里
     * @throws IOException
     */
    public static void decompression(String zipFile,String destination) throws IOException {
        ZipFile zip=new ZipFile(zipFile);
        Enumeration en=zip.entries();
        ZipEntry entry=null;
        byte[] buffer=new byte[8192];
        int length=-1;
        InputStream input=null;
        BufferedOutputStream bos=null;
        File file=null;
        
        while(en.hasMoreElements()) {
            entry=(ZipEntry)en.nextElement();
            if(entry.isDirectory()) {
                System.out.println("directory");
                continue;
            }
            
            input=zip.getInputStream(entry);
            file=new File(destination,entry.getName());
            if(!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            bos=new BufferedOutputStream(new FileOutputStream(file));
            
            while(true) {
                length=input.read(buffer);
                if(length==-1) break;
                bos.write(buffer,0,length);
            }
            bos.close();
            input.close();
        }
        zip.close();
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) throws IOException {
    	List<String> abs = new ArrayList<String>();
    	abs.add("C:/jason/project/cutty/ast/webappuploadTemp/Change catg_svc type_110110.xls");
    	abs.add("C:/jason/project/cutty/ast/webappuploadTemp/Change company_110110.xls");
    	abs.add("C:/jason/project/cutty/ast/webappuploadTemp/Newjoin_110110.xls");
    	compress(abs,"C:/work/temp/aaaccc.zip");
    }
}

