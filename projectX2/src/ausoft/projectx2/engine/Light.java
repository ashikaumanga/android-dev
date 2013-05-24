package ausoft.projectx2.engine;

//import javax.vecmath.Vector4f;

public class Light {
	public float[] position=new float[3];
	public float[] ambient_color=new float[4];
	public float[] diffuse_color=new float[4];
	public float[] specular_color=new float[4];
	public float[] attenuation_factors=new float[4];
	public float spot_exponent;
	public float spot_cutoff_angle;
	public boolean compute_distance_attenuation;
	public Light(){
		position[0]=0.0f;
		position[1]=0.0f;
		position[2]=0.0f;
		position[3]=0.0f;
		
	}
}
