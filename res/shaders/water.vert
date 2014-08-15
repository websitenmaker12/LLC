varying vec2 waterCoord;
varying vec2 gridCoord;
varying float waterAlpha;

void main() {
	gl_Position = ftransform();
	
	waterCoord = gl_MultiTexCoord0.st;
	gridCoord = gl_MultiTexCoord1.st;
	waterAlpha = gl_Color.a;
}