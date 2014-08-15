uniform sampler2D waterTex;
uniform sampler2D waterNormalsTex;
uniform sampler2D gridTex;

uniform vec2 viewportDim;
uniform float waterCellCount;

varying vec2 texCoord;
varying float waterAlpha;

void main() {
	vec4 waterColor = texture2D(waterTex, texCoord * waterCellCount);
	vec3 waterNormal = texture2D(waterNormalsTex, texCoord * waterCellCount).xyz;
	
	vec2 gridCoord = gl_FragCoord.xy / viewportDim;
	vec2 refractedOffset = waterNormal.xy * 0.05f;
	vec2 refractedGridCoord = gridCoord + refractedOffset;
	
	vec4 gridColor = texture2D(gridTex, refractedGridCoord);
	
	gl_FragColor = vec4((waterColor * gridColor).rgb, waterAlpha);
	gl_FragColor = gridColor;
	//gl_FragColor = vec4(refractedGridCoord, 0.0, 1.0);
}