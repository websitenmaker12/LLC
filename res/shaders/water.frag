uniform sampler2D waterTex;
uniform sampler2D gridTex;

varying vec2 waterCoord;
varying vec2 gridCoord;
varying float waterAlpha;

void main() {


	vec4 waterColor = texture2D(waterTex, waterCoord);
	vec4 gridColor = texture2D(gridTex, gridCoord);
	
	gl_FragColor = vec4((waterColor * gridColor).rgb, waterAlpha);
	
	gl_FragColor = gridColor;
}