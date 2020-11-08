package com.gx.wda.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.gx.wda.MyApplication;

import org.apache.commons.net.tftp.TFTP;
import org.apache.commons.net.tftp.TFTPClient;
import org.apache.commons.net.tftp.TFTPPacket;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class TFTPExample {
    //TFTP服务器的端口是69

        static final String USAGE =
                "Usage: tftp [options] hostname localfile remotefile\n\n" +
                        "hostname   - The name of the remote host [:port]\n" +
                        "localfile  - The name of the local file to send or the name to use for\n" +
                        "\tthe received file\n" +
                        "remotefile - The name of the remote file to receive or the name for\n" +
                        "\tthe remote server to use to name the local file being sent.\n\n" +
                        "options: (The default is to assume -r -b)\n" +
                        "\t-t timeout in seconds (default 60s)\n" +
                        "\t-s Send a local file\n" +
                        "\t-r Receive a remote file\n" +
                        "\t-a Use ASCII transfer mode\n" +
                        "\t-b Use binary transfer mode\n" +
                        "\t-v Verbose (trace packets)\n"
                ;

        public static boolean TftpUtils(String vciIP, String fileName, String remoteFilename) throws IOException {
            Log.e("FileName",remoteFilename);
            boolean receiveFile = true, closed;
            int transferMode = TFTP.BINARY_MODE, argc;
            String arg, hostname, localFilename;
            final TFTPClient tftp;
            int timeout = 60000;
            boolean verbose = false;
            hostname = vciIP;
            //localFilename = "/data/data/com.gx.linkvcidemo/filelist.txt";
            localFilename = Environment.getExternalStorageDirectory().getAbsolutePath()+"/com.gx.wda/export_msg_info/bin/"+fileName;
            File locadir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/com.gx.wda/");
            File exportDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/com.gx.wda/export_msg_info/");
            File binDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/com.gx.wda/export_msg_info/bin/");
            File ascDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/com.gx.wda/export_msg_info/asc/");

//            localFilename = "/data/data/com.gx.wda/export_msg_info/"+fileName;
//            File locadir = new File("/data/data/com.gx.linkvcidemo/");
//            File exportDir = new File("/data/data/com.gx.linkvcidemo/export/");


            File file = new File(binDir,fileName);
            Log.i("localFilename", localFilename);

            //判断是否在本地/[下载/直接打开]


            if(!locadir.exists()){
                locadir.mkdirs();
            }
            if(!exportDir.exists()){
                exportDir.mkdirs();
            }
            if(!binDir.exists()){
                binDir.mkdirs();
            }
            if(!ascDir.exists()){
                ascDir.mkdirs();
            }
            if(!file.exists()){
                file.createNewFile();
            }


            // Create our TFTP instance to handle the file transfer.
            if (verbose) {
                tftp = new TFTPClient() {
                    @Override
                    protected void trace(String direction, TFTPPacket packet) {
                        System.out.println(direction + " " + packet);
                    }
                };
            } else {
                tftp = new TFTPClient();
            }

            // We want to timeout if a response takes longer than 60 seconds
            tftp.setDefaultTimeout(timeout);

            // We haven't closed the local file yet.
            closed = false;

            // If we're receiving a file, receive, otherwise send.
            if (receiveFile)
            {
                closed = receive(transferMode, hostname, localFilename, remoteFilename, tftp);
            } else {
                // We're sending a file
                // closed = send(transferMode, hostname, localFilename, remoteFilename, tftp);
                return false;
            }

            System.out.println("Recd: "+tftp.getTotalBytesReceived()+" Sent: "+tftp.getTotalBytesSent());

            if (!closed) {
                System.out.println("Failed");
                return closed;
            }

            return true;
        }

        private static boolean send(int transferMode, String hostname, String localFilename, String remoteFilename,
                                    TFTPClient tftp) {
            boolean closed;
            FileInputStream input = null;

            // Try to open local file for reading
            try
            {
                input = new FileInputStream(localFilename);
            }
            catch (IOException e)
            {
                tftp.close();
                System.err.println("Error: could not open local file for reading.");
                System.err.println(e.getMessage());
                System.exit(1);
            }

            open(tftp);

            // Try to send local file via TFTP
            try
            {
                String[] parts = hostname.split(":");
                if (parts.length == 2) {
                    tftp.sendFile(remoteFilename, transferMode, input, parts[0], Integer.parseInt(parts[1]));
                } else {
                    tftp.sendFile(remoteFilename, transferMode, input, hostname);
                }
            }
            catch (UnknownHostException e)
            {
                System.err.println("Error: could not resolve hostname.");
                System.err.println(e.getMessage());
                System.exit(1);
            }
            catch (IOException e)
            {
                System.err.println("Error: I/O exception occurred while sending file.");
                System.err.println(e.getMessage());
                System.exit(1);
            }
            finally
            {
                // Close local socket and input file
                closed = close(tftp, input);
            }

            return closed;
        }

        private static boolean receive(int transferMode, String hostname, String localFilename, String remoteFilename,
                                       TFTPClient tftp) {
            boolean closed;
            FileOutputStream output = null;
            File file;

            file = new File(localFilename);

            // If file exists, don't overwrite it.
            if (file.exists())
            {
               /* System.err.println("Error: " + localFilename + " already exists.");
                System.exit(1);*/
               /* System.err.println("Error: 文件存在，删除.");
               file.delete();*/
            }

            // Try to open local file for writing
            try
            {
                output = new FileOutputStream(file);
            }
            catch (IOException e)
            {
                tftp.close();
                System.err.println("Error: could not open local file for writing.");
                System.err.println(e.getMessage());
                return false;
            }

            open(tftp);

            // Try to receive remote file via TFTP
            try
            {
                String[] parts = hostname.split(":");
                if (parts.length == 2) {
                    tftp.receiveFile(remoteFilename, transferMode, output, parts[0], Integer.parseInt(parts[1]));
                } else {
                    tftp.receiveFile(remoteFilename, transferMode, output, hostname);
                }
            }
            catch (UnknownHostException e)
            {
                System.err.println("Error: could not resolve hostname.");
                System.err.println(e.getMessage());
                return false;
            }
            catch (IOException e)
            {
                System.err.println(
                        "Error: I/O exception occurred while receiving file.");
                System.err.println(e.getMessage());
                return false;
            }
            finally
            {
                // Close local socket and output file
                closed = close(tftp, output);
            }

            return closed;
        }

        private static boolean close(TFTPClient tftp, Closeable output) {
            boolean closed;
            tftp.close();
            try
            {
                if (output != null) {
                    output.close();
                }
                closed = true;
            }
            catch (IOException e)
            {
                closed = false;
                System.err.println("Error: error closing file.");
                System.err.println(e.getMessage());
            }
            return closed;
        }

        private static void open(TFTPClient tftp) {
            try
            {
                tftp.open();
            }
            catch (SocketException e)
            {
                System.err.println("Error: could not open local UDP socket.");
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }

}
