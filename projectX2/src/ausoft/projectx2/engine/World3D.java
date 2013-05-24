package ausoft.projectx2.engine;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ausoft.projectx2.R;
import android.content.Context;
import android.graphics.Camera;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

public class World3D {
	private List<Entity3D> objects3d;
	private Map<Integer,Light> lights;
	private Camera3D _defaultCamera;
	private Context _context;
	private float[] mMVPMatrix=new float[16];
	private float[] mMVMatrix=new float[16];
	private float[] mProjMatrix=new float[16];
	private float[] mViewMatrix=new float[16];
	private float[] mTmpMatrix=new float[16];
	int oglShaderProgram;
	public int getOgl_aPositionHandle() {
		return ogl_aPositionHandle;
	}

	public void setOgl_aPositionHandle(int ogl_aPositionHandle) {
		this.ogl_aPositionHandle = ogl_aPositionHandle;
	}

	public int getOgl_uMVPMatrixHandle() {
		return ogl_uMVPMatrixHandle;
	}

	public void setOgl_uMVPMatrixHandle(int ogl_uMVPMatrixHandle) {
		this.ogl_uMVPMatrixHandle = ogl_uMVPMatrixHandle;
	}

	public int getOgl_uMVMatrixHandle() {
		return ogl_uMVMatrixHandle;
	}

	public void setOgl_uMVMatrixHandle(int ogl_uMVMatrixHandle) {
		this.ogl_uMVMatrixHandle = ogl_uMVMatrixHandle;
	}

	public int getOgl_aNormalHandle() {
		return ogl_aNormalHandle;
	}

	public void setOgl_aNormalHandle(int ogl_aNormalHandle) {
		this.ogl_aNormalHandle = ogl_aNormalHandle;
	}

	int ogl_aPositionHandle;
	int ogl_uMVPMatrixHandle;
	int ogl_uMVMatrixHandle;
	int ogl_uTexCoord;
	public int getOgl_uTexCoord() {
		return ogl_uTexCoord;
	}

	public void setOgl_uTexCoord(int ogl_uTexCoord) {
		this.ogl_uTexCoord = ogl_uTexCoord;
	}

	int ogl_aNormalHandle;
	int ogl_aTexCoordHandle;
	public int getOgl_aTexCoordHandle() {
		return ogl_aTexCoordHandle;
	}

	public void setOgl_aTexCoordHandle(int ogl_aTexCoordHandle) {
		this.ogl_aTexCoordHandle = ogl_aTexCoordHandle;
	}

	public int getOgl_uVMatrixHandle() {
		return ogl_uVMatrixHandle;
	}

	public void setOgl_uVMatrixHandle(int ogl_uVMatrixHandle) {
		this.ogl_uVMatrixHandle = ogl_uVMatrixHandle;
	}

	int ogl_uVMatrixHandle;
	//private Camera
	
	
	private int loaderShader(int type,String shaderCode)
	{
		int shader=GLES20.glCreateShader(type);
		GLES20.glShaderSource(shader, shaderCode);
		GLES20.glCompileShader(shader);
		if(shader==0){
		Log.e("tag", "ERROR CREATING SHADER "+GLES20.glGetError());
			//System.err.println("Error creating shader!");
		}
		return shader;
	}
	
	Light getLightForced(int index){
		Light ret=null;
		if(lights.containsKey(index)){
			ret=lights.get(index);
		}else{
			ret=new Light();
			lights.put(index, ret);
		}
		return ret;
	}
	Light getLight(int index){
		return lights.get(index);
	}
	
	String loadShaderScript(int id){
		InputStream in=_context.getResources().openRawResource(id);
		

        final InputStreamReader inputStreamReader = new InputStreamReader(
                in);
        final BufferedReader bufferedReader = new BufferedReader(
                inputStreamReader);
 
        String nextLine;
        final StringBuilder body = new StringBuilder();
 
        try
        {
            while ((nextLine = bufferedReader.readLine()) != null)
            {
                body.append(nextLine);
                body.append('\n');
            }
        }
        catch (Exception e)
        {
            return null;
        }
 
        return body.toString();
		
		
		//return body.toString();
		
		
		//return ret;
	}
	
	public World3D(Context context){
		this._context=context;		
		_defaultCamera=new Camera3D();
		objects3d=new ArrayList<Entity3D>();
		lights=new HashMap<Integer,Light>();
		
		  final String vertexShader =loadShaderScript(R.raw.vertex);
		  

		  
		  Log.i("info", vertexShader);
		  
		  final String fragmentShader =loadShaderScript(R.raw.fragment);
		  
		  Log.i("info", fragmentShader);
				


		
		 
		  
		        
		     
		     int verShader= loaderShader(GLES20.GL_VERTEX_SHADER, vertexShader);
			 int fragShader=loaderShader(GLES20.GL_FRAGMENT_SHADER, fragmentShader);
			 if(verShader==0){
				 Log.e("err", "Error Vertex Shader");
			 }
			 
			 if(fragShader==0){
				 Log.e("err", "Error Fragment Shader");
			 }
				
				oglShaderProgram=GLES20.glCreateProgram();
				GLES20.glAttachShader(oglShaderProgram, verShader);
				GLES20.glAttachShader(oglShaderProgram, fragShader);
				GLES20.glLinkProgram(oglShaderProgram);
				
				if(oglShaderProgram==0){
					Log.e("shader", "shader program error!");
				}
				Log.i("glerror0","GLERROR: "+ GLES20.glGetError());
				ogl_aPositionHandle=GLES20.glGetAttribLocation(oglShaderProgram, "aPosition");
				ogl_aTexCoordHandle=GLES20.glGetAttribLocation(oglShaderProgram, "aTexCoord");
				Log.i("glerror1","GLERROR: "+ GLES20.glGetError());
				ogl_aNormalHandle=GLES20.glGetAttribLocation(oglShaderProgram, "aNormal");
				ogl_uMVPMatrixHandle=GLES20.glGetUniformLocation(oglShaderProgram, "uMVPMatrix");
				ogl_uMVMatrixHandle=GLES20.glGetUniformLocation(oglShaderProgram, "uMVMatrix");
				ogl_uVMatrixHandle=GLES20.glGetUniformLocation(oglShaderProgram, "uVMatrix");
				ogl_uTexCoord=GLES20.glGetUniformLocation(oglShaderProgram, "uTexture");
				Log.i("glerror","GLERROR: "+ GLES20.glGetError());
				Log.i("shaderHandle", "ogl_aPositionHandle:"+ogl_aPositionHandle);
				Log.i("shaderHandle", "ogl_uMVPMatrixHandle:"+ogl_uMVPMatrixHandle);
				Log.i("shaderHandle", "ogl_aNormalHandle:"+ogl_aNormalHandle);
				
				
				
				
	}
	

	
	public void addEntity(Entity3D entity){
		//obj.setWorld(this);
		objects3d.add(entity);
	}
	
	public Entity3D getEntity(int ind){
		return objects3d.get(ind);
	}
	
	public void renderWorld(){
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT| GLES20.GL_DEPTH_BUFFER_BIT);
		
	
		GLES20.glUseProgram(oglShaderProgram);		
	
		Matrix.setIdentityM(mTmpMatrix, 0);

		Matrix.multiplyMM(mTmpMatrix, 0, mProjMatrix, 0,_defaultCamera.getViewMatrix(), 0);
		
		float tmpMVMatrix[]=new float[16];
		float tmpMVPMatrix[]=new float[16];
		float tmpVPMatrix[]=new float[16];
		
		Matrix.setIdentityM(tmpVPMatrix, 0);
		Matrix.multiplyMM(tmpVPMatrix, 0, mProjMatrix, 0, _defaultCamera.getViewMatrix(), 0);
		
		for(Entity3D ent:objects3d){
					
			Matrix.setIdentityM(tmpMVPMatrix, 0);
			Matrix.setIdentityM(tmpMVMatrix, 0);
			
			float entMatrix[]=ent.getModelMatrix();
			Matrix.multiplyMM(tmpMVMatrix, 0, _defaultCamera.getViewMatrix(), 0, entMatrix, 0);
			Matrix.multiplyMM(tmpMVPMatrix, 0, tmpVPMatrix, 0, entMatrix, 0);
			GLES20.glUniformMatrix4fv(ogl_uMVMatrixHandle, 1, false,tmpMVMatrix, 0);
			GLES20.glUniformMatrix4fv(ogl_uMVPMatrixHandle, 1, false, tmpMVPMatrix, 0);
			ent.render();
			
		}


		//GLES20.glUniformMatrix4fv(ogl_uVMatrixHandle, 1, false, _defaultCamera.getViewMatrix(),0);
		//GLES20.glGetAttribLocation(oglShaderProgram, "aNormal");
		
		/**
		for(Entity3D ent:objects3d){
			
			Matrix.setIdentityM(mMVPMatrix, 0);
			Matrix.setIdentityM(mMVMatrix, 0);
			float entMMatrix[]=ent.getModelMatrix();
			
			Matrix.multiplyMM(mMVMatrix, 0, _defaultCamera.getViewMatrix(), 0, entMMatrix, 0);			
			Matrix.multiplyMM(mMVPMatrix, 0, mTmpMatrix, 0, entMMatrix, 0);
			

			GLES20.glUniformMatrix4fv(ogl_uMVMatrixHandle, 1, false, mMVMatrix,0);
			GLES20.glUniformMatrix4fv(ogl_uMVPMatrixHandle, 1, false, mMVPMatrix,0);
			//GLES20.glUniformM
			
			ent.render();
			
			//System.out.println("Rendering");		
			
		}
		**/
		
		
		
		
		
		//Matrix.ro
		
		
		
		
		//renderObjects();
	}
	
	public Camera3D getCamera(){
		return _defaultCamera;
	}
	
	
	public void viewPort(int width,int height){
		
		GLES20.glViewport(0, 0, width, height);
		float ratio=(float)width/height;
		Matrix.frustumM(mProjMatrix, 0, -ratio, ratio, -1, 1,3, 100);
		
	}
	
	public void lookAt(float left,float right,float top,float bottom,float rear,float end)
	{
		//Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 4, 0f, 0f, 0f, 0f,1.0f, 0.0f);
		mViewMatrix=_defaultCamera.getViewMatrix();
		
	}

}
