varying vec4 vertColor;
varying vec2 texCoord;

void main() {
	gl_Position = ftransform();
	vertColor = gl_Color;
	texCoord = gl_MultiTexCoord0.st;
}