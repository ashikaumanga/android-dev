package edu.union.graphics;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.res.Resources;
import android.util.Log;

/**
 * A helper abstract handler implementation which concrete ModelLoaders can extend from.
 * Extending classes must provide the:
 * <ul>
 * <li><code>void load(InputStream is);</code>
 * <li><code>boolean canLoad(File f);</code>
 * </ul>
 * @author bburns
 */
public abstract class AbstractModelLoader implements ModelLoader {
	@SuppressWarnings("unused")
	protected MeshFactory factory;
	protected String resourceID;
	protected String packageID;
	protected Resources resources;
	
	public void setResourceID(String resourceID) {
		this.resourceID = resourceID;
		if (resourceID.indexOf(":") > -1)
			this.packageID = resourceID.split(":")[0];
	}
	
	public AbstractModelLoader(Resources resources){
		this.resources=resources;
	}
	
	public Model loadFromResouceID() throws IOException{
		int resourceId=resources.getIdentifier(resourceID, null, null);
		InputStream inp=resources.openRawResource(resourceId);
		return load(inp);
	}


	
	/**
	 * {@inheritDoc}
	 */
	public void setFactory(MeshFactory f) {
		factory = f;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Model load(String file) throws IOException
	{
		return load(new File(file));
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Model load(File f) throws IOException 
	{
		return load(new FileInputStream(f));
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean canLoad(String f) {
		return canLoad(new File(f));
	}
}
