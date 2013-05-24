
package ausoft.projectx2;

import java.io.File;

import edu.union.graphics.FloatMesh;
import edu.union.graphics.Mesh;
import edu.union.graphics.Model;
import edu.union.graphics.ObjLoader;

public class ObjLoadExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		ObjLoader loader=new ObjLoader(null);
		loader.setFactory(FloatMesh.factory());
		Model m=loader.load(new File("c:/opt/t-rex/model.obj"));
		Mesh mesh=m.getFrame(0).getMesh();
		
		int cVerts=mesh.getVertexCount();
		int cFaces=mesh.getFaceCount();
		
		System.out.println("Verts "+cVerts);
		System.out.println("Faces "+cFaces*6);
		
		float faceTexUVs[]=new float[(cFaces)*3*2];
		for(int i=0;i<cFaces;i++){
			int vertInds[]=mesh.getFaceTextures(i);
			for(int v=0;v<3;v++){
				float vUVs[]=mesh.getTextureCoordinatef(vertInds[v]);
				int ind=i*6+v*2;
				System.out.println(ind);

				faceTexUVs[ind]=vUVs[0];
				faceTexUVs[ind+1]=vUVs[1];
			}
			//float v1UVs[]=mesh.getTextureCoordinatef(vertInds[0]);
			//float v1UVs[]=mesh.getTextureCoordinatef(vertInds[0]);
			
		}

	}

}
