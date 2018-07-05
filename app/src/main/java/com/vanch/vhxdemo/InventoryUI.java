package com.vanch.vhxdemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.com.vanch.vhxdemo.helper.Utility;
import com.lab.sodino.language.util.Strings;
import com.vanch.vhxdemo.AccessUI.StatusChangeEvent;
import com.vhxdemo.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;


public class InventoryUI extends Fragment implements OnItemLongClickListener {

	MediaPlayer findEpcSound;
	AudioManager audioManager;
	private Vibrator vibrator;
	long[] pattern = { 100, 400, 100, 400 }; // 停止 开启 停止 开启

	/**
	 * inventory terminal event
	 * 
	 * @author liugang
	 * 
	 */
	public static class InventoryTerminal {

	}

	private static final String TAG = "inventory";

	public static class TimeoutEvent {
	}

	/**
	 * an epc discovered
	 * 
	 * @author liugang
	 */
	public static class EpcInventoryEvent {
	}

	/**
	 * inventory button clicked
	 * 
	 * @author liugang
	 * 
	 */
	public static class InventoryEvent {
	}

	public static InventoryUI me;

	ListView listView;
	Button btnInventory, btnStop;
	TextView txtCount;
	ListAdapter adapter;
	List<Epc> epcs = new ArrayList<Epc>();
	Map<String, Integer> epc2num = new ConcurrentHashMap<String, Integer>();
	ImageView statusOnImageView, statusTxImageView, statusRxImageView;
	Status on = Status.ON, tx = Status.BAD, rx = Status.BAD;

	ProgressDialog progressDialog;

	boolean stoped = false;
	int readCount = 0;

	boolean inventoring = false;

	InventoryThread inventoryThread;
	Timer timer;

	public InventoryUI() {
	}

	class InventoryThread extends Thread {
		int len, addr, mem;
		Strings mask;

		public InventoryThread(int len, int addr, int mem, Strings mask) {
			this.len = len;
			this.addr = addr;
			this.mem = mem;
			this.mask = mask;
		}

		public void run() {
			try {
				LinkUi.currentDevice.listTagID(1, 0, 0);
				Log.i(TAG, "start read!!");
				LinkUi.currentDevice.getCmdResult();
				Log.i(TAG, "read ok!!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		me = this;

		progressDialog = new ProgressDialog(getActivity());
		progressDialog.setCanceledOnTouchOutside(false);

		View view = inflater.inflate(R.layout.inventory, null);
		listView = (ListView) view.findViewById(R.id.list_rfid);

		statusOnImageView = (ImageView) view.findViewById(R.id.status_on);
		statusTxImageView = (ImageView) view.findViewById(R.id.status_tx);
		statusRxImageView = (ImageView) view.findViewById(R.id.status_rx);

		btnInventory = (Button) view.findViewById(R.id.btn_inventory);
		btnInventory.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// This is a test!!!
				//if (ConfigUI.getConfigCheckTest(getActivity())) {
					// progressDialog.setMessage(("test"));
					// progressDialog.show();
				//	addEpcTest("1234");
				//	addEpcTest("1235");
				//	refreshList();
				//	txtCount.setText("" + readCount);
				//	return;
				//}

				if (LinkUi.currentDevice != null) {
					if (!inventoring) {
						inventoring = !inventoring;
						readCount = 0;
						txtCount.setText("" + readCount);
						btnInventory
								.setBackgroundResource(R.drawable.stop_btn_press);
						btnInventory.setText(Strings.getString(R.string.stop));
						clearList();
						//EventBus.getDefault().post(new InventoryEvent());
						setRx(Status.ON);
						// progressDialog.setMessage(Strings.getString(R.string.inventory)+"......");
						// progressDialog.show();
					} else {
						inventoring = !inventoring;
						// setRx(Status.OFF);
						// btnInventory.setBackgroundResource(R.drawable.inventory_btn_press);
						// btnInventory.setText(Strings.getString(R.string.inventory));

						progressDialog.setMessage(Strings
								.getString(R.string.msg_inventory_stoping));
						;
						progressDialog.show();
					}
				} else {
					Utility.WarningAlertDialg(getActivity(),
							Strings.getString(R.string.msg_waring),
							Strings.getString(R.string.msg_device_not_connect))
							.show();
				}
			}
		});

		txtCount = (TextView) view.findViewById(R.id.txt_count);
		listView.setOnItemLongClickListener(this);

		updateLang();

		findEpcSound = new MediaPlayer();
		// mediaPlayer01.start();
		audioManager = (AudioManager) getActivity().getSystemService(
				Context.AUDIO_SERVICE);

		vibrator = (Vibrator) getActivity().getSystemService(
				Context.VIBRATOR_SERVICE);

		// vibrator.vibrate(pattern,-1); //重复两次上面的pattern 如果只想震动一次，index设为-1

		return view;
	}

	private void playFindEpcSound() {
		Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

		try {
			findEpcSound.setDataSource(getActivity(), alert);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
			findEpcSound.setAudioStreamType(AudioManager.STREAM_ALARM);
			findEpcSound.setLooping(false);
			try {
				findEpcSound.prepare();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			findEpcSound.start();
		}
	}

	private void shock() {
		vibrator.vibrate(pattern, -1);
	}

	private void freshStatus() {
		Map<ImageView, Status> map = new HashMap<ImageView, Status>();
		map.put(statusOnImageView, on);
		map.put(statusTxImageView, tx);
		map.put(statusRxImageView, rx);

		for (ImageView imageView : map.keySet()) {
			Status status = map.get(imageView);
			switch (status) {
			case ON:
				imageView.setImageResource(R.drawable.ic_on);
				break;
			// case OFF:
			// imageView.setImageResource(R.drawable.ic_off);
			// break;
			case BAD:
				imageView.setImageResource(R.drawable.ic_unnormal);
				break;
			case INCOMPLETE:
				imageView.setImageResource(R.drawable.ic_unnormal);
				break;
			default:
				break;
			}
		}
	}

	public Status getOn() {
		return on;
	}

	public void setOn(Status on) {
		this.on = on;
		//EventBus.getDefault().post(new StatusChangeEvent());
	}

	public Status getTx() {
		return tx;
	}

	public void setTx(Status tx) {
		this.tx = tx;
		//EventBus.getDefault().post(new StatusChangeEvent());
	}

	public Status getRx() {
		return rx;
	}

	public void setRx(Status rx) {
		this.rx = rx;
		//EventBus.getDefault().post(new StatusChangeEvent());
	}

	/**
	 * clear id list
	 */
	private void clearList() {
		if (epc2num != null && epc2num.size() > 0) {
			epc2num.clear();
			refreshList();
		}
	}

	/**
	 * 响应inventory按钮
	 * 
	 * @param e
	 */
	public void onEventBackgroundThread(InventoryEvent e) {
		// 1.因为要跟新的VH75的一致，所以要加0B命令
		// 设置手机进入读写器模式，即模块电源打开，1--打开，0--关闭
		int i = 0;
		// while (i < 2) {
			try {
				LinkUi.currentDevice.SetReaderMode((byte) 1);
				byte[] res = LinkUi.currentDevice.getCmdResultWithTimeout(3000);
				if (!VH73Device.checkSucc(res)) {
					// TODO show error message
				// if (i > ) {
						inventoring = false;
						//EventBus.getDefault().post(new InventoryTerminal());
						return;
				// }
				// i++;
				// continue;
				// } else {
				// break;
				}

			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			} catch (TimeoutException e1) { // timeout
				Log.i(TAG, "Timeout!!@");
			}

		// }
		//

		while (inventoring) {
			// if (inventoring) {//this is a test!!!
			long lnow = android.os.SystemClock.uptimeMillis(); // 起始时间

			doInventory();

			while (true) {
				long lnew = android.os.SystemClock.uptimeMillis(); // 结束时间
				if (lnew - lnow > 500) {
					break;
				}
			}
		}
		//EventBus.getDefault().post(new InventoryTerminal());

		// 片断code开始 try { // 1.因为要跟新的VH75的一致，所以要加0B命令 //
		// 设置手机进入读写器模式，即模块电源打开，1--打开，0--关闭
		try {
			LinkUi.currentDevice.SetReaderMode((byte) 1);
			byte[] ret = LinkUi.currentDevice.getCmdResultWithTimeout(3000);
			if (!VH73Device.checkSucc(ret)) { // TODO show error message //
				Log.i(TAG, "SetReaderMode Fail!"); // return;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} catch (TimeoutException e1) { // timeout
			Log.i(TAG, "Timeout!!@");
		}
		// 片断code结束

	}

	private void doInventory() {
		try {

			LinkUi.currentDevice.listTagID(1, 0, 0);
			byte[] ret = LinkUi.currentDevice.getCmdResultWithTimeout(3000);
			if (!VH73Device.checkSucc(ret)) {
				// TODO show error message
				return;
			}
			VH73Device.ListTagIDResult listTagIDResult = VH73Device
					.parseListTagIDResult(ret);
			addEpc(listTagIDResult);
			//EventBus.getDefault().post(new EpcInventoryEvent());

			// read the left id
			int left = listTagIDResult.totalSize - 8;
			while (left > 0) {
				if (left >= 8) {
					LinkUi.currentDevice.getListTagID(8, 8);
					left -= 8;
				} else {
					LinkUi.currentDevice.getListTagID(8, left);
					left = 0;
				}
				byte[] retLeft = LinkUi.currentDevice
						.getCmdResultWithTimeout(3000);
				if (!VH73Device.checkSucc(retLeft)) {
					Utility.showTostInNonUIThread(getActivity(),
							Strings.getString(R.string.msg_command_fail));
					continue;
				}
				VH73Device.ListTagIDResult listTagIDResultLeft = VH73Device
						.parseGetListTagIDResult(retLeft);
				addEpc(listTagIDResultLeft);
			}
			// EventBus.getDefault().post(new InventoryTerminal());
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} catch (TimeoutException e1) { // timeout
			// e1.printStackTrace();
			// EventBus.getDefault().post(new TimeoutEvent());
			Log.i(TAG, "Timeout!!!");
		}
	}

	public void onEventMainThread(TimeoutEvent e) {
		progressDialog.dismiss();
		Utility.showDialogInNonUIThread(getActivity(),
				Strings.getString(R.string.msg_waring),
				Strings.getString(R.string.msg_timeout));
		inventoring = false;
		setRx(Status.BAD);
		btnInventory.setBackgroundResource(R.drawable.inventory_btn_press);
		btnInventory.setText(Strings.getString(R.string.inventory));
		//EventBus.getDefault().post(new InventoryTerminal());
	}

	/**
	 * inventory 结束
	 * 
	 * @param e
	 */
	public void onEventMainThread(InventoryTerminal e) {
		progressDialog.dismiss();
		inventoring = false;
		setRx(Status.BAD);
		btnInventory.setBackgroundResource(R.drawable.inventory_btn_press);
		btnInventory.setText(Strings.getString(R.string.inventory));
	}

	private void addEpc(VH73Device.ListTagIDResult list) {
		ArrayList<byte[]> epcs = list.epcs;
		for (byte[] bs : epcs) {
			String string = Utility.bytes2HexString(bs);
			//if (!ConfigUI.getConfigSkipsame(getActivity())) {
				if (epc2num.containsKey(string)) {
					epc2num.put(string, epc2num.get(string) + 1);
				} else {
					epc2num.put(string, 1);
				}
			//} else {
			//	epc2num.put(string, 1);
			//}
			// readCount++;
			// 改为下面表格有多少行，则为多少行显示,add by martrin 20131114
			readCount = epc2num.size();
		}
	}

	private void addEpcTest(String strEpc) {
		if (epc2num.containsKey(strEpc)) {
			epc2num.put(strEpc, epc2num.get(strEpc) + 1);
		} else {
			epc2num.put(strEpc, 1);
		}
		readCount = epc2num.size();
	}

	/**
	 * when inventory an epc, refresh the list
	 * 
	 * @param e
	 */
	public void onEventMainThread(EpcInventoryEvent e) {
		refreshList();
		txtCount.setText("" + readCount);
		//

	}

	/**
	 * 状态改变
	 * 
	 * @param e
	 */
	public void onEventMainThread(StatusChangeEvent e) {
		freshStatus();
	}



	/**
	 * 刷新列表
	 */
	private void refreshList() {
		adapter = new IdListAdaptor();
		listView.setAdapter(adapter);
		// listView.scrollTo(0, adapter.getCount());
		listView.setSelection(listView.getAdapter().getCount() - 1);
	}

	@Override
	public void onResume() {
		Log.i(TAG, "onResume");
		//EventBus.getDefault().register(this);
		refreshList();
		if (LinkUi.currentDevice != null && LinkUi.currentDevice.isConnected())
			setOn(Status.ON);
		else
			setOn(Status.BAD);
		super.onResume();
	}

	private class IdListAdaptor extends BaseAdapter {

		@Override
		public int getCount() {
			return epc2num.size();
		}

		@Override
		public Object getItem(int position) {
			String[] ids = new String[epc2num.size()];
			epc2num.keySet().toArray(ids);
			return ids[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = getActivity().getLayoutInflater();
			View view = inflater.inflate(R.layout.inventory_item_list, null);
			TextView rfidTextView = (TextView) view.findViewById(R.id.txt_rfid);
			TextView countTextView = (TextView) view
					.findViewById(R.id.txt_count_item);

			String id = (String) getItem(position);
			int count = epc2num.get(id);
			rfidTextView.setText(id);
			countTextView.setText("" + count);

			TextView textViewNoTitle = (TextView) view
					.findViewById(R.id.txt_no_title);
			textViewNoTitle.setText(Strings.getString(R.string.count_lable));
			return view;
		}
	}

	@Override
	public void onDestroy() {
		//EventBus.getDefault().unregister(this);
		findEpcSound.stop();
		findEpcSound.release();
		super.onDestroy();
	}

	@Override
	public void onPause() {
		//EventBus.getDefault().unregister(this);
		super.onPause();
	}

	@Override
	public void onStop() {
		//EventBus.getDefault().unregister(this);
		super.onStop();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		String[] ids = new String[epc2num.size()];
		epc2num.keySet().toArray(ids);
		Epc epc = new Epc(ids[position], epc2num.get(ids[position]));
		//EventBus.getDefault().postSticky(new AccessUI.EpcSelectedEvent(epc));
		Log.i(TAG, "epc selected " + epc);
		return true;
	}

	private void updateLang() {
		btnInventory.setText(Strings.getString(R.string.inventory));
		refreshList();
	}

	public void onEventMainThread(VH73Device.GetCommandResultSuccess e) {
		//if (e.isSuccess())
			//setRxWithBlink(ConfigUI.cmd_timeout, Status.ON, Status.BAD);
		//else
			//setRx(Status.BAD);
	}

	public void onEventMainThread(VH73Device.SendCommandSuccess e) {
		if (e.isSuccess()) {
		//	setTxWithBlink(ConfigUI.cmd_timeout, Status.ON, Status.BAD);
		} else {
			setTx(Status.BAD);
		}
	}

	/**
	 * tx blink
	 * 
	 * @param timeout
	 * @param from
	 * @param to
	 */
	private void setTxWithBlink(final long timeout, Status from, final Status to) {
		setTx(from);
		new Thread() {
			public void run() {
				try {
					sleep(timeout);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				new Thread() {
					public void run() {
						Looper.prepare();
						setTx(to);
					}
				}.start();
			}
		}.start();
	}

	private void setRxWithBlink(final long timeout, Status from, final Status to) {
		setRx(from);
		new Thread() {
			public void run() {
				try {
					sleep(timeout);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				new Thread() {
					public void run() {
						Looper.prepare();
						setRx(to);
					}
				}.start();
			}
		}.start();
	}
}
