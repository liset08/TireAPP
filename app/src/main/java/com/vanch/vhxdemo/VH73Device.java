package com.vanch.vhxdemo;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.com.vanch.vhxdemo.helper.Utility;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

//import de.greenrobot.event.EventBus;

/**
 * 设备类，包含所有的命令操作
 * 
 * @author lgnhm_000
 */
public class VH73Device {
	public static class GetCommandResultSuccess {
		boolean success;

		public GetCommandResultSuccess(boolean b) {
			this.success = b;
		}

		public boolean isSuccess() {
			return success;
		}
	}

	public static class SendCommandSuccess {
		boolean success;

		public SendCommandSuccess(boolean b) {
			this.success = b;
		}

		public boolean isSuccess() {
			return success;
		}
	}

	public static class GetCmdResultEvent {
		byte[] ret;

		public GetCmdResultEvent(byte[] ret) {
			this.ret = ret;
		}

		public byte[] getRet() {
			return ret;
		}
	}

	public static class ReadTimerStop {

	}

	public static class ReadTimerStart {

	}

	private static final Logger LOG = Logger.getLogger(VH73Device.class
			.getName());

	private HandsetVersion handsetVersion;
	private HandsetParam handsetParam;

	private static final String TAG = "VH75";
	public static final String SerialPortServiceClass_UUID = "00001101-0000-1000-8000-00805F9B34FB";

	// public VH73Device(BluetoothDevice device) {
	// this.bluetoothDevice = device;
	// }
	//
	// private Context context;

	private Activity activity;

	public VH73Device(Activity activity, BluetoothDevice device) {
		this.bluetoothDevice = device;
		this.activity = activity;
	}

	private BluetoothDevice bluetoothDevice;

	public BluetoothDevice getBluetoothDevice() {
		return bluetoothDevice;
	}

	private InputStream inStream;
	private OutputStream outStream;
	private BluetoothSocket socket;
	private boolean isConnected;

	public HandsetParam getHandsetParam() {
		return handsetParam;
	}

	public void setHandsetParam(HandsetParam handsetParam) {
		this.handsetParam = handsetParam;
	}

	/**
	 * merger 2 byte[]
	 * 
	 * @param first
	 * @param second
	 * @return the merged byte[]
	 */
	private static byte[] mergeBytes(byte[] first, byte[] second) {
		byte[] ret = new byte[first.length + second.length];
		System.arraycopy(first, 0, ret, 0, first.length);
		System.arraycopy(second, 0, ret, first.length, second.length);
		return ret;
	}

	/**
	 * 解析获取记录结果
	 * 
	 * @param data
	 * @return 返回的记录列表
	 */
	public static LinkedList<RecordData> parseGetRecordResult(byte[] data) {
		LinkedList<RecordData> list = new LinkedList<RecordData>();
		int num = data[3];

		if (num == 0) {
			return list;
		}

		byte[] dataSegment = new byte[250];
		// Arrays.copyOfRange(dataSegment, 4, data.length-1);
		System.arraycopy(data, 4, dataSegment, 0, 250);

		for (int i = 0; i < num; i++) {
			// int index = 0;
			RecordData recordData = new RecordData();
			Date date = new Date();
			date.setYear(Utility.BYTE(dataSegment[0] + 2000 - 1900));
			date.setMonth(Utility.BYTE(dataSegment[1]) - 1);
			date.setDate(Utility.BYTE(dataSegment[2]));
			date.setHours(Utility.BYTE(dataSegment[3]));
			date.setMinutes(Utility.BYTE(dataSegment[4]));
			date.setSeconds(Utility.BYTE(dataSegment[5]));
			recordData.time = date;

			byte tagtype = dataSegment[6];
			recordData.tagtype = tagtype;
			// epc
			byte epcLen = dataSegment[7];
			recordData.epcLen = epcLen;

			byte[] epc = new byte[16];
			// Arrays.copyOfRange(epc, 8, 23);
			System.arraycopy(dataSegment, 8, epc, 0, 16);
			recordData.epc = epc;

			if (tagtype == 0x04) { // 6C
				byte tidLen = dataSegment[24];
				byte[] tid = new byte[8];
				// Arrays.copyOfRange(tid, 25, 32);
				System.arraycopy(dataSegment, 25, tid, 0, 8);

				byte userLen = dataSegment[33];
				byte[] user = new byte[249 - 34 + 1];
				// Arrays.copyOfRange(user, 34, 250);
				System.arraycopy(dataSegment, 34, user, 0, 216);

				recordData.tid = tid;
				recordData.tidLen = tidLen;
				recordData.user = user;
				recordData.userLen = userLen;
			} else {
				byte userLen = dataSegment[24];
				byte[] user = new byte[249 - 25 + 1];
				// Arrays.copyOfRange(user, 25, 250);
				System.arraycopy(dataSegment, 25, user, 0, 225);

				recordData.user = user;
				recordData.userLen = userLen;
			}
			//
			list.add(recordData);
		}

		return list;
	}

	/**
	 * 解析获取版本号结果
	 * 
	 * @param data
	 * @return
	 */
	public static HandsetVersion parseGetVersionResult(byte[] data) {

		HandsetVersion version = new HandsetVersion();
		version.hdVer1 = data[3];
		version.hdVer2 = data[4];
		version.swVer1 = data[5];
		version.swVer2 = data[6];

		return version;
	}

	/**
	 * 解析获取名单结果
	 * 
	 * @param data
	 * @return
	 */
	public static List<String> parseGetLabelIDResult(byte[] data) {
		LinkedList<String> result = new LinkedList<String>();

		int count = data[3];
		int index = 4;

		for (int i = 0; i < count; i++) {
			int len = data[index];
			byte[] id = new byte[len * 2];
			for (int j = 0; j < len * 2; j++) {
				id[j] = data[index + 1 + j];
			}

			result.add(Utility.bytes2HexString(id));
			index += 17;
		}

		return result;
	}

	/**
	 * 解析获取设备基本参数结果
	 * 
	 * @param data
	 * @return
	 */
	public static HandsetParam parseReadparamResult(byte[] data) {
		HandsetParam param = new HandsetParam();
		int index = 3;

		param.TagType = data[index++];
		param.Alarm = data[index++];
		param.OutputMode = data[index++];
		param.USBBaudRate = data[index++];
		param.Reserve5 = data[index++];
		param.Min_Frequence = data[index++];
		param.Max_Frequence = data[index++];
		param.Power = data[index++];
		param.RFhrdVer1 = data[index++];
		param.RFhrdVer2 = data[index++];
		param.RFSoftVer1 = data[index++];
		param.RFSoftVer2 = data[index++];
		param.ISTID = data[index++];
		param.TIDAddr = data[index++];
		param.TIDLen = data[index++];
		param.ISUSER = data[index++];
		param.USERAddr = data[index++];
		param.USERLen = data[index++];

		param.Reserve19 = data[index++];
		param.Reserve20 = data[index++];
		param.Reserve21 = data[index++];
		param.Reserve22 = data[index++];
		param.Reserve23 = data[index++];
		param.Reserve24 = data[index++];
		param.Reserve25 = data[index++];
		param.Reserve26 = data[index++];
		param.Reserve27 = data[index++];
		param.Reserve28 = data[index++];
		param.Reserve29 = data[index++];
		param.Reserve30 = data[index++];
		param.Reserve31 = data[index++];
		param.Reserve32 = data[index];
		return param;
	}

	/**
	 * 解析获取ID结果
	 * 
	 * @param data
	 * @return
	 */
	public static String parserGetHandsetIDResult(byte[] data) {
		byte[] dataSegment = getDataSegment(data);
		StringBuffer buffer = new StringBuffer();
		for (byte b : dataSegment) {
			if (b != 0) {
				buffer.append((char) b);
			}
		}
		return buffer.toString();
	}


	private static byte[] getDataSegment(byte[] data) {
		int len = data[1];
		byte[] dataSegment = new byte[len - 2];
		System.arraycopy(data, 3, dataSegment, 0, len - 2);
		LOG.info("data segment is " + Utility.bytes2HexString(dataSegment));
		return dataSegment;
	}


	public boolean connect() {
		BluetoothSocket tmpSocket;
		InputStream tmpIn = null;
		OutputStream tmpOut = null;

		try {
			tmpSocket = bluetoothDevice.createRfcommSocketToServiceRecord(UUID.fromString(SerialPortServiceClass_UUID));
			tmpSocket.connect();
			tmpIn = tmpSocket.getInputStream();
			tmpOut = tmpSocket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(TAG, "connect to " + bluetoothDevice.getName() + " failed!");
			return false;
		}

		socket = tmpSocket;
		inStream = tmpIn;
		outStream = tmpOut;
		setConnected(true);

		// new Thread() {
		// public void run() {
		// while(true) {
		// try {
		// byte[] ret = getCmdResult();
		// EventBus.getDefault().post(new GetCmdResultEvent(ret));
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
		// }
		// }.start();

		return true;
	}


	public void disconnect() throws IOException {
		try {
			inStream.close();
			outStream.close();
			socket.close();
			setConnected(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isConnected() {
		return isConnected;
	}

	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}

	public String getAddress() {
		return bluetoothDevice.getAddress();
	}

	/**
	 * generate command byte[] for sending to device
	 * 
	 * @param cmd
	 *            command code
	 * @param param
	 *            command param, null is there is no param
	 */
	private byte[] generateCMD(CommandCode cmd, byte[] param) {
		int len = 1 + 1; // cmd + checksum
		if (param != null) {
			len = len + param.length; // param
		}

		ByteBuffer buffer = ByteBuffer.allocate(len + 2);
		buffer.put((byte) Head.SEND.getCode()).put((byte) len)
				.put((byte) cmd.getCode());
		if (param != null) {
			buffer.put(param);
		}

		byte checksum = crc(buffer.array(), len + 1);
		buffer.put(checksum);

		LOG.info("cmd=[" + Utility.bytes2HexString(buffer.array()) + "]");

		return buffer.array();
	}

	private byte[] genTimeParam(Date date) {
		byte[] param = new byte[6];
		param[0] = Utility.BYTE(date.getYear() + 1900 - 2000);
		param[1] = Utility.BYTE(date.getMonth() + 1);
		param[2] = Utility.BYTE(date.getDate());
		param[3] = Utility.BYTE(date.getHours());
		param[4] = Utility.BYTE(date.getMinutes());
		param[5] = Utility.BYTE(date.getSeconds());

		return param;
	}

	private byte crc(byte[] data, int len) {
		byte checksum = 0;
		for (int i = 0; i < len; i++) {
			checksum += data[i];
		}
		checksum = Utility.BYTE(~checksum);
		checksum = Utility.BYTE(checksum + 1);

		return Utility.BYTE(checksum);
	}

	/**
	 * 发送命令
	 * 
	 * @param cmd
	 * @throws IOException
	 */
	public void sendCommand(byte[] cmd) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss.SSS");
			Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
			String Standby = formatter.format(curDate);

			// String strData =
			// YY+"-"+MM+"-"+DD+"-"+HH+"-"+NN+"-"+SS+"-"+MI+"--recv:" +
			// Utility.bytes2HexString(bytesTmp);
			String strData = "[" + Standby + "]" + "---Send:" + "("
					+ cmd.length + ")" + Utility.bytes2HexString(cmd);
			wq_UdpSendData(strData + "\r\n");

			outStream.write(cmd);
			//EventBus.getDefault().post(new SendCommandSuccess(true));
		} catch (IOException e1) {
			LOG.info("Write cmdError");
			e1.printStackTrace();
		}

	}

	private byte[] genGetVersionCmd() {
		return generateCMD(CommandCode.GetVersion, null);
	}

	/**
	 * 发送获取版本命令
	 * 
	 * @throws IOException
	 */
	public void getVersion() throws IOException {
		sendCommand(genGetVersionCmd());
	}

	private byte[] genAddLabelIDCmd(List<byte[]> ids) {
		byte[] param = new byte[1];
		param[0] = (byte) ids.size(); // len

		for (int i = 0; i < ids.size(); i++) {
			byte[] id = ids.get(i);
			param = mergeBytes(param, new byte[] { (byte) (id.length / 2) });
			param = mergeBytes(param, id);
		}

		return generateCMD(CommandCode.AddLableID, param);
	}


	public void addLable(String id) throws IOException {
		byte[] param = new byte[18];
		int len = id.length() / 2;
		param[0] = 0x01;
		param[1] = (byte) (len / 2);

		for (int i = 0; i < len; i++) {
			param[2 + i] = (byte) (0xff & Integer.parseInt(
					id.substring(i * 2, i * 2 + 2), 16));
		}
		sendCommand(generateCMD(CommandCode.AddLableID, param));

		// sendCommand(genAddLabelIDCmd(ids));
	}

	private byte[] genDelLabelIDCmd(List<byte[]> ids) {
		byte[] param = new byte[1];
		param[0] = (byte) ids.size(); // len

		for (int i = 0; i < ids.size(); i++) {
			byte[] id = ids.get(i);
			param = mergeBytes(param, new byte[] { (byte) (id.length / 2) });
			param = mergeBytes(param, id);
		}

		return generateCMD(CommandCode.DelLableID, param);
	}


	public void deleteLabel() throws IOException {
		// sendCommand(genDelLabelIDCmd(ids));
		sendCommand(generateCMD(CommandCode.DelLableID, null));
	}


	private byte[] genGetLabelIDCmd(int addr, int len) {
		byte[] param = new byte[3];
		param[0] = (byte) (addr >> 8);
		param[1] = (byte) (addr & 0xFF);
		param[2] = (byte) len;
		return generateCMD(CommandCode.GetLableID, param);
	}


	public void getLabelID(int addr, int len) throws IOException {
		sendCommand(genGetLabelIDCmd(addr, len));
	}


	public void saveLabel() throws IOException {
		sendCommand(generateCMD(CommandCode.SaveLabelToSDCard, null));
	}

	/**
	 * 生成读取设备参数指令数据
	 * 
	 * @return 指令数据
	 */
	private byte[] genReadparamCmd() {
		return generateCMD(CommandCode.ReadHandsetParam, null);
	}

	/**
	 * 读取设备参数
	 * 
	 * @throws IOException
	 */
	public void readParam() throws IOException {
		sendCommand(genReadparamCmd());
	}

	private byte[] genWriteParam(HandsetParam param) {
		return generateCMD(CommandCode.WriteHanderParam, param.toBytes());
	}

	/**
	 * 设置设备参数
	 * 
	 * @param param
	 * @throws IOException
	 */
	public void writeParam(HandsetParam param) throws IOException {
		sendCommand(genWriteParam(param));
	}

	private byte[] genWriteFactoryParam(HandsetParam param) {
		return generateCMD(CommandCode.WriteFactoryParam, param.toBytes());
	}

	public void writeFactoryParam(HandsetParam param) throws IOException {
		sendCommand(genWriteFactoryParam(param));
	}

	/**
	 * 生成设置过滤器指令数据
	 * 
	 * @param addr
	 *            掩码地址， 0 - 95
	 * @param len
	 *            掩码长度， 0 - 96
	 * @param mask
	 *            M个字节，输入M的字符串
	 * @return 指令数据
	 */
	private byte[] genSetReportFilterCmd(int addr, int len, byte[] mask) {

		byte[] param;
		if (len == 0) {
			param = new byte[5];
		} else {
			int i, m;

			if (len % 8 == 0) {
				m = len / 8;
			} else {
				m = len / 8 + 1;
			}

			param = new byte[m + 4];
			param[0] = (byte) (addr >> 8);
			param[1] = (byte) addr;
			param[2] = (byte) (len >> 8);
			param[3] = (byte) len;

			for (i = 0; i < m; i++) {
				param[4 + i] = mask[i];
			}
		}

		return generateCMD(CommandCode.SetReportFilter, param);
	}

	/**
	 * 设置过滤
	 * 
	 * @param addr
	 * @param len
	 * @param mask
	 * @throws IOException
	 */
	public void setReportFilter(int addr, int len, byte[] mask)
			throws IOException {
		sendCommand(genSetReportFilterCmd(addr, len, mask));
	}

	/**
	 * 获取过滤
	 * 
	 * @throws IOException
	 */
	public void getReportFilter() throws IOException {
		sendCommand(generateCMD(CommandCode.GetReportFilter, null));
	}

	// public byte[] genSetFactoryParam(HandsetParam param) {
	// throw new UnsupportedOperationException();
	// }

	/**
	 * 生成恢复出厂设置指令数据
	 * 
	 * @return 指令数据
	 */
	private byte[] genResetFactoryparam() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 生成设置时间指令数据
	 * 
	 * @param date
	 *            时间
	 * @return 指令数据
	 */
	private byte[] genSetTimeCmd(Date date) {
		return generateCMD(CommandCode.SetReaderTime, genTimeParam(date));
	}

	/**
	 * 设置时间
	 * 
	 * @param date
	 * @throws IOException
	 */
	public void setTime(Date date) throws IOException {
		sendCommand(genSetTimeCmd(date));
	}

	/**
	 * 生成获取设备时间指令数据
	 * 
	 * @return 指令数据
	 */
	private byte[] genGetTimeCmd() {
		return generateCMD(CommandCode.GetReaderTime, null);
	}

	/**
	 * 获取时间命令
	 * 
	 * @throws IOException
	 */
	public void getTime() throws IOException {
		sendCommand(genGetTimeCmd());
	}

	/**
	 * 解析获取时间命令结果
	 * 
	 * @param data
	 * @return
	 */
	public static Date parseGetTimeResult(byte[] data) {
		Date date = new Date();
		date.setYear(Utility.BYTE(data[3] + 2000 - 1900));
		date.setMonth(Utility.BYTE(data[4]) - 1);
		date.setDate(Utility.BYTE(data[5]));
		date.setHours(Utility.BYTE(data[6]));
		date.setMinutes(Utility.BYTE(data[7]));
		date.setSeconds(Utility.BYTE(data[8]));

		return date;
	}

	/**
	 * 
	 * @param start
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @return 指令数据
	 */
	private byte[] genGetRecordCmd(Date start, Date end) {
		ByteBuffer buffer = ByteBuffer.allocate(6 + 6);
		buffer.put(genTimeParam(start));
		buffer.put(genTimeParam(end));

		return generateCMD(CommandCode.GetRecord, buffer.array());
	}

	/**
	 * 下载记录
	 * 
	 * @param start
	 * @param end
	 * @throws IOException
	 */
	public void getRecord(Date start, Date end) throws IOException {
		sendCommand(genGetRecordCmd(start, end));
	}

	private byte[] genGetHansetIDCmd() {
		return generateCMD(CommandCode.GetHandsetID, null);
	}

	/**
	 * 获取设备ID
	 * 
	 * @throws IOException
	 */
	public void getHandsetID() throws IOException {
		sendCommand(genGetHansetIDCmd());
	}

	/**
	 * 设置设备ID
	 * 
	 * @param id
	 * @throws IOException
	 */
	public void setHandsetID(String id) throws IOException {
		sendCommand(genSetHansetID(id));
	}

	private byte[] genSetHansetID(String id) {
		byte[] bidBs = id.getBytes();
		byte[] param = new byte[10];
		System.arraycopy(bidBs, 0, param, 0, bidBs.length);
		return generateCMD(CommandCode.SetHandsetID, param);
	}

	private byte[] genDelRecordCmd() {
		return generateCMD(CommandCode.DeleteAllRecord, null);
	}

	/**
	 * 删除设备记录
	 * 
	 * @throws IOException
	 */
	public void delRecord() throws IOException {
		sendCommand(genDelRecordCmd());
	}

	private byte[] genSetBTNameCmd(String name) {
		byte[] bidBs = name.getBytes();
		return generateCMD(CommandCode.SetBluetoothName, bidBs);
	}

	/**
	 * 设置蓝牙名称
	 * 
	 * @param name
	 * @throws IOException
	 */
	public void setBtName(String name) throws IOException {
		sendCommand(genSetBTNameCmd(name));
	}

	private byte[] genGetBTNameCmd() {
		return generateCMD(CommandCode.GetBluetoothName, null);
	}

	/**
	 * 获取蓝牙名称
	 * 
	 * @throws IOException
	 */
	public void getBTName() throws IOException {
		sendCommand(generateCMD(CommandCode.GetBluetoothName, null));
	}

	/**
	 * 解析获取蓝牙名字结果
	 * 
	 * @param data
	 * @return
	 */
	public static String parseGetBTNameResult(byte[] data) {
		byte[] dataSegment = getDataSegment(data);
		StringBuffer buffer = new StringBuffer();
		for (byte b : dataSegment) {
			if (b != 0) {
				buffer.append((char) b);
			}
		}
		return buffer.toString();
	}

	/**
	 * 设置蓝牙波特率
	 * 
	 * @param baud
	 * @throws IOException
	 */
	public void setBtBaudRate(byte baud) throws IOException {
		sendCommand(generateCMD(CommandCode.SetBtBaudRate, new byte[] { baud }));
	}

	/**
	 * 获取蓝牙波特率
	 * 
	 * @throws IOException
	 */
	public void getBtBaudRate() throws IOException {
		sendCommand(generateCMD(CommandCode.GetBtBaudRate, null));
	}

	/**
	 * 设置手机进入读写器模式，即模块电源打开，1--打开，0--关闭
	 * 
	 * @param nMode
	 * @throws IOException
	 */
	public void SetReaderMode(byte nMode) throws IOException {
		sendCommand(generateCMD(CommandCode.SetReaderMode, new byte[] { nMode }));
	}

	/**
	 * 读取一个字节数据
	 * 
	 * @return
	 * @throws IOException
	 */
	public int read() throws IOException {
		// EventBus.getDefault().post(new ReadTimerStart());
		int i = inStream.read();
		// EventBus.getDefault().post(new ReadTimerStop());
		return i;
	}

	/**
	 * 
	 * @param buffer
	 * @return
	 * @throws IOException
	 */
	public int read(byte[] buffer) throws IOException {
		return inStream.read(buffer);
	}

	/**
	 * 打印错误信息
	 * 
	 * @param errCode
	 * @return
	 */
	public static String errMessage(int errCode) {
		String errorString = null;
		switch (errCode) {
		case (byte) 0x10:
			errorString = "invalid command";
			break;
		case (byte) 0x11:
			errorString = "Param error";
			break;
		case (byte) 0x12:
			errorString = "checksum error";
			break;
		case (byte) 0x20:
			errorString = "command executing error";
			break;
		default:
			errorString = "unkown error";
			break;
		}

		return errorString;
	}

	private void processGetReportFileter(byte[] data) {
		int addr = data[3] * 0x100 + data[4];
		int len = data[5] * 0x100 + data[6];
		int l = 0;

		// jTextField_mask_len.setText(len + "");
		// jTextField_mask_start_addr.setText(addr + "");

		if (len % 8 == 0) {
			l = len / 8;
		} else {
			l = len / 8 + 1;
		}

		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < l; i++) {
			buffer.append(String.format("%02X", data[i + 7]));
		}
		// jTextField_mask.setText(buffer.toString());
	}

	/**
	 * 
	 * @return
	 */
	public HandsetVersion getHandsetVersion() {
		return handsetVersion;
	}

	/**
	 * 
	 * @param handsetVersion
	 */
	public void setHandsetVersion(HandsetVersion handsetVersion) {
		this.handsetVersion = handsetVersion;
	}

	/**
	 * 一条标签记录
	 */
	public static class RecordData {
		/**
		 * id号
		 */
		public byte[] id;
		/**
		 * 记录时间
		 */
		public Date time;
		public byte[] user;
		public byte[] tid;
		public byte tagtype;
		public byte tidLen;
		public byte userLen;
		public byte[] epc;
		public byte epcLen;

		@Override
		public String toString() {
			return time.toLocaleString() + " " + Utility.bytes2HexString(id);
		}
	}

	public ConfigParam readConfigParam() throws IOException {
		// TODO read config
		// ========== test ==============
		ConfigParam param = new ConfigParam();
		param.setAutolink(true);
		param.setPower(25);
		param.setSwVersion("1.0.0");
		param.setHwVersion("2.0.0");
		return param;
	}

	public void access() throws IOException {
		// TODO do access
	}

	// public void inventory(int mem, int addr, int len) throws IOException {
	// // listTagID(1, 0, 0);
	// // getCmdResult();
	// }

	public void listTagID(int mem, int addr, int len) throws IOException {
		byte[] cmd = genListTagIDCmd(mem, addr, len, new byte[] {});

		sendCommand(cmd);
	}

	private byte[] genListTagIDCmd(int mem, int addr, int len, byte[] mask) {

		byte[] param;
		if (len == 0) {
			param = new byte[4];
			param[0] = (byte) mem;
		} else {
			int i, m;

			if (len % 8 == 0) {
				m = len / 8;
			} else {
				m = len / 8 + 1;
			}

			param = new byte[m + 4];
			param[0] = (byte) mem;
			param[1] = (byte) (addr >> 8);
			param[2] = (byte) (addr & 0xFF);
			param[3] = (byte) len;

			for (i = 0; i < m; i++) {
				param[4 + i] = mask[i];
			}
		}

		return generateCMD(CommandCode.listTag, param);
	}

	public void getListTagID(int no, int l) throws IOException {
		byte[] param = new byte[2];
		param[0] = (byte) no;
		param[1] = (byte) l;
		byte[] cmd = generateCMD(CommandCode.getIdList, param);
		sendCommand(cmd);
	}

	/**
	 * getListTagID command result
	 * 
	 * @param ret
	 * @return
	 */
	public static ListTagIDResult parseGetListTagIDResult(byte[] ret) {
		byte[] data = getDataSegment(ret);
		int len = data[0];

		int index = 1;
		ArrayList<byte[]> epcs = new ArrayList<byte[]>();
		for (int i = 0; i < len; i++) {
			int ecpLen = data[index];
			byte[] epc = new byte[ecpLen * 2];
			System.arraycopy(data, index + 1, epc, 0, ecpLen * 2);
			epcs.add(epc);
			index = index + (ecpLen * 2) + 1;
			Log.i(TAG, "get epc " + Utility.bytes2HexString(epc) + " " + index
					+ " " + ecpLen);
		}

		return new ListTagIDResult(len, epcs);
	}

	/**
	 * read command result data
	 * 
	 * @return
	 * @throws IOException
	 */
	public byte[] getCmdResult() throws IOException {
		int head = read();
		int len = read();
		ByteBuffer bb = ByteBuffer.allocate(2 + len);
		bb.put((byte) head).put((byte) len);
		for (int i = 0; i < len; i++) {
			bb.put((byte) read());
		}

		Log.d(TAG, "get result " + Utility.bytes2HexString(bb.array()));
		//EventBus.getDefault().post(new GetCommandResultSuccess(true));
		return bb.array();
	}

	/**
	 * 超时读取结果
	 * 
	 * @param maxTimeout
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	// public byte[] getCmdResultWithTimeout(long maxTimeout)
	// throws IOException, InterruptedException, TimeoutException {
	// long interval = 30;
	// int available = 0;
	// long timeout = 0;
	//
	// while((available = inStream.available()) == 0 && timeout <
	// ConfigUI.getConfigTimeout(activity)) {
	// timeout += interval;
	// Thread.sleep(interval);
	// }
	//
	// if( available <=0 && timeout > 0) {
	// throw new TimeoutException();
	// }
	//
	// Log.i(TAG, "available " + available+" timeout " + timeout);
	//
	// int head = inStream.read();
	// //skip data from click button on the reader
	// if ( Head.RECEIVE_OK.getCode() != (byte)head &&
	// Head.RECEIVE_FAIL.getCode() != (byte)head ) {
	// Log.w(TAG, "Get other data, skip 250 bytes [head] " + head + " " +
	// (byte)head);
	// long start = System.currentTimeMillis();
	// for (int i = 0; i< 249; i++) {
	// inStream.read();
	// }
	// long cost = System.currentTimeMillis() - start;
	// timeout+=cost;
	//
	// while((available = inStream.available()) == 0 && timeout <
	// ConfigUI.getConfigTimeout(activity)) {
	// timeout += 100;
	// Thread.sleep(100);
	// }
	//
	// if( available <=0 ) {
	// throw new TimeoutException();
	// }
	// }
	//
	// if ( Head.RECEIVE_OK.getCode() != (byte)head &&
	// Head.RECEIVE_FAIL.getCode() != (byte)head ) {
	// head = inStream.read();
	// }
	//
	// int len = read();
	// ByteBuffer bb = Byte.allocate(2 + len);
	// bb.put((byte) head).put((byte) len);
	// for (int i = 0; i < len; i++) {
	// bb.put((byte) read());
	// }
	//
	// Log.d(TAG, "get result " + Utility.bytes2HexString(bb.array()) +" " +
	// VH73Device.getError(bb.array()));
	// EventBus.getDefault().post(new GetCommandResultSuccess(true));
	// return bb.array();
	// }

	public byte[] getCmdResultWithTimeout(long maxTimeout) throws IOException,
			InterruptedException, TimeoutException {
		long interval = 30;
		int available = 0;
		long timeout = 0;
		//long timev = ConfigUI.getConfigTimeout(activity);
		byte[] buffer = new byte[1024];
		byte[] bufTmp = new byte[5];
		int bytes = 0;
		int k = 0;
		int j = 0;
		int i = 0;
		long start = System.currentTimeMillis();
		String strData = "";

		// 下面这是JAVA中的条件编译，与C++还是有点不同的，C++中则为#IFDEF这种形式
		final boolean bComplile = false;// true为以前的算法，false 为后来的算法

		if (bComplile) {
			while (true) {
			//	if ((available = inStream.available()) == 0 && timeout < timev) {
				//	Thread.sleep(interval);
			//		timeout += interval;
				//	continue;
			//	}

				//if (available <= 0 && timeout >= timev)
///
				int head = inStream.read();
				// skip data from click button on the reader
				if (Head.RECEIVE_OK.getCode() != (byte) head
						&& Head.RECEIVE_FAIL.getCode() != (byte) head) {
					Log.w(TAG, "Get other data, skip 250 bytes [head] " + head
							+ " " + (byte) head);

					for (i = 0; i < 249; i++) {
						if ((available = inStream.available()) == 0
								&& (System.currentTimeMillis() - start) < timeout) {
							Thread.sleep(interval);
							continue;
						}
						inStream.read();
					}
					// long cost = System.currentTimeMillis() - start;
					// timeout += cost;
					// continue;
				}

				int len = read();
				ByteBuffer bb = ByteBuffer.allocate(2 + len);
				bb.put((byte) head).put((byte) len);
				for (i = 0; i < len; i++) {
					bb.put((byte) read());
				}

				Log.d(TAG, "get result " + Utility.bytes2HexString(bb.array())
						+ " " + VH73Device.getError(bb.array()));
				//EventBus.getDefault().post(new GetCommandResultSuccess(true));
				return bb.array();
			}
		} else {
			// Calendar CD = Calendar.getInstance();
			// int YY = CD.get(Calendar.YEAR);
			// int MM = CD.get(Calendar.MONTH) + 1;
			// int DD = CD.get(Calendar.DATE);
			// int HH = CD.get(Calendar.HOUR);
			// int NN = CD.get(Calendar.MINUTE);
			// int SS = CD.get(Calendar.SECOND);
			// int MI = CD.get(Calendar.MILLISECOND);
			// System.out.println();

			// 1.在超时时间内读出两字节,如果超时了直接退出
			// 2.判断第一个字节是不是F0或F4，如果不是则退出
			// 3.根据第二个字节长度，则读出后面的这么多字节，如：F403EE0219,如果超时则退出
			// 4.分析数据，或返回读出BYTE数组
			long lnow = android.os.SystemClock.uptimeMillis(); // 起始时间
			long lnew = android.os.SystemClock.uptimeMillis(); // 结束时间
			// if(true)
			{
				// 1.在超时时间内读出两字节,如果超时了直接退出
				int count = 0;
				int readCount = 0;
				byte[] bufferTmp1 = new byte[5];
				byte[] bufferTmp2 = new byte[32];

				// <1>先读5个
				for (j = 0; j < 5;) {
					lnew = android.os.SystemClock.uptimeMillis();

					available = inStream.available();
					if (available > 0) {
						inStream.read(bufferTmp1, j, 1);
						j++;
					} else {
						continue;
					}
				}

				// 如果大于或等于以上则有可能读到了？
				if (j >= 5) {
					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss.SSS");
					Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
					String Standby = formatter.format(curDate);

					strData = "[" + Standby + "]" + "---recx:" + "(" + j + ")"
							+ Utility.bytes2HexString(bufferTmp1);
					wq_UdpSendData(strData + "\r\n");
					Log.d(TAG, strData);
				}

				// /////////////////////////////////////////////////////////////////////
				// 下面这是判跳250个字节
				// <2>.判断第一个字节是不是F0或F4，如果不是则退出
				strData = "Head Check1!!!";
				wq_UdpSendData(strData + "\r\n");

				int head = bufferTmp1[0];
				// skip data from click button on the reader
				if (Head.RECEIVE_OK.getCode() != (byte) head
						&& Head.RECEIVE_FAIL.getCode() != (byte) head) {
					Log.w(TAG, "Get other data, skip 250 bytes [head] " + head
							+ " " + (byte) head);
					start = System.currentTimeMillis();
					for (i = 0; i < 245; i++) {
						lnew = android.os.SystemClock.uptimeMillis();

						// inStream.read();
						available = inStream.available();
						if (available > 0) {
							inStream.read(buffer, i, 1);
						} else {
							continue;
						}

					}

					strData = "Get Error!!!";
					wq_UdpSendData(strData + "\r\n");
					buffer = new byte[5];
					buffer[0] = (byte) 0xF4;
					buffer[1] = (byte) 0x03;
					buffer[2] = (byte) 0xEE;
					buffer[3] = (byte) 0x02;
					buffer[4] = (byte) 0x19;
					return buffer;

				}
				// //////////////////////////////////////////////////////////////////////

				// <3>.根据第二个字节长度，则读出后面的这么多字节，如：F403EE0219,如果超时则退出

				// int len = read();
				bufferTmp2 = new byte[1];
				strData = "Head Check2!!!";
				wq_UdpSendData(strData + "\r\n");
				int iRevc = 5;// 是已心声了的
				int len = bufferTmp1[1];
				if (Head.RECEIVE_OK.getCode() == (byte) head
						|| Head.RECEIVE_FAIL.getCode() == (byte) head) {
					count = 2 + len - iRevc;
					if (count > 0) {
						bufferTmp2 = new byte[count];
					}
					i = 0;
					while (i < count) {
						lnew = android.os.SystemClock.uptimeMillis();

						available = inStream.available();
						if (available > 0) {
							inStream.read(bufferTmp2, i, 1);
							i++;
						} else {
							continue;
						}

					}

					if (count > 0) {
						SimpleDateFormat formatter = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss.SSS");
						Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
						String Standby = formatter.format(curDate);

						strData = "[" + Standby + "]" + "---recy:" + "(" + i
								+ ")"
								+ Utility.bytes2HexString(bufferTmp2);
						wq_UdpSendData(strData + "\r\n");
					}

					// 合并字节
					buffer = new byte[5 + bufferTmp2.length];
					int kk = 0;
					int ii = 0;
					for (ii = 0; ii < 5; ii++) {
						buffer[ii] = bufferTmp1[kk];
						kk = kk + 1;
					}
					kk = 0;
					for (; ii < 5 + bufferTmp2.length; ii++) {
						buffer[ii] = bufferTmp2[kk];
						kk = kk + 1;
					}

					ByteBuffer bb = ByteBuffer.allocate(2 + len);
					bb.put((byte) head).put((byte) len);
					for (i = 0; i < len; i++) {
						// bb.put((byte) read());
						bb.put((byte) buffer[i + 2]);
					}

					strData = "get result "
							+ Utility.bytes2HexString(bb.array()) + " "
							+ VH73Device.getError(bb.array());
					wq_UdpSendData(strData + "\r\n");
					Log.d(TAG, strData);
				//	EventBus.getDefault().post(	new GetCommandResultSuccess(true));
					return bb.array();
				}

				strData = "Get Error!!!";
				wq_UdpSendData(strData + "\r\n");
				buffer = new byte[5];
				buffer[0] = (byte) 0xF4;
				buffer[1] = (byte) 0x03;
				buffer[2] = (byte) 0xEE;
				buffer[3] = (byte) 0x02;
				buffer[4] = (byte) 0x19;
				return buffer;
			}
		}

	}

	public static boolean wq_UdpSendData(String strData) {
		try {
			// 1. 创建一个DatagramSocket对象

			DatagramSocket socket = new DatagramSocket(8888);

			// 2. 创建一个 InetAddress ， 相当于是地址

			// InetAddress serverAddress =
			// InetAddress.getByName("想要发送到的那个IP地址");
			InetAddress serverAddress = InetAddress.getByName("192.168.0.71");

			// 3. 这是随意发送一个数据

			String str = "hello";
			str = strData;

			// 4. 转为byte类型

			byte data[] = str.getBytes();

			// 5. 创建一个DatagramPacket 对象，并指定要讲这个数据包发送到网络当中的哪个地址，以及端口号

			DatagramPacket sendPacket = new DatagramPacket(data, data.length,
					serverAddress, 8888);

			// 6. 调用DatagramSocket对象的send方法 发送数据

			socket.send(sendPacket);

			socket.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();

		}

		return false;
	}

	public static boolean checkSucc(byte[] data) {
		if (data[0] == Head.RECEIVE_FAIL.getCode())// 0xF4
			return false;
		return true;
	}

	public static String getError(byte[] data) {
		if (!checkSucc(data)) {
			return errMessage(data[4]);
		}

		return "Success";
	}

	public static ListTagIDResult parseListTagIDResult(byte[] ret) {
		byte[] data = getDataSegment(ret);
		int size = data[0];
		int len = 0;
		if (size > 8) {
			len = 8;
		} else {
			len = size;
		}

		int index = 1;
		ArrayList<byte[]> epcs = new ArrayList<byte[]>();
		for (int i = 0; i < len; i++) {
			int ecpLen = data[index];
			byte[] epc = new byte[ecpLen * 2];
			System.arraycopy(data, index + 1, epc, 0, ecpLen * 2);
			epcs.add(epc);
			index = index + (ecpLen * 2) + 1;
			Log.i(TAG, "get epc " + Utility.bytes2HexString(epc) + " " + index
					+ " " + ecpLen);
		}

		return new ListTagIDResult(size, epcs);
	}

	public static class ListTagIDResult {
		public int totalSize;
		public ArrayList<byte[]> epcs;

		public ListTagIDResult(int totalSize, ArrayList<byte[]> epcs) {
			this.totalSize = totalSize;
			this.epcs = epcs;
		}

	}

	public void ReadWordBlock(String epc, int mem, int addr, int lenData,
			String passwd) throws IOException {
		int epcLen = epc.length() / 2;
		int paramSize = 1 + epcLen + 1 + 1 + 1 + 4;
		byte[] param = new byte[paramSize];

		byte[] bepc = Utility.convert2HexArray(epc);
		param[0] = (byte) (epcLen / 2);
		System.arraycopy(bepc, 0, param, 1, epcLen); // epc

		param[1 + epcLen] = (byte) mem;
		param[1 + epcLen + 1] = (byte) addr;
		param[1 + epcLen + 2] = (byte) lenData;

		byte[] bpasswd = Utility.convert2HexArray(passwd);
		param[4 + epcLen] = bpasswd[0];
		param[4 + epcLen + 1] = bpasswd[1];
		param[4 + epcLen + 2] = bpasswd[2];
		param[4 + epcLen + 3] = bpasswd[3];

		sendCommand(generateCMD(CommandCode.readWordBlock, param));
	}

	public static byte[] parseReadWordBlockResult(byte[] ret) {
		return getDataSegment(ret);
	}

	public void WriteWordBlock(String epc, int mem, int addr, String data,
			String passwd) throws IOException {
		// L EPC mem addr len data AccessPassword
		// 1 12 1 1 1 len 4
		int epcLen = epc.length() / 2;
		int dataLen = data.length() / 2;
		int paramSize = 1 + epcLen + 1 + 1 + 1 + dataLen + 4;
		byte[] param = new byte[paramSize];

		byte[] bepc = Utility.convert2HexArray(epc);
		param[0] = (byte) (epcLen / 2);
		System.arraycopy(bepc, 0, param, 1, epcLen); // epc

		param[1 + epcLen] = (byte) mem;
		param[1 + epcLen + 1] = (byte) addr;

		byte[] bData = Utility.convert2HexArray(data);
		param[1 + epcLen + 2] = (byte) (dataLen / 2);
		System.arraycopy(bData, 0, param, 1 + epcLen + 2 + 1, dataLen);

		byte[] bpasswd = Utility.convert2HexArray(passwd);
		param[4 + epcLen + dataLen] = bpasswd[0];
		param[4 + epcLen + dataLen + 1] = bpasswd[1];
		param[4 + epcLen + dataLen + 2] = bpasswd[2];
		param[4 + epcLen + dataLen + 3] = bpasswd[3];

		sendCommand(generateCMD(CommandCode.writeWordBlock, param));
	}

	public void EraseBlock(String epc, int mem, int addr, int len)
			throws IOException {
		int lenEpc = epc.length() / 2; // 12
		int paramSize = 1 + lenEpc + 1 + 1 + 1; // 16
		byte[] param = new byte[paramSize];

		param[0] = (byte) (lenEpc / 2); // 0
		System.arraycopy(Utility.convert2HexArray(epc), 0, param, 1, lenEpc); // 1-13

		param[1 + lenEpc] = (byte) mem; // 14
		param[2 + lenEpc] = (byte) len; // 15

		sendCommand(generateCMD(CommandCode.eraseBlock, param));
	}

	public void KillTag(String epc, String passwd) throws IOException {
		int len = epc.length() / 2; // 12
		int lenPasswd = passwd.length() / 2; // 4
		int paramSize = 1 + len + lenPasswd; // 17

		byte[] param = new byte[paramSize];
		param[0] = (byte) (len / 2); // 0

		System.arraycopy(Utility.convert2HexArray(epc), 0, param, 1, len); // 1
																			// -
																			// 12
		System.arraycopy(Utility.convert2HexArray(passwd), 0, param, 1 + len,
				lenPasswd); // 13-16

		sendCommand(generateCMD(CommandCode.killTag, param));
	}

	public void SetLock(String epc, int mem, int lock, String passwd)
			throws IOException {
		int len = epc.length() / 2; // 12
		int lenPasswd = passwd.length() / 2; // 4
		// L EPC mem Lock AccessPassword
		int paramSize = 1 + len + 1 + 1 + lenPasswd; // 19
		byte[] param = new byte[paramSize];

		// epc
		param[0] = (byte) (len / 2); // 0
		System.arraycopy(Utility.convert2HexArray(epc), 0, param, 1, len); // 1-12

		// mem
		param[1 + len] = (byte) mem; // 13
		// lock
		param[2 + len] = (byte) lock; // 14
		// passwd
		System.arraycopy(Utility.convert2HexArray(passwd), 0, param, 3 + len,
				lenPasswd); // 15-18

		sendCommand(generateCMD(CommandCode.setLock, param));

	}

	public void BlockLock(String epc, String passwd) throws IOException {
		int len = epc.length() / 2; // 12
		int lenPasswd = passwd.length() / 2; // 4
		int paramSize = 1 + len + lenPasswd; // 17

		byte[] param = new byte[paramSize];
		param[0] = (byte) (len / 2); // 0

		System.arraycopy(Utility.convert2HexArray(epc), 0, param, 1, len); // 1
																			// -
																			// 12
		System.arraycopy(Utility.convert2HexArray(passwd), 0, param, 1 + len,
				lenPasswd); // 13-16

		sendCommand(generateCMD(CommandCode.blockLock, param));
	}
}
