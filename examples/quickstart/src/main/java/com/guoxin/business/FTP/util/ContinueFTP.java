package com.guoxin.business.FTP.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ResourceBundle;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/** */
/**
 * 支持断点续传的FTP实用类
 * @author BenZhou http://www.bt285.cn
 * @version 0.1 实现基本断点上传下载
 * @version 0.2 实现上传下载进度汇报
 * @version 0.3 实现中文目录创建及中文文件创建，添加对于中文的支持
 */
public class ContinueFTP
{
    public FTPClient ftpClient = new FTPClient();


    public ContinueFTP()
    {
        // 设置将过程中使用到的命令输出到控制台
        this.ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(
                System.out)));
    }


    /** */
    /**
     * 连接到FTP服务器
     * @param hostname 主机名
     * @param port 端口
     * @param username 用户名
     * @param password 密码
     * @return 是否连接成功
     * @throws IOException
     */
    public boolean connect(String hostname, int port, String username, String password)
            throws IOException
    {
        ftpClient.connect(hostname, port);
        ftpClient.setControlEncoding("GBK");
        if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode()))
        {
            if (ftpClient.login(username, password))
            {
                return true;
            }
        }
        disconnect();
        return false;
    }


    /** */
    /**
     * 从FTP服务器上下载文件,支持断点续传，上传百分比汇报
     * @param remote 远程文件路径
     * @param local 本地文件路径
     * @return 下载的状态
     * @throws IOException
     */
    public InputStream download(String remote, String local) throws IOException
    {
        InputStream in = null;

        // 设置被动模式
        ftpClient.enterLocalPassiveMode();
        // 设置以二进制方式传输
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        // DownloadStatus result;

        // 检查远程文件是否存在
        FTPFile[] files = ftpClient.listFiles(new String(remote.getBytes("GBK"), "iso-8859-1"));
        if (files.length != 1)
        {
            return null;
        }

        long lRemoteSize = files[0].getSize();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try
        {

            in = ftpClient.retrieveFileStream(new String(remote.getBytes("GBK"), "iso-8859-1"));
            byte[] bytes = new byte[1024];
            long step = lRemoteSize / 100;
            if(step==0)
            {
                step = 1;
            }
            long process = 0;
            long localSize = 0L;
            int c;
            while ((c = in.read(bytes)) != -1)
            {
                out.write(bytes, 0, c);
                localSize += c;
                long nowProcess = localSize / step;
                if (nowProcess > process)
                {
                    process = nowProcess; // 记录下载进度
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            in.close();
            out.close();
        }
        return new ByteArrayInputStream(out.toByteArray());

    }



    /** */
    /**
     * 断开与远程服务器的连接
     * @throws IOException
     */
    public void disconnect() throws IOException
    {
        if (ftpClient.isConnected())
        {
            ftpClient.disconnect();
        }
    }



    
    public String getFtpUrl()
    {
        return ResourceBundle.getBundle("config").getString("ftpUrl");
    }


    public String getFtpUserName()
    {
        return ResourceBundle.getBundle("config").getString("ftpusername");
    }


    public String getFtpPassword()
    {
        return ResourceBundle.getBundle("config").getString("ftppassword");
    }


    public String getFtpPort()
    {
        return ResourceBundle.getBundle("config").getString("port");
    }

}
