#version 110

const vec3 lightPos = vec3(-10.0, 10.0, 10.0);
const vec4 ambient = vec4(0.7, 0.7, 0.7, 1.0);
const vec4 diffuse = vec4(0.8, 0.8, 0.8, 1.0);
const vec4 specular = vec4(0.4, 0.4, 0.4, 1.0);

const vec4 materialAmbient = vec4(1.0, 1.0, 1.0, 1.0);
const vec4 materialDiffuse = vec4(1.0, 1.0, 1.0, 1.0);
const vec4 materialSpecular = vec4(1.0, 1.0, 1.0, 1.0);
const float shininess = 4.0;

varying vec4 vertColor;
varying vec3 normal;
varying vec3 vertex;
varying vec2 texCoord;

uniform sampler2D tex;

vec4 phongShade()
{
   vec3 L = normalize(lightPos - vertex);
   vec3 E = normalize(-vertex); // we are in Eye Coordinates, so EyePos is (0,0,0)
   vec3 R = normalize(-reflect(L, normal));

   // ambient component
   vec4 Iamb = materialAmbient * ambient;

   // diffuse component
   vec4 Idiff = materialDiffuse * diffuse * max(dot(normal, L), 0.0);
   Idiff = clamp(Idiff, 0.0, 1.0);

   // specular component
   vec4 Ispec = materialSpecular * specular * pow(max(dot(R, E), 0.0), 0.3 * shininess);
   Ispec = clamp(Ispec, 0.0, 1.0);

   // total color
   return Iamb + Idiff + Ispec;
}

void main() {
	vec4 texColor = texture2D(tex, texCoord);
	//gl_FragColor = texColor * vertColor * phongShade();
	//gl_FragColor = normal;
	gl_FragData[0] = texColor * vertColor * phongShade();
}