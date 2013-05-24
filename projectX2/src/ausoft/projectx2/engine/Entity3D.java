package ausoft.projectx2.engine;

import android.opengl.Matrix;

public class Entity3D {
	
	private AbstractMesh3D _mesh;
	private World3D _world;
	private float[] mMMatrix;
	private float[] vecPosition=new float[3];
	private float[] vecRotation=new float[3];
	
	public Entity3D(World3D world,AbstractMesh3D mesh){
		_mesh=mesh;
		_mesh.setWorld(world);
		mMMatrix=new float[16];
		Matrix.setIdentityM(mMMatrix, 0);
		vecRotation[0]=0;
		vecRotation[1]=0;
		vecRotation[2]=0;
		vecPosition[0]=0;
		vecPosition[1]=0;
		vecPosition[2]=0;
		//Matrix.tr
	}
	
	//public void setWorld(World3D world)
	//{
		//this._world=world;
	//}
	public void setPosition(float x,float y,float z)
	{
		vecPosition[0]=x;
		vecPosition[1]=y;
		vecPosition[2]=z;
	}
	public void setRotationX(float x){
		vecRotation[0]=x;
	}
	
    public void setRotationY(float y){
		vecRotation[1]=y;
	}
   public void setRotationZ(float z){
		vecRotation[2]=z;
	}
   
   public float[] getModelMatrix(){
	   Matrix.setIdentityM(mMMatrix, 0);
	   Matrix.translateM(mMMatrix, 0,vecPosition[0] ,vecPosition[1],vecPosition[2]);
	   Matrix.rotateM(mMMatrix, 0, vecRotation[2], 0, 0, 1);
	   Matrix.rotateM(mMMatrix, 0, vecRotation[1], 0, 1, 0);
	   Matrix.rotateM(mMMatrix, 0, vecRotation[0], 1, 0, 0);
	  
	  
	   
	   return mMMatrix;
   }
   
   public void setMesh(AbstractMesh3D mesh)
   {
	   this._mesh=mesh;
   }
	
	public void render(){
		//do translation ..etc
		_mesh.render();
	}

}
