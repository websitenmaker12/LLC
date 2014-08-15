uniform sampler2D waterTex;
uniform sampler2D gridTex;
uniform vec2 viewportDim;

uniform float waterCellCount;

varying vec2 texCoord;
varying float waterAlpha;

void main() {
	vec2 gridCoord = gl_FragCoord.xy / viewportDim;
	
	vec4 waterColor = texture2D(waterTex, texCoord * waterCellCount);
	vec4 gridColor = texture2D(gridTex, gridCoord);
	
	gl_FragColor = vec4((waterColor * gridColor).rgb, waterAlpha);
	//gl_FragColor = gridColor;
}