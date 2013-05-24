package ausoft.projectx2.engine;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.res.Resources;
import android.util.Log;



import edu.union.graphics.FloatMesh;
import edu.union.graphics.Mesh;
import edu.union.graphics.Model;
import edu.union.graphics.ObjLoader;

public class SimpleObjMesh extends AbstractMesh3D {
	

	public SimpleObjMesh(String resoureID,Resources res){
		ObjLoader loader=new ObjLoader(res);

		loader.setFactory(FloatMesh.factory());
		loader.setResourceID(resoureID);
		
		List<float[]> lstVertex=new ArrayList<float[]>();
		List<short[]> lstIndices=new ArrayList<short[]>();
		List<float[]> lstNormal=new ArrayList<float[]>();
		List<float[]> lstTexCoords=new ArrayList<float[]>();
		
		
		
		try {
			Model model=loader.loadFromResouceID();
			Mesh mesh=model.getFrame(0).getMesh();
			mesh.scale(0.02f);
			
			mesh.calculateVertexNormals();
			
			for(int i=0;i<mesh.getVertexCount();i++){
				float v[]=mesh.getVertexf(i);
				float n[]=mesh.getNormalf(i);
				lstVertex.add(v);
				lstNormal.add(n);
			}
			
			for(int f=0;f<mesh.getFaceCount();f++){
				int indices[]=mesh.getFace(f);
				short sindices[]=new short[3];
				sindices[0]=(short)indices[0];
				sindices[1]=(short)indices[1];
				sindices[2]=(short)indices[2];
				lstIndices.add(sindices);
			}
			
			Map<Integer,Integer> indexTexCoord=new HashMap<Integer, Integer>();
			
			for(int f=0;f<mesh.getFaceCount();f++){
				int indices[]=mesh.getFace(f);
				//short sindices[]=new short[3];
				//sindices[0]=(short)indices[0];
				//sindices[1]=(short)indices[1];
				//sindices[2]=(short)indices[2];
				//lstIndices.add(sindices);
				//Texture coords
				int c=0;
				int[] texCoords=mesh.getFaceTextures(f);
				for(int v=0;v<3;v++){
					int texCoordInd=texCoords[v];
					int vertIndex=indices[v];
					if(!indexTexCoord.containsKey(new Integer(vertIndex))){
						indexTexCoord.put(new Integer(vertIndex),new Integer( texCoordInd));
					}else{
						Integer texInd=indexTexCoord.get(new Integer(vertIndex));
						if(texInd.intValue()!=texCoordInd){//texcoord does not match
							float oldVerts[]=lstVertex.get(vertIndex);
							float newVerts[]=new float[3];
							newVerts[0]=oldVerts[0];
							newVerts[1]=oldVerts[1];
							newVerts[2]=oldVerts[2];
							
							lstVertex.add(newVerts);
							indexTexCoord.put(new Integer(lstVertex.size()-1),texCoordInd );
							//ls
							//modify the normal list
							short[] oldInds=lstIndices.get(f);
							//short[] newInds=new short[3];
							//newInds[0]=oldInds[0];
							//newInds[1]=oldInds[1];
							//newInds[2]=oldInds[2];
							oldInds[v]=(short)(lstVertex.size()-1);
							/**
							System.out.println("***>>>> Old Indices : "+oldInds[0]+" "+oldInds[1]+" "+oldInds[2]);
							oldInds[v]=(short)(lstVertex.size()-1);
							lstIndices.add(vertIndex,oldInds);
							 oldInds=lstIndices.get(vertIndex);
							System.out.println("***>>>> New Indices : "+oldInds[0]+" "+oldInds[1]+" "+oldInds[2]);
							**/
							
							float oldNorms[]=lstNormal.get(vertIndex);						
							lstNormal.add(oldNorms);
							
							//lstIndices.remove(c);
							//lstIndices.add(c, (short)(lstVertex.size()-1));
							
						//	lstVertex.
							//float old
						}
						//System.out.println(">>>>> "+vertIndex+" val: "+texInd.intValue()+ " New Value: "+texCoordInd);
					}
					c++;
				}
				
			}
			
			Set<Integer> indSet=indexTexCoord.keySet();
			List<Integer> indList=new ArrayList<Integer>(indexTexCoord.keySet());
			Collections.sort(indList);
			for(Integer key : indList){
				Integer texInd=indexTexCoord.get(key);
				int k=texInd.intValue();
				float ftextmp[]=mesh.getTextureCoordinatef(k);
				//System.out.println(key.intValue()+ " Texture Coords "+k+" "+ftextmp[0]+" "+ftextmp[1]);
				lstTexCoords.add(ftextmp);
			}
			
			//Set vertices
			float[] fv=new float[lstVertex.size()*3];
			for(int i=0;i<lstVertex.size();i++)
			{
				fv[3*i]=lstVertex.get(i)[0];
				fv[3*i+1]=lstVertex.get(i)[1];
				fv[3*i+2]=lstVertex.get(i)[2];
			}
			
			setVertexBuffer(fv);
			
			//Set indices
			System.out.println("Vertex Number: "+lstVertex.size()+" Texcoord number: "+ lstTexCoords.size());
			short[] fi=new short[lstIndices.size()*3];
			for(int i=0;i<lstIndices.size();i++){
				fi[i*3]=lstIndices.get(i)[0];
				fi[i*3+1]=lstIndices.get(i)[1];
				fi[i*3+2]=lstIndices.get(i)[2];
			}
			
			setIndices(fi);
			
			//Set normals
			float[] fn=new float[lstNormal.size()*3];
			for(int i=0;i<lstNormal.size();i++){
				fn[i*3]=-lstNormal.get(i)[0];
				fn[i*3+1]=-lstNormal.get(i)[1];
				fn[i*3+2]=-lstNormal.get(i)[2];
			}
			
			setNormalBuffer(fn);
			
			//Set texcoords
			float[] ft=new float[lstTexCoords.size()*2];
			for(int i=0;i<lstTexCoords.size();i++){
				ft[i*2]=lstTexCoords.get(i)[0];
				ft[i*2+1]=1-lstTexCoords.get(i)[1];
				//ft[i*3+2]=lstTexCoords.get(i)[2];
			}
			
			setTextureCoords(ft);
			
			
			//setVertexBuffer((float[])lstVertex.toArray());
			
			
			
			
			/**
			int nIndex=mesh.getVertexCount();
			//mesh.get
			float vertices[]=new float[nIndex*3];
			for(int i=0;i<nIndex;i++){
				float tmp[]=mesh.getVertexf(i);
				vertices[i*3]=tmp[0];
				vertices[i*3+1]=tmp[1];
				vertices[i*3+2]=tmp[2];
				//System.out.println("Vertex "+i+" "+vertices[i*3]+" "+vertices[i*3+1]+" "+vertices[i*3+2]);
			}
			
			
			
						
			
					
			int nFaces=mesh.getFaceCount();
			short indices[]=new short[nFaces*3];
			for(int i=0;i<nFaces;i++){
				int ind[]=mesh.getFace(i);
				indices[i*3]=(short)ind[0];
				indices[i*3+1]=(short)ind[1];
				indices[i*3+2]=(short)ind[2];
				//System.out.println("Face "+i+" "+indices[i*3]+" "+indices[i*3+1]+" "+indices[i*3+2]);
			}
		//	mesh.calculateVertexNormals();
			
			
			
			
			int cVerts=mesh.getVertexCount();
			int cFaces=mesh.getFaceCount();
			
			//System.out.println("Verts "+cVerts);
			//System.out.println("Faces "+cFaces*6);
			
			float faceTexUVs[]=new float[(cFaces)*3*2];
			for(int i=0;i<cFaces;i++){
				int vertInds[]=mesh.getFaceTextures(i);
				//System.out.println("TexV: "+ vertInds[0]+" "+vertInds[1]+" "+vertInds[2]);
				//System.out.println("vert :"+vertInds.length);
				for(int v=0;v<3;v++){
					//mesh.get
					float vUVs[]=mesh.getTextureCoordinatef(vertInds[v]);
					int ind=i*6+v*2;
					System.out.print("("+vUVs[0]+","+vUVs[1]+")  ");
					faceTexUVs[ind]=vUVs[0];
					faceTexUVs[ind+1]=vUVs[1];
				}
				System.out.println("");
			}
			
			
			
			
			
			float normals[]=new float[nIndex*3*3];
			for(int i=0;i<nIndex;i++){
				float tmp[]=mesh.getNormalf(i);
				//mesh.get
				//System.out.println("Face normal "+ tmp[0]+" "+tmp[1]+" "+tmp[2]);
				normals[i*3]=-tmp[0];
				normals[i*3+1]=-tmp[1];
				normals[i*3+2]=-tmp[2];
			}
			
		
		
			Log.i("mesh", "vertex count "+vertices.length);
			setVertexBuffer(vertices);
			setIndices(indices);
			setNormalBuffer(normals);
			setTextureCoords(faceTexUVs);
			**/

		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
