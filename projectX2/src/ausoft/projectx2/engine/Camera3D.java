package ausoft.projectx2.engine;

import java.nio.FloatBuffer;

import javax.vecmath.Vector3d;

import android.opengl.Matrix;

public class Camera3D {
	
	private float[] mVMatrix=new float[16];
	private Vector3d position=new Vector3d();
	private Vector3d viewVec=new Vector3d();
	private Vector3d upVec=new Vector3d();
	private Vector3d rightVec=new Vector3d();
	private Vector3d forwardUpVec=new Vector3d();
	private float rotatedX;
	private float rotatedY;
	private float rotatedZ;
	private static double PIdiv180=Math.PI/180.0;
	
	public Camera3D(){
		position.x=0.0;
		position.y=0.0;
		position.z=5.0;
		
		viewVec.x=0.0;
		viewVec.y=0.0;
		viewVec.z=-1.0;
		
		rightVec.x=1.0;
		rightVec.y=0.0;
		rightVec.z=0.0;
		
		forwardUpVec.x=0.0;
		forwardUpVec.y=1.0;
		forwardUpVec.z=0.0;
		
		upVec.x=0.0;
		upVec.y=1.0;
		upVec.z=0.0;
		
		
		
		
	}
	
	public void setPosition(float x,float y,float z){
		position.x=x;
		position.y=y;
		position.z=z;
	}
	
	
	
	public void setLookAt(float x,float y,float z){
		viewVec.x=x;
		viewVec.y=y;
		viewVec.z=z;
	}
	public void rotateY(float stepAngle){
		rotatedY+=stepAngle;
		Vector3d tmp1=new Vector3d();
		Vector3d tmp2=new Vector3d();
		tmp1.scale(Math.cos(stepAngle*PIdiv180), viewVec);
		tmp2.scale(Math.sin(stepAngle*PIdiv180),rightVec);
		viewVec.sub(tmp1, tmp2);
		viewVec.normalize();
		
		rightVec.cross(viewVec, upVec);		
		
	}
	public void move(float distance){
		Vector3d vforward=new Vector3d();
		vforward.cross(forwardUpVec, rightVec);
		vforward.normalize();
		
		vforward.scale(distance);
		position.add(vforward);
		//forwardUpVec.cr
		///position.x+=x;x
		//position.y+=y;
		//position.z+=z;
	}
	
	public void strafe(float steps){
		//position.add(rightVec.scale((float)steps);
		position.x=position.x+rightVec.x*steps;
		position.y=position.y+rightVec.y*steps;
		position.z=position.z+rightVec.z*steps;
	}
	
	
	float[] getViewMatrix(){
		//System.out.println(""+ (float)position.x+ " "+ (float)position.y+ " "+(float)position.z+ " "+ (float)viewVec.x+ " "+ (float)viewVec.y+ " "+ (float)viewVec.z+ " "+(float)upVec.x+ " "+ (float)upVec.y+ " "+ (float)upVec.z);
		Vector3d viewPoint=new Vector3d();
		viewPoint.add(position, this.viewVec);
		Matrix.setLookAtM(mVMatrix, 0, (float)position.x, (float)position.y, (float)position.z, (float)viewPoint.x, (float)viewPoint.y, (float)viewPoint.z, (float)upVec.x, (float)upVec.y, (float)upVec.z);
		//Matrix.setLookAtM(mVMatrix, 0, 0, 0, 4, 0f, 0f, 0f, 0f,1.0f, 0.0f);
		//Matrix.setLookAtM(mVMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
		//Matrix.setLookAtM(rm, rmOffset, eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ)
		return mVMatrix;
		//javax.
		//Matrix.
	}

}
