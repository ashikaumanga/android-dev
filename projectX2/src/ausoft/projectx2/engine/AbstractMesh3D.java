package ausoft.projectx2.engine;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.opengl.GLES20;

public class AbstractMesh3D {
	//private int buffers[]={0,0};
	private int vbVertices[]=new int[1];
	private int vbIndices[]=new int[1];
	private int vbNormals[]=new int[1];
	private int vbColors[]=new int[1];
	private int vbTextureCords[]=new int[1];
	private boolean bVertices=false;
	private boolean bIndices=false;
	private boolean bNormals=false;
	private boolean bTextures=false;
	private int numOfVertices;
	private int numOfIndices;
	private int numOfNormals;
	private World3D _world;
	public void setTextureId(String textureId) {
		this.textureId = textureId;
	}

	public String getTextureId(){
		return this.textureId;
	}

	private String textureId;
	
	
	public AbstractMesh3D(){
		

	}
	public void setWorld(World3D world){
		this._world=world;
	}
	public void setTextureCoords(float textUV[]){
		GLES20.glGenBuffers(1, vbTextureCords,0);
		FloatBuffer vertexFloatBuffer=ByteBuffer.allocateDirect(textUV.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		vertexFloatBuffer.put(textUV);
		vertexFloatBuffer.position(0);
		
		//GLES20.glGenBuffers(1, vbVertices, 0);
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbTextureCords[0]);
		GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, textUV.length*4, vertexFloatBuffer, GLES20.GL_STATIC_DRAW);
		
		bTextures=true;
		
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
		
	}
	
	public void setVertexBuffer(float vertexData[]){
		GLES20.glGenBuffers(1, vbVertices, 0);
		//Short
		FloatBuffer vertexFloatBuffer=ByteBuffer.allocateDirect(vertexData.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		vertexFloatBuffer.put(vertexData);
		vertexFloatBuffer.position(0);
		
		//GLES20.glGenBuffers(1, vbVertices, 0);
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbVertices[0]);
		GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertexData.length*4, vertexFloatBuffer, GLES20.GL_STATIC_DRAW);
		numOfVertices=vertexData.length/3;
		bVertices=true;
		
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);

		
		//vertexFloatBuffer.
	}
	
	public void setNormalBuffer(float normalData[]){
		GLES20.glGenBuffers(1, vbNormals, 0);
		//Short
		FloatBuffer vertexFloatBuffer=ByteBuffer.allocateDirect(normalData.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		vertexFloatBuffer.put(normalData);
		vertexFloatBuffer.position(0);

		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbNormals[0]);
		GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, normalData.length*4, vertexFloatBuffer, GLES20.GL_STATIC_DRAW);
		bNormals=true;
		numOfNormals=normalData.length/3;
		
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);

	}
	
	public void setIndices(short indices[]){
		GLES20.glGenBuffers(1, vbIndices, 0);
		ShortBuffer vertexFloatBuffer=ByteBuffer.allocateDirect(indices.length*2).order(ByteOrder.nativeOrder()).asShortBuffer();
		vertexFloatBuffer.put(indices);
		vertexFloatBuffer.position(0);
		
		//GLES20.glGenBuffers(1, buffers[1], 0);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, vbIndices[0]);
		GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, indices.length*2, vertexFloatBuffer, GLES20.GL_STATIC_DRAW);
		numOfIndices=indices.length;
		bIndices=true;
		
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
		
	}
	
	public void render(){
		int posHandle=_world.getOgl_aPositionHandle();
		int normalHandle=_world.getOgl_aNormalHandle();
		int texHandle=_world.getOgl_aTexCoordHandle();
		int texUniform=_world.getOgl_uTexCoord();
		
		String texId=getTextureId();
		if(texId!=null && !texId.equals("")){
			GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
			int thandle=TextureCache.getInstance().getTextureId(texId);
	        
	        // Bind the texture to this unit.
	        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, thandle);
	        
	        // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.
	        GLES20.glUniform1i(texUniform, 0); 
		}
		
		
		//if(getTextureId()!=null && g)
		
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbVertices[0]);
		GLES20.glEnableVertexAttribArray(posHandle);
		GLES20.glVertexAttribPointer(posHandle, 3, GLES20.GL_FLOAT, false, 12, 0);
		
		if(bNormals){
			GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbNormals[0]);
			GLES20.glEnableVertexAttribArray(normalHandle);
			GLES20.glVertexAttribPointer(normalHandle, 3, GLES20.GL_FLOAT, false, 12, 0);
		}
		if(bTextures){
			GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbTextureCords[0]);
			GLES20.glEnableVertexAttribArray(texHandle);
			GLES20.glVertexAttribPointer(texHandle, 2, GLES20.GL_FLOAT, false, 8, 0);
		}
		
		if(bIndices){	
			//System.out.println("render indices");
			GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, vbIndices[0]);
			
			GLES20.glDrawElements(GLES20.GL_TRIANGLES, numOfIndices, GLES20.GL_UNSIGNED_SHORT, 0);			
			
		}else{
			GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, numOfVertices);
		}
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
		
		GLES20.glDisableVertexAttribArray(posHandle);
		GLES20.glDisableVertexAttribArray(normalHandle);
		//System.out.println("rendered");

		
		
		
		
	}

}
