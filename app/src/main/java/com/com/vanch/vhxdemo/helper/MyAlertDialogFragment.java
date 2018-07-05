package com.com.vanch.vhxdemo.helper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class MyAlertDialogFragment extends DialogFragment {
	OnClickListener pClickListener;
	OnClickListener nClickListener;
	int icon;
	String pTitle = "OK";
	String nTitle = "Cancel";

	public static MyAlertDialogFragment newInstance(int title) {
		MyAlertDialogFragment frag = new MyAlertDialogFragment();
		Bundle args = new Bundle();
		args.putInt("title", title);
		frag.setArguments(args);
		return frag;
	}

	public MyAlertDialogFragment setPositiveListener(OnClickListener listener) {
		this.pClickListener = listener;
		return this;
	}

	public MyAlertDialogFragment setNegtiveListener(OnClickListener listener) {
		this.nClickListener = listener;
		return this;
	}

	public MyAlertDialogFragment setIcon(int id) {
		this.icon = id;
		return this;
	}

	public MyAlertDialogFragment setPositiveButton(String txt) {
		this.pTitle = txt;
		return this;
	}

	public MyAlertDialogFragment setNegativeButton(String txt) {
		this.nTitle = txt;
		return this;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		int title = getArguments().getInt("title");
		return new AlertDialog.Builder(getActivity()).setIcon(icon).setTitle(title)
				.setPositiveButton(pTitle, pClickListener)
				.setNegativeButton(nTitle, nClickListener).create();
	}
}
