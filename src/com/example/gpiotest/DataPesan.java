package com.example.gpiotest;


import java.util.Date;
import java.text.DateFormat;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;

public class DataPesan extends Activity {
	private SimpleCursorAdapter dataAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listpesan);

		displayListView();

	}

	private void displayListView() {
		Intent i = getIntent();
		Uri uriSMS = Uri
				.parse("content://sms/" + i.getStringExtra("tipepesan"));
		Cursor cursor = getContentResolver().query(uriSMS, null, null, null,
				null);

		String[] columns = new String[] { "address", "body", "date" };

		int[] to = new int[] { R.id.pengirim, R.id.isipesan, R.id.waktu };

		dataAdapter = new SimpleCursorAdapter(this, R.layout.pesan_row, cursor,
				columns, to, 0);

		ListView listView = (ListView) findViewById(R.id.listView1);

		dataAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
			@Override
			public boolean setViewValue(View view, Cursor cursor,
					int columnIndex) {

				// ubah nomer hape dengan nama yang ada dikontak
				if (columnIndex == 2) {
					TextView tv = (TextView) view;
					String pengirimDB = cursor.getString(cursor
							.getColumnIndex("address"));
					// get contact name
					Uri contactUri = Uri.withAppendedPath(
							ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
							Uri.encode(pengirimDB));
					Cursor cur = getContentResolver().query(contactUri, null,
							null, null, null);
					ContentResolver contect_resolver = getContentResolver();

					int size = cur.getCount();
					if (size > 0 && cur != null) {
						for (int i = 0; i < size; i++) {
							cur.moveToPosition(i);

							String id1 = cur.getString(cur
									.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

							Cursor phoneCur = contect_resolver
									.query(contactUri,
											null,
											ContactsContract.CommonDataKinds.Phone.CONTACT_ID
													+ " = ?",
											new String[] { id1 }, null);

							if (phoneCur.moveToFirst()) {
								String namaKontak = phoneCur.getString(phoneCur
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
								phoneCur.close();
								tv.setText(namaKontak);
							} else {
								tv.setText(pengirimDB);
							}

						}

						cur.close();
					} else {
						tv.setText(pengirimDB);
					}

					return true;
				}

				// konversi tanggal
				if (columnIndex == 4) {
					TextView tv = (TextView) view;
					String waktu = cursor.getString(cursor
							.getColumnIndex("date"));
					long l = Long.parseLong(waktu);
					Date d = new Date(l);
					String date = DateFormat.getDateInstance(DateFormat.LONG)
							.format(d);
					String time = DateFormat.getTimeInstance().format(d);
					String view_waktu = date + " " + time;

					tv.setText(view_waktu);

					return true;
				}

				return false;
			}
		});

		// menampilkan daftar pesan
		listView.setAdapter(dataAdapter);

		// jika di pesan di klik, maka akan dialihkan ke lihat pesan secara full
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> listView, View view,
					int position, long id) {
				// Get the cursor, positioned to the corresponding row in the
				// result set
				Cursor cursor = (Cursor) listView.getItemAtPosition(position);

				// Get the state's capital from this row in the database.
				String view_pengirim = cursor.getString(cursor
						.getColumnIndexOrThrow("address"));
				String view_isipesan = cursor.getString(cursor
						.getColumnIndexOrThrow("body"));

				String waktu = cursor.getString(cursor
						.getColumnIndexOrThrow("date"));

				// konversi tanggal
				long l = Long.parseLong(waktu);
				Date d = new Date(l);
				String date = DateFormat.getDateInstance(DateFormat.LONG)
						.format(d);
				String time = DateFormat.getTimeInstance().format(d);
				String view_waktu = date + " " + time;

				String view_idpesan = cursor.getString(cursor
						.getColumnIndexOrThrow("_id"));
				String view_thread = cursor.getString(cursor
						.getColumnIndexOrThrow("thread_id"));
			//	Intent click = new Intent(DataPesan.this, LihatPesan.class);

				// get contact name
				Uri contactUri = Uri.withAppendedPath(
						ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
						Uri.encode(view_pengirim));
				Cursor cur = getContentResolver().query(contactUri, null, null,
						null, null);
				ContentResolver contect_resolver = getContentResolver();

				int size = cur.getCount();
				if (size > 0 && cur != null) {
					for (int i = 0; i < size; i++) {
						cur.moveToPosition(i);

						String id1 = cur.getString(cur
								.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

						Cursor phoneCur = contect_resolver
								.query(contactUri,
										null,
										ContactsContract.CommonDataKinds.Phone.CONTACT_ID
												+ " = ?", new String[] { id1 },
										null);

						if (phoneCur.moveToFirst()) {
							String namaKontak = phoneCur.getString(phoneCur
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
							phoneCur.close();
					//		click.putExtra("no", namaKontak);
						} else {
				//			click.putExtra("no", view_pengirim);
						}

					}

					cur.close();
				} else {
			//		click.putExtra("no", view_pengirim);
				}

				// kirim data ke view pesan
			//	click.putExtra("msg", view_isipesan);
		//		click.putExtra("idpesan", view_idpesan);
		//		click.putExtra("idthread", view_thread);
		//		click.putExtra("date", view_waktu);
		//		Intent i = getIntent();
		//		click.putExtra("asal", i.getStringExtra("tipepesan"));
		//		startActivity(click);

			}
		});

	}

	@Override
	public void onBackPressed() {
		Intent link = new Intent(DataPesan.this, mode.class);
		startActivity(link);
		finish();

	}

}
