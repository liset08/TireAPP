package com.vanch.vhxdemo;

/**
 * 
 * @author Administrator
 */
public enum CommandCode {
	SaveLabelToSDCard((byte) 0x01),
	GetVersion((byte) 0x02), 
	AddLableID((byte) 0x03), 
	DelLableID((byte) 0x04), 
	GetLableID((byte) 0x05), 
	SetReportFilter((byte) 0x07), 
	GetReportFilter((byte) 0x08), 
	ReadHandsetParam((byte) 0x06), 
	WriteHanderParam((byte) 0x09),
	SetReaderMode((byte) 0x0b),  //手持机进入读写器模式，即：模块电源强制打开，该参数不保存，数据1字节，1：打开，0：关闭，建议Demo上相关界面，在打开使能命令，反之变灰无效
	WriteFactoryParam((byte) 0x0c), 
	ReadFactoryParameter((byte) 0x0d), 
	SetReaderTime((byte) 0x11), 
	GetReaderTime((byte) 0x12), 
	GetRecord((byte) 0x16), 
	DeleteAllRecord((byte) 0x17), 
	SetBtBaudRate((byte) 0x8f), 
	GetBtBaudRate((byte) 0x90), 
	GetHandsetID((byte) 0x8c), 
	SetHandsetID((byte) 0x8b), 
	GetBluetoothName((byte) 0x8e), 
	SetBluetoothName((byte) 0x8d), 
	readWordBlock((byte) 0xec), 
	writeWordBlock((byte) 0xeb), 
	setLock((byte) 0xea), 
	eraseBlock((byte) 0xe9), 
	killTag((byte) 0xe8), 
	writeEpc((byte) 0xe7), 
	blockLock((byte) 0xe6), 
	listTag((byte) 0xee), 
	getIdList((byte) 0xed), 
	InvalidCode((byte) -1);

	byte code;

	/**
	 * 返回命令码
	 * 
	 * @return
	 */
	public byte getCode() {
		return code;
	}

	private CommandCode(byte code) {
		this.code = code;
	}

	/**
	 * 根据命令码返回实例
	 * 
	 * @param code
	 *          命令码
	 * @return
	 */
	public static CommandCode getInstance(byte code) {
		
		CommandCode[] codes = CommandCode.values();
		for (CommandCode commandCode : codes) {
			if (commandCode.getCode() == code) {
				return commandCode;
			}
		}
		
		return InvalidCode;
		
		
//		if (code == SaveLabelToSDCard.getCode()) {
//			return SaveLabelToSDCard;
//		} else if (code == GetVersion.getCode()) {
//			return GetVersion;
//		} else if (code == AddLableID.getCode()) {
//			return AddLableID;
//		} else if (code == DelLableID.getCode()) {
//			return DelLableID;
//		} else if (code == GetLableID.getCode()) {
//			return GetLableID;
//		} else if (code == SetReportFilter.getCode()) {
//			return SetReportFilter;
//		} else if (code == GetReportFilter.getCode()) {
//			return GetReportFilter;
//		} else if (code == ReadHandsetParam.getCode()) {
//			return ReadHandsetParam;
//		} else if (code == WriteHanderParam.getCode()) {
//			return WriteHanderParam;
//		} else if (code == WriteFactoryParam.getCode()) {
//			return WriteFactoryParam;
//		} else if (code == ReadFactoryParameter.getCode()) {
//			return ReadFactoryParameter;
//		} else if (code == SetReaderTime.getCode()) {
//			return SetReaderTime;
//		} else if (code == GetReaderTime.getCode()) {
//			return GetReaderTime;
//		} else if (code == GetRecord.getCode()) {
//			return GetRecord;
//		} else if (code == DeleteAllRecord.getCode()) {
//			return DeleteAllRecord;
//		} else if (code == SetBtBaudRate.getCode()) {
//			return SetBtBaudRate;
//		} else if (code == GetBtBaudRate.getCode()) {
//			return GetBtBaudRate;
//		} else if (code == SetBluetoothName.getCode()) {
//			return SetBluetoothName;
//		} else if (code == GetHandsetID.getCode()) {
//			return GetHandsetID;
//		} else if (code == SetHandsetID.getCode()) {
//			return SetHandsetID;
//		} else if (code == GetBluetoothName.getCode()) {
//			return GetBluetoothName;
//		} else {
//			return InvalidCode;
//		}
	}
}
