package ausoft.projectx2.engine;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;

public class Mesh3D {
	
	private World3D world;
	private FloatBuffer triangleVB;
	
	
	public World3D getWorld() {
		return world;
	}
	
	public Mesh3D(){
		 float triangleCoords[] = {
		            // X, Y, Z
		            -0.5f, -0.25f, 0,
		             0.5f, -0.25f, 0,
		             0.0f,  0.559016994f, 0
		        }; 
		        
		        // initialize vertex Buffer for triangle  
		        ByteBuffer vbb = ByteBuffer.allocateDirect(
		                // (# of coordinate values * 4 bytes per float)
		                triangleCoords.length * 4); 
		        vbb.order(ByteOrder.nativeOrder());// use the device hardware's native byte order
		        triangleVB = vbb.asFloatBuffer();  // create a floating point buffer from the ByteBuffer
		        triangleVB.put(triangleCoords);    // add the coordinates to the FloatBuffer
		        triangleVB.position(0);  
	}

	public void setWorld(World3D world) {
		this.world = world;
	}

	public void render(){
		
		int maPositionHandle=world.getOgl_aPositionHandle();
        
        // Prepare the triangle data
        GLES20.glVertexAttribPointer(maPositionHandle, 3, GLES20.GL_FLOAT, false, 12, triangleVB);
        GLES20.glEnableVertexAttribArray(maPositionHandle);
        
        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
	}

}
