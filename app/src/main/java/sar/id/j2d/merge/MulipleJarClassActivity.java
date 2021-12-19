package sar.id.j2d.merge;

import android.app.Activity;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;
import org.json.*;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.DialogFragment;
import android.Manifest;
import android.content.pm.PackageManager;

public class MulipleJarClassActivity extends Activity {
	
	private String input = "";
	private String out = "";
	private String minApi = "";
	private String d8lib = "";
	private String libs = "";
	private String outter = "";
	private String classp = "";
	private String rtssd = "";
	
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private TextView log1;
	private EditText edittext1;
	private EditText edittext2;
	private EditText edittext3;
	private Button button1;
	private TextView textview2;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.muliple_jar_class);
		initialize(_savedInstanceState);
		
		if (Build.VERSION.SDK_INT >= 23) {
			if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
				requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
			} else {
				initializeLogic();
			}
		} else {
			initializeLogic();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		linear2 = findViewById(R.id.linear2);
		linear3 = findViewById(R.id.linear3);
		linear4 = findViewById(R.id.linear4);
		linear5 = findViewById(R.id.linear5);
		log1 = findViewById(R.id.log1);
		edittext1 = findViewById(R.id.edittext1);
		edittext2 = findViewById(R.id.edittext2);
		edittext3 = findViewById(R.id.edittext3);
		button1 = findViewById(R.id.button1);
		textview2 = findViewById(R.id.textview2);
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				input = edittext1.getText().toString();
				out = input.substring(input.lastIndexOf("/")+1);
				outter = input.replace(out, "");
				minApi = edittext3.getText().toString();
				edittext2.setText(outter);
				new d8Task().execute("run");
			}
		});
	}
	
	private void initializeLogic() {
		setTitle("Folder to dex");
		d8lib = FileUtil.getExternalStorageDir().concat("/Jar2Dex/d8s.jar");
		if (FileUtil.isExistFile(d8lib)) {
			
		}
		else {
			try{
								copyAssetFile("fonts/d8s.jar", d8lib);
						
				ApplicationUtil.showMessage(getApplicationContext(), "d8.jar copied successfully!");
			}catch (java.io.IOException e){
								log1.setText(e.toString());
					}
				
			
		}
		//check for android.jar
		if (FileUtil.isExistFile(FileUtil.getExternalStorageDir().concat("/Jar2Dex/android.jar"))) {
			libs = FileUtil.getExternalStorageDir().concat("/Jar2Dex/android.jar");
		}
		else {
			ApplicationUtil.showMessage(getApplicationContext(), "android.jar is missing please download it from my GitHub page or from Android studio");
			button1.setEnabled(false);
		}
		//check for rt.jar
		rtssd = FileUtil.getExternalStorageDir().concat("/Jar2Dex/rt.jar");
		if (FileUtil.isExistFile(rtssd)) {
			
		}
		else {
			try{
								copyAssetFile("fonts/rtjar.jar", rtssd);
							
				ApplicationUtil.showMessage(getApplicationContext(), "rt.jar copied successfully!");
			}catch (java.io.IOException e){
								log1.setText(e.toString());
					}
				
			
		}
	}
	
	public void _extra() {
	}
		private class d8Task extends AsyncTask<String, String, String>
		
		
	    {
		        ProgressDialog pd;
		        @Override
		        protected void onPreExecute()
		        {
			            pd = new ProgressDialog(MulipleJarClassActivity.this);
						pd.setTitle("Please wait");
			            pd.setMessage("D8 running...");
			            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			            pd.setCancelable(false);
						pd.setIndeterminate(true);
					    
						
			            pd.show();
						
			            
					}
		
		     
				
				
		        @Override
		        protected String doInBackground(String[] p1)
		        {
			            // add code which need to be done in background
			
					   
			            java.io.File common = new java.io.File(Environment.getExternalStorageDirectory(),"/Jar2Dex/");
			    java.io.File jar = new java.io.File(common, "/d8s.jar");
			
			//    java.io.File classpaths = new java.io.File(common, "/rt.jar");
				classp = FileUtil.getExternalStorageDir().concat("/Jar2Dex/rt.jar");
			
			List<String> cmd= new ArrayList<String>();
			
			        cmd.add("dalvikvm");
			      cmd.add("-Xcompiler-option");
			        cmd.add("--compiler-filter=" + "speed");
			        cmd.add("-Xmx512m");
			
			cmd.add("-cp");
			cmd.add(jar.toString());
			
			        cmd.add("com.android.tools.r8.D8");
			        cmd.add("--release");
			        cmd.add("--min-api");
			        cmd.add(minApi);
			        cmd.add("--lib");
			        cmd.add(libs);
			         cmd.add("--output");
			           cmd.add(outter);
			           
			    
					 cmd.add("--classpath");
			            cmd.add(classp);
			           
			
			        
			        
			     //       cmd.add("--intermediate");
			            
			            
			 
			        cmd.addAll(getSourceFile(new java.io.File(input), new ArrayList<String>()));
			  
			            
			            
			try {
				    
				    // this is for doing the execution
				   java.lang.ProcessBuilder pbs = new java.lang.ProcessBuilder(cmd); 
						java.lang.Process proces = pbs.start();
						
						
						//this below code is for writing input process
						
				java.io.BufferedReader StdInput= new java.io.BufferedReader(new java.io.InputStreamReader(proces.getInputStream()));
						String st = null;
				while ((st = StdInput.readLine()) != null) {
							    FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/Jar2Dex/d8process.txt"), st);
							    
							    } 
				
			}catch(Exception e){
				 java.io.StringWriter stringWriter = new java.io.StringWriter();
				            e.printStackTrace(new java.io.PrintWriter(stringWriter));
				log1.setText(stringWriter.toString());
				
				
			}
			
			
			
			
			return null;
		}
		      @Override
		        protected void onPostExecute(String result)
		        {
			            super.onPostExecute(result);
						Toast.makeText(MulipleJarClassActivity.this, "Completed!",
			Toast.LENGTH_LONG).show();
			            pd.dismiss();
						
			        }
		
		    
	}
	
	
	public void _source() {
	}
	
	//for listing all the files in a directory
	public ArrayList<String> getSourceFile(java.io.File file, ArrayList<String> arrayList) {
		        if (file != null) {
			            java.io.File[] files = file.listFiles();
			            if (files != null) {
				                for (java.io.File value : files) {
					                    file = value;
					                    if (file.isDirectory()) {
						                        getSourceFile(file, arrayList);
						                    } else if ((file.getName().endsWith(".jar")) || (file.getName().endsWith(".class"))) {
						                        arrayList.add(file.getAbsolutePath());
						                    }
					                }
				            }
			        }
		        return arrayList;
	}
	
	
	public void _copy_assets() {
	}
	
	public void copyAssetFile(String assetFilePath, String destinationFilePath) throws java.io.IOException {
		java.io.InputStream in = getApplicationContext().getAssets().open(assetFilePath);
		java.io.OutputStream out = new java.io.FileOutputStream(destinationFilePath);
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) out.write(buf, 0, len);
		in.close();
		out.close();
	}
	
	{
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}