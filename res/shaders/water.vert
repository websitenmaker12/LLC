varying vec2 waterCoord;
varying float waterAlpha;

void main() {
	gl_Position = ftransform();
	
	waterCoord = gl_MultiTexCoord0.st;
	waterAlpha = gl_Color.a;
}