varying vec4 vertColor;
varying vec3 normal;
varying vec3 vertex;
varying vec2 texCoord;

void main() {
	gl_Position = ftransform();
	vertColor = gl_Color;
	texCoord = gl_MultiTexCoord0.st;
	normal = gl_Normal;
	vertex = gl_Position;
}