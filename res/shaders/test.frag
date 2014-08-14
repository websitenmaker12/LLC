varying vec4 vertColor;
varying vec2 texCoord;

uniform sampler2D tex;

void main() {
	vec4 texColor = texture2D(tex, texCoord);
	gl_FragColor = texColor * vertColor;
}