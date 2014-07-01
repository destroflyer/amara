uniform sampler2D m_Texture;
uniform sampler2D m_DepthTexture;
uniform mat4 m_ViewProjectionMatrixInverse;
uniform vec2 m_MapSize;
uniform sampler2D m_Fog;
varying vec2 texCoord;

vec3 getPosition(in float depth, in vec2 uv){
    vec4 pos = vec4(uv, depth, 1.0) * 2.0 - 1.0;
    pos = m_ViewProjectionMatrixInverse * pos;
    return pos.xyz / pos.w;
}

void main(){
    float depth = texture2D(m_DepthTexture, texCoord).r;
    vec3 pos = getPosition(depth, texCoord);
    float fogX = (pos.x / m_MapSize.x);
    float fogY = (pos.z / m_MapSize.y);
    float density = texture2D(m_Fog, vec2(fogX, fogY)).r;
    vec3 color = texture2D(m_Texture, texCoord).rgb;
    gl_FragColor = vec4(color.r * density, color.g * density, color.b * density, 1.0);
}