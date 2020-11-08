package com.gx.wda.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gx.wda.MyApplication;
import com.gx.wda.R;
import com.gx.wda.bean.MessageVo;
import com.gx.wda.bean.VciFileVo;
import com.gx.wda.dialog.MessageDialog;
import com.gx.wda.util.EncodingUtil;
import com.gx.wda.util.TFTPExample;
import com.gx.wda.util.Tools;
import com.gx.wda.widget.MyActionBar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import me.leefeng.promptlibrary.PromptDialog;

/**
 * 导出VCI_15
 */
public class ExportMessageAdapter extends RecyclerView.Adapter<ExportMessageAdapter.ViewHolder>{
    private Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private Activity myActivity;
    private MyApplication myApplication;//全局Application对象
    private PromptDialog promptDialog;//加载框
    private List<VciFileVo> mListData;
    private BigDecimal time = null;
    public static List<VciFileVo> exportList;//导出列表

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public boolean isCheck() {
        return isCheck;
    }

    boolean isCheck=false;
    private MyActionBar myActionBar;

    public ExportMessageAdapter(List<VciFileVo> mListData, Activity activity, MyApplication myApplication,MyActionBar myActionBar){
        this.myActivity = activity;
        this.mListData=mListData;
        this.myApplication = myApplication;
        this.exportList=new ArrayList<>();
        this.myActionBar=myActionBar;
    }

    public void isCheckAll(boolean isCheck ){
        this.exportList.clear();
        for (VciFileVo file:mListData) {
            file.setCheck(isCheck);
            if (isCheck){//全选
                this.exportList.add(file);
            }
        }
        if (!isCheck){//全不选
            this.exportList.clear();
        }
        myActionBar.setTitle("已选择"+exportList.size()+"条");
        Log.i("导出数据条数",String.valueOf(exportList.size()));
        Log.i("数据总数",String.valueOf(mListData.size()));
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(myActivity).inflate(R.layout.item_rv_export_message_list,parent,false);
        promptDialog = new PromptDialog(myActivity);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            final VciFileVo fileVo=mListData.get(position);
            if (fileVo!=null){
                String FileName = fileVo.getFileName();
                if(!Tools.isNotNull(FileName)){
                    FileName = "";
                }else{
                    FileName = FileName.replace("/record/","").replace(".bin","");
                }
                holder.tvFileName.setText(FileName);//设置文件名
                int FileCount = fileVo.getFileNum();

                holder.tvFileCount.setText(String.valueOf(FileCount+" "+myActivity.getString(R.string.text_tf_card_setting_total_unit)));//设置报文数
                int FileSize = fileVo.getFileSize();

                holder.tvFileSize.setText(String.valueOf(FileSize+" "+myActivity.getString(R.string.text_tf_card_setting_bit)));//设置文件大小

                holder.cbCheck.setChecked(false);
                Log.i("isCheck",String.valueOf(fileVo.isCheck()));
                holder.cbCheck.setChecked(fileVo.isCheck());
              /*  if (fileVo.isCheck()){
                    exportList.add(fileVo);
                }else {
                    exportList.remove(fileVo);
                }*/
                if(isCheck){
                    holder.cbCheck.setVisibility(View.VISIBLE);
                   // fileVo.setCheck(true);
                }else{
                    holder.cbCheck.setVisibility(View.GONE);
                    fileVo.setCheck(false);
                }

                //设置整条数据的点击事件
                holder.llVciFile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isCheck){
                            fileVo.setCheck(!holder.cbCheck.isChecked());
                           holder.cbCheck.setChecked(!holder.cbCheck.isChecked());
                            if (fileVo.isCheck()){
                                exportList.add(fileVo);
                            }else {
                                exportList.remove(fileVo);
                            }
                            Log.i("导出数据条数",String.valueOf(exportList.size()));
                            Log.i("数据总数",String.valueOf(mListData.size()));
                            myActionBar.setTitle("已选择"+exportList.size()+"条");
                            if (exportList.size()==mListData.size()){
                                myActionBar.setLeftText("全不选");
                                myActionBar.isCheck=true;
                            }else {
                                myActionBar.setLeftText("全选");
                                myActionBar.isCheck=false;
                            }
                        }else {
                            if(!Tools.isFastClick()){
                                MessageDialog messageDialog = new MessageDialog(myActivity, R.style.dialog, myActivity.getResources().getString(R.string.text_export_tf_card_info), new MessageDialog.OnCloseListener() {
                                    @Override
                                    public void onClick(Dialog dialog, boolean confirm) {
                                        if (confirm==true){//确定按钮
                                            promptDialog.showLoading("正在导出报文信息...");
                                            if(fileVo.getFileNum()==0){
                                                Toast.makeText(myActivity,"没有可导出的报文数据",Toast.LENGTH_SHORT).show();
                                                promptDialog.dismiss();
                                            }else{
                                                new Thread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        try {
                                                            String fileName = fileVo.getFileName().replace("/record/","");
                                                            boolean getFileList = TFTPExample.TftpUtils(myApplication.getVciIp(),fileName,fileVo.getFileName());
                                                            if(getFileList){
                                                                new Thread(new Runnable() {
                                                                    @RequiresApi(api = Build.VERSION_CODES.N)
                                                                    @Override
                                                                    public void run() {
                                                                        Looper.prepare();//增加部分
                                                                        String binPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/com.gx.wda/export_msg_info/bin/"+fileVo.getFileName().replace("/record/","");
                                                                        String ascPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/com.gx.wda/export_msg_info/asc/"+fileVo.getFileName().replace("/record/","").replace(".bin",".asc");
                                                                        String hintPath ="内部存储目录/com.gx.wda/export_msg_info/asc/"+fileVo.getFileName().replace("/record/","").replace(".bin",".asc");
                                                                        try {
                                                                            File f = new File(binPath);
                                                                            int length = (int) f.length();
                                                                            byte[] buff = new byte[length];
                                                                            FileInputStream fin = new FileInputStream(f);
                                                                            fin.read(buff);
                                                                            fin.close();

                                                                            String[] MONTH_EN = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};
                                                                            //"Jan","Feb","Mar","Apr","May","June","July","Aug","Sept","Oct","Nov","Dec"
                                                                            String UTC_DATE = "";
                                                                            //建立链接
                                                                            FileInputStream fileInputStream = new FileInputStream(f);
                                                                            int len = (int) f.length();
                                                                            boolean state = true;
                                                                            boolean isFirst = true;
                                                                            int n = 0;//读出的字节数
                                                                            int i = 0;
                                                                            byte[] bigh = new byte[12];
                                                                            byte [] b_start = new byte[12];
                                                                            File ascPath1 = new File(Environment.getExternalStorageDirectory().getPath() + "/com.gx.wda");
                                                                            File ascPath2 = new File(Environment.getExternalStorageDirectory().getPath() + "/export_msg_info/");
                                                                            File ascFile = new File(ascPath);
                                                                            if (!ascPath1.exists()) {
                                                                                ascPath1.mkdirs();
                                                                            }
                                                                            if (!ascPath2.exists()) {
                                                                                ascPath2.mkdirs();
                                                                            }
                                                                            if (!ascFile.exists()) {
                                                                                ascFile.createNewFile();
                                                                            }
                                                                            FileOutputStream fos = new FileOutputStream(ascFile);

                                                                            while (n != -1) {
                                                                                //创建acs文件

                                                                                //读取前12个字节的报文
                                                                                if (state) {
                                                                                    state = false;
                                                                                    fileInputStream.read(b_start);//返回实际读取到字节数组中的字节数
                                                                                    String HEADER_INFO = EncodingUtil.toHexString(b_start);
                                                                                    //解析数据
                                                                                    //1.起始4个字节，表示CAN_COUNT，全文只出现一次
                                                                                    String CAN_COUNT = HEADER_INFO.substring(0, 8);
                                                                                    //2.接着是UTC，8字节，全文只出现一次
                                                                                    String FILE_UTC = HEADER_INFO.substring(8, 24);
                                                                                    //转小端，然后转byte数组，再转10进制字符串
                                                                                    //UTC_DATE =String.valueOf((int)Long.parseLong(EncodingUtil.sToB(FILE_UTC.split("")),16));
                                                                                    UTC_DATE = EncodingUtil.binaryToDecimal(EncodingUtil.hexString2binaryString(EncodingUtil.sToB(FILE_UTC.split(""))));
                                                                                }

                                                                                //依次读取报文

                                                                                n =  fileInputStream.read(bigh);//读取到数据场前的报文

                                                                                String MSG_INFO = EncodingUtil.toHexString(bigh);//MSG_INFO 存储数据场以前的数据


                                                                                //获取到DLC  （数据场长度）
                                                                                int MSG_DLC = Integer.parseInt(MSG_INFO.substring(13,14));

                                                                                int dataCount = 0;

                                                                                if(MSG_DLC<1)
                                                                                {
                                                                                    dataCount=0;
                                                                                }
                                                                                else if(MSG_DLC<5&&MSG_DLC>0){
                                                                                    dataCount=4;
                                                                                }else if(4<MSG_DLC&&MSG_DLC<9){
                                                                                    dataCount=8;
                                                                                }else if (MSG_DLC > 8)
                                                                                {
                                                                                    dataCount=8;
                                                                                }

                                                                                byte[] bData = new byte[dataCount];
                                                                                n =  fileInputStream.read(bData);//读取数据场数据

                                                                                String dataField = EncodingUtil.toHexString(bData);

                                                                                dataField = dataField.substring(0,MSG_DLC*2);



                                                                                //1.时间戳 （6字节） 小端结构
                                                                                Long timer = Long.parseLong(EncodingUtil.sToB(MSG_INFO.substring(0, 12).split("")), 16);//时间戳
                                                                                BigDecimal bgTimer = new BigDecimal(timer);

                                                                                if(isFirst){
                                                                                    BigDecimal bgUTC = new BigDecimal(UTC_DATE);
                                                                                    bgUTC = bgUTC.add(bgTimer);
                                                                                    UTC_DATE = bgUTC.toString();
                                                                                    // UTC_DATE = Float.toString();
                                                                                    String DATA = EncodingUtil.timeStamp2Date(UTC_DATE, "yyyy-MM-dd");//获取日期 年月日
                                                                                    //获取年份
                                                                                    String YEAR = DATA.split("-")[0];
                                                                                    //获取月份
                                                                                    String MONTH = DATA.split("-")[1];
                                                                                    MONTH = MONTH_EN[Integer.parseInt(MONTH)-1];
                                                                                    //获取日期
                                                                                    String DAY = DATA.split("-")[2];
                                                                                    //获取星期
                                                                                    String WEEK = EncodingUtil.dateToWeek(DATA);
                                                                                    //获取时间
                                                                                    String TIME = EncodingUtil.timeStamp2Date(UTC_DATE, "HH:mm:ss:SSS");//MM dd HH:mm:ss.SSS yyyy
                                                                                    //获取上下午
                                                                                    String HOUR = TIME.split(":")[0];
                                                                                    String IsMORNING = "am";
                                                                                    if (Integer.parseInt(HOUR) > 12) {
                                                                                        IsMORNING = "pm";
                                                                                    } else {
                                                                                        IsMORNING = "am";
                                                                                    }
                                                                                    //写入的内容
                                                                                    //时间格式
                                                                                    String WRITE_DATE = WEEK + " " + MONTH + " " + DAY + " " + TIME + " " + IsMORNING + " " + YEAR;
                                                                                    String WRITE_HEADER = "date " + WRITE_DATE + "\nbase hex timestamps absolute\ninternal events logged\n//version 7.0.0\n" +
                                                                                            "Begin TriggerBlock "+ WRITE_DATE + "\n0.000000 Start of measurement\n";

                                                                                    //String WRITE_DATE = WEEK + " " + MONTH + " " + DAY + " " + TIME + " " + YEAR;
                                                                                    //String WRITE_HEADER = "date " + WRITE_DATE + "\nbase hex timestamps absolute\ninternal events logged\n";

                                                                                    byte[] bytes = WRITE_HEADER.getBytes();
                                                                                    fos.write(bytes);
                                                                                    time = bgTimer;
                                                                                }

                                                                                BigDecimal flo_timer = bgTimer.subtract(time);//减去第一条报文的时间，得到相对时间
                                                                                flo_timer = flo_timer.divide(new BigDecimal(1000*1000));
                                                                                String MSG_TIMER = "";
                                                                                if(isFirst){
                                                                                    isFirst=false;
                                                                                    MSG_TIMER = String.format(Locale.getDefault(), "%.3f", flo_timer);
                                                                                }else {
                                                                                    MSG_TIMER = String.format(Locale.getDefault(), "%.6f", flo_timer);
                                                                                }


                                                                                //2.报文类型 （1字节）
                                                                                String MSG_TYPE = MSG_INFO.substring(14, 16);

                                                                                //3.CAN ID （4字节） 小端结构
                                                                                //CANID根据单帧和多帧不同类型使用不同的解析方式
                                                                                String untreated = EncodingUtil.sToB(MSG_INFO.substring(16, 24).split(""));
                                                                                String first =untreated.substring(0,1);//取得首位 转二进制
                                                                                if(first.equals("0")){
                                                                                    first = "0000";
                                                                                }else {
                                                                                    first = EncodingUtil.hexString2binaryString("0"+first);
                                                                                }
                                                                                String MSG_CANID = first.substring(first.length()-1,first.length()) + untreated.substring(1,untreated.length());
                                                                                MSG_CANID = MSG_CANID.replaceAll("^(0+)", "");
                                                                                //.replaceAll("(0)+$", "")\\




                                                                                //4.CAN通道
                                                                                String CAN_AISLE = "1";
                                                                                if (MSG_TYPE.equals("00")) {
                                                                                    CAN_AISLE = "1";
                                                                                } else if (MSG_TYPE.equals("01")) {
                                                                                    CAN_AISLE = "2";
                                                                                }

                                                                                //5.发送 or 接收
                                                                                String b_MSG_TYPE = EncodingUtil.hexString2binaryString(MSG_TYPE);
                                                                                String b_type = b_MSG_TYPE.substring(b_MSG_TYPE.length()-1,b_MSG_TYPE.length());
                                                                                if(b_type.contains("0")){
                                                                                    b_type = "Rx";
                                                                                }else if(b_type.contains("1")){
                                                                                    b_type = "Tx";
                                                                                }

                                                                                //6.数据场 （）
                                                                                String MSG_DATA = EncodingUtil.addSpace(dataField);
                                        /*String regex = "(.{2})";
                                        MSG_DATA = MSG_DATA.replaceAll(regex, "$1 ");*/

                                                                                //7.ID
                                                                                int MSG_CANID_ID = (int) Long.parseLong(MSG_CANID, 16);


                                                                                //写入格式
                                                                                String WRITE_MSG = " " + MSG_TIMER + " " + CAN_AISLE + " " + MSG_CANID + " "+ b_type + " d " + MSG_DLC + MSG_DATA
                                                                                        + "Length = 768000 BitCount = 67 ID = " + MSG_CANID_ID+"\n";

                                                                                // String WRITE_MSG = "  " + MSG_TIMER + " " + CAN_AISLE + " " + MSG_CANID + " "+ b_type + " d " + MSG_DLC + " " + MSG_DATA+"\n";
                                                                                if (n != -1) {
                                                                                    i++;
                                                                                    byte[] bytes = WRITE_MSG.getBytes();
                                                                                    fos.write(bytes);
                                                                                }
                                                                            }

                                                                            //写入尾部数据
                                                                            String WRITE_FOOTER = "\nEnd TriggerBlock";
                                                                            Log.e("WRITE_FOOTER", WRITE_FOOTER);
                                                                            byte[] bytes = WRITE_FOOTER.getBytes();
                                                                            fos.write(bytes);


                                                                            //关闭流
                                                                            fos.close();
                                                                            Toast.makeText(myActivity,"文件已保存于"+hintPath,Toast.LENGTH_LONG).show();
                                                                            //打开文件
                                    /*Intent intent = new Intent(myActivity,TbsActivity.class);
                                    intent.putExtra("filePath",ascPath);
                                    myActivity.startActivity(intent);*/
                                                                        }catch (IOException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                        finally {
                                                                            myActivity.runOnUiThread(new Runnable() {
                                                                                @Override
                                                                                public void run() {
                                                                                    promptDialog.dismiss();
                                            /*File fileBin = new File(binPath);
                                            if(fileBin.exists()){
                                                fileBin.delete();//删除Bin文件
                                            }*/
                                                                                }
                                                                            });
                                                                        }
                                                                        Looper.loop();//增加部分
                                                                    }
                                                                }).start();
                                                            }else{
                                                                myActivity.runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        promptDialog.dismiss();
                                                                        //连接失败
                                                                        Toast.makeText(myActivity,"导出文件失败！",Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                            }
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                            //连接失败
                                                            myActivity.runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    promptDialog.dismiss();
                                                                    Toast.makeText(myActivity,"导出文件失败！",Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                        }
                                                    }
                                                }).start();
                                            }
                                        }
                                        dialog.dismiss();//关闭弹出框
                                    }
                                });
                                messageDialog.setTitle(myActivity.getResources().getString(R.string.text_warm_prompt));
                                messageDialog.show();//显示弹出框

                            }
                        }

                    }
                });

               /* //长按事件
                holder.llVciFile.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        isCheck  = true;
                        holder.cbCheck.setChecked(true);
                        notifyDataSetChanged();
                        return true;
                    }
                });*/

            }

        }





    @Override
    public int getItemCount() {
        return mListData.size();
    }


    /**
     * 添加数据
     * @param listAdd
     */
    public void addItem(List<VciFileVo> listAdd) {
        //如果是加载第一页，需要先清空数据列表
        this.mListData.clear();
        //添加数据
        if (listAdd != null) {
            this.mListData.addAll(listAdd);
        }
        //通知RecyclerView进行改变--整体
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView tvFileName;
        private final TextView tvFileCount;
        private final TextView tvFileSize;
        private final LinearLayout llVciFile;
        private final CheckBox cbCheck;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            llVciFile = itemView.findViewById(R.id.ll_vci_file);
            tvFileName = itemView.findViewById(R.id.tv_vci_file_name);
            tvFileCount = itemView.findViewById(R.id.tv_vci_file_count);
            tvFileSize = itemView.findViewById(R.id.tv_vci_file_size);
            cbCheck = itemView.findViewById(R.id.cb_check_file);
        }
    }


}

