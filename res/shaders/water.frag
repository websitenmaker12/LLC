uniform sampler2D waterTex;
uniform sampler2D waterNormalsTex;
uniform sampler2D gridTex;

uniform vec2 viewportDim;
uniform float waterCellCount;
uniform float waterTime;

varying vec2 texCoord;

void main() {
	// fetch water color from texture
	vec4 waterColor = texture2D(waterTex, texCoord * waterCellCount);
	
	// fetch normal vector on water from normal texture
	vec3 waterNormal = texture2D(waterNormalsTex, texCoord * waterCellCount + waterTime).xyz - 0.5f;
	
	// derive grid coord from fragment coord
	vec2 gridCoord = gl_FragCoord.xy / viewportDim;
	
	// refract grid coord with normal
	vec2 refractedOffset = waterNormal.xy * 0.05f;
	vec2 refractedGridCoord = gridCoord + refractedOffset;
	
	// fetch grid color from rendered grid texture
	vec4 gridColor = texture2D(gridTex, refractedGridCoord);
	
	// mix water color and refracted grid color
	gl_FragColor = vec4((waterColor * gridColor).rgb, 1); // alpha for water
	//gl_FragColor = gridColor;
	//gl_FragColor = vec4(refractedGridCoord, 0.0, 1.0);
}