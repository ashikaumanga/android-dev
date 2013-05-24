package ausoft.projectx2;

import com.MobileAnarchy.Android.Widgets.Joystick.JoystickView;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class ProjectX2Activity extends Activity {
    /** Called when the activity is first created. */
	
	private GLSurfaceView glSurfaceView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        ProjectX2OpenGLES20Render renderer=new ProjectX2OpenGLES20Render(this);
        
        glSurfaceView=new ProjectX2OpenGL20SurfaceView(this,renderer);
       // setContentView(glSurfaceView);
        
        FrameLayout flayout=new FrameLayout(this);      
         flayout.addView(glSurfaceView);
         
         //View controller=findViewById(R.id.viewControls);
         JoystickView jview=new JoystickView(this);
         jview.setX(200);
         jview.setY(200);
         jview.setId(26461);
         jview.setScaleX(0.2f);
         jview.setScaleY(0.2f);
         jview.setOnJostickMovedListener(renderer);
        
        // jview.set
         
         flayout.addView(jview);        
         //findViewById(p)

         setContentView(flayout);
    }
    
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	glSurfaceView.onPause();
    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	glSurfaceView.onResume();
    }
    
    
    
}

class ProjectX2OpenGL20SurfaceView extends GLSurfaceView {

	public ProjectX2OpenGL20SurfaceView(Context context,ProjectX2OpenGLES20Render renderer) {
		super(context);
		setEGLContextClientVersion(2);
		setRenderer(renderer);
	}
	
}