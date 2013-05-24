package ausoft.projectx2;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.MobileAnarchy.Android.Widgets.Joystick.JoystickMovedListener;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import ausoft.projectx2.engine.AbstractMesh3D;
import ausoft.projectx2.engine.BoxMesh3D;
import ausoft.projectx2.engine.Entity3D;
import ausoft.projectx2.engine.Mesh3D;
import ausoft.projectx2.engine.SimpleObjMesh;
import ausoft.projectx2.engine.TextureCache;
import ausoft.projectx2.engine.World3D;


public class ProjectX2OpenGLES20Render implements GLSurfaceView.Renderer,JoystickMovedListener{

	private World3D world;
	private int pan;
	private int tilt;
	private Context _context;
	
	
	public ProjectX2OpenGLES20Render(Context context){
		_context=context;
	}
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		long time=SystemClock.uptimeMillis()%9000L;
		float angle=0.090f * ((int)time);
		
		world.getEntity(0).setRotationX(angle);
		
		if(Math.abs(tilt)>8){
			world.getCamera().move(-tilt/20.F);
		}
		if(Math.abs(pan)>8){
			world.getCamera().rotateY(-Math.signum(pan));
		}
		 world.renderWorld();
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub		
	    world.viewPort(width, height);
		
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		GLES20.glClearDepthf(1.0f);
	    GLES20.glDepthFunc( GLES20.GL_LEQUAL );
		GLES20.glDepthMask( true );
		// cull backface
		//GLES20.glEnable( GLES20.GL_CULL_FACE );
		//GLES20.glCullFace(GLES20.GL_BACK);
		
		
		world=new World3D(_context);
		AbstractMesh3D mesh=new SimpleObjMesh("ausoft.projectx2:drawable/trex_obj",_context.getResources());
		//mesh.setTextureId("boxtex");
		Entity3D entity=new Entity3D(world, mesh);
		entity.setPosition(0,0,-20);
		world.addEntity(entity);
		
		

		
	}

	public void OnMoved(int pan, int tilt) {
		// TODO Auto-generated method stub
		this.pan=pan;
		this.tilt=tilt;
		
	}

	public void OnReleased() {
		// TODO Auto-generated method stub
		
	}

	public void OnReturnedToCenter() {
		// TODO Auto-generated method stub
		
	}
	
	
	 

	
	

}
