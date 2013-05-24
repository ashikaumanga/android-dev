package ausoft.projectx2.engine;

public class BoxMesh3D extends AbstractMesh3D {
	
	public BoxMesh3D() {
		// TODO Auto-generated constructor stub
		final float[] cubePositionData =
			{
			// In OpenGL counter-clockwise winding is default. This means that when we look at a triangle,
			// if the points are counter-clockwise we are looking at the "front". If not we are looking at
			// the back. OpenGL has an optimization where all back-facing triangles are culled, since they
			// usually represent the backside of an object and aren't visible anyways.

			// Front face
			-1.0f, 1.0f, 1.0f,
			-1.0f, -1.0f, 1.0f,
			1.0f, 1.0f, 1.0f,
			-1.0f, -1.0f, 1.0f,
			1.0f, -1.0f, 1.0f,
			1.0f, 1.0f, 1.0f,

			// Right face
			1.0f, 1.0f, 1.0f,
			1.0f, -1.0f, 1.0f,
			1.0f, 1.0f, -1.0f,
			1.0f, -1.0f, 1.0f,
			1.0f, -1.0f, -1.0f,
			1.0f, 1.0f, -1.0f,

			// Back face
			1.0f, 1.0f, -1.0f,
			1.0f, -1.0f, -1.0f,
			-1.0f, 1.0f, -1.0f,
			1.0f, -1.0f, -1.0f,
			-1.0f, -1.0f, -1.0f,
			-1.0f, 1.0f, -1.0f,

			// Left face
			-1.0f, 1.0f, -1.0f,
			-1.0f, -1.0f, -1.0f,
			-1.0f, 1.0f, 1.0f,
			-1.0f, -1.0f, -1.0f,
			-1.0f, -1.0f, 1.0f,
			-1.0f, 1.0f, 1.0f,

			// Top face
			-1.0f, 1.0f, -1.0f,
			-1.0f, 1.0f, 1.0f,
			1.0f, 1.0f, -1.0f,
			-1.0f, 1.0f, 1.0f,
			1.0f, 1.0f, 1.0f,
			1.0f, 1.0f, -1.0f,

			// Bottom face
			1.0f, -1.0f, -1.0f,
			1.0f, -1.0f, 1.0f,
			-1.0f, -1.0f, -1.0f,
			1.0f, -1.0f, 1.0f,
			-1.0f, -1.0f, 1.0f,
			-1.0f, -1.0f, -1.0f,
			};

			// R, G, B, A
			final float[] cubeColorData =
			{
			// Front face (red)
			1.0f, 0.0f, 0.0f, 1.0f,
			1.0f, 0.0f, 0.0f, 1.0f,
			1.0f, 0.0f, 0.0f, 1.0f,
			1.0f, 0.0f, 0.0f, 1.0f,
			1.0f, 0.0f, 0.0f, 1.0f,
			1.0f, 0.0f, 0.0f, 1.0f,

			// Right face (green)
			0.0f, 1.0f, 0.0f, 1.0f,
			0.0f, 1.0f, 0.0f, 1.0f,
			0.0f, 1.0f, 0.0f, 1.0f,
			0.0f, 1.0f, 0.0f, 1.0f,
			0.0f, 1.0f, 0.0f, 1.0f,
			0.0f, 1.0f, 0.0f, 1.0f,

			// Back face (blue)
			0.0f, 0.0f, 1.0f, 1.0f,
			0.0f, 0.0f, 1.0f, 1.0f,
			0.0f, 0.0f, 1.0f, 1.0f,
			0.0f, 0.0f, 1.0f, 1.0f,
			0.0f, 0.0f, 1.0f, 1.0f,
			0.0f, 0.0f, 1.0f, 1.0f,

			// Left face (yellow)
			1.0f, 1.0f, 0.0f, 1.0f,
			1.0f, 1.0f, 0.0f, 1.0f,
			1.0f, 1.0f, 0.0f, 1.0f,
			1.0f, 1.0f, 0.0f, 1.0f,
			1.0f, 1.0f, 0.0f, 1.0f,
			1.0f, 1.0f, 0.0f, 1.0f,

			// Top face (cyan)
			0.0f, 1.0f, 1.0f, 1.0f,
			0.0f, 1.0f, 1.0f, 1.0f,
			0.0f, 1.0f, 1.0f, 1.0f,
			0.0f, 1.0f, 1.0f, 1.0f,
			0.0f, 1.0f, 1.0f, 1.0f,
			0.0f, 1.0f, 1.0f, 1.0f,

			// Bottom face (magenta)
			1.0f, 0.0f, 1.0f, 1.0f,
			1.0f, 0.0f, 1.0f, 1.0f,
			1.0f, 0.0f, 1.0f, 1.0f,
			1.0f, 0.0f, 1.0f, 1.0f,
			1.0f, 0.0f, 1.0f, 1.0f,
			1.0f, 0.0f, 1.0f, 1.0f
			};

			// X, Y, Z
			// The normal is used in light calculations and is a vector which points
			// orthogonal to the plane of the surface. For a cube model, the normals
			// should be orthogonal to the points of each face.
			final float[] cubeNormalData =
			{
			// Front face
			0.0f, 0.0f, 1.0f,
			0.0f, 0.0f, 1.0f,
			0.0f, 0.0f, 1.0f,
			0.0f, 0.0f, 1.0f,
			0.0f, 0.0f, 1.0f,
			0.0f, 0.0f, 1.0f,

			// Right face
			1.0f, 0.0f, 0.0f,
			1.0f, 0.0f, 0.0f,
			1.0f, 0.0f, 0.0f,
			1.0f, 0.0f, 0.0f,
			1.0f, 0.0f, 0.0f,
			1.0f, 0.0f, 0.0f,

			// Back face
			0.0f, 0.0f, -1.0f,
			0.0f, 0.0f, -1.0f,
			0.0f, 0.0f, -1.0f,
			0.0f, 0.0f, -1.0f,
			0.0f, 0.0f, -1.0f,
			0.0f, 0.0f, -1.0f,

			// Left face
			-1.0f, 0.0f, 0.0f,
			-1.0f, 0.0f, 0.0f,
			-1.0f, 0.0f, 0.0f,
			-1.0f, 0.0f, 0.0f,
			-1.0f, 0.0f, 0.0f,
			-1.0f, 0.0f, 0.0f,

			// Top face
			0.0f, 1.0f, 0.0f,
			0.0f, 1.0f, 0.0f,
			0.0f, 1.0f, 0.0f,
			0.0f, 1.0f, 0.0f,
			0.0f, 1.0f, 0.0f,
			0.0f, 1.0f, 0.0f,

			// Bottom face
			0.0f, -1.0f, 0.0f,
			0.0f, -1.0f, 0.0f,
			0.0f, -1.0f, 0.0f,
			0.0f, -1.0f, 0.0f,
			0.0f, -1.0f, 0.0f,
			0.0f, -1.0f, 0.0f
			};

			// S, T (or X, Y)
			// Texture coordinate data.
			// Because images have a Y axis pointing downward (values increase as you move down the image) while
			// OpenGL has a Y axis pointing upward, we adjust for that here by flipping the Y axis.
			// What's more is that the texture coordinates are the same for every face.
			final float[] cubeTextureCoordinateData =
			{
			// Front face
			0.0f, 0.0f,
			0.0f, 1.0f,
			1.0f, 0.0f,
			0.0f, 1.0f,
			1.0f, 1.0f,
			1.0f, 0.0f,

			// Right face
			0.0f, 0.0f,
			0.0f, 1.0f,
			1.0f, 0.0f,
			0.0f, 1.0f,
			1.0f, 1.0f,
			1.0f, 0.0f,

			// Back face
			0.0f, 0.0f,
			0.0f, 1.0f,
			1.0f, 0.0f,
			0.0f, 1.0f,
			1.0f, 1.0f,
			1.0f, 0.0f,

			// Left face
			0.0f, 0.0f,
			0.0f, 1.0f,
			1.0f, 0.0f,
			0.0f, 1.0f,
			1.0f, 1.0f,
			1.0f, 0.0f,

			// Top face
			0.0f, 0.0f,
			0.0f, 1.0f,
			1.0f, 0.0f,
			0.0f, 1.0f,
			1.0f, 1.0f,
			1.0f, 0.0f,

			// Bottom face
			0.0f, 0.0f,
			0.0f, 1.0f,
			1.0f, 0.0f,
			0.0f, 1.0f,
			1.0f, 1.0f,
			1.0f, 0.0f
			};
		
		setVertexBuffer(cubePositionData);
		setNormalBuffer(cubeNormalData);
		setTextureCoords(cubeTextureCoordinateData);
	}

}
