package ausoft.projectx2.engine;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;

public class TextureCache {
	private Map<String, Integer> textureMap=new HashMap<String, Integer>();
	private static TextureCache _instance;
	
	
	
	private TextureCache(){
		
	}
	
	public static TextureCache getInstance()
	{
		if(_instance==null){
			_instance=new TextureCache();
		}
		return _instance;
	}
	
	public int getTextureId(String id){
		return textureMap.get(id).intValue();
	}
	
	public int loadTexture ( InputStream is,String id )
    {
        int[] textureId = new int[1];
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeStream(is);
        byte[] buffer = new byte[bitmap.getWidth() * bitmap.getHeight() * 3];
        
        for ( int y = 0; y < bitmap.getHeight(); y++ )
            for ( int x = 0; x < bitmap.getWidth(); x++ )
            {
                int pixel = bitmap.getPixel(x, y);
                buffer[(y * bitmap.getWidth() + x) * 3 + 0] = (byte)((pixel >> 16) & 0xFF);
                buffer[(y * bitmap.getWidth() + x) * 3 + 1] = (byte)((pixel >> 8) & 0xFF);
                buffer[(y * bitmap.getWidth() + x) * 3 + 2] = (byte)((pixel >> 0) & 0xFF);
            }
        
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(bitmap.getWidth() * bitmap.getHeight() * 3);
        byteBuffer.put(buffer).position(0);
            
        GLES20.glGenTextures ( 1, textureId, 0 );
        GLES20.glBindTexture ( GLES20.GL_TEXTURE_2D, textureId[0] );

        GLES20.glTexImage2D ( GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGB, bitmap.getWidth(), bitmap.getHeight(), 0, 
                              GLES20.GL_RGB, GLES20.GL_UNSIGNED_BYTE, byteBuffer );
    
        GLES20.glTexParameteri ( GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR );
        GLES20.glTexParameteri ( GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR );
        GLES20.glTexParameteri ( GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE );
        GLES20.glTexParameteri ( GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE );
        
        textureMap.put(id, textureId[0]);
        
        return textureId[0];
    }

}
