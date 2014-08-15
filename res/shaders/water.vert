varying vec2 texCoord;
varying float waterAlpha;

void main() {
	gl_Position = ftransform();
	
	texCoord = gl_MultiTexCoord0.st;
	
	
	waterAlpha = gl_Color.a;
}