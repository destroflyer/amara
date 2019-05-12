#import "Common/ShaderLib/Optics.glsllib"
varying vec2 texCoord;
uniform float m_texturesize;
//varying vec3 vPosition;
//varying vec3 vViewDir;

#ifdef NORMALMAP
    uniform sampler2D m_NormalMap;
    uniform float  m_NormalMapPower;

    varying vec3 mat;
    varying vec3 I;
    //varying vec3 N;
    varying vec3 worldPos;
#endif

varying vec3 vNormal;

#ifdef SPECULAR
    vec3 specularColor;
    uniform sampler2D m_MatCapSpecular;
    uniform float m_specularIntensity;
#endif

uniform vec4 m_Multiply_Color;
uniform float m_colorIntensity;

uniform float m_RefPower;
uniform float m_RefIntensity;
varying vec3 refVec;
uniform ENVMAP m_RefMap;

#ifdef CHROMATIC_ABERRATION
    varying vec3 refVecG;
    varying vec3 refVecB;
#endif

#ifdef FOG
    varying float fog_z;
    uniform vec4 m_FogColor;
    vec4 fogColor;
    float fogFactor;
#endif

#ifdef FOG_SKY
    uniform ENVMAP m_FogSkyBox;
#endif


void main() {

    vec2 newTexCoord;
    newTexCoord = texCoord;

    #ifdef NORMALMAP
        vec3 normalHeight = texture2D(m_NormalMap, newTexCoord).rgb;
        //vec3 normal = ((normalHeight.xyz  - vec3(0.5)) * vec3(2.0));
        vec3 normal = (normalHeight.xyz * vec3(2.0) - vec3(1.0));
        normal = normalize(normal);

        #if defined (NOR_INV_X) && (NORMALMAP)
            normal.x = -normal.x;
        #endif

        #if defined (NOR_INV_Y) && (NORMALMAP)
            normal.y = -normal.y;
        #endif

        #if defined (NOR_INV_Z) && (NORMALMAP)
            normal.z = -normal.z;
        #endif

    #else
        vec3 normal = vNormal;
    #endif

    //vec3  vmr = vNormal.xyz;
    //vec3 coords = (vmr);
    vec3 coords = vNormal.xyz;

    #if defined (NORMALMAP)
        vec3  normalz = mat.xyz * normal.xyz;
        vec4 refGet = Optics_GetEnvColor(m_RefMap, (refVec + normalz * m_NormalMapPower));

        #ifdef CHROMATIC_ABERRATION
            refGet.g = Optics_GetEnvColor(m_RefMap, (refVecG + normalz * m_NormalMapPower)).g;
            refGet.b = Optics_GetEnvColor(m_RefMap, (refVecB + normalz * m_NormalMapPower)).b;
        #endif

        #if defined (SPECULAR)
            specularColor = texture2D(m_MatCapSpecular, vec2(((coords.xyz + normalz.xyz* vec3(m_NormalMapPower)) * vec3(0.495) + vec3(0.5))) ).rgb;
        #endif

    #else
        vec4 refGet = Optics_GetEnvColor(m_RefMap, refVec);

        #ifdef CHROMATIC_ABERRATION
            refGet.g = Optics_GetEnvColor(m_RefMap, refVecG).g;
            refGet.b = Optics_GetEnvColor(m_RefMap, refVecB).b;
        #endif

        #if defined (SPECULAR)
            specularColor = texture2D(m_MatCapSpecular, vec2(coords * vec3(0.495) + vec3(0.5))).rgb;
        #endif
    #endif

    vec3 color = refGet.xyz * m_Multiply_Color.xyz * m_colorIntensity;

    #ifdef SPECULAR
        gl_FragColor.rgb = color + specularColor * m_specularIntensity;
    #else
        gl_FragColor.rgb = color;
    #endif


    float alpha = m_Multiply_Color.w;
    if(alpha < 0.015){
        discard;
    }
    gl_FragColor.a = alpha;


    #ifdef FOG
        fogColor = m_FogColor;

        #ifdef FOG_SKY
            fogColor.rgb = Optics_GetEnvColor(m_FogSkyBox, I).rgb;
        #endif

        float fogDistance = fogColor.a;
        float depth = (fog_z - fogDistance) / fogDistance;
        depth = max(depth, 0.0);
        fogFactor = exp2(-depth * depth);
        fogFactor = clamp(fogFactor, 0.05, 1.0);

        gl_FragColor.rgb = mix(fogColor.rgb, gl_FragColor.rgb, vec3(fogFactor));

    #endif
}
