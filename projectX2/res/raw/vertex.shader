uniform mat4 uMVPMatrix;
uniform mat4 uMVMatrix;
uniform mat4 uVMatrix;
uniform vec3 uLightPos;

attribute vec4 aPosition; 
attribute vec4 aColor;
attribute vec3 aNormal;
attribute vec2 aTexCoord;

varying vec4 vColor;
varying vec2 vTexCoord;

void main()
{   
//vec3 uuLightPos=  vec4(0,0,-20,0);
vec3 uuLightPos= vec3(uVMatrix * vec4(0,0,-30,0));
vec4 aColor= vec4(1.0,1.0,1.0,0.1);
vec3 modelViewVertex = vec3(uMVMatrix * aPosition);
vec3 modelViewNormal = vec3(uMVMatrix * vec4(aNormal, 0.0));
float distance = length(uuLightPos - modelViewVertex);
vec3 lightVector = normalize(uuLightPos - modelViewVertex);
float diffuse = max(dot(modelViewNormal, lightVector), 0.1);
//diffuse = diffuse * (1.0 / (1.0 + (0.25 * distance ))); 
vColor = aColor*diffuse;
vTexCoord=aTexCoord;
gl_Position = uMVPMatrix * aPosition; 
}